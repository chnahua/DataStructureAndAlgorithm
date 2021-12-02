package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-02 19:59
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * 1905. 统计子岛屿
 * <p>
 * 给你两个 m x n 的二进制矩阵 grid1 和 grid2 ，它们只包含 0 （表示水域）和 1 （表示陆地）。
 * 一个 岛屿 是由 四个方向 （水平或者竖直）上相邻的 1 组成的区域。任何矩阵以外的区域都视为水域。
 * <p>
 * 如果 grid2 的一个岛屿，被 grid1 的一个岛屿 完全 包含，也就是说 grid2 中该岛屿的每一个格子
 * 都被 grid1 中同一个岛屿完全包含，那么我们称 grid2 中的这个岛屿为 子岛屿 。
 * <p>
 * 请你返回 grid2 中 子岛屿 的 数目 。
 * <p>
 * m == grid1.length == grid2.length
 * n == grid1[i].length == grid2[i].length
 * 1 <= m, n <= 500
 * grid1[i][j] 和 grid2[i][j] 都要么是 0 要么是 1 。
 */
public class P1905_CountSubIslands {
    public static void main(String[] args) {
        int[][] grid1 = new int[][]{
                {1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1},
                {0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 1, 0, 1, 1}
        };
        int[][] grid2 = new int[][]{
                {1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1},
                {0, 1, 0, 0, 0},
                {1, 0, 1, 1, 0},
                {0, 1, 0, 1, 0}
        };
        int[][] grid3 = new int[][]{
                {1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1}
        };
        int[][] grid4 = new int[][]{
                {0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1},
                {0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0},
                {1, 0, 0, 0, 1}
        };

        P1905_Solution solution = new P1905_Solution();
        System.out.println(solution.countSubIslands(grid1, grid2)); // 3
        System.out.println(solution.countSubIslands(grid3, grid4)); // 2
    }
}

// 深度优先遍历(递归实现)
// 此题 DFS 效率比 BFS 高
class P1905_Solution {
    int[][] grid1;
    int[][] grid2;
    int m;
    int n;

    public int countSubIslands(int[][] grid1, int[][] grid2) {
        this.grid1 = grid1;
        this.grid2 = grid2;
        this.m = grid1.length;
        this.n = grid1[0].length;

        // grid2 中 子岛屿 的 数目
        int numSubIslands = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1) {
                    if (dfs(i, j)) {
                        numSubIslands++;
                    }
                }
            }
        }

        return numSubIslands;
    }

    private boolean dfs(int i, int j) {
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return true;
        }

        if (grid2[i][j] == 0 || grid2[i][j] == 2) {
            return true;
        }
//        // 可以不创建 bl 变量, 使用 & 符号
//        // 1
//        boolean bl = true;
//
//        // 如果(i,j)处, grid1[i][j]为 0, grid2[i][j]为 1, 说明这个岛屿不是子岛屿
//        if (grid1[i][j] == 0) {
//            bl = false;
//        }
//
//        // 陆地标记已访问
//        grid2[i][j] = 2;
//
//        // 左上下右
//        bl = dfs(i, j - 1) && bl;
//        bl = dfs(i - 1, j) && bl;
//        bl = dfs(i + 1, j) && bl;
//        bl = dfs(i, j + 1) && bl;
//        return bl;


//        // 2
        // && 和 & 都是表示与，区别是 && 只要第一个条件不满足，后面条件就不再判断。
        // 而 & 要对所有的条件都进行判断。

        // 陆地标记已访问
        grid2[i][j] = 2;
        // 左上下右
        return dfs(i, j - 1)
                & dfs(i - 1, j)
                & dfs(i + 1, j)
                & dfs(i, j + 1)
                & grid1[i][j] == 1;
    }
}

// 广度优先遍历(队列实现)
class P1905_Solution1 {
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid1.length;
        int n = grid1[0].length;
        // 左上下右
        int[] dx = new int[]{0, -1, 1, 0};
        int[] dy = new int[]{-1, 0, 0, 1};
        // grid2 中 子岛屿 的 数目
        int numSubIslands = 0;
        boolean bl;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1) {
                    Queue<Integer> queue = new LinkedList<>();
                    queue.add(i * n + j);
                    // 每次重新对 bl 赋初值
                    bl = grid1[i][j] == 1;
                    grid2[i][j] = 2;
                    int id, curX, curY;
                    while (!queue.isEmpty()) {
                        id = queue.poll();
                        curX = id / n;
                        curY = id % n;
                        int x, y;
                        // 左上下右
                        for (int k = 0; k < 4; k++) {
                            x = curX + dx[k];
                            y = curY + dy[k];
                            if (x < 0 || x >= m || y < 0 || y >= n) {
                                continue;
                            }
                            if (grid2[x][y] == 1) {
                                queue.add(x * n + y);
                                // 如果(i,j)处, grid1[i][j]为 0, grid2[i][j]为 1, 说明这个岛屿不是子岛屿
                                if (grid1[x][y] == 0) {
                                    bl = false;
                                }
                                // 陆地标记已访问
                                grid2[x][y] = 2;
                            }
                        }
                    }
                    // bl 为 false 时, 表示该岛屿不是子岛屿
                    if (bl) {
                        numSubIslands++;
                    }
                }
            }
        }
        return numSubIslands;
    }
}