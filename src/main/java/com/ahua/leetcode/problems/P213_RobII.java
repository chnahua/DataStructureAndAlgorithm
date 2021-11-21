package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-21 16:38
 */

import java.util.Arrays;

/**
 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
 * <p>
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
 * <p>
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 1000
 */
public class P213_RobII {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 2};
        int[] nums1 = new int[]{1, 2, 3, 1};
        int[] nums2 = new int[]{0};
        int[] nums3 = new int[]{2, 7, 9, 3, 1};
        int[] nums4 = new int[]{1, 1, 1, 2};
        int[] nums5 = new int[]{1, 7, 9, 4};
        P213_Solution solution = new P213_Solution();
        System.out.println(solution.rob(nums)); // 3
        System.out.println(solution.rob(nums1)); // 4
        System.out.println(solution.rob(nums2)); // 0
        System.out.println(solution.rob(nums3)); // 11
        System.out.println(solution.rob(nums4)); // 3
        System.out.println(solution.rob(nums5)); // 11
    }
}

class P213_Solution {
    // 动态规划(滚动数组思想)
    // 时间复杂度：O(n)
    // 空间复杂度：O(1)
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        } else if (n == 2) {
            return Math.max(nums[0], nums[1]);
        } else if (n == 3) {
            return Math.max(Math.max(nums[0], nums[1]), nums[2]);
        }

        // 如何才能保证第一间房屋和最后一间房屋不同时偷窃呢？
        // 如果偷窃了第一间房屋，则不能偷窃最后一间房屋，因此偷窃房屋的范围是第一间房屋到最后第二间房屋；
        // 如果偷窃了最后一间房屋，则不能偷窃第一间房屋，因此偷窃房屋的范围是第二间房屋到最后一间房屋。
        return Math.max(robRange(nums, 0, n - 2), robRange(nums, 1, n - 1));
    }

    public int robRange(int[] nums, int start, int end) {

        int[] dp = new int[3];
        dp[0] = nums[start];
        dp[1] = Math.max(nums[start], nums[start + 1]);
        // dp[2] = dp[1];

        for (int i = start + 2; i <= end; i++) {
            dp[2] = Math.max(dp[0] + nums[i], dp[1]);
            dp[0] = dp[1];
            dp[1] = dp[2];
        }
        System.out.println(Arrays.toString(dp));
        // 此处若返回 dp[2], 必须保证原 nums 数组长度大于 3
        // 由于之前咋 rob 中对 n == 3, 进行了特殊处理, 所以此处可以返回 dp[2]
        // 如果不对 n == 3 做特殊处理, 也可以给 dp[2] 赋初值 dp[2] = dp[1];
        // 当然, 如果是返回 dp[1] 就肯定没错
        return dp[2];
    }
}