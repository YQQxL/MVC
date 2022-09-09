package cn.alimama.dao.user;

import cn.alimama.pojo.User;

import java.util.List;

public interface UserMapper {
    public User getLoginUser(User user) throws Exception;

    public int getUserCount(String queryUsername, int queryUserRole) throws Exception;

    public List<User> getUserList(String queryUsername, int queryUserRole, int currentPageNo, int pageSize) throws Exception;
}
