package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-25 2:35
 */

import java.util.*;

/**
 * 735. 行星碰撞
 * <p>
 * 给定一个整数数组 asteroids，表示在同一行的行星。
 * 对于数组中的每一个元素，其绝对值表示行星的大小，正负表示行星的移动方向（正表示向右移动，负表示向左移动）。
 * 每一颗行星以相同的速度移动。
 * <p>
 * 找出碰撞后剩下的所有行星。
 * 碰撞规则：两个行星相互碰撞，较小的行星会爆炸。
 * 如果两颗行星大小相同，则两颗行星都会爆炸。两颗移动方向相同的行星，永远不会发生碰撞。
 * <p>
 * 2 <= asteroids.length <= 10^4
 * -1000 <= asteroids[i] <= 1000
 * asteroids[i] != 0
 */
public class P735_AsteroidCollision {
    public static void main(String[] args) {
        int[] asteroids = new int[]{5, 10, -5};
        int[] asteroids1 = new int[]{8, -8};
        int[] asteroids2 = new int[]{10, 2, -5};
        int[] asteroids3 = new int[]{-2, -1, 1, 2};
        int[] asteroids4 = new int[]{1, -2, -2, -2};

        P735_Solution solution = new P735_Solution();
        System.out.println(Arrays.toString(solution.asteroidCollision(asteroids))); // [5,10]
        System.out.println(Arrays.toString(solution.asteroidCollision(asteroids1))); // []
        System.out.println(Arrays.toString(solution.asteroidCollision(asteroids2))); // [10]
        System.out.println(Arrays.toString(solution.asteroidCollision(asteroids3))); // [-2,-1,1,2]
        System.out.println(Arrays.toString(solution.asteroidCollision(asteroids4))); // [-2,-2,-2]
    }
}
// 栈
class P735_Solution {
    public int[] asteroidCollision(int[] asteroids) {
        // 栈
        Deque<Integer> stack = new LinkedList<>();
        for (int asteroid : asteroids) {
            twoAsteroidCollision(stack, asteroid);
        }

        // 结果数组
        int[] ans = new int[stack.size()];
        int i = 0;
        while (!stack.isEmpty()) {
            ans[i] = stack.remove();
            i++;
        }
        return ans;
    }

    // 可删, 在 while 中条件判断后发现可以不需要用 while 循环, 更改后的方法见下一个
    private void twoAsteroidCollision1(Deque<Integer> stack, int asteroid) {
        if (stack.isEmpty()) {
            stack.add(asteroid);
            return;
        }
        while (!stack.isEmpty()) {
            // 题目保证 asteroids[i] != 0
            int top = stack.peekLast();
            if (top < 0 || (top > 0 && asteroid > 0)) {
                stack.add(asteroid);
                return;
            } else if (top + asteroid == 0) {
                // stack.peek() > 0 && asteroids[i] < 0
                stack.removeLast();
                return;
            } else if (top + asteroid < 0) {
                // stack.peek() > 0 && asteroids[i] < 0
                // 正数 top 小于负数 asteroids[i] 的绝对值, 移除栈顶 top, 继续判断下一个栈顶
                stack.removeLast();
                twoAsteroidCollision1(stack, asteroid);
                return;
            } else {
                // asteroids[i] 爆炸, asteroids[i] 不入栈顶
                return;
            }
        }
    }

    // 上一个方法的逻辑和代码简化
    private void twoAsteroidCollision(Deque<Integer> stack, int asteroid) {
        if (stack.isEmpty()) {
            stack.add(asteroid);
            return;
        }

        // 题目保证 asteroids[i] != 0
        int top = stack.peekLast();
        // 栈顶小于 0 时, 不管 asteroid 大于 0 还是小于 0, 直接入栈
        // 栈顶大于 0 时, 如果 asteroid 大于 0, 说明两颗行星同向右, 直接入栈
        if (top < 0 || asteroid > 0) {
            stack.add(asteroid);
        } else if (top + asteroid == 0) {
            // 此处 top > 0 && asteroids[i] < 0
            // 两颗行星爆炸, 移除栈顶 top
            stack.removeLast();
        } else if (top + asteroid < 0) {
            // 此处 top > 0 && asteroids[i] < 0
            // 正数 top 小于负数 asteroids[i] 的绝对值, top 行星爆炸, 移除栈顶 top
            stack.removeLast();
            // 继续判断下一个栈顶与 asteroid 谁爆炸还是 asteroid 入栈
            twoAsteroidCollision(stack, asteroid);
        }/* else {
            // 正数 top 大于负数 asteroids[i] 的绝对值, asteroid 行星爆炸, asteroid 不入栈顶
            return;
        }*/
    }
}