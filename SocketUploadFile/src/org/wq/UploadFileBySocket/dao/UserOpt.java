package org.wq.UploadFileBySocket.dao;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.Connection;
import org.wq.UploadFileBySocket.pojo.User;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class UserOpt {
    private static String MYSQLURL ="jdbc:mysql://localhost:3306/fileupload";
    private static String USER = "root";
    private static String PASS = "123123";
    private Connection connection;


    public UserOpt(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(MYSQLURL,USER,PASS);
            System.out.println("数据连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }
    }

    //返回状态: 0成功 -1服务器错误 1已存在该用户
    public String register(User user){
        String sqlStr = "insert into user(user,pass) values(?,?)";
        HashMap<String,String> resMap = new HashMap();
        resMap.put("type","user");
        resMap.put("method","register");
        if(existsUser(user)){
            resMap.put("status","1");
            resMap.put("msg","已存在该用户");
            return JSON.toJSONString(resMap);
        }
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStr);
            pstmt.setString(1,user.getUser());
            pstmt.setString(2,user.getPass());
            pstmt.execute();
            System.out.println("用户注册成功");
            resMap.put("status","0");
            resMap.put("msg","用户注册成功");
        } catch (Exception e) {
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("status", "-1");
            errorMap.put("msg", "服务器错误");
            return JSON.toJSONString(errorMap);
        }
        return JSON.toJSONString(resMap);
    }

    //返回状态 10成功 11账户不存在 12密码错误 -1服务器错误
    public String login(User user){
        String sqlStr = "select pass from user where user =?";
        HashMap<String,String> resMap = new HashMap<>();
        resMap.put("type","user");
        resMap.put("method","login");
        if(!existsUser(user)){
            resMap.put("status","11");
            resMap.put("msg","该用户不存在");
            return  JSON.toJSONString(resMap);
        }
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStr);
            pstmt.setString(1,user.getUser());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String pass = rs.getString("pass");
                if(pass.equals(user.getPass())){
                    resMap.put("status","10");
                    resMap.put("msg","验证成功");
                }else {
                    resMap.put("status","12");
                    resMap.put("msg","密码错误");
                }
            }
        } catch (Exception e) {
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("status", "-1");
            errorMap.put("msg", "服务器错误");
            return JSON.toJSONString(errorMap);
        }
        return JSON.toJSONString(resMap);
    }

    public boolean existsUser(User user){
        boolean res = false;
        String sqlStr = "select user from user where user = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStr);
            pstmt.setString(1,user.getUser());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String userRes = rs.getString("user");
                if (userRes.equals(user.getUser())){
                 res = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }

    public static void main(String[] args) {
        User u = new User();
        u.setUser("admin");
        u.setPass("123123");
        System.out.println(new UserOpt().login(u));

    }
}
