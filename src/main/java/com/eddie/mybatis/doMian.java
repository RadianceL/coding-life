package com.eddie.mybatis;


import com.eddie.mybatis.Dao.UserMapper;
import com.eddie.mybatis.Mapper.User;
import com.eddie.mybatis.SqlSession.SqlSession;

public class doMian {

    public static void main(String[] args) {
        SqlSession session = new SqlSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.getUserById("1");
        System.out.println(user);
    }
}
