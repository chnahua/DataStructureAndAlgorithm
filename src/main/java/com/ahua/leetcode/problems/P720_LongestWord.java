package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-17 22:45
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 720. 词典中最长的单词 longest-word-in-dictionary
 * 给出一个字符串数组 words 组成的一本英语词典。
 * 返回 words 中最长的一个单词，该单词是由 words 词典中其他单词逐步添加一个字母组成。
 * <p>
 * 若其中有多个可行的答案，则返回答案中字典序最小的单词。若无答案，则返回空字符串。
 * <p>
 * 1 <= words.length <= 1000
 * 1 <= words[i].length <= 30
 * 所有输入的字符串 words[i] 都只包含小写字母。
 */
public class P720_LongestWord {
    public static void main(String[] args) {
        String[] words = new String[]{"w", "wo", "wor", "worl", "world"};
        String[] words1 = new String[]{"a", "banana", "app", "appl", "ap", "apply", "apple"};
        String[] words2 = new String[]{"yo", "ew", "fc", "zrc", "yodn", "fcm", "qm", "qmo", "fcmz", "z", "ewq", "yod", "ewqz", "y"};
        String[] words3 = new String[]{"ts", "e", "x", "pbhj", "opto", "xhigy", "erikz", "pbh", "opt", "erikzb", "eri", "erik",
                "xlye", "xhig", "optoj", "optoje", "xly", "pb", "xhi", "x", "o"};
        P720_Solution solution = new P720_Solution();
        System.out.println(solution.longestWord(words)); // "world"
        System.out.println(solution.longestWord(words1)); // "apple"
        System.out.println(solution.longestWord(words2)); // "yodn"
        System.out.println(solution.longestWord(words3)); // "e"
    }
}

// 哈希表
// 16 ms 50.77%
// 41.6 MB 21.49%
class P720_Solution {
    public String longestWord(String[] words) {
        // 先排序
        Arrays.sort(words);
        Set<String> hashSet = new HashSet<>();
        int maxLen = 0;
        String ans = "";
        for (String word : words) {
            int length = word.length();
            // 长度为 1 的字符串直接添加进去, 此 word 的前 length - 1 个字符已在 Set 中时, 也添加进去
            if (length == 1 || hashSet.contains(word.substring(0, length - 1))) {
                if (length > maxLen) {
                    maxLen = length;
                    ans = word;
                }
                hashSet.add(word);
            }
        }
        return ans;
    }
}
// 字典树