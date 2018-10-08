package com.el.Thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		Callable<Integer> callable = new Task();
		FutureTask task = new FutureTask(callable);

		Thread oneThread = new Thread(task);
		oneThread.start();

		System.out.println(">>>  工作结果 " + task.get().toString());
	}

}

class Task implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		System.out.println(">>>  线程开始工作");
		Thread.sleep(1000);
		System.out.println(">>>  结束工作开始返回");
		return 10;
	}

}