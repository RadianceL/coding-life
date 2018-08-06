package com.company.LeetCode.Easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

	//O(n^2)
	public int[] twoSum(int[] nums, int target) {
		Map<Integer,Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
//			for (int y = i; y < nums.length ;y++) {
//				if (nums[i] + nums[y] == target && i != y) {
//					return new int[]{i, y};
//				}
//			}
			int key = target - nums[i];
			if (map.containsKey(key)){
				return new int[]{map.get(key), i};
			}
			map.put(nums[i], i);
		}
		throw new IllegalArgumentException("no result");
	}

	public static void main(String[] args){
		TwoSum sum = new TwoSum();
		int[] nums = new int[]{3,2,4,66};
		int[] ints = sum.twoSum(nums, 7);
		System.out.println("result:" + Arrays.toString(ints));
	}
}
