package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-07 20:04
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * 给你一个大小为 m x n 的二进制矩阵 grid 。
 *
 * 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。
 * 你可以假设 grid 的四个边缘都被 0（代表水）包围着。
 *
 * 岛屿的面积是岛上值为 1 的单元格的数目。
 *
 * 计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 50
 * grid[i][j] 为 0 或 1
 */

public class P695_MaxAreaOfIsland {
    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
        };
        int[][] grid1 = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0}};
        int[][] grid2 = new int[][]{
                {1, 1, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 0, 1, 1},
                {0, 0, 0, 1, 1}
        };
        // 使用 深度优先搜索
        P695_Solution solution = new P695_Solution();
        System.out.println(solution.maxAreaOfIsland(grid)); // 6
        // 使用 广度优先搜索
        P695_Solution1 solution1 = new P695_Solution1();
        System.out.println(solution1.maxAreaOfIsland(grid1)); // 0
        System.out.println(solution1.maxAreaOfIsland(grid2)); // 4
    }
}

// 深度优先搜索(递归实现)
class P695_Solution {
    int[][] grid;

    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        this.grid = grid;
        int maxArea = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    maxArea = Math.max(maxArea, dfs(i, j));
                }
            }
        }
        return maxArea;
    }

    public int dfs(int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return 0;
        }
        // 如果该格子不是土地或者已经访问过, return 0;
        if (grid[i][j] != 1) {
            return 0;
        }
        // 标记为已访问
        grid[i][j] = 2;
        // 当前土地的面积为 1
        int area = 1;
        // 返回上下左右相邻的土地的面积
        area += dfs(i - 1, j);
        area += dfs(i + 1, j);
        area += dfs(i, j - 1);
        area += dfs(i, j + 1);
        return area;
    }
}

// 使用 深度优先搜索 + 栈 (参考官方)

// 使用 广度优先搜索
class P695_Solution1 {
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        int maxArea = 0;
        int area;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    Queue<Integer> queue = new LinkedList<>();
                    queue.add(i * n + j);
                    // 标记为已访问
                    grid[i][j] = 2;
                    // 当前面积为 1
                    area = 1;
                    int id, row, col;
                    while (!queue.isEmpty()) {
                        id = queue.poll();
                        row = id / n;
                        col = id % n;
                        // 上
                        if (row - 1 >= 0 && grid[row - 1][col] == 1) {
                            queue.add(id - n);
                            grid[row - 1][col] = 2;
                            area++;
                        }
                        // 下
                        if (row + 1 < m && grid[row + 1][col] == 1) {
                            queue.add(id + n);
                            grid[row + 1][col] = 2;
                            area++;
                        }
                        // 左
                        if (col - 1 >= 0 && grid[row][col - 1] == 1) {
                            queue.add(id - 1);
                            grid[row][col - 1] = 2;
                            area++;
                        }
                        // 右
                        if (col + 1 < n && grid[row][col + 1] == 1) {
                            queue.add(id + 1);
                            grid[row][col + 1] = 2;
                            area++;
                        }
                    }
                    // 当前遍历到的岛屿的最大面积
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        return maxArea;
    }
}