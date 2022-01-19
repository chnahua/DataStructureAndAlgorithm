package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-19 23:37
 */

import java.util.HashMap;
import java.util.HashSet;

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

// 暴力解法 2103 ms
class P219_Solution1 {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        // k 为 0, 不符合题意, 返回 false
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

// 哈希表 18 ms 90.50%
class P219_Solution2 {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        // k 为 0, 不符合题意, 返回 false
        if (k == 0) {
            return false;
        }
        // 使用哈希表记录每个元素的最大下标
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer index = hashMap.get(nums[i]);
            // 若 index 不为 null, 则说明 hashMap 中若存在 nums[i]
            if (index != null) {
                // 此时判断两者是否满足其差值 <= k
                if (i - index <= k) {
                    return true;
                }
            }
            // 若 index 为 null, 则新添加 (nums[i], i)
            // 若 index 不为 null, 则更新 (nums[i], i)
            hashMap.put(nums[i], i);
        }
        return false;
    }
}

// 滑动窗口 14 ms 99.05%
class P219_Solution {
    // 官方写法
    public boolean containsNearbyDuplicate1(int[] nums, int k) {
        // k 为 0, 不符合题意, 返回 false
        if (k == 0) {
            return false;
        }
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > k) {
                hashSet.remove(nums[i - k - 1]);
            }
            // 今天才知道原来 hashSet 添加一个已经存在于 set 中的值时, 会返回 false,
            // 这样就不用使用 hashSet.contains(num) 来预先判断了
            if (!hashSet.add(nums[i])) {
                return true;
            }
        }
        return false;
    }

    // 我的修改, 先将第一个窗口中的 k 个值添加到 hashSet 中, 有些多此一举
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        // k 为 0, 不符合题意, 返回 false
        if (k == 0) {
            return false;
        }
        HashSet<Integer> hashSet = new HashSet<>();
        // 先把前 k 个加入到 hashSet 中, 其实有些多此一举
        for (int i = 0; i <= k && i < nums.length; i++) {
            if (!hashSet.add(nums[i])) {
                return true;
            }
        }
        for (int i = k + 1; i < nums.length; i++) {
            hashSet.remove(nums[i - k - 1]);
            // 今天才知道原来 hashSet 添加一个已经存在于 set 中的值时, 会返回 false,
            // 这样就不必使用 hashSet.contains(num) 来预先判断了
            if (!hashSet.add(nums[i])) {
                return true;
            }
        }
        return false;
    }
}