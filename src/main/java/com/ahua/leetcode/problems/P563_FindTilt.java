package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-20 0:10
 */

public class P563_FindTilt {
    public static void main(String[] args) {
        // 哈哈, 只提交一次就通过了哈哈哈, 虽然理解错题意, 做错过一次
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
class P563_Solution {

    int tilt = 0;

    public int findTilt(TreeNode root) {
        if (root == null) {
            return 0;
        }

        getTreeSum(root);

        return tilt;
    }

    public int getTreeSum1(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftTreeSum = 0;
        int rightTreeSum = 0;

        if (node.left != null) {
            // 左子树的和
            leftTreeSum = getTreeSum(node.left);

        }
        if (node.right != null) {
            // 右子树的和
            rightTreeSum = getTreeSum(node.right);

        }
        // tilt 加上 node 结点的坡度, 遍历完所有结点, tilt 就是整棵树的坡度
        tilt += Math.abs(leftTreeSum - rightTreeSum);

        // 返回值是 node 这棵树的所有结点值的和
        return leftTreeSum + rightTreeSum + node.val;
    }

    // 代码简化
    public int getTreeSum(TreeNode node) {
        if (node == null) {
            return 0;
        }
        // 左子树的和
        int leftTreeSum = getTreeSum(node.left);

        // 右子树的和
        int rightTreeSum = getTreeSum(node.right);

        // tilt 加上 node 结点的坡度, 遍历完所有结点, tilt 就是整棵树的坡度
        tilt += Math.abs(leftTreeSum - rightTreeSum);

        // 返回值是 node 这棵树的所有结点值的和
        return leftTreeSum + rightTreeSum + node.val;
    }
}

