package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-01 22:15
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 523. 连续的子数组和 continuous-subarray-sum
 * 给你一个整数数组 nums 和一个整数 k ，编写一个函数来判断该数组是否含有同时满足下述条件的连续子数组：
 * <p>
 * 1.子数组大小 至少为 2 ，且
 * 2.子数组元素总和为 k 的倍数。
 * 如果存在，返回 true ；否则，返回 false 。
 * <p>
 * 如果存在一个整数 n ，令整数 x 符合 x = n * k ，则称 x 是 k 的一个倍数。0 始终视为 k 的一个倍数。
 * <p>
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^9
 * 0 <= sum(nums[i]) <= 2^31 - 1
 * 1 <= k <= 2^31 - 1
 */
public class P523_CheckSubarraySum {
    public static void main(String[] args) {
        int[] nums = new int[]{23, 2, 4, 6, 7};
        int[] nums1 = new int[]{23, 2, 6, 4, 7};
        int[] nums2 = new int[]{23, 2, 6, 4, 7};
        int[] nums3 = new int[]{23, 2, 4, 6, 6};
        int[] nums4 = new int[]{5, 0, 0, 0};
        int[] nums5 = new int[]{0, 0};
        P523_Solution solution = new P523_Solution();
        System.out.println(solution.checkSubarraySum(nums, 6)); // true
        System.out.println(solution.checkSubarraySum(nums1, 6)); // true
        System.out.println(solution.checkSubarraySum(nums2, 13)); // false
        System.out.println(solution.checkSubarraySum(nums3, 7)); // true
        System.out.println(solution.checkSubarraySum(nums4, 3)); // true
        System.out.println(solution.checkSubarraySum(nums5, 1)); // true
    }
}

// 官方解法
// 前缀和 + 哈希表 使用到数学方法 同余定理
// O(N) O(min(N, k))
// 15 ms 67.96%
class P523_Solution {
    public boolean checkSubarraySum1(int[] nums, int k) {
        int n = nums.length;
        if (n < 2) {
            return false;
        }
        Map<Integer, Integer> hashMap = new HashMap<>();
        // 前缀和对 k 的余数
        int[] pre = new int[n];
        pre[0] = nums[0] % k;
        hashMap.put(pre[0], 0);
        hashMap.put(0, -1);
        for (int i = 1; i < n; i++) {
            pre[i] = (pre[i - 1] + nums[i]) % k;
            Integer first = hashMap.get(pre[i]);
            if (first != null) {
                if (i - first >= 2) {
                    return true;
                }
            } else {
                hashMap.put(pre[i], i);
            }
        }
        return false;
    }

    // 修改代码
    public boolean checkSubarraySum(int[] nums, int k) {
        int n = nums.length;
        if (n < 2) {
            return false;
        }
        // 前缀和对 k 的余数
        int remainder = 0;
        Map<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(0, -1);
        for (int i = 0; i < n; i++) {
            remainder = (remainder + nums[i]) % k;
            Integer first = hashMap.get(remainder);
            if (first != null) {
                if (i - first >= 2) {
                    return true;
                }
            } else {
                hashMap.put(remainder, i);
            }
        }
        return false;
    }
}