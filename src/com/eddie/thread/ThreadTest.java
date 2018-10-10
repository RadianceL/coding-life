package com.eddie.thread;

/**
 * @author eddie
 */
public class ThreadTest {

	public static void main(String[] args) {
        ThreadTask thread = new ThreadTask();
		thread.start();
	}

}

class ThreadTask extends Thread {

	@Override
	public void run() {
		System.out.println("test");
	}
}
