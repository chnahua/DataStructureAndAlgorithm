package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-04-11 23:39
 */

/**
 * 357. 统计各位数字都不同的数字个数 count-numbers-with-unique-digits
 * 给你一个整数 n ，统计并返回各位数字都不同的数字 x 的个数，其中 0 <= x < 10^n 。
 * 0 <= n <= 8
 */
public class P357_CountNumbersWithUniqueDigits {
    public static void main(String[] args) {
        P357_Solution solution = new P357_Solution();
        for (int i = 0; i <= 8; i++) {
            System.out.println(solution.countNumbersWithUniqueDigits(i));
        }
    }
}

// 数学题 基本操作
// 0 ms 100.00%
// 38.1 MB 43.76%
class P357_Solution {
    public int countNumbersWithUniqueDigits(int n) {
        if (n == 0) {
            return 1;
        }
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            ans += countNumbers(i);
        }
        return ans;
    }

    private int countNumbers(int n) {
        if (n == 1) {
            return 10;
        }
        return (8 * 9 + 9) * factorial(8, n - 2);
    }

    private int factorial(int num, int count) {
        int ans = 1;
        for (int i = 0; i < count; i++) {
            ans *= 8 - i;
        }
        return ans;
    }
}