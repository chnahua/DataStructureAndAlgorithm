package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-15 22:37
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1380. 矩阵中的幸运数 lucky-numbers-in-a-matrix
 * 给你一个 m * n 的矩阵，矩阵中的数字 各不相同 。请你按 任意 顺序返回矩阵中的所有幸运数。
 * <p>
 * 幸运数是指矩阵中满足同时下列两个条件的元素：
 * <p>
 * 在同一行的所有元素中最小
 * 在同一列的所有元素中最大
 * <p>
 * m == mat.length
 * n == mat[i].length
 * 1 <= n, m <= 50
 * 1 <= matrix[i][j] <= 10^5
 * 矩阵中的所有元素都是不同的
 */
public class P1380_LuckyNumbers {
    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {3, 7, 8},
                {9, 11, 13},
                {15, 16, 17}
        };
        int[][] matrix1 = new int[][]{
                {1, 10, 4, 2},
                {9, 3, 8, 7},
                {15, 16, 17, 12}
        };
        int[][] matrix2 = new int[][]{
                {7, 8},
                {1, 2}
        };
        P1380_Solution solution = new P1380_Solution();
        System.out.println(solution.luckyNumbers(matrix)); // 15
        System.out.println(solution.luckyNumbers(matrix1)); // 12
        System.out.println(solution.luckyNumbers(matrix2)); // 7
    }
}

// 我的解法 基本操作
class P1380_Solution1 {
    public List<Integer> luckyNumbers(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        // 行最小元素所在列
        // rowMin[i] 为第 i 行的最小元素所在列
        int[] rowMin = new int[m];
        // 列最大元素所在行
        // colMax[j] 为第 j 列的最大元素所在行
        int[] colMax = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][rowMin[i]] > matrix[i][j]) {
                    // 保存纵坐标
                    rowMin[i] = j;
                }
                if (matrix[colMax[j]][j] < matrix[i][j]) {
                    // 保存横坐标
                    colMax[j] = i;
                }
            }
        }
        List<Integer> ans = new ArrayList<>();
        // 遍历 rowMin 或者 colMax 均可
        // 根据 rowMin[i] 处保存的第 i 行中的最小元素所在列 rowMin[i],
        // 在 colMax 中得到第 rowMin[i] 列中的最大元素所在行 colMax[rowMin[i]]
        // 比较行 colMax[rowMin[i]] 和行 i 是否相等, 就可以判断是不是幸运数
        for (int i = 0; i < m; i++) {
            if (colMax[rowMin[i]] == i) {
                ans.add(matrix[i][rowMin[i]]);
            }
        }
        return ans;
    }
}

/*
根据题目的要求，可以得到一个结论
假设至少有两个解[x1,y1]和[x2,y2]，显然这俩不是同行同列，即x1!=x2 and y1!=y2。
对于[x1,y1]，因为是同行最小值，所以m[x1][y1]<m[x1][y2]，因为是同列最大值，所以m[x1][y1]>m[x2][y1]，即m[x1][y2]>m[x1][y1]>m[x2][y1]。
对[x2,y2]应用相同逻辑，可以得到m[x2][y1]>m[x2][y2]>m[x1][y2]。
矛盾，因此假设不成立，即最多只可能有一个解。
 */
class P1380_Solution {
    public List<Integer> luckyNumbers(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        // 行最小元素所在列
        // rowMin[i] 为第 i 行的最小元素所在列
        int[] rowMin = new int[m];
        // 列最大元素所在行
        // colMax[j] 为第 j 列的最大元素所在行
        int[] colMax = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][rowMin[i]] > matrix[i][j]) {
                    // 保存纵坐标
                    rowMin[i] = j;
                }
                if (matrix[colMax[j]][j] < matrix[i][j]) {
                    // 保存横坐标
                    colMax[j] = i;
                }
            }
        }
        List<Integer> ans = new ArrayList<>();
        // 遍历 rowMin 或者 colMax 均可
        // 根据 rowMin[i] 处保存的第 i 行中的最小元素所在列 rowMin[i],
        // 在 colMax 中得到第 rowMin[i] 列中的最大元素所在行 colMax[rowMin[i]]
        // 比较行 colMax[rowMin[i]] 和行 i 是否相等, 就可以判断是不是幸运数
        for (int i = 0; i < m; i++) {
            if (colMax[rowMin[i]] == i) {
                ans.add(matrix[i][rowMin[i]]);
            }
        }
        return ans;
    }
}