package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-06 21:57
 */

/**
 * 1701. 平均等待时间 average-waiting-time
 * 有一个餐厅，只有一位厨师。你有一个顾客数组 customers ，其中 customers[i] = [arrivali, timei] ：
 * <p>
 * arrivali 是第 i 位顾客到达的时间，到达时间按 非递减 顺序排列。
 * timei 是给第 i 位顾客做菜需要的时间。
 * 当一位顾客到达时，他将他的订单给厨师，厨师一旦空闲的时候就开始做这位顾客的菜。
 * 每位顾客会一直等待到厨师完成他的订单。厨师同时只能做一个人的订单。厨师会严格按照 订单给他的顺序 做菜。
 * <p>
 * 请你返回所有顾客需要等待的 平均 时间。与标准答案误差在 10^-5 范围以内，都视为正确结果。
 * <p>
 * 1 <= customers.length <= 10^5
 * 1 <= arrivali, timei <= 10^4
 * arrivali <= arrivali+1
 */
public class P1701_AverageWaitingTime {
    public static void main(String[] args) {
        int[][] customers = new int[][]{{1, 2}, {2, 5}, {4, 3}};
        int[][] customers1 = new int[][]{{5, 2}, {5, 4}, {10, 3}, {20, 1}};
        P1701_Solution solution = new P1701_Solution();
        System.out.println(solution.averageWaitingTime(customers)); // 5.00000
        System.out.println(solution.averageWaitingTime(customers1)); // 3.25000
    }
}

class P1701_Solution {
    public double averageWaitingTime(int[][] customers) {
        int n = customers.length;
        // 所有顾客的总等待时间
        double totalWaitingTime = 0;
        // 做完某个顾客的菜后的结束时刻
        double endTime = 0;
        for (int[] customer : customers) {
            // 当前顾客 customer 到来的时刻
            int arrivalTime = customer[0];
            // 做当前顾客 customer 的菜需要的时间
            int cookTime = customer[1];
            // 如果当前顾客 customer 到来时, 厨师已经做完了上一位顾客的菜(期间厨师可能休息了 arrivalTime - endTime 的时间)
            // 那么厨师可以立刻马上就做当前顾客 customer 的菜
            if (arrivalTime >= endTime) {
                // 当前顾客 customer 无需等待厨师做之前到来的顾客的菜, 因为已经在到来之前就做完或者刚好做完了
                // 该顾客只需要等待厨师做自己的这份菜的时间 cookTime
                // 那么到目前为止的所有顾客的总等待时间是 totalWaitingTime = totalWaitingTime + cookTime
                totalWaitingTime += cookTime;
                // 厨师做完当前顾客的这份菜的结束时刻是 当前顾客到来的时间 arrivalTime + 做这道菜的时间 cookTime
                // 也可以理解为
                // 厨师做完上一位顾客的菜的结束时刻 endTime +
                // 厨师等待当前顾客 customer 到来中途休息的时间 arrivalTime - endTime +
                // 厨师做当前顾客 customer 的菜的时间 cookTime
                // 三者之和即为厨师做完当前顾客 customer 的这份菜的结束时刻 endTime = endTime + arrivalTime - endTime + costTime
                endTime = arrivalTime + cookTime;
            } else {
                // 当前顾客到来时, 厨师还在做之前到来的顾客的菜, 因此当前顾客需要等待厨师将之前到来的顾客的菜做完, 才能做当前顾客的菜
                // endTime - arrivalTime 为当前顾客 customer 等待厨师做之前到来的顾客的菜的时间
                // cookTime 为当前顾客 customer 等待厨师做自己的菜的时间
                // 两者之和(endTime - arrivalTime + cookTime) + 之前的所有顾客的等待时间 totalWaitingTime
                // 即为到目前为止的所有顾客的总等待时间
                totalWaitingTime += endTime - arrivalTime + cookTime;
                // 厨师做当前顾客 customer 的菜之前是没有休息的, 是紧接着做的, 因此
                // 厨师做完当前顾客 customer 的这份菜后的结束时刻是
                // 上次结束时刻 endTime + 做当前顾客的菜的时间 cookTime
                endTime += cookTime;
            }
        }
        return totalWaitingTime / n;
    }
}