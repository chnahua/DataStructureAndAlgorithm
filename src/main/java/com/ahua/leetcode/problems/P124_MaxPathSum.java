package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-03 19:39
 */

/**
 * 124. 二叉树中的最大路径和
 * <p>
 * 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
 * <p>
 * 路径和 是路径中各节点值的总和。
 * <p>
 * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
 * <p>
 * 树中节点数目范围是 [1, 3 * 10^4]
 * -1000 <= Node.val <= 1000
 * <p>
 * <p>
 * 评论区的疑问
 * 如果还要输出最大和的路径怎么办？？？
 */
public class P124_MaxPathSum {
    public static void main(String[] args) {
        // [1,-2,-3,1,3,-2,null,-1] // 3
        // [-3] // -3
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
class P124_Solution {
    // 此处初始化为小于 -1000 的值
    int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        /*
        // 题目保证至少一个结点, 即 root != null
        if (root == null) {
            return 0;
        }*/
        postorder(root);
        return maxSum;
    }

    // 后序遍历
    public int postorder1(TreeNode node) {
        int leftSum;
        int rightSum;
        int maxLeftAndRight;
        int nodePathSum;

        if (node.left == null && node.right == null) {
            nodePathSum = node.val;
            maxSum = Math.max(nodePathSum, maxSum);
        } else if (node.left != null && node.right != null) {
            leftSum = postorder(node.left);
            rightSum = postorder(node.right);
            maxLeftAndRight = Math.max(leftSum, rightSum);
            nodePathSum = Math.max(maxLeftAndRight + node.val, node.val);
            maxSum = Math.max(Math.max(nodePathSum, leftSum + node.val + rightSum), maxSum);
        } else if (node.left != null) {
            // 此处 node.left != null && node.right == null
            leftSum = postorder(node.left);
            nodePathSum = Math.max(leftSum + node.val, node.val);
            maxSum = Math.max(nodePathSum, maxSum);
        } else {
            // 此处 node.left == null && node.right != null
            rightSum = postorder(node.right);
            nodePathSum = Math.max(rightSum + node.val, node.val);
            maxSum = Math.max(nodePathSum, maxSum);
        }

        return nodePathSum;
    }

    // 后续遍历
    // 对上一个种代码实现的优化, 解题思想不变
    public int postorder(TreeNode node) {
        int leftSum = 0;
        int rightSum = 0;
        // 返回值, 通过该 node 结点到子结点的路径中的最大路径和
        int nodePathSum;

        if (node.left == null && node.right == null) {
            // node 为叶子结点, 无左右子结点
            nodePathSum = node.val;
            maxSum = Math.max(nodePathSum, maxSum);
        } else {
            if (node.left != null) {
                leftSum = postorder(node.left);
            }
            if (node.right != null) {
                rightSum = postorder(node.right);
            }
            // 此处 nodePathSum 为 以下三种情况中的较大值
            // leftSum + node.val、rightSum + node.val 和 node.val

            // 以下两种方式求 nodePathSum
            // 1.
            // 左、右较大值
            // int maxLeftAndRight = Math.max(leftSum, rightSum);
            // nodePathSum = maxLeftAndRight >= 0 ? maxLeftAndRight + node.val : node.val;
            // 2.
            nodePathSum = Math.max(Math.max(leftSum, rightSum) + node.val, node.val);

            // 此处 maxSum 为 以下三种情况中的较大值
            // nodePathSum、leftSum + node.val + rightSum 和 历史 maxSum 值
            // 总计下来其实 maxSum 为 历史 maxSum 值 与
            // [leftSum + node.val、 rightSum + node.val、 node.val、 leftSum + node.val + rightSum]
            // 这四个值中的最大值
            maxSum = Math.max(Math.max(nodePathSum, leftSum + node.val + rightSum), maxSum);
        }
        return nodePathSum;
    }
}