package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-26 22:05
 */

/**
 * 2016. 增量元素之间的最大差值 maximum-difference-between-increasing-elements
 * 给你一个下标从 0 开始的整数数组 nums ，该数组的大小为 n ，请你计算 nums[j] - nums[i] 能求得的 最大差值 ，其中 0 <= i < j < n 且 nums[i] < nums[j] 。
 * <p>
 * 返回 最大差值 。如果不存在满足要求的 i 和 j ，返回 -1 。
 * <p>
 * n == nums.length
 * 2 <= n <= 1000
 * 1 <= nums[i] <= 10^9
 */
public class P2016_MaximumDifference {
    public static void main(String[] args) {
        int[] nums = new int[]{7, 1, 5, 4};
        int[] nums1 = new int[]{9, 4, 3, 2};
        int[] nums2 = new int[]{1, 5, 2, 10};
        P2016_Solution solution = new P2016_Solution();
        System.out.println(solution.maximumDifference(nums)); // 4
        System.out.println(solution.maximumDifference(nums1)); // -1
        System.out.println(solution.maximumDifference(nums2)); // 9
    }
}

// 基本操作 前缀最小值
// O(n) O(1)
// 0 ms 100.00%
// 40.6 MB 22.71%
// 40.9 MB 8.46%
class P2016_Solution {
    // 40.6 MB 22.71%
    public int maximumDifference1(int[] nums) {
        int n = nums.length;
        int minIndex = 0;
        int maxIndex = 0;
        int maxDiff = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
                continue;
            }
            if (nums[i] < nums[minIndex]) {
                maxDiff = Math.max(maxDiff, nums[maxIndex] - nums[minIndex]);
                minIndex = i;
                maxIndex = i;
            }
        }
        maxDiff = Math.max(maxDiff, nums[maxIndex] - nums[minIndex]);
        return maxDiff == 0 ? -1 : maxDiff;
    }

    // 40.9 MB 8.46%
    public int maximumDifference(int[] nums) {
        int n = nums.length;
        int min = nums[0];
        int max = nums[0];
        int maxDiff = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] > max) {
                max = nums[i];
                continue;
            }
            if (nums[i] < min) {
                maxDiff = Math.max(maxDiff, max - min);
                min = nums[i];
                max = nums[i];
            }
        }
        maxDiff = Math.max(maxDiff, max - min);
        // maxDiff == 0 表示 nums 是递减数组
        return maxDiff == 0 ? -1 : maxDiff;
    }
}