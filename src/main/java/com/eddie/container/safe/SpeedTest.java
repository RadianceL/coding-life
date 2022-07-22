package com.eddie.container.safe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Test started for: class java.util.Hashtable
 * 2500K entried added/retrieved in 1632 ms
 * 2500K entried added/retrieved in 1722 ms
 * 2500K entried added/retrieved in 2129 ms
 * 2500K entried added/retrieved in 2061 ms
 * 2500K entried added/retrieved in 2058 ms
 * For class java.util.Hashtable the average time is 1920 ms
 * <p>
 * Test started for: class java.util.Collections$SynchronizedMap
 * 2500K entried added/retrieved in 2677 ms
 * 2500K entried added/retrieved in 1944 ms
 * 2500K entried added/retrieved in 2019 ms
 * 2500K entried added/retrieved in 1964 ms
 * 2500K entried added/retrieved in 1972 ms
 * For class java.util.Collections$SynchronizedMap the average time is 2115 ms
 * <p>
 * Test started for: class java.util.concurrent.ConcurrentHashMap
 * 2500K entried added/retrieved in 752 ms
 * 2500K entried added/retrieved in 454 ms
 * 2500K entried added/retrieved in 1190 ms
 * 2500K entried added/retrieved in 484 ms
 * 2500K entried added/retrieved in 473 ms
 * For class java.util.concurrent.ConcurrentHashMap the average time is 670 ms
 */
public class SpeedTest {

    public final static int THREAD_POOL_SIZE = 5;

    public static Map<String, Integer> crunchifyHashTableObject = null;
    public static Map<String, Integer> crunchifySynchronizedMapObject = null;
    public static Map<String, Integer> crunchifyConcurrentHashMapObject = null;

    public static void main(String[] args) throws InterruptedException {

        // Test with Hashtable Object
        Map<String, Integer> crunchifyHashTableObject = new Hashtable<>();
        structPerformTest(crunchifyHashTableObject);

        // Test with synchronizedMap Object
        Map<String, Integer> crunchifySynchronizedMapObject = Collections.synchronizedMap(new HashMap<>());
        structPerformTest(crunchifySynchronizedMapObject);

        // Test with ConcurrentHashMap Object
        crunchifyConcurrentHashMapObject = new ConcurrentHashMap<>(5000, 0.75f);
        structPerformTest(crunchifyConcurrentHashMapObject);

    }

    public static void structPerformTest(final Map<String, Integer> struct) throws InterruptedException {
        System.out.println("Test started for: " + struct.getClass());
        long averageTime = 0;
        for (int i = 0; i < 5; i++) {

            long startTime = System.nanoTime();
            ExecutorService crunchifyExServer = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

            for (int j = 0; j < THREAD_POOL_SIZE; j++) {
                crunchifyExServer.execute(() -> {
                    for (int i1 = 0; i1 < 500000; i1++) {
                        Integer crunchifyRandomNumber = (int) Math.ceil(Math.random() * 550000);
                        // Retrieve value. We are not using it anywhere
                        Integer crunchifyValue = struct.get(String.valueOf(crunchifyRandomNumber));
                        // Put value
                        struct.put(String.valueOf(crunchifyRandomNumber), crunchifyRandomNumber);
                    }
                });
            }
            // 关闭任务提交
            crunchifyExServer.shutdown();

            //从字面意思就能理解，shutdownNow()能立即停止线程池，正在跑的和正在等待的任务都停下了。这样做立即生效，但是风险也比较大；
            //shutdown()只是关闭了提交通道，用submit()是无效的；而内部该怎么跑还是怎么跑，跑完再停。
            //shutdown()后，不能再提交新的任务进去；但是awaitTermination()后，可以继续提交。
            //awaitTermination()是阻塞的，返回结果是线程池是否已停止（true/false）；shutdown()不阻塞。

            // 确保线程中任务执行完毕
            boolean result = crunchifyExServer.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

            long entTime = System.nanoTime();
            long totalTime = (entTime - startTime) / 1000000L;
            averageTime += totalTime;
            System.out.println("2500K entried added/retrieved in " + totalTime + " ms");
        }
        System.out.println("For " + struct.getClass() + " the average time is " + averageTime / 5 + " ms\n");
    }

}
