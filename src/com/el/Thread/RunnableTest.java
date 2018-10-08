package com.el.Thread;

public class RunnableTest {

	public static void main(String[] args) {
		MyRunnable runnable = new MyRunnable();
		Thread thread = new Thread(runnable);
		thread.start();
	}

}

class MyRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println("test");
	}

}
