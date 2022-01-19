package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-20 0:09
 */

import java.util.Arrays;
import java.util.HashSet;

/**
 * 217. 存在重复元素
 * 给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；如果数组中每个元素互不相同，返回 false 。
 * <p>
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 */
public class P217_ContainsDuplicate {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 1};
        int[] nums1 = new int[]{1, 2, 3, 4};
        int[] nums2 = new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2};
        P217_Solution solution = new P217_Solution();
        System.out.println(solution.containsDuplicate(nums)); // true
        System.out.println(solution.containsDuplicate(nums1)); // false
        System.out.println(solution.containsDuplicate(nums2)); // true
    }
}

// 排序 O(NlogN) O(logN)
// 19 ms
class P217_Solution1 {
    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }
}

// 哈希表 O(N) O(N)
// 4 ms
class P217_Solution {
    // 7 ms
    public boolean containsDuplicate1(int[] nums) {
        HashSet<Integer> hashSet = new HashSet<>();
        for (int num : nums) {
            // 如果 hashSet 中已经包含了该 num, 则直接返回 true
            if (hashSet.contains(num)) {
                return true;
            }
            hashSet.add(num);
        }
        return false;
    }

    // 由于是先做了 P219 和看了它的官解, 知道了原来 可以通过 hashSet.add(num) 的返回值来判断原 hashSet 中是否存在 num
    // 故可以不必使用 hashSet.contains(num) 预先判断
    // 4 ms
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> hashSet = new HashSet<>();
        for (int num : nums) {
            if (!hashSet.add(num)) {
                return true;
            }
        }
        return false;
    }
}
