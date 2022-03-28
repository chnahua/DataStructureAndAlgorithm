package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-28 21:23
 */

/**
 * 693. 交替位二进制数 binary-number-with-alternating-bits
 * 给定一个正整数，检查它的二进制表示是否总是 0、1 交替出现：换句话说，就是二进制表示中相邻两位的数字永不相同。
 * 1 <= n <= 2^31 - 1
 */
public class P693_HasAlternatingBits {
    public static void main(String[] args) {
        P693_Solution solution = new P693_Solution();
        System.out.println(solution.hasAlternatingBits(5)); // true 101
        System.out.println(solution.hasAlternatingBits(7)); // false 111
        System.out.println(solution.hasAlternatingBits(11)); // false 1011
    }
}

// 位运算
class P693_Solution {
    // 1 ms 7.33%
    // 38.1 MB 50.00%
    public boolean hasAlternatingBits1(int n) {
        String s = Integer.toBinaryString(n);
        int len = s.length();
        char ch = s.charAt(0);
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) != ch) {
                return false;
            }
            ch = ch == '0' ? '1' : '0';
        }
        return true;
    }

    // 位运算
    // 0 ms 100.00%
    // 38.3 MB 29.60%
    public boolean hasAlternatingBits2(int n) {
        int flag = n & 1;
        while (n != 0) {
            if ((n & 1) != flag) {
                return false;
            }
            n >>= 1;
            flag = flag == 1 ? 0 : 1;
        }
        return true;
    }

    // 位运算 官方解法
    // 0 ms 100.00%
    // 38.1 MB 43.33%
    public boolean hasAlternatingBits(int n) {
        // 正确时 a 中全为 1
        int a = n ^ (n >> 1);
        // 判断 a 中是否全为 1
        return (a & (a + 1)) == 0;
    }
}