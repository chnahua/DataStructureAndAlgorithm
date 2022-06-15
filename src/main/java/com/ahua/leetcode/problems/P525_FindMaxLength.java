package com.ahua.leetcode.problems;

import java.util.HashMap;

/**
 * @author huajun
 * @create 2022-06-16 0:09
 */

/**
 * 525. 连续数组 contiguous-array
 * 给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。
 * 1 <= nums.length <= 10^5
 * nums[i] 不是 0 就是 1
 */
public class P525_FindMaxLength {
    public static void main(String[] args) {
        int[] nums = new int[]{0, 1};
        int[] nums1 = new int[]{0, 1, 0};
        P525_Solution solution = new P525_Solution();
        System.out.println(solution.findMaxLength(nums)); // 2
        System.out.println(solution.findMaxLength(nums1)); // 2
    }
}

class P525_Solution {
    // 23 ms 57.31%
    // 50.6 MB 5.60%
    public int findMaxLength(int[] nums) {
        int n = nums.length;
        int maxLength = 0;
        int count = 0;
        HashMap<Integer, Integer> firstTimeMap = new HashMap<>();
        firstTimeMap.put(count, -1);
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                count++;
            } else {
                count--;
            }
            if (firstTimeMap.containsKey(count)) {
                maxLength = Math.max(maxLength, i - firstTimeMap.get(count));
            } else {
                firstTimeMap.put(count, i);
            }
        }
        return maxLength;
    }

    // 16 ms 96.02%
    // 49.4 MB 93.78%
    public int findMaxLength1(int[] nums) {
        int n = nums.length;
        int maxLength = 0;
        int count = 0;
        HashMap<Integer, Integer> firstTimeMap = new HashMap<>();
        firstTimeMap.put(count, -1);
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                count++;
            } else {
                count--;
            }
            Integer firstOccur = firstTimeMap.get(count);
            if (firstOccur != null) {
                maxLength = Math.max(maxLength, i - firstOccur);
            } else {
                firstTimeMap.put(count, i);
            }
        }
        return maxLength;
    }
}