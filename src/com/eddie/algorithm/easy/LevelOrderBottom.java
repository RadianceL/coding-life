package com.eddie.algorithm.easy;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author eddie
 * @createTime 2018-10-14
 * @description 层序遍历树结构
 */
public class LevelOrderBottom {

    public List<List<Integer>> levelOrderBottom(TreeNode root) {

        List<List<Integer>> lists = new ArrayList<>();
        Queue<TreeNode> queue = new PriorityQueue<>();

        queue.add(root);

        while (!queue.isEmpty()){
            TreeNode peek = queue.peek();
            if (peek.left != null){
                queue.add(peek.left);
            }
            if (peek.right != null){
                queue.add(peek.right);
            }
        }


        return null;
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
