package com.ahua.leetcode.problems;

import com.sun.jmx.snmp.internal.SnmpSecuritySubSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author huajun
 * @create 2022-03-06 23:18
 */

/**
 * 2100. 适合打劫银行的日子 find-good-days-to-rob-the-bank
 * 你和一群强盗准备打劫银行。
 * 给你一个下标从 0 开始的整数数组 security ，其中 security[i] 是第 i 天执勤警卫的数量。
 * 日子从 0 开始编号。同时给你一个整数 time 。
 * <p>
 * 如果第 i 天满足以下所有条件，我们称它为一个适合打劫银行的日子：
 * <p>
 * 第 i 天前和后都分别至少有 time 天。
 * 第 i 天前连续 time 天警卫数目都是非递增的。
 * 第 i 天后连续 time 天警卫数目都是非递减的。
 * 更正式的，第 i 天是一个合适打劫银行的日子当且仅当：
 * security[i - time] >= security[i - time + 1] >= ... >= security[i] <= ... <=
 * security[i + time - 1] <= security[i + time].
 * <p>
 * 请你返回一个数组，包含 所有 适合打劫银行的日子（下标从 0 开始）。返回的日子可以 任意 顺序排列。
 * <p>
 * 1 <= security.length <= 10^5
 * 0 <= security[i], time <= 10^5
 */
public class P2100_GoodDaysToRobBank {
    public static void main(String[] args) {
        int[] security = new int[]{5, 3, 3, 3, 5, 6, 2};
        int[] security1 = new int[]{1, 1, 1, 1, 1};
        int[] security2 = new int[]{1, 2, 3, 4, 5, 6};
        int[] security3 = new int[]{1};
        int[] security4 = new int[]{3, 0, 0, 0, 1};
        P2100_Solution solution = new P2100_Solution();
        System.out.println(solution.goodDaysToRobBank(security, 2)); // [2,3]
        System.out.println(solution.goodDaysToRobBank(security1, 0)); // [0,1,2,3,4]
        System.out.println(solution.goodDaysToRobBank(security2, 2)); // []
        System.out.println(solution.goodDaysToRobBank(security3, 5)); // []
        System.out.println(solution.goodDaysToRobBank(security4, 2)); // [2]
    }
}

// 动态规划
// 7 ms 74.36%
// 60.8 MB 5.13%
class P2100_Solution {
    public List<Integer> goodDaysToRobBank(int[] security, int time) {
        int n = security.length;
        // 从前往后连续多少天递减
        int[] preDec = new int[n];
        // 从后往前连续多少天递减(即从前往后连续多少天递增)
        int[] sufInc = new int[n];
        for (int i = 1; i < n; i++) {
            if (security[i] <= security[i - 1]) {
                preDec[i] = preDec[i - 1] + 1;
            }
//            if (security[n - 1 - i] <= security[n - i]) {
//                sufInc[n - 1 - i] = sufInc[n - i] + 1;
//            }
        }
        for (int i = n - 2; i >= 0; i--) {
            if (security[i] <= security[i + 1]) {
                sufInc[i] = sufInc[i + 1] + 1;
            }
        }
        System.out.println(Arrays.toString(preDec));
        System.out.println(Arrays.toString(sufInc));
        // 遍历求结果
        List<Integer> ans = new ArrayList<>();
        for (int i = time; i <= n - 1 - time; i++) {
            if (preDec[i] >= time && sufInc[i] >= time) {
                ans.add(i);
            }
        }
        return ans;
    }
}