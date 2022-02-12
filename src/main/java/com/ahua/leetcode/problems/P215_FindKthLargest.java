package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-12 20:46
 */

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 215. 数组中的第K个最大元素 kth-largest-element-in-an-array
 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
 * <p>
 * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * <p>
 * 1 <= k <= nums.length <= 10^4
 * -10^4 <= nums[i] <= 10^4
 */
public class P215_FindKthLargest {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 1, 5, 6, 4};
        int[] nums1 = new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6};
        P215_Solution solution = new P215_Solution();
        System.out.println(solution.findKthLargest(nums, 2)); // 5
        System.out.println(solution.findKthLargest(nums1, 4)); // 4
    }
}

// 直接排序返回
// 时间复杂度 O(N log N)
class P215_Solution {
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }
}

// 基于堆排序的选择方法(优先队列实现)
class P215_Solution1 {
    // 小顶堆
    // 时间复杂度 O(N log k) 只记录 k 个
    // 空间复杂度 O(k)
    // 4ms
    public int findKthLargest1(int[] nums, int k) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int num : nums) {
            /* 4ms */
            if (priorityQueue.size() == k) {
                if (priorityQueue.peek() != null && priorityQueue.peek() < num) {
                    priorityQueue.remove();
                    priorityQueue.add(num);
                }
            } else {
                priorityQueue.add(num);
            }
            /* 5ms */
//            priorityQueue.add(num);
//            if(priorityQueue.size() > k) {
//                priorityQueue.remove();
//            }
        }
        return priorityQueue.remove();
    }

    // 大顶堆
    // 时间复杂度 O(N log N)
    // 空间复杂度 O(N)
    // 5ms
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> (o2 - o1));
        for (int num : nums) {
            priorityQueue.add(num);
        }
        int ans = 0;
        for (int i = 0; i < k; i++) {
            ans = priorityQueue.remove();
        }
        return ans;
    }
}
// 基于快速排序的选择方法