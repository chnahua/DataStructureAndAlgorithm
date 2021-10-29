package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-10-29 23:02
 */

/**
 * 给定一个二叉树，找出其最大深度。
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 * 说明: 叶子节点是指没有子节点的节点。
 */

public class P104_MaxDepth {
    public static void main(String[] args) {

    }
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */

class P104_Solution {
    // DFS
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else if (root.left == null && root.right == null) {
            return 1;
        }
        int leftDepth = 0;
        int rightDepth = 0;
        // 左子树的深度
        if (root.left != null) {
            leftDepth = maxDepth(root.left);
        }
        // 右子树的深度
        if (root.right != null) {
            rightDepth = maxDepth(root.right);
        }
        return Math.max(leftDepth, rightDepth) + 1;
    }

    // DFS 对上方法的逻辑简化
    public int maxDepth1(TreeNode root) {
        if (root == null) {
            return 0;
        } else if (root.left == null && root.right == null) {
            return 1;
        }
        return Math.max(maxDepth1(root.left), maxDepth1(root.right)) + 1;
    }
}