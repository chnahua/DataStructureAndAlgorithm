package com.ahua.leetcode.problems;

import java.util.Arrays;

/**
 * @author huajun
 * @create 2021-10-24 21:38
 */

public class P739_DailyTemperatures {
    public static void main(String[] args) {
        int[] temperatures = new int[]{73, 74, 75, 71, 69, 72, 76, 73};
        P739_Solution solution = new P739_Solution();
        System.out.println(Arrays.toString(solution.dailyTemperatures(temperatures)));
    }
}

class P739_Solution {
    // 暴力解法
    public int[] dailyTemperatures(int[] temperatures) {
        int len = temperatures.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i; j < len; j++) {
                // 找到一个比 temperatures[i] 大的值, 令 temperatures[i] 为距离
                if (temperatures[i] < temperatures[j]) {
                    temperatures[i] = j - i;
                    break;
                }
                // 当比较到最后一个值都比 temperatures[i] 值小, 用 0 代替
                if (j == len - 1) {
                    temperatures[i] = 0;
                }
            }
        }
        // 最后一个值最后肯定为 0
        temperatures[len - 1] = 0;
        return temperatures;
    }
}