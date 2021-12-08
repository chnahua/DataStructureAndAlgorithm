package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-08 19:56
 */

/**
 * 122. 买卖股票的最佳时机 II
 * <p>
 * 给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。
 * <p>
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 * <p>
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * 1 <= prices.length <= 3 * 10^4
 * 0 <= prices[i] <= 10^4
 */

public class P122_MaxProfitII {
    public static void main(String[] args) {
        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        int[] prices1 = new int[]{1, 2, 3, 4, 5};
        int[] prices2 = new int[]{7, 6, 4, 3, 1};
        P122_Solution solution = new P122_Solution();
        System.out.println(solution.maxProfit(prices)); // 7 = (5-1)+(6-3)
        System.out.println(solution.maxProfit(prices1)); // 4 = 5-1
        System.out.println(solution.maxProfit(prices2)); // 0
    }
}

class P122_Solution {
    // 动态规划
    // 时间复杂度 O(n)
    // 空间复杂度 O(n)
    public int maxProfit1(int[] prices) {
        if (prices == null) {
            return 0;
        }
        int n = prices.length;
        if (n == 0 || n == 1) {
            return 0;
        }
        int[] dp = new int[n];

        /* 第一种方式, 每天与前几天中的最低历史价格相关, 代码实现上已经有些不像动态规划了 */
//        int minPriceIndex = 0;
//        for (int i = 1; i < n; i++) {
//            if (prices[i] > prices[i - 1]) {
//                dp[i] = dp[minPriceIndex] + (prices[i] - prices[minPriceIndex]);
//            } else {
//                dp[i] = dp[i - 1];
//                minPriceIndex = i;
//            }
//        }
//        return dp[n - 1];

        /* 第二种方式, 每天与前一天的价格相关, 而不是与前几天中的最低历史价格相关 */
        for (int i = 1; i < n; i++) {
            // 如果今天比前一天的价格高, 在答案结果上就等于
            // 昨天以前(包括昨天)总共获得的利润 dp[i - 1] + 昨天买的、今天卖了的差价(prices[i] - prices[i - 1])
            if (prices[i] > prices[i - 1]) {
                dp[i] = dp[i - 1] + (prices[i] - prices[i - 1]);
            } else {
                // 如果今天比前一天低, 那么今天肯定是不卖的, 有可能买,
                // 但是具体买不买要根据后一天的价格是否比今天高来决定,
                // 如果明天的价格比今天的高, 那么当然是今天要买, 明天再卖就能获利了
                // 如果明天的价格比今天的低, 那么今天就不要买, 应该在明天再买
                // 因此, 今天不卖的话, 就意味着今天不获利, 到今天为止转的钱 dp[i] 之就等于昨天赚的钱 dp[i-1]
                dp[i] = dp[i - 1];
            }
        }
        return dp[n - 1];
    }

    // 动态规划(优化 滚动数组思想)
    // 时间复杂度 O(n)
    // 空间复杂度 O(1)
    public int maxProfit(int[] prices) {
        if (prices == null) {
            return 0;
        }
        int n = prices.length;
        if (n == 0 || n == 1) {
            return 0;
        }

        // 由上一个动态规划实现, 我们知道由于截止到今天的获利 dp[i] 只和截止到前一天的获利 dp [i-1] 有关
        // 于是 dp 数组可以进行压缩滚动, 不必用一个 O(n) 长度的数组记录截至到每天的获利情况, 只需要一个变量即可
        // int[] dp = new int[n]; 改为
        int dp = 0;

        /* 第二种方式, 每天与前一天的价格相关, 而不是与前几天中的最低历史价格相关 */
        for (int i = 1; i < n; i++) {
            // 如果今天比前一天的价格高, 在答案结果上就等于
            // 昨天以前(包括昨天)总共获得的利润 dp[i - 1] + 昨天买的、今天卖了的差价(prices[i] - prices[i - 1])
            if (prices[i] > prices[i - 1]) {
                // dp[i] = dp[i - 1] + (prices[i] - prices[i - 1]); 改为
                dp += prices[i] - prices[i - 1];
            }/* else {
                // 如果今天比前一天低, 那么今天肯定是不卖的, 有可能买,
                // 但是具体买不买要根据后一天的价格是否比今天高来决定,
                // 如果明天的价格比今天的高, 那么当然是今天要买, 明天再卖就能获利了
                // 如果明天的价格比今天的低, 那么今天就不要买, 应该在明天再买
                // 因此, 今天不卖的话, 就意味着今天不获利, 到今天为止转的钱 dp[i] 之就等于昨天赚的钱 dp[i-1]
                // dp[i] = dp[i - 1];
            }*/
        }

        // return dp[n - 1]; 改为
        return dp;
    }
    // 做完看了答案后才知道, 原来官方将我的这种动态规划做法, 认为是贪心算法
    // 并且解释中: 贪心算法只能用于计算最大利润, 计算的过程并不是实际的交易过程。
    // 这个和我的想法一致
}