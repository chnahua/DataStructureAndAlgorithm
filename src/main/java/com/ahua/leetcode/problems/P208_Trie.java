package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-04 23:56
 */

/**
 * 208. 实现 Trie (前缀树) implement-trie-prefix-tree
 * Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。
 * 这一数据结构有相当多的应用情景，例如自动补完和拼写检查。
 * <p>
 * 请你实现 Trie 类：
 * <p>
 * Trie() 初始化前缀树对象。
 * void insert(String word) 向前缀树中插入字符串 word 。
 * boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
 * boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false 。
 * <p>
 * 1 <= word.length, prefix.length <= 2000
 * word 和 prefix 仅由小写英文字母组成
 * insert、search 和 startsWith 调用次数 总计 不超过 3 * 10^4 次
 */
public class P208_Trie {
    public static void main(String[] args) {

    }
}

// Trie（前缀树、字典树）
class Trie {
    private boolean isEnd;
    private final Trie[] children;

    public Trie() {
        isEnd = false;
        children = new Trie[26];
    }

    public void insert(String word) {
        Trie node = this;
        int index;
        for (int i = 0; i < word.length(); i++) {
            index = word.charAt(i) - 'a';
            if (node.children[index] == null) {
                node.children[index] = new Trie();
            }
            node = node.children[index];
        }
        // 退出循环时, node 指向 word 中的最后一个字符, 标记它为结束字符
        // 表示这是一个完整单词
        node.isEnd = true;
    }

    public boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isEnd;
    }

    // 若搜索到了前缀的末尾，就说明字典树中存在该前缀 此外。若前缀末尾对应节点的 isEnd 为真，则说明字典树中存在该字符串
    public boolean startsWith(String prefix) {
        // 查找到前缀, 返回前缀最后一个字符结点
        return searchPrefix(prefix) != null;
    }

    private Trie searchPrefix(String prefix) {
        Trie node = this;
        int index;
        for (int i = 0; i < prefix.length(); i++) {
            index = prefix.charAt(i) - 'a';
            if (node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        // 退出循环时, node 指向 prefix 中的最后一个字符
        return node;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */