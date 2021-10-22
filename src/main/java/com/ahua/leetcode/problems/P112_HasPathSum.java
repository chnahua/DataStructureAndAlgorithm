package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-10-22 17:21
 */

/**
 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum ，
 * 判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。
 * <p>
 * 叶子节点 是指没有子节点的节点。
 */
public class P112_HasPathSum {
    public static void main(String[] args) {
//        int[] nums = new int[]{5, 4, 8, 11, -1, 13, 4, 7, 2, -1, -1, -1, 1};
//        int targetSum = 22;
//        int n = nums.length;
//        TreeNode[] treeNodes = new TreeNode[n];
//        TreeNode root;
//        if (n == 0 || nums[0] == -1) {
//            root = null;
//        } else {
//            for (int i = 0; i <= ( n - 1 ) / 2; i++) {
//                if (nums[i] != -1) {
//                    treeNodes[i] = new TreeNode(nums[i]);
//                } else {
//                    treeNodes[i] = null;
//                }
//                if (i == n / 2 && n % 2 == 0) {
//                    if ()
//                }
//            }
//            root = treeNodes[0];
//        }
//
//        P112_Solution solution = new P112_Solution();
//        System.out.println(solution.hasPathSum(root, targetSum));
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

class P112_Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }
        targetSum = targetSum - root.val;
        if (root.left != null) {
            if (hasPathSum(root.left, targetSum)) {
                return true;
            }
        }
        if (root.right != null) {
            return hasPathSum(root.right, targetSum);
        }
        return false;
    }

    // 对上一个方法的简化, 发现和官方解法一样了
    public boolean hasPathSum1(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }
}

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
