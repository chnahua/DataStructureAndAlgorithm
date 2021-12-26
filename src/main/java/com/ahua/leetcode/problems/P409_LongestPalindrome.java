package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-27 0:31
 */

import java.util.HashMap;

/**
 * 409. 最长回文串
 * 给定一个包含大写字母和小写字母的字符串，找到通过这些字母构造成的最长的回文串。
 * <p>
 * 在构造过程中，请注意区分大小写。比如 "Aa" 不能当做一个回文字符串。
 * <p>
 * 注意:
 * 假设字符串的长度不会超过 1010。
 */

public class P409_LongestPalindrome {
    public static void main(String[] args) {
        P409_Solution solution = new P409_Solution();
        System.out.println(solution.longestPalindrome("abccccdd"));
    }
}

class P409_Solution {
    // 哈希表, 效率很低
    public int longestPalindrome1(String s) {
        if (s == null || "".equals(s)) {
            return 0;
        }

        // 统计每个字符出现的次数
        HashMap<Character, Integer> hashMap = new HashMap<>();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            hashMap.put(c, hashMap.getOrDefault(c, 0) + 1);
        }

        // 最长回文子串的一半的长度
        int maxHalfLen = 0;
        // bl 为 true 时, 表示最终的回文子串的长度为 奇数
        boolean bl = false;
        for (Integer num : hashMap.values()) {
            maxHalfLen += num / 2;
            if (!bl && num % 2 == 1) {
                bl = true;
            }
        }
//        if (bl) {
//            return maxHalfLen * 2 + 1;
//        } else {
//            return maxHalfLen * 2;
//        }
        return maxHalfLen * 2 + (bl ? 1 : 0);
    }

    // 稍稍修改
    public int longestPalindrome(String s) {
        if (s == null || "".equals(s)) {
            return 0;
        }

        // 统计每个字符出现的次数
        HashMap<Character, Integer> hashMap = new HashMap<>();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            hashMap.put(c, hashMap.getOrDefault(c, 0) + 1);
        }

        // 最长回文子串的一半的长度
        int maxHalfLen = 0;
        // 发现还可以再稍稍修改一下
        // flag == 1 时, 表示最终的回文子串的长度为 奇数
        int flag = 0;
        for (Integer num : hashMap.values()) {
            maxHalfLen += num / 2;
            if (flag == 0 && num % 2 == 1) {
                flag = 1;
            }
        }

        return maxHalfLen * 2 + flag;
    }
}