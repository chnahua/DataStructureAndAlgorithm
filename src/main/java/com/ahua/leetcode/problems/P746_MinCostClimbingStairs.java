package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-05 20:33
 */

/**
 * 数组的每个下标作为一个阶梯，第 i 个阶梯对应着一个非负数的体力花费值 cost[i]（下标从 0 开始）。
 * <p>
 * 每当你爬上一个阶梯你都要花费对应的体力值，一旦支付了相应的体力值，你就可以选择向上爬一个阶梯或者爬两个阶梯。
 * <p>
 * 请你找出达到楼层顶部的最低花费。在开始时，你可以选择从下标为 0 或 1 的元素作为初始阶梯。
 * <p>
 * cost 的长度范围是 [2, 1000]。
 * cost[i] 将会是一个整型数据，范围为 [0, 999] 。
 */
public class P746_MinCostClimbingStairs {
    public static void main(String[] args) {
        // 最低花费是从 cost[1] 开始，然后走两步即可到阶梯顶，一共花费 15
        int[] cost = new int[]{10, 15, 20};
        // 最低花费方式是从 cost[0] 开始，逐个经过那些 1 ，跳过 cost[3] ，一共花费 6
        int[] cost1 = new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        // 出错的案例
        int[] cost2 = new int[]{1, 100};
        P746_Solution solution = new P746_Solution();
        System.out.println(solution.minCostClimbingStairs(cost));
        System.out.println(solution.minCostClimbingStairs(cost1));
        System.out.println(solution.minCostClimbingStairs(cost2));
    }
}

class P746_Solution {
    // 我的解法, 感觉还是我的好理解些
    public int minCostClimbingStairs(int[] cost) {
        // 初始化 dp[2] 为 0 或者 Math.min(cost[0], cost[1]) 均可,
        // 若初始化为 后者, 则 return 语句返回的是 Math.min(dp[0], dp[2]);
        int[] dp = new int[]{cost[0], cost[1], 0};
        // 达到第 i 个阶梯最低花费
        for (int i = 2; i < cost.length; i++) {
            // dp[2] 表示要达到第 i 个阶梯的最低花费(i 是下标), 它等于
            // 达到(第 i - 2 个阶梯的最低花费(dp[0]) 与 第 i - 1 个阶梯的最低花费(dp[1])中的较小值) + 达到第 i 个阶梯的花费(cost[i])
            dp[2] = Math.min(dp[0], dp[1]) + cost[i];
            dp[0] = dp[1];
            dp[1] = dp[2];
        }
        // 最后结束时, dp[1] 与 dp[2] 为达到最后一个(n - 1)阶梯的最低花费,
        // dp[0] 为达到第 n - 2 个阶梯的最低花费
        // 故最后返回 dp[0] 与 dp[1] 的较小值
        return Math.min(dp[0], dp[1]);
    }

    // 思维方式有些变化, 等同于官方答案
    public int minCostClimbingStairs1(int[] cost) {
        // dp[] 不需要初始化
        int[] dp = new int[]{0, 0, 0};
        for (int i = 2; i <= cost.length; i++) {
            dp[2] = Math.min(dp[0] + cost[i - 2], dp[1] + cost[i - 1]);
            dp[0] = dp[1];
            dp[1] = dp[2];
        }
        return dp[2];
    }
}
