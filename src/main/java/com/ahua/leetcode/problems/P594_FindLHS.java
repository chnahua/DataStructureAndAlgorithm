package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-25 19:44
 */

import java.util.Arrays;
import java.util.HashMap;

/**
 * 594. 最长和谐子序列
 * <p>
 * 和谐数组是指一个数组里元素的最大值和最小值之间的差别 正好是 1 。
 * <p>
 * 现在，给你一个整数数组 nums ，请你在所有可能的子序列中找到最长的和谐子序列的长度。
 * <p>
 * 数组的子序列是一个由数组派生出来的序列，它可以通过删除一些元素或不删除元素、且不改变其余元素的顺序而得到。
 * <p>
 * 1 <= nums.length <= 2 * 10^4
 * -10^9 <= nums[i] <= 10^9
 */
public class P594_FindLHS {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, 2, 2, 5, 2, 3, 7};
        int[] nums1 = new int[]{1, 2, 3, 4};
        int[] nums2 = new int[]{1, 1, 1, 1};
        P594_Solution solution = new P594_Solution();
        // 5 最长的和谐子序列是 [3,2,2,2,3]
        System.out.println(solution.findLHS(nums));
        System.out.println(solution.findLHS(nums1)); // 2
        System.out.println(solution.findLHS(nums2)); // 0
    }
}

// 枚举
// 时间复杂度 O(NLogN) 空间复杂度 O(N)
class P594_Solution {
    public int findLHS(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return 0;
        }
        Arrays.sort(nums);
        // begin 第一个子序列的头
        // next 第二个子序列的头
        int begin = 0, next = 0;
        boolean bl = false;
        int ans = 0;
        // end 第二个子序列的尾
        for (int end = 1; end < n; end++) {
            if (nums[end] == nums[begin]) {
                continue;
            }
            if (nums[end] - nums[begin] > 1) {
                if (nums[end] - nums[next] == 1) {
                    begin = next;
                    next = end;
                } else {
                    begin = end;
                    next = end;
                    bl = false;
                }
            }
            if (nums[end] - nums[begin] == 1) {
                ans = Math.max(ans, end - begin + 1);
                if (!bl) {
                    next = end;
                    bl = true;
                }
            }
        }
        return ans;
    }
}

// 哈希表
// 理论上的时间复杂度 O(N) 空间复杂度 O(N), 但是效率却不及枚举的 O(NLogN)
class P594_Solution1 {
    public int findLHS(int[] nums) {
        // 记录各个数字出现的次数
        HashMap<Integer, Integer> cnt = new HashMap<>();
        int res = 0;
        for (int num : nums) {
            cnt.put(num, cnt.getOrDefault(num, 0) + 1);
        }
        for (int key : cnt.keySet()) {
            if (cnt.containsKey(key + 1)) {
                res = Math.max(res, cnt.get(key) + cnt.get(key + 1));
            }
        }
        return res;
    }
}

