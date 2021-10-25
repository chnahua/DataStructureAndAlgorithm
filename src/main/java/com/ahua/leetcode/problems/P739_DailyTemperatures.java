package com.ahua.leetcode.problems;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author huajun
 * @create 2021-10-24 21:38
 */

/**
 * 请根据每日 气温 列表 temperatures ，请计算在每一天需要等几天才会有更高的温度。
 * 如果气温在这之后都不会升高，请在该位置用 0 来代替。
 * <p>
 * 1 <= temperatures.length <= 10^5
 * 30 <= temperatures[i] <= 100
 */
public class P739_DailyTemperatures {
    public static void main(String[] args) {
        int[] temperatures = new int[]{73, 74, 75, 71, 69, 72, 76, 73};
        P739_Solution solution = new P739_Solution();
        // [1, 1, 4, 2, 1, 1, 0, 0]
        System.out.println(Arrays.toString(solution.dailyTemperatures2(temperatures)));
    }
}

class P739_Solution {
    // (1)我的暴力解法 时间复杂度 O(n^2) 空间复杂度 O(1)
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

    // (2)官方答案的暴力解法 时间复杂度 O(n^m) 空间复杂度 O(1), m 是数组 next 的长度
    public int[] dailyTemperatures1(int[] temperatures) {
        int len = temperatures.length;
        int[] ans = new int[len];
        int[] next = new int[101];
        Arrays.fill(next, Integer.MAX_VALUE);
        // 从最后一个元素往前遍历
        for (int i = len - 1; i >= 0; i--) {
            int index = Integer.MAX_VALUE;
            // 每次遍历都要遍历完所有比该温度值更大的温度可能第一次出现的位置, 取其中的最小值为 index
            for (int j = temperatures[i] + 1; j < next.length; j++) {
                if (next[j] < index) {
                    index = next[j];
                }
            }
            // 当 index < Integer.MAX_VALUE 时, 说明有比 temperatures[i] 更大的温度值, 且此时距离 temperatures[i] 最近
            if (index < Integer.MAX_VALUE) {
                ans[i] = index - i;
            } else {
                ans[i] = 0;
            }
            // 更新 i 为此温度值为第一次出现的下标
            next[temperatures[i]] = i;
        }
        return ans;
    }

    // (3)官方答案的单调栈解法(最优解法) 时间复杂度 O(n) 空间复杂度 O(n)
    public int[] dailyTemperatures2(int[] temperatures) {
        int len = temperatures.length;
        int[] ans = new int[len];
        // stack 栈中保存的是当前尚未找到更大的温度值的该温度值的下标
        // stack 中栈底到栈顶的各个下标对应的温度是递减的
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < len; i++) {
            int temperature = temperatures[i];
            // 当当前温度大于了栈顶的温度, 就说明之前的小的温度找到了一个离它最近的比它大的温度
            while (!stack.isEmpty() && temperature > temperatures[stack.peek()]) {
                int prevIndex = stack.poll();
                ans[prevIndex] = i - prevIndex;
            }
            // 当前温度的下标进栈
            stack.push(i);
        }
        return ans;
    }
}