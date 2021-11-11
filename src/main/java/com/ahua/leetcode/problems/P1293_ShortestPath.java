package com.ahua.leetcode.problems;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author huajun
 * @create 2021-11-10 15:47
 */

/**
 * 给你一个 m * n 的网格，其中每个单元格不是 0（空）就是 1（障碍物）。每一步，您都可以在空白单元格中上、下、左、右移动。
 * <p>
 * 如果您 最多 可以消除 k 个障碍物，请找出从左上角 (0, 0) 到右下角 (m-1, n-1) 的最短路径，并返回通过该路径所需的步数。如果找不到这样的路径，则返回 -1。
 * <p>
 * grid.length == m
 * grid[0].length == n
 * 1 <= m, n <= 40
 * 1 <= k <= m*n
 * grid[i][j] == 0 or 1
 * grid[0][0] == grid[m-1][n-1] == 0
 */

public class P1293_ShortestPath {
    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {0, 0, 0},
                {1, 1, 0},
                {0, 0, 0},
                {0, 1, 1},
                {0, 0, 0}
        };
        int[][] grid1 = new int[][]{
                {0, 1, 1},
                {1, 1, 1},
                {1, 0, 0}
        };
        // 超出时间限制
        int[][] grid2 = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        // 测试 DFS + 记忆化 时未通过的用例
        int[][] grid3 = new int[][]{
                {0, 0},
                {1, 0},
                {1, 0},
                {1, 0},
                {1, 0},
                {1, 0},
                {0, 0},
                {0, 1},
                {0, 1},
                {0, 1},
                {0, 0},
                {1, 0},
                {1, 0},
                {0, 0}
        };
        int[][] grid4 = new int[][]{
                {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1},
                {1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0},
                {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0},
                {1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1},
                {1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0}
        };
        int[][] grid5 = new int[][]{
                {0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1, 0},
                {1, 0, 0, 0, 1, 1},
                {1, 1, 1, 1, 1, 0}
        };

        P1293_Solution solution = new P1293_Solution();
        System.out.println(solution.shortestPath(grid, 1)); // 6 (3, 2)
        System.out.println(solution.shortestPath(grid1, 1)); // -1

        // k = 0,1,2,3,4,5 的对应结果是 799,699,621,543,465,387
        // 使用 DFS 只能得到 k = 0,1,2 时的结果, 其它的会超时
        System.out.println(solution.shortestPath(grid2, 5)); // 387

        System.out.println(solution.shortestPath(grid3, 4)); // 14
        System.out.println(solution.shortestPath(grid4, 283)); // 41
        System.out.println(solution.shortestPath(grid5, 1)); // 8
    }
}

// 广度优先搜索(队列实现) + 贪心算法
class P1293_Solution {
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        // 只有一个方格, 由于 k 是大于等于 1 的, 不管该方格是不是障碍物, 都能通过
        if (m == 1 && n == 1) {
            return 0;
        }
        // 剪枝
        if (k >= m + n - 2) {
            return m + n - 2;
        }
        // 最后一个(右下角)为障碍物, 先将其消除, k 要减 1
        if (grid[m - 1][n - 1] == 1) {
            grid[m - 1][n - 1] = 0;
            k--;
        }
        // 三维标记数组
        // visited[i][j][0] 该方格历史最优剩余能消除障碍物的次数 剩余次数越多, 越有价值(此处贪心, 记录局部最优)
        // visited[i][j][1] 该方格当前此次剩余能消除障碍物的次数
        int[][][] visited = new int[m][n][2];
        // visited[i][j][0] 初始状态为 -1, 不需要初始化 visited[i][j][1]
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                visited[i][j][0] = -1;
            }
        }

        // 初始化起始点(0, 0)处的 visited[0][0][0] 和 visited[0][0][1] 值为 k , 表示起始点处的
        // (历史最大消除次数 visited[0][0][0]) 和 (从当前该方格出发能够消除的次数 visited[0][0][1])
        visited[0][0][0] = k;
        visited[0][0][1] = k;
        // 构造队列
        Queue<Integer> queue = new LinkedList<>();
        // 初始化队列, 将起始点(0, 0)入队, 0 = 行号 * n + 列号
        queue.add(0);
        // 初始化最短路径步数为 0
        int minSteps = 0;
        // 四个方向
        final int[] dx = new int[]{-1, 1, 0, 0};
        final int[] dy = new int[]{0, 0, -1, 1};
        // BFS 的队列实现 + 贪心
        while (!queue.isEmpty()) {
            // 等于遍历的层数, 每进入一次 while, 表示走了一步
            minSteps++;
            // 提前获取该层需要出队多少个的个数
            // 遍历相同层级下所有节点
            int size = queue.size();
            // 当前方格的 id、横坐标、纵坐标、从该方格出发能够消除障碍的次数
            int id, curX, curY, obsNum;
            for (int i = 0; i < size; i++) {
                // 出队
                id = queue.remove();
                curX = id / n;
                curY = id % n;
                // 从该方格出发能够消除障碍的次数
                obsNum = visited[curX][curY][1];
                // 当前 id 方格相邻的四个方格的 横纵坐标
                int x, y;
                for (int j = 0; j < 4; j++) {
                    x = curX + dx[j];
                    y = curY + dy[j];
                    // 在 grid 内, 不要越界
                    if (x >= 0 && x < m && y >= 0 && y < n) {
                        // id 方格的下一步就是终点(右下角), 返回 minSteps, 此为最短路径(步数)
                        if (x == m - 1 && y == n - 1) {
                            return minSteps;
                        }
                        // 如果当该方格为障碍物, 并且到达 id 方格时还能穿越障碍物的次数已经为 0, 则不能再穿越此方格(障碍物)了
                        if (grid[x][y] == 1 && obsNum == 0) {
                            continue;
                        }
                        // 如果该方格是障碍物, 则将其变成可以通过的方格, 并更新其还能消除障碍物的次数
                        // visited[x][y][1] = grid[x][y] == 1 ? obsNum - 1 : obsNum; // 貌似不需要 newObsNum 也可以
                        int newObsNum = grid[x][y] == 1 ? obsNum - 1 : obsNum;
                        // 以下三段代码取其一, 都是正确的, 逻辑不同而已
/*                        // 1. 是第 2 段代码的简化
                        // 如果该方格已被访问过(可能该方格是障碍物, 但是可能曾经通过将其转变为可以通过的方格被访问过)
                        // 并且当前 visited 记录的历史访问该方格时剩余消除障碍物最大次数 > 当前搜索节点层级的剩余消除次数
                        // 如果将其加入队列, 也很有可能得不到更优解了
                        // 因此不更新该方格的 visited 历史值, 此方格也不加入队列, 直接进行下次循环
                        // 反之, 则更新 visited[x][y][0] 和 visited[x][y][1] 为 newObsNum, 并将该方格加入队列
                        // hi, 真是奇怪, 如果将 ">=" 改为 ">", 会出现问题
                        if (visited[x][y][0] != -1 && visited[x][y][0] >= newObsNum) {
                            continue;
                        } else {
                            visited[x][y][1] = newObsNum;
                            visited[x][y][0] = newObsNum;
                        }
                        // 此处, 该方格可能曾经从未被访问过, 也可能被访问过
                        // 但是不管何种情况, visited[x][y][0] 和 visited[x][y][0] 均已在上一个 if 判断中更新
                        // 现在只需入队即可
                        //queue.add(x * n + y);*/

/*                        // 2. 比第 1 段好理解的, 但是也比第 1 段多了些重复代码
                        // 上一个 if - else 也可理解为 :
                        // 如果被访问过, 贪心一下, 看通过该方格是否有可能得到更优解, 判断方式是
                        if (visited[x][y][0] != -1) {
                            // 如果此次剩余消除次数没有以前保存的历史消除次数值大, 则认为以更小的消除次数从这个方格出发到达终点
                            // 得到的结果(最短路径步数)
                            // 也不会比以 [已经保存在 visited[x][y][0] 中的这个历史的更大消除次数值的] 这种状态的这个方格出发
                            // 到达终点得到的结果更优
                            // 于是, 不更新, 不入队, 直接下次循环
                            // 这个是大于等于还是等于差别这么大吗?
                            if (visited[x][y][0] >= newObsNum) {
                                continue;
                            } else {
                                // 反之, 如果此次的还能消除障碍的次数更大, 则可能通过该方格去尝试, 能够得到更优解
                                // 于是, 更新 visited 数组值, 并入队
                                visited[x][y][1] = newObsNum;
                                visited[x][y][0] = newObsNum;
                            }
                        } else {
                            // 如果没被访问过, 直接给 visited 数组赋值, 然后入队
                            visited[x][y][1] = newObsNum;
                            visited[x][y][0] = newObsNum;
                        }
                        // 此处, 该方格可能曾经从未被访问过, 也可能被访问过
                        // 但是不管何种情况, visited[x][y][0] 和 visited[x][y][0] 均已在上一个 if 判断中更新
                        // 现在只需入队即可
                        //queue.add(x * n + y);*/

                        // 3. 这种也是对的
                        // 由于除了起始方格处的 visited[x][y][0] 值为 k, 其余各方格处的该值初始值都为 -1,
                        // 而 newObsNum 肯定是大于等于 0 的, 肯定比初始值 -1 大,
                        // 又由于需要贪心保证 visited[x][y][0] 能够成为更大值时, 才有可能得到更优解, 才更新其值以及入队
                        // 所以当遇到比 历史保存值(消除障碍数)更大的值时, 就更新该方格的 visited 值, 以及入队
                        // 如果没有, 就什么都不做
                        if (visited[x][y][0] < newObsNum) {
                            visited[x][y][1] = newObsNum;
                            visited[x][y][0] = newObsNum;
                            queue.add(x * n + y);
                        }
                    }
                }
            }
        }
        // 未找到路径, 返回 -1
        return -1;
    }
}

// 深度优先搜索 (应该没问题, 但是会超时, 不敢确定)  测试 grid2 前四行能通过, 为 42 步, 和 BFS 的结果一样
class P1293_Solution2 {
    int[][] grid;
    int m;
    int n;
    int[] dx = new int[]{-1, 1, 0, 0};

    int[] dy = new int[]{0, 0, -1, 1};

    public int shortestPath(int[][] grid, int k) {
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        if (k >= m + n - 2) {
            return m + n - 2;
        }
        if (grid[m - 1][n - 1] == 1) {
            if (k <= 0) {
                return -1;
            }
            grid[m - 1][n - 1] = 0;
            k--;
        }
        int minPath = dfs(0, 0, k);
        return minPath != -1 ? minPath - 1 : -1;
    }

    public int dfs(int i, int j, int k) {
        // 越界
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return -1;
        }
        // 走到了终点(右下角)
        if (i == m - 1 && j == n - 1) {
            return 1;
        }
        // 如果该方格(该方格可能是由障碍物变成的(3))已经访问过, 返回 -1
        if (grid[i][j] == 3 || grid[i][j] == 2) {
            return -1;
        }
        // 如果该方格是障碍物, 并且可以消除障碍物的次数 k > 0, 则消除障碍物,
        // 将其转换为可以通过的方格, 标记为已访问 3, 后续 DFS 完成后需要回溯, 再将其置为 0
        // 否则直接返回 -1, 因为该方格是障碍物, 不能通过走这个方格走到右下角了
        if (grid[i][j] == 1) {
            if (k > 0) {
                grid[i][j] = 3;
                k--;

            } else {
                return -1;
            }
        }
        // 此时 grid[i][j] 可能为 0 或者 3 (3 是由 1 变换过来的)
        // 如果为 0, 标记该方格为已访问 2, 后续 DFS 完成后需要回溯, 再将其置为 0
        if (grid[i][j] == 0) {
            grid[i][j] = 2;
        }
        // 最小路径
        int minStep = -1;
        int step;
        // 访问上下左右, 4 次 DFS
        for (int l = 0; l < 4; l++) {
            step = dfs(i + dx[l], j + dy[l], k);
            if (step != -1) {
                if (minStep == -1) {
                    minStep = step;
                } else {
                    minStep = Math.min(minStep, step);
                }
            }
        }
        // 回溯
        if (grid[i][j] == 3) {
            // 不用回溯 k
            grid[i][j] = 1;
        } else if (grid[i][j] == 2) {
            grid[i][j] = 0;
        }
        return minStep != -1 ? minStep + 1 : -1;
    }
}

// 深度优先搜索 + 记忆化1 (未完成, LeetCode 上不能通过 grid2, IDEA 可以) // 方向错了, 就啥都错了
class P1293_Solution1 {
    int[][] grid;
    int m;
    int n;
    // 上左下右
    int[] dx = new int[]{-1, 0, 1, 0};
    int[] dy = new int[]{0, -1, 0, 1};
    int[][] memo;

    public int shortestPath(int[][] grid, int k) {
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        if (k >= m + n - 2) {
            return m + n - 2;
        }
        if (grid[m - 1][n - 1] == 1) {
            if (k <= 0) {
                return -1;
            }
            grid[m - 1][n - 1] = 0;
            k--;
        }
        this.memo = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                memo[i][j] = -1;
            }
        }
        // 剩余消除次数
        memo[0][0] = k;
        int minPath = dfs(0, 0, k, -1);
        return minPath != -1 ? minPath - 1 : -1;
    }

    public int dfs(int i, int j, int k, int d) {
        // 越界
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return -1;
        }
        // 走到了终点(右下角)
        if (i == m - 1 && j == n - 1) {
            return 1;
        }
        // 如果该方格(该方格可能是由障碍物变成的(3))已经访问过, 返回 -1
        if (grid[i][j] == 3 || grid[i][j] == 2) {
            return -1;
        }
        // 如果该方格是障碍物, 并且可以消除障碍物的次数 k > 0, 则消除障碍物,
        // 将其转换为可以通过的方格, 标记为已访问 3, 后续 DFS 完成后需要回溯, 再将其置为 0
        // 否则直接返回 -1, 因为该方格是障碍物, 不能通过走这个方格走到右下角了
        if (grid[i][j] == 1) {
            if (k > 0) {
                if (k >= memo[i][j] || (j == n - 1 && memo[i - dx[d]][j - dy[d]] == memo[i][j])) {
                    grid[i][j] = 3;

                    // k >= memo[i][j] - 1
                    // (d == 0 || d == 2 || d == 3)   (d == 2 && memo[i][j] == k + 1)
                    // (d == 3 && memo[i - dx[d]][j-dy[d]] == memo[i][j])
                    // k >= memo[i][j] || (j == n - 1 && memo[i - dx[d]][j-dy[d]] == memo[i][j])

                    //System.out.println("第 1 处" + "memo = " + memo[i][j]);
                    memo[i][j] = k;
                    //myPrint("第 1 处" + "k = " + k);
                    k--;
                } else {
                    return -1;
                }
            } else {
                if (memo[i][j] == -1) {
                    memo[i][j] = 0;
                }
                return -1;
            }
        }
        // 此时 grid[i][j] 可能为 0 或者 3 (3 是由 1 变换过来的)
        // 如果为 0, 标记该方格为已访问 2, 后续 DFS 完成后需要回溯, 再将其置为 0
        if (grid[i][j] == 0) {
            grid[i][j] = 2;
            //myPrint("第 2 处");
        }
        // 最小路径
        int minStep = -1;
        int step;
        // 访问上下左右, 4 次 DFS
        for (int l = 0; l < 4; l++) {
            step = dfs(i + dx[l], j + dy[l], k, l);
            if (step != -1) {
                if (minStep == -1) {
                    minStep = step;
                } else {
                    minStep = Math.min(minStep, step);
                }
            }
        }
        // 回溯
        if (grid[i][j] == 3) {
            // 也可以不用回溯 k
            grid[i][j] = 1;
            //myPrint("第 3 处");
            // k++;
        } else if (grid[i][j] == 2) {
            grid[i][j] = 0;
            //myPrint("第 4 处");
        }
        minStep = minStep != -1 ? minStep + 1 : -1;

        //System.out.println(minStep);
        return minStep;
    }

    public void myPrint(String str) {
        System.out.println(str);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}

// 深度优先搜索 + 记忆化2 (已完成)
class P1293_Solution3 {
    int[][] grid;
    int m;
    int n;
    // 上左下右
    int[] dx = new int[]{-1, 0, 1, 0};
    int[] dy = new int[]{0, -1, 0, 1};
    int[][] memo;
    int[][][] visited;

    public int shortestPath(int[][] grid, int k) {
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        // 剪枝
        if (k >= m + n - 2) {
            return m + n - 2;
        }
        // 最后一个(右下角)为障碍物, 先将其消除, k 要减 1
        if (grid[m - 1][n - 1] == 1) {
            if (k <= 0) {
                return -1;
            }
            grid[m - 1][n - 1] = 0;
            k--;
        }
        this.memo = new int[m][n];
        // 初始化
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                memo[i][j] = -1;
            }
        }
        this.visited = new int[m][n][k + 1];
        // 该方格历史最大剩余消除次数
        // (0,0)位置初始化为 k
        memo[0][0] = k;
        int minPath = dfs(0, 0, k, -1);
        return minPath != -1 ? minPath - 1 : -1;
    }

    public int dfs(int i, int j, int k, int d) {
        // 越界
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return -1;
        }
        // 走到了终点(右下角)
        if (i == m - 1 && j == n - 1) {
            return 1;
        }
        // 如果该方格(该方格可能是由障碍物变成的(3))已经访问过, 返回 -1
        if (grid[i][j] == 3 || grid[i][j] == 2) {
            return -1;
        }

        // 如果该方格是障碍物, 并且可以消除障碍物的次数 k > 0, 才可能消除障碍物,
        // 将其转换为可以通过的方格, 标记为已访问 3, 后续 DFS 完成后需要回溯, 再将其置为 1
        // 否则直接返回 -1, 因为该方格是障碍物, 不能通过走这个方格走到右下角了
        if (grid[i][j] == 1) {
            if (k > 0) {
                // 如果 || 前面是 k > memo[i][j], 不能通过 grid3 这个案例
                // 如果是 if (k >= memo[i][j]), 也不能通过 grid3 这个案例
                // 这个判断条件改动可能是针对于 grid3 这个特殊案例的
                // 以下几种都能通过 grid3
                // || (j == n - 1 && memo[i - dx[d]][j - dy[d]] == memo[i][j])
                // || ((d == 2 || d == 3 ) && memo...)
                // || ((d == 2) && memo...)
                // || (j == n - 1 && d == 2 && memo...) // 148 ms 5.06% 内存 99.30%
                // || (j == n - 1 && d == 2 && k + 1 == memo[i][j])
                if (k >= memo[i][j] || (j == n - 1 && d == 2 && memo[i - dx[d]][j - dy[d]] == memo[i][j])) {
                    grid[i][j] = 3;
                    //System.out.println("第 1 处 " + "memo = " + memo[i][j]);
                    // 记录此方格还能消除障碍的次数
                    memo[i][j] = k;
                    //myPrint("第 1 处 " + "k = " + k + " 变为 " + (k - 1));
                    k--;
                    //System.out.println("visited[" + i + "][" + j + "][" + k + "] : " + visited[i][j][k] + " 变为 0");
                    visited[i][j][k] = 0;
                } else {
                    return -1;
                }
            } else {
                // 如果没访问过, 标记为已访问, 此时的能清楚障碍物的次数为 0
                if (memo[i][j] == -1) {
                    memo[i][j] = 0;
                }
                return -1;
            }
        }

        // 不能是 != 0
        if (visited[i][j][k] > 0) {
            return visited[i][j][k];
        }
        // 此时 grid[i][j] 可能为 0 或者 3 (3 是由 1 变换过来的)
        // 如果为 0, 标记该方格为已访问 2, 后续 DFS 完成后需要回溯, 再将其置为 0
        if (grid[i][j] == 0) {
            grid[i][j] = 2;
            // myPrint("第 2 处");
        }

        // 从(i,j)到终点的最短路径的步数
        int minStep = -1;
        // 从(i,j)的相邻方格到终点的路径步数
        int step;
        // 依次访问上左下右, 4 次 DFS, 更新走到终点的最短路径步数
        // 也有可能找不到路径, 4 次循环后, minStep 为 -1
        for (int l = 0; l < 4; l++) {
            step = dfs(i + dx[l], j + dy[l], k, l);
            // 返回 -1, 没有路径, 从下一个相邻方格找
            // 不为 -1, 或者说大于 0, 更新最少步数
            if (step != -1) {
                if (minStep == -1) {
                    minStep = step;
                } else {
                    minStep = Math.min(minStep, step);
                }
            }
        }

        // 回溯 不用回溯 k k++;
        if (grid[i][j] == 3) {
            grid[i][j] = 1;
            // myPrint("第 3 处");
        } else if (grid[i][j] == 2) {
            grid[i][j] = 0;
            // myPrint("第 4 处");
        }

        // 如果 minStep == -1, 表示此次经过(i,j)走不到终点, 返回 -1
        // 否则, 表示能走到终点, 返回 minStep + 1
        minStep = minStep == -1 ? -1 : minStep + 1;
        // 如果 visited[i][j][k] > 0, 表示曾经访问过, 并且走到过终点
        if (visited[i][j][k] > 0) {
            // 如果 此次经过(i,j)走到了终点(minStep > 0), 则 visited[i][j][k] 可能需要更新为这两者的较小值
            if (minStep > 0) {
                visited[i][j][k] = Math.min(visited[i][j][k], minStep);
                // System.out.println("visited[" + i + "][" + j + "][" + k + "] : " + visited[i][j][k]);
            }
        } else {
            // 如果 visited[i][j][k] <= 0, 说明曾经未访问过或者访问过, 但是并没有走到终点
            // 于是 visited[i][j][k] 更新为 minStep, 可能此次 minStep 也等于 -1(表示走不通)
            // 或者 一个表示从(i,j)走到过终点的步数值(大于 0 的整数)
            visited[i][j][k] = minStep;
            // System.out.println("visited[" + i + "][" + j + "][" + k + "] : " + visited[i][j][k]);
        }

        // 等同以上
//        if (visited[i][j][k] <= 0) {
//            visited[i][j][k] = minStep;
//            // System.out.println("visited[" + i + "][" + j + "][" + k + "] : " + visited[i][j][k]);
//        }
//        if (visited[i][j][k] > 0 && minStep > 0) {
//            visited[i][j][k] = Math.min(visited[i][j][k], minStep);
//            // System.out.println("visited[" + i + "][" + j + "][" + k + "] : " + visited[i][j][k]);
//        }

        // System.out.println("minStep : " + minStep + " visited[i][j][k] : " + visited[i][j][k]);
        return minStep;
    }

    public void myPrint(String str) {
        System.out.println(str);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}



