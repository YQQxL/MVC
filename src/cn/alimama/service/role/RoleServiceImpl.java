package cn.alimama.service.role;

import cn.alimama.dao.role.RoleMapper;
import cn.alimama.pojo.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{
    @Resource
    private RoleMapper roleMapper;
    @Override
    public List<Role> getRoleList() {
        try {
            return roleMapper.getRoleList();
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }
}
