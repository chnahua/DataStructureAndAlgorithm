package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-07 22:10
 */

/**
 * 504. 七进制数 base-7
 * 给定一个整数 num，将其转化为 7 进制，并以字符串形式输出。
 * <p>
 * -10^7 <= num <= 10^7
 */
public class P504_ConvertToBase7 {
    public static void main(String[] args) {
        P504_Solution solution = new P504_Solution();
        System.out.println(solution.convertToBase7(100)); // 202
        System.out.println(solution.convertToBase7(-7)); // -10
    }
}

// 数学题 倒推 + 迭代
// 1 ms 76.60%
// 38.9 MB 19.15%
class P504_Solution {
    public String convertToBase7(int num) {
        if (num == 0) {
            return "0";
        }
        boolean negative = num < 0;
        num = Math.abs(num);
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(num % 7);
            num /= 7;
        }
        if (negative) {
            sb.append('-');
        }
        return sb.reverse().toString();
    }
}