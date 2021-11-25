package com.ahua.leetcode.problems;

import java.util.Arrays;

/**
 * @author huajun
 * @create 2021-11-25 18:39
 */

/**
 * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
 * <p>
 * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
 * <p>
 * 你可以认为每种硬币的数量是无限的。
 * <p>
 * 1 <= coins.length <= 12
 * 1 <= coins[i] <= 2^31 - 1
 * 0 <= amount <= 10^4
 */
public class P322_CoinChange {
    public static void main(String[] args) {
        int[] coins = new int[]{1, 2, 5};
        int[] coins1 = new int[]{2};
        int[] coins2 = new int[]{1};
        int[] coins3 = new int[]{1};
        int[] coins4 = new int[]{1};
        P322_Solution solution = new P322_Solution();
        System.out.println(solution.coinChange(coins, 11)); // 3
        System.out.println(solution.coinChange(coins1, 3)); //-1
        System.out.println(solution.coinChange(coins2, 0)); // 0
        System.out.println(solution.coinChange(coins3, 1)); // 1
        System.out.println(solution.coinChange(coins4, 2)); // 2
    }
}

// 动态规划
class P322_Solution {
    public int coinChange1(int[] coins, int amount) {
        // 特殊情况处理
        if (amount == 0) {
            return 0;
        }
        int n = coins.length;
        // 特殊情况处理
        if (n == 0) {
            return -1;
        } else if (n == 1) {
            if (amount % coins[0] == 0) {
                return amount / coins[0];
            } else {
                return -1;
            }
        }

        // 先对 coins 排序, 减少循环次数
        Arrays.sort(coins);

        // 特殊情况处理: 如果金额比最小的一枚硬币还小, 则找不到(前提是 amount > 0)
        if (amount < coins[0]) {
            return -1;
        }

        int[] dp = new int[amount + 1];
        // 由于在循环中比较求得的是最小值, 所以在赋初值时需要将其将 dp 置为尽量大的不可能是结果的数
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        // 依次判断确认, 找出凑成金额为 i 的最少硬币个数
        for (int i = 1; i < amount + 1; i++) {
            for (int j = 0; j < n; j++) {
                // 只有当前硬币小于金额 i 时, 才可能将该硬币添加进组合中
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                } else {
                    break;
                }
            }
        }
        // 如果最终结果中 dp[amount] 还是最初的初值, 说明根本找不到这种硬币组合, 返回 -1
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

    // 些微优化
    public int coinChange(int[] coins, int amount) {
        // 特殊情况处理
        if (amount == 0) {
            return 0;
        }
        int n = coins.length;
        // 特殊情况处理
        if (n == 0) {
            return -1;
        } else if (n == 1) {
            if (amount % coins[0] == 0) {
                return amount / coins[0];
            } else {
                return -1;
            }
        }

        // 先对 coins 排序, 减少循环次数
        Arrays.sort(coins);

        // 特殊情况处理: 如果金额比最小的一枚硬币还小, 则找不到(前提是 amount > 0)
        if (amount < coins[0]) {
            return -1;
        }

        int[] dp = new int[amount + 1];
        dp[0] = 0;

        int min;

        // 依次判断确认, 找出凑成金额为 i 的最少硬币个数
        for (int i = 1; i < amount + 1; i++) {
            // 由于在循环中比较求得的是最小值, 所以在赋初值时需要将其将 min 置为尽量大的不可能是结果的数
            min = amount + 1;
            for (int coin : coins) {
                // 只有当前硬币小于金额 i 时, 才可能将该硬币添加进组合中
                if (coin <= i) {
                    min = Math.min(min, dp[i - coin]);
                } else {
                    break;
                }
            }
            dp[i] = min + 1;
        }
        // 如果最终结果中 dp[amount] 还是最初的初值, 说明根本找不到这种硬币组合, 返回 -1
        return dp[amount] >= amount + 1 ? -1 : dp[amount];
    }
}