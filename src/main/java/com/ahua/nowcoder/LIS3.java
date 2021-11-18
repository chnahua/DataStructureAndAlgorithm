package com.ahua.nowcoder;

import java.util.*;

/**
 * @author huajun
 * @create 2021-11-15 16:25
 */

/**
 * 给定数组 arr ，设长度为 n ，输出 arr 的最长上升子序列。（如果有多个答案，请输出其中 按数值
 * (注：区别于按单个字符的ASCII码值)进行比较的 字典序最小的那个）
 * <p>
 * 数据范围：0≤ n ≤200000,0≤ arri ≤1000000000
 * 要求：空间复杂度 O(n)，时间复杂度 O(nlogn)
 * <p>
 * 该题 类似 LeetCode 的 300 和 673 题
 */

public class LIS3 {
    public static void main(String[] args) {
        int[] arr = new int[]{2, 1, 5, 3, 6, 4, 8, 9, 7};
        int[] arr1 = new int[]{1, 2, 8, 6, 4};
        //int[] arr2 = new int[]{};
        LIS3_Solution solution = new LIS3_Solution();
        System.out.println("res \t" + Arrays.toString(solution.LIS(arr))); // [1, 3, 4, 8, 9]
        LIS3_Solution solution1 = new LIS3_Solution();
        System.out.println("res \t" + Arrays.toString(solution1.LIS(arr1))); // [1, 2, 4]
        //System.out.println(Arrays.toString(solution1.LIS(arr2))); // [1, 2, 4]
    }
}

// 回溯是不符合要求的
class LIS3_Solution1 {
    /**
     * retrun the longest increasing subsequence
     *
     * @param arr int整型一维数组 the array
     * @return int整型一维数组
     */
    List<List<Integer>> allList = new ArrayList<>();
    int[] arr;
    int n;
    int maxLen = 0;

    public int[] LIS(int[] arr) {
        // write code here
        this.arr = arr;
        this.n = arr.length;
        Deque<Integer> list = new ArrayDeque<>();
        dfs(0, -1, list);
/*
        if (allLsit.size() == 1) {
            int[] res = listToArr(allLsit.get(0),maxLen);
            return res;
        }*/
        int num = 0;
        int minNum = 0;
        int index = 0;
        for (int i = 0; i < allList.size(); i++) {
            List<Integer> list1 = allList.get(i);
            num = 0;
            for (int j = 0; j < maxLen; j++) {
                num = num * 10 + list1.get(j);
            }
            // System.out.println(num);
            if (num < minNum || minNum == 0) {
                minNum = num;
                index = i;
            }
            System.out.println(list1);
        }
        return listToArr(allList.get(index), maxLen);
    }

    public int[] listToArr(List<Integer> list, int arrLen) {
        int[] res = new int[arrLen];
        for (int i = 0; i < arrLen; i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    public void dfs(int index, int last, Deque<Integer> list) {
        if (index == n) {
            int len = list.size();
            if (len > maxLen) {
                allList.clear();
                allList.add(new ArrayList<>(list));
                maxLen = len;
            } else if (len == maxLen) {
                allList.add(new ArrayList<>(list));
            } else {
                // maxLen > len 此 list 不加入 啥都不做
            }
            return;
        }

        int temp;
        for (int i = index; i < n; i++) {
            //            if (n - i < maxLen) {
            //                break;
            //            }
            temp = arr[i];
            if (temp > last) {
                list.add(temp);
                dfs(i + 1, temp, list);
                list.removeLast();
            }/* else {
                dfs(i + 1, last, list);
            }*/
            if (i == n - 1 && temp < last) {
                int len = list.size();
                if (len > maxLen) {
                    allList.clear();
                    allList.add(new ArrayList<>(list));
                    maxLen = len;
                } else if (len == maxLen) {
                    allList.add(new ArrayList<>(list));
                } else {
                    // maxLen > len 此 list 不加入 啥都不做
                }
                return;
            }
        }
    }
}

class LIS3_Solution {
    /**
     * retrun the longest increasing subsequence
     *
     * @param arr int整型一维数组 the array
     * @return int整型一维数组
     */

    public int[] LIS(int[] arr) {
        // write code here
        int n = arr.length;
        if (n == 0) {
            return null;
        } else if (n == 1) {
            return arr;
        }
        // 贪心
        // 维护一个数组 d[i] ，表示长度为 i 的最长上升子序列的末尾元素的最小值，
        // 用 len 记录目前最长上升子序列的长度，
        // 起始时 len 为 1，d[1] = nums[0]
        // d[0] 位置不管
        int[] d = new int[n + 1];
        // 以第 i 个元素结尾的子序列的最大长度
        int[] maxLen = new int[n];
        // 赋初值
        int len = 1;
        d[1] = arr[0];
        maxLen[0] = 1;
        for (int i = 1; i < n; i++) {
            // nums[i] 比当前最长子序列的最后一个元素都大, 将其添加在末尾 d[len + 1]
            if (arr[i] > d[len]) {
                d[len + 1] = arr[i];
                maxLen[i] = ++len;
                // len++;
                continue;
            }
            // 二分查找
            int left = 1;
            int right = len - 1;
            int mid = 0;
            while (left <= right) {
                mid = (left + right) / 2;
                if (arr[i] > d[mid]) {
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
            d[left] = arr[i];
            maxLen[i] = left;
            // System.out.println("d \t\t" + Arrays.toString(d));
            // System.out.println("maxLen \t" + Arrays.toString(maxLen));
        }

        // System.out.println("len \t" + len);
        int[] res = new int[len];
        for (int i = n - 1, j = len; j >= 1; i--) {
            if (maxLen[i] == j) {
                res[--j] = arr[i];
            }
        }
        // System.out.println("res \t" + Arrays.toString(res));
        return res;
    }
}

