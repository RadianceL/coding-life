package com.eddie.structure;

import com.eddie.structure.function.TLinkedList;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author eddie
 */
public final class ElinkedList<E> implements
        Cloneable, Serializable, TLinkedList<E> {

    /**
     * 序列化版本信息
     */
    private static final long serialVersionUID = 1L;

    /**
     * 初始化大小
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 借鉴hashMap 获得大于等于#{cap}的最小2的幂
     */
    static int tableSizeFor(int cap) {
        //没有这步操作，则如果#{cap} 已经满足条件，结果将是#{cap}*2n 或 n的右移 1 位  如入参为10， -1 为 9，
        int n = cap - 1;
        //0000 1001 或 0000 0100 结果为 0000 1101
        n |= n >>> 1;
        //0000 1101 或 0000 0011 结果为 0000 1111
        n |= n >>> 2;
        //0000 1111 或 0000 0000 结果为 0000 1111
        n |= n >>> 4;
        //0000 1111 或 0000 0000 结果为 0000 1111
        n |= n >>> 8;
        //0000 1111 或 0000 0000 结果为 0000 1111
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /**
     * 存储单元
     */
    private transient ElementNode<E> node;


    /**
     * 实际数据
     */
    private transient int size;

    public ElinkedList() {
        node = new ElementNode<>((E) new Object(), null);
        size = 0;
    }

    @Override
    public E put(E e) {
        put(size, e);
        return e;
    }

    public void add(int index, E e) {

        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index.");
        }

        ElementNode<E> prev = node;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        prev.next = new ElementNode<>(e, prev.next);
        size++;
    }

    @Override
    public E put(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("非法参数[pos], pos < 0 || pos >" + size);
        }

        ElementNode<E> prev = node;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        prev.next = new ElementNode<>(e, prev.next);
        size++;
        return e;
    }

    @Override
    public void putIfAbsent(E e) {
        if (get(e) == -1) {
            put(size, e);
        }
    }

    @Override
    public E putFirstIndex(E e) {
        node = new ElementNode<>(e, node);
        size++;
        return e;
    }

    @Override
    public boolean contains(E e) {
        return get(e) != -1;
    }

    @Override
    public int get(E e) {
        ElementNode<E> prev = node.next;
        int local = 0;
        while (prev != null) {
            if (prev.getValue().equals(e)) {
                return local;
            }
            local++;
            prev = prev.next;
        }
        return -1;
    }

    @Override
    public E get(int index) {
        ElementNode<E> prev = node.next;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        return prev.getValue();
    }

    @Override
    public E delete(E e) {
        ElementNode<E> prev = node.next;
        while (prev != null) {
            if (prev.next.getValue().equals(e)) {
                E result = prev.next.getValue();
                prev.next = prev.next.next;
                size--;
                return result;
            }
            prev = prev.next;
        }
        return null;
    }

    @Override
    public E delete(int pos) {
        ElementNode<E> head = node;
        for (int i = 0; i < pos; i++) {
            head = head.next;
        }

        ElementNode<E> oldValue = head;
        head.next = head.next.next;
        oldValue.next = null;

        size--;
        return oldValue.getValue();
    }

    @Override
    public void clear() {
        node.next = null;
    }

    @Override
    public void forEach(Action<E> e) {
        ElementNode<E> head = node.next;
        while (head != null) {
            e.execute(head);
            head = head.next;
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ELinkedList{node=[");
        ElementNode<E> ele = node.next;
        while (ele != null) {
            E value = ele.getValue();
            result.append(value).append("->");
            ele = ele.next;

        }
        result.append("NULL], size=")
                .append(size).append("}");

        return result.toString();
    }

    /**
     * 存储节点 链表
     *
     * @param <E>
     */
    public static class ElementNode<E> implements NodeList<E> {
        final int hash;
        ElementNode<E> previous;
        E element;
        ElementNode<E> next;

        public ElementNode(int hash, ElementNode<E> previous, E element, ElementNode<E> next) {
            this.hash = hash;
            this.previous = previous;
            this.element = element;
            this.next = next;
        }

        public ElementNode(E element, ElementNode<E> next) {
            this.hash = element.hashCode();
            this.element = element;
            this.next = next;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(element);
        }

        @Override
        public E getValue() {
            return element;
        }

        @Override
        public E setValue(E e) {
            E oldVal = element;
            this.element = e;
            return oldVal;
        }

        public ElementNode<E> getPrevious() {
            return previous;
        }

        public ElementNode<E> getNext() {
            return next;
        }

        @Override
        public String toString() {
            return "{" +
                    "hash=" + hash +
                    ", element=" + element +
                    "},";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj instanceof ElementNode) {
                ElementNode<E> element = (ElementNode<E>) obj;
                return this.getValue() == element.getValue();
            }

            return false;
        }
    }

    @FunctionalInterface
    public interface Action<E> {
        /**
         * 执行lambda方法
         *
         * @param e
         */
        void execute(ElementNode<E> e);
    }

    /**
     * 改写序列化方法
     *
     * @param s
     * @throws Exception
     */
    private void writeObject(java.io.ObjectOutputStream s) throws Exception {
        s.defaultWriteObject();
        s.writeInt(size);
    }

    /**
     * 改写序列化方法
     *
     * @param s
     * @throws Exception
     */
    private void readObject(java.io.ObjectInputStream s) throws Exception {
        s.defaultReadObject();
    }

}
