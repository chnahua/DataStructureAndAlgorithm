package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-04-09 22:00
 */

/**
 * 780. 到达终点 reaching-points
 * 给定四个整数 sx , sy ，tx 和 ty，
 * 如果通过一系列的转换可以从起点 (sx, sy) 到达终点 (tx, ty)，则返回 true，否则返回 false。
 * <p>
 * 从点 (x, y) 可以转换到 (x, x+y)  或者 (x+y, y)。
 * <p>
 * 1 <= sx, sy, tx, ty <= 10^9
 */
public class P780_ReachingPoints {
    public static void main(String[] args) {
        P780_Solution solution = new P780_Solution();
        System.out.println(solution.reachingPoints(1, 1, 3, 5)); // true
        System.out.println(solution.reachingPoints(1, 1, 2, 2)); // false
        System.out.println(solution.reachingPoints(1, 1, 1, 1)); // true
    }
}

// 数学题 反向计算
// 0 ms 100.00%
// 38.1 MB 68.75%
class P780_Solution {
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        while (tx > sx && ty > sy && tx != ty) {
            if (tx > ty) {
                tx %= ty;
            } else {
                ty %= tx;
            }
        }
        if (tx == sx && ty == sy) {
            return true;
        }
        if (tx == sx) {
            return ty > sy && (ty - sy) % tx == 0;
        }
        if (ty == sy) {
            return tx > sx && (tx - sx) % ty == 0;
        }
        return false;
    }
}