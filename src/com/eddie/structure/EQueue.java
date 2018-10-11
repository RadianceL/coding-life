package com.eddie.structure;

import com.eddie.structure.function.TQueue;

/**
 * @author eddie
 * @param <E>
 */
public final class EQueue<E> implements TQueue<E> {

    private Array<E> data;

    public EQueue() {
        data = new Array<>();
    }

    public EQueue(int capacity) {
        data = new Array<>(capacity);
    }

    public EQueue(int capacity, float limit) {
        data = new Array<>(capacity, limit);
    }

    @Override
    public void put(E e) {
        data.put(e);
    }

    @Override
    public E remove() {
        return data.delete(0);
    }

    @Override
    public int count() {
        return data.getSize();
    }

    @Override
    public int getSize() {
        return data.getCapacity();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
}
