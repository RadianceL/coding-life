package com.eddie.mybatis.session;

import com.eddie.mybatis.data.ExecuteMethod;
import com.eddie.mybatis.data.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Set;

public class MapperProxy implements InvocationHandler {

    private final Configuration myConfiguration;

    private final SqlSession sqlSession;

    public MapperProxy(Configuration myConfiguration, SqlSession sqlSession) {
        this.myConfiguration = myConfiguration;
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        System.out.println("第二步：读取Mapper配置文件");
        MapperBean readMapper = myConfiguration.readMapper("UserMapper.xml");
        if (!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())) {
            throw new RuntimeException("查询数据异常：未找到对应的配置文件");
        }
        Set<ExecuteMethod> list = readMapper.getList();
        if (null != list && 0 != list.size()) {
            for (ExecuteMethod function : list) {
                //id是否和接口方法名一样
                System.out.println("获取方法名添加到list：" + function.getFuncName());
                if (method.getName().equals(function.getFuncName())) {
                    System.out.println("获取方法参数：" + args[0]);
                    return sqlSession.selectOne(function.getSql(), String.valueOf(args[0]), function.getResultType());
                }
            }
        }
        throw new RuntimeException("查询数据异常：未找到对应的方法");
    }
}
