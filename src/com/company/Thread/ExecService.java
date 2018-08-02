package com.company.Thread;

import java.util.Map;
import java.util.concurrent.*;
import java.util.Date;

public class ExecService {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		//runMyCallable();

		long start = System.currentTimeMillis();

		Callable callableO = () -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return ">>> 工作A";
		};
		FutureTask<String> ft1 = new FutureTask<String>(callableO);
		new Thread(ft1).start();

		Callable callableT = () -> {
			try {
				Thread.sleep(1000*2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return ">>> 工作B";
		};
		FutureTask<String> ft2 = new FutureTask<>(callableT);
		new Thread(ft2).start();

		System.out.println(ft1.get());
		System.out.println(ft2.get());

		long end = System.currentTimeMillis();
		System.out.println(">>> 用时："+(end-start));

	}

	public static void runMyCallable() throws InterruptedException, ExecutionException {
		System.out.println("----ExecutorService程序开始运行----");
		long startTime = System.currentTimeMillis();
		ExecutorService crunchifyExServer = Executors.newFixedThreadPool(10);
		// 创建多个有返回值的任务
		Map<String,Future> hashMap = new ConcurrentHashMap<>();
		for (int i = 1; i <= 10; i++) {
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