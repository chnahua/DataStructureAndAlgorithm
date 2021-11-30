package com.ahua.leetcode.problems;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huajun
 * @create 2021-11-30 18:53
 */

/**
 * 145. 二叉树的后序遍历
 * 给定一个二叉树，返回它的 后序 遍历。
 * <p>
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 */
public class P145_PostorderTraversal {
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

// 递归实现
class P145_Solution {
    List<Integer> ans = new ArrayList<>();

    public List<Integer> postorderTraversal(TreeNode root) {
        // 特殊情况提前处理
        // 可删
        if (root == null) {
            return ans;
        }
        postorder(root);
        return ans;
    }

    public void postorder(TreeNode node) {
        if (node == null) {
            return;
        }
        // 遍历左结点
        postorder(node.left);
        // 遍历右节点
        postorder(node.right);
        // 添加该结点入链表
        ans.add(node.val);
    }
}