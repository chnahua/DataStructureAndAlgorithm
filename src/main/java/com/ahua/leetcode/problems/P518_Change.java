package com.ahua.leetcode.problems;

import java.util.Arrays;

/**
 * @author huajun
 * @create 2021-11-26 22:13
 */

public class P518_Change {
    public static void main(String[] args) {
        int[] coins = new int[]{1, 2, 5};
        int[] coins1 = new int[]{2};
        int[] coins2 = new int[]{10};
        int[] coins3 = new int[]{5};
        P518_Solution solution = new P518_Solution();
        System.out.println(solution.change(5, coins)); // 4
        System.out.println(solution.change(3, coins1)); // 0
        System.out.println(solution.change(10, coins2)); // 1
        System.out.println(solution.change(10, coins3)); // 1
    }
}

class P518_Solution {
    public int change(int amount, int[] coins) {
        // 特殊情况处理
        if (amount == 0) {
            return 1;
        }
        int n = coins.length;
        // 特殊情况处理
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            if (amount % coins[0] == 0) {
                return 1;
            } else {
                return 0;
            }
        }

        // 先对 coins 排序, 减少循环次数
        Arrays.sort(coins);

        // 特殊情况处理: 如果金额比最小的一枚硬币还小, 则找不到(前提是 amount > 0)
        if (amount < coins[0]) {
            return 0;
        }

        int[][] dp = new int[n + 1][amount + 1];
        // 第一列为 1
        for (int i = 0; i < n + 1; i++) {
            dp[i][0] = 1;
        }

        // coins[i]
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < amount + 1; j++) {
                if (j - coins[i - 1] >= 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
                }else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][amount];
//        for (int i = 1; i < amount + 1; i++) {
//            for (int j = 0; j < n; j++) {
//                if (coins[j] <= i) {
//                    dp[i] += dp[i - coins[j]];
//                } else {
//                    break;
//                }
//            }
//        }
        // return dp[amount];
    }
}