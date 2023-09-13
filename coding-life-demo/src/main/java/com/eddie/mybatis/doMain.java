package com.eddie.mybatis;


import com.eddie.mybatis.test.dao.UserMapper;
import com.eddie.mybatis.test.User;
import com.eddie.mybatis.session.SqlSession;

public class doMain {

    public static void main(String[] args) {
        SqlSession session = new SqlSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.getUserById("1");
        System.out.println(user);
    }
}
