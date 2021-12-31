package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-31 18:34
 */

public class P226_InvertTree {
    public static void main(String[] args) {

    }
}

/*
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
// 第一种实现
class P226_Solution1 {
    public TreeNode invertTree(TreeNode root) {
        // 前序遍历翻转
        // preInvertTree(root);
        // 后序遍历翻转
        postInvertTree(root);
        return root;
    }

    // 前序遍历翻转
    public void preInvertTree(TreeNode node) {
        if (node == null) {
            return;
        }
        TreeNode tmp = node.left;
        node.left = node.right;
        node.right = tmp;
        preInvertTree(node.left);
        preInvertTree(node.right);
    }

    // 后序遍历翻转
    public void postInvertTree(TreeNode node) {
        if (node == null) {
            return;
        }
        postInvertTree(node.left);
        postInvertTree(node.right);
        TreeNode tmp = node.left;
        node.left = node.right;
        node.right = tmp;
    }
}

// 第二种实现（简化）
class P226_Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root != null) {
            TreeNode tmp = invertTree(root.left);
            root.left = invertTree(root.right);
            root.right = tmp;
        }
        return root;
    }
}