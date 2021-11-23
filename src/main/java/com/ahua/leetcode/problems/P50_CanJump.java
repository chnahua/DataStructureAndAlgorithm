package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-23 16:53
 */

/**
 * 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个下标。
 * <p>
 * 1 <= nums.length <= 3 * 10^4
 * 0 <= nums[i] <= 10^5
 * <p>
 * 哈哈哈哈, 我一次通过, 执行用时击败 100%
 */
public class P50_CanJump {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 1, 1, 4};
        int[] nums1 = new int[]{3, 2, 1, 0, 4};
        int[] nums2 = new int[]{0, 2, 3};
        P50_Solution solution = new P50_Solution();
        System.out.println(solution.canJump(nums)); // true
        System.out.println(solution.canJump(nums1)); // false
        System.out.println(solution.canJump(nums2)); // false
    }
}


class P50_Solution {
    // 我的 同官方解法 二
    public boolean canJump1(int[] nums) {
        // 特殊情况判断
        if (nums == null || nums.length == 0) {
            return false;
        }
        int n = nums.length;
        if (n == 1) {
            return true;
        }
        if (n == 2) {
            return nums[0] >= 1;
        }

        // 能够到达终点(最后一个下标)的最小索引
        // 从后往前遍历, 在已知位于 i 到 n - 1 之间的 minIndexOfCanJump 能够到达 n - 1 位置的前提下
        // 如果 i 能够到达 minIndexOfCanJump, 说明 i 也能到达 n - 1, 那么此时更新 minIndexOfCanJump 值为 i
        int minIndexOfCanJump = n - 1;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] >= minIndexOfCanJump - i) {
                minIndexOfCanJump = i;
            }
        }

        // 如果最终 minIndexOfCanJump 等于了 0, 说明从 0 可以跳到 n - 1
        return minIndexOfCanJump == 0;
    }

    // 我的优化 —— 反而效率降低了
    public boolean canJump(int[] nums) {
        // 特殊情况判断
        if (nums == null || nums.length == 0) {
            return false;
        }
        int n = nums.length;
        if (n == 1) {
            return true;
        }
        if (n == 2) {
            return nums[0] >= 1;
        }

        // 能够到达终点(最后一个下标)的最小索引
        // 从后往前遍历, 在已知位于 i 到 n - 1 之间的 minIndexOfCanJump 能够到达 n - 1 位置的前提下
        // 如果 i 能够到达 minIndexOfCanJump, 说明 i 也能到达 n - 1, 那么此时更新 minIndexOfCanJump 值为 i
        int minIndexOfCanJump = n - 1;
        int maxJump = 0;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] >= minIndexOfCanJump - i) {
                minIndexOfCanJump = i;
            }
            // 以下几行可删
            int j = n - 2 - i;
            if (j > maxJump) {
                return false;
            }
            maxJump = Math.max(maxJump, j + nums[j]);
            if (maxJump >= minIndexOfCanJump) {
                return true;
            }
        }

        // 如果最终 minIndexOfCanJump 等于了 0, 说明从 0 可以跳到 n - 1
        return minIndexOfCanJump == 0;
    }
}