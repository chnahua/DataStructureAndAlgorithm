package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-17 21:14
 */

/**
 * 688. 骑士在棋盘上的概率 knight-probability-in-chessboard
 * 在一个 n x n 的国际象棋棋盘上，一个骑士从单元格 (row, column) 开始，并尝试进行 k 次移动。
 * 行和列是 从 0 开始 的，所以左上单元格是 (0,0) ，右下单元格是 (n - 1, n - 1) 。
 * <p>
 * 象棋骑士有8种可能的走法，如下图所示。每次移动在基本方向上是两个单元格，然后在正交方向上是一个单元格。
 * <p>
 * 每次骑士要移动时，它都会随机从8种可能的移动中选择一种(即使棋子会离开棋盘)，然后移动到那里。
 * <p>
 * 骑士继续移动，直到它走了 k 步或离开了棋盘。
 * <p>
 * 返回 骑士在棋盘停止移动后仍留在棋盘上的概率 。
 * <p>
 * 1 <= n <= 25
 * 0 <= k <= 100
 * 0 <= row, column <= n
 */
public class P688_KnightProbability {
    public static void main(String[] args) {
        P688_Solution solution = new P688_Solution();
        /*
         * 在第一步的时候，每一个方向的概率为1/8，走到（1,2）和（2,1）这两个位置可以留在棋盘；
         *
         * 当第一步走到（1,2）这个位置进行走第二步，留在棋盘的概率为1/4，所有这种方案的留在棋盘的总概率为(1/8)*(1/4);
         *
         * 当第一步走到（2,1）这个位置进行走第二步，留在棋盘的概率也为1/4，所有这种方案的留在棋盘的总概率也为(1/8)*(1/4)。
         *
         * 所有可以计算得到最终的概率为(1/8)*(1/4)+(1/8)*(1/4)=0.0625*/
        System.out.println(solution.knightProbability(3, 2, 0, 0)); // 0.0625
        System.out.println(solution.knightProbability(1, 0, 0, 0)); // 1.00000
        System.out.println(solution.knightProbability(10, 13, 5, 3)); // 0.11734
    }
}

// 动态规划
class P688_Solution {
    // 上下左右
    static final int[][] dirs = {{-2, -1}, {-2, 1}, {2, -1}, {2, 1}, {-1, -2}, {1, -2}, {-1, 2}, {1, 2}};

    // 官方答案
    // O(k×n^2) O(k×n^2)
    // 8 ms 28.48%
    // 40.7 MB 11.33%
    // 第一种理解
    public double knightProbability1(int n, int k, int row, int column) {
        double[][][] dp = new double[k + 1][n][n];
        for (int step = 0; step <= k; step++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (step == 0) {
                        // 特别地，当点 (i,j) 不在棋盘上时，dp[step][i][j] = 0
                        // 当点 (i,j) 在棋盘上且 step=0 时，dp[step][i][j] = 1
                        // 即一步不走, 概率为 1
                        dp[step][i][j] = 1;
                    } else {
                        for (int[] dir : dirs) {
                            int x = i + dir[0];
                            int y = j + dir[1];
                            // 最开始理解成这样了, 可以做, 但是实现上有差别
                            // 走 step 步到达当前 (i,j) 位置处, 是由之前走 step - 1 步到达(i,j) 位置处的八个方向的位置走一步走过来的
                            // 故停留在当前处的概率为八个方向走到该点处的概率和
                            // 正确理解
                            // 从当前 (i,j) 位置处走 step 步仍停留在棋盘内的概率等于先(往八个方向)走一步,
                            // 再从八个方向处走 step - 1 步仍停留在棋盘内的概率和
                            if (x >= 0 && x < n && y >= 0 && y < n) {
                                dp[step][i][j] += dp[step - 1][x][y] / 8;
                            }
                        }
                    }
                }
            }
        }
        return dp[k][row][column];
    }

    // O(k×n^2) O(k×n^2)
    // 7 ms 52.10%
    // 40.7 MB 11.33%
    // 第二种理解(做法)
    public double knightProbability2(int n, int k, int row, int column) {
        double[][][] dp = new double[k + 1][n][n];
        // 特别地，当点 (i,j) 不在棋盘上时，dp[step][i][j] = 0
        // 当在起点处且 step=0 时, 只需要起点处概率为 1
        dp[0][row][column] = 1;
        double ans = 0;
        double ans2 = 0;
        for (int step = 1; step <= k; step++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int[] dir : dirs) {
                        int x = i + dir[0];
                        int y = j + dir[1];
                        // 第二种理解(做法)
                        // 最开始理解成这样了, 可以做, 但是实现上有差别
                        // 走 step 步到达当前 (i,j) 位置处, 是由之前走 step - 1 步到达(i,j) 位置处的八个方向的位置走一步走过来的
                        // 故停留在当前处的概率为八个方向走到该点处的概率和
                        if (x >= 0 && x < n && y >= 0 && y < n) {
                            dp[step][i][j] += dp[step - 1][x][y] / 8;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans += dp[k][i][j];
            }
        }
        return ans;
    }

    // 第一种理解做法的空间优化
    // 每一步的状态只由前一步决定, 滚动数组思想, 空间复杂度可以优化到 O(n^2)
    // O(k×n^2) O(n^2)
    // 6 ms 67.96%
    // 40.2 MB 21.69%
    public double knightProbability(int n, int k, int row, int column) {
        double[][] dp = new double[n][n];
        for (int step = 0; step <= k; step++) {
            double[][] nextDP = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (step == 0) {
                        // 特别地，当点 (i, j)(i,j) 不在棋盘上时，dp[step][i][j] = 0
                        // 当点 (i, j)(i,j) 在棋盘上且 step=0 时，dp[step][i][j] = 1
                        // 即一步不走, 概率为 1
                        nextDP[i][j] = 1;
                    } else {
                        for (int[] dir : dirs) {
                            int x = i + dir[0];
                            int y = j + dir[1];
                            if (x >= 0 && x < n && y >= 0 && y < n) {
                                nextDP[i][j] += dp[x][y] / 8;
                            }
                        }
                    }
                }
            }
            dp = nextDP;
        }
        return dp[row][column];
    }
}