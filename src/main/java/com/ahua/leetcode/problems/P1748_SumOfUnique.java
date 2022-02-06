package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-06 21:13
 */

/**
 * 1748. 唯一元素的和
 * 给你一个整数数组 nums 。数组中唯一元素是那些只出现 恰好一次 的元素。
 * <p>
 * 请你返回 nums 中唯一元素的 和 。
 * <p>
 * 1 <= nums.length <= 100
 * 1 <= nums[i] <= 100
 */
public class P1748_SumOfUnique {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 2};
        int[] nums1 = new int[]{1, 1, 1, 1, 1};
        int[] nums2 = new int[]{1, 2, 3, 4, 5};
        P1748_Solution solution = new P1748_Solution();
        System.out.println(solution.sumOfUnique(nums)); // 4
        System.out.println(solution.sumOfUnique(nums1)); // 0
        System.out.println(solution.sumOfUnique(nums2)); // 15
    }
}

// 这方法叫状态机? 无语, 又是新名词
class P1748_Solution {
    // 本应该使用哈希表来解决本问题, 但是由于 nums 的数据范围较小
    // 所以可以采用 数组代替哈希表 的这种空间换时间的方式来做
    public int sumOfUnique(int[] nums) {
        // 可以赋给每个元素三个状态：
        // 0：该元素尚未被访问；
        // 1：该元素被访问过一次；
        // 2：该元素被访问超过一次。
        int[] frequency = new int[101];
        int sum = 0;
        for (int num : nums) {
            // 第一次出现, sum 加上 num
            if (frequency[num] == 0) {
                frequency[num]++;
                sum += num;
            } else if (frequency[num] == 1) {
                // 第二次出现, sum 需要减掉 num
                // 更多次出现不做任何操作
                frequency[num]++;
                sum -= num;
            }
        }
        return sum;
    }
}