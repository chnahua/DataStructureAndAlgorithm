package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-10 23:24
 */

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 731. 我的日程安排表 II my-calendar-ii
 * 实现一个 MyCalendar 类来存放你的日程安排。如果要添加的时间内不会导致三重预订时，则可以存储这个新的日程安排。
 * <p>
 * MyCalendar 有一个 book(int start, int end)方法。它意味着在 start 到 end 时间内增加一个日程安排，
 * 注意，这里的时间是半开区间，即 [start, end), 实数 x 的范围为， start <= x < end。
 * <p>
 * 当三个日程安排有一些时间上的交叉时（例如三个日程安排都在同一时间内），就会产生三重预订。
 * <p>
 * 每次调用 MyCalendar.book方法时，如果可以将日程安排成功添加到日历中而不会导致三重预订，返回 true。
 * 否则，返回 false 并且不要将该日程安排添加到日历中。
 * <p>
 * 请按照以下步骤调用MyCalendar 类: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
 * <p>
 * 每个测试用例，调用 MyCalendar.book 函数最多不超过 1000次。
 * 调用函数 MyCalendar.book(start, end)时， start 和 end 的取值范围为 [0, 10^9]。
 */
public class P731_MyCalendarTwo {
    public static void main(String[] args) {

    }
}

// 暴力解法
class MyCalendarTwo1 {
    // 一重预定列表 保存所有(不会引起三重预定冲突的)日程安排
    List<int[]> calendar;
    // 二重预定列表 将当前添加到 calendar 中的重合时间段单独保存
    List<int[]> overlaps;

    public MyCalendarTwo1() {
        calendar = new ArrayList<>();
        overlaps = new ArrayList<>();
    }

    public boolean book(int start, int end) {
        // 将 start, end 与 重合的时间段 比较, 如果再次重合, 说明发生了三重预定冲突, 返回 false, 该日程不加入
        for (int[] plan : overlaps) {
            if (start < plan[1] && end > plan[0]) {
                return false;
            }
        }
        // 上一个循环中确定了该日程添加进 calendar 后不会产生三重预定冲突, 但是有可能产生二重预定冲突
        // 如果产生了, 需要将冲突片段(重合片段)加入到 overlaps 中, 因此需要遍历一次 calendar
        for (int[] plan : calendar) {
            if (start < plan[1] && end > plan[0]) {
                overlaps.add(new int[]{Math.max(start, plan[0]), Math.min(end, plan[1])});
            }
        }
        // 最后添加该日程到 calendar 中
        calendar.add(new int[]{start, end});
        return true;
    }
}

// 边界计数(效率不及暴力解法)
class MyCalendarTwo {
    TreeMap<Integer, Integer> delta;

    public MyCalendarTwo() {
        delta = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        // 当前欲添加的以 start 为开始时间的日程是否已存在 calendar 中, 返回以 start 为开始时间的日程的个数 startVal
        int startVal = delta.getOrDefault(start, 0);
        // 如果 startVal 等于 2, 说明现在 calendar 中已经有了两个以 start 为开始时间的日程安排,
        // 现在这个欲添加的以 start 为开始时间的日程就不能再添加进去了, 否则肯定会造成三重预定冲突
        if (startVal == 2) {
            return false;
        }
        // 当前欲添加的以 end 为结束时间的日程是否已存在 calendar 中, 返回以 end 为结束时间的日程的个数
        int endVal = delta.getOrDefault(end, 0);
        // 如果 endVal 等于 -2, 说明现在 calendar 中已经有了两个以 end 为结束时间的日程安排,
        // 现在这个欲添加的以 end 为结束时间的日程就不能再添加进去了, 否则肯定会造成三重预定冲突
        if (endVal == -2) {
            return false;
        }

        // 1 <= startVal <= 2, -2 <= endVal <= -1
        // 将这两个点添加进 delta 中, 表示将该日程添加进 calendar 中
        delta.put(start, startVal + 1);
        delta.put(end, endVal - 1);

        // 依次累计从前往后的 各个开始时间结点和结束时间结点处的值
        int active = 0;
        for (int val : delta.values()) {
            active += val;
            // 当 active 的值大于等于 3 时, 说明刚才添加进 calendar 中的日程产生了三重预定冲突, 应当将其移除, 最后返回 false
            if (active >= 3) {
                // 对于 TreeMap, 直接 put 曾经未添加前的历史值即可
                delta.put(start, startVal);
                delta.put(end, endVal);
                // 添加这两个移除代码, 一个减少 34 ms, 两个总计 68 ms
                // 如果不添加该日程, 之前添加的所有日程中, 没有以 start 为开始时间的日程或以 end 为结束时间的日程,
                // 需要在 delta 中将其移除, 减少后续添加日程时的查找和排序次数?(应该是吧)
                if (startVal == 0) {
                    delta.remove(start);
                }
                if (endVal == 0) {
                    delta.remove(end);
                }
                return false;
            }
        }
        // 循环结束, 执行到此处, 说明新添加的日程并未引起三重预定冲突, 返回 true
        return true;
    }
}

/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */