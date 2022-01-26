package com.ahua.exam.huawei;

import java.util.*;

/**
 * @author huajun
 * @create 2022-01-25 17:49
 */

public class ODInterview_I_CD_2022_01_25 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 2, 3, 4};
        int[] nums1 = new int[]{4, 6, 7, 4, -1, -3, 4};
        int[] nums2 = new int[]{1, 2, 3, 4};
        ODInterview_I_CD_2022_01_25_Solution solution = new ODInterview_I_CD_2022_01_25_Solution();
        System.out.println(Arrays.toString(solution.func(nums)));
        System.out.println(Arrays.toString(solution.func(nums1)));
        System.out.println(Arrays.toString(solution.func(nums2)));
    }
}

/*
 * 此题大致是求
 * 计算一个数组中两个相同元素之间的所有元素（包括这两个元素）组成的这个序列的和
 * 求出所有的这种序列（首尾元素相等）的所有元素的和的最大值
 * 最后返回这个最大值的序列的首尾的这两个相同元素的下标 i j 组成的数组 [i,j]
 * 如果有多个 i 或者 j 都满足, 则先满足 i 最小, i 相同时再满足 j 最小
 * 如果没有这样的两个下标, 返回 空数组
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 例 1
 * 输入 nums = [1, 2, 3, 2, 3, 4]
 * 输出 [2,4]
 * 解释 数组中有两个为 2 的元素, 他们两之间的序列和为 2+3+2 = 7 对应下标 [1,3]
 *     数组中有两个为 3 的元素, 他们两之间的序列和为 3+2+3 = 8 对应下标 [2,4]
 *     最后返回 [2,4]
 *
 * 例 2
 * 输入 nums = [4, 6, 7, 4, -1, -3, 4]
 * 输出 [0,3]
 * 解释 数组中有三个为 4 的元素, 下标分别为 0, 3, 6
 *     [0,3] 之间的序列和为 4+6+7+4 = 21
 *     [0,6] 之间的序列和为 4+6+7+4-1-3+4 = 21
 *     [3,6] 之间的序列和为 4-1-3+4 = 4
 *     可以看到有两个序列和都为 21, 此时返回的两个下标需要满足 i j 都要较小的那一组（首先满足 i 更小, 再满足 j 更小）
 *     最后返回 [0,3]
 *
 * 例 3
 * 输入 nums = [1, 2, 3, 4]
 * 输出 []
 * 解释 数组中没有两个相同的元素, 返回空数组
 */
// 视频面试时写的代码(未作修改, 只重命名了类名、方法名, 删除了部分冗余注释, 和添加大致是哪里错了的注释)
class ODInterview_I_CD_2022_01_25_Solution1 {
    public int[] func(int[] nums) {
        int n = nums.length;
        int[] pre = new int[n + 1];
        HashMap<Integer, ArrayList<Integer>> hashMap = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + nums[i - 1];
            if (hashMap.containsKey(nums[i - 1])) {
                hashMap.get(nums[i - 1]).add(i - 1);
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i - 1);
                hashMap.put(nums[i - 1], list);
            }
        }

        int firstIndex = -1;
        int secondIndex = -1;
        Set<Integer> keySet = hashMap.keySet();
        for (Integer num : keySet) {
            ArrayList<Integer> list = hashMap.get(num);
            int size = list.size();
            if (size >= 2) {
                // 以及 max 应该放到上一层 for 外
                int max = Integer.MIN_VALUE;
                for (int i = 0; i < size - 1; i++) {
                    int temp = list.get(i);
                    for (int j = i + 1; j < size; j++) {
                        int compare = pre[list.get(j) + 1] - pre[temp + 1] + nums[temp];
                        if (max < pre[list.get(j) + 1] - pre[temp + 1] + nums[temp]) {
                            max = compare;
                            firstIndex = temp;
                            // 错误就在这个赋值
                            secondIndex = j;
                        }
                    }
                }
            }
        }
        if (firstIndex == -1) {
            return new int[]{};
        }
        return new int[]{firstIndex, secondIndex};
    }
}

// 按照面试时的思路将此问题尽量解决了, 因为没有更多的测试用例, 所以可能还是会有错
// O(N^3)
class ODInterview_I_CD_2022_01_25_Solution2 {
    public int[] func(int[] nums) {
        int n = nums.length;
        // 前缀和数组
        int[] pre = new int[n + 1];
        // HashMap 保存元素及其对应的下标位置, 有可能同一个值会重复出现, 故需要保存为一个数组或链表
        HashMap<Integer, ArrayList<Integer>> hashMap = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            // 前缀和赋值
            pre[i] = pre[i - 1] + nums[i - 1];
            // 保存元素及其下标
            // 元素不是第一次出现时直接添加该元素的对应下标到该链表
            if (hashMap.containsKey(nums[i - 1])) {
                hashMap.get(nums[i - 1]).add(i - 1);
            } else {
                // 元素第一次出现时需要先创建链表
                ArrayList<Integer> list = new ArrayList<>();
                // 并将该元素对应下标添加到链表中
                list.add(i - 1);
                // 最后添加该键值对进 hashMap
                hashMap.put(nums[i - 1], list);
            }
        }

        // 最终 firstIndex 为返回的第一个位置 i
        int firstIndex = -1;
        // 最终 secondIndex 为返回的第二个位置 j
        int secondIndex = -1;
        // 最终 maxSum 为 i j 之间(包括i j)所有元素和中的最大值
        int maxSum = Integer.MIN_VALUE;
        // 遍历 hashMap 的 key, 即原 nums 数组中出现的各个元素值, 得到他们的下标, 通过在两个下标位置处的前缀和计算出两个下标之间的最大值
        // 遍历完所有的下标后就能够得到两下标之间(包括这两个下标)的所有元素和
        Set<Integer> keySet = hashMap.keySet();
        for (Integer num : keySet) {
            // 获取保存当前元素 num 的所有对应下标的链表
            ArrayList<Integer> list = hashMap.get(num);
            int size = list.size();
            // 长度大于 2 时才符合要求
            if (size >= 2) {
                for (int i = 0; i < size - 1; i++) {
                    // 第一个下标 head
                    int head = list.get(i);
                    for (int j = i + 1; j < size; j++) {
                        // 下标 head 与下标 j 之间的元素和 curSum (通过)
                        int curSum = pre[list.get(j) + 1] - pre[head + 1] + nums[head];
                        // 如果此次的元素和比 maxSum 更大, 则更新; 等于或者小于都不做操作, 这样就能够保证题目中 i,j 均是较小值
                        if (maxSum < curSum) {
                            maxSum = curSum;
                            firstIndex = head;
                            // 我面试时做错的地方就在这里, 赋值成了 j, 应该是 list.get(j) 才对
                            secondIndex = list.get(j);
                        }
                    }
                }
            }
        }
        // 最后判断 firstIndex 或者 secondIndex 是否更新过, 若没有更新过, 说明 nums 中不存在两个相同的元素, 返回为 空数组
        if (firstIndex == -1) {
            return new int[]{};
        }
        return new int[]{firstIndex, secondIndex};
    }
}

// 此次优化时间复杂度, 基本思路没变, 因为没有更多的测试用例, 所以可能还是会有错
// 可以在初始遍历整个数组求前缀和数组 pre 时就能判断求 maxSum 和 firstIndex 和 secondIndex
// O(N^2)
class ODInterview_I_CD_2022_01_25_Solution {
    public int[] func(int[] nums) {
        int n = nums.length;
        // 最终 firstIndex 为返回的第一个位置 i
        int firstIndex = -1;
        // 最终 secondIndex 为返回的第二个位置 j
        int secondIndex = -1;
        // 最终 maxSum 为 i j 之间(包括i j)所有元素和中的最大值
        int maxSum = Integer.MIN_VALUE;
        // 前缀和数组
        int[] pre = new int[n + 1];
        // HashMap 保存元素及其对应的下标位置, 有可能同一个值会重复出现, 故需要保存为一个数组或链表
        HashMap<Integer, ArrayList<Integer>> hashMap = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            // 1.前缀和赋值
            pre[i] = pre[i - 1] + nums[i - 1];
            // 2.保存元素及其下标
            // 条件为 true, 说明当前元素曾经并未添加过, 没有相应的键值对
            // 元素第一次出现时不用判断 maxSum, 因为当前才是第一次添加该元素
            if (!hashMap.containsKey(nums[i - 1])) {
                // 2.1.元素第一次出现时需要先创建链表
                ArrayList<Integer> list = new ArrayList<>();
                // 2.2.并将该元素对应下标添加到链表中
                list.add(i - 1);
                // 2.3.最后添加该键值对进 hashMap
                hashMap.put(nums[i - 1], list);
            } else {
                // 元素不是第一次出现时需要添加该元素的对应下标到该链表
                // 添加该元素的对应下标到该链表前, 可以先遍历已经添加在链表中的当前元素的各个下标, 比较得到 maxSum

                // 2.1.获取保存当前元素 nums[i - 1] 的相同值的所有对应下标的链表 list
                ArrayList<Integer> list = hashMap.get(nums[i - 1]);
                // 2.2.遍历比较更新 maxSum firstIndex secondIndex
                // head 为当前元素 nums[i - 1] 的下标
                for (int head : list) {
                    // 得到下标 head 与下标 (i-1) 之间的元素和 curSum
                    int curSum = pre[i] - pre[head + 1] + nums[head];
                    // 如果此次的元素和比 maxSum 更大, 则更新; 等于或者小于都不做操作, 这样就能够保证题目中 i,j 均是较小值
                    if (maxSum < curSum) {
                        maxSum = curSum;
                        firstIndex = head;
                        secondIndex = i - 1;
                    }
                }
                // 2.3 最后才添加该下标 i-1 到链表
                list.add(i - 1);
            }
        }
        // 最后判断 firstIndex 或者 secondIndex 是否更新过, 若没有更新过, 说明 nums 中不存在两个相同的元素, 返回为 空数组
        if (firstIndex == -1) {
            return new int[]{};
        }
        return new int[]{firstIndex, secondIndex};
    }
}