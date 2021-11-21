package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-21 15:31
 */

/**
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 *
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
 *
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 400
 */
public class P198_RobI {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 1};
        int[] nums1 = new int[]{2, 7, 9, 3, 1};
        P198_Solution solution = new P198_Solution();
        System.out.println(solution.rob(nums)); // 4
        System.out.println(solution.rob(nums1)); // 12
    }
}

// 动态规划
class P198_Solution {
    // 动态规划
    // 时间复杂度：O(n)
    // 空间复杂度：O(n)
    public int rob1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        // 此 if 也可删除
        if (n == 1) {
            // 如果只有一间房屋, 则偷窃该房屋, 可以偷窃到最高总金额
            return nums[0];
        } else if (n == 2) {
            // 如果只有两间房屋, 则由于两间房屋相邻, 不能同时偷窃, 只能偷窃其中的一间房屋
            // 因此选择其中金额较高的房屋进行偷窃, 可以偷窃到最高总金额
            return Math.max(nums[0], nums[1]);
        }

        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            // 偷窃第 i 间房屋，那么就不能偷窃第 i − 1 间房屋，偷窃总金额为前 i − 2 间房屋的最高总金额与第 i 间房屋的金额之和。
            // 不偷窃第 i 间房屋，偷窃总金额为前 i − 1 间房屋的最高总金额。
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        return dp[n - 1];
    }

    // 动态规划(滚动数组思想), 减小空间复杂度
    // 考虑到每间房屋的最高总金额只和该房屋的前两间房屋的最高总金额相关
    // 因此可以使用滚动数组, 在每个时刻只需要存储前两间房屋的最高总金额
    // 时间复杂度：O(n)
    // 空间复杂度：O(1)
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        // 此 if 也可删除
        if (n == 1) {
            // 如果只有一间房屋, 则偷窃该房屋, 可以偷窃到最高总金额
            return nums[0];
        } else if (n == 2) {
            // 如果只有两间房屋, 则由于两间房屋相邻, 不能同时偷窃, 只能偷窃其中的一间房屋
            // 因此选择其中金额较高的房屋进行偷窃, 可以偷窃到最高总金额
            return Math.max(nums[0], nums[1]);
        }

        int[] dp = new int[3];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            // 偷窃第 i 间房屋, 那么就不能偷窃第 i − 1 间房屋，偷窃总金额为前 i − 2 间房屋的最高总金额与第 i 间房屋的金额之和
            // 不偷窃第 i 间房屋, 偷窃总金额为前 i − 1 间房屋的最高总金额
            dp[2] = Math.max(dp[1], dp[0] + nums[i]);
            dp[0] = dp[1];
            dp[1] = dp[2];
        }
        // 最后返回 dp[2] dp[1] 均可, 它俩值相等
        return dp[2];
    }
}