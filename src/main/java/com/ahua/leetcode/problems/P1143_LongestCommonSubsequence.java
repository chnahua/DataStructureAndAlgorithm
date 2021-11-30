package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-30 20:24
 */

/**
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
public class P1143_LongestCommonSubsequence {
    public static void main(String[] args) {
        String text1 = "abcde";
        String text2 = "ace";
        String text3 = "abc";
        String text4 = "abc";
        String text5 = "abc";
        String text6 = "def";
        String text7 = "bsbininm";
        String text8 = "jmjkbkjkv";
        P1143_Solution solution = new P1143_Solution();
        System.out.println(solution.longestCommonSubsequence(text1, text2)); // 3
        System.out.println(solution.longestCommonSubsequence(text3, text4)); // 3
        System.out.println(solution.longestCommonSubsequence(text5, text6)); // 0
        System.out.println(solution.longestCommonSubsequence(text7, text8)); // 1
    }
}

class P1143_Solution {
    // 二维动态规划
    public int longestCommonSubsequence1(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0;
        }
        int m = text1.length();
        int n = text2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                // 两字符串的最后一个字符一样
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // 为何 text1.charAt(i - 1) != text2.charAt(j - 1) 时
                    // dp[i][j] 为 (dp[i][j - 1], dp[i - 1][j]) 中的较大值?

                    // 分为两种情况

                    // 1.dp[i][j] 可以看作是以下两字符串的状态变换过来的
                    // (1) text1的前(i-1)个字符组成的字符串(记为str1{i-1}) 加上 第 i 个字符('text1.charAt(i-1)',记为 ci), 也就是text1的前 i 个字符组成的字符串
                    // (2) text2的前(j-1)个字符组成的字符串(记为str2{j-1})
                    // 而现在str2{j-1}末尾加上了这第 j 个字符('text2.charAt(j-1)',记为 cj), 这个字符与(ci)不相等, 加上后记为 str2{j}
                    // 由于多了 cj 这个字符, 那么这个最长公共子序列【可能】就变成包含这个 cj 字符(并且这个字符是最长公共子序列最后一个字符)的序列了
                    // 那么这个序列在 str1{i} 中就肯定不是以 ci 结尾的, 或者说是这个最长公共子序列是在 str1{i-1} 中找到的
                    // 也就是将【求 str1{i} 与 str2{j} 中的最长公共子序列的长度问题】变成了【求 str1{i-1} 与 str2{j} 中的最长公共子序列的长度问题】
                    // cj 可能是最长公共子序列的最后一个字符, ci 与 cj 不相等, str1 中存不存在 ci, 与 str2{j} 比较的结果都是一样的
                    // 故此时这种情况下的 dp[i][j] 其实就为 dp[i-1][j]
                    // 那如果这个最长公共子序列不包含 cj 这个尾字符呢?
                    // 那 dp[i][j] 其实就直接为 dp[i][j-1]

                    // 2.dp[i][j] 可以看作是以下两字符串的状态变换过来的
                    // (1) text1的前(i-1)个字符组成的字符串(记为str1{i-1})
                    // (2) text2的前(j-1)个字符组成的字符串(记为str2{j-1}) 加上 第 j 个字符('text2.charAt(j-1)',记为 cj), 也就是text2的前 j 个字符组成的字符串
                    // 而现在str1{i-1}末尾加上了这第 i 个字符('text1.charAt(i-1)',记为 ci), 这个字符与(cj)不相等, 加上后记为 str1{i}
                    // 由于多了 ci 这个字符, 那么这个最长公共子序列【可能】就变成包含这个 ci 字符(并且这个字符是最长公共子序列最后一个字符)的序列了
                    // 那么这个序列在 str2{j} 中就肯定不是以 cj 结尾的, 或者说是这个最长公共子序列是在 str2{j-1} 中找到的
                    // 也就是将【求 str1{i} 与 str2{j} 中的最长公共子序列的长度问题】变成了【求 str1{i} 与 str2{j-1} 中的最长公共子序列的长度问题】
                    // ci 可能是最长公共子序列的最后一个字符, cj 与 ci 不相等, str2 中存不存在 cj, 与 str1{i} 比较的结果都是一样的
                    // 故此时这种情况下的 dp[i][j] 其实就为 dp[i][j-1]
                    // 那如果这个最长公共子序列不包含 ci 这个尾字符呢?
                    // 那 dp[i][j] 其实就直接为 dp[i-1][j]

                    // 综上, 故最终得两者最大值为 dp[i][j]
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        return dp[m][n];
    }

    // 一维动态规划(滚动数组思想)
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0;
        }
        int m = text1.length();
        int n = text2.length();

        if (m >= n) {
            return lengthOfLongestCommonSubsequence(text1, text2);
        } else {
            return lengthOfLongestCommonSubsequence(text2, text1);
        }
    }

    private int lengthOfLongestCommonSubsequence(String longStr, String shortStr) {
        if (longStr == null || shortStr == null) {
            return 0;
        }
        int m = longStr.length();
        int n = shortStr.length();

        int[] dp = new int[n + 1];

        int pre, temp;
        for (int i = 1; i < m + 1; i++) {
            pre = dp[0];
            for (int j = 1; j < n + 1; j++) {
                temp = dp[j];
                // 两字符串的最后一个字符一样
                if (longStr.charAt(i - 1) == shortStr.charAt(j - 1)) {
                    dp[j] = pre + 1;
                } else {
                    dp[j] = Math.max(dp[j - 1], dp[j]);
                }
                pre = temp;
            }
        }

        return dp[n];
    }
}