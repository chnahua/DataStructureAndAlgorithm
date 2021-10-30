package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-10-30 18:04
 */

/**
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）
 *
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）
 *
 * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
 *
 * 网格中的障碍物和空位置分别用 1 和 0 来表示
 *
 * m == obstacleGrid.length
 * n == obstacleGrid[i].length
 * 1 <= m, n <= 100
 * obstacleGrid[i][j] 为 0 或 1
 */

public class P63_UniquePathsWithObstacles {
    public static void main(String[] args) {
        int[][] obstacleGrid = new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        // 做题过程中遇到的错误用例
        int[][] obstacleGrid1 = new int[][]{{0, 1}, {0, 0}};
        // 右下角为障碍物
        int[][] obstacleGrid2 = new int[][]{{0, 0}, {0, 1}};
        // 某一行全为障碍物
        int[][] obstacleGrid3 = new int[][]{{0, 0}, {1, 1}, {0, 0}};
        P63_Solution solution = new P63_Solution();
        System.out.println(solution.uniquePathsWithObstacles(obstacleGrid)); // 2
        System.out.println(solution.uniquePathsWithObstacles(obstacleGrid1)); // 1
        System.out.println(solution.uniquePathsWithObstacles(obstacleGrid2)); // 0
        System.out.println(solution.uniquePathsWithObstacles(obstacleGrid3)); // 0
    }
}

class P63_Solution {
    // 时间复杂度: O(nm), 其中 m 为网格的行数, n 为网格的列数, 只需要遍历所有网格一次即可
    // 空间复杂度: O(m)O(m), 利用滚动数组优化, 可以只用 O(n) 大小的空间来记录当前行到右下角的 path 值
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // 行
        int m = obstacleGrid.length;
        // 列
        int n = obstacleGrid[0].length;
        // 右下角为障碍物, 返回 0
        if (obstacleGrid[m - 1][n - 1] == 1) {
            return 0;
        }
        // 只有一行且无障碍物, 返回 1
        if (m == 1) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[0][j] == 1) {
                    return 0;
                }
            }
            return 1;
        }
        // 只有一列且无障碍物, 返回 1
        if (n == 1) {
            for (int i = 0; i < m; i++) {
                if (obstacleGrid[i][0] == 1) {
                    return 0;
                }
            }
            return 1;
        }

        // 「滚动数组思想」把空间复杂度优化成 O(n)
        int[] dp = new int[n];
        dp[n - 1] = 1;

        for (int i = m - 1; i >= 0; i--) {
            // 等价于 dp[n - 1] = (obstacleGrid[i][n - 1] != 1) ? dp[n - 1] : 0;
            if (obstacleGrid[i][n - 1] == 1) {
                dp[n - 1] = 0;
            }
            for (int j = n - 2; j >= 0; j--) {
                // 等价于 dp[j] = (obstacleGrid[i][j] != 1) ? dp[j] + dp[j + 1] : 0;
                // 有障碍物, 可提前结束循环
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0;
                    continue;
                }
                dp[j] += dp[j + 1];
            }
        }
        return dp[0];
//        // 也可以为
//        // 「滚动数组思想」把空间复杂度优化成 O(n)
//        int[] dp = new int[n + 1];
//        dp[n - 1] = 1;
//
//        for (int i = m - 1; i >= 0; i--) {
//            for (int j = n - 1; j >= 0; j--) {
//                // 等价于 dp[j] = (obstacleGrid[i][j] != 1) ? dp[j] + dp[j + 1] : 0;
//                if (obstacleGrid[i][j] == 1) {
//                    dp[j] = 0;
//                    continue;
//                }
//                dp[j] += dp[j + 1];
//            }
//        }
//        return dp[0];
    }
}
