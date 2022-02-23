package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-23 22:38
 */

/**
 * 945. 使数组唯一的最小增量 minimum-increment-to-make-array-unique
 * 给你一个整数数组 nums 。每次 move 操作将会选择任意一个满足 0 <= i < nums.length 的下标 i，并将 nums[i] 递增 1。
 * <p>
 * 返回使 nums 中的每个值都变成唯一的所需要的最少操作次数。
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^5
 */
public class P945_MinIncrementForUnique {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 2};
        int[] nums1 = new int[]{3, 2, 1, 2, 1, 7};
        int[] nums2 = new int[]{0, 7, 4, 9, 4, 0, 4, 7, 8, 0, 1, 4, 1, 8, 9};
        P945_Solution solution = new P945_Solution();
        System.out.println(solution.minIncrementForUnique(nums)); // 1
        System.out.println(solution.minIncrementForUnique(nums1)); // 6 [3, 4, 1, 2, 5, 7]
        System.out.println(solution.minIncrementForUnique(nums2)); // 39
    }
}

// 计数
class P945_Solution {
    static final int NUM_MAX = 100001;

    // O(N) + 2*O(NUM_MAX)
    // O(NUM_MAX)
    // 15 ms 89.24%
    // 54.4 MB 5.23%
    public int minIncrementForUnique1(int[] nums) {
        int n = nums.length;
        // 各个 num 的个数
        int[] count = new int[NUM_MAX];
        for (int num : nums) {
            count[num]++;
        }
        // System.out.println(Arrays.toString(count));
        // 通过 total 数控制循环次数, 最大为 num 的个数 n
        int total = 0;
        // 转换次数
        int action = 0;
        // 要变成的那个数
        int toNum = 0;
        for (int fromNum = 0; fromNum < NUM_MAX && total <= n; fromNum++) {
            while (count[fromNum] > 1) {
                toNum = Math.max(toNum, fromNum + 1);
                // toNum 小于最大值 NUM_MAX 时, 从这个 for 循环中找到将 fromNum 转变为哪个 toNum,
                // toNum 的满足要求为 count[toNum] == 0, 即表示将一个 fromNum 变换为 toNum,
                // count 相应位置处 toNum 的个数加 1, 变换次数为两者差值
                for (; toNum < NUM_MAX; toNum++) {
                    if (count[toNum] == 0) {
                        action += toNum - fromNum;
                        count[toNum]++;
                        break;
                    }
                }
                // toNum 超过了最大值 NUM_MAX 后, 就不再走上面那个 for 循环计算转换次数了
                if (toNum >= NUM_MAX) {
                    action += toNum - fromNum;
                    toNum++;
                }
                count[fromNum]--;
            }
            // 遍历了多少个数
            total += count[fromNum];
        }
        return action;
    }

    // 构建 maxNum + 1 大小的数组, 而不是 NUM_MAX 大小的数组
    // 也就不需要 total 来控制循环条件
    // 2*O(N) + 2*O(maxNum)
    // O(maxNum)
    // 7 ms 94.48%
    // 50 MB 72.96%
    public int minIncrementForUnique(int[] nums) {
        // nums 中的最大值
        int maxNum = 0;
        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        // 各个 num 的个数, 构建 maxNum + 1 大小的数组, 而不是 NUM_MAX 大小的数组
        int[] count = new int[maxNum + 1];
        for (int num : nums) {
            count[num]++;
        }
        // System.out.println(Arrays.toString(count));
        // 转换次数
        int action = 0;
        // 要变成的那个数
        int toNum = 0;
        for (int fromNum = 0; fromNum <= maxNum; fromNum++) {
            while (count[fromNum] > 1) {
                toNum = Math.max(toNum, fromNum + 1);
                // toNum 小于等于最大值 maxNum 时, 从这个 for 循环中找到将 fromNum 转变为哪个 toNum,
                // toNum 的满足要求为 count[toNum] == 0, 即表示将一个 fromNum 变换为 toNum,
                // count 相应位置处 toNum 的个数加 1, 变换次数为两者差值
                for (; toNum <= maxNum; toNum++) {
                    if (count[toNum] == 0) {
                        action += toNum - fromNum;
                        count[toNum]++;
                        break;
                    }
                }
                // toNum 大于了最大值 maxNum 后, 就不再走上面那个 for 循环计算转换次数了
                if (toNum > maxNum) {
                    action += toNum - fromNum;
                    toNum++;
                }
                count[fromNum]--;
            }
        }
        return action;
    }
}
//        TreeMap<Integer, Integer> numCount = new TreeMap<>();
//        for (int num : nums) {
//            numCount.put(num, numCount.getOrDefault(num, 0) + 1);
//        }