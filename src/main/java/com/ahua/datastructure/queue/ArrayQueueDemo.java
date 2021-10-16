package com.ahua.datastructure.queue;

import java.util.Scanner;

/**
 * @author huajun
 * @create 2021-04-14 22:12
 */

public class ArrayQueueDemo {

    public static void main(String[] args) {
        // 测试
        // 创建一个队列
        ArrayQueue queue = new ArrayQueue(3);
        char key = ' '; // 接收用户输入
        Scanner scanner = new Scanner(System.in);//
        boolean loop = true;
        // 输出一个菜单
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("i(in): 添加数据到队列");
            System.out.println("o(out): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            System.out.println();
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
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h': // 查看队列头的数据
                    try {
                        int res = queue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
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

// 使用数组模拟队列-编写一个ArrayQueue类
class ArrayQueue {

    private final int maxSize; // 表示数组的最大容量
    private int front; // 队列头
    private int rear; // 队列尾
    private final int[] arr; // 该数据用于存放数据, 模拟队列

    // 创建队列的构造器
    public ArrayQueue(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
        front = -1; // 指向队列头部,front是指向队列头的前一个位置,初值不同,后续逻辑不同
        rear = -1; // 指向队列尾，指向队列尾的数据(即就是队列最后一个数据)
    }

    // 判断队列是否满
    public boolean isFull() {
        return rear == maxSize - 1;
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
        rear++; // rear 后移
        arr[rear] = n;
    }

    // 获取队列的数据, 出队列
    public int outQueue() {
        // 判断队列是否空
        if (isEmpty()) {
            // 通过抛出异常
            throw new RuntimeException("队列为空,不能出队");
        }
        front++; // front 后移
        return arr[front];
    }

    // 显示队列的所有数据
    public void showQueue() {
        // 遍历
        if (isEmpty()) {
            System.out.println("队列为空,没有数据");
            return;
        }
        for (int i = front + 1; i <= rear; i++) {
            // Java 中也能用占位符诶
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    // 显示队列的头数据， 注意不是取出数据
    public int headQueue() {
        // 判断
        if (isEmpty()) {
            throw new RuntimeException("队列为空,没有数据");
        }
        return arr[front + 1];
    }
}


