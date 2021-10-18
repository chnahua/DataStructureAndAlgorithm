package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-10-18 14:16
 * <p>
 * 合并 K 个升序链表,与 JZOII078 是同一个题
 */

/**
 * 合并 K 个升序链表,与 JZOII078 是同一个题
 */

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

public class P23_MergeKLists {
    public static void main(String[] args) {
        ListNode[] lists = new ListNode[3];

        lists[0] = new ListNode(1);
        lists[0].next = new ListNode(4);
        lists[0].next.next = new ListNode(5);
        lists[1] = new ListNode(1);
        lists[1].next = new ListNode(3);
        lists[1].next.next = new ListNode(4);
        lists[2] = new ListNode(2);
        lists[2].next = new ListNode(6);

        P23_Solution solution = new P23_Solution();
        ListNode mergedList = solution.mergeKLists(lists);

        // ListNode[] lists = new ListNode[]{null, new ListNode(1)};
        // ListNode[] lists = new ListNode[]{new ListNode(1), new ListNode(0)};
        // ListNode mergedList = solution.mergeTwoLists(lists[0], lists[1]);

        while (mergedList != null) {
            System.out.println(mergedList.val);
            mergedList = mergedList.next;
        }
    }
}

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

class P23_Solution_1 {
    public ListNode mergeKLists(ListNode[] lists) {
        int len = lists.length;
        if (len == 0) {
            return null;
        } else if (len == 1) {
            return lists[0];
        }/* else if (len == 2) {
            return mergeTwoLists(lists[0], lists[len - 1]);
        }*/
//        ListNode temp = new ListNode();
//        ListNode mergedList = new ListNode();
//        ListNode mergedListTailNode = new ListNode();
//        mergedList.next = mergedListTailNode;
//        if (len % 2 == 1) {
//            lists[0] = mergeTwoLists(lists[0], lists[len - 1]);
//        }

        int t = len;

        while (t != 0) {
            t = t / 2;
            for (int i = 0; i < t; i++) {
                lists[i] = mergeTwoLists(lists[i], lists[i + t]);
            }
            if (t % 2 == 1) {
                lists[0] = mergeTwoLists(lists[0], lists[t - 1]);
            }
        }

//        for (int i = 1; i < len; i++) {
//            lists[0] = mergeTwoLists(lists[0], lists[i]);
//        }

//        for (int i = 0; i < len; i++) {
////            if (lists[i].val <= lists[i + 1].val) {
////                temp = lists[i];
////                lists[i] = lists[i].next;
////                temp.next = null;
////                mergedListTailNode.next = temp;
////                mergedListTailNode = temp;
////            }
//        }
        return lists[0];
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            list1 = list2;
            return list1;
        } else if (list2 == null) {
            return list1;
        }
        ListNode fromList;
        ListNode toList;

        if (list1.val <= list2.val) {
            // pre.next = list1;
            fromList = list2;
            toList = list1;
        } else {
//            pre = list2;
//            list2 = list2.next;
//            pre.next = list1;
//            list1 = pre;
//            pre.next = list1;
            fromList = list1;
            toList = list2;
        }
        ListNode pre = new ListNode();
        pre.next = toList;
        ListNode cur = toList;
        ListNode temp;
        while (fromList != null && cur != null) {
            if (cur.val <= fromList.val) {
                pre = cur;
                cur = cur.next;
            } else {
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
}

// Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
