package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-03 17:37
 */

/**
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 *
 * n == height.length
 * 1 <= n <= 2 * 104
 * 0 <= height[i] <= 105
 */
public class P42_Trap {
    public static void main(String[] args) {
        int[] height = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int[] height1 = new int[]{4, 2, 0, 3, 2, 5};
        P42_Solution solution = new P42_Solution();
        System.out.println(solution.trap(height)); // 6
        System.out.println(solution.trap(height1)); // 9

        P42_Solution1 solution1 = new P42_Solution1();
        System.out.println(solution1.trap(height)); // 6
        System.out.println(solution1.trap(height1)); // 9
    }
}

class P42_Solution {

    public int trap(int[] height) {
        int len = height.length;
        if (len <= 2) {
            return 0;
        }
        // 左边柱子 和 右边柱子
        int left = 0, right;
        // 遍历到 left 时, left [左边]所有柱子的高度和
        int leftSum;
        // 遍历到 right 时, right [左边]所有柱子的高度和
        // 初始时 rightSum 为第一个柱子的高度
        int rightSum = height[0];
        // 水滴, 也就是面积
        int water = 0;

        // 1.左边部分
        // 每次循环都是找到以 left 为左边, right 为右边围成的坑, 直到最高柱子结束循环
        // 此处 while(true) 也对
        while (left < len) {

            leftSum = rightSum;
            right = left + 1;
            // 从 left 柱子的下一个柱子开始遍历
            while (right < len) {
                rightSum += height[right];
                // 在 left 柱子的右侧找到第一个比 left 柱子更高或者相等高度的柱子 right
                if (height[right] >= height[left]) {
                    // 计算 left 与 right 之间能装多少水滴
                    water += (right - left - 1) * height[left] - (rightSum - leftSum - height[right]);
                    break;
                }

                // 没有找到就找下一个柱子是否比 left 柱子高
                right++;
            }
            // 退出内层 while 时, 如果 right < len, 说明在 left 到 height.length - 1 之间, 找到了 right 柱子,
            // right 柱子比 left 柱子高或者高度相等, 直接进行下次外层 while 循环
            if (right < len) {
                // 下一次外层 while 循环是从 right 处找到以 right 为左边条边的坑
                left = right;
            } else if (right == len) {
                // 退出内层 while 时, 如果 right == len, 那么此时 left 柱子的高度值 height[left] 最大,
                // 当然在 left 到 height.length - 1 之间也就找不到 right 柱子满足要求了,
                // 于是直接退出外层 while 循环, 从数组末尾开始往前找
                break;
            }
        }
//        // 测试, 输出结束时的柱子总高度
//        System.out.println("1 -- left -- < " + leftSum);
//        System.out.println("1 -- right -- < " + rightSum);

        // 2.右边部分
        // 记录最高柱子的下标
        int max = left;
        right = len - 1;
        // 重设为 0;
        // 此处
        // 遍历到 left 时, left [右边]所有柱子的高度和
        // 初始时 leftSum 为最后一个柱子的高度
        leftSum = height[len - 1];
        // 遍历到 right 时, right [右边]所有柱子的高度和
        rightSum = 0;
        while (right >= max) {

            rightSum = leftSum;
            left = right - 1;

            while (left >= max) {
                leftSum += height[left];
                if (height[left] >= height[right]) {
                    water += (right - left - 1) * height[right] - (leftSum - rightSum - height[left]);
                    break;
                }

                left--;
            }
            if (left >= max) {
                right = left;
            }
            // 右边条边为最高柱子时结束外层 while 循环, 此时 water 值即为答案
            if (right == max) {
                break;
            }
        }
//        // 测试, 输出结束时的柱子总高度
//        System.out.println("2 -- left -- > " + leftSum);
//        System.out.println("2 -- right -- > " + rightSum);
        return water;
    }
}

/**
 * 相比较于上一个, 此处将上述的步骤进行了封装, 并且小小改变了一下计算 water 的方式,
 * 由于只需要知道 leftSum 和 rightSum 之间的差值即可,
 * 故将 water 公式中的 leftSum 与 rightSum 之差更改为 midSum
 */
class P42_Solution1 {
    // 水滴, 也就是面积
    private int water;

    public int trap(int[] height) {
        if (height.length <= 2) {
            return 0;
        }
        // 初始化水滴数为 0
        water = 0;
        // 得到最高柱子的下标, 同时也会计算最高柱子前的所有水滴数
        int max = leftWater(height);
        // 得到最终的 water 数
        rightWater(height, max);
        return water;
    }

    /**
     * 1.左边部分
     * 从左往右遍历, 计算最高柱子之前的坑中的水滴数, 返回最高柱子的下标
     *
     * @param height 数组
     * @return left 结束时 left 为最高柱子的下标
     */
    public int leftWater(int[] height) {
        // 左边柱子 和 右边柱子
        int left = 0, right;
        // left 与 right 之间(包含height[right])的所有方格数(柱子高度)总和
        int midSum;

        int len = height.length;
        // 每次循环都是找到以 left 为左边, right 为右边围成的坑, 直到最高柱子结束循环
        while (left < len) {
            // 从 left 柱子的下一个(右边)柱子开始往右遍历
            right = left + 1;
            // 新一轮循环, 置为 0
            midSum = 0;
            while (right < len) {
                midSum += height[right];
                // 在 left 柱子的右侧找到第一个比 left 柱子更高或者相等高度的柱子 right
                if (height[right] >= height[left]) {
                    // 计算 left 与 right 之间能装多少水滴
                    // water += (right - left - 1) * height[left] - (midSum - height[right]);
                    water += calWater(left, right, height[left], height[right], midSum);
                    break;
                }

                // 没有找到就找下一个柱子是否比 left 柱子高
                right++;
            }
            // 退出内层 while 时, 如果 right < len, 说明在 left 到 height.length - 1 之间, 找到了 right 柱子,
            // right 柱子比 left 柱子高或者高度相等, 直接进行下次外层 while 循环
            if (right < len) {
                // 下一次外层 while 循环是从 right 处找到以 right 为左边条边的坑
                left = right;
            } else if (right == len) {
                // 退出内层 while 时, 如果 right == len, 那么此时 left 柱子的高度值 height[left] 最大,
                // 当然在 left 到 height.length - 1 之间也就找不到 right 柱子满足要求了,
                // 于是直接退出外层 while 循环, 从数组末尾开始往前找
                break;
            }
        }
        // 最高柱子下标
        return left;
    }

    /**
     * 2.右边部分
     * 从右往左遍历, 计算从末尾到最高柱子之间的坑中的水滴数
     *
     * @param height 数组
     * @param max    最高柱子下标
     */
    public void rightWater(int[] height, int max) {
        // 左边柱子 和 右边柱子
        int left, right = height.length - 1;
        // left 与 right 之间(包含height[left])的所有方格数(柱子高度)总和
        int midSum;
        // 倒序遍历
        while (right >= max) {
            // 从 right 柱子的下一个(左边)柱子开始往左遍历
            left = right - 1;
            // 新一轮循环, 置为 0
            midSum = 0;
            while (left >= max) {
                midSum += height[left];
                if (height[left] >= height[right]) {
                    // 计算 left 与 right 之间能装多少水滴
                    // water += (right - left - 1) * height[right] - (midSum - height[left]);
                    water += calWater(left, right, height[left], height[right], midSum);
                    break;
                }

                left--;
            }
            if (left >= max) {
                right = left;
            }
            // 右边条边为最高柱子时结束外层 while 循环, 此时 water 值即为答案
            if (right == max) {
                break;
            }
        }
    }

    /**
     * 计算 left 与 right 之间的水滴数
     *
     * @param left        左边柱子的下标
     * @param right       右边柱子的下标
     * @param leftHeight  左边柱子的高度
     * @param rightHeight 右边柱子的高度
     * @param midSum      左右两边柱子之间的所有柱子的高度和(包含 leftHeight 与 rightHeight 中的较大值)
     * @return waterSum   left 与 right 之间的水滴数
     */
    public int calWater(int left, int right, int leftHeight, int rightHeight, int midSum) {
        int waterSum = 0;
        if (leftHeight > rightHeight) {
            waterSum = (right - left - 1) * rightHeight - (midSum - leftHeight);
        } else {
            waterSum = (right - left - 1) * leftHeight - (midSum - rightHeight);
        }
        return waterSum;
    }
}

// 暴力

// 动态规划

// 单调栈

// 双指针