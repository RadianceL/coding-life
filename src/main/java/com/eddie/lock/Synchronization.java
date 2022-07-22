package com.eddie.lock;

/**
 *
 * since 7/21/22
 *
 * @author eddie
 */
public class Synchronization {

    /**
     * synchronized关键字
     * 首先他是一个java的关键字，不同于Lock、AQS等线程安全方式，不需要我们关注过程，JVM负责加锁释放的操作
     * 在java 1.6之前，synchronized关键字性能不如ReentrantLock，或者说高并发下，synchronized的性能不稳定
     * 在java 1.8之后，synchronized关键字性能基本持平ReentrantLock，synchronized锁在刚加上时，是无锁状态
     * 加锁过程
     * code 1：判断对象是否是无锁状态（低三位 = 001），如果是，执行code 2，如果不是，执行code 4
     * code 2：在栈中建立一个Lock Record，将无锁状态的Mark Word拷贝到锁记录的Displaced Mark Word中，将owner指向当前对象。
     * code 3：尝试通过CAS 将锁对象的 Mark Word 更新为指向Lock Record的指针，如果更新成功，该线程获取到轻量级锁，并且需要把对象头的Mark Word的低两位改成10（注意这里修改的是对象头的Mark Word，Lock Record中记录的还是无锁状态的Mark Word）；如果更新失败，执行code 4。
     * code 4：对象是轻量级锁定状态，判断对象头的 Mark Word是否指向当前线程的栈帧。如果是，则这次为锁重入，将刚刚建立的Lock Record中的Displaced Mark Word设置为null，记录重入，该线程重入轻量级锁。如果不是，执行code 5。
     * code 5：线程获取轻量级锁失败，锁膨胀为重量级锁，对象头的Mark Word改为指向重量级锁monitor的指针。获取失败的线程不会立即阻塞，先适应性自旋，尝试获取锁。到达临界值后，阻塞该线程，直到被唤醒
     */
    private synchronized void sync() {

    }
}
