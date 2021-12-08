package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-07 21:56
 */


/**
 * 121. 买卖股票的最佳时机
 * <p>
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * <p>
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * <p>
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 * <p>
 * 1 <= prices.length <= 10^5
 * 0 <= prices[i] <= 10^4
 */
public class P121_MaxProfit {
    public static void main(String[] args) {
        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        int[] prices1 = new int[]{7, 6, 4, 3, 1};
        // 我想到一个很好用例
        int[] prices2 = new int[]{2, 5, 1, 3};
        P121_Solution solution = new P121_Solution();
        System.out.println(solution.maxProfit(prices)); // 5
        System.out.println(solution.maxProfit(prices1)); // 0
    }
}

// 动态规划
class P121_Solution {
    // 动态规划
    // 时间复杂度 O(n)
    // 空间复杂度 O(n)
    // 无注释, 最初版本, dp 定义为 int[] dp = new int[n + 1];
    public int maxProfit0(int[] prices) {
        if (prices == null) {
            return 0;
        }
        int n = prices.length;
        if (n == 0 || n == 1) {
            return 0;
        }

        int minPriceIndex = 0;
        int[] dp = new int[n + 1];

        for (int i = 1; i < n + 1; i++) {
            if (prices[i - 1] > prices[minPriceIndex]) {
                dp[i] = Math.max(prices[i - 1] - prices[minPriceIndex], dp[i - 1]);
            } else {
                dp[i] = dp[i - 1];
                minPriceIndex = i - 1;
            }
        }
        return dp[n];
    }

    // 动态规划
    // 时间复杂度 O(n)
    // 空间复杂度 O(n)
    // 有注释, 第二个版本, dp 定义为 int[] dp = new int[n];
    public int maxProfit1(int[] prices) {
        // 特殊情况处理
        if (prices == null) {
            return 0;
        }
        // 天数
        int n = prices.length;
        // 特殊情况处理
        if (n == 0 || n == 1) {
            return 0;
        }

        int[] dp = new int[n];

        // 价格最小的天数索引
        int minPriceIndex = 0;
        for (int i = 1; i < n; i++) {
            // 如果第 i 天比之前的所有天中的最低价格高
            // 那么dp[i]等于, 在今天选择卖出股票或者不卖出股票就可能取得到这前 i 天能卖出的最大值
            // 卖出股票——利润为 prices[i] - prices[minPriceIndex]
            // 不卖股票——利润为前一天的 dp 值 dp[i-1]
            if (prices[i] > prices[minPriceIndex]) {
                dp[i] = Math.max(prices[i] - prices[minPriceIndex], dp[i - 1]);
            } else {
                // 如果今天的股票价格是历史最低的话, 可以认为如果在今天买入, 在日后某天卖出的话, 也许会得到更高的利润
                // 而由于今天的股票价格是历史最低, 那么截至到今天, 今天能赚到的最大利润为前 i 天能卖出的最大值, 也就是 dp[i-1]
                dp[i] = dp[i - 1];
                // 更新最小价格的索引位置
                minPriceIndex = i;
            }
        }
        return dp[n - 1];
    }

    // 动态规划(滚动数组思想)
    // 时间复杂度 O(n)
    // 空间复杂度 O(1)
    // 优化版本, dp 定义为 int[] dp = new int[2];
    public int maxProfit(int[] prices) {
        // 特殊情况处理
        if (prices == null) {
            return 0;
        }
        // 天数
        int n = prices.length;
        // 特殊情况处理
        if (n == 0 || n == 1) {
            return 0;
        }

        // 最大利润
        int dp = 0;

        // 价格最小的天数索引, 可把这个最小价格索引改为直接保存最小价格值 minPrice
        int minPriceIndex = 0;
        for (int i = 1; i < n; i++) {
            // 如果第 i 天比之前的所有天中的最低价格高
            // 那么dp[i]等于, 在今天选择卖出股票或者不卖出股票就可能取得到这前 i 天能卖出的最大值
            // 卖出股票——利润为 prices[i] - prices[minPriceIndex]
            // 不卖股票——利润为前一天的 dp 值, 就为 dp 本身
            if (prices[i] > prices[minPriceIndex]) {
                dp = Math.max(prices[i] - prices[minPriceIndex], dp);
            } else {
                // 如果今天的股票价格是历史最低的话, 可以认为如果在今天买入, 在日后某天卖出的话, 也许会得到更高的利润
                // 而由于今天的股票价格是历史最低, 那么截至到今天, 今天能赚到的最大利润为前 i 天能卖出的最大值, 也就是 dp 值, 不需要更新了
                // 更新最小价格的索引位置
                minPriceIndex = i;
            }
        }
        return dp;
    }
}