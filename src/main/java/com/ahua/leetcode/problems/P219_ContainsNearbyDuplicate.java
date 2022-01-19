package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-19 23:37
 */

/**
 * 219. 存在重复元素 II contains-duplicate-ii
 * 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，
 * 满足 nums[i] == nums[j] 且 abs(i - j) <= k 。如果存在，返回 true ；否则，返回 false
 * <p>
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * 0 <= k <= 10^5
 */
public class P219_ContainsNearbyDuplicate {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 1};
        int[] nums1 = new int[]{1, 0, 1, 1};
        int[] nums2 = new int[]{1, 2, 3, 1, 2, 3};
        int[] nums3 = new int[]{1, 2, 1};
        P219_Solution solution = new P219_Solution();
        System.out.println(solution.containsNearbyDuplicate(nums, 3)); // true
        System.out.println(solution.containsNearbyDuplicate(nums1, 1)); // true
        System.out.println(solution.containsNearbyDuplicate(nums2, 2)); // false
        System.out.println(solution.containsNearbyDuplicate(nums3, 0)); // false
    }
}

class P219_Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (k == 0) {
            return false;
        }
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j <= i + k && j <= nums.length - 1; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }
}