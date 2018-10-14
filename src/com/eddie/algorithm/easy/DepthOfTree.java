package com.eddie.algorithm.easy;


/**
 * @author eddie
 * @createTime 2018-10-14
 * @description 根据给定树结构求树结构深度
 */
public class DepthOfTree {

    public int maxDepth(TreeNode root) {
        if (root == null){
            return 0;
        }else {
            int left = maxDepth(root.left);
            int right = maxDepth(root.right);
            return Math.max(left,right) + 1;
        }

    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
     }
}
