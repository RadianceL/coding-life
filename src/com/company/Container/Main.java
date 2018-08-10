package com.company.Container;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Main {

	public static void main(String[] args) {
		/**
		 * 线程安全 -- 性能依次提高
		 */
		//同一时间只能有一个线程访问该容器，效率低
		Hashtable<String, String> hashTable = new Hashtable<>();
		//Hashtable的替代版本，同一时间只能有一个线程访问该容器的方法(方法锁)
		Map<String, String> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
		//高性能版本，分段锁，线程安全
		ConcurrentMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

		/**
		 * 非线程安全
		 */
		//实现SortMap接口，默认是按键值的升序排序
		Map<String, String> treeMap = new TreeMap<>();
		//LinkedHashMap保存了记录的插入顺序，在用Iterator遍历LinkedHashMap时，先得到的记录肯定是先插入的。也可以在构造时带参数，按照应用次数排序。
		Map<String, String> linkedHashMap = new LinkedHashMap<>();
		//HashMap 是一个最常用的Map，它根据键的HashCode 值存储数据，根据键可以直接获取它的值，具有很快的访问速度。遍历时，取得数据的顺序是完全随机的。
		//HashMap最多只允许一条记录的键为Null；允许多条记录的值为 Null。
		//HashMap不支持线程的同步（即任一时刻可以有多个线程同时写HashMap），可能会导致数据的不一致。如果需要同步，可以用 Collections的synchronizedMap方法使HashMap具有同步的能力，或者使用ConcurrentHashMap。
		//HashTable与 HashMap类似，它继承自Dictionary类。不同的是：它不允许记录的键或者值为空；它支持线程的同步（即任一时刻只有一个线程能写HashTable），因此也导致了 HashTable在写入时会比较慢。
		Map<String, String> hashMap = new HashMap<>();

		/**
		 * List与Set
		 * 简要说明
		 * set --其中的值不允许重复，无序的数据结构。Set具有与Collection完全一样的接口，Set中的值必须唯一
		 * list --其中的值允许重复，因为其为有序的数据结构
		 */
		//为快速查找设计的Set。存入HashSet的对象必须定义hashCode()。
		Set<String> hashSet = new HashSet<>();
		//保存次序的Set, 底层为树结构。使用它可以从Set中提取有序的序列。
		Set<String> treeSet = new TreeSet<>();
		//LinkedHashSet : 具有HashSet的查询速度，且内部使用链表维护元素的顺序(插入的次序)。
		//于是在使用迭代器遍历Set时，结果会按元素插入的次序显示。
		Set<String> linkedHashSet = new LinkedHashSet<>();

		//由数组实现的List。允许对元素进行快速随机访问，但是向List中间插入与移除元素的速度很慢
		List<String> arrayList = new ArrayList<>();
		//对顺序访问进行了优化，向List中间插入与删除的开销并不大。随机访问则相对较慢。
		List<String> linkedList = new LinkedList<>();
	}
}
