package com.eddie.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author eddie
 * @createTime 2017-04-13
 * @description
 */
public class InvokeObjectHandler implements InvocationHandler {
    /**
     * 这个就是我们要代理的真实对象
     */
    private Object subject;

    /**
     * 构造方法，给我们要代理的真实对象赋初值
     */
    public InvokeObjectHandler(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("被代理的方法开始");
        method.invoke(subject, args);
        System.out.println("被代理的方法结束");
        return null;
    }

    public Object getProxyObject() {
        return Proxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), this);
    }
}
