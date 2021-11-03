package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-03 15:23
 */

/**
 * 泰波那契序列 Tn 定义如下：
 * <p>
 * T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2
 * <p>
 * 给你整数 n，请返回第 n 个泰波那契数 Tn 的值。
 * <p>
 * 0 <= n <= 37
 * 答案保证是一个 32 位整数，即 answer <= 2^31 - 1。
 */

public class P1137_Tribonacci {
    public static void main(String[] args) {
//        P1137_Solution solution = new P1137_Solution();
//        // 动态规划
//        System.out.println(solution.tribonacci(8));
//        // 递归
//        System.out.println(solution.tribonacci1(8));
//
        P1137_Solution1 solution1 = new P1137_Solution1();
        // 快速矩阵幂
        System.out.println(solution1.tribonacci(8));
    }
}

class P1137_Solution {
    // 动态规划
    public int tribonacci(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1 || n == 2) {
            return 1;
        }
        int[] dp = new int[]{0, 1, 1, 0};
        for (int i = 3; i < n + 1; i++) {
            dp[3] = dp[2] + dp[1] + dp[0];
            dp[0] = dp[1];
            dp[1] = dp[2];
            dp[2] = dp[3];
        }
        return dp[3];
    }

    // 递归
    public int tribonacci1(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1 || n == 2) {
            return 1;
        }
        return tribonacci1(n - 1) + tribonacci1(n - 2) + tribonacci1(n - 3);
    }
}

class P1137_Solution1 {
    // 快速矩阵幂
    public int tribonacci(int n) {
        if (n == 0) {
            return 0;
        }
        if (n <= 2) {
            return 1;
        }
        int[][] q = {{1, 1, 1}, {1, 0, 0}, {0, 1, 0}};
        int[][] res = pow(q, n);
        // 此处为何返回 res[0][2], 而不是 res[2][0] + res[2][1] ?
        return res[0][2];
    }

    // n 个矩阵 a 相乘
    public int[][] pow(int[][] a, int n) {
        int[][] ret = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        while (n > 0) {
            if ((n & 1) == 1) {
                ret = multiply(ret, a);
            }
            n >>= 1;
            a = multiply(a, a);
        }
        return ret;
    }

    // 矩阵 a 与矩阵 b 相乘
    public int[][] multiply(int[][] a, int[][] b) {
        int[][] c = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j] + a[i][2] * b[2][j];
            }
        }
        return c;
    }
}