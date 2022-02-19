package com.ahua.exam.huawei;

/**
 * @author huajun
 * @create 2022-02-19 18:16
 */

/**
 * 这道题是 LeetCode P53 题
 * 整数数组A，找出一个具有最大和的连续子数组，并返回最大和。
 * 举例：A:{ 1,2,3,4,3,2,1,-1}  结果输出为 16
 */
public class ODInterview_4_CD_I_2022_02_19 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 3, 2, 1, -1};
        int[] nums1 = new int[]{-1, 2, -3, 4, -3, 2, 1, -1};
        int[] nums2 = new int[]{-1, -2, -3, 0, -3, 3, 1, -1};
        int[] nums3 = new int[]{1, 2, 3, -5, 3, 2, 1, -1};
        int[] nums4 = new int[]{1, -5, 3, -5, 3, 2, 1, -1};
        ODInterview_4_CD_I_2022_02_19_Solution solution = new ODInterview_4_CD_I_2022_02_19_Solution();
        System.out.println(solution.func(nums));
        System.out.println(solution.func(nums1));
        System.out.println(solution.func(nums2));
        System.out.println(solution.func(nums3));
        System.out.println(solution.func(nums4));
    }
}

// 面试时写的代码，由于我前两天做过，并且还印象深刻，所以我很快就做出来了。不过当时没有写这个动态规划解题的代码，现在去更新下。
class ODInterview_4_CD_I_2022_02_19_Solution {
    public int func(int[] nums) {
        int n = nums.length;
        int max = Integer.MIN_VALUE; // 这个赋值不对 应该赋值为 nums[0] 针对于数组只有一个数这种特殊情况
        int[] dp = new int[n];
        dp[0] = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}