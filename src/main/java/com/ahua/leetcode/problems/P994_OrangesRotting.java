package com.ahua.leetcode.problems;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author huajun
 * @create 2022-01-07 23:43
 */

/**
 * 994. 腐烂的橘子 rotting-oranges
 * 在给定的网格中，每个单元格可以有以下三个值之一：
 * <p>
 * 值 0 代表空单元格；
 * 值 1 代表新鲜橘子；
 * 值 2 代表腐烂的橘子。
 * 每分钟，任何与腐烂的橘子（在 4 个正方向上）相邻的新鲜橘子都会腐烂。
 * <p>
 * 返回直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1。
 * <p>
 * 1 <= grid.length <= 10
 * 1 <= grid[0].length <= 10
 * grid[i][j] 仅为 0、1 或 2
 */
public class P994_OrangesRotting {
    public static void main(String[] args) {
        int[][] grid = new int[][]{{2, 1, 1}, {1, 1, 0}, {0, 1, 1}};
        int[][] grid1 = new int[][]{{2, 1, 1}, {0, 1, 1}, {1, 0, 1}};
        int[][] grid2 = new int[][]{{0, 2}};
        P994_Solution solution = new P994_Solution();
        System.out.println(solution.orangesRotting(grid)); // 4
        System.out.println(solution.orangesRotting(grid1)); // -1
        System.out.println(solution.orangesRotting(grid2)); // 0
    }
}

// 广度优先搜索(队列实现)
// 哈哈, 好久没写网格类的 DFS/BFS 了, 这次几乎算是一两次就通过了
class P994_Solution {
    public int orangesRotting(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // 上左下右
        int[] dx = new int[]{-1, 0, 1, 0};
        int[] dy = new int[]{0, -1, 0, 1};

        // 总新鲜橘子数
        int freshOranges = 0;
        // 烂橘子入队列
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 总新鲜橘子数加一
                    freshOranges++;
                } else if (grid[i][j] == 2) {
                    // 烂橘子入队列
                    queue.add(i * n + j);
                }
            }
        }
        // 总新鲜橘子数为 0, 需要 0 分钟
        if (freshOranges == 0) {
            return 0;
        }
        // 存在新鲜橘子, 但是没有烂橘子, 返回 -1
        if (queue.isEmpty()) {
            return -1;
        }

        // 最小分钟数初始化为 -1,
        // 当然也可以初始化为 0, 在最终返回时返回 minMinute - 1 即可
        // 还可以在自加时判断当时的 freshOranges 是否为 0, 当不为 0 时才自加
        // 还可以将 while 判断改为 while (freshOranges > 0 && !queue.isEmpty())
        int minMinute = -1;
        // 临时变量
        int size, id, x, y;
        while (!queue.isEmpty()) {
            // 当前队列中的所有烂橘子的相邻新鲜橘子都要在这一分钟中腐烂, 这一分钟中腐烂的橘子入队, 下一分钟中继续腐烂相邻新鲜橘子
            size = queue.size();
            minMinute++;
            for (int i = 0; i < size; i++) {
                // 该烂橘子所在方格
                id = queue.remove();
                x = id / n;
                y = id % n;
                int newX, newY;
                // 按照 上左下右 的顺序遍历相邻方格
                for (int j = 0; j < 4; j++) {
                    newX = x + dx[j];
                    newY = y + dy[j];
                    // 不要超出边界
                    if (newX >= 0 && newX < m && newY >= 0 && newY < n) {
                        // 如果是新鲜橘子, 将其腐烂, 并将其入队, 更新此时总新鲜橘子个数
                        // 如果是空格或者烂橘子就不管, 跳过
                        if (grid[newX][newY] == 1) {
                            // 新鲜橘子变烂
                            grid[newX][newY] = 2;
                            // 烂橘子加入队列
                            queue.add(newX * n + newY);
                            // 总新鲜橘子数减一
                            freshOranges--;
                        }
                    }
                }
//                // 这个判断可提前结束循环
//                if (freshOranges == 0) {
//                    return minMinute + 1;
//                }
            }
        }
        return freshOranges == 0 ? minMinute : -1;
    }
}