package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-22 19:29
 */

import java.util.Arrays;

/**
 * 1994. 好子集的数目 the-number-of-good-subsets
 * 给你一个整数数组 nums 。
 * 如果 nums 的一个子集中，所有元素的乘积可以表示为一个或多个 互不相同的质数 的乘积，那么我们称它为 好子集 。
 * <p>
 * 比方说，如果 nums = [1, 2, 3, 4] ：
 * [2, 3] ，[1, 2, 3] 和 [1, 3] 是 好 子集，乘积分别为 6 = 2*3 ，6 = 2*3 和 3 = 3 。
 * [1, 4] 和 [4] 不是 好 子集，因为乘积分别为 4 = 2*2 和 4 = 2*2 。
 * 请你返回 nums 中不同的 好 子集的数目对 10^9 + 7 取余 的结果。
 * <p>
 * nums 中的 子集 是通过删除 nums 中一些（可能一个都不删除，也可能全部都删除）元素后剩余元素组成的数组。
 * 如果两个子集删除的下标不同，那么它们被视为不同的子集。
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 30
 */
public class P1994_NumberOfGoodSubsets {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4};
        int[] nums1 = new int[]{4, 2, 3, 15};
        P1994_Solution solution = new P1994_Solution();
        /*
        好子集为：
        - [1,2]：乘积为 2 ，可以表示为质数 2 的乘积。
        - [1,2,3]：乘积为 6 ，可以表示为互不相同的质数 2 和 3 的乘积。
        - [1,3]：乘积为 3 ，可以表示为质数 3 的乘积。
        - [2]：乘积为 2 ，可以表示为质数 2 的乘积。
        - [2,3]：乘积为 6 ，可以表示为互不相同的质数 2 和 3 的乘积。
        - [3]：乘积为 3 ，可以表示为质数 3 的乘积。
         */
        System.out.println(solution.numberOfGoodSubsets(nums)); // 6
        /*
        好子集为：
        - [2]：乘积为 2 ，可以表示为质数 2 的乘积。
        - [2,3]：乘积为 6 ，可以表示为互不相同质数 2 和 3 的乘积。
        - [2,15]：乘积为 30 ，可以表示为互不相同质数 2，3 和 5 的乘积。
        - [3]：乘积为 3 ，可以表示为质数 3 的乘积。
        - [15]：乘积为 15 ，可以表示为互不相同质数 3 和 5 的乘积。
         */
        System.out.println(solution.numberOfGoodSubsets(nums1)); // 5
    }
}

// 状态压缩 DP 官方解法
// 9 ms 78.79%
// 53.3 MB 48.48%
class P1994_Solution {
    static final int[] PRIMES = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
    static final int NUM_MAX = 30;
    static final int MOD = 1000000007;
    static final int maskMax = (1 << PRIMES.length);

    public int numberOfGoodSubsets(int[] nums) {
        // 每个数出现的次数
        // 1 <= nums[i] <= 30
        int[] freq = new int[NUM_MAX + 1];
        for (int num : nums) {
            freq[num]++;
        }
        // 初始化
        // 任意个 1 可以添加进所有集合中
        int[] dp = new int[1 << PRIMES.length];
        dp[0] = 1;
        for (int i = 0; i < freq[1]; ++i) {
            dp[0] = (dp[0] << 1) % MOD;
        }

        for (int i = 2; i <= NUM_MAX; i++) {
            if (freq[i] == 0) {
                continue;
            }
            // 检查 i 的每个质因数是否均不超过 1 个, 超过 1 个肯定不能添加进集合
            // subset 记录分解 i 出现的质数的位置集合
            int subset = 0;
            boolean check = true;
            for (int j = 0; j < PRIMES.length; j++) {
                int prime = PRIMES[j];
                if (i % (prime * prime) == 0) {
                    check = false;
                    break;
                }
                if (i % prime == 0) {
                    subset |= (1 << j);
                }
            }
            if (!check) {
                continue;
            }

            for (int mask = maskMax - 1; mask > 0; mask--) {
                // 如果要将 i 这个数添加到之前的所有的子集合中
                // 需要保证该子集合中的数分解成的质数不包含 i 分解成的质数 subset 集合
                if ((mask & subset) == subset) {
                    dp[mask] = (int) ((dp[mask] + ((long) dp[mask ^ subset]) * freq[i]) % MOD);
                }
            }
            // System.out.println(Arrays.toString(dp));
        }

        int ans = 0;
        for (int mask = 1; mask < maskMax; mask++) {
            ans = (ans + dp[mask]) % MOD;
        }
        return ans;
    }
}