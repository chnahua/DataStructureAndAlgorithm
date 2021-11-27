package com.ahua.leetcode.problems;

import java.util.*;

/**
 * @author huajun
 * @create 2021-11-27 21:52
 */

/**
 * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
 */
public class P47_PermuteUnique {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 2};
        int[] nums1 = new int[]{1, 2, 3};
        P47_Solution solution = new P47_Solution();
        // [[1, 1, 2], [1, 2, 1], [2, 1, 1]]
        System.out.println(solution.permuteUnique(nums));
        // [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
        System.out.println(solution.permuteUnique(nums1));
    }
}

class P47_Solution {
    int[] nums;
    int n;
    List<List<Integer>> ans;
    Deque<Integer> path;
    boolean[] visited;

    public List<List<Integer>> permuteUnique(int[] nums) {
        this.nums = nums;
        this.n = nums.length;
        this.ans = new ArrayList<>();
        this.path = new ArrayDeque<>(n);
        this.visited = new boolean[n];

        // 特殊情况处理 长度为 1 的序列直接返回
        if (n == 1) {
            path.add(nums[0]);
            ans.add(new ArrayList<>(path));
            return ans;
        }

        // 从第一个位置开始遍历
        backtrack(0);

        return ans;
    }

    public void backtrack(int depth) {
        if (depth == n) {
            ans.add(new ArrayList<>(path));
            return;
        }
        // 将确定该层的元素的值保存在 hashSet 中, 下一次遍历如果遇到该值, 则跳过, 剪枝所用
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < n; i++) {
            // 没有遍历过当前位置并且当前位置值在曾经也没有被遍历过
            if (!visited[i] && !hashSet.contains(nums[i])) {

                path.add(nums[i]);
                visited[i] = true;

                hashSet.add(nums[i]);
                // 遍历下一个位置
                backtrack(depth + 1);

                // 回溯 更新
                path.removeLast();
                visited[i] = false;
            }
        }
    }
}