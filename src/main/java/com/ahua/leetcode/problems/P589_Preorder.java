package com.ahua.leetcode.problems;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huajun
 * @create 2022-03-10 22:51
 */

/**
 * 589. N 叉树的前序遍历 n-ary-tree-preorder-traversal
 * 给定一个 n 叉树的根节点 root ，返回 其节点值的 前序遍历 。
 * <p>
 * n 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。
 * <p>
 * 节点总数在范围 [0, 104]内
 * 0 <= Node.val <= 10^4
 * n 叉树的高度小于或等于 1000
 * <p>
 * 进阶：递归法很简单，你可以使用迭代法完成此题吗?
 */
public class P589_Preorder {
    public static void main(String[] args) {

    }
}

/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

// 递归
// 0 ms 100%
// 42.4 MB 5.28%
class P589_Solution {
    List<Integer> ans = new ArrayList<>();

    public List<Integer> preorder(Node root) {
        if (root != null) {
            pre(root);
        }
        return ans;
    }

    private void pre(Node node) {
        ans.add(node.val);
        if (node.children != null) {
            for (Node child : node.children) {
                pre(child);
            }
        }
    }

    static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }
}

// 迭代