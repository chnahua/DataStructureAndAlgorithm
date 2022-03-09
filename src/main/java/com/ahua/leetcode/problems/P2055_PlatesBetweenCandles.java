package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-08 22:42
 */

import java.util.*;

/**
 * 2055. 蜡烛之间的盘子 plates-between-candles
 * 给你一个长桌子，桌子上盘子和蜡烛排成一列。给你一个下标从 0 开始的字符串 s ，
 * 它只包含字符 '*' 和 '|' ，其中 '*' 表示一个 盘子 ，'|' 表示一支 蜡烛 。
 * <p>
 * 同时给你一个下标从 0 开始的二维整数数组 queries ，其中 queries[i] = [lefti, righti] 表示
 * 子字符串 s[lefti...righti] （包含左右端点的字符）。
 * 对于每个查询，你需要找到 子字符串中 在 两支蜡烛之间 的盘子的 数目 。
 * 如果一个盘子在 子字符串中 左边和右边 都 至少有一支蜡烛，那么这个盘子满足在 两支蜡烛之间 。
 * <p>
 * 比方说，s = "||**||**|*" ，查询 [3, 8] ，表示的是子字符串 "*||**|" 。
 * 子字符串中在两支蜡烛之间的盘子数目为 2 ，子字符串中右边两个盘子在它们左边和右边 都 至少有一支蜡烛。
 * 请你返回一个整数数组 answer ，其中 answer[i] 是第 i 个查询的答案。
 * <p>
 * 3 <= s.length <= 10^5
 * s 只包含字符 '*' 和 '|' 。
 * 1 <= queries.length <= 10^5
 * queries[i].length == 2
 * 0 <= lefti <= righti < s.length
 */
public class P2055_PlatesBetweenCandles {
    public static void main(String[] args) {
        int[][] queries = new int[][]{{2, 5}, {5, 9}};
        int[][] queries1 = new int[][]{{1, 17}, {4, 5}, {14, 17}, {5, 11}, {15, 16}};
        int[][] queries2 = new int[][]{{2, 2}};
        P2055_Solution solution = new P2055_Solution();
        // [2,3]
        // - queries[0] 有两个盘子在蜡烛之间。
        // - queries[1] 有三个盘子在蜡烛之间。
        System.out.println(Arrays.toString(solution.platesBetweenCandles("**|**|***|", queries)));
        // [9,0,0,0,0]
        // - queries[0] 有 9 个盘子在蜡烛之间。
        // - 另一个查询没有盘子在蜡烛之间。
        System.out.println(Arrays.toString(solution.platesBetweenCandles("***|**|*****|**||**|*", queries1)));
        System.out.println(Arrays.toString(solution.platesBetweenCandles("||*", queries2)));
    }
}

// 我的解法
// 使用 TreeMap 记录字符 '|' 出现的位置索引以及是第几次出现
// 在 TreeMap 中 分别找到字符 '|' 的 (大于等于 query[0] 和 小于等于 query[1]) 的首次出现的位置索引以及对应记录第几个 '|'
// 通过计算得到该查询对应的 answer
// O(N + M + MLogM + Q) N 为字符串 s 的长度, M 为字符串 s 中字符 '|' 的个数, Q 为 queries.length
// O(M) TreeMap 的大小
// 108 ms 5.48%
// 88.1 MB 58.45%
// 之所以空间比官方的小, 是因为我只额外保存了字符 '|' 个数大小的 TreeMap
// 举例
// 012345678901234567890
// ***|**|*****|**||**|*
// {3=1, 6=2, 12=3, 15=4, 16=5, 19=6}
// 查询 {1, 17} 则为 这一部分 **|**|*****|**||*, >=1 的是 3 , <=17 的是 16, 3<16, 则 ans = (16-3) - (5-1) = 9
// 查询 {4, 5} 则为 这一部分 **, >=4 的是 6 , <=5 的是 3, 6 不满足小于 3, 则 ans = 0
// 查询 {14, 17} 则为 这一部分 *||*, >=14 的是 15 , <=17 的是 16, 15<16, 则 ans = (16-15) - (5-4) = 0
// 查询 {5, 11} 则为 这一部分 *|*****, >=5 的是 6 , <=11 的是 6, 6==6, 说明只有一个字符, 则 ans 直接为 0
// 查询 {15, 16} 则为 这一部分 ||, >=15 的是 15 , <=16 的是 16, 15<16, 则 ans = (16-15) - (5-4) = 0
class P2055_Solution1 {
    public int[] platesBetweenCandles(String s, int[][] queries) {
        int q = queries.length;
        // 最后一个字符 '|' 的下标索引(以此为遍历结束条件)
        int last = s.lastIndexOf('|');
        // s 中不存在字符 '|', 直接返回
        if (last == -1) {
            return new int[q];
        }
        int occur = s.indexOf('|');
        // s 中只有一个字符 '|', 直接返回
        if (occur == last) {
            return new int[q];
        }
        // key 为 字符 '|' 出现的下标索引
        // value 为当前字符 '|' 在位置 key 处是第几次出现, 即此时字符 '|' 总共出现的次数或者说是此时字符 '|' 的个数
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        // 字符 '|' 的个数(从 1 开始)
        int count = 1;
        treeMap.put(occur, count);
        // 执行到此处说明 s 中至少有两个字符 '|'
        // 退出循环时 occur == last 不必考虑是否 occur != -1
        while (occur < last) {
            occur = s.indexOf('|', occur + 1);
            count++;
            treeMap.put(occur, count);
        }
        // System.out.println(treeMap);
        int[] ans = new int[q];
        for (int i = 0; i < q; i++) {
            Integer leftIndex = treeMap.ceilingKey(queries[i][0]);
            Integer rightIndex = treeMap.floorKey(queries[i][1]);
            // leftIndex 或 rightIndex 为 null, 说明该 query 是在字符串的最左字符 '|' 的左边或最右字符 '|' 的右边
            // leftIndex > rightIndex, 说明该 query 中间没有字符 '|'
            // leftIndex == rightIndex, 说明该 query 中间只有一个字符 '|'
            // 这三种情况对应 ans 均为 0
            if (leftIndex == null || rightIndex == null || leftIndex >= rightIndex) {
                continue;
            }
            // 此处表示 query 中至少包含两个字符 '|'
            Integer leftValue = treeMap.get(leftIndex);
            Integer rightValue = treeMap.get(rightIndex);
            // (rightIndex - leftIndex - 1) - (rightValue - leftValue - 1)
            ans[i] = (rightIndex - leftIndex) - (rightValue - leftValue);
        }
        return ans;
    }
}

// 官方解法
// 预处理 + 前缀和
// O(N + Q) O(N)
// 8 ms 62.44%
// 105.2MB 24.37%
class P2055_Solution {
    public int[] platesBetweenCandles(String s, int[][] queries) {
        int n = s.length();
        // 盘子数量前缀和
        int[] preSum = new int[n];
        for (int i = 0, sum = 0; i < n; i++) {
            if (s.charAt(i) == '*') {
                sum++;
            }
            preSum[i] = sum;
        }
        // <= lefti 的第一个字符 '|' 下标
        int[] left = new int[n];
        for (int i = 0, l = -1; i < n; i++) {
            if (s.charAt(i) == '|') {
                l = i;
            }
            left[i] = l;
        }
        // >= righti 的第一个字符 '|' 下标
        int[] right = new int[n];
        for (int i = n - 1, r = -1; i >= 0; i--) {
            if (s.charAt(i) == '|') {
                r = i;
            }
            right[i] = r;
        }
        int q = queries.length;
        int[] ans = new int[q];
        for (int i = 0; i < q; i++) {
            int leftIndex = right[queries[i][0]];
            int rightIndex = left[queries[i][1]];
            // leftIndex 或 rightIndex 为 -1, 说明该 query 是在字符串的最左字符 '|' 的左边或最右字符 '|' 的右边
            // leftIndex > rightIndex, 说明该 query 中间没有字符 '|'
            // leftIndex == rightIndex, 说明该 query 中间只有一个字符 '|'
            // 这三种情况对应 ans 均为 0
            if (leftIndex == -1 || rightIndex == -1 || leftIndex >= rightIndex) {
                continue;
            }
            ans[i] = preSum[rightIndex] - preSum[leftIndex];
        }
        return ans;
    }
}