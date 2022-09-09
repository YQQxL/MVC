package cn.alimama.service.user;

import cn.alimama.pojo.User;

import java.util.List;

public interface UserService {
    public User login(User user);

    public int getUserCount(String queryUsername, int queryUserRole);

    public List<User> getUserList(String queryUsername, int queryUserRole, int currentPageNo, int pageSize);
}
