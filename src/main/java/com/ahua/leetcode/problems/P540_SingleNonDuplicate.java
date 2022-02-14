package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-15 0:31
 */

/**
 * 540. 有序数组中的单一元素 single-element-in-a-sorted-array
 * 给你一个仅由整数组成的有序数组，其中每个元素都会出现两次，唯有一个数只会出现一次。
 * <p>
 * 请你找出并返回只出现一次的那个数。
 * <p>
 * 你设计的解决方案必须满足 O(log n) 时间复杂度和 O(1) 空间复杂度。
 * <p>
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^5
 */
public class P540_SingleNonDuplicate {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 2, 3, 3, 4, 4, 8, 8};
        int[] nums1 = new int[]{3, 3, 7, 7, 10, 11, 11};
        P540_Solution solution = new P540_Solution();
        System.out.println(solution.singleNonDuplicate(nums)); // 2
        System.out.println(solution.singleNonDuplicate(nums1)); // 10
    }
}

// 二分查找 0ms
// 官方解法很神奇, 代码量也少了很多, 暂时不看了
class P540_Solution {
    public int singleNonDuplicate(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        int left = 0;
        int mid;
        int right = n - 1;
        while (left <= right) {
            // 首先可以保证每次循环时子数组长度 right - left + 1 是奇数
            mid = left + (right - left) / 2;
            // 只有一个数时直接返回, 此时的 left mid right 相等
            if (right - left == 0) {
                return nums[mid];
            }
            // 由于总长度是奇数, mid 肯定位于最中间位置, 当它与它两边的数都不想等时, 它就是这个 singleNumber
            if (nums[mid] != nums[mid - 1] && nums[mid] != nums[mid + 1]) {
                return nums[mid];
            }
            // 然后根据当前 mid 是奇数还是偶数, 也就是两边的数字的个数以及与其左边还是右边的数相等来判断
            if (mid % 2 == 0) {
                if (nums[mid] == nums[mid - 1]) {
                    right = mid - 2;
                } else if (nums[mid] == nums[mid + 1]) {
                    left = mid + 2;
                }
            } else {
                if (nums[mid] == nums[mid - 1]) {
                    left = mid + 1;
                } else if (nums[mid] == nums[mid + 1]) {
                    right = mid - 1;
                }
            }
        }
        // System.out.println("永不会执行到这里");
        return 0;
    }
}
