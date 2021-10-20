package com.ahua.leetcode.problems;

import javax.jws.soap.SOAPBinding;
import java.lang.annotation.Target;

/**
 * @author huajun
 * @create 2021-10-20 23:47
 */

/**
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 *
 * 请必须使用时间复杂度为 O(log n) 的算法。
 *
 * 提示:
 * 1 <= nums.length <= 104
 * -104 <= nums[i] <= 104
 * nums 为无重复元素的升序排列数组
 * -104 <= target <= 104
 *
 */

public class P35_SearchInsert {
    public static void main(String[] args) {
        int[] nums = new int[]{1,3,5,6};
        int[] nums1 = new int[]{1};
        int target1 = 5; // 2
        int target2 = 2; // 1
        int target3 = 7; // 4
        int target4 = 0; // 0
        int target5 = 0; // 0
        P35_Solution solution = new P35_Solution();
        System.out.println(solution.searchInsert(nums, target1));
        System.out.println(solution.searchInsert(nums, target2));
        System.out.println(solution.searchInsert(nums, target3));
        System.out.println(solution.searchInsert(nums, target4));
        System.out.println(solution.searchInsert(nums1, target5));
    }
}

class P35_Solution {
    public int searchInsert1(int[] nums, int target) {
        if (nums.length == 1) {
            if (nums[0] >= target) {
                return 0;
            } else {
                return 1;
            }
        }
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }
        }
        if (mid == 0) {
            if (nums[0] > target) {
                return 0;
            } else if (nums[1] > target) {
                return 1;
            }
        } else if (mid == nums.length - 1) {
            if (nums[mid] < target) {
                return mid + 1;
            } else if (nums[mid - 1] < target) {
                return mid;
            }
        } else {
            if (nums[mid - 1] > target) {
                return mid - 1;
            } else if (nums[mid] > target) {
                return mid;
            } else if (nums[mid + 1] > target) {
                return mid + 1;
            }
        }
        return 0;
    }

    public int searchInsert(int[] nums, int target) {
        if (nums.length == 1) {
            if (nums[0] >= target) {
                return 0;
            } else {
                return 1;
            }
        }
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }
        }
        // left > right 的情形,数组中无目标值
        // 表示最后一步从 right = middle - 1 分支跳出,也即 nums[middle] > target, 重新插入位置即为 mid
        if (right < mid) {
            return mid;
        }
        // 表示 nums[mid] < target,重新插入位置为 mid + 1, 此时的 mid + 1 也等于 left
        return mid + 1;
        // 也等价于 return left == mid + 1 ? left : mid;
    }
}