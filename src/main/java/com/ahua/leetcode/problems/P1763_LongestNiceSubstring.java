package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-01 22:31
 */

/**
 * 1763. 最长的美好子字符串 longest-nice-substring
 * 当一个字符串 s 包含的每一种字母的大写和小写形式 同时 出现在 s 中，就称这个字符串 s 是 美好 字符串。
 * 比方说，"abABB" 是美好字符串，因为 'A' 和 'a' 同时出现了，且 'B' 和 'b' 也同时出现了。
 * 然而，"abA" 不是美好字符串因为 'b' 出现了，而 'B' 没有出现。
 * <p>
 * 给你一个字符串 s ，请你返回 s 最长的 美好子字符串 。
 * 如果有多个答案，请你返回 最早 出现的一个。如果不存在美好子字符串，请你返回一个空字符串。
 * <p>
 * 1 <= s.length <= 100
 * s 只包含大写和小写英文字母。
 */
public class P1763_LongestNiceSubstring {
    public static void main(String[] args) {
        P1763_Solution solution = new P1763_Solution();
        System.out.println(solution.longestNiceSubstring("YazaAay")); // "aAa"
        System.out.println(solution.longestNiceSubstring("Bb")); // "Bb"
        System.out.println(solution.longestNiceSubstring("c")); // ""
        System.out.println(solution.longestNiceSubstring("dDzeE")); // "dD", 而不是 "eE"
    }

}

// 枚举 O(N^2) O(1)
// 4 ms
class P1763_Solution1 {
    public String longestNiceSubstring(String s) {
        int n = s.length();
        int maxPos = 0;
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            // lower 和 upper 的每一位表示是否有小写字母和大写字母
            int lower = 0;
            int upper = 0;
            for (int j = i; j < n; j++) {
                char ch = s.charAt(j);
                if (Character.isLowerCase(ch)) {
                    lower |= 1 << (ch - 'a');
                } else {
                    upper |= 1 << (ch - 'A');
                }
                // 相等时表示同一字母的小写字母和大写字母同时存在
                if (lower == upper && j - i + 1 > maxLen) {
                    maxPos = i;
                    maxLen = j - i + 1;
                }
            }
        }
        return s.substring(maxPos, maxPos + maxLen);
    }
}

// 分治
// 0 ms
class P1763_Solution {
    private int maxPos;
    private int maxLen;

    public String longestNiceSubstring(String s) {
        this.maxPos = 0;
        this.maxLen = 0;
        dfs(s, 0, s.length() - 1);
        return s.substring(maxPos, maxPos + maxLen);
    }

    private void dfs(String s, int start, int end) {
        if (start >= end) {
            return;
        }
        // lower 和 upper 的每一位表示是否有小写字母和大写字母
        int lower = 0;
        int upper = 0;
        for (int i = start; i <= end; i++) {
            char ch = s.charAt(i);
            if (Character.isLowerCase(ch)) {
                lower |= 1 << (ch - 'a');
            } else {
                upper |= 1 << (ch - 'A');
            }
        }
        // 相等时表示同一字母的小写字母和大写字母同时存在
        if (lower == upper) {
            if (end - start + 1 > maxLen) {
                maxPos = start;
                maxLen = end - start + 1;
            }
            return;
        }
        // valid 表示大小写字母均存在的字母集合
        int valid = lower & upper;
        int pos = start;
        while (pos <= end) {
            start = pos;
            // pos 位置处的这个字母, 它的大小写字母在该子串中均存在
            while (pos <= end && (valid & (1 << Character.toLowerCase(s.charAt(pos)) - 'a')) != 0) {
                pos++;
            }
            // 退出 while 时 pos 处的这个字母大小写只出现其中之一
            // 遍历这个小子串, 是否为美好子串
            dfs(s, start, pos - 1);
            pos++;
        }
//        // 使用 for 循环
//        for (int pos = start; pos <= end; pos++) {
//            start = pos;
//            // pos 位置处的这个字母, 它的大小写字母在该子串中均存在
//            while (pos <= end && (valid & (1 << Character.toLowerCase(s.charAt(pos)) - 'a')) != 0) {
//                pos++;
//            }
//            // 退出 while 时 pos 处的这个字母大小写只出现其中之一
//            // 遍历这个小子串, 是否为美好子串
//            dfs(s, start, pos - 1);
//        }
    }
}

// 滑动窗口
