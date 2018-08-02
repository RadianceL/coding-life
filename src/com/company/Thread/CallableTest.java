package com.company.Thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableTest {

	public static void main(String[] args){
		Callable oneCallable = new SomeWork();
		FutureTask oneTask = new FutureTask(oneCallable);
		Thread oneThread = new Thread(oneTask);
		oneThread.start();
	}

}

class SomeWork<V> implements Callable<V> {

	@Override
	public V call() throws Exception {
		System.out.println("test");
		return null;
	}

}