package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-03 20:03
 */

/**
 * 258. 各位相加 add-digits
 * 给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。返回这个结果。
 * <p>
 * 0 <= num <= 2^31 - 1
 * <p>
 * 进阶：你可以不使用循环或者递归，在 O(1) 时间复杂度内解决这个问题吗？
 */
public class P258_AddDigits {
    public static void main(String[] args) {
        P258_Solution solution = new P258_Solution();
        System.out.println(solution.addDigits(38)); // 2
        System.out.println(solution.addDigits(0)); // 0
    }
}

// 要不是进阶中提到可以 O(1) 解决该问题, 我肯定会使用循环去解答该题
// 数学
class P258_Solution {
    public int addDigits1(int num) {
        if (num == 0) {
            return 0;
        }
        return num % 9 == 0 ? 9 : num % 9;
    }

    // 官方
    public int addDigits(int num) {
        return (num - 1) % 9 + 1;
    }
}