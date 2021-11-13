package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-13 15:27
 */

/**
 * 有一个二维矩阵 grid ，每个位置要么是陆地（记号为 0 ）要么是水域（记号为 1 ）。
 * <p>
 * 我们从一块陆地出发，每次可以往上下左右 4 个方向相邻区域走，能走到的所有陆地区域，我们将其称为一座「岛屿」。
 * <p>
 * 如果一座岛屿 完全 由水域包围，即陆地边缘上下左右所有相邻区域都是水域，那么我们将其称为 「封闭岛屿」。
 * <p>
 * 请返回封闭岛屿的数目。
 * <p>
 * 1 <= grid.length, grid[0].length <= 100
 * 0 <= grid[i][j] <=1
 */
public class P1254_ClosedIsland {
    public static void main(String[] args) {
        int[][] gird = new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 1, 1, 0},
                {1, 0, 1, 0, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 0}
        };
        int[][] gird1 = new int[][]{
                {0, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 1, 1, 1, 0}
        };
        int[][] gird2 = new int[][]{
                {1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1}
        };
        int[][] grid3 = new int[][]{
                {0, 0, 1, 1, 0, 1, 0, 0, 1, 0},
                {1, 1, 0, 1, 1, 0, 1, 1, 1, 0},
                {1, 0, 1, 1, 1, 0, 0, 1, 1, 0},
                {0, 1, 1, 0, 0, 0, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 1, 1, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1, 1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 0, 1, 1, 0, 1, 1, 0}
        };

        P1254_Solution solution = new P1254_Solution();
        System.out.println(solution.closedIsland(gird)); // 2
        System.out.println(solution.closedIsland(gird1)); // 1
        System.out.println(solution.closedIsland(gird2)); // 2
        System.out.println(solution.closedIsland(grid3)); // 5
//        for (int i = 0; i < grid3.length; i++) {
//            for (int j = 0; j < grid3[0].length; j++) {
//                System.out.print(grid3[i][j] + " ");
//            }
//            System.out.println();
//        }
    }
}

// 深度优先遍历(递归实现)
class P1254_Solution {
    int m, n;

    public int closedIsland(int[][] grid) {
        if (grid == null) {
            return 0;
        }
        this.m = grid.length;
        this.n = grid[0].length;
        if (m == 2 || n == 2) {
            return 0;
        }
        int numOfClosedIsland = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    if (dfs(grid, i, j)) {
                        numOfClosedIsland++;
                    }
                }
            }
        }
        return numOfClosedIsland;
    }

    public boolean dfs(int[][] grid, int i, int j) {
        // 超出边界, 说明该岛屿不是封闭的, 返回 false
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return false;
        }
        // 该方格可能是水域(为 1 的情况), 或者是曾经访问过的陆地(为 2 的情况), 返回 true
        if (grid[i][j] != 0) {
            return true;
        }
        grid[i][j] = 2;
        // 上左下右遍历
        // 注意 bl 放在 && 后, 即要先 dfs 得到结果后再与 bl 进行 ‘与’ 操作
        // 当然更不能直接将这四个 dfs && 连接起来
        boolean bl = dfs(grid, i - 1, j);
        bl = dfs(grid, i, j - 1) && bl;
        bl = dfs(grid, i + 1, j) && bl;
        bl = dfs(grid, i, j + 1) && bl;
        return bl;
    }
}