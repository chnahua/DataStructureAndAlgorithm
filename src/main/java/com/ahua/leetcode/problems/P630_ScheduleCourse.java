package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-14 3:49
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 630. 课程表 III
 * 这里有 n 门不同的在线课程，按从 1 到 n 编号。给你一个数组 courses ，
 * 其中 courses[i] = [durationi, lastDayi] 表示第 i 门课将会 持续 上 durationi 天课，
 * 并且必须在不晚于 lastDayi 的时候完成。
 * <p>
 * 你的学期从第 1 天开始。且不能同时修读两门及两门以上的课程。
 * <p>
 * 返回你最多可以修读的课程数目。
 * <p>
 * 1 <= courses.length <= 10^4
 * 1 <= durationi, lastDayi <= 10^4
 */
public class P630_ScheduleCourse {
    public static void main(String[] args) {
        int[][] courses = new int[][]{
                {100, 200},
                {200, 1300},
                {1000, 1250},
                {2000, 3200}
        };
        int[][] courses1 = new int[][]{{1, 2}};
        int[][] courses2 = new int[][]{{3, 2}, {4, 3}};
        P630_Solution solution = new P630_Solution();
        System.out.println(solution.scheduleCourse(courses)); // 3
        System.out.println(solution.scheduleCourse(courses1)); // 1
        System.out.println(solution.scheduleCourse(courses2)); // 0
    }
}

// 堆(优先队列) + 贪心算法
class P630_Solution {
    public int scheduleCourse(int[][] courses) {
        // 以截止时间 lastDay 排序
        // Arrays.sort(courses, (a, b) -> a[1] - b[1]);
        Arrays.sort(courses, Comparator.comparingInt(a -> a[1]));

        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        // 优先队列中所有课程的总时间
        int total = 0;
        // duration, lastDay;
        int ti, di;
        for (int[] course : courses) {
            ti = course[0];
            di = course[1];
            // 假设此时队列中已有 k 门课程, 这 k 门课程总学习时间为 total
            if (total + ti <= di) {
                // 如果要将课程 i 添加进队列, 那么这 k+1 门课程的总学习时间(total + ti)则不能超过课程 i 的截止时间
                // 如果满足此条件, 说明可以将课程 i 添加进队列
                // 更新这总学习时间 total = total + ti;
                total += ti;
                queue.offer(ti);
            } else if (!queue.isEmpty() && queue.peek() > ti) {
                // 如果要将课程 i 添加进队列, 而总学习时间(total + ti)超过了课程 i 的截止时间
                // 说明课程 i 不能这样直接添加进队列, 使得队列中有 k+1 门课程
                // 此时需要判断如果队列中(k 门课程中)需要耗时学习时间最多的课程(设为 x)的学习时间比课程 i 学习时间多
                // 那么我们可以贪心地将这两门课程交换, 也就是将这课程 x 从队列中取出, 将课程 i 添加队列, 形成新的 k 门课程组合
                // 此时这新的 k 门课程组合的总的学习时间(total - tx + ti) 肯定小于 d(i-1) , 也小于等于 di
                // 即此时的新的 k 门课程组合比之上一次的 k 门课程组合, 不仅总学习时间减少, 连截至时间也延长了, 也就更有可能学习更多的课程
                total -= queue.poll() - ti;
                queue.offer(ti);
            }
        }
        // 最后队列的大小, 即为最多可以修读的课程数目
        return queue.size();
    }
}