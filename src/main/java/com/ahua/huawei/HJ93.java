package com.ahua.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author huajun
 * @create 2021-11-14 1:39
 */


//public class HJ93 {
//    static boolean ans;
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        while (sc.hasNextInt()) {
//
//            int n = sc.nextInt();
//            List<Integer> list = new ArrayList<>();
//            int a = 0;
//            int sum5 = 0;
//            int sum3 = 0;
//            int sum = 0;
//            for (int i = 0; i < n; i++) {
//                a = sc.nextInt();
//                if (a % 5 == 0) {
//                    sum5 += a;
//                } else if (a % 3 == 0) {
//                    sum3 += a;
//                } else {
//                    list.add(a);
//                    sum += a;
//                }
//            }
//
//            int diff = sum - Math.abs(sum5 - sum3);
//            if (diff % 2 != 0) {
//                System.out.println(false);
//                continue;
//            }
//            // 数组中找到多个数的和为 target
//            int target = diff / 2;
//            int len = list.size();
//
//            boolean[] used = new boolean[len];
//            boolean bl = dfs(list, len, target, 0, 0, used);
//            System.out.println(bl);
//
//        }
//    }
//
//    // 我这个都对了，莫名其妙对的
//    public static boolean dfs(List<Integer> list, int len, int target, int index, int sum, boolean[] used) {
//        if (index == len) {
//            if (sum == target) {
//                return true;
//            }
//            return false;
//        }
//
//        int temp = sum;
//        for (int i = index; i < len; i++) {
//            if (!used[i]) {
//                sum += list.get(i);
//                used[i] = true;
//                if (sum == target) {
//                    return true;
//                }
//                if (dfs(list, len, target, index + 1, sum, used)) {
//                    return true;
//                }
//                if (dfs(list, len, target, index + 1, temp, used)) {
//                    return true;
//                }
//
//                sum = temp;
//                used[i] = false;
//            }
//        }
//        return false;
//    }
//
//}


public class HJ93 {
    static boolean ans;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {

            int n = sc.nextInt();
            List<Integer> list = new ArrayList<>();
            int a = 0;
            int sum5 = 0;
            int sum3 = 0;
            int sum = 0;
            for (int i = 0; i < n; i++) {
                a = sc.nextInt();
                if (a % 5 == 0) {
                    sum5 += a;
                } else if (a % 3 == 0) {
                    sum3 += a;
                } else {
                    list.add(a);
                    sum += a;
                }
            }

            int diff = sum - Math.abs(sum5 - sum3);
            if (diff % 2 != 0) {
                System.out.println(false);
                continue;
            }
            // 数组中找到多个数的和为 target
            int target = diff / 2;
            int len = list.size();

            ans = dfs(list, len, target, 0, 0);
            System.out.println(ans);
        }
    }

    public static boolean dfs(List<Integer> list, int len, int target, int index, int sum) {
        if (index == len) {
            if (sum == target) {
                ans = true;
            }
        } else if (sum == target) {
            ans = true;
        } else {
//            if (dfs(list, len, target, index + 1, sum + list.get(index))) {
//                return true;
//            } else if (dfs(list, len, target, index + 1, sum)) {
//                return true;
//            }
            dfs(list, len, target, index + 1, sum + list.get(index));
            if (ans) {
                return true;
            }
            dfs(list, len, target, index + 1, sum);
            if (ans) {
                return true;
            }
        }
        return ans;
    }
}
