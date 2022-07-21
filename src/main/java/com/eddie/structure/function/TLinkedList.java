package com.eddie.structure.function;

import com.eddie.structure.ElinkedList.Action;

/**
 * @author eddie
 */
public interface TLinkedList<E> {

    /**
     * 存入元素到最后
     *
     * @param e
     * @return
     */
    E put(E e);

    /**
     * 存入元素到制定位置
     *
     * @param pos
     * @param e
     * @return
     */
    E put(int pos, E e);

    /**
     * 存放一个元素（之前不存在的元素）
     *
     * @param e
     */
    void putIfAbsent(E e);

    /**
     * 放入队列第一个位置
     *
     * @param e
     * @return
     */
    E putFirstIndex(E e);

    /**
     * 是否包含元素
     *
     * @param e
     * @return
     */
    boolean contains(E e);

    /**
     * 获取元素位置
     *
     * @param e
     * @return
     */
    int get(E e);

    /**
     * 获取指定位置元素
     *
     * @param pos
     * @return
     */
    E get(int pos);

    /**
     * 删除指定元素
     *
     * @param e
     * @return
     */
    E delete(E e);

    /**
     * 删除指定位置元素
     *
     * @param pos
     * @return
     */
    E delete(int pos);

    /**
     * 清空所有元素
     */
    void clear();

    /**
     * lambda表达式遍历
     *
     * @param e
     */
    void forEach(Action<E> e);

    /**
     * 是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 获取实际元素个数
     *
     * @return
     */
    default int getSize() {
        return 0;
    }

    interface NodeList<E> {
        /**
         * 获取元素
         *
         * @return
         */
        E getValue();

        /**
         * 存入元素
         *
         * @param e
         * @return
         */
        default E setValue(E e) {
            return null;
        }
    }
}
