package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-24 20:36
 */

import java.util.Arrays;

/**
 * 661. 图片平滑器 image-smoother
 * 图像平滑器 是大小为 3 x 3 的过滤器，用于对图像的每个单元格平滑处理，平滑处理后单元格的值为该单元格的平均灰度。
 * <p>
 * 每个单元格的 平均灰度 定义为：该单元格自身及其周围的 8 个单元格的平均值，结果需向下取整。
 * （即，需要计算蓝色平滑器中 9 个单元格的平均值）。
 * <p>
 * 如果一个单元格周围存在单元格缺失的情况，则计算平均灰度时不考虑缺失的单元格（即，需要计算红色平滑器中 4 个单元格的平均值）。
 * <p>
 * m == img.length
 * n == img[i].length
 * 1 <= m, n <= 200
 * 0 <= img[i][j] <= 255
 */
public class P661_ImageSmoother {
    public static void main(String[] args) {
        int[][] img = new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        int[][] img1 = new int[][]{{100, 200, 100}, {200, 50, 200}, {100, 200, 100}};
        P661_Solution solution = new P661_Solution();
        System.out.println(Arrays.deepToString(solution.imageSmoother(img))); // [[0, 0, 0],[0, 0, 0], [0, 0, 0]]
        System.out.println(Arrays.deepToString(solution.imageSmoother(img1))); // [[137, 141, 137], [141, 138, 141], [137, 141, 137]]
    }
}

// 6 ms 67.79%
// 42 MB 48.85%
class P661_Solution {
    public static final int[] dx = new int[]{-1, -1, -1, 0, 0, 0, 1, 1, 1};
    public static final int[] dy = new int[]{-1, 0, 1, -1, 0, 1, -1, 0, 1};

    public int[][] imageSmoother(int[][] img) {
        int m = img.length;
        int n = img[0].length;
        int[][] ans = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int count = 0;
                int sum = 0;
                for (int k = 0; k < 9; k++) {
                    int x = i + dx[k];
                    int y = j + dy[k];
                    if (x >= 0 && x < m && y >= 0 && y < n) {
                        count++;
                        sum += img[x][y];
                    }
                }
                ans[i][j] = sum / count;
            }
        }
        return ans;
    }
}