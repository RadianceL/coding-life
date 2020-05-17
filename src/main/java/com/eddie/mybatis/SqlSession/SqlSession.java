package com.eddie.mybatis.SqlSession;

import java.lang.reflect.Proxy;

/**
 * @author eddie
 */
public class SqlSession {

    private Excutor excutor = new QueryExcutor();

    private Configuration myConfiguration = new Configuration();

    public <T> T selectOne(String statement, Object parameter, Object resultType) {
        System.out.println("第三步：开始代理查询");
        return excutor.query(statement, parameter, resultType);
    }

    public <T> T getMapper(Class<T> cls) {
        System.out.println("第一步：代理要映射的对象");
        //动态代理调用
        return (T) Proxy.newProxyInstance(
        		cls.getClassLoader(),
				new Class[]{cls},
                new MapperProxy(myConfiguration, this));
    }
}
