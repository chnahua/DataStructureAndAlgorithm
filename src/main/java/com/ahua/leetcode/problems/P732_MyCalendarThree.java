package com.ahua.leetcode.problems;

import java.util.TreeMap;

/**
 * @author huajun
 * @create 2022-01-12 23:10
 */

/**
 * 732. 我的日程安排表 III my-calendar-iii
 * 当 k 个日程安排有一些时间上的交叉时（例如 k 个日程安排都在同一时间内），就会产生 k 次预订。
 * <p>
 * 给你一些日程安排 [start, end) ，请你在每个日程安排添加后，返回一个整数 k ，表示所有先前日程安排会产生的最大 k 次预订。
 * <p>
 * 实现一个 MyCalendarThree 类来存放你的日程安排，你可以一直添加新的日程安排。
 * <p>
 * MyCalendarThree() 初始化对象。
 * int book(int start, int end) 返回一个整数 k ，表示日历中存在的 k 次预订的最大值。
 * <p>
 * 0 <= start < end <= 10^9
 * 每个测试用例，调用 book 函数最多不超过 400次
 */
public class P732_MyCalendarThree {
    public static void main(String[] args) {

    }
}

// 边界计数
// 没想到这么几步就做出来了? 但是这个官解的效率是真的低
// 今天不在状态, 感觉脑子笨笨的
// 摘抄的他人的思路描述
// 思路：一个日程，从 start 开始，end 结束，想象一条时间线，我们将所有日程的 start 和 end 标记到时间线上，
// 然后从起始开始遍历时间线，每遇到一个 start，我们将进行中的日程计数 加1，每遇到一个 end，将进行中的日程计数 减1.
// 这里会有个地方产生一个最大值，这个最大值就是同时进行的日程数，也就是所求的答案。
class MyCalendarThree {
    TreeMap<Integer, Integer> delta;

    public MyCalendarThree() {
        delta = new TreeMap<>();
    }

    public int book(int start, int end) {
        delta.put(start, delta.getOrDefault(start, 0) + 1);
        delta.put(end, delta.getOrDefault(end, 0) - 1);

        int active = 0, ans = 0;
        // |val| 为以某个值为 key 的开始时间或者结束时间的日程个数
        for (int val : delta.values()) {
            // active 为假设添加了这前几个较早的开始时间的日程出现的重复预订次数
            active += val;
            if (active > ans) {
                ans = active;
            }
        }
        return ans;
    }
}

/**
 * Your MyCalendarThree object will be instantiated and called as such:
 * MyCalendarThree obj = new MyCalendarThree();
 * int param_1 = obj.book(start,end);
 */