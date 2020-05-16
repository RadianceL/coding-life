package com.eddie.container.unsafe;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

    /**
     * HashMap 内部有
     * final int hash;     V value;
     * final K key;        Node<K,V> next;
     * <p>
     * 可以看到 HashMap 内部存储使用了一个 Node 数组(默认大小是16)，相当于一个链表
     * 所有根据hash值计算的 bucket 一样的key会存储到同一个链表里（冲突）
     * 当数据庞大时会影响效率，所以HashMap有自动扩容机制
     * 计算方法为（默认情况下数组大小为16，loadFactor为0.75）
     * 当size > 16 * 0.75 时，size = 16 * 2
     * 扩容会重新计算元素的位置，目的是让每个node链表降低深度，以达到高校
     * <p>
     * 之所以HashMap线程不安全
     * 原因有二：
     * - 假设两个线程同时put的key发生碰撞，那么根据HashMap实现，两个key会被添加到同一位置，其中一个线程的数据会被覆盖
     * - 多线程同时监测到需要扩容，都在重新计算元素位置以及复制数据，但是最终只有一个线程扩容后的数组会赋给 table
     * 也就是说其他线程的都会丢失，并且各自线程 put 的数据也丢失。
     * <p>
     * 《Java并发编程艺术》中说
     * > HashMap 在并发执行 put 操作时会引起死循环，导致 CPU 利用率接近100%。因为多线程会导致 HashMap 的 Node 链表形成环形数据结构，
     * > 一旦形成环形数据结构，Node 的 next 节点永远不为空，就会在获取 Node 时产生死循环。
     * put并不会导致以上问题，死循环发生在扩容阶段
     *
     * @param args
     */
    public static void main(String[] args) {
        Map<String, String> hashMap = new HashMap<>(20, 0.70f);

        String put = hashMap.put("abc", "123");
        //如果key相同，返回oldValue
        String old = hashMap.put("abc", "456");

        System.out.println(put);
        System.out.println(old);

        System.out.println(Integer.MAX_VALUE);
    }

}
