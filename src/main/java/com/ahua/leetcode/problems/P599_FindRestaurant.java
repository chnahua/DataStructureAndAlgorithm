package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-14 22:20
 */

import java.util.*;

/**
 * 599. 两个列表的最小索引总和 minimum-index-sum-of-two-lists
 * 假设 Andy 和 Doris 想在晚餐时选择一家餐厅，并且他们都有一个表示最喜爱餐厅的列表，每个餐厅的名字用字符串表示。
 * <p>
 * 你需要帮助他们用最少的索引和找出他们共同喜爱的餐厅。 如果答案不止一个，则输出所有答案并且不考虑顺序。 你可以假设答案总是存在。
 * <p>
 * 1 <= list1.length, list2.length <= 1000
 * 1 <= list1[i].length, list2[i].length <= 30
 * list1[i] 和 list2[i] 由空格 ' ' 和英文字母组成。
 * list1 的所有字符串都是 唯一 的。
 * list2 中的所有字符串都是 唯一 的。
 */
public class P599_FindRestaurant {
    public static void main(String[] args) {
        String[] list1 = new String[]{"Shogun", "Tapioca Express", "Burger King", "KFC"};
        String[] list2 = new String[]{"Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"};
        String[] list3 = new String[]{"KFC", "Shogun", "Burger King"};
        P599_Solution solution = new P599_Solution();
        System.out.println(Arrays.toString(solution.findRestaurant(list1, list2))); // ["Shogun"]
        System.out.println(Arrays.toString(solution.findRestaurant(list1, list3))); // ["Shogun"]
        String[] list4 = new String[]{"A", "B", "C"};
        String[] list5 = new String[]{"D", "E", "F", "G", "H", "C"};
        System.out.println(Arrays.toString(solution.findRestaurant(list4, list5))); // [C]
        String[] list6 = new String[]{"a", "c", "v", "f", "b"};
        String[] list7 = new String[]{"a", "b", "c", "d"};
        System.out.println(Arrays.toString(solution.findRestaurant(list6, list7))); // [a]
    }
}

// 哈哈, 漂亮, 又是一次通过, 不过花的时间比较久, 80 分钟了
// 有些逻辑可能不严谨
// 哈希表
// 4 ms 100.00%
// 42.3 MB 5.23%
class P599_Solution1 {
    // 原始代码
    public String[] findRestaurant(String[] list1, String[] list2) {
        String[] shortList = list1;
        String[] longList = list2;
        if (list1.length > list2.length) {
            shortList = list2;
            longList = list1;

        }
        int n1 = shortList.length;
        int n2 = longList.length;
        int indexSum = Integer.MAX_VALUE;
        List<String> ansList = new ArrayList<>();
        Map<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < n1 && i <= indexSum; i++) {
            if (shortList[i].equals(longList[i])) {
                hashMap.put(shortList[i], i);
                if (i + i < indexSum) {
                    ansList.clear();
                    ansList.add(shortList[i]);
                    indexSum = i + i;
                } else if (i + i == indexSum) {
                    ansList.add(shortList[i]);
                }
                continue;
            }
            Integer index1 = hashMap.get(shortList[i]);
            Integer index2 = hashMap.get(longList[i]);
            if (index1 == null && index2 == null) {
                hashMap.put(shortList[i], i);
                hashMap.put(longList[i], i);
            } else {
                if (index1 != null) {
                    if (index1 + i < indexSum) {
                        ansList.clear();
                        ansList.add(shortList[i]);
                        indexSum = index1 + i;
                    } else if (index1 + i == indexSum) {
                        ansList.add(shortList[i]);
                    }
                }
                if (index2 != null) {
                    if (index2 + i < indexSum) {
                        ansList.clear();
                        ansList.add(longList[i]);
                        indexSum = index2 + i;
                    } else if (index2 + i == indexSum) {
                        ansList.add(longList[i]);
                    }
                }
            }
        }
        for (int i = n1; i < n2 && i <= indexSum; i++) {
            Integer index = hashMap.get(longList[i]);
            if (index != null) {
                if (index + i < indexSum) {
                    ansList.clear();
                    ansList.add(longList[i]);
                    indexSum = index + i;
                } else if (index + i == indexSum) {
                    ansList.add(longList[i]);
                }
            }
        }
        int n = ansList.size();
        String[] ans = new String[n];
        for (int i = 0; i < n; i++) {
            ans[i] = ansList.get(i);
        }
        return ans;
    }

}

// 不改变解题思路, 只优化代码逻辑
// 哈希表
// 4 ms 100.00%
// 41.7 MB 43.77%
class P599_Solution {
    int indexSum;
    List<String> ansList;

    public String[] findRestaurant(String[] list1, String[] list2) {
        // 较短的字符串数组
        String[] shortList = list1;
        // 较长的字符串数组
        String[] longList = list2;
        if (list1.length > list2.length) {
            shortList = list2;
            longList = list1;
        }
        int n1 = shortList.length;
        int n2 = longList.length;
        indexSum = Integer.MAX_VALUE;
        ansList = new ArrayList<>();
        // 保存第一次出现的字符串及其位置索引
        Map<String, Integer> hashMap = new HashMap<>();
        // 同时遍历两个数组, 直到先遍历完短数组
        for (int i = 0; i < n1 && i <= indexSum; i++) {
            // 如果两个字符串数组中的同一个位置的两个字符串相同则特殊处理
            // 后来才发现其逻辑和下面的代码是一样的, 也就是说不用提前特殊处理
            // 即这段代码可有可无, 并且删掉的话, 理论少还会提高效率, 但是 LeetCode 上并没有表现出来
//            if (shortList[i].equals(longList[i])) {
//                hashMap.put(shortList[i], i);
//                updateAnsListAndIndexSum(shortList, i, i);
//                continue;
//            }
            Integer index1 = hashMap.get(shortList[i]);
            if (index1 != null) {
                updateAnsListAndIndexSum(shortList, i, index1);
            } else {
                hashMap.put(shortList[i], i);
            }
            Integer index2 = hashMap.get(longList[i]);
            if (index2 != null) {
                updateAnsListAndIndexSum(longList, i, index2);
            } else {
                hashMap.put(longList[i], i);
            }
        }
        // 接着遍历长数组中的剩余字符串
        for (int i = n1; i < n2 && i <= indexSum; i++) {
            Integer index = hashMap.get(longList[i]);
            if (index != null) {
                updateAnsListAndIndexSum(longList, i, index);
            }
            // 此处 longList 中第一次出现的字符串就不用加入到 hashMap 中了(可加可不加)
        }
//        int n = ansList.size();
//        String[] ans = new String[n];
//        for (int i = 0; i < n; i++) {
//            ans[i] = ansList.get(i);
//        }
//        return ans;
        // 原来是这个方法
        // ansList.toArray(new String[ansList.size()]);
        return ansList.toArray(new String[0]);
    }

    /**
     * @param list  长短字符串数组
     * @param i     当前正在遍历的餐厅名的下标索引
     * @param index 哈希表中餐厅名第一次出现的下标索引
     */
    private void updateAnsListAndIndexSum(String[] list, int i, int index) {
        if (index + i < indexSum) {
            ansList.clear();
            ansList.add(list[i]);
            indexSum = index + i;
        } else if (index + i == indexSum) {
            ansList.add(list[i]);
        }
    }
}