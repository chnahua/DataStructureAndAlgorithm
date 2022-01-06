package com.ahua.leetcode.problems;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huajun
 * @create 2022-01-06 22:33
 */

/**
 * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
 * <p>
 * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
 * <p>
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
 * <p>
 * 请你实现这个将字符串进行指定行数变换的函数：
 * <p>
 * string convert(string s, int numRows);
 * <p>
 * 1 <= s.length <= 1000
 * s 由英文字母（小写和大写）、',' 和 '.' 组成
 * 1 <= numRows <= 1000
 */
public class P6_Convert {
    public static void main(String[] args) {
        String s = "PAYPALISHIRING";
        String s1 = "A";
        P6_Solution solution = new P6_Solution();
        System.out.println(solution.convert(s, 3)); // PAHNAPLSIIGYIR
        System.out.println(solution.convert(s, 4)); // PINALSIGYAHRPI
        System.out.println(solution.convert(s1, 1)); // A
        // 突然发现, 这个可以用于加密耶
    }
}

// 按行排序
class P6_Solution {
    // 我的解法, 思路同官方解法一, 按行排序
    public String convert1(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        List<ArrayList<Character>> list = new ArrayList<>(numRows);
        // 此处一定要初始化, 之前在做课程表相关题时就遇到过
        for (int i = 0; i < numRows; i++) {
            list.add(new ArrayList<>());
        }
        // flag 表示是当前字符添加到链表上, 链表是增序(从上往下)还是降序(从左往右)
        boolean flag = true;
        // index 表示是当前字符添加到哪个链表上
        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            if (flag) {
                list.get(index).add(s.charAt(i));
                index++;
                // 转变为从左往右添加
                if (index == numRows) {
                    flag = false;
                    index = numRows - 2;
                }
            } else {
                list.get(index).add(s.charAt(i));
                index--;
                // 转变为从上往下添加
                if (index == -1) {
                    flag = true;
                    index = 1;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (ArrayList<Character> str : list) {
            for (Character character : str) {
                sb.append(character);
            }
        }
        return sb.toString();
    }

    // 针对上一个我的解法和官方解法一的代码优化, 解题思路基本不变, 只更改代码细节和实现方式
    public String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        List<StringBuilder> list = new ArrayList<>(numRows);
        for (int i = 0; i < numRows; i++) {
            list.add(new StringBuilder());
        }
        // curRow 表示是当前字符添加到哪个链表上
        int curRow = 0;
        // 每次添加完一个字符后, 都要确定下一个字符要添加在哪个 sb 后, 相应的索引可能要 加 1(从上往下) 或者减 1(从左往右)
        int flag = -1;
        for (int i = 0; i < s.length(); i++) {
            list.get(curRow).append(s.charAt(i));
            if (curRow == 0 || curRow == numRows - 1) {
                // 转变方向
                flag = -flag;
            }
            curRow += flag;
        }
        StringBuilder ans = new StringBuilder();
        for (StringBuilder row : list) {
            ans.append(row);
        }
        return ans.toString();
    }
}