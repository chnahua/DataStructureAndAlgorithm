package com.ahua.leetcode.jzofferII;

/**
 * @author huajun
 * @create 2021-10-17 23:32
 */

/**
 * 合并排序链表,与 P23 是同一个题
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

public class JZOII078_MergeKLists {
    public static void main(String[] args) {
        // 与 P23 是同一个题
    }
}

class Solution1 {
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
