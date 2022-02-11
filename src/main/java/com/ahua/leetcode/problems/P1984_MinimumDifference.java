package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-11 19:31
 */

import java.util.Arrays;

/**
 * 1984. 学生分数的最小差值 minimum-difference-between-highest-and-lowest-of-k-scores
 * 给你一个 下标从 0 开始 的整数数组 nums ，其中 nums[i] 表示第 i 名学生的分数。另给你一个整数 k 。
 * <p>
 * 从数组中选出任意 k 名学生的分数，使这 k 个分数间 最高分 和 最低分 的 差值 达到 最小化 。
 * <p>
 * 返回可能的 最小差值 。
 * 1 <= k <= nums.length <= 1000
 * 0 <= nums[i] <= 10^5
 */
public class P1984_MinimumDifference {
    public static void main(String[] args) {
        int[] nums = new int[]{90};
        int[] nums1 = new int[]{9, 4, 1, 7};
        P1984_Solution solution = new P1984_Solution();
        System.out.println(solution.minimumDifference(nums, 1)); // 0
        System.out.println(solution.minimumDifference(nums1, 2)); // 2
    }
}

// 先排序, 再滑动窗口遍历 基本操作
// 时间复杂度 O(n log n) 4ms 100%
// 空间复杂度 O(log n)   41.7 MB 5.17%
class P1984_Solution {
    public int minimumDifference(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i <= n - k; i++) {
            minDiff = Math.min(minDiff, nums[i + k - 1] - nums[i]);
        }
        return minDiff;
    }
}