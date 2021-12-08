package com.ahua.nowcoder.huawei;

import java.util.Scanner;

/**
 * @author huajun
 * @create 2021-12-02 22:42
 */

public class HJ24_Choir {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            // 特殊情况处理
            if (n == 1 || n == 2) {
                System.out.println(0);
                return;
            }
            int[] stu = new int[n];

            // 获取输入
            for (int i = 0; i < n; i++) {
                stu[i] = sc.nextInt();
            }

            // 存储每个数左边小于其的数的个数
            int[] left = leftDP(stu);
            // 存储每个数右边小于其的数的个数
            int[] right = rightDP(stu);

            // 合唱队中最多人数
            int max = 0;
            for (int i = 0; i < n; i++) {
                // 同一位置 left[i] right[i] 两个都包含了 stu[i] 本身
                max = Math.max(max, left[i] + right[i] - 1);
            }

            // 最少出列人数为
            System.out.println(n - max);
        }

        sc.close();
    }

    // 动态规划
    public static int[] leftDP(int[] arr) {
        if (arr == null) {
            return null;
        }
        int len = arr.length;
        int[] left = new int[len];
        for (int i = 0; i < len; i++) {
            left[i] = 1;
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    left[i] = Math.max(left[i], left[j] + 1);
                }
            }
        }
        return left;
    }

    // 动态规划
    public static int[] rightDP(int[] arr) {
        if (arr == null) {
            return null;
        }
        int len = arr.length;
        int[] right = new int[len];
        for (int i = len - 1; i >= 0; i--) {
            right[i] = 1;
            for (int j = len - 1; j >= 0; j--) {
                if (arr[j] < arr[i]) {
                    right[i] = Math.max(right[i], right[j] + 1);
                }
            }
        }
        return right;
    }
}