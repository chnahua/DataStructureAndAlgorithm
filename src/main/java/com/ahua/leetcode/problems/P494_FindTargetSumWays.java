package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-14 14:38
 */

/**
 * 给你一个整数数组 nums 和一个整数 target 。
 * <p>
 * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
 * <p>
 * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
 * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
 * <p>
 * 1 <= nums.length <= 20
 * 0 <= nums[i] <= 1000
 * 0 <= sum(nums[i]) <= 1000
 * -1000 <= target <= 1000
 */

public class P494_FindTargetSumWays {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 1, 1, 1};
        int[] nums1 = new int[]{1};
        int[] nums2 = new int[]{1, 2, 1};
        int[] nums3 = new int[]{9, 7, 0, 3, 9, 8, 6, 5, 7, 6};
        P494_Solution solution = new P494_Solution();
        System.out.println(solution.findTargetSumWays(nums, 3)); // 5
        System.out.println(solution.findTargetSumWays(nums1, 1)); // 1
        System.out.println(solution.findTargetSumWays(nums2, 0)); // 2
        System.out.println(solution.findTargetSumWays(nums3, 2)); // 2
    }
}

// 回溯
class P494_Solution1 {
    int[] nums;
    int len;
    int count = 0;

    public int findTargetSumWays(int[] nums, int target) {
        // 可删除
        if (nums == null) {
            return 0;
        }
        this.nums = nums;
        this.len = nums.length;
        // 可删除, 特殊情况, nums 长度为 0,
        // target 也为 0, 返回 1; 不为 0, 返回 0
        if (len == 0) {
            if (target == 0) {
                return 1;
            }
            return 0;
        }
        // 特殊情况, nums 长度为 1
        if (len == 1) {
            if (nums[0] == target || nums[0] == -target) {
                return 1;
            }
            return 0;
        }

        dfs(0, 0, target);
        return count;
    }

    public void dfs(int index, int sum, int target) {
        // 结束
        if (index == len) {
            // 相等, 找到一个表达式
            if (sum == target) {
                count++;
            }
            return;
        }

        // 两个分支
        // 前 index 个数组成的表达式子的和 sum
        // 在此处可能加上或者减去 nums[index]
        dfs(index + 1, sum + nums[index], target);
        dfs(index + 1, sum - nums[index], target);
    }
}

// 动态规划 二维 dp 数组
class P494_Solution2 {
    public int findTargetSumWays(int[] nums, int target) {
        // 可删除
        if (nums == null) {
            return 0;
        }
        int len = nums.length;
        // 可删除, 特殊情况, nums 长度为 0,
        // target 也为 0, 返回 1; 不为 0, 返回 0
        if (len == 0) {
            if (target == 0) {
                return 1;
            }
            return 0;
        }
        // 特殊情况, nums 长度为 1
        if (len == 1) {
            if (nums[0] == target || nums[0] == -target) {
                return 1;
            }
            return 0;
        }

        // 数组和
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        int diff = sum - target;
        if (diff < 0 || diff % 2 != 0) {
            return 0;
        }

        // 将问题转换为在 nums 数组中找到几个数的和为 neg 的方案数
        int neg = diff / 2;

        // 动态规划数组
        // 行 i 表示有 i 个数
        // 列 j 表示在这 i 个数中找到若干个数的和为 j 的方案数
        int[][] dp = new int[len + 1][neg + 1];

        // 0 <= nums[i] <= 1000
        // 赋初值
        dp[0][0] = 1;
        for (int i = 1; i < len + 1; i++) {
            for (int j = 0; j < neg + 1; j++) {
                // 如果当前的第 i 个数 nums[i - 1] 比 j 大, 则第 i 个数 nums[i - 1] 不能被选,
                // 那么此次 dp[i][j] 的值 仍然是在前 i - 1 个数中选择若干个数的和为 j 的方案数, 其值为 dp[i - 1][j]
                if (nums[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // 如果当前的第 i 个数 nums[i - 1] 比 j 小或者相等, 则第 i 个数 nums[i - 1] 可能被选,
                    // 那么此时 dp[i][j] 的值就等于选择或者不选择 这第 i 个数 nums[i - 1] 的两种可能的方案数的和
                    // 如果不选, 就是在前 i - 1 个数中选择若干个数的和为 j 的方案数, 其值为 dp[i - 1][j]
                    // 如果选, 就是在前 i - 1 个数中选择若干个数的和为 j - nums[i - 1] 的方案数 dp[i - 1][j - nums[i - 1]]
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        for (int i = 1; i < len + 1; i++) {
            for (int j = 0; j < neg + 1; j++) {
                // 历史: 共有 i - 1 个数时, 找到若干个数的和为 j 的方案数
                // 先令 dp[i][j] 为在前 i - 1 个数中选择若干个数的和为 j 的方案数(dp[i - 1][j]), 即不选择这第 i 个数 nums[i - 1]
                dp[i][j] = dp[i - 1][j];
                // 如果 j >= nums[i - 1], 说明可以选择这个数了, 就再加上这种情况的方案数 dp[i - 1][j - nums[i - 1]]
                if (nums[i - 1] <= j) {
                    dp[i][j] += dp[i - 1][j - nums[i - 1]];
                }
            }
        }

        return dp[len][neg];
    }
}

// 动态规划 一维 dp 数组(滚动数组思想)
class P494_Solution {
    public int findTargetSumWays(int[] nums, int target) {
        // 可删除
        if (nums == null) {
            return 0;
        }
        int len = nums.length;
        // 可删除, 特殊情况, nums 长度为 0,
        // target 也为 0, 返回 1; 不为 0, 返回 0
        if (len == 0) {
            if (target == 0) {
                return 1;
            }
            return 0;
        }
        // 特殊情况, nums 长度为 1
        if (len == 1) {
            if (nums[0] == target || nums[0] == -target) {
                return 1;
            }
            return 0;
        }

        // 数组和
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        int diff = sum - target;
        if (diff < 0 || diff % 2 != 0) {
            return 0;
        }

        // 将问题转换为在 nums 数组中找到几个数的和为 neg 的方案数
        int neg = diff / 2;

        // 动态规划数组
        // 列 j 表示在这 i 个数中找到若干个数的和为 j 的方案数
        int[] dp = new int[neg + 1];

        // 0 <= nums[i] <= 1000
        // 赋初值
        dp[0] = 1;

        // 需要仔细思考, 相比较于正向遍历, 反向遍历的循环次数更少
        // 并且正向遍历会出错, 因为 j 更大时会用到之前一次 i 循环中 j 更小时的一些数据
        // 如果循环是正向从前往后遍历的, 那么在遍历到更大的 j 时, dp[j - nums[i - 1]] 可能已经在此次 i 循环中已经被更改了,
        // 而不是上一次 i - 1 循环结束时保存的值
        for (int num : nums) {
            for (int j = neg; j >= num; j--) {
                // 此次进入循环时 dp[j] 为在前 i - 1 个数中选择若干个数的和为 j 的方案数(dp[j]), 即不选择这第 i 个数 nums[i - 1]
                // 并且此时肯定 j >= nums[i - 1], 说明可以选择这个数了, 就再加上这种情况的方案数 dp[j - nums[i - 1]]
                dp[j] += dp[j - num];
            }
        }
        return dp[neg];
    }
}