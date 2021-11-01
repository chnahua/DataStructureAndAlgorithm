package com.ahua.leetcode.problems;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author huajun
 * @create 2021-11-01 19:06
 */

/**
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 *
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 *
 * 此外，你可以假设该网格的四条边均被水包围。
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 300
 * grid[i][j] 的值为 '0' 或 '1'
 */

public class P200_NumIslands {
    public static void main(String[] args) {
        char[][] grid = new char[][]{
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };
        char[][] grid1 = new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };
        // 深度优先搜索
        P200_Solution solution = new P200_Solution();
        System.out.println("深度优先搜索 " + solution.numIslands(grid));
        // 广度优先搜索
        P200_Solution1 solution1 = new P200_Solution1();
        System.out.println("广度优先搜索 " + solution1.numIslands(grid1));
    }
}

// 使用 深度优先搜索
class P200_Solution {
    char[][] grid;

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        this.grid = grid;
        // numOfIslands 表示岛屿数量
        int numOfIslands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // 每进行一次深度优先搜索, 表示有一个岛屿
                if (grid[i][j] == '1') {
                    dfs(i, j);
                    numOfIslands++;
                }
            }
        }
        return numOfIslands;
    }

    public void dfs(int i, int j) {
        if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
            if (grid[i][j] == '1') {
                // 每次遇到 '1' ,就将其改为 '2', 表示已经遍历了该陆地 '1'
                grid[i][j] = '2';
                // 按照"左上右下"的顺序遍历该陆地的相邻区域(方格)
                dfs(i, j - 1);
                dfs(i - 1, j);
                dfs(i, j + 1);
                dfs(i + 1, j);
            }
        }
    }
}

// 使用 广度优先搜索
class P200_Solution1 {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        // numOfIslands 表示岛屿数量
        int numOfIslands = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 每进行一次广度优先搜索, 表示有一个岛屿
                if (grid[i][j] == '1') {
                    numOfIslands++;
                    // 每次遇到 '1' ,就将其改为 '2', 表示已经遍历了该陆地 '1'
                    grid[i][j] = '2';
                    // 创建一个队列用以保存该陆地及其相邻的陆地和相连的区域
                    Queue<Integer> neighbors = new LinkedList<>();
                    // 该陆地入队
                    neighbors.add(i * n + j);
                    int id, row, col;
                    // while 中出队一个已访问的陆地, 入队与其相邻的陆地, 直到队列为空
                    while (!neighbors.isEmpty()) {
                        id = neighbors.remove();
                        row = id / n;
                        col = id % n;
                        // 如果该陆地的周围(上下左右)有陆地, 将其添加进队列中, 标记为已访问 '2'
                        // 按照"左上右下"的顺序遍历该陆地的相邻区域(方格)
                        // 左
                        if (col - 1 >= 0 && grid[row][col - 1] == '1') {
                            // neighbors.add(row * n + col - 1);
                            // 与上等价
                            neighbors.add(id - 1);
                            grid[row][col - 1] = '2';
                        }
                        // 上
                        if (row - 1 >= 0 && grid[row - 1][col] == '1') {
                            // neighbors.add((row - 1) * n + col);
                            // 与上等价
                            neighbors.add(id - n);
                            grid[row - 1][col] = '2';
                        }
                        // 右
                        if (col + 1 < n && grid[row][col + 1] == '1') {
                            // neighbors.add(row * n + col + 1);
                            // 与上等价
                            neighbors.add(id + 1);
                            grid[row][col + 1] = '2';
                        }
                        // 下
                        if (row + 1 < m && grid[row + 1][col] == '1') {
                            // neighbors.add((row + 1) * n + col);
                            // 与上等价
                            neighbors.add(id + n);
                            grid[row + 1][col] = '2';
                        }
                    }
                }
            }
        }
        return numOfIslands;
    }
}