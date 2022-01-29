package com.ahua.leetcode.problems;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author huajun
 * @create 2022-01-29 16:07
 */

/**
 * 1765. 地图中的最高点 map-of-highest-peak
 * 给你一个大小为 m x n 的整数矩阵 isWater ，它代表了一个由 陆地 和 水域 单元格组成的地图。
 * <p>
 * 如果 isWater[i][j] == 0 ，格子 (i, j) 是一个 陆地 格子。
 * 如果 isWater[i][j] == 1 ，格子 (i, j) 是一个 水域 格子。
 * 你需要按照如下规则给每个单元格安排高度：
 * <p>
 * 每个格子的高度都必须是非负的。
 * 如果一个格子是是 水域 ，那么它的高度必须为 0 。
 * 任意相邻的格子高度差 至多 为 1 。当两个格子在正东、南、西、北方向上相互紧挨着，就称它们为相邻的格子。（也就是说它们有一条公共边）
 * 找到一种安排高度的方案，使得矩阵中的最高高度值 最大 。
 * <p>
 * 请你返回一个大小为 m x n 的整数矩阵 height ，其中 height[i][j] 是格子 (i, j) 的高度。如果有多种解法，请返回 任意一个 。
 * <p>
 * m == isWater.length
 * n == isWater[i].length
 * 1 <= m, n <= 1000
 * isWater[i][j] 要么是 0 ，要么是 1 。
 * 至少有 1 个水域格子。
 */
public class P1765_HighestPeak {
    public static void main(String[] args) {
        int[][] isWater = new int[][]{{0, 1}, {0, 0}};
        int[][] isWater1 = new int[][]{
                {0, 0, 1},
                {1, 0, 0},
                {0, 0, 0}
        };
        P1765_Solution solution = new P1765_Solution();
        myPrint(solution.highestPeak(isWater));
        myPrint(solution.highestPeak(isWater1));
    }

    private static void myPrint(int[][] peak) {
        for (int[] row : peak) {
            System.out.println(Arrays.toString(row));
        }
    }
}

// 广度优先搜索(队列实现) 多源 BFS
// 一次性通过, 都没有测试到更多的测试用例
class P1765_Solution {
    // 上左下右
    final int[] dx = new int[]{-1, 0, 1, 0};
    final int[] dy = new int[]{0, -1, 0, 1};

    // 51 ms
    // 使用 isVisited 记录是否以访问
    public int[][] highestPeak1(int[][] isWater) {
        int m = isWater.length;
        int n = isWater[0].length;
        int[][] gridHeight = new int[m][n];
        boolean[][] isVisited = new boolean[m][n];
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isWater[i][j] == 1) {
                    queue.add(i * n + j);
                    gridHeight[i][j] = 0;
                    isVisited[i][j] = true;
                }
            }
        }
        while (!queue.isEmpty()) {
            int id = queue.remove();
            int x = id / n;
            int y = id % n;
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];
                if (newX >= 0 && newX < m && newY >= 0 && newY < n) {
                    if (isVisited[newX][newY]) {
                        continue;
                    }
                    gridHeight[newX][newY] = gridHeight[x][y] + 1;
                    isVisited[newX][newY] = true;
                    queue.add(newX * n + newY);

                }
            }
        }
        return gridHeight;
    }

    // 46 ms 78.32% 164.2 MB
    // 去掉 isVisited 数组, 改用 gridHeight[i][j] == -1, 表示已访问
    public int[][] highestPeak2(int[][] isWater) {
        int m = isWater.length;
        int n = isWater[0].length;
        int[][] gridHeight = new int[m][n];
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            // 置为 -1, 表示未访问过
            Arrays.fill(gridHeight[i], -1);
            for (int j = 0; j < n; j++) {
                if (isWater[i][j] == 1) {
                    // 置为 0, 表示水域处的高度为 0
                    gridHeight[i][j] = 0;
                    // 先将水域入队(以水域为起点)
                    queue.add(i * n + j);
                }
            }
        }
        while (!queue.isEmpty()) {
            int id = queue.remove();
            int x = id / n;
            int y = id % n;
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];
                // 边界内且没有被访问过, (newX,newY)高度比起(x,y)多 1
                if (newX >= 0 && newX < m && newY >= 0 && newY < n && gridHeight[newX][newY] == -1) {
                    gridHeight[newX][newY] = gridHeight[x][y] + 1;
                    queue.add(newX * n + newY);
                }
            }
        }
        return gridHeight;
    }

    // 43 ms 90.21% 141.1MB 71.33%
    // 队列 queue 中的元素由 i * n + j 改为 new int[]{i, j}
    public int[][] highestPeak(int[][] isWater) {
        int m = isWater.length;
        int n = isWater[0].length;
        int[][] gridHeight = new int[m][n];
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            // 置为 -1, 表示未访问过
            Arrays.fill(gridHeight[i], -1);
            for (int j = 0; j < n; j++) {
                if (isWater[i][j] == 1) {
                    // 置为 0, 表示水域处的高度为 0
                    gridHeight[i][j] = 0;
                    // 先将水域入队(以水域为起点)
                    queue.add(new int[]{i, j});
                }
            }
        }
        while (!queue.isEmpty()) {
            int[] grid = queue.remove();
            for (int i = 0; i < 4; i++) {
                int x = grid[0] + dx[i];
                int y = grid[1] + dy[i];
                // 边界内且没有被访问过, (x,y)高度比起(grid[0],grid[1])多 1
                if (x >= 0 && x < m && y >= 0 && y < n && gridHeight[x][y] == -1) {
                    gridHeight[x][y] = gridHeight[grid[0]][grid[1]] + 1;
                    queue.add(new int[]{x, y});
                }
            }
        }
        return gridHeight;
    }
}