package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-10 20:06
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 1447. 最简分数 simplified-fractions
 * 给你一个整数 n ，请你返回所有 0 到 1 之间（不包括 0 和 1）满足分母小于等于  n 的 最简 分数 。
 * 分数可以以 任意 顺序返回。
 * 1 <= n <= 100
 * <p>
 * denominator 分母
 * numerator 分子
 */
public class P1447_SimplifiedFractions {
    public static void main(String[] args) {
        P1447_Solution solution = new P1447_Solution();
        System.out.println(solution.simplifiedFractions(2)); // ["1/2"]
        System.out.println(solution.simplifiedFractions(3)); // ["1/2","1/3","2/3"]
        System.out.println(solution.simplifiedFractions(4)); // ["1/2","1/3","1/4","2/3","3/4"]
        System.out.println(solution.simplifiedFractions(1)); // []
    }
}

// 数学题 官方解 20 ms 55.32%
class P1447_Solution {
    public List<String> simplifiedFractions(int n) {
        List<String> ans = new ArrayList<>();
        for (int denominator = 2; denominator <= n; denominator++) {
            for (int numerator = 1; numerator < denominator; numerator++) {
                if (gcd(numerator, denominator) == 1) {
                    ans.add(numerator + "/" + denominator);
                }
            }
        }
        return ans;
    }

    // 欧几里得算法 求分子分母的最大公约数
    // 没看懂怎么求出来的
    public int gcd(int a, int b) {
        return b != 0 ? gcd(b, a % b) : a;
    }
}