# LeetCode 笔记

## Problems

### 做题记录

| 题目编号                                                     |   数据结构    |     算法     | 难度 | 初次完成时间 | 最近完成时间 | 完成次数 | 最近一次完成是否是独立完成 | 是否有不同于官方答案的解题思路 | 是否需要重点复习 |                  页内跳转                   |
| :----------------------------------------------------------- | :-----------: | :----------: | :--: | :----------: | :----------: | :------: | :------------------------: | :----------------------------: | :--------------: | :-----------------------------------------: |
| [23. 合并K个升序链表](https://leetcode-cn.com/problems/merge-k-sorted-lists/) |     链表      |     分治     | 困难 |  2021.10.18  |  2021.10.18  |    1     |             是             |            巧合一样            |        是        |    <a href="#23-合并K个升序链表">23</a>     |
| [105. 从前序与中序遍历序列构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) |    二叉树     |     递归     | 中等 |  2021.09.19  |  2021.09.19  |    1     |             否             |               否               |        是        | [105](#105. 从前序与中序遍历序列构造二叉树) |
| [106. 从中序与后序遍历序列构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/) |    二叉树     |     递归     | 中等 |  2021.09.22  |  2021.09.22  |    1     |             否             |               否               |        是        | [106](#106. 从中序与后序遍历序列构造二叉树) |
| [112. 路径总和](https://leetcode-cn.com/problems/path-sum/)  |               |              | 简单 |  2021.04.26  |  2021.04.26  |    1     |             否             |               否               |        是        |            [112](#112. 路径总和)            |
| [146. LRU 缓存机制](https://leetcode-cn.com/problems/lru-cache/) | 哈希表+双链表 |              | 中等 |  2021.10.15  |  2021.10.16  |    2     |             否             |               是               |        是        |          [146](#146. LRU 缓存机制)          |
| [1857. 有向图中最大颜色值](https://leetcode-cn.com/problems/largest-color-value-in-a-directed-graph/) |      图       | 动态规划+BFS | 困难 |  2021.10.15  |  2021.10.15  |    1     |             否             |               否               |        是        |      [1857](#1857. 有向图中最大颜色值)      |
|                                                              |               |              |      |              |              |          |                            |                                |                  |                                             |

### 题目整理

#### 23. 合并K个升序链表

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

给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有从根节点到叶子节点路径总和等于给定目标和的路径。

叶子节点 是指没有子节点的节点。

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

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
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
                if (rec == sum) {
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



