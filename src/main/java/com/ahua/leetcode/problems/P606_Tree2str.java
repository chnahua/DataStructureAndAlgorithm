package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-19 22:12
 */

/**
 * 606. 根据二叉树创建字符串 construct-string-from-binary-tree
 * 你需要采用前序遍历的方式，将一个二叉树转换成一个由括号和整数组成的字符串。
 * <p>
 * 空节点则用一对空括号 "()" 表示。而且你需要省略所有不影响字符串与原始二叉树之间的一对一映射关系的空括号对。
 */
public class P606_Tree2str {
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
// 最开始做时, 好绕啊, 不过结果还蛮好的, 一次通过
// 递归
// 1 ms 100.00%
// 41.2 MB 28.66%
class P606_Solution {
    StringBuilder ans;

    public String tree2str(TreeNode root) {
        ans = new StringBuilder();
        if (root == null) {
            return "";
        }
        preorder(root);
        return ans.toString();
    }

    private void preorder1(TreeNode node) {
        ans.append(node.val);
//        if (node.left == null && node.right == null) {
//            return ;
//        }
        if (node.left == null && node.right != null) {
            ans.append("()");
            ans.append("(");
            preorder(node.right);
            ans.append(")");
        } else if (node.left != null && node.right == null) {
            ans.append("(");
            preorder(node.left);
            ans.append(")");
        } else if (node.left != null && node.right != null) {
            ans.append("(");
            preorder(node.left);
            ans.append(")");
            ans.append("(");
            preorder(node.right);
            ans.append(")");
        }
    }

    // 逻辑修改优化
    private void preorder(TreeNode node) {
        ans.append(node.val);
        // 处理左子树
        // 左不为 null 添加左边 val
        if (node.left != null) {
            ans.append("(");
            preorder(node.left);
            ans.append(")");
        } else {
            // 左为 null 若右也为 null, 则不添加
            if (node.right == null) {
                return;
            }
            // 左为 null 若右不为 null, 则左边 括号内为空
            ans.append("()");
        }
        // 处理右子树
        // 若右不为 null, 则添加右边 val, 若为 null, 则不添加
        if (node.right != null) {
            ans.append("(");
            preorder(node.right);
            ans.append(")");
        }
    }
}