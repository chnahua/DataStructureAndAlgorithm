package com.ahua.leetcode.problems;

import java.util.*;

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
class P145_Solution1 {
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

// 迭代
class P145_Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> ans = new LinkedList<>();
        // 特殊情况提前处理
        if (root == null) {
            return ans;
        }

        Deque<TreeNode> stack = new LinkedList<>();

        while (root != null || !stack.isEmpty()) {
            // 一直遍历右结点
            if (root != null) {
                // 每遇到一个结点, 就把它加入结果集, 并把该节点保存到中间结果中
                // 每次前序遍历时, 都将结点写入结果链表头, 而不是尾
                ans.addFirst(root.val);
                stack.push(root);
                // 每次先遍历右结点, 走到空, 再遍历左结点
                root = root.right;
            } else {
                // 右子树走到空, 就从获取已经遍历过右子树的中间结果, 将它出栈, 并遍历它的左子树
                root = stack.poll();
                root = root.left;
            }
        }
        return ans;
    }
}