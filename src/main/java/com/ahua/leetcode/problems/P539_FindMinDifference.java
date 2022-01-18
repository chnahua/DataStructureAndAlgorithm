package com.ahua.leetcode.problems;

import java.util.*;

/**
 * @author huajun
 * @create 2022-01-18 22:25
 */

/**
 * 539. 最小时间差 minimum-time-difference
 * 给定一个 24 小时制（小时:分钟 "HH:MM"）的时间列表，找出列表中任意两个时间的最小时间差并以分钟数表示。
 * <p>
 * 2 <= timePoints.length <= 2 * 10^4
 * timePoints[i] 格式为 "HH:MM"
 */
public class P539_FindMinDifference {
    public static void main(String[] args) {
        List<String> timePoints = Arrays.asList("23:59", "00:00");
        List<String> timePoints1 = Arrays.asList("00:00", "23:59", "00:00");
        List<String> timePoints2 = Arrays.asList("00:00", "04:00", "22:00");
        P539_Solution solution = new P539_Solution();
        System.out.println(solution.findMinDifference(timePoints)); // 1
        System.out.println(solution.findMinDifference(timePoints1)); // 0
        System.out.println(solution.findMinDifference(timePoints2)); // 120
    }
}

class P539_Solution1 {
    public int findMinDifference(List<String> timePoints) {
        TreeSet<Integer> timeSet = new TreeSet<>();
        int minTimeDiff = Integer.MAX_VALUE;
        for (String str : timePoints) {
            int time = Integer.parseInt(str.substring(0, 2)) * 60 + Integer.parseInt(str.substring(3, 5));
            if (timeSet.contains(time)) {
                // System.out.println("000");
                return 0;
            }
            Integer f = timeSet.floor(time);
            Integer c = timeSet.ceiling(time);
            if (f != null) {
                minTimeDiff = Math.min(minTimeDiff, Math.min(time - f, f + 1440 - time));
                // System.out.println("fffff" + Math.min(time - f, f + 1440 - time));
            }
            if (c != null) {
                minTimeDiff = Math.min(minTimeDiff, Math.min(c - time, time + 1440 - c));
                // System.out.println("ccccc" + Math.min(c - time, time + 1440 - c));
            }
            timeSet.add(time);
//            System.out.println(time);
//            System.out.println(f);
//            System.out.println(c);
//            System.out.println(minTimeDiff);
            // System.out.println(24 * 60);
        }
        return minTimeDiff;
    }
}

class P539_Solution {
    public int findMinDifference(List<String> timePoints) {
        int n = timePoints.size();
        int[] timeArr = new int[n];
        for (int i = 0; i < n; i++) {
            timeArr[i] = Integer.parseInt(timePoints.get(i).substring(0, 2)) * 60 + Integer.parseInt(timePoints.get(i).substring(3, 5));
        }
        Arrays.sort(timeArr);
        int minTimeDiff = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            int floorDiff = timeArr[i] - timeArr[i - 1];
            if (floorDiff == 0) {
                return 0;
            }
            minTimeDiff = Math.min(minTimeDiff, floorDiff);
        }
        minTimeDiff = Math.min(minTimeDiff, timeArr[0] + 24 * 60 - timeArr[n - 1]);
        return minTimeDiff;
    }
}