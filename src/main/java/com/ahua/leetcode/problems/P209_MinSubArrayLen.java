package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-20 15:32
 */

/**
 * 209. 长度最小的子数组 minimum-size-subarray-sum
 * 给定一个含有 n 个正整数的数组和一个正整数 target 。
 * <p>
 * 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组
 * [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
 * <p>
 * 1 <= target <= 10^9
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^5
 * <p>
 * 如果你已经实现 O(n) 时间复杂度的解法, 请尝试设计一个 O(n log(n)) 时间复杂度的解法。
 */
public class P209_MinSubArrayLen {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 1, 2, 4, 3};
        int[] nums1 = new int[]{1, 4, 4};
        int[] nums2 = new int[]{1, 1, 1, 1, 1, 1, 1, 1};
        int[] nums3 = new int[]{1, 2, 3, 4, 5};
        P209_Solution solution = new P209_Solution();
        System.out.println(solution.minSubArrayLen(7, nums)); // 2
        System.out.println(solution.minSubArrayLen(4, nums1)); // 1
        System.out.println(solution.minSubArrayLen(11, nums2)); // 0
        System.out.println(solution.minSubArrayLen(15, nums3)); // 5
    }
}

// 滑动窗口
// O(N) O(1)
// 1 ms 99.99%
// 41.1 MB 16.71%
class P209_Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int subArrSum = 0;
        int minSubArrLen = n + 1;
        // 子数组和 subArrSum >= target 的子数组的开始位置
        int start = 0;
        for (int end = 0; end < n; end++) {
            if (nums[end] >= target) {
                return 1;
            }
            subArrSum += nums[end];
            // 直到子数组和 subArrSum < target, start 为开始位置的后一个位置
            // 第一种写法
            /*
            while (subArrSum >= target) {
                minSubArrLen = Math.min(minSubArrLen, end - start + 1);
                subArrSum -= nums[start];
                start++;
            }*/
            if (subArrSum >= target) {
                while (subArrSum >= target) {
                    subArrSum -= nums[start];
                    start++;
                }
                // 此时 (start - 1) 才为子数组和大于等于 target 的开始位置
                minSubArrLen = Math.min(minSubArrLen, end - start + 2);
            }
        }
        return minSubArrLen == n + 1 ? 0 : minSubArrLen;
    }
}

// 前缀和 + 二分查找

