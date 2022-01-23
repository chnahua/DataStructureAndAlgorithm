package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-22 22:14
 */

/**
 * 1332. 删除回文子序列 remove-palindromic-subsequences
 * 给你一个字符串 s，它仅由字母 'a' 和 'b' 组成。每一次删除操作都可以从 s 中删除一个回文 子序列。
 * <p>
 * 返回删除给定字符串中所有字符（字符串为空）的最小删除次数。
 * <p>
 * 「子序列」定义：如果一个字符串可以通过删除原字符串某些字符而不改变原字符顺序得到，那么这个字符串就是原字符串的一个子序列。
 * <p>
 * 「回文」定义：如果一个字符串向后和向前读是一致的，那么这个字符串就是一个回文。
 * <p>
 * 1 <= s.length <= 1000
 * s 仅包含字母 'a'  和 'b'
 */
public class P1332_RemovePalindromeSub {
    public static void main(String[] args) {
        P1332_Solution solution = new P1332_Solution();
        // 1 字符串本身就是回文序列，只需要删除一次
        System.out.println(solution.removePalindromeSub("ababa"));
        // 2 "abb" -> "bb" -> "" 先删除回文子序列 "a"，然后再删除 "bb"
        System.out.println(solution.removePalindromeSub("abb"));
        // 2 "baabb" -> "b" -> "" 先删除回文子序列 "baab"，然后再删除 "b"
        System.out.println(solution.removePalindromeSub("baabb"));
    }
}

// 直接判断, 有些脑筋急转弯了, 本以为很麻烦的
class P1332_Solution {
    // 官方写法
    public int removePalindromeSub1(String s) {
        int n = s.length();
        for (int i = 0; i < n / 2; i++) {
            // 如果该字符串本身不是回文串, 此时只需删除 2 次即可,
            // 比如可以先删除所有的 ‘a’, 再删除所有的 ‘b’, 删除次数为 2
            if (s.charAt(i) != s.charAt(n - i - 1)) {
                return 2;
            }
        }
        // 如果整个字符串是回文串, 此时只需删除 1 次即可，删除次数为 1
        return 1;
    }

    // 他人 (时间复杂度差不多O(N), 空间复杂度O(N))
    public int removePalindromeSub(String s) {
        int n = s.length();
        // 如果该字符串本身不是回文串, 此时只需删除 2 次即可,
        // 比如可以先删除所有的 ‘a’, 再删除所有的 ‘b’, 删除次数为 2
        // 如果整个字符串是回文串, 此时只需删除 1 次即可，删除次数为 1
        return new StringBuilder(s).reverse().toString().equals(s) ? 1 : 2;
    }
}