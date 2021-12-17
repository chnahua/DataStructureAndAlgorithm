package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-17 3:16
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 给你一个整数 n ，表示有 n 节课，课程编号从 1 到 n 。
 * 同时给你一个二维整数数组 relations ，其中 relations[j] = [prevCoursej, nextCoursej] ，
 * 表示课程 prevCoursej 必须在课程 nextCoursej 之前 完成（先修课的关系）。
 * <p>
 * 同时给你一个下标从 0 开始的整数数组 time，其中 time[i] 表示完成第 (i+1) 门课程需要花费的 月份 数。
 * <p>
 * 请你根据以下规则算出完成所有课程所需要的 最少 月份数：
 * <p>
 * 如果一门课的所有先修课都已经完成，你可以在 任意 时间开始这门课程。
 * 你可以 同时 上 任意门课程。
 * 请你返回完成所有课程所需要的 最少 月份数。
 * <p>
 * 注意：测试数据保证一定可以完成所有课程（也就是先修课的关系构成一个有向无环图）。
 * <p>
 * 1 <= n <= 5 * 10^4
 * 0 <= relations.length <= min(n * (n - 1) / 2, 5 * 104)
 * relations[j].length == 2
 * 1 <= prevCoursej, nextCoursej <= n
 * prevCoursej != nextCoursej
 * 所有的先修课程对 [prevCoursej, nextCoursej] 都是 互不相同 的。
 * time.length == n
 * 1 <= time[i] <= 104
 * 先修课程图是一个有向无环图。
 */

public class P2050_MinimumTime {
    public static void main(String[] args) {
        int[][] relations = new int[][]{{1, 3}, {2, 3}};
        int[] time = new int[]{3, 2, 5};

        int[][] relations1 = new int[][]{{1, 5}, {2, 5}, {3, 5}, {3, 4}, {4, 5}};
        int[] time1 = new int[]{1, 2, 3, 4, 5};

        int[][] relations2 = new int[][]{
                {2, 7}, {2, 6}, {3, 6}, {4, 6},
                {7, 6}, {2, 1}, {3, 1}, {4, 1},
                {6, 1}, {7, 1}, {3, 8}, {5, 8},
                {7, 8}, {1, 9}, {2, 9}, {6, 9}, {7, 9}
        };
        int[] time2 = new int[]{9, 5, 9, 5, 8, 7, 7, 8, 4};
        P2050_Solution solution = new P2050_Solution();
        System.out.println(solution.minimumTime(3, relations, time)); // 8
        System.out.println(solution.minimumTime(5, relations1, time1)); // 12
        System.out.println(solution.minimumTime(9, relations2, time2)); // 32
    }
}

class P2050_Solution1 {
    public int minimumTime(int n, int[][] relations, int[] time) {
        int[] pre = new int[n];
        for (int[] relation : relations) {
            pre[relation[1] - 1] |= 1 << (relation[0] - 1);
        }
        // 动态规划, 求出每种状态下的最少学习学期数
        // 总状态数 N, 状态可理解为现有 n 门课程, 每门课程有两种情况(可能已学习, 也可能未学习), 共计 2^n = 1 << n 种状态
        final int N = 1 << n;
        // dp[mask] 表示修完 mask 代表的所有课程的最少学习学期数
        // 动态规划就会遍历这 N 种状态, 得到每种状态下的最少学习学期数
        int[] dp = new int[N];
        // 对 dp 数组赋初值, 题目保证能全部修完, 初始化为最大值 n (或 >n 的数)即可
        // 因为不管有多少门课程, k(k>=1) 为多少, n 门课程最多不过 n 学期就能学完
        Arrays.fill(dp, Integer.MAX_VALUE);
        // 当然对于 dp[0], 即状态 mask == 0, 代表学习 0 门课程的最少学习学期数, 当然是 0, 其余的情况未知, 设为最大值 n
        // 假设为 4 门课程, 初始 0 门课需要 0 学期 dp[0000] = 0, 最终目标是求 dp[1111]
        dp[0] = 0;

        int[] cnt = new int[N];
        cnt[0] = 0;
        for (int i = 1; i < N; i++) {
            // 这个算法很奇妙, 没细看, 我也不懂
            cnt[i] = cnt[i >> 1] + (i & 1);
        }

        // 遍历这 2^n 种状态
        for (int mask = 0; mask < N; mask++) {
            if (dp[mask] >= Integer.MAX_VALUE) {
                continue;
            }
            // boolean valid = true;

            // 当前状态下可学习的所有课程集合(二进制表示)
            int canStudy = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) == 0 && (mask & pre[i]) == pre[i]) {
                    canStudy |= 1 << i;
                }
/*
                // 优化 1-1 是对前面 if 的优化, 当然也可以不使用此处优化, 而选择优化 1-2 也可以, 同时选择优化 1-1 和 1-2 与单选两者之一的效果是一样的
                // 如果 mask 中包含了这门课程 i, 说明已经学习了这门课程 i, 那么 mask 就一定要包含课程 i 的所有先修课程
                // 才能使得 mask 这种状态作为正确的中转的状态, 能够转移到其它的状态, 不然就是不可能状态
                if ((mask & (1 << i)) != 0) {
                    // mask 包含了课程 i, 但是不包含或者不完全包含课程 i 的所有先修课程, 这种状态就是不对的, 可以跳过
                    if ((mask & pre[i]) != pre[i]) {
                        // 也可以设置 dp[i] == n 或者 n + 1; 来退出循环
                        dp[mask] = Integer.MAX_VALUE;
                        // valid = false;
                        break;
                    }
                } else {
                    // 如果 mask 中不包含这门课程 i, 再根据是否包含这门课程 i 的所有先修课程, 决定是否能学习这门课程
                    // 包含所有先修课程, 则能学习; 反之, 则不能学习
                    if ((mask & pre[i]) == pre[i]) {
                        canStudy |= 1 << i;
                    }
                }*/
            }
//            // 如果 mask 是错误状态, 不可能存在的状态, 则直接进行下一次循环
//            if (!valid) {
//                continue;
//            }
            /*if (dp[mask] == Integer.MAX_VALUE) {
                continue;
            }*/
            for (int toStudy = canStudy; toStudy > 0; toStudy = (toStudy - 1) & canStudy) {
                // Integer.bitCount(study) : 二进制 toStudy 中的 1 的个数, 表示此子集代表的课程数量(假设为 t)
                // if (cnt[toStudy] <= k) {
                // if (Integer.bitCount(toStudy) <= k) {
                // if (bitCount(toStudy) <= k) {

                System.out.println("dp[" + mask + " | " + toStudy + "] : " + dp[mask | toStudy]);
                int newMask = mask | toStudy;
                // dp[mask | toStudy] = Math.min(dp[mask | toStudy], dp[mask] + 1);
                dp[newMask] = Math.min(dp[newMask], dp[mask] + getTime(time, toStudy));
                // cnt[0]++;
                System.out.println("dp[" + mask + " | " + toStudy + "] : " + dp[mask | toStudy]);
                // }
            }
        }
        // System.out.println(Arrays.toString(dp));
        // System.out.println("个数" + cnt[0]);
        // 最种要得到的是学完 n 门课程的最少学习学期数, 即在最后那种 n 位二进制的 n 个位置上的值都为 1 的那个状态
        return dp[N - 1];
    }

    // 第一种求二进制中 1 的个数的方法
    // 这个自己写的和源码的效率相差太大了吧
    // 使用源码是 94 ms
    // 使用这个是 886 ms
    private int bitCount(int a) {
        int count = 0;
        while (a != 0) {
            if ((a & 0x1) == 1) {
                count++;
            }
            a = a >> 1;
        }
        return count;
    }

    // 第二种求二进制中 1 的个数的方法 99ms
    private int getBit(int x) {
        int res = 0;
        while (x > 0) {
            res++;
            x = x & (x - 1);
        }
        return res;
    }

    private int getTime(int[] time, int a) {
        int times = 0;
        int i = 0;
        while (a > 0) {
            if ((a & 0x1) == 1) {
                times = Math.max(times, time[i]);
            }
            i++;
            a = a >> 1;
        }
        return times;
    }
}

// 拓扑排序(BFS 队列实现) + 动态规划
class P2050_Solution {
    public int minimumTime1(int n, int[][] relations, int[] time) {
        // 设置数组大小为 n + 1, 方便索引, 下标 0 不起作用
        // 邻接表
        P2050_CourseNode[] adjList = new P2050_CourseNode[n + 1];
        // 各个节点的入度, 每门课程的先修课程的数目
        int[] inDegree = new int[n + 1];
        // 该节点是否有出度, 是否是最后一门才能学习的课程, 或者是无入度无出度的可随时学习的课程
        boolean[] outDegree = new boolean[n + 1];
        // dp 数组, 记录到达各个节点(要学完此课程)所需的最少月份数
        int[] dp = new int[n + 1];
        // 学完所有课程所需要的 最少 月份数
        int minTime = 0;
        // 构造邻接表
        int from, to;
        for (int[] edge : relations) {
            // 有向边起点
            from = edge[0];
            // 有向边终点
            to = edge[1];
            // 头插法构造邻接表, adjList[from] 存储的是所有以 from 课程为先修课程的课程组成的链表
            adjList[from] = new P2050_CourseNode(to, adjList[from]);

            // 终点入度加 1
            inDegree[to]++;
            // from 有后修课程, 置为 true
            outDegree[from] = true;
        }

        // 将入度为 0 的各节点入队, 并更新初始学完这些入度为 0 (无先修课程)的课程的所需的时间就为 time[i - 1]
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
                dp[i] = time[i - 1];
            }
        }

        // 有向无环图
        int cur;
        P2050_CourseNode next;
        while (!queue.isEmpty()) {
            // 此次学习的课程
            cur = queue.poll();
            // 以 cur 课程为先修课程的一门课程 next
            next = adjList[cur];

            while (next != null) {
                // 入度减 1, 表示又学习了 next 的一门先修课程
                inDegree[next.value]--;
                // 当 next 入度减到 0 时, 说明 next 的所有的先修课程都学习完毕(刚学习完的 cur 课程就是它的最后一门先修课程)
                if (inDegree[next.value] == 0) {
                    // 将该 next 课程加入到队列中, 后续就可以学习该课程了
                    queue.add(next.value);
                }

                // 学习完课程 cur 至少需要的月份数为 dp[cur], 此次如果要学习课程 next, 需要的月份数为 dp[cur] + time[next.value - 1]
                // 课程 next 的先修课程课程有多个, 但是在学习 next 之前已经学过了, 它的 dp 值已经确定
                // 那么学完课程 next 所需的最少时间, 就是历次学完它的所有先修课程所花费的时间中的最大值 + 学习课程 next 所需要花费的时间 time[next.value - 1]
                dp[next.value] = Math.max(dp[next.value], dp[cur] + time[next.value - 1]);
                // 继续判断学习完 cur 课程后, 以 cur 课程为先修课程的下一门课程是否可以学习
                next = next.next;
            }
        }
        // 找到所有无出度(出度为 0 )的节点, 即终点, 比较到达终点的所有花费时间中的最大值就是学完所有课程花费的最少时间
        for (int i = 1; i <= n; i++) {
            if (!outDegree[i]) {
                minTime = Math.max(minTime, dp[i]);
            }
        }
        return minTime;
    }

    // 小优化
    public int minimumTime(int n, int[][] relations, int[] time) {
        // 设置数组大小为 n + 1, 方便索引, 下标 0 不起作用
        // 邻接表
        P2050_CourseNode[] adjList = new P2050_CourseNode[n + 1];
        // 各个节点的入度, 每门课程的先修课程的数目
        int[] inDegree = new int[n + 1];
        // 该节点是否有出度, 是否是最后一门才能学习的课程, 或者是无入度无出度的可随时学习的课程
        // boolean[] outDegree = new boolean[n + 1];
        // dp 数组, 记录到达各个节点(要学完此课程)所需的最少月份数
        int[] dp = new int[n + 1];
        // 学完所有课程所需要的 最少 月份数
        int minTime = 0;
        // 构造邻接表
        int from, to;
        for (int[] edge : relations) {
            // 有向边起点
            from = edge[0];
            // 有向边终点
            to = edge[1];
            // 头插法构造邻接表, adjList[from] 存储的是所有以 from 课程为先修课程的课程组成的链表
            adjList[from] = new P2050_CourseNode(to, adjList[from]);

            // 终点入度加 1
            inDegree[to]++;
            // from 有后修课程, 置为 true
            // outDegree[from] = true;
        }

        // 将入度为 0 的各节点入队, 并更新初始学完这些入度为 0 (无先修课程)的课程的所需的时间就为 time[i - 1]
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
                dp[i] = time[i - 1];
            }
        }

        // 有向无环图
        int cur;
        P2050_CourseNode next;
        while (!queue.isEmpty()) {
            // 此次学习的课程
            cur = queue.poll();
            // 以 cur 课程为先修课程的一门课程 next
            next = adjList[cur];

            // 不使用 outDegree
            // next == null, 说明 cur 课程不是任何课程的先修课程,
            // 也即 cur 课程随时可以学习或者 cur 的所有先修课程已经学习完毕并且没有以 cur 课程为先修课程的课程, 此拓扑排序到达了终点
            // 学完所有课程所需的时间就是学完这几门课程时, 它们所需时间的最大值, 所有 dp[cur] 中的最大值, cur 必须满足无后继学习课程(不是任何课程的先修课程)
            // 这样的课程数目, 也就是 minTime 比较次数就是所有课程组成的有向无环图的个数
            if (next == null) {
                minTime = Math.max(minTime, dp[cur]);
                continue;
            }

            while (next != null) {
                // 入度减 1, 表示又学习了 next 的一门先修课程
                inDegree[next.value]--;
                // 当 next 入度减到 0 时, 说明 next 的所有的先修课程都学习完毕(刚学习完的 cur 课程就是它的最后一门先修课程)
                if (inDegree[next.value] == 0) {
                    // 将该 next 课程加入到队列中, 后续就可以学习该课程了
                    queue.add(next.value);
                }

                // 学习完课程 cur 至少需要的月份数为 dp[cur], 此次如果要学习课程 next, 需要的月份数为 dp[cur] + time[next.value - 1]
                // 课程 next 的先修课程课程有多个, 但是在学习 next 之前已经学过了, 它的 dp 值已经确定
                // 那么学完课程 next 所需的最少时间, 就是历次学完它的所有先修课程所花费的时间中的最大值 + 学习课程 next 所需要花费的时间 time[next.value - 1]
                dp[next.value] = Math.max(dp[next.value], dp[cur] + time[next.value - 1]);
                // 继续判断学习完 cur 课程后, 以 cur 课程为先修课程的下一门课程是否可以学习
                next = next.next;
            }
        }
        /* // 找到所有无出度(出度为 0 )的节点, 即终点, 比较到达终点的所有花费时间中的最大值就是学完所有课程花费的最少时间
        for (int i = 1; i <= n; i++) {
            if (!outDegree[i]) {
                minTime = Math.max(minTime, dp[i]);
            }
        }*/
        return minTime;
    }
}

class P2050_CourseNode {
    // value 为课程编号
    int value;
    P2050_CourseNode next;

    public P2050_CourseNode(int value) {
        this.value = value;
    }

    public P2050_CourseNode(int value, P2050_CourseNode next) {
        this.value = value;
        this.next = next;
    }
}