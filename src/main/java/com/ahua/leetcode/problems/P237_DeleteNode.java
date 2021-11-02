package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-02 21:21
 */

/**
 * 请编写一个函数，用于 删除单链表中某个特定节点 。在设计函数时需要注意，你无法访问链表的头节点 head ，只能直接访问 要被删除的节点 。
 *
 * 题目数据保证需要删除的节点 不是末尾节点 。
 *
 * 链表中节点的数目范围是 [2, 1000]
 * -1000 <= Node.val <= 1000
 * 链表中每个节点的值都是唯一的
 * 需要删除的节点 node 是链表中的一个有效节点，且不是末尾节点
 *
 */
public class P237_DeleteNode {
    public static void main(String[] args) {

    }
}

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
