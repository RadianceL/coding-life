package com.company.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Invoke implements InvocationHandler {
	//这个就是我们要代理的真实对象
	private Object subject;

	//构造方法，给我们要代理的真实对象赋初值
	public Invoke(Object subject) {
		this.subject = subject;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("即将被代理的方法");
		method.invoke(subject, args);
		return null;
	}
}
