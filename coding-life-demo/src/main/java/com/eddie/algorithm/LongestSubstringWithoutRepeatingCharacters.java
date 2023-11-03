package com.eddie.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * @author eddie.lys
 * @since 2023/10/23
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public static void main(String[] args) {
        String s =  "abcabcbb";
        System.out.println(lengthOfLongestSubstring(s));
    }

    public static int lengthOfLongestSubstring(String s) {
        Set<Character> strings = new HashSet<>();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            strings.add(s.charAt(i));
        }
        return strings.size();
    }

}
