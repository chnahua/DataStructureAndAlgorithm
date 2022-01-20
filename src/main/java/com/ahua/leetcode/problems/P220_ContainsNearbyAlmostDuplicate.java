package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-20 20:33
 */

import java.util.*;

/**
 * 220. 存在重复元素 III contains-duplicate-iii
 * 给你一个整数数组 nums 和两个整数 k 和 t 。请你判断是否存在 两个不同下标 i 和 j，
 * 使得 abs(nums[i] - nums[j]) <= t ，同时又满足 abs(i - j) <= k 。
 * <p>
 * 如果存在则返回 true，不存在返回 false。
 * <p>
 * 0 <= nums.length <= 2 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * 0 <= k <= 10^4
 * 0 <= t <= 2^31 - 1
 */
public class P220_ContainsNearbyAlmostDuplicate {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 1};
        int[] nums1 = new int[]{1, 0, 1, 1};
        int[] nums2 = new int[]{1, 5, 9, 1, 5, 9};
        int[] nums3 = new int[]{2147483640, 2147483641};
        P220_Solution solution = new P220_Solution();
        System.out.println(solution.containsNearbyAlmostDuplicate(nums, 3, 0)); // true
        System.out.println(solution.containsNearbyAlmostDuplicate(nums1, 1, 2)); // true
        System.out.println(solution.containsNearbyAlmostDuplicate(nums2, 2, 3)); // false
        System.out.println(solution.containsNearbyAlmostDuplicate(nums2, 1, 100)); // true
    }
}

// 滑动窗口 + 有序集合
// O(nlog(min(n,k)))
// O(min(n,k))
// 35 ms
class P220_Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        int n = nums.length;
        if (k == 0 || n <= 1) {
            return false;
        }

        TreeSet<Long> treeSet = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            if (i > k) {
                treeSet.remove((long) nums[i - k - 1]);
            }
            Long ceiling = treeSet.ceiling((long) nums[i] - (long) t);
            // 如果在集合中知道到了第一个大于等于 nums[i] - t 的值 ceiling, 并且该值小于等于 nums[i] + t, 说明找到了一对符合条件的元素
            // 不能使用 (long) (nums[i] + t) , 因为可能会超出 int 类型的表示范围
            if (ceiling != null && ceiling <= (long) nums[i] + (long) t) {
                return true;
            }
            // 将该值添加到集合中
            treeSet.add((long) nums[i]);
        }

        return false;
    }
}

// 桶排序
