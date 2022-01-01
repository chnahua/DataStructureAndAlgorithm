package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-01 21:45
 */

import java.util.Arrays;

/**
 * 2022. 将一维数组转变成二维数组
 * <p>
 * 给你一个下标从 0 开始的一维整数数组 original 和两个整数 m 和 n 。你需要使用 original 中 所有 元素创建一个 m 行 n 列的二维数组。
 * <p>
 * original 中下标从 0 到 n - 1 （都 包含 ）的元素构成二维数组的第一行，下标从 n 到 2 * n - 1 （都 包含 ）的元素构成二维数组的第二行，依此类推。
 * <p>
 * 请你根据上述过程返回一个 m x n 的二维数组。如果无法构成这样的二维数组，请你返回一个空的二维数组。
 * <p>
 * 1 <= original.length <= 5 * 10^4
 * 1 <= original[i] <= 10^5
 * 1 <= m, n <= 4 * 10^4
 */
public class P2022_Construct2DArray {
    public static void main(String[] args) {
        int[] original = new int[]{1, 2, 3, 4};
        int[] original1 = new int[]{1, 2, 3};
        int[] original2 = new int[]{1, 2};
        int[] original3 = new int[]{3};
        P2022_Solution solution = new P2022_Solution();
        System.out.println(Arrays.deepToString(solution.construct2DArray(original, 2, 2)));
        System.out.println(Arrays.deepToString(solution.construct2DArray(original1, 1, 3)));
        System.out.println(Arrays.deepToString(solution.construct2DArray(original2, 1, 1)));
        System.out.println(Arrays.deepToString(solution.construct2DArray(original3, 1, 2)));
    }
}

class P2022_Solution {
    // 我的解法
    public int[][] construct2DArray1(int[] original, int m, int n) {
        int len = original.length;
        if (len != m * n) {
            return new int[][]{};
        }
        int[][] ans = new int[m][n];
        int k = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ans[i][j] = original[k];
                k++;
            }
        }
        return ans;
    }

    // 官方解法 效率好低呀！
    public int[][] construct2DArray(int[] original, int m, int n) {
        if (original.length != m * n) {
            return new int[0][];
        }
        int[][] ans = new int[m][n];
        for (int i = 0; i < original.length; i += n) {
            System.arraycopy(original, i, ans[i / n], 0, n);
        }
        return ans;
    }
}