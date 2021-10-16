package com.ahua.bytedance;

import java.util.HashMap;

/**
 * @author huajun
 * @create 2021-10-10 22:14
 */

/*
 * 数字 0-9 可以分别由 6, 2, 5, 5, 4, 5, 6, 3, 7, 6 根火柴棍摆成
 * 求使用 n(2 ≤ n ≤ 100?) 根火柴棍能够摆成的最大整数和最小整数是多少
 * 整数首位不为 0,火柴要用完
 */
public class Matchstick {
    public static void main(String[] args) {
        int n = 8;
        // key 为摆出一个数字需要的火柴数,只能为 2, 3, 4, 5, 6, 7
        // value 为需要 key 根火柴时,可以摆出的那个数字是几(最小的那个)
        // 比如:6 根火柴, 可以摆出0, 6, 9 这三个数,但是为了使得摆出来的数尽量小,所以此处保存的 value 为 0
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(2, 1);
        hashMap.put(3, 7);
        hashMap.put(4, 4);
        hashMap.put(5, 2);
        hashMap.put(6, 0);
        hashMap.put(7, 8);
        // 求最大整数
        System.out.println(maxInt(n));
        // 求最小整数
        System.out.println(minInt(n, hashMap));
    }

    // 求最大整数
    public static String maxInt(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        // 如果 n 为偶数 1111
        if (n % 2 == 0) {
            for (int i = 0; i < n / 2; i++) {
                stringBuilder.append(1);
            }
        } else {
            // 7111
            stringBuilder.append(7);
            for (int i = 0; i < n / 2 - 1; i++) {
                stringBuilder.append(1);
            }
        }
        return stringBuilder.toString();
    }

    // 求最小整数
    public static String minInt(int n, HashMap<Integer, Integer> hashMap) {
        StringBuilder stringBuilder = new StringBuilder();

        if (n <= 7) {
            stringBuilder.append(hashMap.get(n));
            return stringBuilder.toString();
        }

        // 假设所有都为 8
        for (int i = 0; i < n / 7; i++) {
            stringBuilder.append(8);
        }

        int m = n % 7;
        // 余数为 0,直接返回;不为 0,则需要在其前添加一个数字或者去掉一个8,添加两个数字
        if (m != 0) {
            switch (m) {
                case 1:
                    // 1 根火柴棍不能组成数字,于是删掉一个 8,取出 7 根火柴,那么目前就有 8 根火柴了
                    // 然后将这 8 根火柴组成 10 放在 stringBuilder 最前位置, 此数最小
                    // 1 + 7 == 8
                    stringBuilder.deleteCharAt(0);// 删除一个 8
                    stringBuilder.insert(0, 0);// 首位添加 0
                    stringBuilder.insert(0, 1);// 首位添加 1 成为 10888
                    break;
                /*case 2:
                    stringBuilder.insert(0, hashMap.get(m));
                    break;*/
                case 3:
                    // 3 + 7 == 10
                    stringBuilder.deleteCharAt(0);// 删除一个 8
                    stringBuilder.insert(0, 2);// 首位添加 2
                    stringBuilder.insert(0, 2);// 首位添加 2 成为 22888, 而不是 78888
                    break;
                case 4:
                    // 4 + 7 == 11
                    stringBuilder.deleteCharAt(0);// 删除一个 8
                    stringBuilder.insert(0, 0);// 首位添加 0
                    stringBuilder.insert(0, 2);// 首位添加 2 成为 20888, 而不是 48888
                    break;
                /*case 5:
                    stringBuilder.insert(0, hashMap.get(m));
                    break;*/
                case 6:
                    stringBuilder.insert(0, 6); // 68888
                    break;
                default:
                    // m 为 2, 5 时执行,将该数添加在首位 18888 28888
                    stringBuilder.insert(0, hashMap.get(m));
            }
        }

        return stringBuilder.toString();
    }
}
