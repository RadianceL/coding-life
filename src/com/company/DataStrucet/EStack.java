package com.company.DataStrucet;

//FIFO
public class EStack<E> {

	private Array<E> stack;

	private int size;

	public EStack(int capacity){
		stack = new Array<>(capacity, 0.4f);
		size = 0;
	}

	public EStack(){
		stack = new Array<>();
	}

	public void push(E e){
		stack.put(e);
		size ++;
	}

	public E pop(){
		if (size <= 0)
			throw new IllegalArgumentException("栈中无数据");
		size --;
		return stack.deleteLast();
	}

	public E peek(){
		if (size <= 0)
			throw new IllegalArgumentException("栈中无数据");

		return stack.getLast();
	}

	@Override
	public String toString() {
		return "EStack{" +
				"stack=" + stack +
				'}';
	}

	public static void main(String[] args){
		EStack<String> stringEStack = new EStack<>();
		stringEStack.push("ssss");
		stringEStack.push("yyyy");
		stringEStack.push("zzzz");

		System.out.println(stringEStack);

		System.out.println(stringEStack.peek());

		System.out.println(stringEStack.pop());

		System.out.println(stringEStack.pop());

		System.out.println(stringEStack.pop());

		stringEStack.push("shshshs");

		System.out.println(stringEStack);



	}
}
