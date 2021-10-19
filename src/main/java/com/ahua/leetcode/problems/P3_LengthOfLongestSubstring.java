package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-10-18 17:57
 */

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 */
public class P3_LengthOfLongestSubstring {
    public static void main(String[] args) {
        String s = "abcabcbb";
        String s1 = "";
        String s2 = "bbbbbbb";
        P3_Solution solution = new P3_Solution();
        System.out.println(solution.lengthOfLongestSubstring(s));
        System.out.println(solution.lengthOfLongestSubstring(s1));
        System.out.println(solution.lengthOfLongestSubstring(s2));
    }
}

class P3_Solution {
    /**
     * @param s 要查找的字符串
     * @return maxLen 字符串 s 中不含有重复字符的 最长子串 的长度
     */
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        } else if (s.length() == 1) {
            return 1;
        }
        StringBuilder longestSubstring = new StringBuilder();
        // 初始化 将字符串 s 中的第一个字符加入子串中
        longestSubstring.append(s.charAt(0));
        // 初始化 最大长度 为 1 或者 0 均可
        int maxLen = 1;
        // 字符串中待比较的字符下标
        int indexStr = 1;
        // 字符串中待比较的字符在子串中的下标 如果有,返回下标;如果没有,返回 -1
        int indexSub = 0;
        while (indexStr < s.length()) {
            // indexSub = longestSubstring.indexOf(s.charAt(indexStr) + ""); // 效率不及下者
            // indexSub = longestSubstring.indexOf(String.valueOf(s.charAt(indexStr))); // 效率不及下者
            indexSub = longestSubstring.toString().indexOf(s.charAt(indexStr));
            // 如果子串中没有找到该字符,将该字符加入到子串中,并且 indexStr++,比较下一个字符
            if (indexSub == -1) {
                longestSubstring.append(s.charAt(indexStr));
                indexStr++;
            } else {
                // 如果子串中找到了该字符,说明此时子串为当前的最大子串,maxLen 为前一个 maxLen 和 当前子串长度 两者中的较大值
                maxLen = Math.max(maxLen, longestSubstring.length());
                // 在子串中删除找到的字符以前的所有字符(包括这个字符)
                // 例如 s = abcdbe 中,当比较到第二个 b 时,子串为 abcd, 重复的字符为 b, 删除 ab, 即(包含) indexSub 之前的所有字符
                // 删除后为 cd
                longestSubstring.delete(0, indexSub + 1);
                // 再在末尾加上这个重复的字符 即此时子串为 cdb
                longestSubstring.append(s.charAt(indexStr));
                // 继续比较下一个
                indexStr++;
            }
        }
        return Math.max(maxLen, longestSubstring.length());
    }
}