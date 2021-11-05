package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-05 16:18
 */

/**
 * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。
 * 在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 说明：你不能倾斜容器。
 *
 * n == height.length
 * 2 <= n <= 105
 * 0 <= height[i] <= 104
 */
public class P11_MaxArea {
    public static void main(String[] args) {
        int[] height = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
        int[] height1 = new int[]{1, 1};
        int[] height2 = new int[]{4, 3, 2, 1, 4};
        int[] height3 = new int[]{1, 2, 1};
        P11_Solution solution = new P11_Solution();
        // maxArea1
        System.out.println("---------- maxArea ----------");
        System.out.println(solution.maxArea(height)); // 49
        System.out.println(solution.maxArea(height1)); // 1
        System.out.println(solution.maxArea(height2)); // 16
        System.out.println(solution.maxArea(height3)); // 2
        // maxArea1
        System.out.println("---------- maxArea1 ----------");
        System.out.println(solution.maxArea1(height)); // 49
        System.out.println(solution.maxArea1(height1)); // 1
        System.out.println(solution.maxArea1(height2)); // 16
        System.out.println(solution.maxArea1(height3)); // 2
    }
}

/**
 * 双指针
 */
class P11_Solution {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int area = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                area = Math.max(area, (right - left) * height[left]);
                left++;
            } else {
                area = Math.max(area, (right - left) * height[right]);
                right--;
            }
            // System.out.println("left : " + left + " | right : " + right + " | area : " + area);
        }
        return area;
    }

    // 逻辑优化 —— 不计算中间过程中肯定小于等于已知最大 area 的面积
    // 但是会多了很多其它代码以及逻辑判断
    public int maxArea1(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int area = 0;
        int temp;
        while (left < right) {
            while (height[left] < height[right]) {
                temp = left;
                area = Math.max(area, (right - left) * height[left]);
                left++;
                while (left < right && height[left] <= height[temp]) {
                    left++;
                }
                // System.out.println("left : " + left + " | right : " + right + " | area : " + area);
            }
            while (height[left] >= height[right]) {
                temp = right;
                area = Math.max(area, (right - left) * height[right]);
                right--;
                while (left < right && height[right] <= height[temp]) {
                    right--;
                }
                // System.out.println("left : " + left + " | right : " + right + " | area : " + area);
                if (right <= left) {
                    break;
                }
            }
        }
        return area;
    }
}