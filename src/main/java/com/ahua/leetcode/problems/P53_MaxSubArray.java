package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-16 21:22
 */

/**
 * 53. 最大子数组和 maximum-subarray
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * <p>
 * 子数组 是数组中的一个连续部分。
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * <p>
 * 进阶：如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的 分治法 求解。
 */
public class P53_MaxSubArray {
    public static void main(String[] args) {
        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int[] nums1 = new int[]{1};
        int[] nums2 = new int[]{5, 4, -1, 7, 8};
        int[] nums3 = new int[]{-1};
        int[] nums4 = new int[]{-2, -1};
        int[] nums5 = new int[]{3, -3, 2, -3};
        int[] nums6 = new int[]{8, -19, 5, -4, 20};
        P53_Solution solution = new P53_Solution();
        System.out.println(solution.maxSubArray(nums)); // [4,-1,2,1] = 6
        System.out.println(solution.maxSubArray(nums1)); // 1
        System.out.println(solution.maxSubArray(nums2)); // 23
        System.out.println(solution.maxSubArray(nums3)); // -1
        System.out.println(solution.maxSubArray(nums4)); // -1
        System.out.println(solution.maxSubArray(nums5)); // 3
        System.out.println(solution.maxSubArray(nums6)); // 21
    }
}

// 我的解法
// O(N) O(1)
// 3 ms 15.39%
class P53_Solution1 {
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        // 全为正数的子数组和
        int positive = 0;
        // 全为复数的子数组和
        int negative = 0;
        int tempMaxSubArrSum = Integer.MIN_VALUE;
        int maxSubArrSum = 0;
        boolean flag = false;
        int i = 0;
        // 对数组中前几个数为负数的情况进行处理
        for (; i < n; i++) {
            // 为正数时退出
            if (nums[i] >= 0) {
                flag = true;
                break;
            } else {
                // 为负数时, 得到前 i 个连续负数中的最大值
                tempMaxSubArrSum = Math.max(tempMaxSubArrSum, nums[i]);
            }
        }
        // 如果整个数组都是负数, 返回负数中的最大值
        if (!flag) {
            return tempMaxSubArrSum;
        } else {
            // 不全是负数, 存在整数时, 置初始值为 0
            tempMaxSubArrSum = 0;
        }
        // 从第一个整数开始遍历
        for (; i < n; i++) {
            // 前一个数为负数时
            if (!flag) {
                if (nums[i] > 0) {
                    positive = nums[i];
                    flag = true;
                } else {
                    negative += nums[i];
                }
            } else {
                // 前一个数为正数时
                // 当前数还为正数, 直接累加
                if (nums[i] > 0) {
                    positive += nums[i];
                } else {
                    // 当前数为负数, 更新次数前的最大子数组和
                    tempMaxSubArrSum = Math.max(tempMaxSubArrSum + negative + positive, positive);
                    if (tempMaxSubArrSum > maxSubArrSum) {
                        maxSubArrSum = tempMaxSubArrSum;
                    }
                    negative = nums[i];
                    flag = false;
                }
            }
        }
        // 数组最后一个或者几个元素如果是负数, 不用管
        // 如果是正数, 则最大子数组和可能在其中产生, 所以需要再判断一次
        if (flag) {
            tempMaxSubArrSum = Math.max(tempMaxSubArrSum + negative + positive, positive);
            maxSubArrSum = Math.max(tempMaxSubArrSum, maxSubArrSum);
        }
        return maxSubArrSum;
    }
}

// 动态规划
// 2022-02-19 面试题
class P53_Solution {
    // O(N) O(N)
    // 2 ms 42.95%
    // 50.7 MB 10.57%
    public int maxSubArray1(int[] nums) {
        int n = nums.length;
        // 以 nums[i] 结尾的最长子数组的大小
        int[] dp = new int[n];
        dp[0] = nums[0];
        // 历史最长子数组的大小
        int maxSubArrSum = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            maxSubArrSum = Math.max(maxSubArrSum, dp[i]);
        }
        return maxSubArrSum;
    }

    // 滚动数组思想 优化空间复杂度
    // 官方解法
    // O(N) O(1)
    // 1 ms 100%
    // 50.1 MB 23.50%
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        // 以 nums[i] 结尾的最长子数组的大小
        // int[] dp = new int[n];
        // dp[0] = nums[0];
        int curMaxSubArrSum = nums[0];
        // 历史最长子数组的大小
        int maxSubArrSum = nums[0];
        for (int i = 1; i < n; i++) {
            // dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            // maxSubArrSum = Math.max(maxSubArrSum, dp[i]);
            curMaxSubArrSum = Math.max(curMaxSubArrSum + nums[i], nums[i]);
            maxSubArrSum = Math.max(maxSubArrSum, curMaxSubArrSum);
        }
        return maxSubArrSum;
    }
}