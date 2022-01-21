package com.ahua.exam.huawei;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author huajun
 * @create 2022-01-11 17:00
 */

/**
 * 此题为 华为 OD 面试 一面 面试题
 * 对应于 LeetCode 101. 对称二叉树
 * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
 *
 * 树中节点数目在范围 [1, 1000] 内
 * -100 <= Node.val <= 100
 *
 * 进阶：你可以运用递归和迭代两种方法解决这个问题吗？
 *
 * 详细见 problems P101_isSymmetric
 */
public class ODInterview_I_2022_01_11 {
    public static void main(String[] args) {

    }
}

// 面试时写的代码, 结束后对这道题的更改在 P101_isSymmetric 中
class ODInterviewI_2022_01_11_solution {
    public boolean func(Trnode root) {
        if (root == null) {
            return true;
        }

        Queue<Trnode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);
        while (!queue.isEmpty()) {
            int size = queue.size();
            Trnode[] arr = new Trnode[size / 2];
            // 前一半
            for (int i = 0; i < size / 2; i++) {
                Trnode node = queue.remove();
                arr[i] = node;
                if (node != null) {
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
            for (int i = 1; i <= size / 2; i++) {
                Trnode node = queue.remove();
                // 两个都为 null
                if (node == null && arr[size / 2 - i] == null) {
                    continue;
                }
                // 其中一个为 null
                if (node == null || arr[size / 2 - i] == null) {
                    return false;
                }
                // 两个不为 null 判断值是否相等
                if (node.value != arr[size / 2 - i].value) {
                    return false;
                }
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        return true;
    }
}

class Trnode {
    Trnode left;
    Trnode right;
    int value;

    public Trnode(int value) {
        this.value = value;
    }

    public Trnode(Trnode left, Trnode right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }
}