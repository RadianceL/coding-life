package com.eddie.thread;

import java.util.concurrent.Executor;

/**
 * @author eddie.lys
 * @since 2023/8/10
 */
public class ThreadRunCurrent {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getId());
        Executor e = command -> new Thread(command).start();

        e.execute(() -> System.out.println(Thread.currentThread().getId()));
    }
}
