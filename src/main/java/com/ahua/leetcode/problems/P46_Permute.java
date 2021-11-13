package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-13 19:16
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 * <p>
 * 1 <= nums.length <= 6
 * -10 <= nums[i] <= 10
 * nums 中的所有整数 互不相同
 */
public class P46_Permute {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        int[] nums1 = new int[]{0, 1};
        int[] nums2 = new int[]{1};
        P46_Solution solution = new P46_Solution();
        System.out.println(solution.permute(nums));
        System.out.println(solution.permute(nums1));
        System.out.println(solution.permute(nums2)); // [[1]]
    }
}

class P46_Solution {
    int[] nums;
    int len;
    // 使用一个动态数组保存所有可能的全排列
    List<List<Integer>> ans;
    Deque<Integer> path;
    boolean[] used;

    public List<List<Integer>> permute(int[] nums) {
        this.nums = nums;
        this.len = nums.length;
        this.used = new boolean[len];
        this.path = new ArrayDeque<>(len);
        this.ans = new ArrayList<>();
        // 长度为 0, 返回 [[]]
        if (len == 0) {
            return ans;
        }
        // 只有一个数, 返回 [[nums[0]]]
        if (len == 1) {
            path.add(nums[0]);
            ans.add(new ArrayList<>(path));
            return ans;
        }

        dfs(0);
        return ans;
    }

    public void dfs(int depth) {
        // 遍历到的层数 等于 数字的个数时
        if (depth == len) {
            // 将当前路径添加进 ans 中
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < len; i++) {
            if (!used[i]) {
                path.add(nums[i]);
                used[i] = true;

                dfs(depth + 1);

                path.removeLast();
                used[i] = false;
            }
        }
    }
}