package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-05 20:41
 */

/**
 * 1219. 黄金矿工 path-with-maximum-gold
 * 你要开发一座金矿，地质勘测学家已经探明了这座金矿中的资源分布，并用大小为 m * n 的网格 grid 进行了标注。
 * 每个单元格中的整数就表示这一单元格中的黄金数量；如果该单元格是空的，那么就是 0。
 * <p>
 * 为了使收益最大化，矿工需要按以下规则来开采黄金：
 * <p>
 * 每当矿工进入一个单元，就会收集该单元格中的所有黄金。
 * 矿工每次可以从当前位置向上下左右四个方向走。
 * 每个单元格只能被开采（进入）一次。
 * 不得开采（进入）黄金数目为 0 的单元格。
 * 矿工可以从网格中 任意一个 有黄金的单元格出发或者是停止。
 * <p>
 * 1 <= grid.length, grid[i].length <= 15
 * 0 <= grid[i][j] <= 100
 * 最多 25 个单元格中有黄金。
 */
public class P1219_GetMaximumGold {
    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {0, 6, 0},
                {5, 8, 7},
                {0, 9, 0}
        };
        int[][] grid1 = new int[][]{
                {1, 0, 7},
                {2, 0, 6},
                {3, 4, 5},
                {0, 3, 0},
                {9, 0, 20}
        };
        P1219_Solution solution = new P1219_Solution();
        // 一种收集最多黄金的路线是：9 -> 8 -> 7 = 24
        System.out.println(solution.getMaximumGold(grid));
        // 一种收集最多黄金的路线是：1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 = 28
        System.out.println(solution.getMaximumGold(grid1));
    }
}

// 回溯
// 18 ms 49.51%
class P1219_Solution {
    int[][] grid;
    int m;
    int n;
    private final int[] dx = new int[]{-1, 0, 1, 0};
    private final int[] dy = new int[]{0, -1, 0, 1};

    public int getMaximumGold(int[][] grid) {
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        int maximumGold = 0;
        // 以每个可挖矿的格子为出发点开始遍历
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0) {
                    maximumGold = Math.max(maximumGold, dfs(i, j));
                }
            }
        }
        return maximumGold;
    }

    private int dfs(int i, int j) {
        // 保证在边界内
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return 0;
        }
        // 保证可开采或者未开采过(可访问或未访问)
        if (grid[i][j] == 0 || grid[i][j] == -1) {
            return 0;
        }
        // 当前格子处的黄金数量 curGold
        int curGold = grid[i][j];
        // 标记为已访问
        grid[i][j] = -1;
        // 从当前格子开始往其它方向开采的路径中, 能开采到的最多的黄金数量(此处不包括当前格子处的黄金数量 curGold, 最后返回时再加上即可)
        int curMaxPathGold = 0;
        for (int k = 0; k < 4; k++) {
            curMaxPathGold = Math.max(curMaxPathGold, dfs(i + dx[k], j + dy[k]));
        }
        // 回溯还原为未访问(未开采)
        grid[i][j] = curGold;
        return curMaxPathGold + curGold;
    }
}