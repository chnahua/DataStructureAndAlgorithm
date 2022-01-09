package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-10 0:22
 */

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 729. 我的日程安排表 I my-calendar-i
 * 实现一个 MyCalendar 类来存放你的日程安排。如果要添加的日程安排不会造成 重复预订 ，则可以存储这个新的日程安排。
 * <p>
 * 当两个日程安排有一些时间上的交叉时（例如两个日程安排都在同一时间内），就会产生 重复预订 。
 * <p>
 * 日程可以用一对整数 start 和 end 表示，这里的时间是半开区间，即 [start, end), 实数 x 的范围为， start <= x < end 。
 * <p>
 * 实现 MyCalendar 类：
 * <p>
 * MyCalendar() 初始化日历对象。
 * boolean book(int start, int end) 如果可以将日程安排成功添加到日历中而不会导致重复预订，返回 true 。
 * 否则，返回 false 并且不要将该日程安排添加到日历中。
 * <p>
 * 0 <= start < end <= 10^9
 * 每个测试用例，调用 book 方法的次数最多不超过 1000 次。
 */
public class P729_MyCalendar {
    public static void main(String[] args) {

    }
}

// 暴力解法
class MyCalendar1 {
    List<int[]> calendar;

    public MyCalendar1() {
        calendar = new ArrayList<>();
    }

    public boolean book(int start, int end) {
        for (int[] plan : calendar) {
            // 如果新加入日程的开始时间 start 小于已在日程中的安排的结束时间
            // 并且结束时间大于已在日程中的安排的开始时间
            // 则不能添加该日程安排
            if (start < plan[1] && end > plan[0]) {
                return false;
            }
        }
        calendar.add(new int[]{start, end});
        return true;
    }
}

// 平衡二叉树
class MyCalendar {
    TreeMap<Integer, Integer> calendar;

    public MyCalendar() {
        calendar = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        // 返回所有开始时间小于等于 start 的日程中, 开始时间最大的那个日程的开始时间
        Integer prev = calendar.floorKey(start);
        // 返回所有开始时间大于等于 start 的日程中, 开始时间最小的那个日程的开始时间
        Integer next = calendar.ceilingKey(start);
        // 添加第一个日程时 prev == null && next == null, 直接添加, 后续的情况中两者最多只能有一个是 null
        // prev == null 时, 就要要求 end <= next
        // next == null 时, 就要要求 calendar.get(prev) <= start
        // calendar.get(prev) <= start 时, 就必须保证 next == null || end <= next
        // end <= next 时, 就必须保证 prev == null || calendar.get(prev) <= start
        if ((prev == null || calendar.get(prev) <= start) && (next == null || end <= next)) {
            calendar.put(start, end);
            return true;
        }
        return false;
    }
}
/**
 * Your MyCalendar object will be instantiated and called as such:
 * MyCalendar obj = new MyCalendar();
 * boolean param_1 = obj.book(start,end);
 */