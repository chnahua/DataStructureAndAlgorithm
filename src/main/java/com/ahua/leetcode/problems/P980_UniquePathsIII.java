package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-01 14:48
 */

/**
 * 在二维网格 grid 上，有 4 种类型的方格：
 *
 * 1 表示起始方格。且只有一个起始方格。
 * 2 表示结束方格，且只有一个结束方格。
 * 0 表示我们可以走过的空方格。
 * -1 表示我们无法跨越的障碍。
 *
 * 返回在四个方向（上、下、左、右）上行走时，从起始方格到结束方格的不同路径的数目。
 * 每一个无障碍方格都要通过一次，但是一条路径中不能重复通过同一个方格。
 * 起始和结束方格可以位于网格中的任意位置。
 */

public class P980_UniquePathsIII {
    public static void main(String[] args) {
        int[][] grid = new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 2, -1}};
        int[][] grid1 = new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 2}};
        int[][] grid2 = new int[][]{{0, 1}, {2, 0}};
        P980_Solution solution = new P980_Solution();
        System.out.println(solution.uniquePathsIII(grid)); // 2
        System.out.println(solution.uniquePathsIII(grid1)); // 4
        System.out.println(solution.uniquePathsIII(grid2)); // 0
    }
}

class P980_Solution {
    private int[][] grid;

    public int uniquePathsIII(int[][] grid) {
        this.grid = grid;
        // 开始位置
        int startX = 0, startY = 0;
        // stepNum 表示 grid 中 0 的个数 + 找到 2 时这最后一步, 每经过一个 0 自减, 找到 2 时, stepNum 为 0
        int stepNum = 1;
        // 遍历 grid 得到起始位置 和 总步数
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    stepNum++;
                } else if (grid[i][j] == 1) {
                    startX = i;
                    startY = j;
                }
            }
        }
        return dfs(startX, startY, stepNum);
    }

    public int dfs(int x, int y, int step) {
        // 遍历到边界, 返回 0
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
            return 0;
        }
        // grid[x][y] 是障碍(为 -1 时)或者已经访问过(为 3 时), 返回 0
        if (grid[x][y] == -1 || grid[x][y] == 3) {
            return 0;
        }
        // 找到结束方格, 但是如果此时(此路径)并没有访问完所有的 0 方格, 则还是认为没有找到满足题意的一条路径
        if (grid[x][y] == 2) {
            return step == 0 ? 1 : 0;
        }
        // 能执行到这里表示 grid[x][y] == 0, 将其置为 3, 表示正在访问或者已经访问过该方格, 在回溯时再将其置为 0
        grid[x][y] = 3;
        // path 表示之前已经访问过若干个 0 方格, 现从此方格开始能访问完所有剩余 0 方格, 并且最终能访问到结束方格的路径数
        // 任何一个从当前方格能够访问到结束方格的路径数等于该方格"上下左右"这四个方格访问到结束方格的路径数的总和
        int path = 0;
        // 按照左上右下的顺序遍历
        path += dfs(x, y - 1, step - 1);
        path += dfs(x - 1, y, step - 1);
        path += dfs(x, y + 1, step - 1);
        path += dfs(x + 1, y, step - 1);
        // 回溯时将其置为 0
        grid[x][y] = 0;
        return path;
    }
}