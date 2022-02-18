package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-18 20:22
 */

import java.util.HashMap;

/**
 * 1791. 找出星型图的中心节点 find-center-of-star-graph
 * 有一个无向的 星型 图，由 n 个编号从 1 到 n 的节点组成。
 * 星型图有一个 中心 节点，并且恰有 n - 1 条边将中心节点与其他每个节点连接起来。
 * <p>
 * 给你一个二维整数数组 edges ，其中 edges[i] = [ui, vi] 表示在节点 ui 和 vi 之间存在一条边。
 * 请你找出并返回 edges 所表示星型图的中心节点。
 * <p>
 * 3 <= n <= 10^5
 * edges.length == n - 1
 * edges[i].length == 2
 * 1 <= ui, vi <= n
 * ui != vi
 * 题目数据给出的 edges 表示一个有效的星型图
 */
public class P1791_FindCenter {
    public static void main(String[] args) {
        int[][] edges = new int[][]{{1, 2}, {2, 3}, {4, 2}};
        int[][] edges1 = new int[][]{{1, 2}, {5, 1}, {1, 3}, {1, 4}};
        int[][] edges2 = new int[][]{{1, 3}, {2, 3}};
        P1791_Solution solution = new P1791_Solution();
        System.out.println(solution.findCenter(edges)); // 2
        System.out.println(solution.findCenter(edges1)); // 1
        System.out.println(solution.findCenter(edges2)); // 3
    }
}

class P1791_Solution {
    // 数组
    // 0 ms 100%
    // 67.2 MB 16.49%
    public int findCenter1(int[][] edges) {
        int[] degree = new int[edges.length + 2];
        int id = 0;
        for (int[] edge : edges) {
            degree[edge[0]]++;
            if (degree[edge[0]] == 2) {
                id = edge[0];
            } else if (degree[edge[0]] >= 3) {
                return edge[0];
            }
            degree[edge[1]]++;
            if (degree[edge[1]] == 2) {
                id = edge[1];
            } else if (degree[edge[1]] >= 3) {
                return edge[1];
            }
        }
        return id;

    }

    // 哈希表
    // 0 ms 100%
    // 64 MB 26.47%
    public int findCenter2(int[][] edges) {
        HashMap<Integer, Integer> degree = new HashMap<>();
        int id = 0;
        for (int[] edge : edges) {
            int value = degree.getOrDefault(edge[0], 0);
            if (value == 1) {
                id = edge[0];
            } else if (value == 2) {
                return edge[0];
            }
            degree.put(edge[0], value + 1);

            value = degree.getOrDefault(edge[1], 0);
            if (value == 1) {
                id = edge[1];
            } else if (value == 2) {
                return edge[1];
            }
            degree.put(edge[1], value + 1);
        }
        return id;
    }

    // 我没有完全看透题意, 理解错了一点, 但是这样做确实没问题, 题目中明确说了中心节点有 n-1 条边与之相连
    // 所以只需要判断任意两条边的相同节点是谁即可
    // 感觉智商收到侮辱
    // 0 ms 100%
    // 64.1 MB 21.79%
    public int findCenter(int[][] edges) {
        return edges[0][0] == edges[1][0] || edges[0][0] == edges[1][1] ? edges[0][0] : edges[0][1];
    }
}