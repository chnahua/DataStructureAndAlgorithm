package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-08 15:22
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 给你一个大小为 n x n 二进制矩阵 grid 。最多 只能将一格 0 变成 1 。
 *
 * 返回执行此操作后，grid 中最大的岛屿面积是多少？
 *
 * 岛屿 由一组上、下、左、右四个方向相连的 1 形成。
 *
 * n == grid.length
 * n == grid[i].length
 * 1 <= n <= 500
 * grid[i][j] 为 0 或 1
 */

public class P827_LargestIsland {
    public static void main(String[] args) {
        int[][] grid3 = new int[][]{
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
        };
        int[][] grid = new int[][]{{1, 0}, {0, 1}};
        int[][] grid1 = new int[][]{{1, 1}, {1, 0}};
        int[][] grid2 = new int[][]{{1, 1}, {1, 1}};

        int[][] grid4 = new int[][]{{0, 0}, {0, 1}};

        // 深度优先搜索
        P827_Solution solution = new P827_Solution();
        System.out.println(solution.largestIsland(grid)); // 3
        System.out.println(solution.largestIsland(grid1)); // 4
        System.out.println(solution.largestIsland(grid2)); // 4

        P827_Solution1 solution1 = new P827_Solution1();
        System.out.println(solution1.largestIsland(grid3)); // 12
    }
}

// 深度优先搜索(递归实现)
class P827_Solution {
    int[][] grid;
    int[] dx = new int[]{-1, 1, 0, 0};
    int[] dy = new int[]{0, 0, -1, 1};
    // 保存 grid 中各岛屿的编号 id 及其对应面积 area
    Map<Integer, Integer> hashmap = new HashMap<>();
    // 保存当前(i, j)坐标水域上下左右相邻的陆地的编号
    Set<Integer> hashSet = new HashSet<>();

    public int largestIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        this.grid = grid;
        int m = grid.length;
        int n = grid[0].length;
        // 最终结果, 最大岛屿面积
        int maxArea = 0;
        // 一个岛屿的面积
        int area;
        // 所有水域的面积和, 不影响最终结果, 只为减少循环次数
        int waterArea = m * n;
        // 岛屿编号, 从 2 号开始
        int id = 2;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 得到编号为 id 的岛屿的面积
                    // dfs 过程中会将属于这个岛屿的陆地("1")全部变为岛屿的 id
                    area = dfs(i, j, id);
                    // 将 (id, area) 加入到 map 中
                    hashmap.put(id, area);
                    maxArea = Math.max(maxArea, area);
                    // System.out.println("area : " + area + ", id : " + id + ", maxArea : " + maxArea);
                    waterArea -= area;
                    id++;
                }
            }
        }
        if (maxArea == 0) {
            return 1;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    // 将当前(i, j)坐标水域变为陆地
                    mergeIsland(i, j);
                    // 得到当前(i, j)坐标水域变为陆地后, 可能将多块岛屿连接后形成的整块大岛屿的面积
                    area = getMergedArea();
                    // 比较得到当前所有合并后的岛屿的最大面积
                    maxArea = Math.max(maxArea, area);
                    waterArea--;
                    if (waterArea == 0) {
                        break;
                    }
                }
            }
            if (waterArea == 0) {
                break;
            }
        }
        return maxArea;
    }

    /**
     * 得到当前(i, j)坐标水域变为陆地后, 可能将多块岛屿连接后形成的整块大岛屿的面积
     *
     * @return area
     */
    public int getMergedArea() {
        // 表示转变这个水域为陆地, 其面积为 1
        int area = 1;
        // 取出相邻的陆地编号, hashMap 中得到编号为 idNum 的岛屿的面积, 并累加
        for (Integer idNum : hashSet) {
            area += hashmap.get(idNum);
        }
        hashSet.clear();
        return area;
    }

    /**
     * 将当前(i, j)坐标水域上下左右相邻的陆地的编号(大于0)加入到 hashSet 中
     * 如果相邻为水域则不加入, 不做任何操作
     *
     * @param i 当前水域的横坐标
     * @param j 当前水域的纵坐标
     */
    public void mergeIsland(int i, int j) {
        int x, y;
        for (int k = 0; k < 4; k++) {
            x = i + dx[k];
            y = j + dy[k];
            if (inGrid(x, y) && grid[x][y] > 0) {
                hashSet.add(grid[x][y]);
            }
        }
    }

    /**
     * 得到得到编号为 id 的岛屿的面积, 并将相应值改为 id
     *
     * @param i  格子的横坐标
     * @param j  格子的纵坐标
     * @param id 岛屿的编号
     * @return area 该 id 岛屿的面积
     */
    public int dfs(int i, int j, int id) {
        if (inGrid(i, j) && grid[i][j] == 1) {
            grid[i][j] = id;
            int x, y;
            int area = 1;
            for (int k = 0; k < 4; k++) {
                x = i + dx[k];
                y = j + dy[k];
                area += dfs(x, y, id);
            }
            return area;
        }
        return 0;
    }

    /**
     * 判断坐标 (x, y) 是否在网格中
     *
     * @param x 格子的横坐标
     * @param y 格子的纵坐标
     * @return true 在网格中; false 不在网格中, 超出边界
     */
    public boolean inGrid(int x, int y) {
        return inGridX(x) && inGridY(y);
    }

    public boolean inGridX(int x) {
        return x >= 0 && x < grid.length;
    }

    public boolean inGridY(int y) {
        return y >= 0 && y < grid[0].length;
    }
}

// 深度优先搜索(递归实现)
// 本想着优化上方式的循环次数, 但是在 LeetCode 上执行的效率却不及前者
class P827_Solution1 {
    int[][] grid;
    int[] dx = new int[]{-1, 1, 0, 0};
    int[] dy = new int[]{0, 0, -1, 1};
    // 保存 grid 中各岛屿的编号 id 及其对应面积 area
    Map<Integer, Integer> hashmap = new HashMap<>();
    // 保存当前(i, j)坐标水域上下左右相邻的陆地的编号
    Set<Integer> hashSet = new HashSet<>();
    // 保存格子为水域的横纵坐标, 以 waterId(waterId = i * n + j) 的单个整数的方式保存为一个集合
    Set<Integer> waterSet = new HashSet<>();

    public int largestIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        this.grid = grid;
        int m = grid.length;
        int n = grid[0].length;
        // 最终结果, 最大岛屿面积
        int maxArea = 0;
        // 一个岛屿的面积
        int area;
        // 岛屿编号, 从 2 号开始
        int id = 2;
        // 1.遍历 grid[][], 得到所有岛屿及其面积并编号
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 得到编号为 id 的岛屿的面积
                    // dfs 过程中会将属于这个岛屿的陆地("1")全部变为岛屿的 id
                    area = dfs(i, j, id);
                    // 将 (id, area) 加入到 map 中
                    hashmap.put(id, area);
                    // maxArea = Math.max(maxArea, area);
                    // System.out.println("area : " + area + ", id : " + id + ", maxArea : " + maxArea);
                    id++;
                } else if (grid[i][j] == 0) {
                    // 将水域加入 waterSet
                    waterSet.add(i * n + j);
                }
            }
        }

        // 可提前结束执行的一些特殊情况, 可删除, 删除后不影响最终结果
        // 全为陆地, 或者只有一块水域, return m * n
        int size = waterSet.size();
        if (size == 0 || size == 1) {
            return m * n;
        } else if (size == m * n) {
            // 全为水域, return 1
            return 1;
        }
        // 存在水域, 但只有一个岛屿, 返回 2 号岛屿的面积 + 1
        if (hashmap.size() == 1) {
            return hashmap.get(2) + 1;
        }

        // 2.遍历每个水域, 得到各个水域变为陆地后, 将多个岛屿连接后形成的整块大岛屿的面积
        for (Integer waterId : waterSet) {
            // 如果当前(i, j)坐标水域变为陆地
            mergeIsland(waterId / n, waterId % n);
            // 得到当前(i, j)坐标水域变为陆地后, 可能将多块岛屿连接后形成的整块大岛屿的面积
            area = getMergedArea();
            // 比较得到当前所有合并后的岛屿的最大面积
            maxArea = Math.max(maxArea, area);
        }
        return maxArea;
    }

    /**
     * 得到当前(i, j)坐标水域变为陆地后, 可能将多块岛屿连接后形成的整块大岛屿的面积
     *
     * @return area
     */
    public int getMergedArea() {
        // 表示转变这个水域为陆地, 其面积为 1
        int area = 1;
        // 取出相邻的陆地编号, hashMap 中得到编号为 idNum 的岛屿的面积, 并累加
        for (Integer idNum : hashSet) {
            area += hashmap.get(idNum);
        }
        hashSet.clear();
        return area;
    }

    /**
     * 将当前(i, j)坐标水域上下左右相邻的陆地的编号(大于0)加入到 hashSet 中
     * 如果相邻为水域则不加入, 不做任何操作
     *
     * @param i 当前水域的横坐标
     * @param j 当前水域的纵坐标
     */
    public void mergeIsland(int i, int j) {
        int x, y;
        for (int k = 0; k < 4; k++) {
            x = i + dx[k];
            y = j + dy[k];
            if (inGrid(x, y) && grid[x][y] > 0) {
                hashSet.add(grid[x][y]);
            }
        }
    }

    /**
     * 得到得到编号为 id 的岛屿的面积, 并将相应值改为 id
     *
     * @param i  格子的横坐标
     * @param j  格子的纵坐标
     * @param id 岛屿的编号
     * @return area 该 id 岛屿的面积
     */
    public int dfs(int i, int j, int id) {
        if (inGrid(i, j) && grid[i][j] == 1) {
            grid[i][j] = id;
            int x, y;
            int area = 1;
            for (int k = 0; k < 4; k++) {
                x = i + dx[k];
                y = j + dy[k];
                area += dfs(x, y, id);
            }
            return area;
        }
        return 0;
    }

    /**
     * 判断坐标 (x, y) 是否在网格中
     *
     * @param x 格子的横坐标
     * @param y 格子的纵坐标
     * @return true 在网格中; false 不在网格中, 超出边界
     */
    public boolean inGrid(int x, int y) {
        return inGridX(x) && inGridY(y);
    }

    public boolean inGridX(int x) {
        return x >= 0 && x < grid.length;
    }

    public boolean inGridY(int y) {
        return y >= 0 && y < grid[0].length;
    }
}
