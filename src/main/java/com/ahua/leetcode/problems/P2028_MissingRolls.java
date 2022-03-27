package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-27 22:05
 */

import javafx.beans.value.ObservableNumberValue;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * 2028. 找出缺失的观测数据 find-missing-observations
 * 现有一份 n + m 次投掷单个 六面 骰子的观测数据，骰子的每个面从 1 到 6 编号。
 * 观测数据中缺失了 n 份，你手上只拿到剩余 m 次投掷的数据。幸好你有之前计算过的这 n + m 次投掷数据的 平均值 。
 * <p>
 * 给你一个长度为 m 的整数数组 rolls ，其中 rolls[i] 是第 i 次观测的值。同时给你两个整数 mean 和 n 。
 * <p>
 * 返回一个长度为 n 的数组，包含所有缺失的观测数据，且满足这 n + m 次投掷的 平均值 是 mean 。
 * 如果存在多组符合要求的答案，只需要返回其中任意一组即可。如果不存在答案，返回一个空数组。
 * <p>
 * k 个数字的 平均值 为这些数字求和后再除以 k 。
 * <p>
 * 注意 mean 是一个整数，所以 n + m 次投掷的总和需要被 n + m 整除。
 * <p>
 * m == rolls.length
 * 1 <= n, m <= 10^5
 * 1 <= rolls[i], mean <= 6
 */
public class P2028_MissingRolls {
    public static void main(String[] args) {
        int[] rolls = new int[]{3, 2, 4, 3};
        int[] rolls1 = new int[]{1, 5, 6};
        int[] rolls2 = new int[]{1, 2, 3, 4};
        int[] rolls3 = new int[]{1};
        int[] rolls4 = new int[]{6, 3, 4, 3, 5, 3};
        P2028_Solution solution = new P2028_Solution();
        System.out.println(Arrays.toString(solution.missingRolls(rolls, 4, 2))); // [6, 6]
        System.out.println(Arrays.toString(solution.missingRolls(rolls1, 3, 4))); // [2, 2, 2, 3]
        System.out.println(Arrays.toString(solution.missingRolls(rolls2, 6, 4))); // []
        System.out.println(Arrays.toString(solution.missingRolls(rolls3, 3, 1))); // [5]
        System.out.println(Arrays.toString(solution.missingRolls(rolls4, 1, 6))); // []
    }
}

// 数学题 基本操作
// 2 ms 100.00%
// 54.4 MB 82.88%
class P2028_Solution {
    public int[] missingRolls(int[] rolls, int mean, int n) {
        // 若是在 int i = 0; 处再创建这个数组, 就会消耗更多内存
        int[] ans = new int[n];
        // 剩余的 m 个数据的和
        int mRollsSum = 0;
        for (int roll : rolls) {
            mRollsSum += roll;
        }
        // 丢失的 n 个数据的和
        int nRollsSum = (rolls.length + n) * mean - mRollsSum;
        // 较小的骰子点数
        int a = nRollsSum / n;
        // 大一点的骰子点数
        int b = a + 1;
        // 大一点的骰子点数 b 的个数
        int bNum = nRollsSum % n;
        // aNum + bNum = n
        // 剩下的就是较小的骰子点数 a 的个数
        int aNum = n - bNum;
        if (a <= 0 || a > 6 || (a == 6 && bNum != 0)) {
            return new int[]{};
        }
        int i = 0;
        for (; i < aNum; i++) {
            ans[i] = a;
        }
        for (; i < n; i++) {
            ans[i] = b;
        }
        return ans;
    }
}