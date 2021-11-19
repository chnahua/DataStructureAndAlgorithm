package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-19 22:43
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个数组 rectangles ，其中 rectangles[i] = [xi, yi, ai, bi] 表示一个坐标轴平行的矩形。这个矩形的左下顶点是 (xi, yi) ，右上顶点是 (ai, bi) 。
 * <p>
 * 如果所有矩形一起精确覆盖了某个矩形区域，则返回 true ；否则，返回 false 。
 */
public class P391_IsRectangleCover {
    public static void main(String[] args) {
        int[][] rectangles = new int[][]{
                {1, 1, 3, 3},
                {3, 1, 4, 2},
                {3, 2, 4, 4},
                {1, 3, 2, 4},
                {2, 3, 3, 4}
        };
        int[][] rectangles1 = new int[][]{
                {1, 1, 2, 3},
                {1, 3, 2, 4},
                {3, 1, 4, 2},
                {3, 2, 4, 4}
        };
        int[][] rectangles2 = new int[][]{
                {1, 1, 3, 3},
                {3, 1, 4, 2},
                {1, 3, 2, 4},
                {3, 2, 4, 4}
        };
        int[][] rectangles3 = new int[][]{
                {1, 1, 3, 3},
                {3, 1, 4, 2},
                {1, 3, 2, 4},
                {2, 2, 4, 4}
        };
        P391_Solution solution = new P391_Solution();
        System.out.println(solution.isRectangleCover(rectangles)); // true
        System.out.println(solution.isRectangleCover(rectangles1)); // false
        System.out.println(solution.isRectangleCover(rectangles2)); // false
        System.out.println(solution.isRectangleCover(rectangles3)); // false
    }
}

class P391_Solution {
    public boolean isRectangleCover(int[][] rectangles) {
        int n = rectangles.length;
        if (n == 1) {
            return true;
        }
        // 定义矩形的面积
        long area = 0;

        int minX = rectangles[0][0];
        int minY = rectangles[0][1];
        int maxX = rectangles[0][2];
        int maxY = rectangles[0][3];
        // 保存各个顶点出现的次数
        Map<Point, Integer> cnt = new HashMap<>();
        for (int[] rect : rectangles) {
            // 两个顶点的横纵坐标
            int a = rect[0];
            int b = rect[1];
            int c = rect[2];
            int d = rect[3];

            // 累加各个矩形的面积, 得到总和, 最后与 (maxX - minX) * (maxY - minY) 比较
            area += (long) (c - a) * (d - b);

            minX = Math.min(minX, a);
            minY = Math.min(minY, b);
            maxX = Math.max(maxX, c);
            maxY = Math.max(maxY, d);

            // 将该矩形的四个顶点加入 map 中, 并更新出现次数
            Point point1 = new Point(a, b);
            Point point2 = new Point(a, d);
            Point point3 = new Point(c, b);
            Point point4 = new Point(c, d);

            cnt.put(point1, cnt.getOrDefault(point1, 0) + 1);
            cnt.put(point2, cnt.getOrDefault(point2, 0) + 1);
            cnt.put(point3, cnt.getOrDefault(point3, 0) + 1);
            cnt.put(point4, cnt.getOrDefault(point4, 0) + 1);
        }

        // System.out.println("maxX = " + maxX + ", minX = " + minX + ", maxY = " + maxY + ", minY = " + minY);
        // System.out.println((maxX - minX) * (maxY - minY));
        // 得到最终完美矩形的四个顶点
        Point pointMinMin = new Point(minX, minY);
        Point pointMinMax = new Point(minX, maxY);
        Point pointMaxMin = new Point(maxX, minY);
        Point pointMaxMax = new Point(maxX, maxY);

        // 面积不相等或者四个顶点中并不是每个顶点都只出现一次
        if (area != (long) (maxX - minX) * (maxY - minY) ||
                cnt.getOrDefault(pointMinMin, 0) != 1 ||
                cnt.getOrDefault(pointMinMax, 0) != 1 ||
                cnt.getOrDefault(pointMaxMin, 0) != 1 ||
                cnt.getOrDefault(pointMaxMax, 0) != 1) {
            return false;
        }

        // 移除四个角顶点
        cnt.remove(pointMinMin);
        cnt.remove(pointMinMax);
        cnt.remove(pointMaxMin);
        cnt.remove(pointMaxMax);

        // 判断非矩形区域四角的顶点 出现的次数
        // 如果是完美矩形, 出现次数只能是 2 或者 4 次
        for (Map.Entry<Point, Integer> entry : cnt.entrySet()) {
            int value = entry.getValue();
            if (value != 2 && value != 4) {
                return false;
            }
        }
        return true;
    }
}

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        // return x + y;
        // 这个太神奇了, 改哈希函数, 太影响性能了
        return x * 512 - y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point point2 = (Point) obj;
            return this.x == point2.x && this.y == point2.y;
        }
        return false;
    }
}