package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-27 21:03
 */

import java.util.Arrays;

/**
 * 553. 最优除法 optimal-division
 * 给定一组正整数，相邻的整数之间将会进行浮点除法操作。例如，[2,3,4] -> 2 / 3 / 4 。
 * <p>
 * 但是，你可以在任意位置添加任意数目的括号，来改变算数的优先级。
 * 你需要找出怎么添加括号，才能得到最大的结果，并且返回相应的字符串格式的表达式。
 * 你的表达式不应该含有冗余的括号。
 * <p>
 * 输入数组的长度在 [1, 10] 之间。
 * 数组中每个元素的大小都在 [2, 1000] 之间。
 * 每个测试用例只有一个最优除法解。
 */
public class P553_OptimalDivision {
    public static void main(String[] args) {
        int[] nums = new int[]{1000, 100, 10, 2};
        P553_Solution solution = new P553_Solution();
        System.out.println(solution.optimalDivision(nums)); // "1000/(100/10/2)"
    }
}

// 准备使用动态规划做, 但是不会加括号, 所以此代码未完成
class P553_Solution1 {
    public String optimalDivision1(int[] nums) {
        int n = nums.length;
        // 动态规划 dp[0]最大 dp[1]最小
        int[][][] dp = new int[2][n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[1][i], Integer.MAX_VALUE); // >= 1000 即可
            dp[0][i][i] = nums[i];
            dp[1][i][i] = nums[i];
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= n - i; j++) {
                int t = j + i - 1;
                for (int k = j; k < t; k++) {
                    System.out.print("i = " + i + ", j = " + j + ", k = " + k + " ");
                    dp[0][j][t] = Math.max(dp[0][j][t], dp[0][j][k] / dp[1][k + 1][t]);
                    dp[1][j][t] = Math.min(dp[1][j][t], dp[1][j][k] / dp[0][k + 1][t]);
                    System.out.println(Arrays.toString(dp[0][j]) + " " + Arrays.toString(dp[1][j]));
                }
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(dp[0][i]) + " " + Arrays.toString(dp[1][i]));
        }
        // 结果最大值
        System.out.println(dp[0][0][n - 1]);
        return "";
    }
}

// 官方答案 数学解题 这我是真的想不到啊
// 6 ms 78.05%
// 39.7 MB 16.26%
class P553_Solution {
    public String optimalDivision(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return String.valueOf(nums[0]);
        }
        if (n == 2) {
            return nums[0] + "/" + nums[1];
        }
        StringBuilder res = new StringBuilder();
        res.append(nums[0]);
        res.append("/(");
        res.append(nums[1]);
        for (int i = 2; i < n; i++) {
            res.append("/");
            res.append(nums[i]);
        }
        res.append(")");
        return res.toString();
    }
}