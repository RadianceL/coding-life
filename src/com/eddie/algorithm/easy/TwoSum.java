package com.eddie.algorithm.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author eddie
 */
public class TwoSum {

    /**
     * O(n^2)
     *
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> map = new HashMap<>(numbers.length);
        for (int i = 0; i < numbers.length; i++) {
//			for (int y = i; y < nums.length ;y++) {
//				if (nums[i] + nums[y] == target && i != y) {
//					return new int[]{i, y};
//				}
//			}
            int key = target - numbers[i];
            if (map.containsKey(key)) {
                return new int[]{map.get(key), i};
            }
            map.put(numbers[i], i);
        }
        throw new IllegalArgumentException("no result");
    }

    public static void main(String[] args) {
        TwoSum sum = new TwoSum();
        int[] numbers = new int[]{3, 2, 4, 66};
        int[] result = sum.twoSum(numbers, 7);
        System.out.println("result:" + Arrays.toString(result));
    }
}
