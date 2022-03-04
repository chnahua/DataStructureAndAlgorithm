package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-04 22:44
 */

/**
 * 2104. 子数组范围和 sum-of-subarray-ranges
 * 给你一个整数数组 nums 。nums 中，子数组的 范围 是子数组中最大元素和最小元素的差值。
 *
 * 返回 nums 中 所有 子数组范围的 和 。
 *
 * 子数组是数组中一个连续 非空 的元素序列。
 *
 * 1 <= nums.length <= 1000
 * -109 <= nums[i] <= 10^9
 *
 * 进阶：你可以设计一种时间复杂度为 O(n) 的解决方案吗？
 */
public class P2104_SubArrayRanges {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        int[] nums1 = new int[]{1, 3, 3};
        int[] nums2 = new int[]{4, -2, -3, 4, 1};
        P2104_Solution solution = new P2104_Solution();
        System.out.println(solution.subArrayRanges(nums)); // 0 + 0 + 0 + 1 + 1 + 2 = 4
        System.out.println(solution.subArrayRanges(nums1)); // 0 + 0 + 0 + 2 + 0 + 2 = 4
        System.out.println(solution.subArrayRanges(nums2)); // 59
    }
}

// 暴力解法、遍历子数组
// O(N^2) O(1)
// 17 ms 92.41%
// 41.1 MB 17.84%
class P2104_Solution {
    public long subArrayRanges(int[] nums) {
        int n = nums.length;
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int minVal = Integer.MAX_VALUE;
            int maxVal = Integer.MIN_VALUE;
            for (int j = i; j < n; j++) {
                minVal = Math.min(minVal, nums[j]);
                maxVal = Math.max(maxVal, nums[j]);
                ans += maxVal - minVal;
            }
        }
        return ans;
    }
}

// 单调栈
