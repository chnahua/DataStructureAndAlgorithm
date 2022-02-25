package com.ahua.exam.huawei;

import java.util.*;

/**
 * @author huajun
 * @create 2022-01-27 18:35
 */

/*
题目描述
给一非空的单词列表，返回前 k 个出现次数最多的单词。

解答要求
时间限制：1000ms, 内存限制：64MB
输入
第一行为一个整数T（1<=T<=1000）,表示有T个单词，接下来是T个单词的输入，最后一行是一个整数k，表示返回需要返回前 k 个出现次数最多的单词

输出
输出k个单词，应该按单词出现频率由高到低排序。如果不同的单词有相同出现频率，按字母顺序排序。
输入样例 1

6
I
Love
Life
I
Love
Coding
2
输出样例 1

I
Love
 */
public class ODInterview_CD_II_2022_01_27 {
    public static void main(String[] args) {
        String[] str = new String[]{"I", "Love", "Life", "I", "Love", "Coding"};
        String[] str1 = new String[]{"I", "Love"};
        String[] str2 = new String[]{"I", "Love", "Life", "I", "Love", "Coding", "I", "Love", "Spark", "Kafka"};
        ODInterview_CD_II_2022_01_27_Solution solution = new ODInterview_CD_II_2022_01_27_Solution();
        System.out.println(Arrays.toString(solution.func(str, 2)));
        System.out.println(Arrays.toString(solution.func(str1, 1)));
        System.out.println(Arrays.toString(solution.func(str2, 3)));
    }
}

// 面试时写的代码, 未做任何修改, 应该是做正确了
class ODInterview_CD_II_2022_01_27_Solution1 {
    public String[] func(String[] str, int k) {
        int n = str.length;
        HashMap<String, Integer> hashMap = new HashMap<>();
        // 各单词，及其出现次数
        for (String s : str) {
            hashMap.put(s, hashMap.getOrDefault(s, 0) + 1);
        }
        TreeMap<Integer, ArrayList<String>> listHashMap = new TreeMap<>((o1, o2) -> o2 - o1);
        Set<String> keySet = hashMap.keySet();
        // 根据出现次数由大到小排序
        for (String s : keySet) {
            int count = hashMap.get(s);
            ArrayList<String> strList = listHashMap.get(count);
            if (strList == null) {
                ArrayList<String> list = new ArrayList<>();
                list.add(s);
                listHashMap.put(count, list);
            } else {
                strList.add(s);
            }
        }
        // 将出现次数最多的单词保存输出
        Set<Integer> set = listHashMap.keySet();
        String[] ans = new String[k];
        int i = 0;
        for (Integer num : set) {
            ArrayList<String> list = listHashMap.get(num);
            Collections.sort(list);
            for (String s : list) {
                if (i < k) {
                    ans[i] = s;
                    i++;
                }
            }
        }
        // Arrays.sort(ans);
        return ans;
    }
}

// 思路不变, 优化代码(更改变量名、添加注释等)
class ODInterview_CD_II_2022_01_27_Solution2 {
    public String[] func(String[] str, int k) {
        // 1.保存各单词及其出现次数
        HashMap<String, Integer> wordAndCount = new HashMap<>();
        for (String word : str) {
            wordAndCount.put(word, wordAndCount.getOrDefault(word, 0) + 1);
        }
        // 保存出现次数相同的单词为一个链表
        TreeMap<Integer, List<String>> countAndWordsList = new TreeMap<>((o1, o2) -> o2 - o1);
        // wordAndCount 中的所有 word 组成的集合
        Set<String> wordSet = wordAndCount.keySet();
        // 2.将单词出现次数 (作为 key) 相同的单词组成一个链表, 作为 value 保存在 TreeMap 中, 并根据单词出现次数由大到小排序
        for (String word : wordSet) {
            // 获取单词 word 出现的次数
            int wordCount = wordAndCount.get(word);
            // 出现此次数的所有单词组成的单词链表, 不为 null 时, 将该单词添加进这个链表中
            List<String> wordList = countAndWordsList.get(wordCount);
            // wordList 为 null 时, 说明原 countAndWordsList 中还没有出现当前次数的单词
            // 现要新添加这个键值对入 countAndWordsList 中, 此时需要先新建链表
            if (wordList == null) {
                List<String> newWordList = new ArrayList<>();
                newWordList.add(word);
                countAndWordsList.put(wordCount, newWordList);
            } else {
                // wordList 不为 null 时, 直接添加
                wordList.add(word);
            }
        }
        // 输出结果数组
        String[] ans = new String[k];
        // 下标计数
        int i = 0;
        // 3.将出现次数最多的单词保存到输出结果 ans 中
        Set<Integer> countSet = countAndWordsList.keySet();
        for (Integer moreCount : countSet) {
            // 率先遍历出现次数更多的单词链表
            List<String> moreCountWordList = countAndWordsList.get(moreCount);
            // 遍历 moreCountWordList 前需要将其中的单词按照字典序排序, 再取出排在前面的相应个数的单词保存到输出结果 ans 中
            Collections.sort(moreCountWordList);
            /*
            // 可修改为直接返回
            for (String word : moreCountWordList) {
                if (i < k) {
                    ans[i] = word;
                    i++;
                } else {
                    break;
                }
            }
            if (i == k) {
                break;
            }
            */
            for (String word : moreCountWordList) {
                if (i < k) {
                    ans[i] = word;
                    i++;
                } else {
                    return ans;
                }
            }
        }
        return ans;
    }
}

// 再次修改 进行封装
// 其实可以改用 entrySet 代替 keySet, 这里不做了
class ODInterview_CD_II_2022_01_27_Solution3 {
    public String[] func(String[] str, int k) {
        // 1.保存各单词及其出现次数
        HashMap<String, Integer> wordAndCount = new HashMap<>();
        for (String word : str) {
            wordAndCount.put(word, wordAndCount.getOrDefault(word, 0) + 1);
        }
        // 2.将单词出现次数 (作为 key) 相同的单词组成一个链表, 作为 value 保存在 TreeMap 中, 并根据单词出现次数由大到小排序
        // key 为单词出现次数
        // value 为所有出现次数为 key 的单词组成的链表
        TreeMap<Integer, List<String>> countAndWordsList = getCountAndWordsList(wordAndCount);
        // 3. 返回出现次数最多的单词组成的数组
        return getKMoreCountWord(countAndWordsList, k);
    }

    private TreeMap<Integer, List<String>> getCountAndWordsList(HashMap<String, Integer> wordAndCount) {
        // 保存出现次数相同的单词为一个链表
        TreeMap<Integer, List<String>> countAndWordsList = new TreeMap<>((o1, o2) -> o2 - o1);
        // wordAndCount 中的所有 word 组成的集合
        Set<String> wordSet = wordAndCount.keySet();
        // 2.将单词出现次数 (作为 key) 相同的单词组成一个链表, 作为 value 保存在 TreeMap 中, 并根据单词出现次数由大到小排序
        for (String word : wordSet) {
            // 获取单词 word 出现的次数
            int wordCount = wordAndCount.get(word);
            // 出现此次数的所有单词组成的单词链表, 不为 null 时, 将该单词添加进这个链表中
            List<String> wordList = countAndWordsList.get(wordCount);
            // wordList 为 null 时, 说明原 countAndWordsList 中还没有出现当前次数的单词
            // 现要新添加这个键值对入 countAndWordsList 中, 此时需要先新建链表
            if (wordList == null) {
                List<String> newWordList = new ArrayList<>();
                newWordList.add(word);
                countAndWordsList.put(wordCount, newWordList);
            } else {
                // wordList 不为 null 时, 直接添加
                wordList.add(word);
            }
        }
        return countAndWordsList;
    }

    private String[] getKMoreCountWord(TreeMap<Integer, List<String>> countAndWordsList, int k) {
        // 输出结果数组
        String[] ans = new String[k];
        // 下标计数
        int i = 0;
        // 3.将出现次数最多的单词保存到输出结果 ans 中
        Set<Integer> countSet = countAndWordsList.keySet();
        for (Integer moreCount : countSet) {
            // 率先遍历出现次数更多的单词链表
            List<String> moreCountWordList = countAndWordsList.get(moreCount);
            // 遍历 moreCountWordList 前需要将其中的单词按照字典序排序, 再取出排在前面的相应个数的单词保存到输出结果 ans 中
            Collections.sort(moreCountWordList);
            /*
            // 可修改为直接返回
            for (String word : moreCountWordList) {
                if (i < k) {
                    ans[i] = word;
                    i++;
                } else {
                    break;
                }
            }
            if (i == k) {
                break;
            }
            */
            for (String word : moreCountWordList) {
                if (i < k) {
                    ans[i] = word;
                    i++;
                } else {
                    return ans;
                }
            }
        }
        return ans;
    }
}

// 2022-02-23
// 创建 Set<Map.Entry<String, Integer>>, 重写 compare 方法, 直接添加 wordAndCount.entrySet() 集合即可自动排序
class ODInterview_CD_II_2022_01_27_Solution {
    public String[] func(String[] str, int k) {
        // 1.保存各单词及其出现次数
        Map<String, Integer> wordAndCount = new HashMap<>();
        for (String word : str) {
            wordAndCount.put(word, wordAndCount.getOrDefault(word, 0) + 1);
        }
        // 2.对各单词及其出现次数排序, 需要重写 compare 方法
        Set<Map.Entry<String, Integer>> sortedWordAndCountSet = new TreeSet<>((o1, o2) -> {
            if (o2.getValue() - o1.getValue() > 0) {
                return 1;
            } else if (o2.getValue() - o1.getValue() < 0) {
                return -1;
            } else {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        // 添加后就完成了按照单词出现频率(由大到小)排序以及字典序(由小到大)的排序
        sortedWordAndCountSet.addAll(wordAndCount.entrySet());

        // 输出结果数组
        String[] ans = new String[k];
        // 下标计数
        int i = 0;
        // 3.将出现次数最多的单词保存到输出结果 ans 中, 即前 k 个满足条件
        Iterator<Map.Entry<String, Integer>> iterator = sortedWordAndCountSet.iterator();
        while (iterator.hasNext() && i < k) {
            ans[i] = iterator.next().getKey();
            i++;
        }
        return ans;
    }
}