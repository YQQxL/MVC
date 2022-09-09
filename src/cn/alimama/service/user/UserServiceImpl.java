package cn.alimama.service.user;

import cn.alimama.dao.user.UserMapper;
import cn.alimama.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        try {
            return userMapper.getLoginUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getUserCount(String queryUsername, int queryUserRole) {
        int count = 0;
        System.out.println("UserServiceImpl,getUserCount(),queryUsername====>"+queryUsername);
        System.out.println("UserServiceImpl,getUserCount(),queryUserRole====>"+queryUserRole);
        try {
            count = userMapper.getUserCount(queryUsername,queryUserRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<User> getUserList(String queryUsername, int queryUserRole, int currentPageNo, int pageSize) {
        List<User> userList = null;
        System.out.println("UserServiceImpl,getUserList(),queryUsername====>"+queryUsername);
        System.out.println("UserServiceImpl,getUserList(),queryUserRole====>"+queryUserRole);
        System.out.println("UserServiceImpl,getUserList(),currentPageNo====>"+currentPageNo);
        System.out.println("UserServiceImpl,getUserList(),pageSize====>"+pageSize);
        try {
            userList = userMapper.getUserList(queryUsername,queryUserRole,currentPageNo,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
