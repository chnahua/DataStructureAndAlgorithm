package com.ahua.leetcode.problems;

import java.util.Arrays;

/**
 * @author huajun
 * @create 2021-12-27 16:07
 */

/**
 * 825. 适龄的朋友
 * 在社交媒体网站上有 n 个用户。给你一个整数数组 ages ，其中 ages[i] 是第 i 个用户的年龄。
 * <p>
 * 如果下述任意一个条件为真，那么用户 x 将不会向用户 y（x != y）发送好友请求：
 * <p>
 * age[y] <= 0.5 * age[x] + 7
 * age[y] > age[x]
 * age[y] > 100 && age[x] < 100
 * 否则，x 将会向 y 发送一条好友请求。
 * <p>
 * 注意，如果 x 向 y 发送一条好友请求，y 不必也向 x 发送一条好友请求。另外，用户不会向自己发送好友请求。
 * 返回在该社交媒体网站上产生的好友请求总数。
 * <p>
 * n == ages.length
 * 1 <= n <= 2 * 10^4
 * 1 <= ages[i] <= 120
 */

public class P825_NumFriendRequests {
    public static void main(String[] args) {
        int[] ages = new int[]{16, 16};
        int[] ages1 = new int[]{16, 17, 18};
        int[] ages2 = new int[]{20, 30, 100, 110, 120};
        P825_Solution solution = new P825_Solution();
        System.out.println(solution.numFriendRequests(ages)); // 2
        System.out.println(solution.numFriendRequests(ages1)); // 2
        System.out.println(solution.numFriendRequests(ages2)); // 3
    }
}

// 我的暴力解法, 效率极低 O(n^2)
class P825_Solution1 {
    public int numFriendRequests(int[] ages) {
        Arrays.sort(ages);
        double condition;
        int numRequests = 0;
        // 0.5 × ages[x] + 7 < ages[y] ≤ ages[x]
        for (int i = ages.length - 1; i >= 0; i--) {
            if (ages[i] <= 14) {
                break;
            }
            condition = ages[i] * 0.5 + 7;
            for (int j = i - 1; j >= 0; j--) {
                // 由于已经排序了, 三个条件中的第二三两个条件不必考虑了
                // 如果 ages[j] <= condition, i 不能向 j 发
                if (ages[j] <= condition) {
                    // 起初是 continue; 会超时
                    // continue;
                    // 根据题意, 改为 break; 就不超时了, 但是效率很低下
                    break;
                }
                numRequests++;
                // 如果 ages[i] == ages[j], j 也可以向 i 发
                if (ages[i] == ages[j]) {
                    numRequests++;
                }
            }
        }
        return numRequests;
    }
}

// 官方解法一
// 排序 + 双指针
// 时间复杂度: O(nlogn) 排序需要的时间为 O(nlogn)，遍历所有的 ages[x] 以及使用双指针维护答案区间的时间复杂度为 O(n)
// 空间复杂度: O(logn)  即为排序需要使用的栈空间
class P825_Solution2 {
    public int numFriendRequests(int[] ages) {
        int n = ages.length;
        Arrays.sort(ages);
        double condition;
        int left = 0, right = 0;
        int numRequest = 0;
        // 0.5 × ages[x] + 7 < ages[y] ≤ ages[x]
        for (int age : ages) {
            if (age <= 14) {
                continue;
            }
            condition = age * 0.5 + 7;
            while (ages[left] <= condition) {
                left++;
            }
            while (right + 1 < n && ages[right + 1] <= age) {
                right++;
            }
            numRequest += right - left;
        }
        return numRequest;
    }
}

// 官方解法二
// 计数排序 + 前缀和
// 时间复杂度: O(n+C) 其中 C 是用户年龄的范围，本题中 C = 120。计数排序需要 O(n) 的时间，
// 计算前缀和以及统计答案需要 O(C) 的时间
// 空间复杂度: O(C) 即为计数排序以及前缀和数组需要使用的空间
class P825_Solution {
    public int numFriendRequests(int[] ages) {
        // 题目条件范围 1 <= ages[i] <= 120
        // 各个年龄的人数
        int[] cnt = new int[121];
        for (int age : ages) {
            cnt[age]++;
        }
        // 年龄小于等于 i 的总人数(前缀和)
        int[] pre = new int[121];
        for (int i = 1; i <= 120; i++) {
            pre[i] = pre[i - 1] + cnt[i];
        }

        int numRequest = 0;
        for (int age = 15; age <= 120; age++) {
            if (cnt[age] > 0) {
                // 0.5 × ages[x] + 7 < ages[y] ≤ ages[x]
                // 某个年龄 age 的用户能够向年龄满足这个不等式的用户发送好友请求
                numRequest += cnt[age] * (pre[age] - pre[(int) (age * 0.5 + 7)] - 1);
            }
        }
        return numRequest;
    }
}

