package com.el.DataStructure.Interface;

public interface TStack<E> {

	int getSize();

	boolean isEmpty();

	void push(E e);

	E pop();

	E peek();
}
