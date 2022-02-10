package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-10 20:46
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 2006. 差的绝对值为 K 的数对数目 count-number-of-pairs-with-absolute-difference-k
 * 给你一个整数数组 nums 和一个整数 k ，请你返回数对 (i, j) 的数目，满足 i < j 且 |nums[i] - nums[j]| == k 。
 * <p>
 * |x| 的值定义为：
 * <p>
 * 如果 x >= 0 ，那么值为 x 。
 * 如果 x < 0 ，那么值为 -x 。
 * 1 <= nums.length <= 200
 * 1 <= nums[i] <= 100
 * 1 <= k <= 99
 */
public class P2006_CountKDifference {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 2, 1};
        int[] nums1 = new int[]{1, 3};
        int[] nums2 = new int[]{3, 2, 1, 5, 4};
        P2006_Solution solution = new P2006_Solution();
        System.out.println(solution.countKDifference(nums, 1)); // 4
        System.out.println(solution.countKDifference(nums1, 3)); // 0
        System.out.println(solution.countKDifference(nums2, 2)); // 3
    }
}

// 计数 一次遍历
// 0 ms 100.00%
// 41.1 MB 11.53%
class P2006_Solution {
    public int countKDifference(int[] nums, int k) {
        int ans = 0;
        int[] count = new int[101];
        for (int num : nums) {
            int major = num + k;
            int minor = num - k;
            ans += major <= 100 ? count[major] : 0;
            ans += minor >= 1 ? count[minor] : 0;
            count[num]++;
        }
        return ans;
    }
}

// 哈希表 一次遍历 这道题类似于 LeetCode 的第一题 两数之和
// 4 ms 75.02%
// 40.7 MB 44.24%
class P2006_Solution1 {
    public int countKDifference(int[] nums, int k) {
        int ans = 0;
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            ans += countMap.getOrDefault(num + k, 0) + countMap.getOrDefault(num - k, 0);
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        return ans;
    }
}