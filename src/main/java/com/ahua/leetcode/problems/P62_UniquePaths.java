package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-10-30 14:37
 */

/**
 * 一个机器人位于一个 m x n网格的左上角 （起始点在下图中标记为 “Start” ）。
 * <p>
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
 * <p>
 * 问总共有多少条不同的路径？
 */

public class P62_UniquePaths {
    public static void main(String[] args) {
        int m = 3;
        int n = 3;
        P62_Solution solution = new P62_Solution();
        System.out.println(solution.uniquePaths(m, n));
        System.out.println(solution.uniquePaths1(m, n));
        System.out.println(solution.uniquePaths2(m, n));
    }
}

class P62_Solution {
    // 简单的动态规划
    // 时间复杂度：O(mn)
    // 空间复杂度：O(mn)
    public int uniquePaths(int m, int n) {
        int[][] arr = new int[m + 1][n + 1];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 && j == n - 1) {
                    arr[m - 1][n - 1] = 1;
                } else {
                    arr[i][j] = arr[i + 1][j] + arr[i][j + 1];
                }
            }
        }
        return arr[0][0];
    }

    // 动态规划, 上一个方法的等价, 不用每次循环内判断
    // 此方法和代码实现与官方答案一极为类似, 可以等价
    // 时间复杂度：O(mn)
    // 空间复杂度：O(mn)
    public int uniquePaths1(int m, int n) {
        // 只有一行, 返回 1
        if (m == 1) {
            return 1;
        }
        int[][] arr = new int[m][n];
        // 最后一行置为 1
        for (int j = 0; j < n; j++) {
            arr[m - 1][j] = 1;
        }
        // 最后一列置为 1
        for (int i = 0; i < m; i++) {
            arr[i][n - 1] = 1;
        }
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                arr[i][j] = arr[i + 1][j] + arr[i][j + 1];
            }
        }
        return arr[0][0];
    }

    // 优化上一个方法的空间复杂度, 只用一个一维数组来记录路径长度,
    // 并且由于交换m,n的值对答案无影响, 因此该数组长度最好为 min(m,n)
    // 时间复杂度：O(mn)
    // 空间复杂度：O(min(m,n))
    public int uniquePaths2(int m, int n) {
        // 只有一行, 返回 1
        if (m == 1) {
            return 1;
        }
        // m 为较大值(代表行), n 为较小值(代表列)
        if (m < n) {
            int temp = m;
            m = n;
            n = temp;
        }
        int[] arr = new int[n];
        arr[n - 1] = 1;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                arr[j] += +arr[j + 1];
            }
        }
        return arr[0];
    }
}