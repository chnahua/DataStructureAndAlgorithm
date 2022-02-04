package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-04 20:07
 */

/**
 * 1725. 可以形成最大正方形的矩形数目 number-of-rectangles-that-can-form-the-largest-square
 * 给你一个数组 rectangles ，其中 rectangles[i] = [li, wi] 表示第 i 个矩形的长度为 li 、宽度为 wi 。
 * <p>
 * 如果存在 k 同时满足 k <= li 和 k <= wi ，就可以将第 i 个矩形切成边长为 k 的正方形。例如，矩形 [4,6] 可以切成边长最大为 4 的正方形。
 * <p>
 * 设 maxLen 为可以从矩形数组 rectangles 切分得到的 最大正方形 的边长。
 * <p>
 * 请你统计有多少个矩形能够切出边长为 maxLen 的正方形，并返回矩形 数目 。
 * 1 <= rectangles.length <= 1000
 * rectangles[i].length == 2
 * 1 <= li, wi <= 10^9
 * li != wi
 */
public class P1725_CountGoodRectangles {
    public static void main(String[] args) {
        int[][] rectangles = new int[][]{{5, 8}, {3, 9}, {5, 12}, {16, 5}};
        int[][] rectangles1 = new int[][]{{2, 3}, {3, 7}, {4, 3}, {3, 7}};
        P1725_Solution solution = new P1725_Solution();
        System.out.println(solution.countGoodRectangles(rectangles)); // 3
        System.out.println(solution.countGoodRectangles(rectangles1)); // 3
    }
}

class P1725_Solution {
    public int countGoodRectangles(int[][] rectangles) {
        int maxLen = 0;
        int count = 0;
        for (int[] lw : rectangles) {
            int edge = Math.min(lw[0], lw[1]);
            // 遇到更大的正方形, 保存最大长度和数量置为 1
            if (maxLen < edge) {
                maxLen = edge;
                count = 1;
            } else if (maxLen == edge) {
                // 相同长度数量加一
                count++;
            }
        }
        return count;
    }
}