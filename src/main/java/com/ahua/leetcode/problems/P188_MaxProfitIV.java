package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-12 23:50
 */

/**
 * 188. 买卖股票的最佳时机 IV
 * <p>
 * 给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。
 * <p>
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
 * <p>
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * 0 <= k <= 100
 * 0 <= prices.length <= 1000
 * 0 <= prices[i] <= 1000
 */
public class P188_MaxProfitIV {
    public static void main(String[] args) {
        int[] prices = new int[]{2, 4, 1};
        int[] prices1 = new int[]{3, 2, 6, 5, 0, 3};
        P188_Solution solution = new P188_Solution();
        System.out.println(solution.maxProfit(2, prices)); // 2
        System.out.println(solution.maxProfit(2, prices1)); // 7
    }
}

// 模仿着 P123_买卖股票的最佳时机III 做, 一次通过哈哈哈
class P188_Solution {
    // dp 数组定义为 dp[k * 2] 大小
    public int maxProfit1(int k, int[] prices) {
        if (prices == null || k == 0) {
            return 0;
        }
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }

        // count == k * 2;
        // k 为交易次数, count 为总买卖次数, 交易次数的两倍
        final int count = k * 2;
        // dp 数组
        int[] dp = new int[count];
        // 赋初值
        for (int i = 0; i < count; i += 2) {
            dp[i] = -prices[0];
            dp[i + 1] = 0;
        }
        // 动态规划
        // 第 i 天时, 第 j 次买和卖的最大利润
        for (int i = 1; i < n; i++) {
            // 第 i 天第 1 次买获得的最大利润
            dp[0] = Math.max(dp[0], -prices[i]);
            // 第 i 天第 1 次卖获得的最大利润
            dp[1] = Math.max(dp[1], dp[0] + prices[i]);
            for (int j = 2; j < count; j += 2) {
                // 第 i 天第 j 次买获得的最大利润 为
                // (第 i-1 天第 j 次买) 与 (第 j-1 次卖 - 第 i 天第 j 次买的这只股票的价格(prices[i])) 中的较大值
                dp[j] = Math.max(dp[j], dp[j - 1] - prices[i]);
                // 第 i 天第 j 次卖获得的最大利润 为
                // (第 i-1 天第 j 次卖) 与 (第 j 次买 + 第 i 天第 j 次卖的这只股票的价格(prices[i])) 中的较大值
                dp[j + 1] = Math.max(dp[j + 1], dp[j] + prices[i]);
            }
        }
        return dp[count - 1];
    }

    // 将 dp 数组定义为 dp[k * 2] 大小, 确实复杂了好多, 改为两个 k 大小的数组要好很多
    // 这效率居然还没有上一个高？就挺意外的。难道是两个数组在赋值寻找索引位置时的查找时间更长？
    public int maxProfit(int k, int[] prices) {
        if (prices == null || k == 0) {
            return 0;
        }
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }

        // dp 数组
        int[] buy = new int[k];
        int[] sell = new int[k];
        // 赋初值
        for (int i = 0; i < k; i++) {
            buy[i] = -prices[0];
            sell[i] = 0;
        }
        // 动态规划
        // 第 i 天时, 第 j 次买和卖的最大利润
        for (int i = 1; i < n; i++) {
            // 第 i 天第 1 次买获得的最大利润
            buy[0] = Math.max(buy[0], -prices[i]);
            // 第 i 天第 1 次卖获得的最大利润
            sell[0] = Math.max(sell[0], buy[0] + prices[i]);
            for (int j = 1; j < k; j++) {
                // 第 i 天第 j 次买获得的最大利润 为
                // (第 i-1 天第 j 次买) 与 (第 j-1 次卖 - 第 i 天第 j 次买的这只股票的价格(prices[i])) 中的较大值
                buy[j] = Math.max(buy[j], sell[j - 1] - prices[i]);
                // 第 i 天第 j 次卖获得的最大利润 为
                // (第 i-1 天第 j 次卖) 与 (第 j 次买 + 第 i 天第 j 次卖的这只股票的价格(prices[i])) 中的较大值
                sell[j] = Math.max(sell[j], buy[j] + prices[i]);
            }
        }
        return sell[k - 1];
    }
}