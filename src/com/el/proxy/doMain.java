package com.el.proxy;

public class doMain {

	public static void main(String[] args) {

		Subject subjectImpl = System.out::println;

		InvokeObjectHandler invokeObjectHandler = new InvokeObjectHandler(subjectImpl);

		Subject subject = (Subject) invokeObjectHandler.getProxyObject();

		subject.sayHello("test");
	}

}
