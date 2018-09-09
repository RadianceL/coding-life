package com.company.DataStructure;

import com.company.DataStructure.Interface.TStack;

//FIFO
public final class EStack<E> implements TStack<E> {

	private Array<E> stack;

	public EStack(int capacity) {
		stack = new Array<>(capacity, 0.4f);
	}

	public EStack() {
		stack = new Array<>();
	}

	@Override
	public int getSize() {
		return stack.getSize();
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	//O(1) 均摊复杂度
	@Override
	public void push(E e) {
		stack.put(e);
	}

	//O(1)
	@Override
	public E pop() {
		return stack.deleteLast();
	}

	//O(1)
	@Override
	public E peek() {
		return stack.getLast();
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("Stack: ");
		res.append('[');
		for (int i = 0; i < stack.getSize(); i++) {
			res.append(stack.get(i));
			if (i != stack.getSize() - 1)
				res.append(", ");
		}
		res.append("] top");
		return res.toString();
	}


	public static void main(String[] args) {
		EStack<String> stack = new EStack<>();
		for (int i = 1; i <= 10; i++)
			stack.push("测试数据 :" + i);

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		//输出
		System.out.println(stack);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("弹出栈顶数据 ： " + stack.pop());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(stack);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		stack.push("ttttt");
		//查看栈顶
		System.out.println("查看栈顶数据 ：" + stack.peek());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		//弹栈
		System.out.println("弹出栈顶数据 ： " + stack.pop());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		//压栈（压入size所在的位置）
		stack.push("shshshs");
		System.out.println("弹出栈顶数据 ： " + stack.pop());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("弹出栈顶数据 ： " + stack.pop());
		System.out.println("弹出栈顶数据 ： " + stack.pop());
		System.out.println("弹出栈顶数据 ： " + stack.pop());
		System.out.println("弹出栈顶数据 ： " + stack.pop());
		System.out.println("弹出栈顶数据 ： " + stack.pop());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(stack);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
}
