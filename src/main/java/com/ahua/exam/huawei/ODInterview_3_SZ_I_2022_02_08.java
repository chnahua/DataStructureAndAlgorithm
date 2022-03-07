package com.ahua.exam.huawei;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author huajun
 * @create 2022-02-08 14:13
 */

/*
LeetCode P739_DailyTemperatures

请根据每日 气温 列表 temperatures ，请计算在每一天需要等几天才会有更高的温度。
如果气温在这之后都不会升高，请在该位置用 0 来代替。

示例 1:
输入: temperatures = [73,74,75,71,69,72,76,73]
输出: [1,1,4,2,1,1,0,0]

示例 2:
输入: temperatures = [30,40,50,60]
输出: [1,1,1,0]

示例 3:
输入: temperatures = [30,60,90]
输出: [1,1,0]

 */
public class ODInterview_3_SZ_I_2022_02_08 {
    public static void main(String[] args) {
        int[] temperatures = new int[]{73, 74, 75, 71, 69, 72, 76, 73};
        int[] temperatures1 = new int[]{30, 40, 50, 60};
        int[] temperatures2 = new int[]{30, 60, 90};
        int[] temperatures3 = new int[]{75, 71, 69, 72};
        ODInterview_3_SZ_I_2022_02_08_Solution solution = new ODInterview_3_SZ_I_2022_02_08_Solution();
        System.out.println(Arrays.toString(solution.dailyTemperatures(temperatures)));
        System.out.println(Arrays.toString(solution.dailyTemperatures(temperatures1)));
        System.out.println(Arrays.toString(solution.dailyTemperatures(temperatures2)));
        System.out.println(Arrays.toString(solution.dailyTemperatures(temperatures3)));
    }
}

// 面试时写的代码, 由于这道题之前做过, 此处就不再重写了
class ODInterview_3_SZ_I_2022_02_08_Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] ans = new int[n];
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                int index = stack.remove();
                // 天数
                ans[index] = i - index;
            }
            stack.offerFirst(i);
        }
        while (!stack.isEmpty()) {
            int index = stack.remove();
            ans[index] = 0;
        }
        return ans;
    }
}