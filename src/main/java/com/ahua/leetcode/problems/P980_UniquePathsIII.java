package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-01 14:48
 */

/**
 * 980. 不同路径 III
 * 在二维网格 grid 上，有 4 种类型的方格：
 * <p>
 * 1 表示起始方格。且只有一个起始方格。
 * 2 表示结束方格，且只有一个结束方格。
 * 0 表示我们可以走过的空方格。
 * -1 表示我们无法跨越的障碍。
 * <p>
 * 返回在四个方向（上、下、左、右）上行走时，从起始方格到结束方格的不同路径的数目。
 * 每一个无障碍方格都要通过一次，但是一条路径中不能重复通过同一个方格。
 * 起始和结束方格可以位于网格中的任意位置。
 * <p>
 * 1 <= grid.length * grid[0].length <= 20
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

// 回溯(最优解法)
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

// 2021.12.21
// DFS + 动态规划(状态压缩 DP)
// 由于此题范围为 1 <= grid.length * grid[0].length <= 20
// 可以使用 DFS 结合 状态压缩 DP 来解答此题
// 将在某点处还要走多少个无防碍方格(步数)改为在某点处的状态(id, state)来记录
// 无论时间还是空间, 效率均远远不及上面的回溯方法
class P980_Solution1 {
    int[][] grid;
    int m;
    int n;
    // 右上左下
    int[] dx = new int[]{0, -1, 0, 1};
    int[] dy = new int[]{1, 0, -1, 0};
    // 记录状态
    // int[][] dp; // 220ms, 这种方式涉及到给 dp 数组全体赋值 -1 效率降低 for (int[] row : dp) { Arrays.fill(row, -1); }
    Integer[][] dp; // 82ms 两者空间消耗差不多, 但是是上一种方法的 6 倍左右

    public int uniquePathsIII(int[][] grid) {
        this.grid = grid;
        this.m = this.grid.length;
        this.n = this.grid[0].length;
        // 开始位置
        int startX = 0, startY = 0;

        // 上个方法中的 stepNum 等于 target 的二进制表示中 1 的个数
        int target = 0;
        // 遍历 grid 得到起始位置 和 总步数
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 要走的格子
                if (grid[i][j] % 2 == 0) {
                    target |= code(i, j);
                } else if (grid[i][j] == 1) {
                    // 起点
                    startX = i;
                    startY = j;
                }
            }
        }
        dp = new Integer[m * n][1 << m * n];
        return dfs(startX, startY, target);
    }

    public Integer dfs(int x, int y, int togo) {
        // 如果走到该空格时的这种状态曾经遍历过, 那么直接返回其值
        // 可以将这个判断改变一下放在 for 中
        int id = x * n + y;
        if (dp[id][togo] != null) {
            return dp[id][togo];
        }

        // 找到结束方格, 但是如果此时(此路径)并没有访问完所有的 0 方格, 则还是认为没有找到满足题意的一条路径
        if (grid[x][y] == 2) {
            return togo == 0 ? 1 : 0;
        }

        // path 表示之前已经访问过若干个 0 方格, 现从此方格开始能访问完所有剩余 0 方格, 并且最终能访问到结束方格的路径数
        // 任何一个从当前方格能够访问到结束方格的路径数等于该方格"上下左右"这四个方格访问到结束方格的路径数的总和
        int path = 0;

        // 按照右上左下的顺序遍历
        for (int k = 0; k < 4; k++) {
            int newX = x + dx[k];
            int newY = y + dy[k];
            // 这两个条件也可以放在 dfs 执行开始处
            // 边界条件: 要走的格子在区域内才能走
            if (newX >= 0 && newX < m && newY >= 0 && newY < n) {
                int possibleTogo = code(newX, newY);
                // togo 是还未走过的(剩下要走的)所有空格的一个二进制集合表示, possibleTogo 是 (newX, newY) 方格的一种二进制表示
                // (newX, newY) 可能走, 可能不走
                // 如果 (newX, newY) 处是起始点或者是障碍物点则不走它, 此时 togo & possibleTogo == 0
                // 如果 (newX, newY) 处是空格, 但是该空格曾经走过了, 也就是点 (newX, newY) 在从起点到点 (x,y) 的路径中了
                // 现在不能再从该点 (x,y) 走到点 (newX, newY) 了, 这样就重复走点 (newX, newY)了
                // 此时 togo & possibleTogo == 0

                // 假设 togo == 1010 1010 表示从(x, y)还要走这四个空格
                // 1.起点情况——起点的二进制表示是 0000 0001, 刚好 (newX, newY) 也是起点, togo & possibleTogo == 0, 起点不能走
                // 2.障碍物情况——如果 (newX, newY) 的二进制表示是 0000 0100, 说明该点是已经走过的空格, togo 中已经不包含这个空格了
                // 此时 togo & possibleTogo 也等于 0
                // 3.空格情况——如果 (newX, newY) 的二进制表示是 0000 1000, togo & possibleTogo == 0000 1000 != 0
                // 表示该空格在 togo 要走的空格集合中, 那么下一次就尝试走这个空格
                // 并将这个空格 (newX, newY) 从 togo 从删除, 表示从空格 (newX, newY) 出发去走新 togo 中的空格集合
                // 新的 togo 为 原togo ^ possibleTogo, 此例中则为 1010 1010 ^ 0000 1000 == 1010 0010, 即将相应位置变为 0

                // 返回能通过 (newX, newY) 走 新todo 走到终点的路径数
                if ((togo & possibleTogo) != 0) {
                    path += dfs(newX, newY, togo ^ possibleTogo);
                }
                // 其实吧, 这里就等于是把曾经的 dfs 中的判断 grid[x][y] 为 -1 3 0 等影响是否继续遍历的情况放在了此处, 用状态来代替了
            }
        }

        // 从该点走到终点的所有情况已经得到了, 记录其值
        dp[id][togo] = path;
        return path;
    }

    public int code(int x, int y) {
        return 1 << (x * n + y);
    }
}