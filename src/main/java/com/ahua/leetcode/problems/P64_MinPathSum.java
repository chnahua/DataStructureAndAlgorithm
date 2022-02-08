package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-08 20:29
 */

import java.util.Arrays;

/**
 * 64. 最小路径和 minimum-path-sum
 * 给定一个包含非负整数的 m x n 网格 grid ，
 * 请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * <p>
 * 说明：每次只能向下或者向右移动一步。
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 200
 * 0 <= grid[i][j] <= 100
 * <p>
 * 这道题好像和 不同路径 那道题很像
 */
public class P64_MinPathSum {
    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };
        int[][] grid1 = new int[][]{
                {1, 2, 3},
                {4, 5, 6}
        };
        P64_Solution solution = new P64_Solution();
        System.out.println(solution.minPathSum(grid)); // 7
        System.out.println(solution.minPathSum(grid1)); // 12
    }
}

// 动态规划
class P64_Solution {
    // 时间复杂度 O(MN), 2 ms 95.93%
    // 空间复杂度 O(MN), 44.1 MB 5.06%
    public int minPathSum1(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        // 右下角终点处
        dp[m - 1][n - 1] = grid[m - 1][n - 1];
        // 最后一行 m-1
        for (int j = n - 2; j >= 0; j--) {
            dp[m - 1][j] = dp[m - 1][j + 1] + grid[m - 1][j];
        }
        // 最后一列 n-1
        for (int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = dp[i + 1][n - 1] + grid[i][n - 1];
        }
        // 其余 m-2 行 n-2 列
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp[i][j] = Math.min(dp[i][j + 1], dp[i + 1][j]) + grid[i][j];
            }
        }/*
        for (int[] row : dp) {
            System.out.println(Arrays.toString(row));
        }*/
        return dp[0][0];
    }

    // 滚动数组思想优化空间复杂度为 O(N)
    // 时间复杂度 O(MN), 2 ms 95.93%
    // 空间复杂度 O(N), 43.7 MB 9.23%
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];
        // 右下角终点处
        dp[n - 1] = grid[m - 1][n - 1];
        // 最后一行 m-1
        for (int j = n - 2; j >= 0; j--) {
            dp[j] = dp[j + 1] + grid[m - 1][j];
        }
        System.out.println(Arrays.toString(dp));
        // 其余 m-2 行
        for (int i = m - 2; i >= 0; i--) {
            dp[n - 1] += grid[i][n - 1];
            for (int j = n - 2; j >= 0; j--) {
                dp[j] = Math.min(dp[j + 1], dp[j]) + grid[i][j];
            }
            System.out.println(Arrays.toString(dp));
        }
        return dp[0];
    }
}