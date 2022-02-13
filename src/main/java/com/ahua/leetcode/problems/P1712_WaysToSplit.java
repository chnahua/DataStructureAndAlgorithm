package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-13 20:17
 */

/**
 * 1712. 将数组分成三个子数组的方案数 ways-to-split-array-into-three-subarrays
 * 我们称一个分割整数数组的方案是 好的 ，当它满足：
 * <p>
 * 数组被分成三个 非空 连续子数组，从左至右分别命名为 left ， mid ， right 。
 * left 中元素和小于等于 mid 中元素和，mid 中元素和小于等于 right 中元素和。
 * 给你一个 非负 整数数组 nums ，请你返回 好的 分割 nums 方案数目。
 * 由于答案可能会很大，请你将结果对 10^9 + 7 取余后返回。
 * <p>
 * 3 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^4
 */
public class P1712_WaysToSplit {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 1};
        int[] nums1 = new int[]{1, 2, 2, 2, 5, 0};
        int[] nums2 = new int[]{3, 2, 1};
        int[] nums3 = new int[]{8892, 2631, 7212, 1188, 6580, 1690, 5950, 7425, 8787, 4361,
                9849, 4063, 9496, 9140, 9986, 1058, 2734, 6961, 8855, 2567,
                7683, 4770, 40, 850, 72, 2285, 9328, 6794, 8632, 9163,
                3928, 6962, 6545, 6920, 926, 8885, 1570, 4454, 6876, 7447,
                8264, 3123, 2980, 7276, 470, 8736, 3153, 3924, 3129, 7136,
                1739, 1354, 661, 1309, 6231, 9890, 58, 4623, 3555, 3100,
                3437};
        P1712_Solution solution = new P1712_Solution();
        System.out.println(solution.waysToSplit(nums)); // 1
        /*
        nums 总共有 3 种好的分割方案：
        [1] [2] [2,2,5,0]
        [1] [2,2] [2,5,0]
        [1,2] [2,2] [5,0]
         */
        System.out.println(solution.waysToSplit(nums1)); // 3
        System.out.println(solution.waysToSplit(nums2)); // 0
        System.out.println(solution.waysToSplit(nums3)); // 227
    }
}

// 超时做法 O(N^2)
class P1712_Solution1 {
    // 超时 O(N^2)
    public int waysToSplit1(int[] nums) {
        int ans = 0;
        int n = nums.length;
        int[] pre = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] + nums[i];
        }
        for (int left = 0; left <= n - 3; left++) {
            // for (int right = n - 2; right > left; right--) {
            for (int right = left + 1; right <= n - 2; right++) {
                int leftSum = pre[left + 1];
                int midSum = pre[right + 1] - leftSum;
                int rightSum = pre[n] - pre[right + 1];
                if (leftSum <= midSum && midSum <= rightSum) {
                    System.out.println("left = " + left + ", right = " + right);
                    // ans++;
                    ans = (ans + 1) % 1000000007;
                }
            }

        }
        return ans;
    }

    // 超时 O(N^2)
    public int waysToSplit2(int[] nums) {
        int ans = 0;
        int n = nums.length;
        int[] pre = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] + nums[i];
        }
        int firstRight = 1;
        for (int left = 0; left <= n - 3; left++) {
            // 错误语句
//            if (firstRight == -1) {
//                break;
//            }
            int right = firstRight;
            firstRight = -1;
            int leftSum = pre[left + 1];
            // for (int right = n - 2; right > left; right--) {
            for (; right <= n - 2; right++) {
                int midSum = pre[right + 1] - leftSum;
                int rightSum = pre[n] - pre[right + 1];
                if (leftSum <= midSum && midSum <= rightSum) {
                    System.out.println("left = " + left + ", right = " + right);
                    // ans++;
                    ans = (ans + 1) % 1000000007;
                    if (firstRight == -1) {
                        firstRight = right;
                    }
                }
            }
        }
        return ans;
    }
}

// 前缀和 + 二分查找
// O(N log N)
// 60 ms 20.10%
class P1712_Solution {
    public int waysToSplit(int[] nums) {
        final int mod = 1000000007;
        int ans = 0;
        int n = nums.length;
        // 前缀和
        int[] pre = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] + nums[i];
        }

        // 上一次 right 的最小值 minRight(minRight 要保证 >= left + 1)
        int lastMinRight = 1;
        int minRight = 1, maxRight;
        for (int left = 0; left <= n - 3; left++) {
            // 不能直接让 lastMinRight 直接等于 上次的 minRight, 因为有可能 left>=minRight （比如全 0 测试用例）
            lastMinRight = minRight == Integer.MAX_VALUE ? Math.max(left + 1, lastMinRight) : Math.max(left + 1, minRight);
            // right 的最小值 minRight
            minRight = minRightBinarySearch(pre, pre[left + 1], lastMinRight, n - 2);
            // 此时不能切割(没有方案数), 直接下次循环
            if (minRight == Integer.MAX_VALUE) {
                continue;
            }
            // right 的最大值 maxRight
            maxRight = maxRightBinarySearch(pre, pre[left + 1], lastMinRight, n - 2);
            // 方案数 maxRight - minRight + 1
            ans = (ans + maxRight - minRight + 1) % mod;
            System.out.println("left = " + left + ", minRight = " + minRight + ", maxRight = " + maxRight);
        }
        return ans;
    }

    // 右边最小值
    private int minRightBinarySearch(int[] pre, int leftSum, int left, int right) {
        int n = pre.length;
        int ans = Integer.MAX_VALUE;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 中间部分子数组和
            int midSum = pre[mid + 1] - leftSum;
            // 右边部分子数组和
            int rightSum = pre[n - 1] - pre[mid + 1];
            if (midSum >= leftSum) {
                if (rightSum >= midSum) {
                    // 左边找最小
                    ans = Math.min(ans, mid);
                    right = mid - 1;
                } else {
                    // 左边找最小
                    right = mid - 1;
                }
            } else {
                if (rightSum >= midSum) {
                    // 右边找最小
                    left = mid + 1;
                } else {
                    // 找不到了呀
                    break;
                }
            }
        }
        return ans;
    }

    // 右边最大值
    private int maxRightBinarySearch(int[] pre, int leftSum, int left, int right) {
        int n = pre.length;
        int ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 中间部分子数组和
            int midSum = pre[mid + 1] - leftSum;
            // 右边部分子数组和
            int rightSum = pre[n - 1] - pre[mid + 1];
            if (midSum >= leftSum) {
                if (rightSum >= midSum) {
                    // 右边找最大
                    ans = Math.max(ans, mid);
                    left = mid + 1;
                } else {
                    // 左边找最大
                    right = mid - 1;
                }
            } else {
                if (rightSum >= midSum) {
                    // 右边找最大
                    left = mid + 1;
                } else {
                    // 找不到了呀
                    break;
                }
            }
        }
        return ans;
    }
}
