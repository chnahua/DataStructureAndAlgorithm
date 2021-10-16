package com.ahua.leetcode.problems;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author huajun
 * @create 2021-10-15 20:56
 */

public class P1857_LargestPathValue {
    public static void main(String[] args) {
        String colors = "abaca";
        int[][] edges = {{0, 1}, {0, 2}, {2, 3}, {3, 4}};
//        int[][] edges = {{0, 1}, {0, 2}, {2, 3}, {3, 4}, {1, 2}, {3, 1}};
//        String colors = "a";
//        int[][] edges = {{0, 0}};
//        String colors = "g";
//        int[][] edges = {};

        Solution solution = new Solution();
        int largestPathValue = solution.largestPathValue(colors, edges);

        System.out.println(largestPathValue);
    }
}

class Solution {
    public int largestPathValue(String colors, int[][] edges) {
//        if (colors.length() == 1) {
//            if (edges.length != 0 && edges[0][0] == 0 && edges[0][1] == 0) {
//                // System.out.println(1111);
//                return -1;
//            }
//        }

        // 节点个数
        int nodeNum = colors.length();
        // 每个节点的入度
        int[] degree = new int[nodeNum];
        // 保存到达每个节点时, 该条路径上的各种颜色出现的次数
        int[][] pathValue = new int[nodeNum][26];
        // 创建邻接表
        Node[] adjList = new Node[nodeNum];
        // 保存每个节点到邻接表中
//        for (int i = 0; i < adjList.length; i++) {
//            adjList[i] = new Node(i);
//        }
//        System.out.println(Arrays.toString(adjList));
        // 把从 from 节点到所有 to 节点的所有边的关系都保存为
        // 从 from 节点指向的所有的 to 节点形成的一个链表
        // 例如：从字符 ’a‘ 这个节点到 字符 ’b‘’c‘’d‘ 这三个节点的五条边关系保存为
        // [aNode] --> bNode --> cNode --> dNode
        // 如果 ’b‘ 到 ’e‘’f‘ ，则
        // [bNode] --> eNode --> fNode
        // 其中，[]括号里的[aNode][bNode]两节点是没有保存在邻接表中的
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            System.out.println("from " + from + ", to " + to);
            // to 节点的入度更新,一条 from -> to 的边, 则 to 节点的入度 + 1
            degree[to]++;
            // 采用头插法，与注释的三行等价
            adjList[from] = new Node(to, adjList[from]);
            /*
            Node toNode = new Node(colors.charAt(to));
            toNode.next = adjList[from].next;
            adjList[from] = toNode;
             */
        }
        // System.out.println(Arrays.toString(adjList));
        // 保存入度为 0 的各个节点，有可能是非连通图
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < degree.length; i++) {
            if (degree[i] == 0) {
                queue.add(i);
            }
        }
        // 如果 queue 中一个都没有, 表示存在环
        if (queue.isEmpty()) {
            // System.out.println(2222);
            return -1;
        }

        // 如果无环,当循环结束时, bfsCount 应该等于节点数 nodeNum
        int bfsCount = 0;
        Node next;
        while (!queue.isEmpty()) {
            // 初始时前 initQueueSize = queue.size() 个 cur 代表入度为 0 的从该点开始遍历的出发节点的下标
            // 可以看作有 initQueueSize 个子图
            int cur = queue.remove();
            // adjList[cur] 处存储的是 cur 的下一个节点
            next = adjList[cur];

            // System.out.println("cur " + cur);
            // bfsCount 入度为 0 的点的个数
            bfsCount++;
            // 将当前路径上的该点处的相应颜色的值加 1
            pathValue[cur][colors.charAt(cur) - 97]++;
            // 遍历当前节点能够到达的其它所有节点
            while (next != null) {
                // 入度减 1, 减小到 0 时, 将其入队
                degree[next.value]--;
                if (degree[next.value] == 0) {
                    queue.add(next.value);
                }

                // 更新 next 所有颜色次数
                // 由于当前的这个节点在曾经可能已经在其它路径中被访问过,所以此次访问需要比较上次
                // pathValue[0].length == 26 小写字母个数
                for (int i = 0; i < pathValue[0].length; i++) {
                    pathValue[next.value][i] = Math.max(pathValue[next.value][i], pathValue[cur][i]);
                }
                // next 指向 cur 这个入度为 0 的节点的下一个与它相连的能组成边的点
                next = next.next;
            }
        }

        // 如果无环,当循环结束时, bfsCount 应该等于节点数 nodeNum
        if (bfsCount != nodeNum) {
            System.out.println("bfsCount " + bfsCount);
            return -1;
        }

        // 从 pathValue 二维数组中获取最大值 largestPathValue
        int res = 0;
        for (int[] ints : pathValue) {
            res = Math.max(res, Arrays.stream(ints).max().getAsInt());
        }
//        for (int[] ints : pathValue) {
//            for (int j = 0; j < pathValue[0].length; j++) {
//                res = Math.max(res, ints[j]);
//            }
//        }
        return res;
    }
}

class Node {
    // public char color;
    // 26 个字母 对应成 0-25 个数字
    // public int colorValue;
    // 字符下标
    public int value;
    Node next;

    public Node(int value) {
        this.value = value;
        //this.colorValue = colorValue;
    }

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }
}
