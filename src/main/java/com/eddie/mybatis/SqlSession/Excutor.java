package com.eddie.mybatis.SqlSession;

public interface Excutor {

    <T> T query(String statement, Object parameter, Object resultType);

}