package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-22 22:40
 */

/**
 * 2038. 如果相邻两个颜色均相同则删除当前颜色 remove-colored-pieces-if-both-neighbors-are-the-same-color
 * 总共有 n 个颜色片段排成一列，每个颜色片段要么是 'A' 要么是 'B' 。
 * 给你一个长度为 n 的字符串 colors ，其中 colors[i] 表示第 i 个颜色片段的颜色。
 * <p>
 * Alice 和 Bob 在玩一个游戏，他们 轮流 从这个字符串中删除颜色。Alice 先手 。
 * <p>
 * 如果一个颜色片段为 'A' 且 相邻两个颜色 都是颜色 'A' ，那么 Alice 可以删除该颜色片段。Alice 不可以 删除任何颜色 'B' 片段。
 * 如果一个颜色片段为 'B' 且 相邻两个颜色 都是颜色 'B' ，那么 Bob 可以删除该颜色片段。Bob 不可以 删除任何颜色 'A' 片段。
 * Alice 和 Bob 不能 从字符串两端删除颜色片段。
 * 如果其中一人无法继续操作，则该玩家 输 掉游戏且另一玩家 获胜 。
 * 假设 Alice 和 Bob 都采用最优策略，如果 Alice 获胜，请返回 true，否则 Bob 获胜，返回 false。
 * <p>
 * 1 <= colors.length <= 10^5
 * colors 只包含字母 'A' 和 'B'
 */
public class P2038_WinnerOfGame {
    public static void main(String[] args) {
        P2038_Solution solution = new P2038_Solution();
        System.out.println(solution.winnerOfGame("AAABABB")); // true
        System.out.println(solution.winnerOfGame("AA")); // false
        System.out.println(solution.winnerOfGame("ABBBBBBBAAA")); // false
    }
}

class P2038_Solution {
    // 14 ms 32.08%
    // 42.1 MB 50.95%
    public boolean winnerOfGame1(String colors) {
        int n = colors.length();
        if (n <= 2) {
            return false;
        }
        int removeA = 0;
        int removeB = 0;
        for (int i = 1; i <= n - 2; i++) {
            char ch = colors.charAt(i);
            if (ch == colors.charAt(i - 1) && ch == colors.charAt(i + 1)) {
                if (ch == 'A') {
                    removeA++;
                } else {
                    removeB++;
                }
            }
        }
        return removeA > removeB;
    }

    // 解题思路无本质区别
    // 8 ms 84.91%
    // 42.2 MB 41.51%
    public boolean winnerOfGame(String colors) {
        int n = colors.length();
        if (n <= 2) {
            return false;
        }
        int removeA = 0;
        int removeB = 0;
        char left = colors.charAt(0);
        char mid = colors.charAt(1);
        char right;
        for (int i = 2; i < n; i++) {
            right = colors.charAt(i);
            if (left == mid && mid == right) {
                if (mid == 'A') {
                    removeA++;
                } else {
                    removeB++;
                }
            }
            left = mid;
            mid = right;
        }
        return removeA > removeB;
    }
}