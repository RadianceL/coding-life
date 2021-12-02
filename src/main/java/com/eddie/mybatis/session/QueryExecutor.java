package com.eddie.mybatis.session;

import com.eddie.mybatis.test.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryExecutor implements Executor {

    private final Configuration xmlConfiguration = new Configuration();

    @Override
    @SuppressWarnings("unchecked")
    public <T> T query(String statement, Object parameter, Object resultType) {
        System.out.println("获得一个链接");
        Connection connection = getConnection();
        ResultSet set = null;
        PreparedStatement pre = null;
        try {
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
            throw new RuntimeException(e);
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
                if (pre != null) {
                    pre.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Throwable e2) {
                e2.printStackTrace();
            }
        }
    }

    private Connection getConnection() {
        System.out.println("从config.xml中获取参数");
        return xmlConfiguration.build("config.xml");
    }
}
