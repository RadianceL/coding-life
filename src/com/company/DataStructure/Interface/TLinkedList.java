package com.company.DataStructure.Interface;

import com.company.DataStructure.ELinkedList.Action;

public interface TLinkedList<E> {

	E put(E e);

	E put(int pos, E e);

	void putIfAbsent(E e);

	E putAll(TLinkedList<? extends E> linkedList);

	E putFirstIndex(E e);

	boolean contains(E e);

	int get(E e);

	E get(int pos);

	E delete(E e);

	E delete(int pos);

	void clear();

	void forEach(Action e);

	boolean isEmpty();

	//获得目前有多少数据
	int getSize();

	interface NodeList<E>{
		 E getValue();
		 E setValue(E e);
	}
}
