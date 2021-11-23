package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-23 15:05
 */

public class P740_DeleteAndEarn {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 4, 2};
        int[] nums1 = new int[]{2, 2, 3, 3, 3, 4};
        P740_Solution solution = new P740_Solution();
        System.out.println(solution.deleteAndEarn(nums)); // 6
        System.out.println(solution.deleteAndEarn(nums1)); // 9

    }
}

// 动态规划
// 时间复杂度：O(N+M), 其中 N 是数组 nums 的长度, M 是 nums 中元素的最大值
// 空间复杂度：O(M)
class P740_Solution {
    public int deleteAndEarn(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        } else if (nums.length == 1) {
            return nums[0];
        }
        // 得到整个 nums 数组中的最大值
        int maxVal = 0;
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
        }
        // 创建 maxVal + 1 大小的数组, 保存 nums 中值等于 num 的所有数据的总和到 sum[num] 中
        int[] sum = new int[maxVal + 1];
        for (int num : nums) {
            sum[num] += num;
        }

        // 原问题就等价于 P198 打家劫舍 问题
        return rob(sum);
    }

    public int rob(int[] nums) {
        int n = nums.length;
        int[] dp = new int[3];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            dp[2] = Math.max(dp[0] + nums[i], dp[1]);
            dp[0] = dp[1];
            dp[1] = dp[2];
        }
        // 若 return dp[2];
        // [1,1,1]、[1,1,1,1] 此测试用例不能通过
        // 即要保证 nums 的长度大于 3 或者给 dp[2] 赋初值等于 dp[1] 时, 才能 return dp[2];
        return dp[1];
    }
}

// 排序 + 动态规划 (先不做)