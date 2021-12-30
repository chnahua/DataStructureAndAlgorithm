package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-30 22:21
 */

/**
 * 33. 搜索旋转排序数组
 * <p>
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 * <p>
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，
 * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
 * 例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
 * <p>
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
 * <p>
 * 1 <= nums.length <= 5000
 * -10^4 <= nums[i] <= 10^4
 * nums 中的每个值都 独一无二
 * 题目数据保证 nums 在预先未知的某个下标上进行了旋转
 * -10^4 <= target <= 10^4
 * <p>
 * 进阶：你可以设计一个时间复杂度为 O(log n) 的解决方案吗？
 */
public class P33_Search {
    public static void main(String[] args) {
        int[] nums = new int[]{4, 5, 6, 7, 0, 1, 2};
        int[] nums1 = new int[]{1};
        int[] nums2 = new int[]{5, 1, 3};
        int[] nums3 = new int[]{1, 3};
        P33_Solution solution = new P33_Solution();
        System.out.println(solution.search(nums, 0)); // 4
        System.out.println(solution.search(nums, 3)); // -1
        System.out.println(solution.search(nums1, 0)); // -1
        System.out.println(solution.search(nums2, 5)); // 0
        System.out.println(solution.search(nums3, 3)); // 1
    }
}

class P33_Solution {
    // 顺序查找
    // 时间复杂度: O(n)
    // 空间复杂度: O(1)
    public int search1(int[] nums, int target) {
        int n = nums.length;
        if (n <= 0) {
            return -1;
        } else if (n == 1) {
            if (nums[0] == target) {
                return 0;
            } else {
                return -1;
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // 二分查找
    // 时间复杂度: O(log n)
    // 空间复杂度: O(1)
    public int search2(int[] nums, int target) {
        int n = nums.length;
        // 可删的特殊情况判断
        if (n <= 0) {
            return -1;
        } else {
            if (nums[0] == target) {
                return 0;
            } else if (nums[n - 1] == target) {
                return n - 1;
            }
            if (n == 1) {
                return -1;
            }
        }
        int left = 0, right = n - 1, mid;
        // onTheLeft 为真 target 在左边
        boolean onTheLeft = target >= nums[0];
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                // 题目保证 nums 中的每个值都 独一无二
                // mid 在左边
                if (nums[mid] >= nums[0]) {
                    left = mid + 1;
                } else {
                    // mid 在右边
                    // target 在左边
                    if (onTheLeft) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
            } else if (nums[mid] > target) {
                // mid 在右边
                if (nums[mid] < nums[0]) {
                    right = mid - 1;
                } else {
                    // mid 在左边
                    // target 在左边
                    if (onTheLeft) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
            }
        }
        return -1;
    }

    // 二分查找
    // 时间复杂度: O(log n)
    // 空间复杂度: O(1)
    // 代码简化
    public int search(int[] nums, int target) {
        int n = nums.length;
        // 可删的特殊情况判断
        if (n <= 0) {
            return -1;
        } else {
            if (nums[0] == target) {
                return 0;
            } else if (nums[n - 1] == target) {
                return n - 1;
            }
            if (n == 1) {
                return -1;
            }
        }
        int left = 0, right = n - 1, mid;
        // onTheLeft 为真 target 在左边
        boolean onTheLeft = target >= nums[0];
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                // target 和 mid 在同一边
                if (!onTheLeft || nums[mid] >= nums[0]) {
                    left = mid + 1;
                } else {
                    // target 在左边, mid 在右边
                    right = mid - 1;
                }
            } else if (nums[mid] > target) {
                // target 和 mid 在同一边
                if (onTheLeft || nums[mid] < nums[0]) {
                    right = mid - 1;
                } else {
                    // target 在右边, mid 在左边
                    left = mid + 1;
                }
            }
        }
        return -1;
    }
}