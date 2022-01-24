package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-24 22:40
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 2045. 到达目的地的第二短时间 second-minimum-time-to-reach-destination
 * 城市用一个 双向连通 图表示，图中有 n 个节点，从 1 到 n 编号（包含 1 和 n）。
 * 图中的边用一个二维整数数组 edges 表示，其中每个 edges[i] = [ui, vi] 表示一条节点 ui 和节点 vi 之间的双向连通边。
 * 每组节点对由 最多一条 边连通，顶点不存在连接到自身的边。穿过任意一条边的时间是 time 分钟。
 * <p>
 * 每个节点都有一个交通信号灯，每 change 分钟改变一次，从绿色变成红色，再由红色变成绿色，循环往复。
 * 所有信号灯都 同时 改变。你可以在 任何时候 进入某个节点，但是 只能 在节点 信号灯是绿色时 才能离开。
 * 如果信号灯是  绿色 ，你 不能 在节点等待，必须离开。
 * <p>
 * 第二小的值 是 严格大于 最小值的所有值中最小的值。
 * <p>
 * 例如，[2, 3, 4] 中第二小的值是 3 ，而 [2, 2, 4] 中第二小的值是 4 。
 * 给你 n、edges、time 和 change ，返回从节点 1 到节点 n 需要的 第二短时间 。
 * <p>
 * 注意：
 * 你可以 任意次 穿过任意顶点，包括 1 和 n 。
 * 你可以假设在 启程时 ，所有信号灯刚刚变成 绿色 。
 * <p>
 * 2 <= n <= 10^4
 * n - 1 <= edges.length <= min(2 * 10^4, n * (n - 1) / 2)
 * edges[i].length == 2
 * 1 <= ui, vi <= n
 * ui != vi
 * 不含重复边
 * 每个节点都可以从其他节点直接或者间接到达
 * 1 <= time, change <= 10^3
 */
public class P2045_SecondMinimum {
    public static void main(String[] args) {
        int[][] edges = new int[][]{{1, 2}, {1, 3}, {1, 4}, {3, 4}, {4, 5}};
        int[][] edges1 = new int[][]{{1, 2}};
        P2045_Solution solution = new P2045_Solution();
        System.out.println(solution.secondMinimum(5, edges, 3, 5)); // 13
        System.out.println(solution.secondMinimum(2, edges1, 3, 2)); // 11
    }
}

class P2045_Solution {
    // 超时, 需要加入类似记忆化的数组来解决
    public int secondMinimum1(int n, int[][] edges, int time, int change) {
        // 邻接表
        GraphNode[] adjList = new GraphNode[n + 1];
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            // 头插法构建邻接表
            adjList[u] = new GraphNode(v, adjList[u]);
            adjList[v] = new GraphNode(u, adjList[v]);
            /*
            // 等价于这三行
            GraphNode vNode = new GraphNode(v);
            vNode.next = adjList[u];
            adjList[u] = vNode;
             */
        }
        // printAdjList(n, adjList);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        // 到达 N 节点的时间, 第二短时间就是说此次到达 N 节点的时间比前一次到达 N 节点的大, 此时结束程序并返回
        // 当然, 为了比较, 需在做判断时得排除未到达前的情况, 可赋初值为 -1
        // 注意通过到达 N 节点的次数(第二次到达)来判断第二次到达时的时间是第二短的
        int arriveNNodeTime = -1;
        int curTime = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            // 到达 curNode 节点时的时间为 curTime
            // 如果此时该节点是绿色的(通过下面这个条件判断), 则可以不用等待, 立即花费 time 时间到达下一个节点 next
            if (curTime / change % 2 == 0) {
                curTime += time;
            } else {
                // 如果此时该节点是红色的, 则需要等待 (change - curTime % change) 分钟后
                // 才能从 curNode 节点立即花费 time 时间到达下一个节点 next
                curTime += change - curTime % change + time;
            }
            for (int i = 0; i < size; i++) {
                // 从当前节点 curNode 出发
                int curNode = queue.remove();
                // 到达当前节点 curNode 的相邻节点 next
                GraphNode next = adjList[curNode];
                while (next != null) {
                    // 如果 next 恰好为终点 N 节点
                    if (next.value == n) {
                        // 并且如果之前从未有节点到达过终点, 则此次是最短时间到达, 时间为 (当前轮 curTime)
                        // 在同一轮的出队中, 可能有多个节点也是以最短时间 (当前轮 curTime) 到达
                        if (arriveNNodeTime == -1) {
                            arriveNNodeTime = curTime;
                        } else if (curTime > arriveNNodeTime) {
                            // 当曾经有节点到达过终点时, 如果当前到达时间 curTime 比曾经到达该节点时的时间 arriveNNodeTime(!= -1) 大
                            // 那么此次到达时间 curTime 即为第二短时间, 即刻返回
                            return curTime;
                        }
                    }
                    // System.out.println(next.value);
                    queue.add(next.value);
                    next = next.next;
                }
            }
        }
        // System.out.println("应该是永远不会执行到这里");
        return arriveNNodeTime;
    }

    // 跟据官方解法的思想添加数组记录(与官方解法的记录值不同, 但是基本原理还是一样的) 终于不再超时, 并且最终时间超过 98%
    public int secondMinimum2(int n, int[][] edges, int time, int change) {
        // 邻接表
        GraphNode[] adjList = new GraphNode[n + 1];
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            // 头插法构建邻接表
            adjList[u] = new GraphNode(v, adjList[u]);
            adjList[v] = new GraphNode(u, adjList[v]);
            /*
            // 等价于这三行
            GraphNode vNode = new GraphNode(v);
            vNode.next = adjList[u];
            adjList[u] = vNode;
             */
        }
        // printAdjList(n, adjList);

        // arriveINodeTime[i][0] 为到达第 i 个节点的最短时间
        // arriveINodeTime[i][1] 为到达第 i 个节点的次最短时间
        int[][] arriveINodeTime = new int[n + 1][2];
        for (int[] arr : arriveINodeTime) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }
        // 初始化达到节点 1 时的最短时间为 0
        arriveINodeTime[1][0] = 0;

        // 到达 N 节点的时间, 第二短时间就是说此次到达 N 节点的时间比前一次到达 N 节点的大, 此时结束程序并返回
        // 当然, 为了比较, 需在做判断时得排除未到达前的情况, 可赋初值为 -1
        // 注意通过到达 N 节点的次数(第二次到达)来判断第二次到达时的时间是第二短的
        int arriveNNodeTime = -1;
        int curTime = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        while (!queue.isEmpty()) {
            int size = queue.size();
            // 到达 curNode 节点时的时间为 curTime
            // 如果此时该节点是绿色的(通过下面这个条件判断), 则可以不用等待, 立即花费 time 时间到达下一个节点 next
            if (curTime / change % 2 == 0) {
                curTime += time;
            } else {
                // 如果此时该节点是红色的, 则需要等待 (change - curTime % change) 分钟后
                // 才能从 curNode 节点立即花费 time 时间到达下一个节点 next
                curTime += change - curTime % change + time;
            }
            for (int i = 0; i < size; i++) {
                // 从当前节点 curNode 出发
                int curNode = queue.remove();
                // 到达当前节点 curNode 的相邻节点 next
                GraphNode next = adjList[curNode];
                while (next != null) {
                    /* 此段逻辑代码可删除, 通过 arriveINodeTime[n][1] 的值是否更新过来判断是否得到最短路径 */
                    // 如果 next 恰好为终点 N 节点
                    if (next.value == n) {
                        // 并且如果之前从未有节点到达过终点, 则此次是最短时间到达, 时间为 (当前轮 curTime)
                        // 在同一轮的出队中, 可能有多个节点也是以最短时间 (当前轮 curTime) 到达
                        if (arriveNNodeTime == -1) {
                            arriveNNodeTime = curTime;
                        } else if (curTime > arriveNNodeTime) {
                            // 当曾经有节点到达过终点时, 如果当前到达时间 curTime 比曾经到达该节点时的时间 arriveNNodeTime(!= -1) 大
                            // 那么此次到达时间 curTime 即为第二短时间, 即刻返回
                            return curTime;
                        }
                    }

                    // 当前次从 curNode 到达 next 节点的时间 curTime 与曾经历史到达 next 节点的最短时间相等时
                    // 以及大于曾经历史到达 next 节点的次最短时间时都不将该节点 next 再次入队
                    if (curTime < arriveINodeTime[next.value][0]) {
                        arriveINodeTime[next.value][0] = curTime;
                        queue.add(next.value);
                    } else if (curTime > arriveINodeTime[next.value][0] && curTime < arriveINodeTime[next.value][1]) {
                        arriveINodeTime[next.value][1] = curTime;
                        queue.add(next.value);
                    }

                    next = next.next;
                }
            }
        }
        // System.out.println("应该是永远不会执行到这里");
        return arriveNNodeTime;
    }

    // 广度优先搜索(队列实现) 29 ms 99.09%, 10.89%
    // 对到达终点的判断部分略微修改, 去掉使用 arriveNNodeTime 判断结束
    public int secondMinimum(int n, int[][] edges, int time, int change) {
        // 邻接表
        GraphNode[] adjList = new GraphNode[n + 1];
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            // 头插法构建邻接表
            adjList[u] = new GraphNode(v, adjList[u]);
            adjList[v] = new GraphNode(u, adjList[v]);
            /*
            // 等价于这三行
            GraphNode vNode = new GraphNode(v);
            vNode.next = adjList[u];
            adjList[u] = vNode;
             */
        }
        // printAdjList(n, adjList);

        // arriveINodeTime[i][0] 为到达第 i 个节点的最短时间
        // arriveINodeTime[i][1] 为到达第 i 个节点的次最短时间
        int[][] arriveINodeTime = new int[n + 1][2];
        for (int[] arr : arriveINodeTime) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }
        // 初始化达到节点 1 时的最短时间为 0
        arriveINodeTime[1][0] = 0;

        // 到达当前节点时的时间为 curTime
        int curTime = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        while (!queue.isEmpty()) {
            // 不能删除此行
            int size = queue.size();
            // 到达 curNode 节点时的时间为 curTime
            // 如果此时该节点是绿色的(通过下面这个条件判断), 则可以不用等待, 立即花费 time 时间到达下一个节点 next
            if (curTime / change % 2 == 0) {
                curTime += time;
            } else {
                // 如果此时该节点是红色的, 则需要等待 (change - curTime % change) 分钟后
                // 才能从 curNode 节点立即花费 time 时间到达下一个节点 next
                curTime += change - curTime % change + time;
            }
            for (int i = 0; i < size; i++) {
                // 从当前节点 curNode 出发
                int curNode = queue.remove();
                // 到达当前节点 curNode 的相邻节点 next
                GraphNode next = adjList[curNode];
                while (next != null) {
                    // 当前次从 curNode 到达 next 节点的时间 curTime 与曾经历史到达 next 节点的最短时间相等时
                    // 以及大于曾经历史到达 next 节点的次最短时间时都不将该节点 next 再次入队
                    if (curTime < arriveINodeTime[next.value][0]) {
                        arriveINodeTime[next.value][0] = curTime;
                        queue.add(next.value);
                    } else if (curTime > arriveINodeTime[next.value][0] && curTime < arriveINodeTime[next.value][1]) {
                        arriveINodeTime[next.value][1] = curTime;
                        queue.add(next.value);
                    }

                    next = next.next;

                    // 如果终点的第二短时间值刚刚被更新过, 说明得到了到达终点的第二最短路径(时间), 即刻返回
                    if (arriveINodeTime[n][1] != Integer.MAX_VALUE) {
                        return arriveINodeTime[n][1];
                    }
                }
            }
        }
        // System.out.println("应该是永远不会执行到这里");
        return arriveINodeTime[n][1];
    }

    // 打印
    private void printAdjList(int n, GraphNode[] adjList) {
        for (int i = 1; i <= n; i++) {
            GraphNode nextNode = adjList[i];
            System.out.print(i + " :");
            while (nextNode != null) {
                System.out.print(" " + nextNode.value);
                nextNode = nextNode.next;
            }
            System.out.println();
        }
    }
}

class GraphNode {

    // 节点编号
    int value;
    GraphNode next;

    public GraphNode(int value) {
        this.value = value;
    }

    public GraphNode(int value, GraphNode next) {
        this.value = value;
        this.next = next;
    }
}