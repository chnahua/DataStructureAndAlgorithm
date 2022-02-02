package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-02 20:31
 */

/**
 * 2000. 反转单词前缀 reverse-prefix-of-word
 * 给你一个下标从 0 开始的字符串 word 和一个字符 ch 。
 * 找出 ch 第一次出现的下标 i ，反转 word 中从下标 0 开始、直到下标 i 结束（含下标 i ）的那段字符。
 * 如果 word 中不存在字符 ch ，则无需进行任何操作。
 * <p>
 * 例如，如果 word = "abcdefd" 且 ch = "d" ，那么你应该 反转 从下标 0 开始、直到下标 3 结束（含下标 3 ）。
 * 结果字符串将会是 "dcbaefd" 。
 * 返回 结果字符串 。
 * <p>
 * 1 <= word.length <= 250
 * word 由小写英文字母组成
 * ch 是一个小写英文字母
 */
public class P2000_ReversePrefix {
    public static void main(String[] args) {
        P2000_Solution solution = new P2000_Solution();
        System.out.println(solution.reversePrefix("abcdefd", 'd')); // "dcbaefd"
        System.out.println(solution.reversePrefix("xyxzxe", 'z')); // "zxyxxe"
        System.out.println(solution.reversePrefix("abcd", 'z')); // "abcd"
    }
}

class P2000_Solution {
    // 我的解法
    public String reversePrefix(String word, char ch) {
        int firstIndex = word.indexOf(ch);
        if (firstIndex == -1) {
            return word;
        }
//        // 第一种 27%
//        String reversedPrefix = new StringBuilder(word.substring(0, firstIndex + 1)).reverse().toString();
//        return firstIndex == word.length() - 1 ? reversedPrefix : reversedPrefix + word.substring(firstIndex + 1);
        // 第二种 效率比第一种高 100%
        StringBuilder reversedPrefix = new StringBuilder(word.substring(0, firstIndex + 1)).reverse();
        return firstIndex == word.length() - 1 ? reversedPrefix.toString() : reversedPrefix.append(word.substring(firstIndex + 1)).toString();
    }

    // 官方解法
    public String reversePrefix1(String word, char ch) {
        int index = word.indexOf(ch);
        if (index >= 0) {
            char[] arr = word.toCharArray();
            int left = 0, right = index;
            while (left < right) {
                char temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
            word = new String(arr);
        }
        return word;
    }
}