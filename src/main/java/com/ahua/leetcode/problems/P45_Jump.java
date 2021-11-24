package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-24 23:13
 */

/**
 * 给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
 * <p>
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * <p>
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 * <p>
 * 假设你总是可以到达数组的最后一个位置。
 * <p>
 * 1 <= nums.length <= 104
 * 0 <= nums[i] <= 1000
 */
public class P45_Jump {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 1, 1, 4};
        int[] nums1 = new int[]{2, 3, 0, 1, 4};
        P45_Solution solution = new P45_Solution();
        System.out.println(solution.jump(nums)); // 2
        System.out.println(solution.jump(nums1)); // 2
    }
}

// 贪心: 方法一: 反向查找出发位置
// 时间复杂度：O(n^2)
// 空间复杂度：O(1)
class P45_Solution1 {
    public int jump(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        // 要走到的位置
        int position = nums.length - 1;
        // 跳跃次数
        int steps = 0;
        while (position > 0) {
            for (int i = 0; i < position; i++) {
                if (i + nums[i] >= position) {
                    position = i;
                    steps++;
                    break;
                }
            }
        }
        return steps;
    }
}

// 贪心: 方法二：正向查找可到达的最大位置
// 时间复杂度：O(n)
// 空间复杂度：O(1)
class P45_Solution {
    public int jump(int[] nums) {
        if (nums == null) {
            return 0;
        }

        int n = nums.length;

        if (n <= 1) {
            return 0;
        }
        // 每一段遍历要走到的最后位置, 遍历过程中比较确定下一步能够走到的最远位置
        // 上次跳跃可达范围右边界（下次的最右起跳点）
        int end = 0;
        // 目前能跳到的最远位置
        int maxPosition = 0;
        // 跳跃次数
        int steps = 0;

        for (int i = 0; i < n - 1; i++) {
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == end) {
                // 目前能跳到的最远位置变成了下次起跳位置的右边界
                end = maxPosition;
                steps++;
            }
        }

        return steps;
    }
}