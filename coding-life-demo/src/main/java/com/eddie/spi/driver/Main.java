package com.eddie.spi.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * since 2020/5/23
 *
 * @author eddie
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://47.107.225.84:3306/home-manager-db?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=GMT%2B8";
        String user = "root";
        String password = "Qwer7410";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
    }
}
