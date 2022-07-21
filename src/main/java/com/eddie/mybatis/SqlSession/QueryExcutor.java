package com.eddie.mybatis.SqlSession;

import com.eddie.mybatis.Mapper.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryExcutor implements Excutor {

    private Configuration xmlConfiguration = new Configuration();

    @Override
    public <T> T query(String statement, Object parameter, Object resultType) {
        System.out.println("获得一个链接");

        ResultSet set = null;
        PreparedStatement pre = null;
        try (Connection connection = getConnection()){
            System.out.println("放入SQL语句");

            pre = connection.prepareStatement(statement);
            //设置参数
            System.out.println("设置参数");
            pre.setString(1, parameter.toString());
            set = pre.executeQuery();
            User u = new User();

            //遍历结果集
            while (set.next()) {
                System.out.println("遍历结果集");
                u.setId(set.getString(1));
                u.setUsername(set.getString(2));
                u.setPassword(set.getString(3));
            }
            return (T) u;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
                if (pre != null) {
                    pre.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    private Connection getConnection() {
        try {
            System.out.println("从config.xml中获取参数");
            return xmlConfiguration.build("config.xml");
        } catch (Exception e) {
            throw new RuntimeException("无法获取数据库链接");
        }
    }
}
