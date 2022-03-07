package com.ahua.exam.huawei;

/**
 * @author huajun
 * @create 2022-01-21 17:29
 */

public class ODInterview_1_SZ_II_2022_01_21 {
    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 3, 5, 6, 7};
        int[] nums2 = new int[]{2, 8, 10, 3, 20};

        int[] nums3 = new int[]{1, 3, -5, 7, 9};
        int[] nums4 = new int[]{2, 30, 8, 10, 50};
        ODInterview_1_SZ_II_2022_01_21_Solution solution = new ODInterview_1_SZ_II_2022_01_21_Solution();
        System.out.println(solution.func(nums1, nums2));
        System.out.println(solution.func(nums3, nums4));
    }
}

/*
  给定两个相同长度的整数数组，交换一次两数组中的某个元素，使得交换后的两个数组和的差的绝对值最小，输出交换的元素位置，若无需交换返回 -1
 */
// 现场面试版本
class ODInterview_1_SZ_II_2022_01_21_Solution1 {
    public String func(int[] nums1, int[] nums2) {
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < nums1.length; i++) {
            sum1 += nums1[i];
            sum2 += nums2[i];
        }
        int sumDiff = Math.abs(sum1 - sum2);
        int index1 = 0;
        int index2 = 0;
        boolean bl = false;
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                int newSum1 = sum1 - nums1[i] + nums2[j];
                int newSum2 = sum2 - nums2[j] + nums1[i];
                int newDiff = Math.abs(newSum1 - newSum2);
                if (newDiff < sumDiff) {
                    sumDiff = newDiff;
                    index1 = i;
                    index2 = j;
                    bl = true;
                }
            }
        }

        if (!bl) {
            return "" + -1;
        }
        return index1 + " " + index2;
    }
}

// 添加注释版本
class ODInterview_1_SZ_II_2022_01_21_Solution2 {
    public String func(int[] nums1, int[] nums2) {
        // 两数组中的元素的总和
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < nums1.length; i++) {
            sum1 += nums1[i];
            sum2 += nums2[i];
        }
        // 两数组的总和之差的绝对值
        int sumDiff = Math.abs(sum1 - sum2);
        System.out.println("sumDiff : " + sumDiff);
        // 交换的两个元素的位置索引
        int index1 = 0;
        int index2 = 0;
        // 标记是否需要交换
        boolean bl = false;
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                // 交换 i,j 元素之后的两数组中元素的总和
                int newSum1 = sum1 - nums1[i] + nums2[j];
                int newSum2 = sum2 - nums2[j] + nums1[i];
                // 交换 i,j 元素之后的两数组中元素的总和之差的绝对值
                int newDiff = Math.abs(newSum1 - newSum2);
                System.out.println("i : " + i + " , j : " + j + ", newDiff : " + newDiff);
                // 如果新的差值 newDiff 比之前的差值 sumDiff 小就更新
                if (newDiff < sumDiff) {
                    sumDiff = newDiff;
                    index1 = i;
                    index2 = j;
                    bl = true;
                }
            }
        }
        // 如果从未交换过（无需交换）, 返回 -1
        if (!bl) {
            return "" + -1;
        }
        return index1 + " " + index2;
    }
}

// 简化逻辑与代码, 只是小修改, 没有大变化
class ODInterview_1_SZ_II_2022_01_21_Solution {
    public String func(int[] nums1, int[] nums2) {
        // 两数组中的元素的总和
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < nums1.length; i++) {
            sum1 += nums1[i];
            sum2 += nums2[i];
        }
        // 两数组的总和之差的绝对值
        int sumDiff = Math.abs(sum1 - sum2);
        System.out.println("sumDiff : " + sumDiff);
        // 交换的两个元素的位置索引
        int index1 = 0;
        int index2 = 0;
        // 标记是否需要交换
        boolean bl = false;
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                /*
                // 交换 i,j 元素之后的两数组中元素的总和
                int newSum1 = sum1 - nums1[i] + nums2[j];
                int newSum2 = sum2 - nums2[j] + nums1[i];
                // 交换 i,j 元素之后的两数组中元素的总和之差的绝对值
                int newDiff = Math.abs(newSum1 - newSum2);
                 */
                // 交换 i,j 元素之后的两数组中元素的总和之差的绝对值
                int newDiff = Math.abs(sum1 - sum2 + 2 * (nums2[j] - nums1[i]));
                System.out.println("i : " + i + " , j : " + j + ", newDiff : " + newDiff);
                // 如果新的差值 newDiff 比之前的差值 sumDiff 小就更新
                if (newDiff < sumDiff) {
                    sumDiff = newDiff;
                    index1 = i;
                    index2 = j;
                    bl = true;
                }
            }
        }
        // 如果从未交换过（无需交换）, 返回 -1
        if (!bl) {
            return "" + -1;
        }
        return index1 + " " + index2;
    }
}