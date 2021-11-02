package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-02 19:51
 */

/**
 * 斐波那契数，通常用 F(n) 表示，形成的序列称为 斐波那契数列 。
 * 该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。
 * 也就是：
 *
 * F(0) = 0，F(1) = 1
 * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
 * 给你 n ，请计算 F(n) 。
 *
 * 0 <= n <= 30
 */

public class P509_Fib {
    public static void main(String[] args) {
        P509_Solution solution = new P509_Solution();
        System.out.println(solution.fib(8));
        System.out.println(solution.fib1(8));
        System.out.println(solution.fib2(8));
    }
}

class P509_Solution {
    // 动态规划 时间复杂度O(n) 空间复杂度O(n)
    public int fib(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    // 动态规划 对上方法的代码优化
    // 滚动数组思想, 时间复杂度O(n) 空间复杂度O(1)
    public int fib1(int n) {
        if (n < 2) {
            return n;
        }
        int[] dp = new int[3];
        dp[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            dp[2] = dp[0] + dp[1];
            dp[0] = dp[1];
            dp[1] = dp[2];
        }
        return dp[2];
    }
    // 递归 消耗最大
    public int fib2(int n) {
        if (n < 2) {
            return n;
        }
        return fib2(n - 1) + fib2(n - 2);
    }
}
