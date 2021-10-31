package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-10-31 22:40
 */

/**
 * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
 *
 * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
 *
 */
public class P344_ReverseString {
    public static void main(String[] args) {
        char[] s = new char[]{'h', 'e', 'l', 'l', 'o'};
        P344_Solution solution = new P344_Solution();
        solution.reverseString(s);
        System.out.println(s);
    }
}

class P344_Solution {
    public void reverseString(char[] s) {
        int len = s.length;
        char temp;
        for (int i = 0; i < len / 2; i++) {
            temp = s[i];
            s[i] = s[len - 1 - i];
            s[len - 1 - i] = temp;
        }
    }
}