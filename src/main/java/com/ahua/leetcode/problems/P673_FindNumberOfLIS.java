package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-15 17:16
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个未排序的整数数组，找到最长递增子序列的个数。
 * 注意: 给定的数组长度不超过 2000 并且结果一定是32位有符号整数。
 */
public class P673_FindNumberOfLIS {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, 5, 4, 7};
        int[] nums1 = new int[]{2, 2, 2, 2, 2};
        int[] nums2 = new int[]{1, 2, 4, 3, 5, 4, 7, 2};
        P673_Solution solution = new P673_Solution();
        System.out.println(solution.findNumberOfLIS(nums)); // 2
        System.out.println(solution.findNumberOfLIS(nums1)); // 5
        System.out.println(solution.findNumberOfLIS(nums2)); // 3
    }
}

// 动态规划
class P673_Solution1 {
    // 动态规划
    public int findNumberOfLIS1(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        // 最长递增子序列的长度
        int maxLen = 1;
        // 最长递增子序列的个数
        int maxNum = 1;
        // 以当前第 i 个数 nums[i] 结尾的最长子序列的长度
        int[] dp = new int[n];
        // 以当前第 i 个数 nums[i] 结尾的最长子序列的个数
        int[] cnt = new int[n];
        // 赋初值: 以 nums[0] 结尾的最长子序列的长度为 1
        dp[0] = 1;
        // 赋初值: 以 nums[0] 结尾的最长子序列的个数为 1
        cnt[0] = 1;
        // 从 i == 1 开始
        for (int i = 1; i < n; i++) {
            // 在 [0,i) 之间找到比当前 nums[i] 小的那个数, 假设有 x 个这样的数
            // 那么 dp[i] 也就等于以这 x 个这样的数结尾的最长子序列长度的最大值 + 1
            // 也就是找到一条 i 之前的最长子序列, 并且该最长子序列的结尾值要 nums[j] < nums[i]
            // 那么现在加上 nums[i] 这个数, 就有了一条以 nums[i] 结尾的最长子序列
            // 同时也知道了其长度值, 更新 dp[i]
            // 那么当循环结束时, 就知道了以这 n 个数结尾的最长子序列的长度, 最长长度就很轻松就能得到

            // 而这最长长度的子序列有多少个呢?
            // 不就是遍历 dp数组, 找到 dp[i] 等于 maxLen 的个数吗?
            // 以上这两行的想法是错误的

            // 以上讨论的是找得到的情况, 如果找不到呢?
            // 找不到不就说明当前 nums[i] 是 [0,i] 之间这 i + 1 个数中的最大值吗?
            // 那么 nums[i] 就成为了一条新的子序列的开头(也即末尾), 当前序列长度就为 1 了
            // 故在查找前, 先令其为 1, 后续如果找到, 肯定值比 1 大

            // 以 nums[i] 结尾的最长子序列的长度为 1
            dp[i] = 1;
            // 以 nums[i] 结尾的最长子序列的个数为 1
            cnt[i] = 1;
            for (int j = 0; j < i; j++) {
                // 如果是非严格单调递增, 改为 <= 即可
                if (nums[j] < nums[i]) {
                    // dp[i] = Math.max(dp[i], dp[j] + 1);
                    // 如果 dp[j] + 1 > dp[i], 说明以 nums[j] 结尾的这条序列的长度更长
                    // 更新此可最大长度 dp[i] 为 dp[j] + 1;
                    // 以 nums[i] 结尾的最长子序列的个数 cnt[i] 也就是 以 nums[j] 结尾的最长子序列的个数 cnt[j];
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        cnt[i] = cnt[j];
                    } else if (dp[j] + 1 == dp[i]) {
                        // 如果又遇到 cnt[j] 条长度为 dp[j](dp[j] == dp[i] - 1) 的最长子序列
                        // 那么说明现在又多了 cnt[j] 条以 nums[j] 结尾的不同序列 加上 nums[i] 能得到
                        // 长度为 dp[i] == dp[j] + 1 的以 nums[i] 结尾的子序列
                        // 更新 cnt[i] 值
                        cnt[i] += cnt[j];
                    }
                }
            }

            // 每次得到以 nums[i] 结尾的最长子序列长度及其序列条数, 就与之前的 maxLen 比较
            // 得到 [0,i] 之间以这 i + 1 个数结尾的最长子序列长度中的最大值以及有当前最长长度 maxLen 的子序列的条数
            // 以 nums[i] 结尾的子序列的长度比曾经的 maxLen 大, 更新 maxLen 为 dp[i]
            // 更新 maxNum 为 cnt[i], 表示当前有最长长度 maxLen 的子序列的条数
            // maxLen = Math.max(maxLen, dp[i]);
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                maxNum = cnt[i];
            } else if (dp[i] == maxLen) {
                // 如果刚好相等, 说明又多了 cnt[j] 种不同方式(不同条序列), 也能得到长度为 maxLen 的子序列
                maxNum += cnt[i];
            }
        }

        // System.out.println(Arrays.toString(dp));
        return maxNum;
    }

    // 动态规划
    // 循环遍历从 0 开始, 有些初值就不需要特殊赋值了
    // 也是 官方写法
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        // 最长递增子序列的长度
        int maxLen = 0;
        // 最长递增子序列的个数
        int maxNum = 0;
        // 以当前第 i 个数 nums[i] 结尾的最长子序列的长度
        int[] dp = new int[n];
        // 以当前第 i 个数 nums[i] 结尾的最长子序列的个数
        int[] cnt = new int[n];
        // 从 i == 0 开始
        for (int i = 0; i < n; i++) {
            // 在 [0,i) 之间找到比当前 nums[i] 小的那个数, 假设有 x 个这样的数
            // 那么 dp[i] 也就等于以这 x 个这样的数结尾的最长子序列长度的最大值 + 1
            // 也就是找到一条 i 之前的最长子序列, 并且该最长子序列的结尾值要 nums[j] < nums[i]
            // 那么现在加上 nums[i] 这个数, 就有了一条以 nums[i] 结尾的最长子序列
            // 同时也知道了其长度值, 更新 dp[i]
            // 那么当循环结束时, 就知道了以这 n 个数结尾的最长子序列的长度, 最长长度就很轻松就能得到

            // 而这最长长度的子序列有多少个呢?
            // 不就是遍历 dp数组, 找到 dp[i] 等于 maxLen 的个数吗?
            // 以上这两行的想法是错误的

            // 以上讨论的是找得到的情况, 如果找不到呢?
            // 找不到不就说明当前 nums[i] 是 [0,i] 之间这 i + 1 个数中的最大值吗?
            // 那么 nums[i] 就成为了一条新的子序列的开头(也即末尾), 当前序列长度就为 1 了
            // 故在查找前, 先令其为 1, 后续如果找到, 肯定值比 1 大

            // 以 nums[i] 结尾的最长子序列的长度为 1
            dp[i] = 1;
            // 以 nums[i] 结尾的最长子序列的个数为 1
            cnt[i] = 1;
            for (int j = 0; j < i; j++) {
                // 如果是非严格单调递增, 改为 <= 即可
                if (nums[j] < nums[i]) {
                    // dp[i] = Math.max(dp[i], dp[j] + 1);
                    // 如果 dp[j] + 1 > dp[i], 说明以 nums[j] 结尾的这条序列的长度更长
                    // 更新此可最大长度 dp[i] 为 dp[j] + 1;
                    // 以 nums[i] 结尾的最长子序列的个数 cnt[i] 也就是 以 nums[j] 结尾的最长子序列的个数 cnt[j];
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        cnt[i] = cnt[j];
                    } else if (dp[j] + 1 == dp[i]) {
                        // 如果又遇到 cnt[j] 条长度为 dp[j](dp[j] == dp[i] - 1) 的最长子序列
                        // 那么说明现在又多了 cnt[j] 条以 nums[j] 结尾的不同序列 加上 nums[i] 能得到
                        // 长度为 dp[i] == dp[j] + 1 的以 nums[i] 结尾的子序列
                        // 更新 cnt[i] 值
                        cnt[i] += cnt[j];
                    }
                }
            }

            // 每次得到以 nums[i] 结尾的最长子序列长度及其序列条数, 就与之前的 maxLen 比较
            // 得到 [0,i] 之间以这 i + 1 个数结尾的最长子序列长度中的最大值以及有当前最长长度 maxLen 的子序列的条数
            // 以 nums[i] 结尾的子序列的长度比曾经的 maxLen 大, 更新 maxLen 为 dp[i]
            // 更新 maxNum 为 cnt[i], 表示当前有最长长度 maxLen 的子序列的条数
            // maxLen = Math.max(maxLen, dp[i]);
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                maxNum = cnt[i];
            } else if (dp[i] == maxLen) {
                // 如果刚好相等, 说明又多了 cnt[j] 种不同方式(不同条序列), 也能得到长度为 maxLen 的子序列
                maxNum += cnt[i];
            }
        }

        // System.out.println(Arrays.toString(dp));
        return maxNum;
    }
}

// 贪心 + 前缀和 + 二分查找
class P673_Solution {
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        // 贪心
        // 维护一个二维数组 d[i] ，表示长度为 i 的最长上升子序列的末尾元素的最小值组成的一个链表
        // 即一个数组的下标为 i 的位置上保存的元素是
        // (长度为 i + 1 的最长上升子序列的)所有(历次遍历得到的)末尾元素组成个一个数组(可理解为保存历史所有的最小的末尾元素的一个链表)
        // 在一个位置上添加的同样长度的上升子序列的所有末尾元素, 其依次添加的 nums值 肯定是非递增的
        List<List<Integer>> d = new ArrayList<>();
        // 相应子序列数量
        List<List<Integer>> cnt = new ArrayList<>();
        // 依次遍历 nums 数组, 将每个num 插入到合适位置, 并且更新以相应元素结尾的最长子序列的条数
        for (int num : nums) {
//            if (d.size() == 0) {
//
//            }
            // 二分查找找到元素 num 应当插入到 d 数组的哪个链表(数组)上, 下标为 i, 对应链表(数组)为  d[i]
            int i = binarySearch1(d, num);
            // 得到 i 后, 由于要插入到 d 的第 i 链表上, 计算 新cnt 时需要计算第 i - 1 链表上该 num 值应该处在哪个位置, 设为 k
            // 即 在长度为 i 的子序列(对应下标是 i - 1 的链表)后插入 num, 需要知道这是可以在下标为 i - 1 的链表中有哪几个元素后可以添加 num
            // 也就是得到长度为 i + 1 的子序列的条数, 当然这只是第 i - 1 条链表上通过添加 num 能得到的子序列(条数)
            // cnt.get(i - 1).get(cnt.get(i - 1).size() - 1) - cnt.get(i - 1).get(k)
            // 后续还得加上本来可能已经存在的长度为 i + 1 的其它子序列条数 cnt.get(i).get(cntSize - 1),
            // 若不存在(即新增最新最大的 i + 1 长度)就加 0

            // diffOfCnt 默认在 i == 0 时为 1, 即插入第一个元素 num 或者在第一条链表上插入 num 时为 1
            // 试想一下, 插入第一个元素 num 或者在第一条链表上插入 num, 不就是插入过少个, 就有多少个长度为 1 的子序列吗?
            int diffOfCnt = 1;
            // 而大于 0 时再通过计算可得
            // 不过在计算 diffOfCnt 前, 得计算第 i - 1 条链表上的比待插入 num 小的所有元素中的最大值的下标, 通过二分查找得到
            if (i > 0) {
                int k = binarySearch2(d.get(i - 1), num);
                diffOfCnt = cnt.get(i - 1).get(cnt.get(i - 1).size() - 1) - cnt.get(i - 1).get(k);
            }
            // 当 i == d.size() 时, 说明要插入新的链表, 有更长的以 num 结尾的子序列了
            if (i == d.size()) {
                // 新建链表, 保存 num
                List<Integer> newDList = new ArrayList<>();
                newDList.add(num);
                d.add(newDList);
                // 新建链表, 保存 num 对应的最长子序列条数
                List<Integer> newCntList = new ArrayList<>();
                newCntList.add(0);
                newCntList.add(diffOfCnt);
                cnt.add(newCntList);
            } else {
                // 在已有的最后一条链表上更新(添加)最小 num
                d.get(i).add(num);
                // cnt 中 i 链表上最后一个元素(即最小元素)对应的目前(未添加 num 前)长度为 i + 1 的上升子序列的个数
                int countOfCntI = cnt.get(i).get(cnt.get(i).size() - 1);
                // countOfCntI + diffOfCnt 就为添加 num 后长度为 i + 1 的上升子序列的个数
                cnt.get(i).add(countOfCntI + diffOfCnt);
            }
        }
        // cnt 的长度, 即最长上升子序列的长度
        int sizeOfCnt = cnt.size();
        // 最长上升子序列的最后一个元素可以是多少个值, 最末尾保存的值即为最长子序列的个数
        int sizeOfLastListOfCnt = cnt.get(sizeOfCnt - 1).size();
        return cnt.get(sizeOfCnt - 1).get(sizeOfLastListOfCnt - 1);
    }

    public int binarySearch1(List<List<Integer>> d, int target) {
        int l = 0, r = d.size();
        while (l < r) {
            int mid = (l + r) / 2;
            List<Integer> list = d.get(mid);
            if (list.get(list.size() - 1) >= target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    public int binarySearch2(List<Integer> list, int target) {
        int l = 0, r = list.size();
        while (l < r) {
            int mid = (l + r) / 2;
            if (list.get(mid) < target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
}