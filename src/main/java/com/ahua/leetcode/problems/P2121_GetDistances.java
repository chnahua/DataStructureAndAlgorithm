package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-26 20:46
 */

import java.util.*;

/**
 * 2121. 相同元素的间隔之和 intervals-between-identical-elements
 * 给你一个下标从 0 开始、由 n 个整数组成的数组 arr 。
 * <p>
 * arr 中两个元素的 间隔 定义为它们下标之间的 绝对差 。更正式地，arr[i] 和 arr[j] 之间的间隔是 |i - j| 。
 * <p>
 * 返回一个长度为 n 的数组 intervals ，其中 intervals[i] 是 arr[i] 和 arr 中每个相同元素（与 arr[i] 的值相同）的 间隔之和 。
 * <p>
 * 注意：|x| 是 x 的绝对值。
 * <p>
 * n == arr.length
 * 1 <= n <= 10^5
 * 1 <= arr[i] <= 10^5
 */
public class P2121_GetDistances {
    public static void main(String[] args) {
        int[] arr = new int[]{2, 1, 3, 1, 2, 3, 3};
        int[] arr1 = new int[]{10, 5, 10, 10};
        P2121_Solution solution = new P2121_Solution();
        System.out.println(Arrays.toString(solution.getDistances(arr))); // [4,2,7,2,4,4,5]
        System.out.println(Arrays.toString(solution.getDistances(arr1))); // [5,0,3,4]
    }
}

// 超时, 类似于我的那道面试题的做法
// O(N^2)
class P2121_Solution1 {
    public long[] getDistances1(int[] arr) {
        int n = arr.length;
        long[] intervals = new long[n];
        Map<Integer, ArrayList<Integer>> hashMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (!hashMap.containsKey(arr[i])) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                hashMap.put(arr[i], list);
            } else {
                ArrayList<Integer> list = hashMap.get(arr[i]);
                for (int index : list) {
                    intervals[index] += i - index;
                    intervals[i] += i - index;
                }
                list.add(i);
            }
        }
        return intervals;
    }

    // 修改代码实现逻辑, 本质不变
    public long[] getDistances(int[] arr) {
        int n = arr.length;
        long[] intervals = new long[n];
        Map<Integer, ArrayList<Integer>> hashMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            // 获取元素对应索引链表
            ArrayList<Integer> indexList = hashMap.get(arr[i]);
            // indexList == null 即 hashMap 不包含此元素
            if (indexList == null) {
                // 将该键值对(元素,索引链表)添加进 hashMap 中, 由于是第一次添加, 还需要新建链表
                indexList = new ArrayList<>();
                indexList.add(i);
                hashMap.put(arr[i], indexList);
            } else {
                // 两个位置都要加上这个间隔值
                for (int index : indexList) {
                    intervals[index] += i - index;
                    intervals[i] += i - index;
                }
                // 最后将该索引加入链表
                indexList.add(i);
            }
        }
        return intervals;
    }
}

// 哈希表 + 前缀后缀和
// O(N) = 2N
// 46 ms 87.95%
// O(N)
// 64.4 M 83.89%
class P2121_Solution {
    public long[] getDistances(int[] arr) {
        int n = arr.length;
        long[] intervals = new long[n];

        // 前缀和
        long[] prefix = new long[n];
        // value 值 indexAndCount[] 的长度为 2
        // indexAndCount[0] 为与当前元素相同的前一个元素的下标
        // indexAndCount[1] 为与当前元素相同的元素总共出现的次数
        Map<Integer, int[]> hashMap1 = new HashMap<>();
        for (int i = 0; i < n; i++) {
            // 获取 key 对应 value
            int[] indexAndCount = hashMap1.get(arr[i]);
            // indexList == null 即 hashMap 不包含此元素, 需要创建该元素 key 对应的 value 数组
            if (indexAndCount == null) {
                // 将该键值对(元素, 数组)添加进 hashMap 中, 由于是第一次添加, 还需要新建数组
                indexAndCount = new int[]{i, 1};
                hashMap1.put(arr[i], indexAndCount);
            } else {
                // prefix[i] 表示的是 arr[i] 与左边与之相同的元素的间隔和
                // 通过前一个与之相同的元素处的 prefix[] 和 arr[i] 总出现次数计算得到
                prefix[i] += prefix[indexAndCount[0]] + (long) (i - indexAndCount[0]) * indexAndCount[1];
                // 更新 indexAndCount
                indexAndCount[0] = i;
                indexAndCount[1]++;
            }
        }
        // 后缀和
        long[] suffix = new long[n];
        Map<Integer, int[]> hashMap2 = new HashMap<>();
        for (int i = n - 1; i >= 0; i--) {
            // 获取 key 对应 value
            int[] indexAndCount = hashMap2.get(arr[i]);
            // indexList == null 即 hashMap 不包含此元素, 需要创建该元素 key 对应的 value 数组
            if (indexAndCount == null) {
                // 将该键值对(元素, 数组)添加进 hashMap 中, 由于是第一次添加, 还需要新建数组
                indexAndCount = new int[]{i, 1};
                hashMap2.put(arr[i], indexAndCount);
            } else {
                // suffix[i] 表示的是 arr[i] 与右边与之相同的元素的间隔和
                // 通过前一个与之相同的元素处的 suffix[] 和 arr[i] 总出现次数计算得到
                suffix[i] += suffix[indexAndCount[0]] + (long) (indexAndCount[0] - i) * indexAndCount[1];
                // 更新 indexAndCount
                indexAndCount[0] = i;
                indexAndCount[1]++;
            }
            // 可将最后的结果计算赋值提前到此处
            intervals[i] = prefix[i] + suffix[i];
        }
//        // 结果计算
//        for (int i = 0; i < n; i++) {
//            intervals[i] = prefix[i] + suffix[i];
//        }
        return intervals;
    }
}