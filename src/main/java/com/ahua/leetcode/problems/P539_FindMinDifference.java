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

// 排序
class P539_Solution {
    // 4 ms 额外使用 O(N) 空间
    public int findMinDifference1(List<String> timePoints) {
        int n = timePoints.size();
        int[] timeArr = new int[n];
        // 将字符格式 "HH:MM" 的时间映射为整形保存
        for (int i = 0; i < n; i++) {
            timeArr[i] = Integer.parseInt(timePoints.get(i).substring(0, 2)) * 60 + Integer.parseInt(timePoints.get(i).substring(3, 5));
        }
        // 对所有时间排序
        Arrays.sort(timeArr);
        // 最小时间差
        int minTimeDiff = Integer.MAX_VALUE;
        // 当前时间与前一个时间的差值与历史最小时间差比较, 得到当前最小时间差
        for (int i = 1; i < n; i++) {
            int timeDiff = timeArr[i] - timeArr[i - 1];
            if (timeDiff == 0) {
                return 0;
            }
            minTimeDiff = Math.min(minTimeDiff, timeDiff);
        }
        // 对于最后的这个最大的时间, 它与第一个时间的差值可能比与前一个时间的差值更小
        minTimeDiff = Math.min(minTimeDiff, timeArr[0] + 24 * 60 - timeArr[n - 1]);
        return minTimeDiff;
    }

    // 本想着对上一个代码进行空间优化, 不用自己再创建一个整形时间集合
    // 虽然空间节省了一些, 但是由于是对字符串排序, 因此在时间消耗上比上者多
    // 9 ms
    public int findMinDifference2(List<String> timePoints) {
        // 对所有时间排序
        Collections.sort(timePoints);
        int n = timePoints.size();
        // 第一个时间
        final int firstTime = Integer.parseInt(timePoints.get(0).substring(0, 2)) * 60 + Integer.parseInt(timePoints.get(0).substring(3, 5));
        // preTime 保存前一个时间, 初始化为第一个时间
        int preTime = firstTime;
        int curTime, timeDiff;
        // 最小时间差
        int minTimeDiff = Integer.MAX_VALUE;
        // 当前时间与前一个时间的差值与历史最小时间差比较, 得到当前最小时间差
        for (int i = 1; i < n; i++) {
            curTime = Integer.parseInt(timePoints.get(i).substring(0, 2)) * 60 + Integer.parseInt(timePoints.get(i).substring(3, 5));
            timeDiff = curTime - preTime;
            if (timeDiff == 0) {
                return 0;
            }
            minTimeDiff = Math.min(minTimeDiff, timeDiff);
            preTime = curTime;
        }
        // 对于最后的这个最大的时间, 它与第一个时间的差值可能比与前一个时间的差值更小
        minTimeDiff = Math.min(minTimeDiff, firstTime + 24 * 60 - preTime);
        return minTimeDiff;
    }

    // 上面两个方法和代码实现和官方很相似, 结合官方的计算时间整形值的方式, 对上两个方法进行修改
    // 经过测试 1 和 2, 使用 getMinutes() 方法将字符格式 "HH:MM" 的时间转变为整形, 更快
    // 测试 1 :  2 ms 减小时间和空间
    public int findMinDifference1_1(List<String> timePoints) {
        int n = timePoints.size();
        int[] timeArr = new int[n];
        // 将字符格式 "HH:MM" 的时间映射为整形保存
        for (int i = 0; i < n; i++) {
            timeArr[i] = getMinutes(timePoints.get(i));
        }
        // 对所有时间排序
        Arrays.sort(timeArr);
        // 最小时间差
        int minTimeDiff = Integer.MAX_VALUE;
        // 当前时间与前一个时间的差值与历史最小时间差比较, 得到当前最小时间差
        for (int i = 1; i < n; i++) {
            int timeDiff = timeArr[i] - timeArr[i - 1];
            if (timeDiff == 0) {
                return 0;
            }
            minTimeDiff = Math.min(minTimeDiff, timeDiff);
        }
        // 对于最后的这个最大的时间, 它与第一个时间的差值可能比与前一个时间的差值更小
        minTimeDiff = Math.min(minTimeDiff, timeArr[0] + 24 * 60 - timeArr[n - 1]);
        return minTimeDiff;
    }

    // 测试 2 : 9 ms 只减小空间
    public int findMinDifference1_2(List<String> timePoints) {
        // 对所有时间排序
        Collections.sort(timePoints);
        int n = timePoints.size();
        // 第一个时间
        final int firstTime = getMinutes(timePoints.get(0));
        // preTime 保存前一个时间, 初始化为第一个时间
        int preTime = firstTime;
        int curTime, timeDiff;
        // 最小时间差
        int minTimeDiff = Integer.MAX_VALUE;
        // 当前时间与前一个时间的差值与历史最小时间差比较, 得到当前最小时间差
        for (int i = 1; i < n; i++) {
            curTime = getMinutes(timePoints.get(i));
            timeDiff = curTime - preTime;
            if (timeDiff == 0) {
                return 0;
            }
            minTimeDiff = Math.min(minTimeDiff, timeDiff);
            preTime = curTime;
        }
        // 对于最后的这个最大的时间, 它与第一个时间的差值可能比与前一个时间的差值更小
        minTimeDiff = Math.min(minTimeDiff, firstTime + 24 * 60 - preTime);
        return minTimeDiff;
    }

    // 鸽笼原理(我真是服了这个)
    // 1 : 2 ms -> 1 ms 37.6 MB
    // 理论上的时间复杂度 O(min(n,C)log min(n,C)) C=24×60=1440
    // 空间复杂度 O(min(n,C)), 注意, 不是 O(log min(n,C))
    public int findMinDifference3(List<String> timePoints) {
        int n = timePoints.size();
        // 一共有 24×60=1440 种不同的时间
        // 由鸽巢原理可知, 如果 timePoints 的长度超过 1440, 那么必然会有两个相同的时间, 此时可以直接返回 0
        if (n > 1440) {
            return 0;
        }
        int[] timeArr = new int[n];
        // 将字符格式 "HH:MM" 的时间映射为整形保存
        for (int i = 0; i < n; i++) {
            timeArr[i] = getMinutes(timePoints.get(i));
        }
        // 对所有时间排序
        Arrays.sort(timeArr);
        // 最小时间差
        int minTimeDiff = Integer.MAX_VALUE;
        // 当前时间与前一个时间的差值与历史最小时间差比较, 得到当前最小时间差
        for (int i = 1; i < n; i++) {
            // 相邻时间的时间差
            int timeDiff = timeArr[i] - timeArr[i - 1];
            if (timeDiff == 0) {
                return 0;
            }
            minTimeDiff = Math.min(minTimeDiff, timeDiff);
        }
        // 首尾时间的时间差
        // 对于最后的这个最大的时间, 它与第一个时间的差值可能比与前一个时间的差值更小
        minTimeDiff = Math.min(minTimeDiff, timeArr[0] + 24 * 60 - timeArr[n - 1]);
        return minTimeDiff;
    }

    // 2 : 9 ms -> 2 ms 37.4 MB
    // 理论上的时间复杂度 O(min(n,C)log min(n,C)) C=24×60=1440
    // 空间复杂度 这次是 O(log min(n,C))
    public int findMinDifference(List<String> timePoints) {
        int n = timePoints.size();
        // 一共有 24×60=1440 种不同的时间
        // 由鸽巢原理可知, 如果 timePoints 的长度超过 1440, 那么必然会有两个相同的时间, 此时可以直接返回 0
        if (n > 1440) {
            return 0;
        }
        // 对所有时间排序
        Collections.sort(timePoints);
        // 第一个时间
        final int firstTime = getMinutes(timePoints.get(0));
        // preTime 保存前一个时间, 初始化为第一个时间
        int preTime = firstTime;
        int curTime, timeDiff;
        // 最小时间差
        int minTimeDiff = Integer.MAX_VALUE;
        // 当前时间与前一个时间的差值与历史最小时间差比较, 得到当前最小时间差
        for (int i = 1; i < n; i++) {
            curTime = getMinutes(timePoints.get(i));
            // 相邻时间的时间差
            timeDiff = curTime - preTime;
            if (timeDiff == 0) {
                return 0;
            }
            minTimeDiff = Math.min(minTimeDiff, timeDiff);
            preTime = curTime;
        }
        // 首尾时间的时间差
        // 对于最后的这个最大的时间, 它与第一个时间的差值可能比与前一个时间的差值更小
        minTimeDiff = Math.min(minTimeDiff, firstTime + 24 * 60 - preTime);
        return minTimeDiff;
    }

    public int getMinutes(String t) {
        return ((t.charAt(0) - '0') * 10 + (t.charAt(1) - '0')) * 60 + (t.charAt(3) - '0') * 10 + (t.charAt(4) - '0');
    }
}