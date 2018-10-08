package com.el.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InvokeObjectHandler implements InvocationHandler {
	//这个就是我们要代理的真实对象
	private Object subject;

	//构造方法，给我们要代理的真实对象赋初值
	public InvokeObjectHandler(Object subject) {
		this.subject = subject;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("即将被代理的方法");
		method.invoke(subject, args);
		return null;
	}

	public Object getProxyObject() {
		return Proxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), this);
	}
}
