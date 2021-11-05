package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-05 18:55
 */


/**
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * <p>
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * <p>
 * 注意：给定 n 是一个正整数。
 */

public class P70_ClimbStairs {
    public static void main(String[] args) {
        P70_Solution solution = new P70_Solution();
        System.out.println(solution.climbStairs(2));
        System.out.println(solution.climbStairs(3));
    }
}

class P70_Solution {
    // 动态规划
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        }
        int[] dp = new int[]{1, 2, 0};
        for (int i = 3; i <= n; i++) {
            // 最初我还以为这个状态转移方程不对
            dp[2] = dp[0] + dp[1];
            dp[0] = dp[1];
            dp[1] = dp[2];
        }
        return dp[2];
    }
}

// 递归

// 记忆化递归

// 动态规划

// 矩阵快速幂

// 通项公式