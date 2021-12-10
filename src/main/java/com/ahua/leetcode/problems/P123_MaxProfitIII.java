package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-10 22:17
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 123. 买卖股票的最佳时机 III
 * <p>
 * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
 * <p>
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
 * <p>
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * 1 <= prices.length <= 105
 * 0 <= prices[i] <= 105
 */

public class P123_MaxProfitIII {
    public static void main(String[] args) {
        int[] prices = new int[]{3, 3, 5, 0, 0, 3, 1, 4};
        int[] prices1 = new int[]{1, 2, 3, 4, 5};
        int[] prices2 = new int[]{7, 6, 4, 3, 1};
        int[] prices3 = new int[]{1};
        // 这个案例起初我的算法(没用动态规划)真是没有考虑到
        int[] prices4 = new int[]{1, 2, 4, 2, 5, 7, 2, 4, 9, 0};
        int[] prices5 = new int[]{1, 2, 4, 2, 5, 7, 2, 3, 9, 0};
        // 又一错误案例
        int[] prices6 = new int[]{6, 5, 4, 8, 6, 8, 7, 8, 9, 4, 5};

        P123_Solution solution = new P123_Solution();
        System.out.println(solution.maxProfit(prices)); // 6 = (3-0) + (4-1)
        System.out.println(solution.maxProfit(prices1)); // 4
        System.out.println(solution.maxProfit(prices2)); // 0
        System.out.println(solution.maxProfit(prices3)); // 0
        System.out.println(solution.maxProfit(prices4)); // 13 = (7-1) + (9-2)
        System.out.println(solution.maxProfit(prices5)); // 13 = (7-1) + (9-2)
        System.out.println(solution.maxProfit(prices6)); // 7 = (8-4) + (9-6)
    }
}

// 动态规划
/* 此案例出错 {6, 5, 4, 8, 6, 8, 7, 8, 9, 4, 5} */
// 错误的 dp[i][1]
// System.out.println("dp[" + i + "][1] = max(" + (prices[i] - prices[index] + dp[index][0]) + ", " + (prices[i] - prices[minPriceIndex] + dp[minPriceIndex][0]) + ", " + dp[i - 1][1] + ")");
// dp[i][1] = Math.max(Math.max(prices[i] - prices[index] + dp[index][0], prices[i] - prices[minPriceIndex] + dp[minPriceIndex][0]), dp[i - 1][1]);
// 原因在于 (8-4) + (9-6) 这种组合没考虑到, 以为只能 (8-4) + (9-7)
// 故再结合 {1, 2, 4, 2, 5, 7, 2, 4, 9, 0} 这个案例的 13 = (7-1) + (9-2)
// dp[i][1] 的式子中应该 4 到 7 之间的值都要比较, 即所有的 prices[i] - prices[ind] + dp[ind][0]
class P123_Solution {
    public int maxProfit(int[] prices) {
        if (prices == null) {
            return 0;
        }
        int n = prices.length;
        if (n == 0 || n == 1) {
            return 0;
        }

        // dp[i][0] 买卖一次的最大利润
        // dp[i][1] 买卖两次的最大利润
        int[][] dp = new int[n][2];
        int index = 0;
        int minPriceIndex = 0;
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 1; i < n; i++) {
            if (prices[i] > prices[i - 1]) {
                // dp[i][0] = Math.max(Math.max(prices[i] - prices[minPriceIndex], prices[i] - prices[index]), dp[i - 1][0]);
                // 其中 Math.max(prices[i] - prices[minPriceIndex], prices[i] - prices[index]) 这个式子更利于理解
                // 但是由题意, 它可直接简化为 prices[i] - prices[minPriceIndex], 因为 prices[minPriceIndex] <= prices[index] 是恒定的
                // System.out.println("dp[" + i + "][0] = max(" + (prices[i] - prices[minPriceIndex]) + ", " + dp[i - 1][0] + ")");
                dp[i][0] = Math.max(prices[i] - prices[minPriceIndex], dp[i - 1][0]);

                // System.out.println("dp[" + i + "][1] = max(" + (prices[i] - prices[index] + dp[index][0]) + ", " + dp[i - 1][1] + ")");
                dp[i][1] = Math.max(prices[i] - prices[index] + dp[index][0], dp[i - 1][1]);
                for (Integer ind : hashSet) {
                    dp[i][1] = Math.max(prices[i] - prices[ind] + dp[ind][0], dp[i][1]);
                    // System.out.println("dp[" + i + "][1] 还要和这个 max 值比较 : " + (prices[i] - prices[ind] + dp[ind][0]));
                }
                // System.out.println("dp[" + i + "][1] 最终为 " + dp[i][1]);
                // System.out.println("此轮结束后 dp 数组为 " + Arrays.deepToString(dp) + "\n");
            } else {
                // prices[i] <= prices[i - 1]
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][1];

                // 此处 prices[minPriceIndex] <= prices[index] 是恒定的, minPriceIndex 与 index 也可能相等
                // 如果 prices[i] == prices[index], 只需执行 index = i;
                if (prices[i] > prices[index]) {
                    // 将 index 加入 hashSet
                    hashSet.add(index);
                } else if (prices[i] < prices[index]) {
                    if (prices[i] <= prices[minPriceIndex]) {
                        // prices[i] 为最低价格, 更新最低价格下标, set 清空, minPriceIndex 入 set
                        // 最低价格下标
                        minPriceIndex = i;
                        // 清空 hashSet
                        hashSet.clear();
                        // 将 minPriceIndex 加入 hashSet
                        hashSet.add(minPriceIndex);
                    } else {
                        // prices[index] > prices[i] > prices[minPriceIndex]
                        // 需要将 hashSet 中比 prices[i] 大的 prices[ind] 的 ind 移除

                        // 这种方式在 LeetCode 上会报如下错误
                        // java.util.ConcurrentModificationException
                        //  at line 1495, java.base/java.util.HashMap$HashIterator.nextNode
                        //  at line 1518, java.base/java.util.HashMap$KeyIterator.next
//                        for (Integer ind : hashSet) {
//                            if (prices[ind] >= prices[i]) {
//                                hashSet.remove(ind);
//                            }
//                        }
                        // 改为迭代器就可以了, 这是一个新知识点啊哈哈
                        Iterator<Integer> iterator = hashSet.iterator();
                        while (iterator.hasNext()) {
                            if (prices[iterator.next()] >= prices[i]) {
                                iterator.remove();
                            }
                        }
                    }
                }
                index = i;
            }
        }
        return dp[n - 1][1];
    }
}


/* 错误做法 通过 193 / 214 */
/* 这个案例让我意识到 我的这个做法是错误的 {1, 2, 4, 2, 5, 7, 2, 4, 9, 0} */
/* 看来还是得用动态规划噢 */
//class P123_Solution {
//    int firstMax;
//    int secondMax;
//
//    public int maxProfit(int[] prices) {
//        if (prices == null) {
//            return 0;
//        }
//        int n = prices.length;
//        if (n == 0 || n == 1) {
//            return 0;
//        }
//
//        firstMax = 0;
//        secondMax = 0;
//        int diffPrice = 0;
//        for (int i = 1; i < n; i++) {
//            if (prices[i] >= prices[i - 1]) {
//                diffPrice += prices[i] - prices[i - 1];
//            } else {
//                if (diffPrice > 0) {
//                    updateTwoMaxProfit(diffPrice);
//                }
//                diffPrice = 0;
//            }
//        }
//        if (diffPrice > 0) {
//            updateTwoMaxProfit(diffPrice);
//            // diffPrice = 0;
//        }
//        return firstMax + secondMax;
//    }
//
//    public void updateTwoMaxProfit(int newProfit) {
//        if (newProfit >= firstMax) {
//            secondMax = firstMax;
//            firstMax = newProfit;
//        } else {
//            if (newProfit >= secondMax) {
//                secondMax = newProfit;
//            }
//        }
//    }
//}
