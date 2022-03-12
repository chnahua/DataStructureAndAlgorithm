package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-12 23:29
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 590. N 叉树的后序遍历 n-ary-tree-postorder-traversal
 * 给定一个 n 叉树的根节点 root ，返回 其节点值的 后序遍历 。
 * <p>
 * n 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。
 * <p>
 * 节点总数在范围 [0, 104] 内
 * 0 <= Node.val <= 10^4
 * n 叉树的高度小于或等于 1000
 * <p>
 * 进阶：递归法很简单，你可以使用迭代法完成此题吗?
 */
public class P590_Postorder {
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

// 递归实现
// 0 ms 100.00%
// 42.5 MB 5.16%
class P590_Solution {
    List<Integer> ans = new ArrayList<>();

    public List<Integer> postorder(Node root) {
        if (root != null) {
            post(root);
        }
        return ans;
    }

    private void post(Node node) {
        if (node == null) {
            return;
        }
        for (Node child : node.children) {
            post(child);
        }
        ans.add(node.val);
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