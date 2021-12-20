package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-20 21:02
 */

/**
 * 1411. 给 N x 3 网格图涂色的方案数
 * <p>
 * 你有一个 n x 3 的网格图 grid ，你需要用 红，黄，绿 三种颜色之一给每一个格子上色，且确保相邻格子颜色不同（也就是有相同水平边或者垂直边的格子颜色不同）。
 * <p>
 * 给你网格图的行数 n 。
 * <p>
 * 请你返回给 grid 涂色的方案数。由于答案可能会非常大，请你返回答案对 10^9 + 7 取余的结果。
 * <p>
 * n == grid.length
 * grid[i].length == 3
 * 1 <= n <= 5000
 */
public class P1411_NumOfWays {
    public static void main(String[] args) {
        P1411_Solution solution = new P1411_Solution();

        System.out.println(solution.numOfWays(1)); // 12
        System.out.println(solution.numOfWays(2)); // 54
        System.out.println(solution.numOfWays(3)); // 346
        System.out.println(solution.numOfWays(7)); // 106494
        System.out.println(solution.numOfWays(5000)); // 30228214
    }
}

class P1411_Solution {
    public int numOfWays(int n) {
        final int divisor = 1000000007;
        long numABC;
        long numABA;
        // dp[0] 表示从前到这一行的三种颜色不一样(ABC)的情况的个数
        // dp[1] 表示从前到这一行的有两种颜色(ABA)的情况的个数
        long[] dp = new long[2];
        dp[0] = 6;
        dp[1] = 6;
        for (int i = 1; i < n; i++) {
            // 保存上一行的 ABC 和 ABA 情况
            numABC = dp[0];
            numABA = dp[1];

            // 下一行的 ABC 情况为上一行的 ABC 的 2 倍 + ABA 的 2 倍
            dp[0] = (numABC + numABA) * 2 % divisor;
            // 下一行的 ABA 情况为上一行的 ABC 的 2 倍 + ABA 的 3 倍
            dp[1] = (numABC * 2 + numABA * 3) % divisor;
        }
        // 返回两数之和
        return (int) (dp[0] + dp[1]) % divisor;
    }
}