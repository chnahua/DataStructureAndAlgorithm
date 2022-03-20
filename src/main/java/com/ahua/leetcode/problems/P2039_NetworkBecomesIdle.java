package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-20 22:19
 */

import java.util.*;

/**
 * 2039. 网络空闲的时刻 the-time-when-the-network-becomes-idle
 * 给你一个有 n 个服务器的计算机网络，服务器编号为 0 到 n - 1 。
 * 同时给你一个二维整数数组 edges ，其中 edges[i] = [ui, vi] 表示服务器 ui 和 vi 之间有一条信息线路，
 * 在 一秒 内它们之间可以传输 任意 数目的信息。再给你一个长度为 n 且下标从 0 开始的整数数组 patience 。
 * <p>
 * 题目保证所有服务器都是 相通 的，也就是说一个信息从任意服务器出发，都可以通过这些信息线路直接或间接地到达任何其他服务器。
 * <p>
 * 编号为 0 的服务器是 主 服务器，其他服务器为 数据 服务器。每个数据服务器都要向主服务器发送信息，并等待回复。
 * 信息在服务器之间按 最优 线路传输，也就是说每个信息都会以 最少时间 到达主服务器。
 * 主服务器会处理 所有 新到达的信息并 立即 按照每条信息来时的路线 反方向 发送回复信息。
 * <p>
 * 在 0 秒的开始，所有数据服务器都会发送各自需要处理的信息。
 * 从第 1 秒开始，每 一秒最 开始 时，每个数据服务器都会检查它是否收到了主服务器的回复信息（包括新发出信息的回复信息）：
 * <p>
 * 如果还没收到任何回复信息，那么该服务器会周期性 重发 信息。数据服务器 i 每 patience[i] 秒都会重发一条信息，
 * 也就是说，数据服务器 i 在上一次发送信息给主服务器后的 patience[i] 秒 后 会重发一条信息给主服务器。
 * 否则，该数据服务器 不会重发 信息。
 * 当没有任何信息在线路上传输或者到达某服务器时，该计算机网络变为 空闲 状态。
 * <p>
 * 请返回计算机网络变为 空闲 状态的 最早秒数 。
 * <p>
 * n == patience.length
 * 2 <= n <= 10^5
 * patience[0] == 0
 * 对于 1 <= i < n ，满足 1 <= patience[i] <= 10^5
 * 1 <= edges.length <= min(10^5, n * (n - 1) / 2)
 * edges[i].length == 2
 * 0 <= ui, vi < n
 * ui != vi
 * 不会有重边。
 * 每个服务器都直接或间接与别的服务器相连。
 */
public class P2039_NetworkBecomesIdle {
    public static void main(String[] args) {
        int[][] edges = new int[][]{{0, 1}, {1, 2}};
        int[] patience = new int[]{0, 2, 1};
        int[][] edges1 = new int[][]{{0, 1}, {0, 2}, {1, 2}};
        int[] patience1 = new int[]{0, 10, 10};
        int[][] edges2 = new int[][]{{0, 5}, {3, 7}, {2, 6}, {10, 12}, {11, 1}, {9, 3}, {0, 4},
                {5, 9}, {7, 8}, {4, 10}, {8, 2}, {1, 6}, {12, 11}};
        int[] patience2 = new int[]{0, 1, 7, 3, 6, 3, 6, 1, 1, 4, 3, 2, 1};

        P2039_Solution solution = new P2039_Solution();
        System.out.println(solution.networkBecomesIdle(edges, patience)); // 8
        System.out.println(solution.networkBecomesIdle(edges1, patience1)); // 3
        System.out.println(solution.networkBecomesIdle(edges2, patience2)); // 20
    }
}

// 无向图 广度优先搜索(队列实现)
// 66 ms 85.71%
// 82.4 MB 98.41%
class P2039_Solution {
    public int networkBecomesIdle(int[][] edges, int[] patience) {
        // 总服务器数
        int n = patience.length;
        List<List<Integer>> adjList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            // 与 u 相邻的结点 v
            adjList.get(u).add(v);
            // 与 v 相邻的结点 u
            adjList.get(v).add(u);
        }
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        queue.add(0);
        visited[0] = true;
        int distance = 1;
        int ans = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Integer cur = queue.remove();
                List<Integer> curList = adjList.get(cur);
                for (Integer next : curList) {
                    // 该结点的距离 0 结点的最短距离已经计算过, 跳过
                    if (visited[next]) {
                        continue;
                    }
                    queue.add(next);
                    // 第一种写法
                    /*
                    int time;
                    if (patience[next] >= 2 * distance) {
                        time = 2 * distance + 1;
                    } else {
                        // 第一种理解
                        // 第一条信息的回复信息到达该发送数据服务器时, 此服务器发出的最后一条信息已经发出了多少时间
                        // int lastMes = distance * 2 % patience[next] == 0 ? patience[next] : distance * 2 % patience[next];
                        // time = 4 * distance - lastMes + 1;

                        // 第二种理解
                        // 第一条信息的回复信息到达该发送数据服务器时, 此服务器发出的最后一条信息的回复信息还要多久才能返回到该数据服务器
                        int replyLastMesTime = 2 * distance - (distance * 2 % patience[next] == 0 ? patience[next] : distance * 2 % patience[next]);
                        time = 2 * distance + replyLastMesTime + 1;
                    }
                    */
                    // 第二种写法, 由第一种总结而来
                    // 初始化为 第一条信息的回复信息的返回时间
                    int time = 2 * distance + 1;
                    // 满足此条件说明, 在回复信息返回前, 还发送了其它信息
                    if (patience[next] < 2 * distance) {
                        // 故还需要加上最后一条信息的返回信息的返回时间
                        // 第一条信息的回复信息到达该发送数据服务器时, 此服务器发出的最后一条信息的回复信息还要多久才能返回到该数据服务器
                        time += 2 * distance - (distance * 2 % patience[next] == 0 ? patience[next] : distance * 2 % patience[next]);
                    }
                    ans = Math.max(ans, time);
                    visited[next] = true;
                }
            }
            distance++;
        }
        return ans;
    }
}