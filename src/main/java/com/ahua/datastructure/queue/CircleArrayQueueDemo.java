package com.ahua.datastructure.queue;

import java.util.Scanner;

/**
 * @author huajun
 * @create 2021-04-14 23:14
 */

public class CircleArrayQueueDemo {

    public static void main(String[] args) {

        // 测试
        System.out.println("测试数组模拟环形队列的案例~~~");

        // 创建一个环形队列
        CircleArrayQueue queue = new CircleArrayQueue(4); // 说明设置4, 其队列的有效数据最大是3
        char key = ' '; // 接收用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        // 输出一个菜单
        while (loop) {
            System.out.println("s(show): 显示队列" + "\n" +
                    "e(exit): 退出程序" + "\n" +
                    "i(in): 添加数据到队列" + "\n" +
                    "o(out): 从队列取出数据" + "\n" +
                    "h(head): 查看队列头的数据" + "\n");
            key = scanner.next().charAt(0); // 接收一个字符
            switch (key) {
                case 's':
                    queue.showQueue();
                    break;
                case 'i':
                    System.out.println("输入一个入队的数");
                    int value = scanner.nextInt();
                    queue.inQueue(value);
                    break;
                case 'o': // 取出数据
                    try {
                        int res = queue.outQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h': // 查看队列头的数据
                    try {
                        int res = queue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e': // 退出
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出~~");
    }
}

class CircleArrayQueue {
    private final int maxSize; // 表示数组的最大容量
    // front 变量的含义做一个调整: front 就指向队列的第一个元素, 也就是说 arr[front] 就是队列的第一个元素
    // front 的初始值 = 0
    private int front;
    // rear 变量的含义做一个调整: rear 指向队列的最后一个元素的后一个位置. 因为希望空出一个空间做为约定
    // rear 的初始值 = 0
    private int rear; // 队列尾
    private final int[] arr; // 该数据用于存放数据, 模拟队列

    public CircleArrayQueue(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
    }

    // 判断队列是否满
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    // 判断队列是否为空
    public boolean isEmpty() {
        return rear == front;
    }

    // 添加数据到队列
    public void inQueue(int n) {
        // 判断队列是否满
        if (isFull()) {
            System.out.println("队列满,不能入队");
            return;
        }
        // 直接将数据加入
        arr[rear] = n;
        // 将 rear 后移, 这里必须考虑取模
        rear = (rear + 1) % maxSize;
    }

    // 获取队列的数据,出队列
    public int outQueue() {
        // 判断队列是否空
        if (isEmpty()) {
            // 通过抛出异常
            throw new RuntimeException("队列为空,不能出队");
        }
        // 这里需要分析出 front 是指向队列的第一个元素
        // 1. 先把 front 对应的值保留到一个临时变量
        // 2. 将 front 后移, 考虑取模
        // 3. 将临时保存的变量返回
        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;
    }

    // 显示队列的所有数据
    public void showQueue() {
        // 遍历
        if (isEmpty()) {
            System.out.println("队列为空,没有数据");
            return;
        }
        // 思路：从 front 开始遍历,遍历多少个元素
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    // 求出当前队列有效数据的个数
    public int size() {
        // rear = 2
        // front = 1
        // maxSize = 3
        return (rear + maxSize - front) % maxSize;
    }

    // 显示队列的头数据,注意不是取出数据
    public int headQueue() {
        // 判断
        if (isEmpty()) {
            throw new RuntimeException("队列为空,没有数据");
        }
        return arr[front];
    }
}
