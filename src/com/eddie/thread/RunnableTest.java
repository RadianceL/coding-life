package com.eddie.thread;

public class RunnableTest {

	public static void main(String[] args) {
		RunnableTask runnable = new RunnableTask();
		Thread thread = new Thread(runnable);
		thread.start();
	}

}

class RunnableTask implements Runnable {

	@Override
	public void run() {
		System.out.println("test");
	}

}
