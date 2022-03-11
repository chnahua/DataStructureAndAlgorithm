package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-11 22:57
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 2049. 统计最高分的节点数目 count-nodes-with-the-highest-score
 * 给你一棵根节点为 0 的 二叉树 ，它总共有 n 个节点，节点编号为 0 到 n - 1 。
 * 同时给你一个下标从 0 开始的整数数组 parents 表示这棵树，其中 parents[i] 是节点 i 的父节点。
 * 由于节点 0 是根，所以 parents[0] == -1 。
 * <p>
 * 一个子树的 大小 为这个子树内节点的数目。每个节点都有一个与之关联的 分数 。
 * 求出某个节点分数的方法是，将这个节点和与它相连的边全部 删除 ，剩余部分是若干个 非空 子树，这个节点的 分数 为所有这些子树 大小的乘积 。
 * <p>
 * 请你返回有 最高得分 节点的 数目 。
 * <p>
 * n == parents.length
 * 2 <= n <= 10^5
 * parents[0] == -1
 * 对于 i != 0 ，有 0 <= parents[i] <= n - 1
 * parents 表示一棵二叉树。
 */
public class P2049_CountHighestScoreNodes {
    public static void main(String[] args) {
        int[] parents = new int[]{-1, 2, 0, 2, 0};
        int[] parents1 = new int[]{-1, 2, 0};
        P2049_Solution solution = new P2049_Solution();
        System.out.println(solution.countHighestScoreNodes(parents)); // 3
        System.out.println(solution.countHighestScoreNodes(parents1)); // 2
    }
}

// 深度优先搜索(递归实现)
// 45 ms 84.71%
// 66.4 MB 69.48%
// 第一次通过测试用例: 204 / 206, 原因是 score 分数越界
class P2049_Solution1 {
    int n;
    List<Integer>[] children;
    long maxScore;
    int count;

    public int countHighestScoreNodes(int[] parents) {
        this.n = parents.length;
        this.children = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            children[parents[i]].add(i);
        }
        System.out.println(Arrays.toString(children));
        this.maxScore = 0;
        this.count = 0;
        dfs(0);
        return count;
    }

    private int dfs(int parent) {
        List<Integer> childrenList = children[parent];
        long score = 0;
        // 该树节点个数(包含该节点)
        int thisTreeNodeNum = 1;
        // 左子树节点个数
        int leftTreeNodeNum;
        // 右子树节点个数
        int rightTreeNodeNum;
        int size = childrenList.size();
        // 无子节点时
        if (size == 0) {
            score = n - 1;
        } else if (size == 1) {
            // 只有一颗子树
            leftTreeNodeNum = dfs(childrenList.get(0));
            thisTreeNodeNum = leftTreeNodeNum + 1;
            score = (long) leftTreeNodeNum * (n - thisTreeNodeNum == 0 ? 1 : n - thisTreeNodeNum);
        } else if (size == 2) {
            // 左子树节点个数
            leftTreeNodeNum = dfs(childrenList.get(0));
            // 右子树节点个数
            rightTreeNodeNum = dfs(childrenList.get(1));
            // 该树节点个数
            thisTreeNodeNum = leftTreeNodeNum + rightTreeNodeNum + 1;
            // 该节点分数
            score = (long) leftTreeNodeNum * rightTreeNodeNum * (n - thisTreeNodeNum == 0 ? 1 : n - thisTreeNodeNum);
        }

        if (maxScore < score) {
            maxScore = score;
            count = 1;
        } else if (maxScore == score) {
            count++;
        }
        return thisTreeNodeNum;
    }
}

// 将 List<Integer>[] children 改为 List<List<Integer>> children , 其他不变, 效率变差了
// 但是设定初始化容量, 会提高执行效率
// 44 ms 86.64%
// 60.1 MB 87.67%
class P2049_Solution {
    int n;
    List<List<Integer>> children;
    long maxScore;
    int count;

    public int countHighestScoreNodes(int[] parents) {
        this.n = parents.length;
        this.children = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            children.add(new ArrayList<>(2));
        }
        for (int i = 1; i < n; i++) {
            children.get(parents[i]).add(i);
        }
        this.maxScore = 0;
        this.count = 0;
        dfs(0);
        return count;
    }

    private int dfs(int parent) {
        List<Integer> childrenList = children.get(parent);
        long score = 0;
        // 该树节点个数(包含该节点)
        int thisTreeNodeNum = 1;
        // 左子树节点个数
        int leftTreeNodeNum;
        // 右子树节点个数
        int rightTreeNodeNum;
        int size = childrenList.size();
        // 无子节点时
        if (size == 0) {
            score = n - 1;
        } else if (size == 1) {
            // 只有一颗子树
            leftTreeNodeNum = dfs(childrenList.get(0));
            thisTreeNodeNum = leftTreeNodeNum + 1;
            score = (long) leftTreeNodeNum * (n - thisTreeNodeNum == 0 ? 1 : n - thisTreeNodeNum);
        } else if (size == 2) {
            // 左子树节点个数
            leftTreeNodeNum = dfs(childrenList.get(0));
            // 右子树节点个数
            rightTreeNodeNum = dfs(childrenList.get(1));
            // 该树节点个数
            thisTreeNodeNum = leftTreeNodeNum + rightTreeNodeNum + 1;
            // 该节点分数
            score = (long) leftTreeNodeNum * rightTreeNodeNum * (n - thisTreeNodeNum == 0 ? 1 : n - thisTreeNodeNum);
        }

        if (maxScore < score) {
            maxScore = score;
            count = 1;
        } else if (maxScore == score) {
            count++;
        }
        return thisTreeNodeNum;
    }
}