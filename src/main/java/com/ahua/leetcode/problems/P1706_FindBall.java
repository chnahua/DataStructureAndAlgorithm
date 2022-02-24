package com.ahua.leetcode.problems;

import java.util.Arrays;

/**
 * @author huajun
 * @create 2022-02-24 20:43
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