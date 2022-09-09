package cn.alimama.controller;

import cn.alimama.pojo.Role;
import cn.alimama.pojo.User;
import cn.alimama.service.role.RoleService;
import cn.alimama.service.user.UserService;
import cn.alimama.tools.Constants;
import cn.alimama.tools.PageSupport;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    Logger logger = Logger.getLogger(UserController.class);
    @RequestMapping("/dologin.html")
    public String dologin(@RequestParam String userCode, @RequestParam String userPassword, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        logger.info("dologin==============================");
        //将接收的数据封装在User对象内
        User user = new User();
        user.setUserCode(userCode);
        //调用UserService的login方法得到返回结果
        user = userService.login(user);
        //判断
        if (user == null){
            logger.info("用户名输入错误");
            request.setAttribute("error","用户名输入错误！");
            return "login";
        }else{
            logger.info("用户名输入正确");
            if (user.getUserPassword().equals(userPassword)){
                logger.info("密码输入正确");
                session.setAttribute(Constants.USER_SESSION,user);
                return "redirect:/user/main.html";
            }else{
                logger.info("密码输入错误");
                request.setAttribute("error","用户密码输入错误！");
                return "login";
            }
        }
    }

    @RequestMapping("/main.html")
    public String main(HttpSession session){
        if(session.getAttribute(Constants.USER_SESSION) ==null){
            return "login";
        }
        return "frame";
    }

    @RequestMapping("/userlist.html")
    public String userlist(Model model,@RequestParam(value = "queryUsername",required = false)String queryUsername,@RequestParam(value = "queryUserRole",required = false)String queryUserRole,@RequestParam(value = "pageIndex",required = false)String pageIndex){
        logger.info("queryUsername搜索框姓名===========>"+queryUsername);
        logger.info("queryUserRole角色ID===========>"+queryUserRole);
        logger.info("pageIndex跳转的页码===========>"+pageIndex);
        int _queryUserRole = 0;
        List<User> userList = null;
        //设置当前页
        int currentPageNo = 1;
        //设置页容量
        int pageSize = Constants.pageSize;
        if (queryUsername==null){
            queryUsername = "";
        }
        if (queryUserRole!=null&&!queryUserRole.equals("")){
            _queryUserRole = Integer.parseInt(queryUserRole);
        }
        if (pageIndex!=null){
            currentPageNo = Integer.valueOf(pageIndex);
        }
        //查询1:查询行数
        int totalCount = userService.getUserCount(queryUsername,_queryUserRole);
        logger.info("count================>"+totalCount);
        //计算总页数
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);
        int totalPageCount = pageSupport.getTotalPageCount();
        //控制首页和尾页
        if(currentPageNo<1){
            currentPageNo = 1;
        }else if(currentPageNo>totalPageCount){
            currentPageNo = totalPageCount;
        }
        //调用查询方法
        userList = userService.getUserList(queryUsername,_queryUserRole,currentPageNo,pageSize);
        logger.info("userList.size()================>"+userList.size());
        //将集合保存在model对象内
        model.addAttribute("userList",userList);
        //准备角色的集合
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        model.addAttribute("roleList",roleList);
        model.addAttribute("queryUsername",queryUsername);//用户名回显
        model.addAttribute("queryUserRole",queryUserRole);//角色id回显
        model.addAttribute("totalPageCount",totalPageCount);//总页数回显
        model.addAttribute("totalCount",totalCount);//总行数回显
        model.addAttribute("currentPageNo",currentPageNo);//当前页回显
        return "userlist";
    }
}
