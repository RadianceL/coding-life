package com.eddie.proxy;

/**
 * @author eddie
 */
public class MainTest {

	public static void main(String[] args) {

		Subject subjectImpl = System.out::println;

		InvokeObjectHandler invokeObjectHandler = new InvokeObjectHandler(subjectImpl);

		Subject subject = (Subject) invokeObjectHandler.getProxyObject();

		subject.sayHello("test");
	}

}
