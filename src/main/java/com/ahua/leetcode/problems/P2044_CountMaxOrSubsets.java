package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-15 22:20
 */

/**
 * 2044. 统计按位或能得到最大值的子集数目 count-number-of-maximum-bitwise-or-subsets
 * 给你一个整数数组 nums ，请你找出 nums 子集 按位或 可能得到的 最大值 ，并返回按位或能得到最大值的 不同非空子集的数目 。
 * <p>
 * 如果数组 a 可以由数组 b 删除一些元素（或不删除）得到，则认为数组 a 是数组 b 的一个 子集 。
 * 如果选中的元素下标位置不一样，则认为两个子集 不同 。
 * <p>
 * 对数组 a 执行 按位或 ，结果等于 a[0] OR a[1] OR ... OR a[a.length - 1]（下标从 0 开始）。
 * <p>
 * 1 <= nums.length <= 16
 * 1 <= nums[i] <= 10^5
 */
public class P2044_CountMaxOrSubsets {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 1};
        int[] nums1 = new int[]{2, 2, 2};
        int[] nums2 = new int[]{3, 2, 1, 5};
        P2044_Solution solution = new P2044_Solution();
        System.out.println(solution.countMaxOrSubsets(nums)); // 2
        System.out.println(solution.countMaxOrSubsets(nums1)); // 7
        System.out.println(solution.countMaxOrSubsets(nums2)); // 6
    }
}

// 位运算
// O(2^N * N) O(1)
// 81 ms 28.40%
// 38.3 MB 52.68%
class P2044_Solution1 {
    public int countMaxOrSubsets(int[] nums) {
        int n = nums.length;
        // 总非空子集个数 (2^n-1)
        int totalSetCnt = (1 << n) - 1;
        // 按位或的最大值
        int maxOr = 0;
        for (int num : nums) {
            maxOr |= num;
        }
        // 最大按位或值子集的个数
        int cnt = 0;
        // 遍历所有子集情况
        for (int i = 1; i <= totalSetCnt; i++) {
            // 当前子集的按位或值
            int orVal = 0;
            for (int j = 0; j < n; j++) {
                if (((i >> j) & 1) == 1) {
                    orVal |= nums[j];
                }
            }
            // 如果等于 maxOr, 则加一
            if (orVal == maxOr) {
                cnt++;
            }
        }
        return cnt;
    }
}

// 回溯(对上一个方法的优化)
// O(2^N) O(N) 搜索深度为 N
// 7ms 55.56%
// 38.7 MB 45.27%
class P2044_Solution {
    int[] nums;
    int n;
    // 按位或的最大值
    int maxOr;
    // 最大按位或值子集的个数
    int cnt;

    public int countMaxOrSubsets(int[] nums) {
        this.nums = nums;
        this.n = nums.length;
        // 按位或的最大值
        this.maxOr = 0;
        for (int num : nums) {
            maxOr |= num;
        }
        // 最大按位或值子集的个数
        this.cnt = 0;
        dfs(0, 0);
        return cnt;
    }

    // 遍历所有非空子集 (2^n-1)
    private void dfs(int pos, int orVal) {
        if (pos == n) {
            // 如果等于 maxOr, 则加一
            if (orVal == maxOr) {
                cnt++;
            }
            return;
        }
        // 选择
        dfs(pos + 1, orVal | nums[pos]);
        // 不选择
        dfs(pos + 1, orVal);
    }
}

// 状态压缩 DP