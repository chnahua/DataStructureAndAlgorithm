package com.ahua.exam.huawei;

/**
 * @author huajun
 * @create 2022-03-07 14:54
 */

import java.util.Collection;
import java.util.HashMap;

/**
 * 对应于 LeetCode 上的该题 面试题 01.04. 回文排列
 * https://leetcode-cn.com/problems/palindrome-permutation-lcci/
 * <p>
 * 给定一个字符串，编写一个函数判定其是否为某个回文串的排列之一。
 * 回文串是指正反两个方向都一样的单词或短语。排列是指字母的重新排列。
 * 回文串不一定是字典当中的单词。
 * <p>
 * 示例1：
 * 输入："tactcoa"
 * 输出：true
 * <p>
 * 注：排列有"tacocat"、"atcocta"，等等
 */
public class ODInterview_4_CD_II_2022_03_07 {
    public static void main(String[] args) {
        ODInterview_4_CD_II_2022_03_07_Solution solution = new ODInterview_4_CD_II_2022_03_07_Solution();
        System.out.println(solution.func("tactcoa"));
        System.out.println(solution.func("tactca"));
        System.out.println(solution.func("tacoadcct"));
        System.out.println(solution.func("tacgdfg"));
    }
}

class ODInterview_4_CD_II_2022_03_07_Solution {
    public boolean func(String s) {
        int n = s.length();
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            hashMap.put(ch, hashMap.getOrDefault(ch, 0) + 1);
        }
        Collection<Integer> values = hashMap.values();
        int count = 0;
        for (Integer value : values) {
            if (value % 2 == 1) {
                count++;
                if (count == 2) {
                    return false;
                }
            }
        }
        return true;
    }
}