package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-05 23:14
 */

/**
 * 521. 最长特殊序列 Ⅰ longest-uncommon-subsequence-i
 * 给你两个字符串 a 和 b，请返回 这两个字符串中 最长的特殊序列  的长度。如果不存在，则返回 -1 。
 * <p>
 * 「最长特殊序列」 定义如下：该序列为 某字符串独有的最长子序列（即不能是其他字符串的子序列） 。
 * <p>
 * 字符串 s 的子序列是在从 s 中删除任意数量的字符后可以获得的字符串。
 * <p>
 * 例如，"abc" 是 "aebdc" 的子序列，因为删除 "aebdc" 中斜体加粗的字符ed可以得到 "abc" 。
 * "aebdc" 的子序列还包括 "aebdc" 、 "aeb" 和 "" (空字符串)。
 * <p>
 * 1 <= a.length, b.length <= 100
 * a 和 b 由小写英文字母组成
 */
public class P521_FindLUSlength_I {
    public static void main(String[] args) {
        P521_Solution solution = new P521_Solution();
        System.out.println(solution.findLUSlength("aba", "cdc")); // 3
        System.out.println(solution.findLUSlength("aaa", "bbb")); // 3
        System.out.println(solution.findLUSlength("aaa", "aaa")); // -1
    }
}

// 字符串 脑筋急转弯啊
class P521_Solution {
    public int findLUSlength(String a, String b) {
        int aLen = a.length();
        int bLen = b.length();
        if (aLen == bLen) {
            if (a.equals(b)) {
                return -1;
            } else {
                return aLen;
            }
        } else {
            return Math.max(aLen, bLen);
        }

        // 以上总结为官方解法
        // return !a.equals(b) ? Math.max(a.length(), b.length()) : -1;
    }
}