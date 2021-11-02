# LeetCode 笔记

## 算法理论/问题/区别

###### 1.递归、回溯和深度优先搜索(DFS)的区别？动态规划？

从第<a href="#509-斐波那契数">509</a>题貌似可以看到动态规划其实在有些时候是递归的一种优化？减少递归过程中的计算次数？

## Problems

### 算法分类

#### 排序算法

| 排序算法 | 字符串 | 数组 |               （单向）链表                |  栈  | 队列 | 树（二叉树） |
| :------: | :----: | :--: | :---------------------------------------: | :--: | :--: | :----------: |
| 冒泡排序 |        |      |                                           |      |      |              |
| 选择排序 |        |      |                                           |      |      |              |
| 插入排序 |        |      | <a href="#147-对链表进行插入排序">147</a> |      |      |              |
| 希尔排序 |        |      |                                           |      |      |              |
| 快速排序 |        |      |                                           |      |      |              |
| 归并排序 |        |      |                                           |      |      |              |
| 基数排序 |        |      |                                           |      |      |              |
|  堆排序  |        |      |                                           |      |      |              |

#### 查找算法

| 查找算法 |               数组                |
| :------: | :-------------------------------: |
| 线性查找 |                                   |
| 二分查找 | <a href="#35-搜索插入位置">35</a> |
| 插值查找 |                                   |

> 斐波那契（黄金分割法）查找

#### 基础算法

> 使用单一算法就能解决的问题

|       算法       | 字符串 | 一维数组 |                           二维数组                           |             （单向）链表             |  栈  |              队列               | 树（二叉树） |  图  |               其它                |
| :--------------: | :----: | :------: | :----------------------------------------------------------: | :----------------------------------: | :--: | :-----------------------------: | :----------: | :--: | :-------------------------------: |
|       递归       |        |          |                                                              |                                      |      |                                 |              |      | <a href="#509-斐波那契数">509</a> |
|       回溯       |        |          |                                                              |                                      |      |                                 |              |      |                                   |
|     分治算法     |        |          |                                                              | <a href="#23-合并K个升序链表">23</a> |      |                                 |              |      |                                   |
|     动态规划     |        |          | <a href="#62-不同路径">62</a>、<a href="#980-不同路径 III">980</a> |                                      |      |                                 |              |      | <a href="#509-斐波那契数">509</a> |
|     贪心算法     |        |          |                                                              |                                      |      |                                 |              |      |                                   |
| 深度优先搜索 DFS |        |          |               <a href="#200-岛屿数量">200</a>                |                                      |      |                                 |              |      |                                   |
| 广度优先搜索 BFS |        |          |                                                              |                                      |      | <a href="#200-岛屿数量">200</a> |              |      |                                   |
|    普里姆算法    |        |          |                                                              |                                      |      |                                 |              |      |                                   |
|  克鲁斯卡尔算法  |        |          |                                                              |                                      |      |                                 |              |      |                                   |
|  迪杰斯特拉算法  |        |          |                                                              |                                      |      |                                 |              |      |                                   |
|    回溯 + DFS    |        |          |             <a href="#980-不同路径 III">980</a>              |                                      |      |                                 |              |      |                                   |

> 动态规划可以通过滚动数组思想减小空间复杂度，如题509，

#### 多种算法

> 同时使用到多种算法才能解决的问题，大部分这类题都属于困难题了

|       算法       | 字符串 | 一维数组 | 二维数组                            | （单向）链表 |  栈  | 队列 | 树（二叉树） |  图  |
| :--------------: | :----: | :------: | ----------------------------------- | :----------: | :--: | :--: | :----------: | :--: |
|       递归       |        |          |                                     |              |      |      |              |      |
|       回溯       |        |          |                                     |              |      |      |              |      |
| 深度优先搜索 DFS |        |          |                                     |              |      |      |              |      |
| 广度优先搜索 BFS |        |          |                                     |              |      |      |              |      |
|    回溯 + DFS    |        |          | <a href="#980-不同路径 III">980</a> |              |      |      |              |      |

#### 其它技巧

| 其它技巧 |                 字符串                  | 一维数组 |                 二维数组                  |                           其它                            |                             解释                             |
| :------- | :-------------------------------------: | -------- | :---------------------------------------: | :-------------------------------------------------------: | :----------------------------------------------------------: |
| 数学公式 |                                         |          | <a href="#62-不同路径">62</a>（组合数学） | <a href="#509-斐波那契数">509</a>（矩阵快速幂、通项公式） | 有些题根据题意可以使用数学上的某些公式或现有定理直接计算得出答案 |
| 滑动窗口 | <a href="#3-无重复字符的最长子串">3</a> |          |                                           |                                                           |                                                              |
|          |                                         |          |                                           |                                                           |                                                              |

### 题目记录

| 题目编号                                                     |   数据结构    |        算法        | 难度 | 初次完成时间 | 最近完成时间 | 完成次数 | 第一次是否独立完成 | 最近一次完成是否是独立完成 | 是否有不同于官方答案的解题思路 | 是否需要重点复习(ABCD四个等级) |                             备注                             |                       页内跳转                        |
| :----------------------------------------------------------- | :-----------: | :----------------: | :--: | :----------: | :----------: | :------: | ------------------ | :------------------------: | :----------------------------: | :----------------------------: | :----------------------------------------------------------: | :---------------------------------------------------: |
| [3. 无重复字符的最长子串](https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/) |    字符串     |      滑动窗口      | 中等 |  2021.10.19  |  2021.10.19  |    1     | 是                 |             是             |               是               |               C                |        2021.10.19_很巧合，与答案思想差不多，实现不同         |        <a href="#3-无重复字符的最长子串">3</a>        |
| [23. 合并K个升序链表](https://leetcode-cn.com/problems/merge-k-sorted-lists/) |     链表      |        分治        | 困难 |  2021.10.18  |  2021.10.18  |    1     | 是                 |             是             |               否               |               A                |           2021.10.18_思路一致，具体代码实现有差别            |         <a href="#23-合并K个升序链表">23</a>          |
| [35. 搜索插入位置](https://leetcode-cn.com/problems/search-insert-position/) |     数组      |      二分查找      | 简单 |  2021.10.20  |  2021.10.20  |    1     | 是                 |             是             |               是               |               C                | 2021.10.20_思考的不够简洁，但是挺有逻辑性，官方的或者他人的要简洁些，但是理解上需要总结一下规律 |           <a href="#35-搜索插入位置">35</a>           |
| [62. 不同路径](https://leetcode-cn.com/problems/unique-paths/) |     数组      | 动态规划/组合数学  | 中等 |  2021.10.30  |  2021.10.30  |    1     | 是                 |             是             |               是               |               B                | 2021.10.30_一开始完全没想到是动态规划，以为是递归回溯，但是知道是动态规划后就一下子知道怎么做了 |             <a href="#62-不同路径">62</a>             |
| [63. 不同路径 II](https://leetcode-cn.com/problems/unique-paths-ii/) |     数组      |      动态规划      | 中等 |  2021.10.30  |  2021.10.30  |    1     | 是                 |             是             |               是               |               B                |      2021.10.30_和上一题类似，难了一些，多了些条件判断       |           <a href="#63-不同路径 II">63</a>            |
| [104. 二叉树的最大深度](https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/) |    二叉树     |      DFS、BFS      | 简单 |  2021.10.29  |  2021.10.29  |    1     | 是                 |             是             |               否               |               C                | 2021.10.29_这道题很简单，之前做过比这道题更难的相似的题，所以完成不难 |        <a href="#104-二叉树的最大深度">104</a>        |
| [105. 从前序与中序遍历序列构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) |    二叉树     |        递归        | 中等 |  2021.09.19  |  2021.09.19  |    1     | 否                 |             否             |               否               |               A                |                                                              | <a href="#105-从前序与中序遍历序列构造二叉树">105</a> |
| [106. 从中序与后序遍历序列构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/) |    二叉树     |        递归        | 中等 |  2021.09.22  |  2021.09.22  |    1     | 否                 |             否             |               否               |               A                |                                                              | <a href="#106-从中序与后序遍历序列构造二叉树">106</a> |
| [112. 路径总和](https://leetcode-cn.com/problems/path-sum/)  |    二叉树     |     递归、DFS      | 简单 |  2021.04.26  |  2021.10.22  |    2     | 否                 |             是             |               否               |              A->C              |      2021.10.22_哈哈，这次做的逻辑简化后和答案一模一样       |           <a href="##112-路径总和">112</a>            |
| [113. 路径总和 II](https://leetcode-cn.com/problems/path-sum-ii/) |    二叉树     |      DFS、BFS      | 中等 |  2021.10.23  |  2021.10.23  |    1     | 否                 |             否             |               否               |               B                |   2021.10.23_可以尝试做，但是没有明确思路，就按照答案做了    |          <a href="#113-路径总和 II">113</a>           |
| [146. LRU 缓存机制](https://leetcode-cn.com/problems/lru-cache/) | 哈希表+双链表 |                    | 中等 |  2021.10.15  |  2021.10.16  |    2     | 否                 |             否             |               是               |               A                |                                                              |          <a href="#146-LRU 缓存机制">146</a>          |
| [147. 对链表进行插入排序](https://leetcode-cn.com/problems/insertion-sort-list/) |     链表      |      插入排序      | 中等 |  2021.10.26  |  2021.10.26  |    1     | 是                 |             是             |               是               |               B                | 2021.10.26_同样都是实现插入排序，整体思路一致，但也有些差别，代码实现自然也就不同了 |       <a href="#147-对链表进行插入排序">147</a>       |
| [200. 岛屿数量](https://leetcode-cn.com/problems/number-of-islands/) |     数组      |      DFS、BFS      | 中等 |  2021.11.01  |  2021.11.01  |    1     | 是                 |             是             |               否               |               A                | 2021.11.01_由于今天是先做了P980再做的此题，使用DFS，方便了许多，和官方答案的DFS思路与代码差不多，实现略微不同 |            <a href="#200-岛屿数量">200</a>            |
| [237. 删除链表中的节点](https://leetcode-cn.com/problems/delete-node-in-a-linked-list/) |     链表      |    链表基础操作    | 简单 |  2021.11.02  |  2021.11.02  |    1     | 是                 |             是             |               否               |               D                | 2021.11.02_差点没读懂题目，虽然做出来了，但是还是不够答案聪明，做得复杂了一点点 |        <a href="#237-删除链表中的节点">237</a>        |
| [344. 反转字符串](https://leetcode-cn.com/problems/reverse-string/) |     数组      |       双指针       | 简单 |  2021.05.09  |  2021.10.31  |    2     | 是                 |             是             |               是               |               D                | 2021.10.31_看答案前我想着超简单，就是简单的数组头尾数据交换，以后可以不再复习，然而这题官方答案居然使用了双指针，这种思想我没想到，看来还是得要注意下 |           <a href="#344-反转字符串">344</a>           |
| [509. 斐波那契数](https://leetcode-cn.com/problems/fibonacci-number/) |    数学题     |   递归、动态规划   | 简单 |  2021.11.02  |  2020.11.02  |    1     | 是                 |             是             |               否               |               D                | 2021.11.02_以前都没有想到过可以用动态规划做这个题，一直以为是递归就行了，但是现在才发现不仅如此，还可以有官方答案的使用矩阵或者特征方程等方式作答 |           <a href="#509-斐波那契数">509</a>           |
| [980. 不同路径 III](https://leetcode-cn.com/problems/unique-paths-iii/) |     数组      | 回溯+DFS、动态规划 | 困难 |  2021.11.01  |  2021.11.01  |    1     | 否                 |             否             |               否               |               A                |   2021.11.01_还是看别人的做法完成的，官方做法还有些看不懂    |          <a href="#980-不同路径 III">980</a>          |
| [1857. 有向图中最大颜色值](https://leetcode-cn.com/problems/largest-color-value-in-a-directed-graph/) |      图       |    动态规划+BFS    | 困难 |  2021.10.15  |  2021.10.15  |    1     | 否                 |             否             |               否               |               A                |                                                              |      <a href="#1857-有向图中最大颜色值">1857</a>      |

> 页内跳转：#后 字符省略，空格变为 -
>
> <a href="#"></a>

#### 数组

| 题目编号                                                     | 数据结构——数组 |    算法     | 难度 | 初次完成时间 | 最近完成时间 | 完成次数 | 第一次是否独立完成 | 最近一次完成是否是独立完成 | 是否有不同于官方答案的解题思路 | 是否需要重点复习(ABCD四个等级) |                             备注                             |             页内跳转              |
| :----------------------------------------------------------- | :------------: | :---------: | :--: | :----------: | :----------: | :------: | ------------------ | :------------------------: | :----------------------------: | :---------------------------: | :----------------------------------------------------------: | :-------------------------------: |
| [15. 三数之和](https://leetcode-cn.com/problems/3sum/)       |      数组      | 排序+双指针 | 中等 |  2021.10.21  |  2021.10.21  |    1     | 否                 |             否             |               否               |               C               | 2021.10.21_一开始完全没有头绪，只能想到暴力破解，最后去重，于是直接查看答案，并按照答案写出来 |   <a href="#15-三数之和">15</a>   |
| [35. 搜索插入位置](https://leetcode-cn.com/problems/search-insert-position/) |      数组      |  二分查找   | 简单 |  2021.10.20  |  2021.10.20  |    1     | 是                 |             是             |               是               |               C               | 2021.10.20_思考的不够简洁，但是挺有逻辑性，官方的或者他人的要简洁些，但是理解上需要总结一下规律 | <a href="#35-搜索插入位置">35</a> |
| [739. 每日温度](https://leetcode-cn.com/problems/daily-temperatures/) |     数组      |    单调栈    | 中等 |  2021.10.24  |  2021.10.24  |    1     | 是                 |             是             |               是               |               B               | 2021.10.24_用了比官方暴力解法更为暴力的方法哈哈哈 |            <a href="#739-每日温度">739</a>            |

### 题目整理

#### [3. 无重复字符的最长子串](https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/)

```java
class P3_Solution {
    /**
     * @param s 要查找的字符串
     * @return maxLen 字符串 s 中不含有重复字符的 最长子串 的长度
     */
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        } else if (s.length() == 1) {
            return 1;
        }
        StringBuilder longestSubstring = new StringBuilder();
        // 初始化 将字符串 s 中的第一个字符加入子串中
        longestSubstring.append(s.charAt(0));
        // 初始化 最大长度 为 1 或者 0 均可
        int maxLen = 1;
        // 字符串中待比较的字符下标
        int indexStr = 1;
        // 字符串中待比较的字符在子串中的下标 如果有,返回下标;如果没有,返回 -1
        int indexSub = 0;
        while (indexStr < s.length()) {
            // indexSub = longestSubstring.indexOf(s.charAt(indexStr) + ""); // 效率不及下者
            // indexSub = longestSubstring.indexOf(String.valueOf(s.charAt(indexStr))); // 效率不及下者
            indexSub = longestSubstring.toString().indexOf(s.charAt(indexStr));
            // 如果子串中没有找到该字符,将该字符加入到子串中,并且 indexStr++,比较下一个字符
            if (indexSub == -1) {
                longestSubstring.append(s.charAt(indexStr));
                indexStr++;
            } else {
                // 如果子串中找到了该字符,说明此时子串为当前的最大子串,maxLen 为前一个 maxLen 和 当前子串长度 两者中的较大值
                maxLen = Math.max(maxLen, longestSubstring.length());
                // 在子串中删除找到的字符以前的所有字符(包括这个字符)
                // 例如 s = abcdbe 中,当比较到第二个 b 时,子串为 abcd, 重复的字符为 b, 删除 ab, 即(包含) indexSub 之前的所有字符
                // 删除后为 cd
                longestSubstring.delete(0, indexSub + 1);
                // 再在末尾加上这个重复的字符 即此时子串为 cdb
                longestSubstring.append(s.charAt(indexStr));
                // 继续比较下一个
                indexStr++;
            }
        }
        return Math.max(maxLen, longestSubstring.length());
    }
}
```

#### [15. 三数之和](https://leetcode-cn.com/problems/3sum/)

```java
class P15_Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        int len = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        if (len < 3) {
            return ans;
        }
        Arrays.sort(nums);
        if (nums[0] > 0) {
            return ans;
        }
        int first, second, third;
        // 枚举 a
        for (first = 0; first < len; first++) {
            // 和上一次枚举的数不相同
            if(first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // c 对应的指针初始指向数组的最右端
            third = len - 1;
            // 枚举 b c
            for (second = first + 1; second < len; second++) {
                // 和上一次枚举的数不相同
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                // 保证 b 的指针在 c 的指针的左侧
                while (second < third && nums[second] + nums[third] > -nums[first]) {
                    third--;
                }
                // 如果指针重合，随着 b 后续的增加
                // 就不会有满足 a+b+c=0 并且 b<c 的 c 了,可以退出循环
                if (second == third) {
                    break;
                }
                // 找到
                if (nums[second] + nums[third] == -nums[first]) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }
            }
        }
        return ans;
    }
}
```



#### [23. 合并K个升序链表](https://leetcode-cn.com/problems/merge-k-sorted-lists/)

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class P23_Solution {
    // 分治合并
    public ListNode mergeKLists(ListNode[] lists) {
        int k = lists.length;
        // 链表为空，直接返回
        if (k == 0) {
            return null;
        }

        while (true) {
            if (k == 1) {
                return lists[0];
            } else if (k == 2) {
                return mergeTwoLists(lists[0], lists[k - 1]);
            }
            // 有奇数个链表,先将最后一个链表合并到第一个链表中,就可以看作有偶数个链表
            if (k % 2 == 1) {
                lists[0] = mergeTwoLists(lists[0], lists[k - 1]);
            }
            k = k / 2;
            // 将偶数(2k)个链表分别两两合并(第 i 个和第 i + k 个)成为前(k)个链表, 类似于归并排序——分治
            for (int i = 0; i < k; i++) {
                lists[i] = mergeTwoLists(lists[i], lists[i + k]);
            }
        }
    }

    // 顺序合并
    public ListNode mergeKLists2(ListNode[] lists) {
        int len = lists.length;
        if (len == 0) {
            // 链表为空,直接返回
            return null;
        } else if (len == 1) {
            // 只有一个链表,直接返回该链表
            return lists[0];
        }

        // 将其它链表依次插入到第一个链表中，类似于插入排序,性能不如前一个
        for (int i = 1; i < len; i++) {
            lists[0] = mergeTwoLists(lists[0], lists[i]);
        }

        return lists[0];
    }

    // 我的 mergeTwoLists
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            list1 = list2;
            return list1;
        } else if (list2 == null) {
            return list1;
        }

        ListNode fromList;
        ListNode toList;
        // 将两个链表中的第一个节点值较小的那个链表认为被插入链表 toList
        if (list1.val <= list2.val) {
            fromList = list2;
            toList = list1;
        } else {
            fromList = list1;
            toList = list2;
        }
        // cur 初始化为被插入链表 toList 的第一个节点
        ListNode cur = toList;
        ListNode pre = new ListNode();
        // pre 指向被插入链表 toList 的第一个节点
        pre.next = cur;
        ListNode temp;
        // 将 fromList 链表中的元素插入到 toList 链表中
        while (fromList != null && cur != null) {
            // toList 链表中的当前节点的值小于等于当前 fromList 链表中的第一个节点的值时
            // 改变 toList 链表中的 pre 和 cur 指针
            if (cur.val <= fromList.val) {
                pre = cur;
                cur = cur.next;
            } else {
                // 此时,fromList 链表中的第一个节点的值小于 cur 节点处的值,大于等于 pre 节点处的值
                // 将 fromList 链表中的第一个节点 插入到 pre 和 cur 之间
                temp = fromList.next;
                pre.next = fromList;
                fromList.next = cur;
                pre = fromList;
                fromList = temp;
            }
        }
        if (cur == null) {
            pre.next = fromList;
        }
        return toList;
    }

    // 官方的 mergeTwoLists, 实现更简洁
    public ListNode mergeTwoLists1(ListNode list1, ListNode list2) {
        if (list1 == null || list2 == null) {
            return list1 != null ? list1 : list2;
        }

        ListNode head = new ListNode();
        ListNode tail = head;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }

        tail.next = list1 == null ? list2 : list1;
        return head.next;
    }
}
```

#### [35. 搜索插入位置](https://leetcode-cn.com/problems/search-insert-position/)

```java
class P35_Solution {
    public int searchInsert1(int[] nums, int target) {
        if (nums.length == 1) {
            if (nums[0] >= target) {
                return 0;
            } else {
                return 1;
            }
        }
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }
        }
        if (mid == 0) {
            if (nums[0] > target) {
                return 0;
            } else if (nums[1] > target) {
                return 1;
            }
        } else if (mid == nums.length - 1) {
            if (nums[mid] < target) {
                return mid + 1;
            } else if (nums[mid - 1] < target) {
                return mid;
            }
        } else {
            if (nums[mid - 1] > target) {
                return mid - 1;
            } else if (nums[mid] > target) {
                return mid;
            } else if (nums[mid + 1] > target) {
                return mid + 1;
            }
        }
        return 0;
    }

    public int searchInsert(int[] nums, int target) {
        if (nums.length == 1) {
            if (nums[0] >= target) {
                return 0;
            } else {
                return 1;
            }
        }
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }
        }
        // left > right 的情形, 数组中无目标值
        // 表示最后一步从 right = middle - 1 分支跳出, 也即 nums[middle] > target, 重新插入位置即为 mid
        if (right < mid) {
            return mid;
        }
        // 表示 nums[mid] < target, 重新插入位置为 mid + 1, 此时的 mid + 1 也等于 left
        return mid + 1;
        // 也等价于 return right == mid - 1 ? mid : mid + 1;
        // 也等价于 return left == mid + 1 ? left : mid;
    }
}
```

#### [62. 不同路径](https://leetcode-cn.com/problems/unique-paths/)

```java
public class P62_UniquePaths {
    public static void main(String[] args) {
        int m = 3;
        int n = 3;
        P62_Solution solution = new P62_Solution();
        System.out.println(solution.uniquePaths(m, n));
        System.out.println(solution.uniquePaths1(m, n));
        System.out.println(solution.uniquePaths2(m, n));
    }
}

class P62_Solution {
    // 简单的动态规划
    // 时间复杂度：O(mn)
    // 空间复杂度：O(mn)
    public int uniquePaths(int m, int n) {
        int[][] arr = new int[m + 1][n + 1];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 && j == n - 1) {
                    arr[m - 1][n - 1] = 1;
                } else {
                    arr[i][j] = arr[i + 1][j] + arr[i][j + 1];
                }
            }
        }
        return arr[0][0];
    }

    // 动态规划, 上一个方法的等价, 不用每次循环内判断
    // 此方法和代码实现与官方答案一极为类似, 可以等价
    // 时间复杂度：O(mn)
    // 空间复杂度：O(mn)
    public int uniquePaths1(int m, int n) {
        // 只有一行, 返回 1
        if (m == 1) {
            return 1;
        }
        int[][] arr = new int[m][n];
        // 最后一行置为 1
        for (int j = 0; j < n; j++) {
            arr[m - 1][j] = 1;
        }
        // 最后一列置为 1
        for (int i = 0; i < m; i++) {
            arr[i][n - 1] = 1;
        }
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                arr[i][j] = arr[i + 1][j] + arr[i][j + 1];
            }
        }
        return arr[0][0];
    }

    // 优化上一个方法的空间复杂度, 只用一个一维数组来记录路径长度,
    // 并且由于交换m,n的值对答案无影响, 因此该数组长度最好为 min(m,n)
    // 时间复杂度：O(mn)
    // 空间复杂度：O(min(m,n))
    public int uniquePaths2(int m, int n) {
        // 只有一行, 返回 1
        if (m == 1) {
            return 1;
        }
        // m 为较大值(代表行), n 为较小值(代表列)
        if (m < n) {
            int temp = m;
            m = n;
            n = temp;
        }
        int[] arr = new int[n];
        arr[n - 1] = 1;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                arr[j] += +arr[j + 1];
            }
        }
        return arr[0];
    }
}
```

#### [63. 不同路径 II](https://leetcode-cn.com/problems/unique-paths-ii/)

```java
class P63_Solution {
    // 时间复杂度: O(nm), 其中 m 为网格的行数, n 为网格的列数, 只需要遍历所有网格一次即可
    // 空间复杂度: O(m)O(m), 利用滚动数组优化, 可以只用 O(n) 大小的空间来记录当前行到右下角的 path 值
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // 行
        int m = obstacleGrid.length;
        // 列
        int n = obstacleGrid[0].length;
        // 右下角为障碍物, 返回 0
        if (obstacleGrid[m - 1][n - 1] == 1) {
            return 0;
        }
        // 只有一行且无障碍物, 返回 1
        if (m == 1) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[0][j] == 1) {
                    return 0;
                }
            }
            return 1;
        }
        // 只有一列且无障碍物, 返回 1
        if (n == 1) {
            for (int i = 0; i < m; i++) {
                if (obstacleGrid[i][0] == 1) {
                    return 0;
                }
            }
            return 1;
        }

        // 「滚动数组思想」把空间复杂度优化成 O(n)
        int[] dp = new int[n];
        dp[n - 1] = 1;

        for (int i = m - 1; i >= 0; i--) {
            // 等价于 dp[n - 1] = (obstacleGrid[i][n - 1] != 1) ? dp[n - 1] : 0;
            if (obstacleGrid[i][n - 1] == 1) {
                dp[n - 1] = 0;
            }
            for (int j = n - 2; j >= 0; j--) {
                // 等价于 dp[j] = (obstacleGrid[i][j] != 1) ? dp[j] + dp[j + 1] : 0;
                // 有障碍物, 可提前结束循环
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0;
                    continue;
                }
                dp[j] += dp[j + 1];
            }
        }
        return dp[0];
//        // 也可以为
//        // 「滚动数组思想」把空间复杂度优化成 O(n)
//        int[] dp = new int[n + 1];
//        dp[n - 1] = 1;
//
//        for (int i = m - 1; i >= 0; i--) {
//            for (int j = n - 1; j >= 0; j--) {
//                // 等价于 dp[j] = (obstacleGrid[i][j] != 1) ? dp[j] + dp[j + 1] : 0;
//                if (obstacleGrid[i][j] == 1) {
//                    dp[j] = 0;
//                    continue;
//                }
//                dp[j] += dp[j + 1];
//            }
//        }
//        return dp[0];
    }
}
```



#### [104. 二叉树的最大深度](https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */

class P104_Solution {
    // DFS
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else if (root.left == null && root.right == null) {
            return 1;
        }
        int leftDepth = 0;
        int rightDepth = 0;
        // 左子树的深度
        if (root.left != null) {
            leftDepth = maxDepth(root.left);
        }
        // 右子树的深度
        if (root.right != null) {
            rightDepth = maxDepth(root.right);
        }
        return Math.max(leftDepth, rightDepth) + 1;
    }

    // DFS 对上方法的逻辑简化
    public int maxDepth1(TreeNode root) {
        if (root == null) {
            return 0;
        } else if (root.left == null && root.right == null) {
            return 1;
        }
        return Math.max(maxDepth1(root.left), maxDepth1(root.right)) + 1;
    }
}
```



#### [105. 从前序与中序遍历序列构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    // 必须放在这里，后续两方法中都要使用到这个属性
    // 使用k,v记录中序遍历数组中各节点值与其索引值,通过前序遍历中的第一个值就为头节点,然后在该map中找到对应中序遍历数组中的该值的下标
    private Map<Integer, Integer> indexMap = null;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;

        indexMap = new HashMap<Integer, Integer>();

        for(int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }

        return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);
    }

    public TreeNode myBuildTree(int[] preorder, int[] inorder, int pre_left, int pre_right, int in_left, int in_right) {
        // 一个左右子树对应的那部分数组长度大于等于1时才有节点,只有一个节点的情况是pre_left等于pre_right,那么没有节点的情况是pre_left>pre_right
        if (pre_left > pre_right) {
            return null;
        }
        // 前序遍历中第一个结点为根节点,保存其值
        int rootValPre = preorder[pre_left];
        // 中序遍历得到根节点所在位置索引值
        int rootIndexIn = indexMap.get(rootValPre);
        // 构造根节点
        TreeNode rootNode = new TreeNode(rootValPre);
        // 统计左子树中有多少个节点
        int left_size = rootIndexIn - in_left;
        // 递归遍历左子树,得到左节点
        rootNode.left = myBuildTree(preorder, inorder, pre_left + 1, pre_left + left_size, in_left, rootIndexIn - 1);
        // 递归遍历右子树,得到右节点
        rootNode.right = myBuildTree(preorder, inorder, pre_left + left_size + 1, pre_right, rootIndexIn + 1, in_right);
        return rootNode;
    }
}
```

#### [106. 从中序与后序遍历序列构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {

    private Map<Integer, Integer> indexMap = null;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int n = inorder.length;

        indexMap = new HashMap<Integer,Integer>();

        for (int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }

        return myBuildTree(inorder, postorder, 0, n - 1, 0, n - 1);
    }

    public TreeNode myBuildTree(int[] inorder, int[] postorder, int in_left, int in_right, int post_left, int post_right) {

        if (post_left > post_right) {
            return null;
        }
        // 获取后序遍历中最后一位的值,即为根节点
        int rootVal = postorder[post_right];
        // 在中序遍历MAP中找到相应rootVal所在中序遍历数组中的索引位置
        int rootIndexIn = indexMap.get(rootVal);
        // 构造根节点
        TreeNode rootNode = new TreeNode(rootVal);
        // 计算左子树长度
        int left_size = rootIndexIn - in_left;
        // 递归遍历左子树,得到左节点
        rootNode.left = myBuildTree(inorder, postorder, in_left, rootIndexIn - 1, post_left, post_left + left_size - 1);
        // 递归遍历右子树,得到右节点
        rootNode.right = myBuildTree(inorder, postorder, rootIndexIn + 1, in_right, post_left + left_size, post_right - 1);
        return rootNode;
    }
}
```



#### [112. 路径总和](https://leetcode-cn.com/problems/path-sum/)

给你二叉树的根节点 root 和一个表示目标和的整数 targetSum ，判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。

叶子节点 是指没有子节点的节点。

已定义节点

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
```

##### 2021.04.26

我的做法（第一种）

```java
class Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        return Order(root, targetSum);
    }
    
    public boolean Order(TreeNode node, int pathSum) {
        if (node == null) {
            return false;
        }
        
        pathSum = pathSum - node.val;
        boolean leftRet = false;
        boolean rightRet = false;
        
        if (node.left == null && node.right == null) {
            if(pathSum == 0) {
                return true;
            }
        }
        if (node.left != null) {
            leftRet = Order(node.left, pathSum);
        }
        if (node.right != null) {
            rightRet = Order(node.right, pathSum);
        }
        
        return leftRet || rightRet;
    }
}
```

我的方法（第二种）较第一种小改，无本质变化

```java
class Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        return Order(root, targetSum);
    }
    
    public boolean Order(TreeNode node, int pathSum) {
        if (node == null) {
            return false;
        }
        
        pathSum = pathSum - node.val;
        
        if (node.left == null && node.right == null) {
            if(pathSum == 0) {
                return true;
            }
        }
        
        return Order(node.left, pathSum) || Order(node.right, pathSum);
    }
}
```

##### 2021.10.22——第二次做——独立完成

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */

class P112_Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }
        targetSum = targetSum - root.val;
        if (root.left != null) {
            if (hasPathSum(root.left, targetSum)) {
                return true;
            }
        }
        if (root.right != null) {
            return hasPathSum(root.right, targetSum);
        }
        return false;
    }

    // 对上一个方法的简化, 发现和官方解法一样了
    public boolean hasPathSum1(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }
}
```



官方做法（递归）

思路及算法

观察要求我们完成的函数，我们可以归纳出它的功能：询问是否存在从当前节点 root 到叶子节点的路径，满足其路径和为 sum。

假定从根节点到当前节点的值之和为 val，我们可以将这个大问题转化为一个小问题：是否存在从当前节点的子节点到叶子的路径，满足其路径和为 sum - val。

不难发现这满足递归的性质，若当前节点就是叶子节点，那么我们直接判断 sum 是否等于 val 即可（因为路径和已经确定，就是当前节点的值，我们只需要判断该路径和是否满足条件）。若当前节点不是叶子节点，我们只需要递归地询问它的子节点是否能满足条件即可。

```java
class Solution {
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return sum == root.val;
        }
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }
}
```

> 复杂度分析
>
> 时间复杂度：O(N)，其中 N 是树的节点数。对每个节点访问一次。
>
> 空间复杂度：O(H)，其中 H 是树的高度。空间复杂度主要取决于递归时栈空间的开销，最坏情况下，树呈现链状，空间复杂度为 O(N)。平均情况下树的高度与节点数的对数正相关，空间复杂度为 O(log N)。

#### [113. 路径总和 II](https://leetcode-cn.com/problems/path-sum-ii/)

**给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有从根节点到叶子节点路径总和等于给定目标和的路径。**

**叶子节点 是指没有子节点的节点。**

前言
注意到本题的要求是，找到所有满足从「根节点」到某个「叶子节点」经过的路径上的节点之和等于目标和的路径。核心思想是对树进行一次遍历，在遍历时记录从根节点到当前节点的路径和，以防止重复计算。

方法一：深度优先搜索
思路及算法

我们可以采用深度优先搜索的方式，枚举每一条从根节点到叶子节点的路径。当我们遍历到叶子节点，且此时路径和恰为目标和时，我们就找到了一条满足条件的路径。

```Java
class Solution {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    Deque<Integer> path = new LinkedList<Integer>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        dfs(root, targetSum);
        return ret;
    }

    public void dfs(TreeNode root, int sum) {
        if (root == null) {
            return;
        }
        path.offerLast(root.val);
        sum -= root.val;
        if (root.left == null && root.right == null && sum == 0) {
            ret.add(new LinkedList<Integer>(path));
        }
        dfs(root.left, sum);
        dfs(root.right, sum);
        path.pollLast();
    }
}
```

> 复杂度分析
>
> 时间复杂度：O(N^2)，其中 N 是树的节点数。在最坏情况下，树的上半部分为链状，下半部分为完全二叉树，并且从根节点到每一个叶子节点的路径都符合题目要求。此时，路径的数目为 O(N)，并且每一条路径的节点个数也为 O(N)，因此要将这些路径全部添加进答案中，时间复杂度为 O(N^2)。
>
> 空间复杂度：O(N)，其中 N 是树的节点数。空间复杂度主要取决于栈空间的开销，栈中的元素个数不会超过树的节点数。

方法二：广度优先搜索
思路及算法

我们也可以采用广度优先搜索的方式，遍历这棵树。当我们遍历到叶子节点，且此时路径和恰为目标和时，我们就找到了一条满足条件的路径。

为了节省空间，我们使用哈希表记录树中的每一个节点的父节点。每次找到一个满足条件的节点，我们就从该节点出发不断向父节点迭代，即可还原出从根节点到当前节点的路径。

```java
class Solution {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    Map<TreeNode, TreeNode> map = new HashMap<TreeNode, TreeNode>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return ret;
        }

        Queue<TreeNode> queueNode = new LinkedList<TreeNode>();
        Queue<Integer> queueSum = new LinkedList<Integer>();
        queueNode.offer(root);
        queueSum.offer(0);

        while (!queueNode.isEmpty()) {
            TreeNode node = queueNode.poll();
            int rec = queueSum.poll() + node.val;

            if (node.left == null && node.right == null) {
                if (rec == targetSum) {
                    getPath(node);
                }
            } else {
                if (node.left != null) {
                    map.put(node.left, node);
                    queueNode.offer(node.left);
                    queueSum.offer(rec);
                }
                if (node.right != null) {
                    map.put(node.right, node);
                    queueNode.offer(node.right);
                    queueSum.offer(rec);
                }
            }
        }

        return ret;
    }

    public void getPath(TreeNode node) {
        List<Integer> temp = new LinkedList<Integer>();
        while (node != null) {
            temp.add(node.val);
            node = map.get(node);
        }
        Collections.reverse(temp);
        ret.add(new LinkedList<Integer>(temp));
    }
}
```

> 复杂度分析
>
> 时间复杂度：O(N^2)，其中 N 是树的节点数。分析思路与方法一相同。
>
> 空间复杂度：O(N)，其中 N 是树的节点数。空间复杂度主要取决于哈希表和队列空间的开销，哈希表需要存储除根节点外的每个节点的父节点，队列中的元素个数不会超过树的节点数。

##### 2021.10.23

第一次做，没做出来，这是答案DFS的解法

```java
class P113_Solution {
    List<List<Integer>> ans = new LinkedList<>();
    Deque<Integer> path = new LinkedList<>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        dfs(root, targetSum);
        return ans;
    }

    public void dfs(TreeNode root, int targetSum) {
        if (root == null) {
            return;
        }
        path.offerLast(root.val);
        targetSum = targetSum - root.val;
        if (root.left == null && root.right == null && targetSum == 0) {
            ans.add(new LinkedList<Integer>(path));
        }
        dfs(root.left, targetSum);
        dfs(root.right, targetSum);
        path.pollLast();
    }
}
```



#### [143. 重排链表](https://leetcode-cn.com/problems/reorder-list/)

给定一个单链表 L：L0→L1→…→Ln-1→Ln ，
将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→…

你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。

示例 1:

给定链表 1->2->3->4, 重新排列为 1->4->2->3.
示例 2:

给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3.

###### 方法一：**线性表**

因为链表不支持下标访问，所以我们无法随机访问链表中任意位置的元素。

因此比较容易想到的一个方法是，我们利用线性表存储该链表，然后利用线性表可以下标访问的特点，直接按顺序访问指定元素，重建该链表即可。

```java
class Solution {
    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        List<ListNode> list = new ArrayList<ListNode>();
        ListNode node = head;
        while (node != null) {
            list.add(node);
            node = node.next;
        }
        int i = 0, j = list.size() - 1;
        while (i < j) {
            list.get(i).next = list.get(j);
            i++;
            if (i == j) {
                break;
            }
            list.get(j).next = list.get(i);
            j--;
        }
        list.get(i).next = null;
    }
}
```

> **复杂度分析**
>
> 时间复杂度：O(N)*O*(*N*)，其中 N*N* 是链表中的节点数。
>
> 空间复杂度：O(N)*O*(*N*)，其中 N*N* 是链表中的节点数。主要为线性表的开销。

###### 方法二：寻找链表中点 + 链表逆序 + 合并链表

注意到目标链表即为将原链表的左半端和反转后的右半端合并后的结果。

这样我们的任务即可划分为三步：

1.找到原链表的中点（参考「876. 链表的中间结点」）。

​	我们可以使用快慢指针来 O(N) 地找到链表的中间节点。
2.将原链表的右半端反转（参考「206. 反转链表」）。

​	我们可以使用迭代法实现链表的反转。
3.将原链表的两端合并。

​	因为两链表长度相差不超过1，因此直接合并即可。

```java
class Solution {
    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        ListNode mid = middleNode(head);
        ListNode l1 = head;
        ListNode l2 = mid.next;
        mid.next = null;
        l2 = reverseList(l2);
        mergeList(l1, l2);
    }

    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    public void mergeList(ListNode l1, ListNode l2) {
        ListNode l1_tmp;
        ListNode l2_tmp;
        while (l1 != null && l2 != null) {
            l1_tmp = l1.next;
            l2_tmp = l2.next;

            l1.next = l2;
            l1 = l1_tmp;

            l2.next = l1;
            l2 = l2_tmp;
        }
    }
}
```

> **复杂度分析**
>
> 时间复杂度：O(N)，其中 N是链表中的节点数。
>
> 空间复杂度：O(1)。

#### [146. LRU 缓存机制](https://leetcode-cn.com/problems/lru-cache/)

第一次

```java
// 使用 哈希表 + 双向链表
// 时间复杂度：对于 put 和 get 都是 O(1)O(1)。
// 空间复杂度：O(\text{capacity})O(capacity)，因为哈希表和双向链表最多存储 \text{capacity} + 1capacity+1 个元素。
class LRUCache {

    static class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;

        public DLinkedNode() {
        }

        public DLinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final Map<Integer, DLinkedNode> cache = new HashMap<>();
    private int size;
    private final int capacity;
    private final DLinkedNode head;
    private final DLinkedNode tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        // 使用伪头部和伪尾部节点
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 如果 key 存在，先通过哈希表定位，再移到头部
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            // 如果 key 不存在，创建一个新的节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            // 添加进哈希表
            cache.put(key, newNode);
            // 新节点添加到双向链表的头部
            addToHead(newNode);
            size++;
            if (size > capacity) {
                // 如果超出容量，删除双向链表的尾部节点
                DLinkedNode tailNode = tail.prev;
                removeNode(tailNode);
                // 删除哈希表中对应的项
                cache.remove(tailNode.key);
                size--;
            }
        } else {
            // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
            node.value = value;
            moveToHead(node);
        }
    }

    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }
}
```



#### [223. 矩形面积](https://leetcode-cn.com/problems/rectangle-area/)

```java
class Solution {
    public int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        // 解决思路：用两块矩形的面积和减去两块矩形重叠部分的面积,不过为了在运算过程中防止溢出，需要先用其中一块矩形面积减去重叠面积，再加上另一块矩形面积
        
        // 重叠面积初始化为 0
        int coverArea = 0;
        // 如果两块矩形没有重合，则重叠面积 coverArea 为 0，后续 return 直接返回两矩形面积和
        if (bx1 >= ax2 || bx2 <= ax1 || by1 >= ay2 || by2 <= ay1) {
             coverArea = 0;
        } else {

            // 两块矩形重叠部分的面积 = 两矩形重叠部分长（x 轴差） * 两矩形重叠部分宽（y 轴差）
            // 两矩形重叠部分长 = x 轴上两矩形右边条边的较小值 min(ax2, bx2) - x 轴上两矩形左边条边的较大值 max(ax1, bx1)
            // 两矩形重叠部分宽 = y 轴上两矩形上边条边的较小值 min(ay2, by2) - y 轴上两矩形下边条边的较大值 max(ay1, by1)

            int x_right_min = Math.min(ax2, bx2);
            int x_left_max = Math.max(ax1, bx1);
            int y_up_min = Math.min(ay2, by2);
            int y_down_max = Math.max(ay1, by1);

            // 两块矩形重叠部分的面积
            coverArea = (x_right_min - x_left_max) * (y_up_min - y_down_max);
        }
        
        // 一块矩形面积 - 重叠面积 + 另一块矩形面积
        return (ax2 - ax1) * (ay2 - ay1) - coverArea + (bx2 - bx1) * (by2 - by1);
    }
}
```

#### [739. 每日温度](https://leetcode-cn.com/problems/daily-temperatures/)

##### 2021.10.24

```java
class P739_Solution {
    // (1)我的暴力解法 时间复杂度 O(n^2) 空间复杂度 O(1)
    public int[] dailyTemperatures(int[] temperatures) {
        int len = temperatures.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i; j < len; j++) {
                // 找到一个比 temperatures[i] 大的值, 令 temperatures[i] 为距离
                if (temperatures[i] < temperatures[j]) {
                    temperatures[i] = j - i;
                    break;
                }
                // 当比较到最后一个值都比 temperatures[i] 值小, 用 0 代替
                if (j == len - 1) {
                    temperatures[i] = 0;
                }
            }
        }
        // 最后一个值最后肯定为 0
        temperatures[len - 1] = 0;
        return temperatures;
    }

    // (2)官方答案的暴力解法 时间复杂度 O(n^m) 空间复杂度 O(1), m 是数组 next 的长度
    public int[] dailyTemperatures1(int[] temperatures) {
        int len = temperatures.length;
        int[] ans = new int[len];
        int[] next = new int[101];
        Arrays.fill(next, Integer.MAX_VALUE);
        // 从最后一个元素往前遍历
        for (int i = len - 1; i >= 0; i--) {
            int index = Integer.MAX_VALUE;
            // 每次遍历都要遍历完所有比该温度值更大的温度可能第一次出现的位置, 取其中的最小值为 index
            for (int j = temperatures[i] + 1; j < next.length; j++) {
                if (next[j] < index) {
                    index = next[j];
                }
            }
            // 当 index < Integer.MAX_VALUE 时, 说明有比 temperatures[i] 更大的温度值, 且此时距离 temperatures[i] 最近
            if (index < Integer.MAX_VALUE) {
                ans[i] = index - i;
            } else {
                ans[i] = 0;
            }
            // 更新 i 为此温度值为第一次出现的下标
            next[temperatures[i]] = i;
        }
        return ans;
    }

    // (3)官方答案的单调栈解法(最优解法) 时间复杂度 O(n) 空间复杂度 O(n)
    public int[] dailyTemperatures2(int[] temperatures) {
        int len = temperatures.length;
        int[] ans = new int[len];
        // stack 栈中保存的是当前尚未找到更大的温度值的该温度值的下标
        // stack 中栈底到栈顶的各个下标对应的温度是递减的
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < len; i++) {
            int temperature = temperatures[i];
            // 当当前温度大于了栈顶的温度, 就说明之前的小的温度找到了一个离它最近的比它大的温度
            while (!stack.isEmpty() && temperature > temperatures[stack.peek()]) {
                int prevIndex = stack.poll();
                ans[prevIndex] = i - prevIndex;
            }
            // 当前温度的下标进栈
            stack.push(i);
        }
        return ans;
    }
}
```

#### [147. 对链表进行插入排序](https://leetcode-cn.com/problems/insertion-sort-list/)

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class P147_Solution {
    // 从前往后找插入点
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode node = head.next;
        head.next = null;
        // 表示最终找到插入位置时的后一个结点
        ListNode cur;
        ListNode temp;
        // 每次插入一个结点, 都从头遍历到合适位置
        while (node != null) {
            temp = node.next;
            // 表示最终找到插入位置时的前一个结点
            ListNode prev = new ListNode();
            prev.next = head;
            cur = head;
            while (cur != null && cur.val < node.val) {
                prev = cur;
                cur = cur.next;
            }
            // 退出 while 时要么是该 node.val 为已排链表的最大值(cur == null), 添加在末尾
            // 要么 不是最大值, 添加在中间某处(prev 与 cur 之间, cur 不为 null)
            if (cur == null) {
                prev.next = node;
                node.next = null;
            } else {
                node.next = cur;
                prev.next = node;
                // 如果 node.val 的值比头结点的值小, 需要将该 node 结点设为头结点
                if (cur == head) {
                    head = node;
                }
            }
            node = temp;
        }
        return head;
    }

    // 从前往后找插入点, 对上一个方法的代码逻辑简化
    public ListNode insertionSortList1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode node = head.next;
        head.next = null;
        // 表示最终找到插入位置时的后一个结点
        ListNode cur;
        ListNode temp;
        // 每次插入一个结点, 都从头遍历到合适位置
        while (node != null) {
            temp = node.next;
            // 表示最终找到插入位置时的前一个结点
            ListNode prev = new ListNode();
            prev.next = head;
            cur = head;
            while (cur != null && cur.val < node.val) {
                prev = cur;
                cur = cur.next;
            }
            // 退出 while 时要么是该 node.val 为已排链表的最大值(cur == null), 添加在末尾
            // 要么 不是最大值, 添加在中间某处(prev 与 cur 之间, cur 不为 null)
            node.next = cur;
            prev.next = node;
            // 如果 node.val 的值比头结点的值小, 需要将该 node 结点设为头结点
            if (cur == head) {
                head = node;
            }
            node = temp;
        }
        return head;
    }
}
```

#### [200. 岛屿数量](https://leetcode-cn.com/problems/number-of-islands/)

##### 2021.11.01

###### 深度优先搜索 DFS

```java
// 使用 深度优先搜索
class P200_Solution {
    char[][] grid;

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        this.grid = grid;
        // numOfIslands 表示岛屿数量
        int numOfIslands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // 每进行一次深度优先搜索, 表示有一个岛屿
                if (grid[i][j] == '1') {
                    dfs(i, j);
                    numOfIslands++;
                }
            }
        }
        return numOfIslands;
    }

    public void dfs(int i, int j) {
        if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
            if (grid[i][j] == '1') {
                // 每次遇到 '1' ,就将其改为 '2', 表示已经遍历了该陆地 '1'
                grid[i][j] = '2';
                // 按照"左上右下"的顺序遍历该陆地的相邻区域(方格)
                dfs(i, j - 1);
                dfs(i - 1, j);
                dfs(i, j + 1);
                dfs(i + 1, j);
            }
        }
    }
}
```

###### 广度优先搜索 BFS

```java
// 使用 广度优先搜索
class P200_Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        // numOfIslands 表示岛屿数量
        int numOfIslands = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 每进行一次广度优先搜索, 表示有一个岛屿
                if (grid[i][j] == '1') {
                    numOfIslands++;
                    // 每次遇到 '1' ,就将其改为 '2', 表示已经遍历了该陆地 '1'
                    grid[i][j] = '2';
                    // 创建一个队列用以保存该陆地及其相邻的陆地和相连的区域
                    Queue<Integer> neighbors = new LinkedList<>();
                    // 该陆地入队
                    neighbors.add(i * n + j);
                    int id, row, col;
                    // while 中出队一个已访问的陆地, 入队与其相邻的陆地, 直到队列为空
                    while (!neighbors.isEmpty()) {
                        id = neighbors.remove();
                        row = id / n;
                        col = id % n;
                        // 如果该陆地的周围(上下左右)有陆地, 将其添加进队列中, 标记为已访问 '2'
                        // 按照"左上右下"的顺序遍历该陆地的相邻区域(方格)
                        // 左
                        if (col - 1 >= 0 && grid[row][col - 1] == '1') {
                            // neighbors.add(row * n + col - 1);
                            // 与上等价
                            neighbors.add(id - 1);
                            grid[row][col - 1] = '2';
                        }
                        // 上
                        if (row - 1 >= 0 && grid[row - 1][col] == '1') {
                            // neighbors.add((row - 1) * n + col);
                            // 与上等价
                            neighbors.add(id - n);
                            grid[row - 1][col] = '2';
                        }
                        // 右
                        if (col + 1 < n && grid[row][col + 1] == '1') {
                            // neighbors.add(row * n + col + 1);
                            // 与上等价
                            neighbors.add(id + 1);
                            grid[row][col + 1] = '2';
                        }
                        // 下
                        if (row + 1 < m && grid[row + 1][col] == '1') {
                            // neighbors.add((row + 1) * n + col);
                            // 与上等价
                            neighbors.add(id + n);
                            grid[row + 1][col] = '2';
                        }
                    }
                }
            }
        }
        return numOfIslands;
    }
}
```

#### [237. 删除链表中的节点](https://leetcode-cn.com/problems/delete-node-in-a-linked-list/)

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */

class P237_Solution {
    // 我做的, 将 node 结点的值置为后一个结点的值, 直到末尾, 然后再删除最后一个结点(此时值与倒数第二个结点重复了)
    public void deleteNode(ListNode node) {
        ListNode temp = node;
        while (node != null) {
            if (node.next != null) {
                node.val = node.next.val;
                temp = node;
                node = node.next;
            } else {
                temp.next = null;
                node = null;
            }
        }
    }

    // 优化, 只需要将 node 结点的值变为 node.next 的值, 然后删除 node.next 就行了
    public void deleteNode1(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
```



#### [344. 反转字符串](https://leetcode-cn.com/problems/reverse-string/)

##### 2021.10.31

```java
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
```

#### [509. 斐波那契数](https://leetcode-cn.com/problems/fibonacci-number/)

```java
class P509_Solution {
    // 动态规划 时间复杂度O(n) 空间复杂度O(n)
    public int fib(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    // 动态规划 对上方法的代码优化
    // 滚动数组思想, 时间复杂度O(n) 空间复杂度O(1)
    public int fib1(int n) {
        if (n < 2) {
            return n;
        }
        int[] dp = new int[3];
        dp[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            dp[2] = dp[0] + dp[1];
            dp[0] = dp[1];
            dp[1] = dp[2];
        }
        return dp[2];
    }
    
    // 递归 消耗最大
    public int fib2(int n) {
        if (n < 2) {
            return n;
        }
        return fib2(n - 1) + fib2(n - 2);
    }
}
```



#### [980. 不同路径 III](https://leetcode-cn.com/problems/unique-paths-iii/)

```java
class P980_Solution {
    private int[][] grid;

    public int uniquePathsIII(int[][] grid) {
        this.grid = grid;
        // 开始位置
        int startX = 0, startY = 0;
        // stepNum 表示 grid 中 0 的个数 + 找到 2 时这最后一步, 每经过一个 0 自减, 找到 2 时, stepNum 为 0
        int stepNum = 1;
        // 遍历 grid 得到起始位置 和 总步数
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    stepNum++;
                } else if (grid[i][j] == 1) {
                    startX = i;
                    startY = j;
                }
            }
        }
        return dfs(startX, startY, stepNum);
    }

    public int dfs(int x, int y, int step) {
        // 遍历到边界, 返回 0
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
            return 0;
        }
        // grid[x][y] 是障碍(为 -1 时)或者已经访问过(为 3 时), 返回 0
        if (grid[x][y] == -1 || grid[x][y] == 3) {
            return 0;
        }
        // 找到结束方格, 但是如果此时(此路径)并没有访问完所有的 0 方格, 则还是认为没有找到满足题意的一条路径
        if (grid[x][y] == 2) {
            return step == 0 ? 1 : 0;
        }
        // 能执行到这里表示 grid[x][y] == 0, 将其置为 3, 表示正在访问或者已经访问过该方格, 在回溯时再将其置为 0
        grid[x][y] = 3;
        // path 表示之前已经访问过若干个 0 方格, 现从此方格开始能访问完所有剩余 0 方格, 并且最终能访问到结束方格的路径数
        // 任何一个从当前方格能够访问到结束方格的路径数等于该方格"上下左右"这四个方格访问到结束方格的路径数的总和
        int path = 0;
        // 按照左上右下的顺序遍历
        path += dfs(x, y - 1, step - 1);
        path += dfs(x - 1, y, step - 1);
        path += dfs(x, y + 1, step - 1);
        path += dfs(x + 1, y, step - 1);
        // 回溯时将其置为 0
        grid[x][y] = 0;
        return path;
    }
}
```



#### [1857. 有向图中最大颜色值](https://leetcode-cn.com/problems/largest-color-value-in-a-directed-graph/)

```java
public class P1857_LargestPathValue {
    public static void main(String[] args) {
        // 测试案例 1
        String colors = "abaca";
        int[][] edges = {{0, 1}, {0, 2}, {2, 3}, {3, 4}};
//        int[][] edges = {{0, 1}, {0, 2}, {2, 3}, {3, 4}, {1, 2}, {3, 1}}; // 存在环
//        // 测试案例 2
//        String colors = "a";
//        int[][] edges = {{0, 0}};
//        // 测试案例 3
//        String colors = "g";
//        int[][] edges = {};

        Solution solution = new Solution();
        int largestPathValue = solution.largestPathValue(colors, edges);

        System.out.println(largestPathValue);
    }
}

class Solution {
    public int largestPathValue(String colors, int[][] edges) {

        // 节点个数
        int nodeNum = colors.length();
        // 每个节点的入度
        int[] inDegree = new int[nodeNum];
        // 保存到达每个节点时, 该条路径上的各种颜色出现的次数
        int[][] pathValue = new int[nodeNum][26];
        // 创建邻接表
        Node[] adjList = new Node[nodeNum];

        // 把从 from 节点到所有 to 节点的所有边的关系都保存为
        // 从 from 节点指向的所有的 to 节点形成的一个链表
        // 例如：从字符 ’a‘ 这个节点到 字符 ’b‘’c‘’d‘ 这三个节点的五条边关系保存为
        // {aNode} --> [bNode] --> cNode --> dNode
        // 如果 ’b‘ 到 ’e‘’f‘ ，则
        // {bNode} --> [eNode] --> fNode
        // 其中,[]括号里的[bNode][eNode]两节点才保存在邻接表adjList[from]中
        // from 为相对应的’a‘’b‘在原字符串中的下标
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            // to 节点的入度更新,一条 from -> to 的边, 则 to 节点的入度 + 1
            inDegree[to]++;
            // 采用头插法，与注释的三行等价
            adjList[from] = new Node(to, adjList[from]);
            /*
            Node toNode = new Node(to);
            toNode.next = adjList[from];
            adjList[from] = toNode;
             */
        }

        // 保存入度为 0 的各个节点，有可能是非连通图
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        // 如果 queue 中一个都没有, 表示存在环, 所有节点的入度都大于 0
        // 此判断可以删除
        if (queue.isEmpty()) {
            return -1;
        }

        // 如果无环,当循环结束时, bfsCount 应该等于节点数 nodeNum
        int bfsCount = 0;
        Node next;
        while (!queue.isEmpty()) {
            // 初始时前 initQueueSize = queue.size() 个 cur 代表入度为 0 的从该点开始遍历的出发节点的下标
            // 可以看作有 initQueueSize 个子图
            int cur = queue.remove();
            // adjList[cur] 处存储的是 cur 的下一个节点
            next = adjList[cur];

            // bfsCount 入度为 0 的点的个数
            bfsCount++;
            // 将当前路径上的该点处的相应颜色的值加 1
            pathValue[cur][colors.charAt(cur) - 97]++;
            // 遍历当前节点能够到达的其它所有节点
            while (next != null) {
                // 入度减 1, 减小到 0 时, 将其入队
                inDegree[next.value]--;
                if (inDegree[next.value] == 0) {
                    queue.add(next.value);
                }

                // 更新 next 所有颜色次数
                // 由于当前的这个节点在曾经可能已经在其它路径中被访问过,所以此次访问需要比较上次
                // pathValue[0].length == 26 小写字母个数
                for (int i = 0; i < pathValue[0].length; i++) {
                    pathValue[next.value][i] = Math.max(pathValue[next.value][i], pathValue[cur][i]);
                }
                // next 指向 cur 这个入度为 0 的节点的下一个与它相连的能组成边的点
                next = next.next;
            }
        }

        // 如果无环,当循环结束时, bfsCount 应该等于节点数 nodeNum
        if (bfsCount != nodeNum) {
            return -1;
        }

        // 从 pathValue 二维数组中获取最大值 largestPathValue
        int res = 0;
        for (int[] ints : pathValue) {
            res = Math.max(res, Arrays.stream(ints).max().getAsInt());
        }
//        for (int[] ints : pathValue) {
//            for (int j = 0; j < pathValue[0].length; j++) {
//                res = Math.max(res, ints[j]);
//            }
//        }
        return res;
    }
}

class Node {

    // value 表示第几个字符
    public int value;
    Node next;

    public Node(int value) {
        this.value = value;
    }

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }
}
```

## 剑指 Offer II



