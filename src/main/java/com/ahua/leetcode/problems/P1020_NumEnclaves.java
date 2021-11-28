package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-28 20:23
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * 给出一个二维数组 A，每个单元格为 0（代表海）或 1（代表陆地）。
 * <p>
 * 移动是指在陆地上从一个地方走到另一个地方（朝四个方向之一）或离开网格的边界。
 * <p>
 * 返回网格中无法在任意次数的移动中离开网格边界的陆地单元格的数量。
 * <p>
 * 1 <= A.length <= 500
 * 1 <= A[i].length <= 500
 * 0 <= A[i][j] <= 1
 * 所有行的大小都相同
 */
public class P1020_NumEnclaves {
    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {0, 0, 0, 0},
                {1, 0, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };
        int[][] grid1 = new int[][]{
                {0, 1, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 0}
        };
        P1020_Solution solution = new P1020_Solution();
        System.out.println(solution.numEnclaves(grid)); // 3
        System.out.println(solution.numEnclaves(grid1)); // 0
    }
}

// 深度优先搜索（递归实现）
class P1020_Solution0 {
    int[][] grid;
    int m;
    int n;
    int numOfEnclaves;
    boolean flag;

    public int numEnclaves(int[][] grid) {
        this.grid = grid;
        this.m = grid.length;
        if (m == 0) {
            return 0;
        }
        this.n = grid[0].length;
        this.numOfEnclaves = 0;

        int area;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 默认是一个封闭岛屿(无法走出)
                    flag = true;
                    // 该岛屿的面积
                    area = dfs(i, j);
                    if (flag) {
                        // 如果是, 加上这些格子的数量(岛屿面积)
                        numOfEnclaves += area;
                    }
                }
            }
        }

        return numOfEnclaves;
    }

    public int dfs(int i, int j) {
        // 边界条件
        if (i < 0 || i >= m || j < 0 || j >= n) {
            flag = false;
            return 0;
        }

        // 是水域或者曾经遍历过, 跳过遍历
        if (grid[i][j] == 0 || grid[i][j] == 2) {
            return 0;
        }

        grid[i][j] = 2;

        // 上左下右
        return 1 + dfs(i - 1, j)
                + dfs(i, j - 1)
                + dfs(i + 1, j)
                + dfs(i, j + 1);
    }
}

// 深度优先搜索（递归实现）
// 换一种思考方式的解题思路
// 三种实现中效率最高
class P1020_Solution {
    int[][] grid;
    int m;
    int n;

    public int numEnclaves(int[][] grid) {
        this.grid = grid;
        this.m = grid.length;
        if (m == 0) {
            return 0;
        }
        this.n = grid[0].length;
        int numOfEnclaves = 0;

        // 1.将与左右两边界相连的陆地去掉
        for (int i = 0; i < m; i++) {
            if (grid[i][0] == 1) {
                dfs(i, 0);
            }
            if (grid[i][n - 1] == 1) {
                dfs(i, n - 1);
            }
        }

        // 2.将与上下两边界相连的陆地去掉
        for (int j = 0; j < n; j++) {
            if (grid[0][j] == 1) {
                dfs(0, j);
            }
            if (grid[m - 1][j] == 1) {
                dfs(m - 1, j);
            }
        }

        // 3.最后遍历 grid 中为 1 的格子的数量, 即为答案
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (grid[i][j] == 1) {
                    numOfEnclaves++;
                }
            }
        }

        return numOfEnclaves;
    }

    public void dfs(int i, int j) {
        // 边界条件
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return;
        }

        // 是水域或者曾经遍历过, 跳过遍历
        if (grid[i][j] == 0 || grid[i][j] == 2) {
            return;
        }

        grid[i][j] = 2;

        // 上左下右
        dfs(i - 1, j);
        dfs(i, j - 1);
        dfs(i + 1, j);
        dfs(i, j + 1);
    }
}


// 广度优先搜索（队列实现）
// 效率不及 DFS
class P1020_Solution1 {
    int[] dx = new int[]{-1, 0, 1, 0};
    int[] dy = new int[]{0, -1, 0, 1};

    public int numEnclaves(int[][] grid) {
        int m = grid.length;
        if (m == 0) {
            return 0;
        }
        int n = grid[0].length;
        int numOfEnclaves = 0;

        boolean flag;
        // 每块岛屿的面积
        int area;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 默认是一个封闭岛屿(无法走出)
                    flag = true;
                    Queue<Integer> queue = new LinkedList<>();
                    // 该个岛屿第一块陆地入队
                    queue.add(i * n + j);
                    // 标记为已访问
                    grid[i][j] = 2;
                    // 初始为 1
                    area = 1;
                    int id, x, y;
                    while (!queue.isEmpty()) {
                        id = queue.remove();
                        x = id / n;
                        y = id % n;
                        int newX, newY;
                        // 上左下右四块陆地入队
                        for (int k = 0; k < 4; k++) {
                            newX = x + dx[k];
                            newY = y + dy[k];
                            // 如果该块陆地与边界相连, 则 flag 置为 false, 表示该岛屿与边界是相连的
                            // 遍历完后, 该岛屿的面积 area 不加入 numOfEnclaves 中
                            if (newX < 0 || newX >= m || newY < 0 || newY >= n) {
                                flag = false;
                                continue;
                            }
                            // 跳过周围的四块陆地中 是水域或者已经遍历过的陆地
                            if (grid[newX][newY] == 0 || grid[newX][newY] == 2) {
                                continue;
                            }
                            // 此处 grid[newX][newY] 肯定为 1, 表示从未遍历过的陆地
                            // 将其入队, 并置为已访问 2, 面积 area 加 1
                            queue.add(newX * n + newY);
                            grid[newX][newY] = 2;
                            area++;
                        }
                    }
                    // flag 为 false 时, 表示该岛屿与边界相连, 不加上该块岛屿面积
                    if (flag) {
                        numOfEnclaves += area;
                    }
                }
            }
        }
        return numOfEnclaves;
    }
}