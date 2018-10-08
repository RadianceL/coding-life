package com.el.Thread;

public class ThreadTest {

	public static void main(String[] args) {
		MyThread thread = new MyThread();
		thread.start();
	}

}

class MyThread extends Thread {
	@Override
	public void run() {
		System.out.println("test");
	}
}
