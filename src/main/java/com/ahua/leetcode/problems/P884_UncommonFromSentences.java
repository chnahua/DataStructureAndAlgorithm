package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-30 19:05
 */

import java.util.*;

/**
 * 884. 两句话中的不常见单词 uncommon-words-from-two-sentences
 * 句子 是一串由空格分隔的单词。每个 单词 仅由小写字母组成。
 * <p>
 * 如果某个单词在其中一个句子中恰好出现一次，在另一个句子中却 没有出现 ，那么这个单词就是 不常见的 。
 * <p>
 * 给你两个 句子 s1 和 s2 ，返回所有 不常用单词 的列表。返回列表中单词可以按 任意顺序 组织。
 * <p>
 * 1 <= s1.length, s2.length <= 200
 * s1 和 s2 由小写英文字母和空格组成
 * s1 和 s2 都不含前导或尾随空格
 * s1 和 s2 中的所有单词间均由单个空格分隔
 */
public class P884_UncommonFromSentences {
    public static void main(String[] args) {
        String s1 = "this apple is sweet";
        String s2 = "this apple is sour";
        String s3 = "apple apple";
        String s4 = "banana";
        String s5 = "s z z z s";
        String s6 = "s z ejt";
        P884_Solution solution = new P884_Solution();
        System.out.println(Arrays.toString(solution.uncommonFromSentences(s1, s2)));
        System.out.println(Arrays.toString(solution.uncommonFromSentences(s3, s4)));
        System.out.println(Arrays.toString(solution.uncommonFromSentences(s5, s6)));
    }
}

// 我的解法
// 2ms 99.60%
// 40.2MB 5.06%
class P884_Solution1 {
    public String[] uncommonFromSentences(String s1, String s2) {
        // 获得这两个句子中的各单词及其出现次数
        HashMap<String, Integer> wordAndCount1 = getWordAndCountHashMap(s1);
        HashMap<String, Integer> wordAndCount2 = getWordAndCountHashMap(s2);

        List<String> list = new ArrayList<>();

        // 遍历得到 WordAndCount1 中的独有的不常见的单词
        Set<Map.Entry<String, Integer>> entrySet1 = wordAndCount1.entrySet();
        // 遍历过程中, 句子 1 中的各单词及其出现次数 WordAndCount1 不会有删除或者更改操作
        for (Map.Entry<String, Integer> entry : entrySet1) {
            // 句子 1 中出现次数大于 1 的单词, 在句子 2 中将其删除(不管是否存在, 或者出现多少次)
            if (entry.getValue() > 1) {
                wordAndCount2.remove(entry.getKey());
            } else {
                // 句子 1 中出现次数为 1 的单词, 如果在句子 2 中也存在, 在句子 2 中将其删除
                // 如果在句子 2 中不存在, 则将其添加到 list
                // 换个思路想, 为了提升效率, 不管句子 2 中有没有这个单词, 都执行删除语句,
                // 如果有自然就删除了, 如果没有, 也就不会删除了
                // 根据 remove 的返回值判断是否删除了, 进一步确定是否添加该单词进 list 中
                /*if (WordAndCount2.containsKey(entry.getKey())) {
                    WordAndCount2.remove(entry.getKey());
                } else {
                    list.add(entry.getKey());
                }*/
                // 为 null, 表示句子 2 中不存在, 当然也就不用删除, 并且是不常见单词, 将其添加到 list 中
                if (wordAndCount2.remove(entry.getKey()) == null) {
                    list.add(entry.getKey());
                }
            }
        }

        // 遍历得到 WordAndCount2 中的独有的不常见的单词
        Set<Map.Entry<String, Integer>> entrySet2 = wordAndCount2.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet2) {
            // 可省略 && !WordAndCount1.containsKey(entry.getKey())
            if (entry.getValue() == 1) {
                list.add(entry.getKey());
            }
        }

        // 结果输出
        String[] ans = new String[list.size()];
        int i = 0;
        for (String word : list) {
            ans[i] = word;
            i++;
        }
        return ans;
    }

    // 一个句子中各单词及其出现次数
    private HashMap<String, Integer> getWordAndCountHashMap(String s) {
        String[] str = s.split(" ");
        HashMap<String, Integer> wordAndCountHashMap = new HashMap<>();
        for (String word : str) {
            wordAndCountHashMap.put(word, wordAndCountHashMap.getOrDefault(word, 0) + 1);
        }
        return wordAndCountHashMap;
    }
}

// 官方解法, 更简单的思路, 效果差不多
//「在句子 1 中恰好出现一次，但在句子 2 中没有出现的单词」或者「在句子 2 中恰好出现一次，但在句子 1 中没有出现的单词」。
// 这其实等价于找出：在两个句子中一共只出现一次的单词。
class P884_Solution {
    public String[] uncommonFromSentences(String s1, String s2) {
        // 获得这两个句子中的各单词及其出现次数
        HashMap<String, Integer> wordAndCount = new HashMap<>();
        getWordAndCountHashMap(s1, wordAndCount);
        getWordAndCountHashMap(s2, wordAndCount);

        List<String> ans = new ArrayList<>();

        // 遍历只出现一次的单词即为所求
        Set<Map.Entry<String, Integer>> entrySet = wordAndCount.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            if (entry.getValue() == 1) {
                ans.add(entry.getKey());
            }
        }

        return ans.toArray(new String[0]);
    }

    // 一个句子中各单词及其出现次数
    private void getWordAndCountHashMap(String s, HashMap<String, Integer> wordAndCountHashMap) {
        String[] str = s.split(" ");
        for (String word : str) {
            wordAndCountHashMap.put(word, wordAndCountHashMap.getOrDefault(word, 0) + 1);
        }
    }
}