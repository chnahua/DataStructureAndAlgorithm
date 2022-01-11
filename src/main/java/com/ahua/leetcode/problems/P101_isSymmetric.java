package com.ahua.leetcode.problems;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author huajun
 * @create 2022-01-11 19:51
 */

/**
 * 101. 对称二叉树
 * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
 * 树中节点数目在范围 [1, 1000] 内
 * -100 <= Node.val <= 100
 * <p>
 * 进阶：你可以运用递归和迭代两种方法解决这个问题吗？
 */
public class P101_isSymmetric {
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
// 面试时写的代码, 有一些不改变逻辑以及所使用的数据结构的小修改, 使其能够直接复制在 LeetCode 上
class ODInterview_2022_01_11_solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);
        while (!queue.isEmpty()) {
            int size = queue.size();
            TreeNode[] arr = new TreeNode[size / 2];
            // 前一半
            for (int i = 0; i < size / 2; i++) {
                TreeNode node = queue.remove();
                arr[i] = node;
                if (node != null) {
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
            // 后一半
            for (int i = 1; i <= size / 2; i++) {
                TreeNode node = queue.remove();
                // 两个都为 null
                if (node == null && arr[size / 2 - i] == null) {
                    continue;
                }
                // 其中一个为 null
                if (node == null || arr[size / 2 - i] == null) {
                    return false;
                }
                // 两个都不为 null 判断值是否相等
                if (node.val != arr[size / 2 - i].val) {
                    return false;
                }
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        return true;
    }
}

// 模式识别 广度周游(队列)
// 迭代
// 本来想觉得使用双端队列也行, 但是发现判断的逻辑逻辑可以实现, 但是添加进 queue 的顺序不对
// 于是, 想起刚才看了一眼的那个迭代代码, 但是没有看解释, 发现原来是这样的思维过程
// 现在我该想想怎么从迭代转换到递归
class P101_solution1 {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);
        while (!queue.isEmpty()) {
            TreeNode first = queue.remove();
            TreeNode second = queue.remove();
            // 两个都为 null
            if (first == null && second == null) {
                continue;
            }
            // 其中一个为 null
            if (first == null || second == null) {
                return false;
            }
            // 两个都不为 null 判断值是否相等
            if (first.val != second.val) {
                return false;
            }
            // 每两个对称的结点, 要判断一个结点的子结点与另一个结点的子结点是否对称
            // 只需要将两个要判断的子结点按照相应顺序添加进 queue 即可
            // 要判断 first.left 与 second.right 是否对称, 将其加入 queue
            queue.add(first.left);
            queue.add(second.right);

            // 要判断 first.right 与 second.left 是否对称, 将其加入 queue
            queue.add(first.right);
            queue.add(second.left);
        }
        return true;
    }
}

// 模式识别 深度周游(递归) (最优解法)
class P101_solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return checkTowNode(root.left, root.right);
    }

    private boolean checkTowNode(TreeNode first, TreeNode second) {
        // 两个都为 null
        if (first == null && second == null) {
            return true;
        }
        // 其中一个为 null
        if (first == null || second == null) {
            return false;
        }
        // 两个都不为 null 判断值是否相等
        if (first.val != second.val) {
            return false;
        }

        // 两颗小子树是否轴对称
        return checkTowNode(first.left, second.right) && checkTowNode(first.right, second.left);
    }
}