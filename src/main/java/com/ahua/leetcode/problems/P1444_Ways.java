package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-09 14:27
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 给你一个 rows x cols 大小的矩形披萨和一个整数 k，矩形包含两种字符：'A' （表示苹果）和 '.' （表示空白格子）。
 * 你需要切披萨 k-1 次，得到 k 块披萨并送给别人。
 * <p>
 * 切披萨的每一刀，先要选择是向垂直还是水平方向切，再在矩形的边界上选一个切的位置，将披萨一分为二。
 * 如果垂直地切披萨，那么需要把左边的部分送给一个人，如果水平地切，那么需要把上面的部分送给一个人。
 * 在切完最后一刀后，需要把剩下来的一块送给最后一个人。
 * <p>
 * 请你返回确保每一块披萨包含至少一个苹果的切披萨方案数。由于答案可能是个很大的数字，请你返回它对 10^9 + 7 取余的结果。
 * <p>
 * 1 <= rows, cols <= 50
 * rows == pizza.length
 * cols == pizza[i].length
 * 1 <= k <= 10
 * pizza 只包含字符 'A' 和 '.'。
 */

public class P1444_Ways {
    public static void main(String[] args) {
        String[] pizza1 = new String[]{"A..", "AAA", "..."};
        String[] pizza2 = new String[]{"A..", "AA.", "..."};
        String[] pizza3 = new String[]{"A..", "A..", "..."};
        String[] pizza4 = new String[]{".A", "AA", "A."};
        String[] pizza5 = new String[]{".A..A", "A.A..", "A.AA.", "AAAA.", "A.AA."};
        P1444_Solution solution = new P1444_Solution();
        System.out.println(solution.ways(pizza1, 3)); // 3
        //System.out.println(solution.ways(pizza2, 3)); // 1
        //System.out.println(solution.ways(pizza3, 1)); // 1
        //System.out.println(solution.ways(pizza4, 3)); // 3
        //System.out.println(solution.ways(pizza5, 5)); // 153
    }
}

// 动态规划
class P1444_Solution {
    public int ways(String[] pizza, int k) {
        final int mod = (int) Math.pow(10, 9) + 7;
        int rows = pizza.length;
        int cols = pizza[0].length();
        int[][][] dp = new int[rows][cols][k + 1];
        int[][] apple = new int[rows + 1][cols + 1];
        // 遍历矩阵, 获取指定右下角矩阵中的苹果数量
/*        for (int i = rows - 1; i >= 0; i--) {
            for (int j = cols - 1; j >= 0; j--) {
                apple[i][j] = (pizza[i].charAt(j) == 'A' ? 1 : 0)
                        + apple[i + 1][j] + apple[i][j + 1] - apple[i + 1][j + 1];
            }
        }*/
        // 遍历每个小矩阵, 可得到将每个小矩阵切成 1 到 k 块的方案数
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = cols - 1; j >= 0; j--) {
                // 该矩阵中的苹果数量
                apple[i][j] = (pizza[i].charAt(j) == 'A' ? 1 : 0)
                        + apple[i + 1][j] + apple[i][j + 1] - apple[i + 1][j + 1];
                // 该矩阵中有苹果, 如果没有苹果, 怎么切都没用, 直接下次循环
                if (apple[i][j] > 0) {
                    // 如果只切成一块, 方案数为 1;
                    dp[i][j][1] = 1;
                    // 如果切成 2...k 块, 枚举每种情况横着切和竖着切的方案和 dp[i][j][block]
                    // 本应是 block <= k, 但是如果本身此矩阵内的苹果数就小于要切成的块数 block,
                    // 这种要切成的块数情况不能满足每个块内都有苹果, 于是将结束条件设为
                    // [block <= Math.min(k, apple[i][j])], 可提前结束循环
                    for (int block = 2; block <= k; block++) {
                        // 横着切
                        // horCutNum 横着切的次数
                        for (int horCutNum = (rows - 1) - i; horCutNum >= 1; horCutNum--) {
                            // 如果当前横着切一刀后, 切掉的 [上边] 矩阵内有苹果, dp[i][j][block] 的值
                            // 应加上被切掉后剩下的 [下边] 矩阵切成 block - 1 块的方案数值
                            // 如果没有苹果, 说明此条 [水平线] 不能切, 接着循环判断下一条
                            if (apple[i][j] - apple[i + horCutNum][j] > 0) {
                                dp[i][j][block] = (dp[i][j][block] + dp[i + horCutNum][j][block - 1]) % mod;
                            }
                        }
                        // 竖着切
                        // verWaysNum 竖着切的次数
                        for (int verWaysNum = (cols - 1) - j; verWaysNum >= 1; verWaysNum--) {
                            // 如果当前竖着切一刀后, 切掉的 [左边] 矩阵内有苹果, dp[i][j][block] 的值
                            // 应加上被切掉后剩下的 [右边] 矩阵切成 block - 1 块的方案数值
                            // 如果没有苹果, 说明此条 [垂直线] 不能切, 接着循环判断下一条
                            if (apple[i][j] - apple[i][j + verWaysNum] > 0) {
                                dp[i][j][block] = (dp[i][j][block] + dp[i][j + verWaysNum][block - 1]) % mod;
                            }
                        }
                    }
                }
            }
        }
        // 返回将整个大矩阵切成 k 块的方案数
        return dp[0][0][k];
    }
}

// 深度优先搜索 (未通过 LeetCode 用例) 可删除
class P1444_Solution1 {
    String[] pizza;
    int rows;
    int cols;
    public int ways(String[] pizza, int k) {
        this.pizza = pizza;
        this.rows = this.pizza.length;
        this.cols = this.pizza[0].length();
        int[][][] horCutWays = new int[rows][cols][k];
        int[][][] verCutWays = new int[rows][cols][k];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Arrays.fill(horCutWays[i][j], -1);
                Arrays.fill(verCutWays[i][j], -1);
            }
        }

        return cutPizza(0, 0, k - 1, horCutWays, verCutWays);
    }

    // 深度优先搜索 (未通过 LeetCode 用例)
    public int cutPizza(int r, int c, int cutNum, int[][][] horCutWays, int[][][] verCutWays) {
        // 超出边界了
        if (r == rows || c == cols) {
            return 0;
        }
        // horizontal 水平
        int horWaysNum = 0;
        // vertical 垂直
        int verWaysNum = 0;
        int waysNum = 0;
        // HashMap<Integer, Integer> map1 = new HashMap<>();
        // HashMap<Integer, Integer> map2 = new HashMap<>();
        for (int i = r; i < rows; i++) {
            for (int j = c; j < cols; j++) {
                if (pizza[i].charAt(j) == 'A') {
                    if (cutNum == 0) {
                        // System.out.println("退出循环=====i : " + i + ", j : " + j + ", cutNum : " + cutNum);
                        //horCutWays[r][c][0] = 1;
                        //verCutWays[r][c][0] = 1;
                        return 1;
                    }
                    // 横着切
                    if (horCutWays[r][c][cutNum] == -1) {
                        horWaysNum = cutPizza(i + 1, c, cutNum - 1, horCutWays, verCutWays);
                        for (int co = 0; co < cols; co++) {
                            horCutWays[i][co][cutNum] = horWaysNum;
                        }
                        // horCutWays[i][c][cutNum - 1] = waysNum;
                    }
                    // 竖着切
                    if (verCutWays[r][c][cutNum] == -1) {
                        verWaysNum = cutPizza(r, j + 1, cutNum - 1, horCutWays, verCutWays);
                        for (int ro = 0; ro < rows; ro++) {
                            verCutWays[ro][j][cutNum] = verWaysNum;
                        }
                        // horCutWays[i][c][cutNum - 1] = waysNum;
                    }
                    waysNum += horWaysNum + verWaysNum;
//                    if (ways[r][c][cutNum] != -1) {
//                        waysNum = ways[r][c][cutNum];
//                        // temp = cutPizza(pizzaChar, i + 1, c, cutNum - 1, ways);
//                        // waysNum += temp;
//                        // System.out.println("横切=====i : " + i + ", j : " + j + ", cutNum : " + cutNum + ", horWaysNum : " + horWaysNum);
//                        // map1.put(i, temp);
//                    } else {
//                        // 横着切
//                        waysNum += cutPizza(pizzaChar, i + 1, c, cutNum - 1, ways);
//                        ways[i][c][cutNum - 1] = waysNum;
//                        // 竖着切
//                        waysNum += cutPizza(pizzaChar, r, j + 1, cutNum - 1, ways);
//                        ways[r][j][cutNum - 1] = waysNum;
//                    }
//                    // 竖着切
//                    if (map2.get(j) == null) {
//                        //System.out.println("竖切=====i : " + i + ", j : " + j + ", cutNum : " + cutNum + ", verWaysNum : " + verWaysNum);
//                        temp = cutPizza(pizzaChar, r, j + 1, cutNum - 1, ways);
//                        waysNum += temp;
//                        map2.put(j, temp);
//                    }
                }
            }
        }
        // System.out.println("=======waysNum : " + waysNum + ", cutNum : " + cutNum);
        return waysNum;
    }
}
