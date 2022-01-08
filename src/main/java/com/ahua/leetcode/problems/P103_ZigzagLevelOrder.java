package com.ahua.leetcode.problems;

import java.util.*;

/**
 * @author huajun
 * @create 2022-01-08 22:39
 */

/**
 * 103. 二叉树的锯齿形层序遍历
 * 给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。
 * （即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 * <p>
 * 树中节点数目在范围 [0, 2000] 内
 * -100 <= Node.val <= 100
 */
public class P103_ZigzagLevelOrder {
    public static void main(String[] args) {
        // [3,9,20,null,null,15,7]
        // [1,2,3,4,null,null,5]
    }
}

/*
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
// 我的解法, 与官方解法的遍历思路不同, 但本质还是一样的
// 我的解法是根据左右, 每次遍历的时候, 遍历结点左右顺序会有所不同, 添加到队列中时, 会选择是添加到队列尾还是队列首, 添加到 ans 中时都是添加到末尾;
// 而官方解法是在每次遍历的时候, 都是先左后右遍历, 添加到队列中时, 也是只添加到队列尾, 添加元素到 ans 中时, 会根据是奇偶层, 会选择是添加到头还是尾
class P103_Solution {
    public List<List<Integer>> zigzagLevelOrder1(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        // index 表示当前该层二叉树应当添加在 ans 的那个链表上, while 循环结束后的 index 值为 ans 的长度
        // 也表示 二叉树的深度, 遍历二叉树的层数, 遍历队列的层数
        int index = -1;
        ans.add(new ArrayList<>());
        // 根结点 root 添加在第一个链表上, 链表下标为 0
        index++;
        ans.get(index).add(root.val);
        // 提前结束, 可删除, 后续 while 中也会有等价的判断
        if (root.left == null && root.right == null) {
            return ans;
        }
        // 根据 flag 的值的不同, 出队位置不同、遍历左右子结点顺序不同、将左右子结点添加到队列 deque 的首还是尾也有不同
        // flag 为 false 时, 从 deque 的 [队列首] 出队结点 node, 先遍历 右结点, 后遍历 左结点, 各子结点添加到 deque 的 [队列尾]
        // flag 为 true 时, 从 deque 的 [队列尾] 出队结点 node, 先遍历 左结点, 后遍历 右结点, 各子结点添加到 deque 的 [队列首]
        boolean flag = false;
        Deque<TreeNode> deque = new LinkedList<>();
        // 初始时 根结点 root 入队
        deque.add(root);
        int size;
        while (!deque.isEmpty()) {
            // 每次在添加一层结点(已在 deque 和 ans 中)的子结点(马上要遍历添加到 deque 和 ans 中)时, 都要提前创建这个要添加到的链表
            ans.add(new ArrayList<>());
            // 链表的下标索引
            index++;
            // 队列长度, 该层结点数, 出队结点数
            size = deque.size();
            for (int i = 0; i < size; i++) {
                if (!flag) {
                    // [队列首] 出队结点 node
                    TreeNode node = deque.remove();
                    // 先遍历 右结点
                    if (node.right != null) {
                        // 添加到 [队列尾]
                        deque.add(node.right);
                        ans.get(index).add(node.right.val);
                    }
                    // 后遍历 左结点
                    if (node.left != null) {
                        // 添加到 [队列尾]
                        deque.add(node.left);
                        ans.get(index).add(node.left.val);
                    }
                } else {
                    // [队列尾] 出队结点 node
                    TreeNode node = deque.removeLast();
                    // 先遍历 左结点
                    if (node.left != null) {
                        // 添加到 [队列首]
                        deque.addFirst(node.left);
                        ans.get(index).add(node.left.val);
                    }
                    // 后遍历 右结点
                    if (node.right != null) {
                        // 添加到 [队列首]
                        deque.addFirst(node.right);
                        ans.get(index).add(node.right.val);
                    }
                }
            }
            // 该层结点遍历结束, 变换方向
            flag = !flag;
        }
        // 当最后一层结点添加到 deque 以及 ans 后, 此时 deque 不为空, 那么就还会再次遍历这最后一层结点, 寻找它的子结点, 但是这显然是不可能实现的
        // 于是就多执行了一次 ans.add(new ArrayList<>()); 于是要在返回结果前将最后这个空 list 移除
        ans.remove(index);
        return ans;
    }

    // 小修改, 基本思路逻辑不变, 测试用例上无性能提升
    // 修改 int index 为 List<Integer> list
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        ans.add(new ArrayList<>());
        // 根结点 root 添加在第一个链表上
        ans.get(0).add(root.val);
        // 提前结束, 可删除, 后续 while 中也会有等价的判断
        if (root.left == null && root.right == null) {
            return ans;
        }
        // 根据 flag 的值的不同, 出队位置不同、遍历左右子结点顺序不同、将左右子结点添加到队列 deque 的首还是尾也有不同
        // flag 为 false 时, 从 deque 的 [队列首] 出队结点 node, 先遍历 右结点, 后遍历 左结点, 各子结点添加到 deque 的 [队列尾]
        // flag 为 true 时, 从 deque 的 [队列尾] 出队结点 node, 先遍历 左结点, 后遍历 右结点, 各子结点添加到 deque 的 [队列首]
        boolean flag = false;
        Deque<TreeNode> deque = new LinkedList<>();
        // 初始时 根结点 root 入队
        deque.add(root);
        int size;
        while (!deque.isEmpty()) {
            // 每次在添加一层结点(已在 deque 和 ans 中)的子结点(马上要遍历添加到 deque 和 ans 中)时, 都要提前创建这个要添加到的链表
            List<Integer> list = new ArrayList<>();
            ans.add(list);
            // 队列长度, 该层结点数, 出队结点数
            size = deque.size();
            for (int i = 0; i < size; i++) {
                if (!flag) {
                    // [队列首] 出队结点 node
                    TreeNode node = deque.remove();
                    // 先遍历 右结点
                    if (node.right != null) {
                        // 添加到 [队列尾]
                        deque.add(node.right);
                        list.add(node.right.val);
                    }
                    // 后遍历 左结点
                    if (node.left != null) {
                        // 添加到 [队列尾]
                        deque.add(node.left);
                        list.add(node.left.val);
                    }
                } else {
                    // [队列尾] 出队结点 node
                    TreeNode node = deque.removeLast();
                    // 先遍历 左结点
                    if (node.left != null) {
                        // 添加到 [队列首]
                        deque.addFirst(node.left);
                        list.add(node.left.val);
                    }
                    // 后遍历 右结点
                    if (node.right != null) {
                        // 添加到 [队列首]
                        deque.addFirst(node.right);
                        list.add(node.right.val);
                    }
                }
            }
            // 该层结点遍历结束, 变换方向
            flag = !flag;
        }
        // 当最后一层结点添加到 deque 以及 ans 后, 此时 deque 不为空, 那么就还会再次遍历这最后一层结点, 寻找它的子结点, 但是这显然是不可能实现的
        // 于是就多执行了一次 ans.add(new ArrayList<>()); 于是要在返回结果前将最后这个空 list 移除
        ans.remove(ans.size() - 1);
        return ans;
    }
}