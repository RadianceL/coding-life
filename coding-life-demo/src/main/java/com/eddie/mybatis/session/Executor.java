package com.eddie.mybatis.session;

public interface Executor {
    /**
     * 执行sql查询
     * @param statement     sql
     * @param parameter     请求参数
     * @param resultType    返回对象
     * @param <T>           返回类型
     * @return              结果集
     */
    <T> T query(String statement, Object parameter, Object resultType);

}