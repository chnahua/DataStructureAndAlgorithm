package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-29 21:12
 */

public class P72_MinDistance {
    public static void main(String[] args) {
        String word1 = "horse";
        String word2 = "ros";
        String word3 = "intention";
        String word4 = "execution";

        String word5 = "sea";
        String word6 = "ate";
        P72_Solution solution = new P72_Solution();
        System.out.println(solution.minDistance(word1, word2)); // 3
        System.out.println(solution.minDistance(word3, word4)); // 5
        System.out.println(solution.minDistance(word5, word6)); // 3
    }
}

class P72_Solution {
    // 二维动态规划
    // 时间复杂度 ：O(mn)，其中 m 为 word1 的长度，n 为 word2 的长度。
    // 空间复杂度 ：O(mn)，我们需要大小为 O(mn) 的 DP 数组来记录状态值。
    public int minDistance1(String word1, String word2) {
        if (word1 == null || word2 == null) {
            return 0;
        }
        int m = word1.length();
        int n = word2.length();

        // 有一个字符串为空串
        if (n * m == 0) {
            return n + m;
        }

        int[][] dp = new int[m + 1][n + 1];

        // 边界状态初始化
        // 空串转换为字符串的转换次数为字符串的长度
        // 初始化首列
        for (int i = 0; i < m + 1; i++) {
            dp[i][0] = i;
        }
        // 字符串转换为空串的转换次数为字符串的长度
        // 初始化首行
        for (int j = 0; j < n + 1; j++) {
            dp[0][j] = j;
        }
        // 计算所有 DP 值
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                // 三种情况的最小值
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // 这种情况下, 其实 dp[i - 1][j - 1] 就是三者中的最小值? 有些题解是这样说的
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1]);
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[m][n];
    }

    // 一维动态规划(滚动数组思想)
    // 时间复杂度 ：O(mn)，其中 m 为 word1 的长度，n 为 word2 的长度。
    // 空间复杂度 ：O(n)，我们需要大小为 O(n) 的 DP 数组来记录状态值。
    public int minDistance2(String word1, String word2) {
        if (word1 == null || word2 == null) {
            return 0;
        }
        int m = word1.length();
        int n = word2.length();

        // 有一个字符串为空串
        if (n * m == 0) {
            return n + m;
        }

        int[] dp = new int[n + 1];

        // 边界状态初始化, 初始化 dp 数组
        // 字符串转换为空串的转换次数为字符串的长度
        for (int j = 0; j < n + 1; j++) {
            dp[j] = j;
        }
        // 计算所有 DP 值
        for (int i = 1; i < m + 1; i++) {
            // 初始化保存 dp[0] 处的值为 pre
            int pre = i;
            for (int j = 1; j < n + 1; j++) {
                // pre 保存的是现在第 i 次循环中 dp[j - 1] 处的值
                // 而此时 dp[j - 1] 及其以后的值都是上次(第 i - 1 次)循环中的值
                int temp = pre;
                // 三种情况的最小值
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    pre = Math.min(Math.min(dp[j] + 1, pre + 1), dp[j - 1]);
                } else {
                    pre = Math.min(Math.min(dp[j] + 1, pre + 1), dp[j - 1] + 1);
                }
                // 此时的 pre 保存的就是当前次循环中 dp[j] 处的值, 但是现在还不能给 dp[j] 赋值
                // 因为下一次 j + 1 的循环还需要用到上次(第 i - 1 次)循环中 dp[j] 处的值
                // 但是在此处就要更新 dp[j - 1] 处的值了, 因为后续的循环用不到上次(第 i - 1 次)循环中 dp[j - 1] 处的值了
                dp[j - 1] = temp;
            }
            // 循环结束后, dp 的最后一个位置处的次数还没更新, 在此处更新
            dp[n] = pre;
        }
        return dp[n];
    }

    // 一维动态规划(滚动数组思想)
    // 时间复杂度 ：O(mn)，其中 m 为 word1 的长度，n 为 word2 的长度。
    // 空间复杂度 ：O(min(m,n))，我们需要大小为 O(min(m,n)) 的 DP 数组来记录状态值。
    public int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) {
            return 0;
        }
        int m = word1.length();
        int n = word2.length();

        // 有一个字符串为空串
        if (n * m == 0) {
            return n + m;
        }

        if (m >= n) {
            return getMinDistance(word1, word2);
        } else {
            return getMinDistance(word2, word1);
        }
    }

    private int getMinDistance(String longWord, String shortWord) {
        if (shortWord == null || longWord == null) {
            return 0;
        }
        int m = longWord.length();
        int n = shortWord.length();

        // 有一个字符串为空串
        if (n * m == 0) {
            return n + m;
        }

        int[] dp = new int[n + 1];

        // 边界状态初始化, 初始化 dp 数组
        // 字符串转换为空串的转换次数为字符串的长度
        for (int j = 0; j < n + 1; j++) {
            dp[j] = j;
        }
        // 计算所有 DP 值
        for (int i = 1; i < m + 1; i++) {
            // 初始化保存 dp[0] 处的值为 pre
            int pre = i;
            for (int j = 1; j < n + 1; j++) {
                // pre 保存的是现在第 i 次循环中 dp[j - 1] 处的值
                // 而此时 dp[j - 1] 及其以后的值都是上次(第 i - 1 次)循环中的值
                int temp = pre;
                // 三种情况的最小值
                if (longWord.charAt(i - 1) == shortWord.charAt(j - 1)) {
                    pre = Math.min(Math.min(dp[j] + 1, pre + 1), dp[j - 1]);
                } else {
                    pre = Math.min(Math.min(dp[j] + 1, pre + 1), dp[j - 1] + 1);
                }
                // 此时的 pre 保存的就是当前次循环中 dp[j] 处的值, 但是现在还不能给 dp[j] 赋值
                // 因为下一次 j + 1 的循环还需要用到上次(第 i - 1 次)循环中 dp[j] 处的值
                // 但是在此处就要更新 dp[j - 1] 处的值了, 因为后续的循环用不到上次(第 i - 1 次)循环中 dp[j - 1] 处的值了
                dp[j - 1] = temp;
            }
            // 循环结束后, dp 的最后一个位置处的次数还没更新, 在此处更新
            dp[n] = pre;
        }
        return dp[n];
    }
}