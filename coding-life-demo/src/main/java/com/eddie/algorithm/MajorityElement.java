package com.eddie.algorithm;

/**
 * 取一个数组中出现数量超过一半的数
 */
public class MajorityElement {
    public static int findMajorityElement(int[] nums) {
        int count = 0;
        int candidate = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            if (num == candidate) {
                count += 1;
            } else {
                count -= 1;
            }
        }

        return candidate;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 2, 2, 2, 5, 4, 2};
        int result = findMajorityElement(nums);
        System.out.println(result);  // Êä³ö£º2
    }
}