package com.company.DataStrucet;

import java.util.Arrays;

public class Array<E> {

	private E[] data;
	private int size;

	private float limit;

	public Array(){
		this(10, 0.4f);
	}

	public Array(int capacity){
		this(capacity, 0.4f);
	}

	public Array(int capacity,float limit){
		data = (E[]) new Object[capacity];
		this.size = 0;
		this.limit = limit;
	}

	//O(1)
	public int getSize(){
		return size;
	}
	//O(1)
	public int getCapacity(){
		return data.length;
	}
	//O(1)
	public boolean isEmpty(){
		return size == 0;
	}

	//O(1)
	public void put(E e){
		put(size, e);
	}

	//O(n)
	public void addFirst(E e){
		put(0, e);
	}

	//O(1)
	public E get(int pos){
		if (pos < 0 || pos >= size)
			throw new IllegalArgumentException("out of array, pos = " + pos + ", size= " + size);

		return data[pos];
	}

	//O(1) 均摊复杂度
	public void set(int pos, E e) {
		if (pos < 0 || pos >= size)
			throw new IllegalArgumentException("set out of array, pos = " + pos + ", size= " + size);

		data[pos] = e;
	}

	//O(n)
	public void put(int pos, E e){
		if (pos >= data.length)
			reset(data.length * 2);

		if (size == data.length || pos < 0)
			throw new IllegalArgumentException("add failed, array is already full pos = " + pos + " ,Size = "  + size + " ,Capacity = " + data.length);

		for (int i = size - 1; i >= pos; i --)
			data[i + 1] = data[i];

		data[pos] = e;
		size ++;
	}

	//O(n)
	public E delete(int pos){
		if (pos < 0 || pos >= size)
			throw new IllegalArgumentException("delete failed, pos = " + pos + " ,Size = "  + size + " ,Capacity = " + data.length);

		E e =  data[pos];
		for (int i = pos ; i < size - 1; i ++){
			data[i] = data[i + 1];
		}
		size --;

		if (size < data.length * limit)
			reset(data.length/2);

		return e;
	}

	//O(n)
	private void reset(int capacity){
		E[] newData = (E[]) new Object[capacity];
		for (int i = 0; i < size ; i++ ){
			newData[i] = data[i];
		}
		data = newData;
	}

	@Override
	public String toString() {
		return "Array: size = " + size + " , capacity = " + data.length  +
				"\ndata=" + Arrays.toString(data);
	}

	public static void main(String[] args){
		Array<Integer> array = new Array<>();
		for (int i = 0; i <= 20; i ++ )
			array.put(i);

		for (int i = 0; i < 15; i ++){
			array.delete(0);
			System.out.println(array);
		}



	}
}
