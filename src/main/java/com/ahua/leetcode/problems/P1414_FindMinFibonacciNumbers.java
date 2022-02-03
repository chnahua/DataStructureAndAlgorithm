package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-03 21:39
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 1414. 和为 K 的最少斐波那契数字数目 find-the-minimum-number-of-fibonacci-numbers-whose-sum-is-k
 * 给你数字 k ，请你返回和为 k 的斐波那契数字的最少数目，其中，每个斐波那契数字都可以被使用多次。
 * <p>
 * 斐波那契数字定义为：
 * <p>
 * F1 = 1
 * F2 = 1
 * Fn = Fn-1 + Fn-2 ， 其中 n > 2 。
 * 数据保证对于给定的 k ，一定能找到可行解。
 * <p>
 * 1 <= k <= 10^9
 */
public class P1414_FindMinFibonacciNumbers {
    public static void main(String[] args) {
        P1414_Solution solution = new P1414_Solution();
        System.out.println(solution.findMinFibonacciNumbers(5)); // 1
        System.out.println(solution.findMinFibonacciNumbers(7)); // 2
        System.out.println(solution.findMinFibonacciNumbers(10)); // 2
        System.out.println(solution.findMinFibonacciNumbers(19)); // 3
        System.out.println(solution.findMinFibonacciNumbers(10000)); // 6
    }
}

// 我的解法 - 递归
// 2 ms 60.57%
class P1414_Solution1 {
    List<Integer> fibonacci;

    public int findMinFibonacciNumbers(int k) {
        fibonacci = new ArrayList<>();
        // 动态规划, 求出所有小于等于 k 的 fibonacci 数, 将其添加进 fibonacci 数组中
        fibonacciDP(k);
        // System.out.println(fibonacci);
        return getMinFibonacciNumbers(fibonacci.size() - 1, k);
    }

    public void fibonacciDP(int k) {
        int first = 1;
        int second = 1;
        fibonacci.add(first);
        fibonacci.add(second);
//        // 第一种写法
//        // 先计算(当前次要添加的 fibonacci 数), 当前次添加
//        int cur;
//        while (first + second <= k) {
//            // 当前次要添加进 fibonacci 数组的数
//            cur = first + second;
//            first = second;
//            second = cur;
//            fibonacci.add(cur);
//        }
        // 第二种写法, 变换一下代码顺序
        // 先添加(上次计算出的 fibonacci 数), 再计算(下次要添加的 fibonacci 数)
        int cur = first + second;
        while (cur <= k) {
            fibonacci.add(cur);
            first = second;
            second = cur;
            // 下一次要添加进 fibonacci 数组的数
            cur = first + second;
        }
    }

    public int getMinFibonacciNumbers(int index, int k) {
        int res = 0;
        for (int i = index; i >= 0; i--) {
            // 刚好相等, 说明找到, 返回 1(表示一个数)
            // 不能使用 fibonacci.contains(k) 来判断, 这样更慢
            if (fibonacci.get(i) == k) {
                return 1;
            }
//            // 数组中的数比 k 大, 进行下次循环, 找一个比 k 更小的数再 dfs
//            if (fibonacci.get(i) > k) {
//                continue;
//            }
//            // 数组中的数比 k 小, 就从当前位置处往前找是否存在等于 k - fibonacci.get(i) 的数
//            res = getMinFibonacciNumbers(i, k - fibonacci.get(i));
//            // 根据返回值 res 判断此次是否找到, 如果找到则说明找到了和为 k 的斐波那契数字的最少数目, 就退出循环
//            if (res > 0) {
//                break;
//            }

            // 代码简化
            // 数组中的数比 k 小, 就从当前位置处往前找是否存在等于 k - fibonacci.get(i) 的数
            // 如果比 k 大, 进行下次循环, 找一个比 k 更小的数再 dfs
            if (fibonacci.get(i) < k) {
                res = getMinFibonacciNumbers(i, k - fibonacci.get(i));
                // 根据返回值 res 判断此次是否找到, 如果找到则说明找到了和为 k 的斐波那契数字的最少数目, 就退出循环
                if (res > 0) {
                    break;
                }
            }
        }
        return res > 0 ? res + 1 : 0;
    }
}

// 官方解法 - 贪心
class P1414_Solution2 {
    public int findMinFibonacciNumbers(int k) {
        List<Integer> f = new ArrayList<>();
        f.add(1);
        int a = 1, b = 1;
        while (a + b <= k) {
            int c = a + b;
            f.add(c);
            a = b;
            b = c;
        }
        int ans = 0;
        for (int i = f.size() - 1; i >= 0 && k > 0; i--) {
            int num = f.get(i);
            if (k >= num) {
                k -= num;
                ans++;
            }
        }
        return ans;
    }
}

/**
 * 贪心
 * 作者：AC_OIer
 * 链接：https://leetcode-cn.com/problems/find-the-minimum-number-of-fibonacci-numbers-whose-sum-is-k/solution/gong-shui-san-xie-noxiang-xin-ke-xue-xi-rgty8/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
class P1414_Solution {
    public int findMinFibonacciNumbers(int k) {
        int a = 1, b = 1;
        while (b <= k) {
            int c = a + b;
            a = b; b = c;
        }
        int ans = 0;
        while (k != 0) {
            if (k >= b) {
                k -= b; ans++;
            }
            int c = b - a;
            b = a; a = c;
        }
        return ans;
    }
}