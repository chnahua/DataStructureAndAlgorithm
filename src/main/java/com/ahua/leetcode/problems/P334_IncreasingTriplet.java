package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-13 22:49
 */

/**
 * 334. 递增的三元子序列 increasing-triplet-subsequence
 * 给你一个整数数组 nums ，判断这个数组中是否存在长度为 3 的递增子序列。
 * <p>
 * 如果存在这样的三元组下标 (i, j, k) 且满足 i < j < k ，使得 nums[i] < nums[j] < nums[k] ，返回 true ；否则，返回 false 。
 * 1 <= nums.length <= 5 * 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 * <p>
 * 进阶：你能实现时间复杂度为 O(n) ，空间复杂度为 O(1) 的解决方案吗？
 */
public class P334_IncreasingTriplet {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5};
        int[] nums1 = new int[]{5, 4, 3, 2, 1};
        int[] nums2 = new int[]{2, 1, 5, 0, 4, 6};
        int[] nums3 = new int[]{2, 4, -2, -3};
        P334_Solution solution = new P334_Solution();
//        System.out.println(solution.increasingTriplet(nums)); // true
//        System.out.println(solution.increasingTriplet(nums1)); // false
//        System.out.println(solution.increasingTriplet(nums2)); // true
        System.out.println(solution.increasingTriplet(nums3)); // false
    }
}

// 动态规划 0(N^2) O(N) 超时
class P334_Solution1 {
    public boolean increasingTriplet(int[] nums) {
        int n = nums.length;
        // dp[i] 表示以序列中的第 i 个元素结尾的最长上升子序列的长度
        int[] dp = new int[n];
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            if (dp[i] >= 3) {
                return true;
            }
        }
        return false;
    }
}

// 贪心 O(N) O(1)
class P334_Solution {
    public boolean increasingTriplet(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return false;
        }
        // 三元组中的前两个数
        int[] dp = new int[2];
        dp[0] = nums[0];
        // 一定要初始化为一个数组范围最大值
        dp[1] = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            // nums[i] 比最小数小, 更新
            if (nums[i] <= dp[0]) {
                dp[0] = nums[i];
            } else if (nums[i] <= dp[1]) {
                // nums[i] 比第二小数小, 更新
                dp[1] = nums[i];
            } else {
                // 找到 nums[i] 为三元组的第三个数
                return true;
            }
        }
        return false;
    }
}