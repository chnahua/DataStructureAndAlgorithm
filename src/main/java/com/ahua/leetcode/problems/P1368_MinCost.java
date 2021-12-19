package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-18 18:08
 */

import java.util.*;

/**
 * 给你一个 m x n 的网格图 grid 。 grid 中每个格子都有一个数字，对应着从该格子出发下一步走的方向。
 * grid[i][j] 中的数字可能为以下几种情况：
 * <p>
 * 1 ，下一步往右走，也就是你会从 grid[i][j] 走到 grid[i][j + 1]
 * 2 ，下一步往左走，也就是你会从 grid[i][j] 走到 grid[i][j - 1]
 * 3 ，下一步往下走，也就是你会从 grid[i][j] 走到 grid[i + 1][j]
 * 4 ，下一步往上走，也就是你会从 grid[i][j] 走到 grid[i - 1][j]
 * 注意网格图中可能会有 无效数字 ，因为它们可能指向 grid 以外的区域。
 * <p>
 * 一开始，你会从最左上角的格子 (0,0) 出发。我们定义一条 有效路径 为从格子 (0,0) 出发，
 * 每一步都顺着数字对应方向走，最终在最右下角的格子 (m - 1, n - 1) 结束的路径。
 * 有效路径 不需要是最短路径 。
 * <p>
 * 你可以花费 cost = 1 的代价修改一个格子中的数字，但每个格子中的数字 只能修改一次 。
 * <p>
 * 请你返回让网格图至少有一条有效路径的最小代价。
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 100
 */
public class P1368_MinCost {
    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {1, 1, 1, 1},
                {2, 2, 2, 2},
                {1, 1, 1, 1},
                {2, 2, 2, 2}
        };
        int[][] grid1 = new int[][]{
                {1, 1, 3},
                {3, 2, 2},
                {1, 1, 4}
        };
        int[][] grid2 = new int[][]{
                {1, 2},
                {4, 3}
        };
        int[][] grid3 = new int[][]{
                {2, 2, 2},
                {2, 2, 2},
        };
        int[][] grid4 = new int[][]{{4}};
        // DFS 超时案例, 只能通过前 13 行, minCost 为 10
        int[][] grid5 = new int[][]{
                {3, 4, 3},
                {2, 2, 2},
                {2, 1, 1},
                {4, 3, 2},
                {2, 1, 4},
                {2, 4, 1},
                {3, 3, 3},
                {1, 4, 2},
                {2, 2, 1},
                {2, 1, 1},
                {3, 3, 1},
                {4, 1, 4},
                {2, 1, 4},
                {3, 2, 2},
                {3, 3, 1},
                {4, 4, 1},
                {1, 2, 2},
                {1, 1, 1},
                {1, 3, 4},
                {1, 2, 1},
                {2, 2, 4},
                {2, 1, 3},
                {1, 2, 1},
                {4, 3, 2},
                {3, 3, 4},
                {2, 2, 1},
                {3, 4, 3},
                {4, 2, 3},
                {4, 4, 4}
        };
        P1368_Solution solution = new P1368_Solution();
        System.out.println(solution.minCost(grid)); // 3
        System.out.println(solution.minCost(grid1)); // 0
        System.out.println(solution.minCost(grid2)); // 1
        System.out.println(solution.minCost(grid3)); // 3
        System.out.println(solution.minCost(grid4)); // 0
        System.out.println(solution.minCost(grid5)); // 18
    }
}

// DFS, 超时, 未通过 LeetCode 所有测试
class P1368_Solution1 {
    int[][] grid;
    int m;
    int n;
    // 右左下上
    final int[] dx = new int[]{0, 0, 1, -1};
    final int[] dy = new int[]{1, -1, 0, 0};
    int maxCost;

    public int minCost(int[][] grid) {
        // 题目保证 grid != null , 不为空
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        this.maxCost = m + n - 2;
        // 可删除, 已包含在 dfs 中
        if (m == 1 && n == 1) {
            return 0;
        }

        return dfs(0, 0);
    }

    private int dfs(int i, int j) {
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return maxCost;
        }
        if (i == m - 1 && j == n - 1) {
            return 0;
        }
        if (grid[i][j] > 4) {
            return maxCost;
        }

        // 保存该方格的值, 走的方向
        int direction = grid[i][j];
        // +4, 代表已访问
        grid[i][j] += 4;
        // 初始化为最大花费
        int minCost = maxCost;

        for (int k = 0; k < 4; k++) {
            if (k == direction - 1) {
                minCost = Math.min(minCost, dfs(i + dx[k], j + dy[k]));
                if (minCost == 0) {
                    break;
                }
            } else {
                minCost = Math.min(minCost, dfs(i + dx[k], j + dy[k]) + 1);
            }
        }
        // 回溯
        grid[i][j] -= 4;
        return minCost;
    }
}

// BFS, 我的 BFS 错误做法, 认为只是需要判断从左上过来就行了, 结果不能
class P1368_Solution2 {
    int[][] grid;
    int m;
    int n;
    // 右左下上
    final int[] dx = new int[]{0, 0, 1, -1};
    final int[] dy = new int[]{1, -1, 0, 0};
    int maxCost;

    public int minCost(int[][] grid) {
        // 题目保证 grid != null , 不为空
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        this.maxCost = m + n - 2;
        // 可删除
        if (m == 1 && n == 1) {
            return 0;
        }
        int[][] dp = new int[m][n];
        for (int[] row : dp) {
            Arrays.fill(row, maxCost);
        }
        dp[0][0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        // 标记为已访问
        grid[0][0] += 4;
        int id, i, j;
        while (!queue.isEmpty()) {
            id = queue.remove();
            i = id / n;
            j = id % n;

            // 左 -> 右
            if (inArea(i, j - 1)) {
                if (grid[i][j - 1] - 4 == 1) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - 1]);
                } else {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
                }
            }
            // 上 -> 下
            if (inArea(i - 1, j)) {
                if (grid[i - 1][j] - 4 == 3) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j]);
                } else {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + 1);
                }
            }

            int x, y;
            for (int k = 0; k < 4; k++) {
                x = i + dx[k];
                y = j + dy[k];
                if (inArea(x, y) && grid[x][y] <= 4) {
                    queue.add(x * n + y);
                    // 标记为已访问
                    grid[x][y] += 4;
                }
            }
        }

        // System.out.println(Arrays.deepToString(dp));
        return dp[m - 1][n - 1];
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < m && j >= 0 && j < n;
    }
}

// 广度优先搜索（队列实现）
class P1368_Solution3 {
    int[][] grid;
    int m;
    int n;
    // 右左下上
    final int[] dx = new int[]{0, 0, 1, -1};
    final int[] dy = new int[]{1, -1, 0, 0};
    int maxCost;

    public int minCost1(int[][] grid) {
        // 题目保证 grid != null , 不为空
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        this.maxCost = m + n - 2;
        // 可删除
        if (m == 1 && n == 1) {
            return 0;
        }

        // int[][] num = new int[m][n];
        int count = 0;
        int[][] dp = new int[m][n];
        for (int[] row : dp) {
            Arrays.fill(row, maxCost);
        }
        dp[0][0] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);

        int id, i, j;
        while (!queue.isEmpty()) {
            id = queue.remove();
            i = id / n;
            j = id % n;
            // num[i][j]++;
            count++;
            int x, y;
            int minCost;
            for (int k = 0; k < 4; k++) {
                x = i + dx[k];
                y = j + dy[k];
                if (inArea(x, y)) {
                    minCost = dp[i][j] + (grid[i][j] == k + 1 ? 0 : 1);
                    // System.out.println(Arrays.deepToString(dp));
                    if (minCost < dp[x][y]) {
                        queue.add(x * n + y);
                        dp[x][y] = minCost;
                    }
                }
            }
        }

        // System.out.println(Arrays.deepToString(dp));
        // System.out.println(Arrays.deepToString(num));
        System.out.println(count);
        return dp[m - 1][n - 1];
    }

    // 优化-SPFA, 但是貌似效果并不明显
    public int minCost(int[][] grid) {
        // 题目保证 grid != null , 不为空
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        this.maxCost = m + n - 2;
        // 可删除
        if (m == 1 && n == 1) {
            return 0;
        }

        int[][] cost = new int[m][n];
        for (int[] row : cost) {
            Arrays.fill(row, maxCost);
        }
        cost[0][0] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        // 需要记录当前(i,j)是否在队列中, 可以新建一个数组来记录, 亦或者通过改变 grid[i][j] 处的值来标记是否访问(是否在队列中)
        // 表示在队列中
        grid[0][0] += 4;

        int id, i, j;
        while (!queue.isEmpty()) {
            id = queue.remove();
            i = id / n;
            j = id % n;

            // 表示不在队列中
            grid[i][j] -= 4;

            int x, y;
            int minCost;
            for (int k = 0; k < 4; k++) {
                x = i + dx[k];
                y = j + dy[k];
                if (inArea(x, y)) {
                    // 是顺方向的没有代价(不加 1)
                    if (grid[i][j] == k + 1 || grid[i][j] == k + 5) {
                        minCost = cost[i][j];
                    } else {
                        minCost = cost[i][j] + 1;
                    }

                    // 如果要此次从 (i,j) 到 (x,y), 更新, 那么应当比曾经到 (x,y) 的代价要更小
                    // 即 minCost < cost[x][y] 时, 找到新的一条到 (x,y) 的代价更小的路径, 则需要更新其代价值
                    // 如果当前方格 (x,y) 已经在队列中, 即随后就会遍历它时, 可以不将它入队, 只需要更新其代价值
                    // 而如果不在队列中, 由于此次又更新了它的代价值(更小了), 那么就有可能通过方格(x,y) 而找到距离终点代价值更小的路径
                    // 故需要将其加入队列中

                    // 其实正常的思路是, 四个方向都应该加入队列, 但是有代价值更小以及当前队列中不需要重复遍历同一个方格
                    // 当队列中没有该方格(后续不会遍历该方格)时, 才将其加入队列
                    if (minCost < cost[x][y]) {
                        cost[x][y] = minCost;
                        if (grid[x][y] <= 4) {
                            queue.add(x * n + y);
                            // 表示在队列中
                            grid[x][y] += 4;
                        }
                    }
                }
            }
        }

        return cost[m - 1][n - 1];
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < m && j >= 0 && j < n;
    }
}

// Dijkstra 算法(优先级队列实现)(次优解法)
class P1368_Solution4 {
    int[][] grid;
    int m;
    int n;
    // 右左下上
    final int[] dx = new int[]{0, 0, 1, -1};
    final int[] dy = new int[]{1, -1, 0, 0};
    int maxCost;

    public int minCost(int[][] grid) {
        // 题目保证 grid != null , 不为空
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        this.maxCost = m + n - 2;
        // 可删除
        if (m == 1 && n == 1) {
            return 0;
        }

        int[][] cost = new int[m][n];
        for (int[] row : cost) {
            Arrays.fill(row, maxCost);
        }
        cost[0][0] = 0;

        // 优先队列(小顶堆)
        // PriorityQueue<Node> queue = new PriorityQueue<>((n1, n2) -> n1.cost - n2.cost);
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        queue.add(new Node(0, 0));

        int id, i, j;
        while (!queue.isEmpty()) {
            // 每次出队都是选择距离起点最短的节点, 遍历该节点的四个方向的节点, 更新这四个方向的节点到通过该节点到起点的距离
            // 如果找到距离起点更近的节点, 则将其添加进队列
            // 由于此队列是优先级队列, 下次出队时会自动选择能够构建最短路径的节点, 继续遍历
            id = queue.remove().id;
            i = id / n;
            j = id % n;

            // 需要记录当前(i,j)距离起点的距离最小值是否得到, 可以新建一个数组来记录, 亦或者通过改变 grid[i][j] 处的值来标记是否访问过(是否得到)
            // 如果已经访问过, 不再访问
            if (grid[i][j] > 4) {
                continue;
            }
            // 置为已经访问过, 起点(0,0)到当前节点(i,j)的距离已经在节点(i,j)添加到队列中时确定, 并且该路径是到目前为止的最短路径
            grid[i][j] += 4;

            int x, y;
            int minCost;
            for (int k = 0; k < 4; k++) {
                x = i + dx[k];
                y = j + dy[k];
                if (inArea(x, y)) {
                    // 是顺方向的没有代价(不加 1)
                    minCost = cost[i][j] + (grid[i][j] == k + 5 ? 0 : 1);

                    // 如果要此次从 (i,j) 到 (x,y), 更新, 那么应当比曾经到 (x,y) 的代价要更小
                    // 即 minCost < cost[x][y] 时, 找到新的一条到 (x,y) 的代价更小的路径, 则需要更新其代价值
                    // 并将其加入到队列中

                    if (minCost < cost[x][y]) {
                        cost[x][y] = minCost;
                        // 此处肯定 grid[x][y] <= 4
                        queue.add(new Node(x * n + y, cost[x][y]));
                    }
                }
            }
        }

        return cost[m - 1][n - 1];
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < m && j >= 0 && j < n;
    }

    static class Node {
        int id;
        int cost;

        public Node(int id, int cost) {
            this.id = id;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "id:" + id + ", cost:" + cost;
        }
    }
}

// 0-1 广度优先搜索(双端队列实现)(最优解法)
// 类似 Dijkstra, 貌似就是对 Dijkstra 中优先队列需要排序, 找到最小的代价节点的优化
// 使用双端节点, 每次选择代价为 0 (顺方向)的入队出队, 如果没有顺方向的, 再选择代价为 1 的入队
// 那这么说来其实双端队列就等于两个队列, 不需要代价的加入 0 队列, 需要代价的加入 1 队列, 当队列 0 为空时, 出队 1 队列
// 比起随机的两者添加到同一个队列中, 这种方式能够减少某些节点的入队次数, 从而节省时间消耗
class P1368_Solution {
    int[][] grid;
    int m;
    int n;
    // 右左下上
    final int[] dx = new int[]{0, 0, 1, -1};
    final int[] dy = new int[]{1, -1, 0, 0};
    int maxCost;

    public int minCost(int[][] grid) {
        // 题目保证 grid != null , 不为空
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        this.maxCost = m + n - 2;
        // 可删除
        if (m == 1 && n == 1) {
            return 0;
        }

        int[][] cost = new int[m][n];
        for (int[] row : cost) {
            Arrays.fill(row, maxCost);
        }
        cost[0][0] = 0;

        // 双端队列
        Deque<Integer> deque = new LinkedList<>();
        deque.add(0);

        int id, i, j;
        while (!deque.isEmpty()) {
            id = deque.removeFirst();
            i = id / n;
            j = id % n;

            // 需要记录当前(i,j)距离起点的距离最小值是否得到, 可以新建一个数组来记录, 亦或者通过改变 grid[i][j] 处的值来标记是否访问过(是否得到)
            // 如果已经访问过, 不再访问
            // 在常规的广度优先搜索中, 每个节点最多被添加进队列一次, 而在 0-1 广度优先搜索中, 每个节点最多被添加进双端队列两次(即队首一次, 队尾一次)
            // 因为我们求的是最短路径, 所以当第一次出队列时, 就将该节点标记为已读, 第二次出队列时就不用再处理了
            if (grid[i][j] > 4) {
                continue;
            }
            // 置为已经访问过, 起点(0,0)到当前节点(i,j)的距离已经在节点(i,j)添加到队列中时确定, 并且该路径是到目前为止的最短路径
            grid[i][j] += 4;

            int x, y;
            int minCost;
            for (int k = 0; k < 4; k++) {
                x = i + dx[k];
                y = j + dy[k];
                if (inArea(x, y)) {
                    // 是顺方向的没有代价(不加 1)
                    minCost = cost[i][j] + (grid[i][j] == k + 5 ? 0 : 1);

                    // 如果要此次从 (i,j) 到 (x,y), 更新, 那么应当比曾经到 (x,y) 的代价要更小
                    // 即 minCost < cost[x][y] 时, 找到新的一条到 (x,y) 的代价更小的路径, 则更新其代价值, 并将其添加到队列中
                    // 而又由于有可能需要代价或者不需要代价
                    // 那么为了先遍历无代价的, 更有可能先走到终点(代价也能最快最小), 可将其加入到队列 0, 而需要代价的加入到队列 1,
                    // 出队时, 只要队列 0 中有节点, 就先出队队列 0 的节点
                    // 这里的队列 0 1 用数据结构 Deque 双端队列来模拟, 分别添加到队列头和队列尾

                    if (minCost < cost[x][y]) {
                        cost[x][y] = minCost;
                        // 此处肯定 grid[x][y] <= 4
                        if (grid[i][j] == k + 5) {
                            deque.addFirst(x * n + y);
                        } else {
                            deque.addLast(x * n + y);
                        }
                    }
                }
            }
        }

        return cost[m - 1][n - 1];
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < m && j >= 0 && j < n;
    }
}