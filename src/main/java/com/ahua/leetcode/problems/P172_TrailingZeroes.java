package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-25 23:02
 */

/**
 * 172. 阶乘后的零 factorial-trailing-zeroes
 * 给定一个整数 n ，返回 n! 结果中尾随零的数量。
 * <p>
 * 提示 n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1
 * <p>
 * 0 <= n <= 10^4
 * 进阶：你可以设计并实现对数时间复杂度的算法来解决此问题吗？
 */
public class P172_TrailingZeroes {
    public static void main(String[] args) {
        P172_Solution solution = new P172_Solution();
        System.out.println(solution.trailingZeroes(3)); // 0 --- 3! = 6 ，不含尾随 0
        System.out.println(solution.trailingZeroes(5)); // 1 --- 5! = 120 ，有一个尾随 0
        System.out.println(solution.trailingZeroes(0)); // 0
    }
}

// 数学
class P172_Solution {
    // O(N) O (1)
    // 7 ms 14.66%
    // 38.7 MB 15.90%
    public int trailingZeroes1(int n) {
        int ans = 0;
        // 统计[1, n]中各个数包含 5 的个数, 其和即为 ans
        for (int i = 5; i <= n; i++) {
            int x = i;
            while (x % 5 == 0) {
                ans++;
                x /= 5;
            }
        }
        return ans;
    }

    // 神奇的数学
    // O(NlogN) O (1)
    // 0 ms 100.00%
    // 38.7 MB 9.44%
    public int trailingZeroes(int n) {
        int ans = 0;
        while (n != 0) {
            n /= 5;
            ans += n;
        }
        return ans;
    }
}