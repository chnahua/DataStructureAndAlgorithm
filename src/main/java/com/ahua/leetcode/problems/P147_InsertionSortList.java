package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-10-26 19:30
 */


/**
 * 对链表进行插入排序。
 *
 * 插入排序的动画演示如上。从第一个元素开始，该链表可以被认为已经部分排序（用黑色表示）。
 * 每次迭代时，从输入数据中移除一个元素（用红色表示），并原地将其插入到已排好序的链表中。
 *
 * 插入排序算法：
 * 插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
 * 每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
 * 重复直到所有输入数据插入完为止。
 *
 */
public class P147_InsertionSortList {
    public static void main(String[] args) {
        // P147_Solution solution = new P147_Solution();
        // System.out.println(solution.insertionSortList());
    }
}

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
