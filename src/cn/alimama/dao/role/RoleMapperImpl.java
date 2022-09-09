package cn.alimama.dao.role;

import cn.alimama.dao.BaseDao;
import cn.alimama.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class RoleMapperImpl implements RoleMapper{
    @Override
    public List<Role> getRoleList() throws Exception {
        String sql = "SELECT * FROM smbms_role";
        Object [] objects = {};
        BaseDao.resultSet = BaseDao.executeQuery(sql,objects);
        List<Role> roleList = new ArrayList<>();
        if (BaseDao.resultSet!=null){
            while(BaseDao.resultSet.next()){
                Role role = new Role();
                role.setId(BaseDao.resultSet.getInt("id"));
                role.setRoleCode(BaseDao.resultSet.getString("roleCode"));
                role.setRoleName(BaseDao.resultSet.getString("roleName"));
                roleList.add(role);
            }
        }
        BaseDao.closeConnection();
        return roleList;
    }
}
