package com.ahua.leetcode.second;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author huajun
 * @create 2021-12-05 20:41
 */

public class Second_P1857_LargestPathValue {
    public static void main(String[] args) {
        // 测试案例 1
        String colors = "abaca";
        int[][] edges = {{0, 1}, {0, 2}, {2, 3}, {3, 4}};
        int[][] edges1 = {{0, 1}, {0, 2}, {2, 3}, {3, 4}, {1, 2}, {3, 1}}; // 存在环

        // 测试案例 2
        String colors2 = "a";
        int[][] edges2 = {{0, 0}}; // 存在环

        // 测试案例 3
        String colors3 = "g";
        int[][] edges3 = {};

        Second_P1857_Solution solution = new Second_P1857_Solution();
        System.out.println(solution.largestPathValue(colors, edges)); // 3
        System.out.println(solution.largestPathValue(colors, edges1)); // -1
        System.out.println(solution.largestPathValue(colors2, edges2)); // -1
        System.out.println(solution.largestPathValue(colors3, edges3)); // 1
    }
}

// 拓扑排序(Topological sort)(BFS 队列实现) + 动态规划
class Second_P1857_Solution {
    public int largestPathValue(String colors, int[][] edges) {

        // 节点个数
        int nodeNum = colors.length();
        // 小写字母个数
        final int letterNum = 26;
        // 各个节点的入度
        int[] inDegree = new int[nodeNum];
        // 动态规划数组, 保存到达各个节点时, 该条路径上的各种颜色出现的次数
        int[][] pathValueDP = new int[nodeNum][letterNum];
        // 创建邻接表
        Node[] adjList = new Node[nodeNum];

        // 把从 from 节点到所有 to 节点的所有边的关系都保存为
        // 从 from 节点指向的所有的 to 节点形成的一个链表
        // 例如: 从字符 ’a‘ 这个节点到字符 ’b‘’c‘’d‘ 这三个节点的四条边关系保存为
        // {aNode} --> [bNode] --> cNode --> dNode
        // 如果字符 ’b‘ 能到 ’e‘’f‘, 则
        // {bNode} --> [eNode] --> fNode
        // 其中, []括号里的 [bNode]、[eNode] 两个节点才保存在邻接表 adjList[from] 中
        // from 为相对应的 ’a‘’b‘ 在原字符串中的下标
        int from, to;
        for (int[] edge : edges) {
            from = edge[0];
            to = edge[1];
            // 更新 to 节点的入度, 一条 from -> to 的边, 则 to 节点的入度 + 1
            inDegree[to]++;
            // 采用头插法, 与注释的三行等价
            adjList[from] = new Node(to, adjList[from]);
            /*
            Node toNode = new Node(to);
            toNode.next = adjList[from];
            adjList[from] = toNode;
             */
        }

        // BFS
        // 保存入度为 0 的各个节点的下标, 有可能是非连通图
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // 如果 queue 中一个入度为 0 的节点都没有, 表示图存在环, 并且整个图是一个大环, 所有节点的入度都大于 0
        // 此判断可以删除
        if (queue.isEmpty()) {
            return -1;
        }

        // bfsCount 表示遍历过程中遍历了多少个入度为 0 的 节点
        // 如果无环, 当循环结束时, bfsCount 应该等于节点总数 nodeNum
        int bfsCount = 0;
        Node next;
        int cur;
        while (!queue.isEmpty()) {
            // 初始时前 initQueueSize = queue.size() 个 cur 代表入度为 0 的从该点开始遍历的出发节点的下标
            // 可以看作有 initQueueSize 个子图 (这个说法是错误的)

            // 出队一个入度为 0 的节点
            cur = queue.remove();
            // adjList[cur] 处存储的是 cur 能够到达的下一个节点 next
            next = adjList[cur];

            // bfsCount 为遍历过程中遍历了的入度为 0 的节点的个数
            bfsCount++;
            // 将当前路径上的该点处的相应颜色的值加 1
            pathValueDP[cur][colors.charAt(cur) - 97]++;

            // 遍历当前节点能够到达的其它所有节点
            while (next != null) {
                // 入度减 1, 减小后为 0 时, 将其入队
                inDegree[next.value]--;
                if (inDegree[next.value] == 0) {
                    queue.add(next.value);
                }

                // 动态规划, 更新 next 所有颜色次数
                // 由于当前的这个 next 节点在曾经可能已经在其它路径中被访问过, 所以此次访问需要比较上次
                // 以确保当前循环结束后, pathValueDP[next.value][i] 处保存的值为
                // 到目前为止, 到达该节点的各条路径中各种颜色(26个字母)出现的最多的次数
                // letterNum == pathValueDP[0].length == 26 小写字母个数
                for (int i = 0; i < letterNum; i++) {
                    pathValueDP[next.value][i] = Math.max(pathValueDP[next.value][i], pathValueDP[cur][i]);
                }
                // 再令 next 为 cur 这个入度为 0 的节点的其它下一个节点(与它相连的能形成有向边的节点)
                // 直到遍历完所有 next 节点后退出循环
                next = next.next;
            }
        }

        // 如果无环, 当循环结束时, bfsCount 应该等于节点总数 nodeNum
        // 如果无法遍历完所有的结点，则意味着当前的图不是有向无环图, 不存在拓扑排序
        if (bfsCount != nodeNum) {
            return -1;
        }

        // 从 pathValueDP 二维数组中获取最大值 largestPathValue
        int res = 0;
//        for (int[] ints : pathValueDP) {
//            res = Math.max(res, Arrays.stream(ints).max().getAsInt());
//        }
        for (int[] ints : pathValueDP) {
            for (int j = 0; j < letterNum; j++) {
                res = Math.max(res, ints[j]);
            }
        }
        return res;
    }
}

class Node {

    // value 表示第几个字符, 节点下标
    int value;
    Node next;

    public Node(int value) {
        this.value = value;
    }

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }
}

