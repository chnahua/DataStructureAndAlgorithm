package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-30 23:34
 */

/**
 * 1603. 设计停车系统 design-parking-system
 * 请你给一个停车场设计一个停车系统。停车场总共有三种不同大小的车位：大，中和小，每种尺寸分别有固定数目的车位。
 * <p>
 * 请你实现 ParkingSystem 类：
 * <p>
 * ParkingSystem(int big, int medium, int small) 初始化 ParkingSystem 类，三个参数分别对应每种停车位的数目。
 * bool addCar(int carType) 检查是否有 carType 对应的停车位。
 * carType 有三种类型：大，中，小，分别用数字 1， 2 和 3 表示。一辆车只能停在 carType 对应尺寸的停车位中。
 * 如果没有空车位，请返回 false ，否则将该车停入车位并返回 true 。
 * <p>
 * 0 <= big, medium, small <= 1000
 * carType 取值为 1， 2 或 3
 * 最多会调用 addCar 函数 1000 次
 */
public class P1603_ParkingSystem {
    public static void main(String[] args) {

    }
}

// 6 ms 100.00%
// 42 MB 31.22%
class ParkingSystem {
    int[] car;

    public ParkingSystem(int big, int medium, int small) {
        car = new int[4];
        car[1] = big;
        car[2] = medium;
        car[3] = small;
    }

    public boolean addCar(int carType) {
        if (car[carType] == 0) {
            return false;
        }
        car[carType]--;
        return true;
    }
}

/**
 * Your ParkingSystem object will be instantiated and called as such:
 * ParkingSystem obj = new ParkingSystem(big, medium, small);
 * boolean param_1 = obj.addCar(carType);
 */