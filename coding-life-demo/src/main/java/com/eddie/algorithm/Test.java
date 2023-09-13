package com.eddie.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eddie.lys
 * @since 2023/5/30
 */
public class Test {
    public static void main(String[] args) {
        int target = 32;
        int[] array = {20, 10, 1, 8, 6};

        List<Integer> result = findMinimumNumbers(array, target);

        if (result.isEmpty()) {
            System.out.println("无法找到满足条件的数字组合");
        } else {
            System.out.println("最少的数字组合为: " + result);
        }
    }

    /**
     * 查找满足target的数组
     */
    public static List<Integer> findMinimumNumbers(int[] array, int target) {
        return generateCombinations(array, target, 0, new ArrayList<>(), new ArrayList<>(), Integer.MAX_VALUE);
    }

    private static List<Integer> generateCombinations(int[] array, int target, int index,
                                                      List<Integer> currentCombination,
                                                      List<Integer> minimumCombination, int minimumSize) {
        int sum = currentCombination.stream().mapToInt(Integer::intValue).sum();

        if (sum == target) {
            if (currentCombination.size() < minimumSize) {
                minimumCombination.clear();
                minimumCombination.addAll(currentCombination);
            }
            return minimumCombination;
        }

        if (sum > target || currentCombination.size() >= minimumSize || index >= array.length) {
            return minimumCombination;
        }

        minimumCombination = generateCombinations(array, target, index + 1, currentCombination,
                minimumCombination, minimumSize);

        currentCombination.add(array[index]);
        minimumCombination = generateCombinations(array, target, index, currentCombination,
                minimumCombination, minimumSize);
        currentCombination.remove(currentCombination.size() - 1);

        return minimumCombination;
    }
}
