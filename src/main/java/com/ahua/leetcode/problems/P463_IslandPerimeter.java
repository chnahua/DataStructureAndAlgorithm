package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-07 23:06
 */

/**
 * 给定一个 row x col 的二维网格地图 grid ，其中：grid[i][j] = 1 表示陆地， grid[i][j] = 0 表示水域。
 *
 * 网格中的格子 水平和垂直 方向相连（对角线方向不相连）。整个网格被水完全包围，但其中恰好有一个岛屿（或者说，一个或多个表示陆地的格子相连组成的岛屿）。
 *
 * 岛屿中没有“湖”（“湖” 指水域在岛屿内部且不和岛屿周围的水相连）。格子是边长为 1 的正方形。网格为长方形，且宽度和高度均不超过 100 。计算这个岛屿的周长。
 */

// 只有一个岛屿
public class P463_IslandPerimeter {
    public static void main(String[] args) {
//        int[][] grid3 = new int[][]{
//                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
//                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
//                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
//        };
        int[][] grid = new int[][]{
                {0, 1, 0, 0},
                {1, 1, 1, 0},
                {0, 1, 0, 0},
                {1, 1, 0, 0}
        };
        int[][] grid1 = new int[][]{{1}};
        int[][] grid2 = new int[][]{{1, 0}};
        // 深度优先搜索
        P463_Solution solution = new P463_Solution();
        System.out.println(solution.islandPerimeter(grid)); // 16
        System.out.println(solution.islandPerimeter(grid1)); // 4
        System.out.println(solution.islandPerimeter(grid2)); // 4
    }
}

// 迭代(省略)

// 深度优先搜索(递归实现)
class P463_Solution {
    int[][] grid;

    public int islandPerimeter(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        this.grid = grid;
        int Perimeter = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    Perimeter = dfs(i, j);
                    // 本题要求只有一个岛屿
                    break;
                }
            }
            if (Perimeter != 0) {
                // 本题要求只有一个岛屿
                break;
            }
        }
        return Perimeter;
    }

    public int dfs(int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return 1;
        }
        // 如果该格子是水, return 1;
        if (grid[i][j] == 0) {
            return 1;
        } else if (grid[i][j] == 2) {
            // 如果该格子是已经访问过的陆地, return 0;
            return 0;
        }

        // 如果该格子是未访问过的陆地
        // 标记为已访问
        grid[i][j] = 2;
        // 返回该陆地相连的其它陆地的总周长
        return dfs(i - 1, j) + dfs(i + 1, j) + dfs(i, j - 1) + dfs(i, j + 1);
    }
}


