package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-04-10 23:20
 */

import java.util.HashSet;

/**
 * 804. 唯一摩尔斯密码词 unique-morse-code-words
 * 国际摩尔斯密码定义一种标准编码方式，将每个字母对应于一个由一系列点和短线组成的字符串， 比如:
 * <p>
 * 'a' 对应 ".-" ，
 * 'b' 对应 "-..." ，
 * 'c' 对应 "-.-." ，以此类推。
 * 为了方便，所有 26 个英文字母的摩尔斯密码表如下：
 * <p>
 * [".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."]
 * 给你一个字符串数组 words ，每个单词可以写成每个字母对应摩尔斯密码的组合。
 * <p>
 * 例如，"cab" 可以写成 "-.-..--..." ，(即 "-.-." + ".-" + "-..." 字符串的结合)。
 * 我们将这样一个连接过程称作 单词翻译 。
 * 对 words 中所有单词进行单词翻译，返回不同 单词翻译 的数量。
 * <p>
 * 1 <= words.length <= 100
 * 1 <= words[i].length <= 12
 * words[i] 由小写英文字母组成
 */
public class P804_UniqueMorseRepresentations {
    public static void main(String[] args) {
        String[] words = new String[]{"gin", "zen", "gig", "msg"};
        String[] words1 = new String[]{"a"};
        P804_Solution solution = new P804_Solution();
        System.out.println(solution.uniqueMorseRepresentations(words)); // 2
        System.out.println(solution.uniqueMorseRepresentations(words1)); // 1
    }
}

// 哈希表 基本操作
// 1 ms 100.00%
// 39.1 MB 66.33%
class P804_Solution {
    public int uniqueMorseRepresentations(String[] words) {
        final String[] code = new String[]{
                ".-", "-...", "-.-.", "-..", ".", "..-.",
                "--.", "....", "..", ".---", "-.-", ".-..",
                "--", "-.", "---", ".--.", "--.-", ".-.",
                "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        HashSet<String> hashSet = new HashSet<>();
        for (String word : words) {
            StringBuilder sb = new StringBuilder();
            int n = word.length();
            for (int i = 0; i < n; i++) {
                sb.append(code[word.charAt(i) - 97]);
            }
            hashSet.add(sb.toString());
        }
        return hashSet.size();
    }
}