package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-28 17:56
 */

/**
 * 给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
 * <p>
 * 由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
 * <p>
 * 注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。
 * <p>
 * 0 <= x <= 231 - 1
 */
public class P69_MySqrt {
    public static void main(String[] args) {
        P69_Solution solution = new P69_Solution();
        System.out.println(solution.mySqrt(4)); // 2
        System.out.println(solution.mySqrt(8)); // 2
        System.out.println(solution.mySqrt(6)); // 2
        System.out.println(solution.mySqrt(2147395599)); // 46339
    }
}

class P69_Solution {
    // 当 x 很大时, 数据 int target == mid * mid 会溢出
    // 定义成 long target == (long)mid * mid 就不会了
    public int mySqrt1(int x) {
        int left = 0, right = x;
        int mid = 0;
        long target;
        while (left <= right) {
            mid = left + (right - left) / 2;
            target = (long) mid * mid;
            if (target == x) {
                return mid;
            } else if (target > x) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right < mid ? mid - 1 : mid;
    }

    // 刚开始以为使用 mid * mid 的方式不能比较, 于是想到使用 x / mid 与 mid 比较的方式
    public int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }
        int left = 0, right = x;
        int mid = 0;
        int y;
        while (left <= right) {
            mid = left + (right - left) / 2;
            y = x / mid;
            if (mid == y) {
                return mid;
            } else if (mid > y) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        // return right < mid ? mid - 1 : mid; // right == mid - 1, left == mid, 返回 mid - 1
        // return left > mid ? mid : mid - 1; //  left == mid + 1, right == mid, 返回 mid
        // 综上, 可直接返回 right
        return right;
    }
}


