package cn.alimama.dao.role;

import cn.alimama.pojo.Role;
import cn.alimama.pojo.User;

import java.util.List;

public interface RoleMapper {
    public List<Role> getRoleList() throws Exception;
}
