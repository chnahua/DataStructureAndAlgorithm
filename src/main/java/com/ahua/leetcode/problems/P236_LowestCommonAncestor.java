package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-28 19:02
 */

/**
 * 236. 二叉树的最近公共祖先
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 * <p>
 * 百度百科中最近公共祖先的定义为：
 * “对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，
 * 满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 * 树中节点数目在范围 [2, 105] 内。
 * <p>
 * -10^9 <= Node.val <= 10^9
 * 所有 Node.val 互不相同 。
 * p != q
 * p 和 q 均存在于给定的二叉树中。
 */
public class P236_LowestCommonAncestor {
    public static void main(String[] args) {

    }
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
class P236_Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return preorder(root, p, q);
    }

    // 后序遍历(理论上不及前序遍历)
    public TreeNode postorder(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return null;
        }
        TreeNode leftAncestor = postorder(node.left, p, q);
        TreeNode rightAncestor = postorder(node.right, p, q);
        if (leftAncestor != null && rightAncestor != null || (node.val == p.val || node.val == q.val)) {
            return node;
        } else if (leftAncestor != null) {
            return leftAncestor;
        }
        return rightAncestor;
    }

    // 前序遍历
    public TreeNode preorder(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return null;
        }
        // 遇到找到 p 或者 q 可直接返回该节点
        if (node.val == p.val || node.val == q.val) {
            // 一个节点也可以是它自己的祖先
            return node;
        }
        TreeNode leftAncestor = preorder(node.left, p, q);
        TreeNode rightAncestor = preorder(node.right, p, q);
        if (leftAncestor != null && rightAncestor != null) {
            // 两个指定节点的最近公共祖先
            return node;
        } else if (leftAncestor != null) {
            return leftAncestor;
        }
        // 此时的 rightAncestor 可能为 pq 之一, 也可能为 null
        return rightAncestor;
    }
}



