package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-31 21:08
 */

/**
 * 1342. 将数字变成 0 的操作次数 number-of-steps-to-reduce-a-number-to-zero
 * 给你一个非负整数 num ，请你返回将它变成 0 所需要的步数。 如果当前数字是偶数，你需要把它除以 2 ；否则，减去 1 。
 * 0 <= num <= 10^6
 */
public class P1342_NumberOfSteps {
    public static void main(String[] args) {
        P1342_Solution solution = new P1342_Solution();
        System.out.println(solution.numberOfSteps(14)); // 6
        System.out.println(solution.numberOfSteps(8)); // 4
        System.out.println(solution.numberOfSteps(123)); // 12
    }
}

class P1342_Solution {
    // 使用余除运算
    public int numberOfSteps1(int num) {
        int ans = 0;
        while (num > 0) {
            if (num % 2 == 0) {
                num /= 2;
            } else {
                num--;
            }
            ans++;
        }
        return ans;
    }

    // 使用位运算
    public int numberOfSteps(int num) {
        int ans = 0;
        while (num > 0) {
            if ((num & 1) == 0) {
                num >>= 1;
            } else {
                num ^= 1;
            }
            ans++;
        }
        return ans;
    }
}