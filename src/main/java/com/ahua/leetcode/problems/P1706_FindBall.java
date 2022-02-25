package com.ahua.leetcode.problems;

import java.util.Arrays;

/**
 * @author huajun
 * @create 2022-02-24 20:43
 */

/**
 * 1706. 球会落何处 where-will-the-ball-fall
 * 用一个大小为 m x n 的二维网格 grid 表示一个箱子。你有 n 颗球。箱子的顶部和底部都是开着的。
 *
 * 箱子中的每个单元格都有一个对角线挡板，跨过单元格的两个角，可以将球导向左侧或者右侧。
 *
 * 将球导向右侧的挡板跨过左上角和右下角，在网格中用 1 表示。
 * 将球导向左侧的挡板跨过右上角和左下角，在网格中用 -1 表示。
 * 在箱子每一列的顶端各放一颗球。每颗球都可能卡在箱子里或从底部掉出来。
 * 如果球恰好卡在两块挡板之间的 "V" 形图案，或者被一块挡导向到箱子的任意一侧边上，就会卡住。
 *
 * 返回一个大小为 n 的数组 answer ，
 * 其中 answer[i] 是球放在顶部的第 i 列后从底部掉出来的那一列对应的下标，如果球卡在盒子里，则返回 -1 。
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 100
 * grid[i][j] 为 1 或 -1
 */
public class P1706_FindBall {
    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {1, 1, 1, -1, -1},
                {1, 1, 1, -1, -1},
                {-1, -1, -1, 1, 1},
                {1, 1, 1, 1, -1},
                {-1, -1, -1, -1, -1}
        };
        int[][] grid1 = new int[][]{{-1}};
        int[][] grid2 = new int[][]{
                {1, 1, 1, 1, 1, 1},
                {-1, -1, -1, -1, -1, -1},
                {1, 1, 1, 1, 1, 1},
                {-1, -1, -1, -1, -1, -1}
        };
        P1706_Solution solution = new P1706_Solution();
        myPrint(grid);
        System.out.println(Arrays.toString(solution.findBall(grid))); // [1,-1,-1,-1,-1]
        myPrint(grid1);
        System.out.println(Arrays.toString(solution.findBall(grid1))); // [-1]
        myPrint(grid2);
        System.out.println(Arrays.toString(solution.findBall(grid2))); // [0,1,2,3,4,-1]
    }

    private static void myPrint(int[][] grid) {
        for (int[] row : grid) {
            System.out.print('|');
            for (int j = 0; j < grid[0].length; j++) {
                if (row[j] == 1) {
                    // 往右
                    System.out.print("\\");
                } else {
                    // 往左
                    System.out.print("/");
                }
            }
            System.out.println('|');
        }
    }
}

// 我太帅啦, 哈哈, 一次通过
// 深度优先搜索(递归实现)
// O(M*N) O(M)
class P1706_Solution {
    int[][] grid;
    int m;
    int n;

    public int[] findBall(int[][] grid) {
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        int[] ans = new int[n];
        // 依次得到每个球的最终位置
        for (int i = 0; i < n; i++) {
            ans[i] = dfs(0, i);
        }
        return ans;
    }

    // 0 ms 100%
    // 42.7 MB 13.86%
    private int dfs1(int i, int j) {
        // 往右
        if (grid[i][j] == 1) {
            // 最后一列, 右侧墙
            if (j == n - 1) {
                return -1;
            }
            // V 字型
            if (grid[i][j + 1] == -1) {
                return -1;
            }
            // 当前在最后一行, 从 j+1 列出去
            if (i == m - 1) {
                return j + 1;
            }
            return dfs1(i + 1, j + 1);
        } else {
            // 往左
            // 第一列, 左侧墙
            if (j == 0) {
                return -1;
            }
            // V 字型
            if (grid[i][j - 1] == 1) {
                return -1;
            }
            // 当前在最后一行, 从 j-1 列出去
            if (i == m - 1) {
                return j - 1;
            }
            return dfs1(i + 1, j - 1);
        }
    }

    // 上一个 dfs 的代码归纳与简化
    // 0 ms 100%
    // 42.4 MB 31.33%
    private int dfs(int i, int j) {
        // 往右, 在最后一列, 右侧墙
        if (grid[i][j] == 1 && j == n - 1) {
            return -1;
        }
        // 往左, 在第一列, 左侧墙
        if (grid[i][j] == -1 && j == 0) {
            return -1;
        }
        // V 字型
        if (grid[i][j] + grid[i][j + grid[i][j]] == 0) {
            return -1;
        }
        // 当前在最后一行, 从 j+grid[i][j] 列出去
        if (i == m - 1) {
            return j + grid[i][j];
        }
        return dfs(i + 1, j + grid[i][j]);
    }
}