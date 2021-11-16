package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-15 18:38
 */

/**
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 * <p>
 * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 * <p>
 * 1 <= nums.length <= 2500
 * -10^4 <= nums[i] <= 10^4
 */
public class P300_LengthOfLIS {
    public static void main(String[] args) {
        int[] nums = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        int[] nums1 = new int[]{0, 1, 0, 3, 2, 3};
        int[] nums2 = new int[]{7, 7, 7, 7, 7, 7, 7};
        int[] nums3 = new int[]{4, 10, 4, 3, 8, 9};
        int[] nums4 = new int[]{0, 1, 0, 3, 2, 3};
        int[] nums5 = new int[]{3, 5, 6, 2, 5, 4, 19, 5, 6, 7, 12};
        P300_Solution solution = new P300_Solution();
        System.out.println(solution.lengthOfLIS(nums)); // 4
        System.out.println(solution.lengthOfLIS(nums1)); // 4
        System.out.println(solution.lengthOfLIS(nums2)); // 1
        System.out.println(solution.lengthOfLIS(nums3)); // 3
        System.out.println(solution.lengthOfLIS(nums4)); // 4
        System.out.println(solution.lengthOfLIS(nums5)); // 6
    }
}

class P300_Solution {
    // 动态规划
    // 时间复杂度：O(n^2) 空间复杂度：O(n)
    // 内层 for 循环我犯过一次错误, 以为在 nums[i] 前找到第一个比 nums[i] 小的值的 dp 就行了
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        // 最长递增子序列的长度, 初值为 0 为 1 均可
        int maxLen = 1;
        // 以当前第 i 个数 nums[i] 结尾的最长子序列的长度
        int[] dp = new int[n];
        // 赋初值: 以 nums[0] 结尾的最长子序列的长度为 1
        dp[0] = 1;
        // 从 i == 1 开始
        for (int i = 1; i < n; i++) {
            // 在 [0,i) 之间找到比当前 nums[i] 小的那个数, 假设有 x 个这样的数
            // 那么 dp[i] 也就等于以这 x 个这样的数结尾的最长子序列长度的最大值 + 1
            // 也就是找到一条 i 之前的最长子序列, 并且该最长子序列的结尾值要 nums[j] < nums[i]
            // 那么现在加上 nums[i] 这个数, 就有了一条以 nums[i] 结尾的最长子序列
            // 同时也知道了其长度值, 更新 dp[i]
            // 那么当循环结束时, 就知道了以这 n 个数结尾的最长子序列的长度, 最长长度就很轻松就能得到

            // 以上讨论的是找得到的情况, 如果找不到呢?
            // 找不到不就说明当前 nums[i] 是 [0,i] 之间这 i + 1 个数中的最大值吗?
            // 那么 nums[i] 就成为了一条新的子序列的开头(也即末尾), 当前序列长度就为 1 了
            // 故在查找前, 先令其为 1, 后续如果找到, 肯定值比 1 大
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    // 如果是非严格单调递增, 改为 <= 即可
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            // 每次得到以 nums[i] 结尾的最长子序列长度, 就与之前的 maxLen 比较
            // 得到 [0,i] 之间以这 i + 1 个数结尾的最长子序列长度中的最大值
            maxLen = Math.max(maxLen, dp[i]);
        }
        // System.out.println(Arrays.toString(dp));
        return maxLen;
    }

    // 贪心 + 二分查找
    // 时间复杂度：O(nlogn) 空间复杂度：O(n)
    public int lengthOfLIS1(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        // 贪心
        // 维护一个数组 d[i] ，表示长度为 i 的最长上升子序列的末尾元素的最小值，
        // 用 len 记录目前最长上升子序列的长度，
        // 起始时 len 为 1，d[1] = nums[0]
        // d[0] 位置不管
        int[] d = new int[n + 1];
        // 赋初值
        int len = 1;
        d[len] = nums[0];
        for (int i = 1; i < n; i++) {
            // nums[i] 比当前最长子序列的最后一个元素都大, 将其添加在末尾 d[len + 1]
            if (nums[i] > d[len]) {
                d[len + 1] = nums[i];
                len++;
                continue;
            }
            // 二分查找
            int left = 1;
            int right = len;
            int mid = 0;
            while (left <= right) {
                mid = (left + right) / 2;
                if (nums[i] > d[mid]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            // 两种赋值方式选其一
/*
            // 1.
            // 此时, left == mid
            if (right < mid) {
                d[mid] = nums[i];
            } else {
                // 此时, left == mid + 1
                d[mid + 1] = nums[i];
            }
*/
            // 2. 算是第一种的归纳总结
            // 故最终
            // 这个结束赋值极其重要, 到现在其实都不太明白, while 结束后应该在哪一个位置赋值
            // 这个类似插入位置之前在 P35 遇到过
            d[left] = nums[i];
        }
        return len;
    }
}