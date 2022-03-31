package com.ahua.leetcode.problems;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huajun
 * @create 2022-03-31 23:46
 */

/**
 * 728. 自除数 self-dividing-numbers
 * 自除数 是指可以被它包含的每一位数整除的数。
 * <p>
 * 例如，128 是一个 自除数 ，因为 128 % 1 == 0，128 % 2 == 0，128 % 8 == 0。
 * 自除数 不允许包含 0 。
 * <p>
 * 给定两个整数 left 和 right ，返回一个列表，列表的元素是范围 [left, right] 内所有的 自除数 。
 * 1 <= left <= right <= 10^4
 */
public class P728_SelfDividingNumbers {
    public static void main(String[] args) {
        P728_Solution solution = new P728_Solution();
        System.out.println(solution.selfDividingNumbers(1, 22)); // [1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22]
        System.out.println(solution.selfDividingNumbers(47, 85)); // [48,55,66,77]
    }
}

// 数学题 基本操作
// 1 ms 98.33%
// 39 MB 41.80%
class P728_Solution {
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> ans = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (i < 10) {
                ans.add(i);
                continue;
            }
            int num = i;
            // 个数上的数字
            int bit = num % 10;
            while (bit != 0 && i % bit == 0) {
                // 去掉个数上的数字后的数
                num /= 10;
                // 个数上的数字
                bit = num % 10;
            }
            // 结束时 num 应当等于 0, 说明当前 i 为自除数
            if (num == 0) {
                ans.add(i);
            }
        }
        return ans;
    }
}