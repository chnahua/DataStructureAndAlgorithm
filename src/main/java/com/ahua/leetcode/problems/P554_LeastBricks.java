package com.ahua.leetcode.problems;

import java.util.*;

/**
 * @author huajun
 * @create 2022-03-02 21:33
 */

/**
 * 554. 砖墙 brick-wall
 * 你的面前有一堵矩形的、由 n 行砖块组成的砖墙。
 * 这些砖块高度相同（也就是一个单位高）但是宽度不同。每一行砖块的宽度之和相等。
 * <p>
 * 你现在要画一条 自顶向下 的、穿过 最少 砖块的垂线。如果你画的线只是从砖块的边缘经过，就不算穿过这块砖。
 * 你不能沿着墙的两个垂直边缘之一画线，这样显然是没有穿过一块砖的。
 * <p>
 * 给你一个二维数组 wall ，该数组包含这堵墙的相关信息。其中，wall[i] 是一个代表从左至右每块砖的宽度的数组。
 * 你需要找出怎样画才能使这条线 穿过的砖块数量最少 ，并且返回 穿过的砖块数量 。
 * <p>
 * n == wall.length
 * 1 <= n <= 10^4
 * 1 <= wall[i].length <= 10^4
 * 1 <= sum(wall[i].length) <= 2 * 10^4
 * 对于每一行 i ，sum(wall[i]) 是相同的
 * 1 <= wall[i][j] <= 2^31 - 1
 */
public class P554_LeastBricks {
    public static void main(String[] args) {
        // [[1,2,2,1],[3,1,2],[1,3,2],[2,4],[3,1,2],[1,3,1,1]]
        List<List<Integer>> wall = new ArrayList<>();
        wall.add(Arrays.asList(1, 2, 2, 1));
        wall.add(Arrays.asList(3, 1, 2));
        wall.add(Arrays.asList(1, 3, 2));
        wall.add(Arrays.asList(2, 4));
        wall.add(Arrays.asList(3, 1, 2));
        wall.add(Arrays.asList(1, 3, 1, 1));
        List<List<Integer>> wall1 = new ArrayList<>();
        wall1.add(Arrays.asList(1));
        wall1.add(Arrays.asList(1));
        wall1.add(Arrays.asList(1));

        P554_Solution solution = new P554_Solution();
        System.out.println(solution.leastBricks(wall)); // 2
        System.out.println(solution.leastBricks(wall1)); // 3
    }
}

// 哈希表
// 和官解不谋而合
// 问题可以转换成求「垂线穿过的砖块边缘数量的最大值」，用砖墙的高度减去该最大值即为答案
class P554_Solution {
    // 17 ms 12.11%
    // 44.9 MB 15.06%
    public int leastBricks1(List<List<Integer>> wall) {
        Map<Integer, Integer> hashMap = new HashMap<>();
        int n = wall.size();
        for (List<Integer> list : wall) {
            int edgeIndex = 0;
            for (Integer len : list) {
                edgeIndex += len;
                hashMap.put(edgeIndex, hashMap.getOrDefault(edgeIndex, 0) + 1);
            }
        }
        int totalLen = 0;
        for (Integer integer : wall.get(0)) {
            totalLen += integer;
        }
        if (hashMap.size() == 1) {
            return hashMap.get(totalLen);
        }
        hashMap.remove(totalLen);
        PriorityQueue<Map.Entry<Integer, Integer>> priorityQueue = new PriorityQueue<>((o1, o2) -> o2.getValue() - o1.getValue());
        priorityQueue.addAll(hashMap.entrySet());
        return n - priorityQueue.peek().getValue();
    }

    // 14 ms 37.54%
    // 43.6 MB 34.61%
    public int leastBricks2(List<List<Integer>> wall) {
        Map<Integer, Integer> hashMap = new HashMap<>();
        int n = wall.size();
        for (List<Integer> list : wall) {
            int edgeIndex = 0;
            int wallCount = list.size();
            for (int i = 0; i < wallCount - 1; i++) {
                edgeIndex += list.get(i);
                hashMap.put(edgeIndex, hashMap.getOrDefault(edgeIndex, 0) + 1);
            }
        }
        if (hashMap.isEmpty()) {
            return n;
        }
        PriorityQueue<Map.Entry<Integer, Integer>> priorityQueue = new PriorityQueue<>((o1, o2) -> o2.getValue() - o1.getValue());
        priorityQueue.addAll(hashMap.entrySet());
        return n - priorityQueue.peek().getValue();
    }

    // 13 ms 51.73%
    // 44.7 MB 21.98%
    public int leastBricks(List<List<Integer>> wall) {
        Map<Integer, Integer> hashMap = new HashMap<>();
        int n = wall.size();
        for (List<Integer> list : wall) {
            int edgeIndex = 0;
            int wallCount = list.size();
            for (int i = 0; i < wallCount - 1; i++) {
                edgeIndex += list.get(i);
                hashMap.put(edgeIndex, hashMap.getOrDefault(edgeIndex, 0) + 1);
            }
        }
        if (hashMap.isEmpty()) {
            return n;
        }
        return n - Collections.max(hashMap.values());
    }
}