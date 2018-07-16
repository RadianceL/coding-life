package com.company.Proxy;

import java.lang.reflect.Proxy;

public class doMain {

	public static void main(String[] args){

		Subject subjectImpl = str -> System.out.println("Hello:" + str);

		Invoke invoke = new Invoke(subjectImpl);

		Subject subject = (Subject) Proxy.newProxyInstance(subjectImpl.getClass().getClassLoader(), subjectImpl.getClass().getInterfaces(), invoke);

		subject.sayHello("test");
	}

}
