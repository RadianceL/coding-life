package com.eddie.structure;

import java.util.Arrays;

public final class Array<E> {

    private Object[] data;
    private int size;

    private final float limit;

    public Array() {
        this(10, 0.55f);
    }

    public Array(int capacity) {
        this(capacity, 0.55f);
    }

    public Array(int capacity, float limit) {
        data = new Object[capacity];
        this.size = 0;
        this.limit = limit;
    }

    /**
     * O(1)
     */
    public int getSize() {
        return size;
    }

    /**
     * O(1)
     */
    public int getCapacity() {
        return data.length;
    }

    /**
     * O(1)
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * O(1)
     */
    public void put(E e) {
        put(size, e);
    }

    /**
     * O(n)
     */
    public void addFirst(E e) {
        put(0, e);
    }

    /**
     * O(1)
     */
    @SuppressWarnings("unchecked")
    public E getLast() {
        return (E) data[size - 1];
    }

    /**
     * O(1)
     */
    @SuppressWarnings("unchecked")
    public E get(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IllegalArgumentException("out of array, pos = " + pos + ", size= " + size);
        }

        return (E)data[pos];
    }

    /**
     * O(1)
     */
    public synchronized void set(int pos, E e) {
        if (pos < 0 || pos >= size) {
            throw new IllegalArgumentException("set out of array, pos = " + pos + ", size= " + size);
        }
        data[pos] = e;
    }

    /**
     * O(n)
     */
    private synchronized void put(int pos, E e) {
        if (pos >= data.length) {
            resize(data.length * 2);
        }
        if (size == data.length || pos < 0) {
            throw new IllegalArgumentException("add failed, array is already full pos = " + pos + " ,Size = " + size + " ,Capacity = " + data.length);
        }
        if (size - pos >= 0) System.arraycopy(data, pos, data, pos + 1, size - pos);
        data[pos] = e;
        size++;
    }
    public synchronized E deleteLast() {
        return delete(size - 1);
    }

    /**
     * O(n)
     */
    @SuppressWarnings("unchecked")
    public synchronized E delete(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IllegalArgumentException("delete failed, pos = " + pos + " ,Size = " + size + " ,Capacity = " + data.length);
        }

        Object e = data[pos];
        if (pos != size - 1) {
            if (size - 1 - pos >= 0) {
                System.arraycopy(data, pos + 1, data, pos, size - pos - 1);
            }
        }
        size--;
        data[size] = null;
        if (size < data.length * limit) {
            resize(data.length / 2);
        }
        return (E) e;
    }

    /**
     * O(n)
     */
    private synchronized void resize(int capacity) {
        if (capacity < 5) {
            return;
        }
        Object[] newData = (data.getClass() == Object[].class)
                ?  new Object[capacity]
                : (Object[]) java.lang.reflect.Array.newInstance(data.getClass().getComponentType(), capacity);
        if (size >= 0) {
            System.arraycopy(data, 0, newData, 0, Math.min(data.length, capacity));
        }
        data = newData;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d , capacity = %d\n", size, data.length));
        res.append('[');
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1) {
                res.append(", ");
            }
        }
        res.append(']');
        System.out.println("内存中的数据{" + Arrays.toString(data) + "}");
        return res.toString();
    }

    public static void main(String[] args) {
        Array<Integer> array = new Array<>();
        for (int i = 0; i <= 20; i++) {
            array.put(i);
        }

        for (int i = 0; i < 15; i++) {
            array.delete(0);
            System.out.println(array + "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
    }
}
