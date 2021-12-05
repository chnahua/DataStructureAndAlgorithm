package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-04 19:45
 */

/**
 * 5. 最长回文子串
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * <p>
 * 1 <= s.length <= 1000
 * s 仅由数字和英文字母（大写和/或小写）组成
 */

public class P5_LongestPalindrome {
    public static void main(String[] args) {
        String s = "babad";
        String s1 = "cbbd";
        String s2 = "a";
        String s3 = "ac";
        P5_Solution solution = new P5_Solution();
        System.out.println(solution.longestPalindrome(s)); // "bab" "aba" 同样是符合题意的答案
        System.out.println(solution.longestPalindrome(s1)); // "bb"
        System.out.println(solution.longestPalindrome(s2)); // "a"
        System.out.println(solution.longestPalindrome(s3)); // "a"
    }
}

// 动态规划
// 此方法效率低, 在官方答案上更改而来
class P5_Solution1 {
    public String longestPalindrome(String s) {
        if (s == null) {
            return "";
        }
        int len = s.length();
        if (len == 0 || len == 1) {
            return s;
        }

        // 最长回文子串长度
        int maxLen = 1;
        // (第一条)最长回文子串的开始下标
        int begin = 0;

        // dp[i][j] 表示 s[i..j] 是否是回文串
        boolean[][] dp = new boolean[len][len];
        // 初始化
        for (int i = 0; i <= len - 2; i++) {
            // 所有长度为 1 的子串都是回文串
            dp[i][i] = true;
            // 所有长度为 2 的子串中两字符相同的子串为回文串
            // 不相同则不是回文子串, 为 false, (可省略) /* else { dp[i][j] = false; } */
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                maxLen = 2;
                begin = i;
            }
        }
        // 所有长度为 1 的子串都是回文串
        dp[len - 1][len - 1] = true;

        // 先枚举子串长度
        for (int subLen = 3; subLen <= len; subLen++) {
            // 枚举左边界，左边界的上限设置可以宽松一些, 但是这里设置得最小(临界值), 这样的话 右边界 j 就肯定不会越界
            for (int i = 0; i <= len - subLen; i++) {
                // 由 subLen 和 i 可以确定右边界，即 j - i + 1 = subLen 得
                int j = subLen + i - 1;

                // 如果这两字符相同, 则看内部子串是否为回文子串, 内部是则是, 内部不是则不是
                // 如果不相同, 则肯定不是回文子串, 为 false, (可省略) /* else { dp[i][j] = false; } */
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1];
                }
                // 只要 dp[i][j] == true 成立，就表示子串 s[i..j] 是回文，此时记录回文长度 subLen 和起始位置 i
                if (dp[i][j] && subLen > maxLen) {
                    maxLen = subLen;
                    begin = i;
                }
            }
        }
        // 测试输出
//        for (int i = 0; i < len; i++) {
//            System.out.println(Arrays.toString(dp[i]));
//        }
        return s.substring(begin, begin + maxLen);
    }
}

// 中心扩展算法
class P5_Solution {
    String s;
    int len;

    public String longestPalindrome(String s) {
        if (s == null) {
            return "";
        }
        this.s = s;
        this.len = s.length();
        if (len == 0 || len == 1) {
            return s;
        }

        // 最长回文子串的左右下标
        int start = 0, end = 0;
        // tempMaxLen 遍历过程中的最长回文子串长度
        int len1, len2, tempMaxLen;
        for (int i = 0; i < len; i++) {
            // 以 s.charAt(i) 为中心的奇数长度的回文子串的长度
            len1 = expandAroundCenter(i, i);
            // 以 s.charAt(i) 和 s.charAt(i + 1) 为中心的偶数长度的回文子串的长度
            len2 = expandAroundCenter(i, i + 1);
            // 此次循环中以上两种情况得到的最长回文子串的较大值
            tempMaxLen = Math.max(len1, len2);
            // 如果比历史的最长回文子串长, 更新
            if (tempMaxLen > end - start) {
                // 以上两种情况(回文子串长度为奇数或偶数)都能得到如下求左右下标的公式
                start = i - (tempMaxLen - 1) / 2;
                end = i + tempMaxLen / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    public int expandAroundCenter(int left, int right) {
        while (left >= 0 && right < len && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 返回此最长回文子串的长度
        return right - left - 1;
    }
}