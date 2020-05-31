package com.eddie.thread;

public class RunnableTest {

    public static void main(String[] args) {
        RunnableTask runnable = new RunnableTask();
        new Thread(runnable).start();
    }

}

class RunnableTask implements Runnable {

    @Override
    public void run() {
        System.out.println("test");
    }

}
