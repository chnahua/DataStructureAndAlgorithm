package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-13 19:25
 */

import java.util.Arrays;

/**
 * 1189. “气球” 的最大数量 maximum-number-of-balloons
 * 给你一个字符串 text，你需要使用 text 中的字母来拼凑尽可能多的单词 "balloon"（气球）。
 * <p>
 * 字符串 text 中的每个字母最多只能被使用一次。请你返回最多可以拼凑出多少个单词 "balloon"。
 * <p>
 * 1 <= text.length <= 10^4
 * text 全部由小写英文字母组成
 */
public class P1189_MaxNumberOfBalloons {
    public static void main(String[] args) {
        String text = "nlaebolko";
        String text1 = "loonbalxballpoon";
        String text2 = "leetcode";
        P1189_Solution solution = new P1189_Solution();
        System.out.println(solution.maxNumberOfBalloons(text)); // 1
        System.out.println(solution.maxNumberOfBalloons(text1)); // 2
        System.out.println(solution.maxNumberOfBalloons(text2)); // 0
    }
}

// 基本操作
// 3 ms 69.44%
class P1189_Solution {
    public int maxNumberOfBalloons(String text) {
        int b = 0;
        int a = 0;
        int l = 0;
        int o = 0;
        int n = 0;
        for (int i = 0; i < text.length(); i++) {
            // balloon
            char ch = text.charAt(i);
            if (ch == 'b') {
                b++;
            } else if (ch == 'a') {
                a++;
            } else if (ch == 'l') {
                l++;
            } else if (ch == 'o') {
                o++;
            } else if (ch == 'n') {
                n++;
            }
        }
        return Math.min(Math.min(Math.min(b, a), n), Math.min(l / 2, o / 2));
    }
}