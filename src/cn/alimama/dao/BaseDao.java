package cn.alimama.dao;

import cn.alimama.tools.ConfigManager;

import java.sql.*;

public class BaseDao {
    public static Connection connection = null;
    public static PreparedStatement preparedStatement = null;
    public static ResultSet resultSet = null;
    public static void getConnection(){
        //加载四大金刚====>ConfigManager
        String driver = ConfigManager.getInstance().getValue("driver");
        String url = ConfigManager.getInstance().getValue("url");
        String user = ConfigManager.getInstance().getValue("user");
        String password = ConfigManager.getInstance().getValue("password");
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url,user,password);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public static void closeConnection(){
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet executeQuery(String sql,Object [] objects) throws Exception{
        getConnection();
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0;i<objects.length;i++){
            preparedStatement.setObject((i+1),objects[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
}
