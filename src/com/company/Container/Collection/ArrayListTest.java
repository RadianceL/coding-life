package com.company.Container.Collection;

import java.util.*;

public class ArrayListTest {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();

		//添加数据
		for (int i = 0; i < 10; i++) {
			list.add("数据:" + i);
		}

		boolean isAddSuccess = list.add("0");
		String pos = list.get(0);
		int size = list.size();
		int position = list.indexOf("");
		boolean empty = list.isEmpty();
		boolean isRemoveSuccess = list.remove("");
		String remove = list.remove(0);

		//不建议使用，如频繁使用，则选择LinkedList
		list.add(10, "添加数据");


		//是否包含某条数据
		boolean contains = list.contains("0");

		//迭代器
		Iterator<String> iterator = list.iterator();
		//for循环其实是一个封装了迭代的语法块,所以我们遍历采用迭代器
		while (iterator.hasNext()) {
			String str = iterator.next();
			System.out.println(str);
		}

		//带有操作功能的迭代器，add(),set(),remove...
		ListIterator<String> listIterator = list.listIterator();
		listIterator.previousIndex();


		//Spliterator是一个可分割迭代器(splitable iterator)
		Spliterator<String> spliterator1 = list.spliterator();

		//将要迭代的元素数量
		System.out.println("将要迭代的元素的数量" + spliterator1.estimateSize());

		//尝试分割一个List，返回靠前的数据段的迭代器，原迭代器从中间位置到最后位置。
		Spliterator<String> spliterator2 = spliterator1.trySplit();

		// 使用action消费下一个元素，指针向后移动一位
		spliterator1.tryAdvance(s -> System.out.println(s));

		System.out.println("切分迭代器遍历数据1");
		System.out.println("获取ExactSizeIfKnown" + spliterator1.getExactSizeIfKnown());
		spliterator1.forEachRemaining(str -> System.out.println(str));
		System.out.println("切分迭代器遍历数据2");
		System.out.println("获取ExactSizeIfKnown" + spliterator1.getExactSizeIfKnown());
		spliterator2.forEachRemaining(str -> System.out.println(str));


		//显示的调用这个函数，如果参数大于低层数组长度的1.5倍
		//那么这个数组的容量就会被扩容到这个参数值，如果参数小于低层数组长度的1.5倍
		//那么这个容量就会被扩容到低层数组长度的1.5倍（至少底层数组的1.5倍)
		((ArrayList<String>) list).ensureCapacity(10);

		list.clear();
	}

}
