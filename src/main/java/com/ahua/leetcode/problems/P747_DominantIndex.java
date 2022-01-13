package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-13 21:51
 */

/**
 * 747. 至少是其他数字两倍的最大数 largest-number-at-least-twice-of-others
 * 给你一个整数数组 nums ，其中总是存在 唯一的 一个最大整数 。
 * <p>
 * 请你找出数组中的最大元素并检查它是否 至少是数组中每个其他数字的两倍 。如果是，则返回 最大元素的下标 ，否则返回 -1
 * <p>
 * 1 <= nums.length <= 50
 * 0 <= nums[i] <= 100
 * nums 中的最大元素是唯一的
 * 给你一个整数数组 nums ，其中总是存在 唯一的 一个最大整数 。
 *
 * 请你找出数组中的最大元素并检查它是否 至少是数组中每个其他数字的两倍 。如果是，则返回 最大元素的下标 ，否则返回 -1 。
 *
 */
public class P747_DominantIndex {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 6, 1, 0};
        int[] nums1 = new int[]{1, 2, 3, 4};
        int[] nums2 = new int[]{1};
        int[] nums3 = new int[]{0, 0, 3, 2};
        int[] nums4 = new int[]{1, 0};
        P747_Solution solution = new P747_Solution();
        System.out.println(solution.dominantIndex(nums)); // 1
        System.out.println(solution.dominantIndex(nums1)); // -1
        System.out.println(solution.dominantIndex(nums2)); // 0
        System.out.println(solution.dominantIndex(nums3)); // -1
        System.out.println(solution.dominantIndex(nums4)); // 0
    }
}

class P747_Solution {
    public int dominantIndex1(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        } else if (n == 1) {
            return 0;
        }
        // 整个数组中最大值的索引
        int maxIndex = 0;
        // 整个数组中的第二大值的索引
        int minorMaxIndex = 0;
        // 初始化
        if (nums[1] > nums[0]) {
            maxIndex = 1;
        } else {
            minorMaxIndex = 1;
        }
        for (int i = 2; i < n; i++) {
            if (nums[i] > nums[maxIndex]) {
                minorMaxIndex = maxIndex;
                maxIndex = i;
            } else {
                // 当前 nums[i] 小于 nums[maxIndex], 但是可能是大于之前的 nums[minorMaxIndex] 的, 于是 minorMaxIndex 需要比较保存的
                if (nums[i] > nums[minorMaxIndex]) {
                    minorMaxIndex = i;
                }
            }
        }
        return nums[maxIndex] >= nums[minorMaxIndex] * 2 ? maxIndex : -1;
    }

    // 判断 minorMaxIndex == -1, 可减少开始的初始化, 但是其实每次循环都会判断这个, 是否就影响效率了?
    public int dominantIndex(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        } else if (n == 1) {
            return 0;
        }
        // 整个数组中最大值的索引
        int maxIndex = 0;
        // 整个数组中的第二大值的索引
        int minorMaxIndex = -1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[maxIndex]) {
                minorMaxIndex = maxIndex;
                maxIndex = i;
            } else {
                // 当前 nums[i] 小于 nums[maxIndex], 但是可能是大于之前的 nums[minorMaxIndex] 的, 于是 minorMaxIndex 需要比较保存的
                if (minorMaxIndex == -1 || nums[i] > nums[minorMaxIndex]) {
                    minorMaxIndex = i;
                }
            }
        }
        return nums[maxIndex] >= nums[minorMaxIndex] * 2 ? maxIndex : -1;
    }
}
