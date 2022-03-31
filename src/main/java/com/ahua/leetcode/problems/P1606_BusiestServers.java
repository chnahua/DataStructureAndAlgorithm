package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-31 0:13
 */

import java.util.*;

/**
 * 1606. 找到处理最多请求的服务器 find-servers-that-handled-most-number-of-requests
 * 你有 k 个服务器，编号为 0 到 k-1 ，它们可以同时处理多个请求组。
 * 每个服务器有无穷的计算能力但是 不能同时处理超过一个请求 。请求分配到服务器的规则如下：
 * <p>
 * 第 i （序号从 0 开始）个请求到达。
 * 如果所有服务器都已被占据，那么该请求被舍弃（完全不处理）。
 * 如果第 (i % k) 个服务器空闲，那么对应服务器会处理该请求。
 * 否则，将请求安排给下一个空闲的服务器（服务器构成一个环，必要的话可能从第 0 个服务器开始继续找下一个空闲的服务器）。
 * 比方说，如果第 i 个服务器在忙，那么会查看第 (i+1) 个服务器，第 (i+2) 个服务器等等。
 * 给你一个 严格递增 的正整数数组 arrival ，表示第 i 个任务的到达时间，
 * 和另一个数组 load ，其中 load[i] 表示第 i 个请求的工作量（也就是服务器完成它所需要的时间）。
 * 你的任务是找到 最繁忙的服务器 。最繁忙定义为一个服务器处理的请求数是所有服务器里最多的。
 * <p>
 * 请你返回包含所有 最繁忙服务器 序号的列表，你可以以任意顺序返回这个列表。
 * <p>
 * 1 <= k <= 10^5
 * 1 <= arrival.length, load.length <= 10^5
 * arrival.length == load.length
 * 1 <= arrival[i], load[i] <= 10^9
 * arrival 保证 严格递增 。
 */
public class P1606_BusiestServers {
    public static void main(String[] args) {
        int[] arrival = new int[]{1, 2, 3, 4, 5};
        int[] load = new int[]{5, 2, 3, 3, 3};

        int[] arrival1 = new int[]{1, 2, 3, 4};
        int[] load1 = new int[]{1, 2, 1, 2};

        int[] arrival2 = new int[]{1, 2, 3};
        int[] load2 = new int[]{10, 12, 11};

        int[] arrival3 = new int[]{1, 2, 3, 4, 8, 9, 10};
        int[] load3 = new int[]{5, 2, 10, 3, 1, 2, 2};

        int[] arrival4 = new int[]{1};
        int[] load4 = new int[]{1};

        P1606_Solution solution = new P1606_Solution();
        System.out.println(solution.busiestServers(3, arrival, load)); // [1]
        System.out.println(solution.busiestServers(3, arrival1, load1)); // [0]
        System.out.println(solution.busiestServers(3, arrival2, load2)); // [0,1,2]
        System.out.println(solution.busiestServers(3, arrival3, load3)); // [1]
        System.out.println(solution.busiestServers(1, arrival4, load4)); // [0]
    }
}

// 我的解法 超出时间限制
// O(N^2)
// 看了官方的一部分解题思路, 发现有个条件我没有用到, 就是 arrival[] 是递增的
// 我采用的数据结构是数组, 使用一个数组保存各个服务器处理请求的结束时间,
// 但是这样的话, 当某一个请求达到时, 去找到处理它的服务器就需要 O(N) 的时间, 因此整体时间复杂度就会是 O(N^2)
// 那怎样才能在尽量短的时间里找到一个处理一个请求的空闲服务器呢?
class P1606_Solution1 {
    // 超出时间限制
    public List<Integer> busiestServers1(int k, int[] arrival, int[] load) {
        // 每个服务器处理请求的结束时间
        int[] serverRequestEndTime = new int[k];
        // 每个服务器处理请求的次数
        int[] serverRequestCount = new int[k];
        // 服务器处理请求的最大次数
        int maxRequestCount = 0;
        int n = arrival.length;
        for (int i = 0; i < n; i++) {
            int serverNum = i % k;
            // loop 表示该请求尝试服务器处理的次数
            int loop = 1;
            while (loop <= k) {
                serverNum = serverNum % k;
                // 该请求到达时, 此服务器处于空闲状态, 则该服务器处理
                if (arrival[i] >= serverRequestEndTime[serverNum]) {
                    serverRequestEndTime[serverNum] = arrival[i] + load[i];
                    serverRequestCount[serverNum]++;
                    // 更新服务器处理的请求最多的次数
                    maxRequestCount = Math.max(maxRequestCount, serverRequestCount[serverNum]);
                    break;
                }
                // 查看下一台服务器
                serverNum++;
                // 尝试服务器次数加 1
                loop++;
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (serverRequestCount[i] == maxRequestCount) {
                ans.add(i);
            }
        }
        return ans;
    }

    // 超出时间限制
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        // 每个服务器处理请求的结束时间
        int[] serverRequestEndTime = new int[k];
        // 每个服务器处理请求的次数
        int[] serverRequestCount = new int[k];
        // 服务器处理请求的最大次数
        int maxRequestCount = 0;
        List<Integer> ans = new ArrayList<>();
        int n = arrival.length;
        for (int i = 0; i < n; i++) {
            int serverNum = i % k;
            // loop 表示该请求尝试服务器处理的次数
            int loop = 1;
            while (loop <= k) {
                serverNum = serverNum % k;
                // 该请求到达时, 此服务器处于空闲状态, 则该服务器处理
                if (arrival[i] >= serverRequestEndTime[serverNum]) {
                    serverRequestEndTime[serverNum] = arrival[i] + load[i];
                    serverRequestCount[serverNum]++;
                    // 更新服务器处理的请求最多的次数以及处理最多请求次数的服务器的编号集合
                    if (maxRequestCount == serverRequestCount[serverNum]) {
                        ans.add(serverNum);
                    } else if (maxRequestCount < serverRequestCount[serverNum]) {
                        ans.clear();
                        ans.add(serverNum);
                        maxRequestCount = serverRequestCount[serverNum];
                    }
                    break;
                }
                // 尝试下一台服务器
                serverNum++;
                // 尝试服务器次数加 1
                loop++;
            }
        }
        return ans;
    }
}

// 有序集合 + 优先队列
// 124 ms 36.00%
// 62 MB 5.20%
// 将所有的服务器分为两个部分, 空闲的(未处理请求的 available 表示)和繁忙的(正在处理请求的 busy 表示)
// 使用 Set 存储空闲的服务器编号(按编号排序), 使用 优先队列 存储繁忙的服务器编号及其结束时刻(按时间排序)
// 由于 arrival[] 是递增的, 每当请求到来时, 就可判断历史繁忙中的服务器, 此刻是否还繁忙,
// 如果此刻空闲了就将其从 busy 中移除, 并将服务器编号添加到 available 中
// 在查找处理该请求的服务器时, 就不用一一遍历了, 而是只需要在 available 中 O(1) 找到最接近 i % k 的服务器编号就行了
class P1606_Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        // 空闲的服务器集合
        TreeSet<Integer> available = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            available.add(i);
        }
        // 繁忙的服务器集合
        PriorityQueue<int[]> busy = new PriorityQueue<>(((o1, o2) -> (o1[0] - o2[0])));
        // 每个服务器处理请求的次数
        int[] serverRequestCount = new int[k];
        // 服务器处理请求的最大次数
        int maxRequestCount = 0;
        List<Integer> ans = new ArrayList<>();
        int n = arrival.length;
        for (int i = 0; i < n; i++) {
            // 更新空闲服务器集合与繁忙服务器集合
            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {
                int[] serverRequestEndTimeAndNumber = busy.remove();
                available.add(serverRequestEndTimeAndNumber[1]);
            }
            // 查找处理该请求的空闲服务器
            if (!available.isEmpty()) {
                Integer serverNum = available.ceiling(i % k);
                if (serverNum == null) {
                    // serverNum = available.first(); 也对
                    serverNum = available.ceiling(0);
                }
                // 更新空闲服务器集合与繁忙服务器集合
                available.remove(serverNum);
                busy.add(new int[]{arrival[i] + load[i], serverNum});
                // 该服务器处理请求次数加 1
                serverRequestCount[serverNum]++;

                // 更新服务器处理的请求最多的次数以及处理最多请求次数的服务器的编号集合
                if (maxRequestCount == serverRequestCount[serverNum]) {
                    ans.add(serverNum);
                } else if (maxRequestCount < serverRequestCount[serverNum]) {
                    ans.clear();
                    ans.add(serverNum);
                    maxRequestCount = serverRequestCount[serverNum];
                }
            }
        }
        return ans;
    }
}