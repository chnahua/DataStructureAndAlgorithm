package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-09 20:19
 */

import java.util.*;

/**
 * 347. 前 K 个高频元素 top-k-frequent-elements
 * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
 * <p>
 * 1 <= nums.length <= 10^5
 * k 的取值范围是 [1, 数组中不相同的元素的个数]
 * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的
 * <p>
 * 进阶：你所设计算法的时间复杂度 必须 优于 O(n log n) ，其中 n 是数组大小。
 */
public class P347_TopKFrequent {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 1, 2, 2, 3};
        int[] nums1 = new int[]{1};
        P347_Solution solution = new P347_Solution();
        System.out.println(Arrays.toString(solution.topKFrequent(nums, 2)));
        System.out.println(Arrays.toString(solution.topKFrequent(nums1, 1)));
    }
}

// 大小顶堆(优先队列实现)
class P347_Solution {
    // 时间复杂度 O(N log N)
    // 11 ms 94.34%
    // 大顶堆
    public int[] topKFrequent2(int[] nums, int k) {
        HashMap<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        Set<Map.Entry<Integer, Integer>> entrySet = countMap.entrySet();
        // 小顶堆 只保留 k 个
        PriorityQueue<Map.Entry<Integer, Integer>> priorityQueue = new PriorityQueue<>((o1, o2) -> (o1.getValue() - o2.getValue()));
        // 限制优先队列的大小为 k, 这样的话就是 O(N log k) 的时间复杂度了
        for (Map.Entry<Integer, Integer> entry : entrySet) {
//            /* 1. 每次先添加, 再移除 15 ms 22.64% */
//            priorityQueue.offer(entry);
//            // 移除出现次数较小的, 剩下的就是出现次数较大的了
//            if (priorityQueue.size() > k) {
//                priorityQueue.remove();
//            }

            /* 2. 每次先判断是否添加, 也即先移除, 再添加 13 ms 61.93% */
            if (priorityQueue.size() == k) {
                // 移除出现次数较小的, 剩下的就是出现次数较大的了
                if (priorityQueue.peek().getValue() < entry.getValue()) {
                    priorityQueue.remove();
                    priorityQueue.offer(entry);
                }
            } else {
                priorityQueue.offer(entry);
            }
        }
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = priorityQueue.remove().getKey();
        }
        return ans;
    }

    // 时间复杂度 O(N log k)
    // 13 ms 61.93%
    // 小顶堆
    public int[] topKFrequent1(int[] nums, int k) {
        HashMap<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        Set<Map.Entry<Integer, Integer>> entrySet = countMap.entrySet();
        // 大顶堆 保留 n 个
        PriorityQueue<Map.Entry<Integer, Integer>> priorityQueue = new PriorityQueue<>((o1, o2) -> (o2.getValue() - o1.getValue()));
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            priorityQueue.offer(entry);
        }
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = priorityQueue.remove().getKey();
        }
        return ans;
    }

    // 时间复杂度 O(N log k)
    // 12 ms 89.90%
    // 优先队列中保存的元素不是 Map.Entry<Integer, Integer> 类型
    // 而是 int[]{num, count}
    // 小顶堆
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        Set<Map.Entry<Integer, Integer>> entrySet = countMap.entrySet();
        // 小顶堆 只保留 k 个
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> (o1[1] - o2[1]));
        // 限制优先队列的大小为 k, 这样的话就是 O(N log k) 的时间复杂度了
        for (Map.Entry<Integer, Integer> entry : entrySet) {
//            /* 1. 每次先添加, 再移除 14 ms 37.32% */
//            priorityQueue.offer(new int[]{entry.getKey(), entry.getValue()});
//            // 移除出现次数较小的, 剩下的就是出现次数较大的了
//            if (priorityQueue.size() > k) {
//                priorityQueue.remove();
//            }

            /* 2. 每次先判断是否添加, 也即先移除, 再添加 12 ms 89.90% */
            if (priorityQueue.size() == k) {
                // 移除出现次数较小的, 剩下的就是出现次数较大的了
                assert priorityQueue.peek() != null;
                if (priorityQueue.peek()[1] < entry.getValue()) {
                    priorityQueue.remove();
                    priorityQueue.offer(new int[]{entry.getKey(), entry.getValue()});
                }
            } else {
                priorityQueue.offer(new int[]{entry.getKey(), entry.getValue()});
            }
        }
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = priorityQueue.remove()[0];
        }
        return ans;
    }
}