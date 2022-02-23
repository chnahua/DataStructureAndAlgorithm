package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-23 20:42
 */

/**
 * 917. 仅仅反转字母 reverse-only-letters
 * 给你一个字符串 s ，根据下述规则反转字符串：
 * <p>
 * 所有非英文字母保留在原有位置。
 * 所有英文字母（小写或大写）位置反转。
 * 返回反转后的 s 。
 * <p>
 * 1 <= s.length <= 100
 * s 仅由 ASCII 值在范围 [33, 122] 的字符组成
 * s 不含 '\"' 或 '\\'
 */
public class P917_ReverseOnlyLetters {
    public static void main(String[] args) {
        P917_Solution solution = new P917_Solution();
        System.out.println(solution.reverseOnlyLetters("ab-cd")); // "dc-ba"
        System.out.println(solution.reverseOnlyLetters("a-bC-dEf-ghIj")); // "j-Ih-gfE-dCba"
        System.out.println(solution.reverseOnlyLetters("Test1ng-Leet=code-Q!")); // "Qedo1ct-eeLg=ntse-T!"
        System.out.println(solution.reverseOnlyLetters("7_28]")); // "7_28]"
    }
}

// 双指针
// 0 ms 100.00%
// 39.1 MB 21.55%
class P917_Solution {
    public String reverseOnlyLetters(String s) {
        char[] arr = s.toCharArray();
        int left = 0, right = arr.length - 1;
        while (left < right) {
            // Character.isLetter(arr[left]) 也可判断是不是字母
            while (left < right && (!isLetter(arr[left]))) {
                left++;
            }
            while (left < right && (!isLetter(arr[right]))) {
                right--;
            }
            if (left < right) {
                // 交换
                char ch = arr[left];
                arr[left] = arr[right];
                arr[right] = ch;
                left++;
                right--;
            }
        }
        return new String(arr);
    }

    private boolean isLetter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }
}