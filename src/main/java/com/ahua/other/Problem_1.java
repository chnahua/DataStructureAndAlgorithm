package com.ahua.other;

/**
 * @author huajun
 * @create 2021-12-28 1:59
 */

import java.util.Arrays;

/**
 * 小明妈带小明去超市买东西，超市中一共有n件商品，知道每件商品的价值，要求在必须拿够m件（m<n）商品的情况下，
 * 尽可能拿最多价值，但又不超过妈妈带的钱x，求最大能拿的价值数。
 * <p>
 * 这题乍一看是动态规划中的背包问题，实际写的时候发现后面的最优状态根本不是从前面最优状态推过来的。
 * 例如 n = 5,m = 3,x=100 商品价值列表为[15 20 35 40 50]，前4个商品中能去取到的最大值来源于20 35 40，
 * 而前5个商品中能去取到的最大值来源于15 35 50.
 * <p>
 * 作者：Sid不是种子
 * 链接：https://leetcode-cn.com/circle/discuss/Of0IQM/
 */
public class Problem_1 {
    public static void main(String[] args) {
        int[] value = new int[]{15, 20, 35, 40, 50};
        O1_solution solution = new O1_solution();
        System.out.println(solution.maxValue(value, 3, 100)); // 100 = 15 + 35 + 50
    }
}

// 未解决
class O1_solution {
    public int maxValue(int[] value, int m, int total) {
        // 总共 n 件商品
        int n = value.length;
        // 在有 i(0-n) 件商品时, 购买 j(0-m) 件能够得到的最大价值数
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                int sum = dp[i - 1][j - 1] + value[i - 1];
                if (sum <= total) {
                    dp[i][j] = Math.max(sum, dp[i - 1][j]);
                } else {
                    // 这里存在问题, 此处不一定取这个值
                    // 感觉这道题应该是一道状态压缩 DP
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        // System.out.println(Arrays.deepToString(dp));
        return dp[n][m];
    }
}