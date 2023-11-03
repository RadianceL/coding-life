package com.eddie.algorithm;

import java.util.Objects;

/**
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * Example 1:
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807.
 * Example 2:
 * Input: l1 = [0], l2 = [0]
 * Output: [0]
 * Example 3:
 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * Output: [8,9,9,9,0,0,0,1]
 * Constraints:
 * The number of nodes in each linked list is in the range [1, 100].
 * 0 <= Node.val <= 9
 * It is guaranteed that the list represents a number that does not have leading zeros.
 * @author eddie.lys
 * @since 2023/10/23
 */
public class AddTwoNumbers {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(9)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4, new ListNode(9))));

        ListNode listNode = addTwoNumbers(l1, l2);
        System.out.println(listNode);
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode();
        ListNode curHead = pre;
        int carry = 0; // carry表示进位
        while (l1 != null) {
            int x = l1.val;
            int y = Objects.nonNull(l2) ? l2.val : 0;

            int cur = carry + x + y;

            carry = cur / 10;
            int curSum = cur % 10;

            curHead.next = new ListNode(curSum);
            curHead = curHead.next;

            if (Objects.isNull(l1.next)) {
                if (Objects.nonNull(l2) && Objects.nonNull(l2.next)) {
                    l1 = l2.next;
                    l2 = null;
                }else {
                    l2 = null;
                    if (carry != 0) {
                        l1 = new ListNode(carry);
                        carry = 0;
                    }else {
                        break;
                    }
                }
            }else {
                l1 = l1.next;
                if (Objects.nonNull(l2)) {
                    l2 = l2.next;
                }
            }
        }
        return pre.next;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }
}

