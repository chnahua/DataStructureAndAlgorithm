package com.ahua.leetcode.second;

/**
 * @author huajun
 * @create 2022-01-03 23:11
 */

import java.util.Arrays;

/**
 * 1143. 最长公共子序列
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
 * <p>
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * <p>
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 * <p>
 * 1 <= text1.length, text2.length <= 1000
 * text1 和 text2 仅由小写英文字符组成。
 */
public class Second_P1143_LengthOfLongestCommonSubsequence {
    public static void main(String[] args) {
        String text1 = "abcde";
        String text2 = "ace";

        String text3 = "abc";
        String text4 = "abc";

        String text5 = "abc";
        String text6 = "def";

        String text7 = "bsbininm";
        String text8 = "jmjkbkjkv";

        Second_P1143_Solution solution = new Second_P1143_Solution();
        System.out.println(solution.longestCommonSubsequence(text1, text2)); // 3
        System.out.println(solution.longestCommonSubsequence(text3, text4)); // 3
        System.out.println(solution.longestCommonSubsequence(text5, text6)); // 0
        System.out.println(solution.longestCommonSubsequence(text7, text8)); // 1
    }
}

// 动态规划
class Second_P1143_Solution {
    public int longestCommonSubsequence1(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        // 可定义成dp[m+1][n+1], 减少初始化过程, 就如同第一次做的那样
        int[][] dp = new int[m][n];
        // 初始化
        if (text1.charAt(0) == text2.charAt(0)) {
            dp[0][0] = 1;
        }
        // 第一列
        for (int i = 1; i < m; i++) {
            if (text1.charAt(i) == text2.charAt(0) || dp[i - 1][0] == 1) {
                dp[i][0] = 1;
            }
        }
        // 第一行
        for (int j = 1; j < n; j++) {
            if (text1.charAt(0) == text2.charAt(j) || dp[0][j - 1] == 1) {
                dp[0][j] = 1;
            }
        }

        // 动态规划 其余行列
        for (int i = 1; i < m; i++) {
            char ch = text1.charAt(i);
            for (int j = 1; j < n; j++) {
                if (ch == text2.charAt(j)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    // 可通过使用滚动数组减小空间复杂度
    // 第一种实现
    // pre 为 dp[i][j-1]
    public int longestCommonSubsequence2(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[] dp = new int[n];
        // 初始化
        if (text1.charAt(0) == text2.charAt(0)) {
            dp[0] = 1;
        }
        // 第一行
        for (int j = 1; j < n; j++) {
            if (text1.charAt(0) == text2.charAt(j) || dp[j - 1] == 1) {
                dp[j] = 1;
            }
        }

        System.out.println(Arrays.toString(dp));
        // 动态规划 其余行列
        char ch;
        // pre 为 当前 dp[i][j-1]
        int pre, temp;
        for (int i = 1; i < m; i++) {
            ch = text1.charAt(i);
            if (ch == text2.charAt(0) || dp[0] == 1) {
                pre = 1;
            } else {
                pre = 0;
            }
            for (int j = 1; j < n; j++) {
                temp = pre;
                if (ch == text2.charAt(j)) {
                    pre = dp[j - 1] + 1;
                } else {
                    pre = Math.max(pre, dp[j]);
                }
                dp[j - 1] = temp;
            }
            dp[n - 1] = pre;
            System.out.println(Arrays.toString(dp));
        }

        return dp[n - 1];
    }

    // 可通过使用滚动数组减小空间复杂度
    // 第二种实现(细节上不同)
    // pre 为 dp[i-1][j-1]
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[] dp = new int[n];
        // 初始化
        if (text1.charAt(0) == text2.charAt(0)) {
            dp[0] = 1;
        }
        // 第一行
        for (int j = 1; j < n; j++) {
            if (text1.charAt(0) == text2.charAt(j) || dp[j - 1] == 1) {
                dp[j] = 1;
            }
        }

        System.out.println(Arrays.toString(dp));
        // 动态规划 其余行列
        char ch;
        int pre, temp;
        for (int i = 1; i < m; i++) {
            ch = text1.charAt(i);
            pre = dp[0];
            if (ch == text2.charAt(0)) {
                dp[0] = 1;
            }
            for (int j = 1; j < n; j++) {
                temp = dp[j];
                if (ch == text2.charAt(j)) {
                    dp[j] = pre + 1;
                } else {
                    dp[j] = Math.max(dp[j - 1], dp[j]);
                }
                pre = temp;
            }
            System.out.println(Arrays.toString(dp));
        }

        return dp[n - 1];
    }
}