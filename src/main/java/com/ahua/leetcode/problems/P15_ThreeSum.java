package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-10-19 16:35
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？
 * 请你找出所有和为 0 且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 * 其它地方遇到的题,
 * 1. 一个数组被分成三份, 左边子数组的最大值等于中间子数组的最小值等于右边子数组的最大值
 * 2. 一个数组被两个其中两个数 num[i], num[j] 分成了三个子数组(不包含这两个数), 求使得这三个子数组的和相等的这两个数的下标 i, j
 * 如: nums[] = [2,1,3,1,1,1,5,3], 选取 nums[2] = 3, num[6] = 5, 三部分的和都是3. 则打印输出2, 6, 如果找不到符合条件的等分点，返回失败
 */
public class P15_ThreeSum {
    public static void main(String[] args) {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4}; // 输出: [[-1,-1,2],[-1,0,1]]
        P15_Solution solution = new P15_Solution();
        List<List<Integer>> ans = solution.threeSum(nums);
        System.out.println(ans);
        for(List<Integer> list : ans) {
            System.out.println(list.get(0) + " " + list.get(1) + " " + list.get(2));
        }
    }
}

class P15_Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        int len = nums.length;
        if (len < 3) {
            return null;
        }
        Arrays.sort(nums);
        if (nums[0] > 0) {
            return null;
        }
        List<List<Integer>> ans = new ArrayList<>();
        int first, second, third;
        // 枚举 a
        for (first = 0; first < len; first++) {
            // 和上一次枚举的数不相同
            if(first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // c 对应的指针初始指向数组的最右端
            third = len - 1;
            // 枚举 b c
            for (second = first + 1; second < len; second++) {
                // 和上一次枚举的数不相同
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                // 保证 b 的指针在 c 的指针的左侧
                while (second < third && nums[second] + nums[third] > -nums[first]) {
                    third--;
                }
                // 如果指针重合，随着 b 后续的增加
                // 就不会有满足 a+b+c=0 并且 b<c 的 c 了,可以退出循环
                if (second == third) {
                    break;
                }
                // 找到
                if (nums[second] + nums[third] == -nums[first]) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }
            }
        }
        return ans;
    }
}
