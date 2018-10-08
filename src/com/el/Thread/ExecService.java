package com.el.Thread;

import java.util.Map;
import java.util.concurrent.*;

public class ExecService {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		runCallable();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		runTest();
	}

	public static void runTest() throws InterruptedException, ExecutionException {
		int poolSize = 2;
		long start = System.currentTimeMillis();
		ExecutorService crunchifyExServer = Executors.newFixedThreadPool(poolSize);

		Callable<String> callableO = () -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return ">>> 工作A";
		};
		// FutureTask 因为FutureTask实现了Runnable，和Future接口，
		// 所以即可以通过Runnable接口实现线程，也可以通过Future取得线程执行完后的结果
		FutureTask<String> ft1 = new FutureTask<>(callableO);
		new Thread(ft1).start();

		Callable<String> callableT = () -> {
			try {
				Thread.sleep(1000 * 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return ">>> 工作B";
		};
		FutureTask<String> ft2 = new FutureTask<>(callableT);
		new Thread(ft2).start();

		System.out.println(ft1.get());
		System.out.println(ft2.get());


		Future submitO = crunchifyExServer.submit(callableO);
		Future submitT = crunchifyExServer.submit(callableT);
		//并未真正关闭，但任务不允许提交了
		crunchifyExServer.shutdown();
		//等待所有线程执行完毕
		crunchifyExServer.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

		System.out.println(submitO.get());
		System.out.println(submitT.get());

		long end = System.currentTimeMillis();
		System.out.println(">>> 用时：" + (end - start));
	}

	public static void runCallable() throws InterruptedException, ExecutionException {
		System.out.println("----ExecutorService程序开始运行----");
		int poolSize = 10;
		long startTime = System.currentTimeMillis();
		ExecutorService crunchifyExServer = Executors.newFixedThreadPool(poolSize);
		// 创建多个有返回值的任务
		Map<String, Future> hashMap = new ConcurrentHashMap<>();
		for (int i = 1; i <= poolSize; i++) {
			Callable c = new MyCallable(i + " ");
			// 执行任务并获取Future对象
			Future future = crunchifyExServer.submit(c);
			//System.out.println(">>>" + future.get().toString()); 放在此时会等待任务执行完毕返回结果，否则会阻塞线程
			hashMap.put(i + "", future);
		}
		crunchifyExServer.shutdown();
		crunchifyExServer.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

		for (String set : hashMap.keySet()) {
			System.out.println(">>> " + hashMap.get(set).get().toString());
		}

		long endTime = System.currentTimeMillis();
		System.out.println("----程序结束运行----，程序运行时间【" + (endTime - startTime) + "毫秒】");
	}
}


class MyCallable implements Callable<Object> {

	private String taskNum;

	MyCallable(String taskNum) {
		this.taskNum = taskNum;
	}

	public Object call() throws Exception {
		System.out.println(">>>" + taskNum + "任务启动");
		long startTime = System.currentTimeMillis();
		Thread.sleep(1000);
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		System.out.println(">>>" + taskNum + "任务终止");
		return taskNum + "任务返回运行结果,当前任务时间【" + time + "毫秒】";
	}
}  