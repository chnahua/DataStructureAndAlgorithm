package com.ahua.nowcoder.huawei;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author huajun
 * @create 2021-12-25 21:56
 */

public class HJ48 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            int head = sc.nextInt();
            List<Integer> list = new LinkedList<>();
            list.add(head);
            int preNode, nextNode, index;
            for (int i = 0; i < n;i++) {
                // 要插入的节点
                nextNode = sc.nextInt();
                // 插入在哪个节点之后
                preNode = sc.nextInt();
                index = list.indexOf(preNode);
                list.add(index + 1,nextNode);
            }
            list.remove((Integer) sc.nextInt());
            for (int i = 0; i < list.size() - 1; i++) {
                System.out.print(list.get(i) + " ");
            }
            System.out.println(list.get(list.size() - 1));
        }
        // System.out.println();
        sc.close();
    }
}