package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-22 20:58
 */

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 1687. 从仓库到码头运输箱子
 * 你有一辆货运卡车，你需要用这一辆车把一些箱子从仓库运送到码头。这辆卡车每次运输有 箱子数目的限制 和 总重量的限制 。
 * <p>
 * 给你一个箱子数组 boxes 和三个整数 portsCount, maxBoxes 和 maxWeight ，其中 boxes[i] = [ports i, weight i] 。
 * <p>
 * ports i 表示第 i 个箱子需要送达的码头， weights i 是第 i 个箱子的重量。
 * portsCount 是码头的数目。
 * maxBoxes 和 maxWeight 分别是卡车每趟运输箱子数目和重量的限制。
 * 箱子需要按照 数组顺序 运输，同时每次运输需要遵循以下步骤：
 * <p>
 * 卡车从 boxes 队列中按顺序取出若干个箱子，但不能违反 maxBoxes 和 maxWeight 限制。
 * 对于在卡车上的箱子，我们需要 按顺序 处理它们，卡车会通过 一趟行程 将最前面的箱子送到目的地码头并卸货。
 * 如果卡车已经在对应的码头，那么不需要 额外行程 ，箱子也会立马被卸货。
 * 卡车上所有箱子都被卸货后，卡车需要 一趟行程 回到仓库，从箱子队列里再取出一些箱子。
 * 卡车在将所有箱子运输并卸货后，最后必须回到仓库。
 * <p>
 * 请你返回将所有箱子送到相应码头的 最少行程 次数。
 * <p>
 * 1 <= boxes.length <= 105
 * 1 <= portsCount, maxBoxes, maxWeight <= 105
 * 1 <= ports i <= portsCount
 * 1 <= weights i <= maxWeight
 */
public class P1687_BoxDelivering {
    public static void main(String[] args) {
        int[][] boxes = new int[][]{{1, 1}, {2, 1}, {1, 1}};
        int[][] boxes1 = new int[][]{{1, 2}, {3, 3}, {3, 1}, {3, 1}, {2, 4}};
        int[][] boxes2 = new int[][]{{1, 4}, {1, 2}, {2, 1}, {2, 1}, {3, 2}, {3, 4}};
        int[][] boxes3 = new int[][]{{2, 4}, {2, 5}, {3, 1}, {3, 2}, {3, 7}, {3, 1}, {4, 4}, {1, 3}, {5, 2}};
        P1687_Solution solution = new P1687_Solution();
        System.out.println(solution.boxDelivering(boxes, 2, 3, 3)); // 4
        System.out.println(solution.boxDelivering(boxes1, 3, 3, 6)); // 6
        System.out.println(solution.boxDelivering(boxes2, 3, 6, 7)); // 6
        System.out.println(solution.boxDelivering(boxes3, 5, 5, 7)); // 14
    }
}

// 动态规划 + 单调队列
class P1687_Solution {
    public int boxDelivering(int[][] boxes, int portsCount, int maxBoxes, int maxWeight) {
        int n = boxes.length;
        // 将前 i 个箱子运送到对应码头需要变更的行程次数(不包括首尾的两次, 只计算中途变更的次数)
        int[] cntSteps = new int[n + 1];
        // 前 i 个箱子的总重量
        int[] sumWeight = new int[n + 1];
        // 没有箱子
        cntSteps[0] = 0;
        sumWeight[0] = 0;
        // 第一个箱子
        cntSteps[1] = 0;
        sumWeight[1] = boxes[0][1];
        // 预处理, 得到 cntSteps 和 sumWeight
        getSumStepsAndSumWeight(boxes, cntSteps, sumWeight);

        System.out.println("cntSteps  : " + Arrays.toString(cntSteps));
        System.out.println("sumWeight : " + Arrays.toString(sumWeight));

        // 单调队列(双端队列)
        Deque<Integer> deque = new LinkedList<>();
        // dp[i] = min(dp[i - 1] + 2, dp[j] + cnt[i] - cnt[j + 1] + 2) = min(dp[i - 1] + 2, dp[j] - cnt[j + 1] + cnt[i] + 2)
        // 等价于求 dp[j] - cnt[j + 1] 的最小值
        int[] dp = new int[n + 1];
        // 将前 0 个箱子运到相应码头的行程数
        dp[0] = 0;
        // 已经得到将前 0 个箱子运到相应码头的行程数, 首次时将 0 入队
        deque.add(0);

        // int diff = dp[0] - cntSteps[1] = 0;
        int diff;
/*
        for (int i = 1; i <= n; i++) {
            // 队列肯定不为空
            while (!deque.isEmpty() && (sumWeight[i] - sumWeight[deque.peek()] > maxWeight || i - deque.peek() > maxBoxes)) {
                // 不是 i - deque.peek() + 1
                // System.out.println("出队首 : " + deque.peek());
                deque.removeFirst();
            }
            // 队列肯定不为空
            if (!deque.isEmpty()) {
                dp[i] = dp[deque.peek()] + cntSteps[i] - cntSteps[deque.peek() + 1] + 2;
                // System.out.println(dp[deque.peek()] + "+" + cntSteps[i] + "-" + cntSteps[deque.peek() + 1] +"+2");
            }
            // System.out.println("dp[" + i + "] == " + dp[i]);
            if (i != n) {
                diff = dp[i] - cntSteps[i + 1];
                while (!deque.isEmpty() && diff < dp[deque.peekLast()] - cntSteps[deque.peekLast() + 1]) {
                    // System.out.println("出队尾 : " + deque.peekLast());
                    deque.removeLast();
                }
                deque.add(i);
                // System.out.println("入队尾 : " + i);
            }
        }
*/
        // 对上种方式的稍稍修改
        int j = 0, k;
        for (int i = 1; i <= n; i++) {
            // 找到最小的 dp[j] - cntSteps[j + 1], 此时得到的 dp[j] + cntSteps[i] - cntSteps[j + 1] + 2 最小, 即 dp[i] 最小
            // 队列肯定不为空
            while (!deque.isEmpty()) {
                j = deque.peek();
                // 移除不满足装运条件的 j, 直到遇到满足装运条件的 j 时退出循环
                if (sumWeight[i] - sumWeight[j] > maxWeight || i - j > maxBoxes) {
                    deque.removeFirst();
                } else {
                    break;
                }
            }

            dp[i] = dp[j] + cntSteps[i] - cntSteps[j + 1] + 2;

            if (i != n) {
                diff = dp[i] - cntSteps[i + 1];
                // 移除使得 dp[k] - cntSteps[k + 1] 大于 dp[i] - cntSteps[i + 1] 的 k
                // dp[i] - cntSteps[i + 1] 为最大时或者队列为空时退出循环, 将 i 入队
                while (!deque.isEmpty()) {
                    k = deque.peekLast();
                    if (diff < dp[k] - cntSteps[k + 1]) {
                        deque.removeLast();
                    } else {
                        break;
                    }
                }
                deque.add(i);
            }
        }

        System.out.println("dp        : " + Arrays.toString(dp));
        return dp[n];
    }

    private void getSumStepsAndSumWeight(int[][] boxes, int[] cntSteps, int[] sumWeight) {
        // 预处理前缀和
        // 从第二个箱子开始遍历
        for (int i = 1; i < boxes.length; i++) {
            // 前 i+1 个箱子运到相应码头所需的总行程(除去出发和返回的 2 次, 只统计变更行程的次数)
            // 例如: 10 个箱子依次要运到这些码头:          1 1 2 2 3 4 1 2 2 5
            // 对应的           cntSteps 值为: (首0填充) 0 0 1 1 2 3 4 5 5 6
            // 那么要运满足要求的其中几个箱子的行程数(出发和返回的 2 次)也就为相应位置处的 cntSteps 的差
            // 如这一次要运 2 2 3 4 1 这五个箱子, 那么中途需要变更行程的情况是 2->3->4->1 共 3 次, 也等于 (4-1)
            // 运送连续的两个或多个相同目的码头(也就是 2 )的箱子的行程数是不增加的
            if (boxes[i][0] != boxes[i - 1][0]) {
                cntSteps[i + 1] = cntSteps[i] + 1;
            } else {
                cntSteps[i + 1] = cntSteps[i];
            }
            // 前 i+1 个箱子的总重量
            sumWeight[i + 1] = sumWeight[i] + boxes[i][1];
        }
    }

    private void getSumStepsAndSumWeight1(int[][] boxes, int[] cntSteps, int[] sumWeight) {
        // 预处理前缀和
        // 从第二个箱子开始遍历
        for (int i = 2; i <= boxes.length; i++) {
            // 前 i 个箱子运到相应码头所需的总行程(除去出发和返回的 2 次, 只统计变更行程的次数)
            // 例如: 10 个箱子依次要运到这些码头:            1 1 2 2 3 4 1 2 2 5
            // 对应的        cntSteps 值为: [(首0填充)] 0 0 1 1 2 3 4 5 5 6
            // 那么要运满足要求的其中几个箱子的行程数(出发和返回的 2 次)也就为相应位置处的 cntSteps 的差
            // 如这一次要运 2 2 3 4 1 这五个箱子, 那么中途需要变更行程的情况是 2->3->4->1 共 3 次, 也等于 (4-1)
            // 运送连续的两个或多个相同目的码头(也就是 2 )的箱子的行程数是不增加的
            if (boxes[i - 1][0] != boxes[i - 2][0]) {
                cntSteps[i] = cntSteps[i - 1] + 1;
            } else {
                cntSteps[i] = cntSteps[i - 1];
            }
            // 前 i 个箱子的总重量
            sumWeight[i] = sumWeight[i - 1] + boxes[i - 1][1];
        }
    }
}