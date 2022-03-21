package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-21 22:59
 */

import java.util.HashSet;
import java.util.Set;

/**
 * 653. 两数之和 IV - 输入 BST two-sum-iv-input-is-a-bst
 * 给定一个二叉搜索树 root 和一个目标结果 k，如果 BST 中存在两个元素且它们的和等于给定的目标结果，则返回 true。
 * 二叉树的节点个数的范围是  [1, 10^4].
 * -10^4 <= Node.val <= 10^4
 * root 为二叉搜索树
 * -10^5 <= k <= 10^5
 */
public class P653_FindTarget {
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
// 递归(深度优先搜索) + 哈希表
// 2 ms 97.52%
// 42 MB 21.72%
class P653_Solution {
    Set<Integer> hashSet = new HashSet<>();

    public boolean findTarget(TreeNode root, int k) {
        if (root == null) {
            return false;
        }
        if (hashSet.contains(k - root.val)) {
            return true;
        }
        hashSet.add(root.val);
        return findTarget(root.left, k) || findTarget(root.right, k);
    }
}

// 广度优先搜索 + 哈希表
// 深度优先搜索 + 中序遍历 + 双指针
// 迭代 + 中序遍历 + 双指针