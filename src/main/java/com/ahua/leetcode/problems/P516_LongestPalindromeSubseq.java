package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-03 0:49
 */

/**
 * 516. 最长回文子序列
 * 给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
 * <p>
 * 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
 * <p>
 * 1 <= s.length <= 1000
 * s 仅由小写英文字母组成
 */
public class P516_LongestPalindromeSubseq {
    public static void main(String[] args) {
        String s = "bbbab";
        String s1 = "cbbd";
        String s2 = "abcdef";
        P516_Solution solution = new P516_Solution();
        System.out.println(solution.longestPalindromeSubseq(s)); // 4 bbbb
        System.out.println(solution.longestPalindromeSubseq(s1)); // 2 bb
        System.out.println(solution.longestPalindromeSubseq(s2)); // 1
    }
}

class P516_Solution {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        // 字符 i 到字符 j 之间的最长回文子序列的长度
        int[][] dp = new int[n][n];
        // 初始化 dp, 得到长度为 1 和 2 的回文子序列(长度为 2 的是连续的)
        for (int i = 0; i <= n - 2; i++) {
            // i 到 i, 为回文子序列, 长度为 1
            dp[i][i] = 1;
            // i 到 i+1,
            // 如果相等, 则是回文子序列, 长度为 2;
            // 如果不相等, 则不是回文子序列, 但最长的回文子序列长度为 1
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = 2;
            } else {
                dp[i][i + 1] = 1;
            }
        }
        dp[n - 1][n - 1] = 1;

        // 长度为 subLen(3 到 n) 的子串的最长回文子序列的长度
        for (int subLen = 3; subLen <= n; subLen++) {
            // 遍历每一个长度为 subLen 的子串(下标 i -> j), 计算其最长回文子序列的长度
            for (int i = 0; i <= n - subLen; i++) {
                int j = i + subLen - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    // 如果 i j 处的字符相等, 那么其最长回文子序列的长度为 (i+1 -> j-1) 之间的子串的最长回文子序列的长度 + 2
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    // 如果不相等, 那么其最长回文子序列的长度为
                    // (i -> j) 这个长度为 subLen 的子串中的两个长度为 subLen-1 的两个子串中的最长回文子序列的长度中的较大值
                    // 长度为 subLen-1 的两个子串下标为 (i -> j-1) 与 (i+1 -> j),
                    // 其对应的最长回文子序列的长度为 dp[i][j - 1] 与 dp[i + 1][j]
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                }
            }
        }

        return dp[0][n - 1];
    }
}