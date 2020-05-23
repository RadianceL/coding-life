package com.eddie.spi.driver;

import com.mysql.cj.jdbc.NonRegisteringDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 自定义驱动
 * since 2020/5/23
 *
 * hor eddie
 */
public class CustomDriver extends NonRegisteringDriver implements Driver {
    static {
        try {
            java.sql.DriverManager.registerDriver(new CustomDriver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }
    public CustomDriver()throws SQLException {}

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        System.out.println("准备创建数据库连接.url:"+url);
        System.out.println("JDBC配置信息："+info);
        Connection connection =  super.connect(url, info);
        System.out.println("数据库连接创建完成!"+connection.toString());
        return connection;
    }
}