package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-23 23:26
 */

/**
 * 434. 字符串中的单词数 number-of-segments-in-a-string
 * 统计字符串中的单词个数，这里的单词指的是连续的不是空格的字符。
 * <p>
 * 请注意，你可以假定字符串里不包括任何不可打印的字符。
 */
public class P434_CountSegments {
    public static void main(String[] args) {
        P434_Solution solution = new P434_Solution();
        System.out.println(solution.countSegments("Hello, my name is John")); // 5
        System.out.println(solution.countSegments(", , , ,        a, eaefa")); // 6
        System.out.println(solution.countSegments("    foo    bar")); // 2
        System.out.println(solution.countSegments("                ")); // 0
    }
}

class P434_Solution {
    // 1 ms 10.33%
    // 39.2 MB 21.69%
    public int countSegments1(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        // 匹配连续的一个或多个 0
        int num = s.split(" +").length;
        if (num > 0 && s.charAt(0) == ' ') {
            return num - 1;
        }
        return num;
    }

    // 0 ms 100.00%
    // 39.3 MB 14.76%
    public int countSegments(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        int n = s.length();
        int segmentCount = 0;
        if (s.charAt(0) != ' ') {
            segmentCount++;
        }
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) != ' ' && s.charAt(i - 1) == ' ') {
                segmentCount++;
            }
        }
        return segmentCount;
    }
}