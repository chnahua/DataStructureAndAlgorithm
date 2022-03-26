package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-26 21:15
 */

import java.util.Deque;
import java.util.LinkedList;

/**
 * 682. 棒球比赛 baseball-game
 * 你现在是一场采用特殊赛制棒球比赛的记录员。这场比赛由若干回合组成，过去几回合的得分可能会影响以后几回合的得分。
 * <p>
 * 比赛开始时，记录是空白的。
 * 你会得到一个记录操作的字符串列表 ops，其中 ops[i] 是你需要记录的第 i 项操作，ops 遵循下述规则：
 * <p>
 * 整数 x - 表示本回合新获得分数 x
 * "+" - 表示本回合新获得的得分是前两次得分的总和。题目数据保证记录此操作时前面总是存在两个有效的分数。
 * "D" - 表示本回合新获得的得分是前一次得分的两倍。题目数据保证记录此操作时前面总是存在一个有效的分数。
 * "C" - 表示前一次得分无效，将其从记录中移除。题目数据保证记录此操作时前面总是存在一个有效的分数。
 * 请你返回记录中所有得分的总和。
 * <p>
 * 1 <= ops.length <= 1000
 * ops[i] 为 "C"、"D"、"+"，或者一个表示整数的字符串。整数范围是 [-3 * 10^4, 3 * 10^4]
 * 对于 "+" 操作，题目数据保证记录此操作时前面总是存在两个有效的分数
 * 对于 "C" 和 "D" 操作，题目数据保证记录此操作时前面总是存在一个有效的分数
 */
public class P682_CalPoints {
    public static void main(String[] args) {
        String[] ops = new String[]{"5", "2", "C", "D", "+"};
        String[] ops1 = new String[]{"5", "-2", "4", "C", "D", "9", "+", "+"};
        String[] ops2 = new String[]{"1"};
        P682_Solution solution = new P682_Solution();
        System.out.println(solution.calPoints(ops)); // 30
        System.out.println(solution.calPoints(ops1)); // 27
        System.out.println(solution.calPoints(ops2)); // 1
    }
}

// 栈的基本操作
class P682_Solution {
    // 先进栈, 最后出栈, 计算栈中元素的总和
    // 2 ms 88.66%
    // 40.9MB 6.31%
    public int calPoints1(String[] ops) {
        Deque<Integer> stack = new LinkedList<>();
        // 入栈
        for (String op : ops) {
            if ("C".equals(op)) {
                stack.removeLast();
            } else if ("D".equals(op)) {
                // 入栈 栈顶元素(原栈第一个元素) 的 两倍
                stack.add(stack.peekLast() * 2);
            } else if ("+".equals(op)) {
                // 栈顶元素出栈(原栈第一个元素)
                int firstPrev = stack.removeLast();
                // 查看栈顶元素(原栈第二个元素)
                Integer secondPrev = stack.peekLast();
                // 入栈(原栈第一个元素)
                stack.add(firstPrev);
                // 入栈新元素元素 firstPrev + secondPrev
                stack.add(firstPrev + secondPrev);
            } else {
                stack.add(Integer.valueOf(op));
            }
        }
        // 出栈计算
        int ans = 0;
        for (Integer num : stack) {
            ans += num;
        }
        return ans;
    }

    // 与上解法效率上相差无几
    // 边进栈, 边计算, 遍历结束, 得到答案, 无需再出栈计算
    // 2 ms 88.66%
    // 40.5 MB 14.88%
    public int calPoints(String[] ops) {
        Deque<Integer> stack = new LinkedList<>();
        int ans = 0;
        // 入栈
        for (String op : ops) {
            if ("C".equals(op)) {
                int value = stack.removeLast();
                ans -= value;
            } else {
                int value;
                if ("D".equals(op)) {
                    // 入栈 栈顶元素(原栈第一个元素) 的 两倍
                    value = stack.peekLast() * 2;
                } else if ("+".equals(op)) {
                    // 栈顶元素出栈(原栈第一个元素)
                    int firstPrev = stack.removeLast();
                    // 查看栈顶元素(原栈第二个元素)
                    Integer secondPrev = stack.peekLast();
                    // 入栈(原栈第一个元素)
                    stack.add(firstPrev);
                    // 入栈新元素元素 firstPrev + secondPrev
                    value = firstPrev + secondPrev;
                } else {
                    value = Integer.parseInt(op);
                }
                stack.add(value);
                ans += value;
            }
        }
        return ans;
    }
}