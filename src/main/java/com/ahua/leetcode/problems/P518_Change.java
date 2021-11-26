package com.ahua.leetcode.problems;

import java.util.Arrays;

/**
 * @author huajun
 * @create 2021-11-26 22:13
 */

/**
 * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
 *
 * 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
 *
 * 假设每一种面额的硬币有无限个。
 *
 * 题目数据保证结果符合 32 位带符号整数。
 *
 * 1 <= coins.length <= 300
 * 1 <= coins[i] <= 5000
 * coins 中的所有值 互不相同
 * 0 <= amount <= 5000
 *
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

/* 最初内外层循环不对会产生重复计算, 造成错误的代码 */
//        // 什么鬼啊
//        // 情况1. 注意这里我们需要的是'组合数'，所以要将coins放在外层循环，而不是将金额放在外层
//        // 情况2. 如果将金额放在外层所求的是'排列数'，会有重复
//        int[] dp = new int[amount + 1];
//        dp[0] = 1;
//        for (int i = 1; i < amount + 1; i++) {
//            for (int j = 0; j < n; j++) {
//                if (coins[j] <= i) {
//                    dp[i] += dp[i - coins[j]];
//                } else {
//                    break;
//                }
//            }
//        }
//         return dp[amount];

// 二维动态规划
class P518_Solution1 {
    // 二维动态规划
    public int change1(int amount, int[] coins) {
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
//        // 可有可无
//        // 先对 coins 排序, 减少循环次数
//        Arrays.sort(coins);
//
//        // 特殊情况处理: 如果金额比最小的一枚硬币还小, 则找不到(前提是 amount > 0)
//        if (amount < coins[0]) {
//            return 0;
//        }
        // 二维动态规划
        // 行表示有 0 种, 1 种, 2 种, ..., n 种硬币, 每种硬币的价值保存在 coins[] 数组中, 共 n + 1 行
        // 列表示要凑成的金额为 0, 1, 2, ..., amount, 共 amount + 1 列
        // dp[i][j] 表示当有 i 种硬币时, 需要把这 i 种硬币凑出金额为 j 的组合数
        int[][] dp = new int[n + 1][amount + 1];
        // 对特殊情况下的 dp数组 进行初始化赋值, 一般是 i 或 j 为 0 的初始情况
        // 首先, 考虑 dp[0][0], 0 种硬币凑成金额为 0 的组合数? 是多少呢? 为 1 对吧?
        // 然后, 判断第一行, 第一行表示 0 种硬币凑成金额为 j(j 在 0 到 amount 之间) 的组合数?
        // 好像除了能凑出金额 0 之外, 凑不出其它金额了吧? 故第一行除了 dp[0][0] 为 1, 其余均为 0
        // 最后, 判断第一列, 第一列表示可能有若干种硬币要凑成金额为 0 的组合数? 每种情况都可以吧, 组合数为 1
        // 于是, 第一列全为 1
        // 当然这个赋初值, 可以放到真正循环确定二维dp数组值时赋值也是可以的, 还减少了在此处赋值的循环次数
        for (int i = 0; i < n + 1; i++) {
            dp[i][0] = 1;
        }

        // 依次遍历得到有 i 种硬币时, 凑成金额为 j 的组合数 dp[i][j], 那么这个 dp[i][j] 是多少呢? 分两种情况:
        // 1.在上一次循环中, 当没有第 i 种硬币时, 即当只有前 i - 1 种硬币时, 它凑成金额为 j 的组合数为 dp[i - 1][j], dp[i][j] 肯定是要包含它的对吧?
        // 2.当肯定有第 i 种硬币(至少一枚, 金额为 coins[i - 1])时, 这种情况的组合数值就等价于
        // 要从这 i 种硬币中 凑成金额为 j - coins[i - 1] 的组合数, 其值为 dp[i][j - coins[i - 1]]
        // 但是在此处, 应该要特别注意当前要凑成的金额数 j 与 第 i 种硬币的价值大小(coins[i - 1])
        // 如果 j < coins[i - 1], 那么这种情况根本不能存在啊
        // 就比如: 第 i 种硬币的价值是 5, 现在拿它和前面的 i - 1 种硬币去凑出金额为 4 或者比 4 小的一定要包含 5 这种硬币的组合, 这显然不可能嘛
        // 于是乎, 在这种情况下的 dp[i][j] 值就为 dp[i - 1][j], 第二种情况的值为 0
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < amount + 1; j++) {
                if (j >= coins[i - 1]) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        // 最终 dp[n][amount] 就为 n 种硬币凑成金额为 amount 的组合数
        return dp[n][amount];
    }

    // 二维动态规划 代码简化
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
        // 二维动态规划
        // 行表示有 0 种, 1 种, 2 种, ..., n 种硬币, 每种硬币的价值保存在 coins[] 数组中, 共 n + 1 行
        // 列表示要凑成的金额为 0, 1, 2, ..., amount, 共 amount + 1 列
        // dp[i][j] 表示当有 i 种硬币时, 需要把这 i 种硬币凑出金额为 j 的组合数
        int[][] dp = new int[n + 1][amount + 1];
        // 对特殊情况下的 dp数组 进行初始化赋值, 一般是 i 或 j 为 0 的初始情况
        // 首先, 考虑 dp[0][0], 0 种硬币凑成金额为 0 的组合数? 是多少呢? 为 1 对吧?
        // 然后, 判断第一行, 第一行表示 0 种硬币凑成金额为 j(j 在 0 到 amount 之间) 的组合数?
        // 好像除了能凑出金额 0 之外, 凑不出其它金额了吧? 故第一行除了 dp[0][0] 为 1, 其余均为 0
        // 最后, 判断第一列, 第一列表示可能有若干种硬币要凑成金额为 0 的组合数? 每种情况都可以吧, 组合数为 1
        // 于是, 第一列全为 1
        // 当然这个赋初值, 可以放到真正循环确定二维dp数组值时赋值也是可以的, 还减少了在此处赋值的循环次数
//        for (int i = 0; i < n + 1; i++) {
//            dp[i][0] = 1;
//        }

        // 依次遍历得到有 i 种硬币时, 凑成金额为 j 的组合数 dp[i][j], 那么这个 dp[i][j] 是多少呢? 分两种情况:
        // 1.在上一次循环中, 当没有第 i 种硬币时, 即当只有前 i - 1 种硬币时, 它凑成金额为 j 的组合数为 dp[i - 1][j], dp[i][j] 肯定是要包含它的对吧?
        // 2.当肯定有第 i 种硬币(至少一枚, 金额为 coins[i - 1])时, 这种情况的组合数值就等价于
        // 要从这 i 种硬币中 凑成金额为 j - coins[i - 1] 的组合数, 其值为 dp[i][j - coins[i - 1]]
        // 但是在此处, 应该要特别注意当前要凑成的金额数 j 与 第 i 种硬币的价值大小(coins[i - 1])
        // 如果 j < coins[i - 1], 那么这种情况根本不能存在啊
        // 就比如: 第 i 种硬币的价值是 5, 现在拿它和前面的 i - 1 种硬币去凑出金额为 4 或者比 4 小的一定要包含 5 这种硬币的组合, 这显然不可能嘛
        // 于是乎, 在这种情况下的 dp[i][j] 值就为 dp[i - 1][j], 第二种情况的值为 0
        for (int i = 1; i < n + 1; i++) {
            dp[i][0] = 1;
            for (int j = 1; j < amount + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= coins[i - 1]) {
                    dp[i][j] += dp[i][j - coins[i - 1]];
                }
            }
        }
        // 最终 dp[n][amount] 就为 n 种硬币凑成金额为 amount 的组合数
        return dp[n][amount];
    }
}

// 一维动态规划(滚动数组思想)
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

        // 一维动态规划
        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int i = 1; i < n + 1; i++) {
            for (int j = coins[i - 1]; j < amount + 1; j++) {
                dp[j] += dp[j - coins[i - 1]];
            }
        }

        // 最终 dp[amount] 就为 n 种硬币凑成金额为 amount 的组合数
        return dp[amount];
    }
}