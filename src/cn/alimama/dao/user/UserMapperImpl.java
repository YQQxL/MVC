package cn.alimama.dao.user;

import cn.alimama.dao.BaseDao;
import cn.alimama.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserMapperImpl implements UserMapper{
    @Override
    public User getLoginUser(User user) throws Exception{
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM smbms_user WHERE userCode = ?");
        Object [] objects = {user.getUserCode()};
            BaseDao.resultSet = BaseDao.executeQuery(stringBuilder.toString(),objects);
            user = null;
            if(BaseDao.resultSet!=null){
                if(BaseDao.resultSet.next()==true){
                    user = new User();
                    user.setId(BaseDao.resultSet.getInt("id"));
                    user.setUserCode(BaseDao.resultSet.getString("userCode"));
                    user.setUserName(BaseDao.resultSet.getString("userName"));
                    user.setUserPassword(BaseDao.resultSet.getString("userPassword"));
                    user.setGender(BaseDao.resultSet.getInt("gender"));
                    user.setBirthday(BaseDao.resultSet.getDate("birthday"));
                    user.setPhone(BaseDao.resultSet.getString("phone"));
                    user.setAddress(BaseDao.resultSet.getString("address"));
                    user.setUserRole(BaseDao.resultSet.getInt("userRole"));
                    user.setCreatedBy(BaseDao.resultSet.getInt("createdBy"));
                    user.setCreationDate(BaseDao.resultSet.getTimestamp("creationDate"));
                    user.setModifyBy(BaseDao.resultSet.getInt("modifyBy"));
                    user.setModifyDate(BaseDao.resultSet.getTimestamp("modifyDate"));
                }
            }
        BaseDao.closeConnection();
        return user;
    }

    @Override
    public int getUserCount(String queryUsername, int queryUserRole) throws Exception{
        int count = 0;
        StringBuilder stringBuilder = new StringBuilder("SELECT COUNT(1) AS count from smbms_user u,smbms_role r WHERE u.userRole=r.id");
        List list = new ArrayList();
        if(!queryUsername.equals("")){
            stringBuilder.append(" AND u.userName LIKE CONCAT ('%',?,'%')");
            list.add(queryUsername);
        }
        if(queryUserRole>0){
            stringBuilder.append(" AND u.userRole = ?");
            list.add(queryUserRole);
        }
        BaseDao.resultSet = BaseDao.executeQuery(stringBuilder.toString(),list.toArray());
        if (BaseDao.resultSet.next()){
            count = BaseDao.resultSet.getInt("count");
        }
        BaseDao.closeConnection();
        return count;
    }

    @Override
    public List<User> getUserList(String queryUsername, int queryUserRole, int currentPageNo, int pageSize) throws Exception{
        List<User> userList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder("SELECT u.*,r.roleName AS userRoleName from smbms_user u,smbms_role r WHERE u.userRole=r.id");
        List list = new ArrayList();
        if(!queryUsername.equals("")){
            stringBuilder.append(" AND u.userName LIKE CONCAT ('%',?,'%')");
            list.add(queryUsername);
        }
        if(queryUserRole>0){
            stringBuilder.append(" AND u.userRole = ?");
            list.add(queryUserRole);
        }
        stringBuilder.append(" LIMIT ?,?");
        list.add((currentPageNo-1)*pageSize);
        list.add(pageSize);
        BaseDao.resultSet = BaseDao.executeQuery(stringBuilder.toString(),list.toArray());
        if (BaseDao.resultSet!=null){
            while(BaseDao.resultSet.next()){
                User _user = new User();
                _user.setId(BaseDao.resultSet.getInt("id"));
                _user.setUserCode(BaseDao.resultSet.getString("userCode"));
                _user.setUserName(BaseDao.resultSet.getString("userName"));
                _user.setGender(BaseDao.resultSet.getInt("gender"));
                _user.setBirthday(BaseDao.resultSet.getDate("birthday"));
                _user.setPhone(BaseDao.resultSet.getString("phone"));
                _user.setUserRole(BaseDao.resultSet.getInt("userRole"));
                _user.setUserRoleName(BaseDao.resultSet.getString("userRoleName"));
                userList.add(_user);
            }
        }
        BaseDao.closeConnection();

        return userList;
    }
}
