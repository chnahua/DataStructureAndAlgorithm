package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-06 18:00
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 472. 连接词 concatenated-words
 * 给你一个 不含重复 单词的字符串数组 words ，请你找出并返回 words 中的所有 连接词 。
 * <p>
 * 连接词 定义为：一个完全由给定数组中的至少两个较短单词组成的字符串。
 * 1 <= words.length <= 10^4
 * 0 <= words[i].length <= 1000
 * words[i] 仅由小写字母组成
 * 0 <= sum(words[i].length) <= 10^5
 */
public class P472_FindAllConcatenatedWordsInADict {
    public static void main(String[] args) {
        String[] words = new String[]{"cat", "cats", "catsdogcats", "dog", "dogcatsdog", "hippopotamuses", "rat", "ratcatdogcat"};
        P472_Solution solution = new P472_Solution();
        System.out.println(solution.findAllConcatenatedWordsInADict(words));
    }
}

// 字典树 + 深度优先搜索（递归实现）
class P472_Solution1 {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> ans = new ArrayList<>();
        P472_Trie trie = new P472_Trie();

        // 注意最终的字典树 Trie 中并不包含连接词, 即不将连接词加入到字典树中
        // 将 words 中的 word 按照长度从小到大排序, 将较短的 word 加入到字典树中, 判断较长的是否由已加入到字典树中的较短字符串组成
        // Arrays.sort(words, (s1, s2) -> (s1.length() - s2.length()));
        Arrays.sort(words, Comparator.comparingInt(String::length));
        for (String word : words) {
            // 跳过空字符串
            if (word.length() == 0) {
                continue;
            }
            // 对于每一个 word, 在 dfs 前, 该 word 并没有被添加到字典树中
            // 如果该 word 可由字典树中的多个较短字符串组成, 即返回为 true, 则说明该 word 为连接词, 连接词不加入到字典树中, 保存到结果链表
            // 如果该 word 不由字典树中的多个较短字符串组成, 即返回为 false, 则说明该 word 不为连接词, 那么则需要将其加入到字典树中(可能后续遍历判断的 word 是由该字符串组成的连接词)
            if (dfs(trie, word, 0)) {
                ans.add(word);
            } else {
                trie.insert(word);
            }
        }
        return ans;
    }

    private boolean dfs(P472_Trie trie, String word, int start) {
        P472_Trie node = trie;
        int index;
        for (int i = start; i < word.length(); i++) {
            index = word.charAt(i) - 'a';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
            if (node.isEnd) {
                if (i == word.length() - 1) {
                    return true;
                }
                // 判断子串是否可由已存在字典树中的较短字符串组成
                if (dfs(trie, word, i + 1)) {
                    return true;
                }
            }
        }

        return false;
    }
}

// 字典树 + 深度优先搜索（递归实现） + 记忆化搜索
class P472_Solution {
    P472_Trie trie = new P472_Trie();

    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> ans = new ArrayList<>();

        // 注意最终的字典树 Trie 中并不包含连接词, 即不将连接词加入到字典树中
        // 将 words 中的 word 按照长度从小到大排序, 将较短的 word 加入到字典树中, 判断较长的是否由已加入到字典树中的较短字符串组成
        // Arrays.sort(words, (s1, s2) -> (s1.length() - s2.length()));
        Arrays.sort(words, Comparator.comparingInt(String::length));
        for (String word : words) {
            // 跳过空字符串
            if (word.length() == 0) {
                continue;
            }

            boolean[] visited = new boolean[word.length()];
            // 对于每一个 word, 在 dfs 前, 该 word 并没有被添加到字典树中
            // 如果该 word 可由字典树中的多个较短字符串组成, 即返回为 true, 则说明该 word 为连接词, 连接词不加入到字典树中, 保存到结果链表
            // 如果该 word 不由字典树中的多个较短字符串组成, 即返回为 false, 则说明该 word 不为连接词, 那么则需要将其加入到字典树中(可能后续遍历判断的 word 是由该字符串组成的连接词)
            if (dfs(word, 0, visited)) {
                ans.add(word);
            } else {
                trie.insert(word);
            }
        }
        return ans;
    }

    private boolean dfs(String word, int start, boolean[] visited) {
        if (start == word.length()) {
            return true;
        }
        if (visited[start]) {
            return false;
        }
        visited[start] = true;

        P472_Trie node = trie;
        int index;
        for (int i = start; i < word.length(); i++) {
            index = word.charAt(i) - 'a';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
            if (node.isEnd) {
                // 判断子串是否可由已存在字典树中的较短字符串组成
                if (dfs(word, i + 1, visited)) {
                    return true;
                }
            }
        }

        return false;
    }
}

class P472_Trie {
    P472_Trie[] children;
    boolean isEnd;

    public P472_Trie() {
        children = new P472_Trie[26];
        isEnd = false;
    }

    public void insert(String word) {
        P472_Trie node = this;
        int index;
        for (int i = 0; i < word.length(); i++) {
            index = word.charAt(i) - 'a';
            if (node.children[index] == null) {
                node.children[index] = new P472_Trie();
            }
            node = node.children[index];
        }
        node.isEnd = true;
    }
}

