package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-29 23:51
 */

/**
 * 2011. 执行操作后的变量值 final-value-of-variable-after-performing-operations
 * 存在一种仅支持 4 种操作和 1 个变量 X 的编程语言：
 * <p>
 * ++X 和 X++ 使变量 X 的值 加 1
 * --X 和 X-- 使变量 X 的值 减 1
 * 最初，X 的值是 0
 * <p>
 * 给你一个字符串数组 operations ，这是由操作组成的一个列表，返回执行所有操作后， X 的 最终值 。
 * <p>
 * 1 <= operations.length <= 100
 * operations[i] 将会是 "++X"、"X++"、"--X" 或 "X--"
 */
public class P2011_FinalValueAfterOperations {
    public static void main(String[] args) {
        String[] operations = new String[]{"--X", "X++", "X++"};
        String[] operations1 = new String[]{"++X", "++X", "X++"};
        String[] operations2 = new String[]{"X++", "++X", "--X", "X--"};
        P2011_Solution solution = new P2011_Solution();
        System.out.println(solution.finalValueAfterOperations(operations)); // 1
        System.out.println(solution.finalValueAfterOperations(operations1)); // 3
        System.out.println(solution.finalValueAfterOperations(operations2)); // 0
    }
}

// 基本操作 简单题
class P2011_Solution {
    // 1 ms 60.44%
    // 41.2 MB 7.72%
    public int finalValueAfterOperations1(String[] operations) {
        int x = 0;
        for (String operation : operations) {
            if ("X++".equals(operation) || "++X".equals(operation)) {
                x++;
            } else {
                x--;
            }
        }
        return x;
    }

    // 0 ms 100.00%
    // 40.8 MB 42.41%
    public int finalValueAfterOperations(String[] operations) {
        int x = 0;
        for (String operation : operations) {
            if (operation.charAt(1) == '+') {
                x++;
            } else {
                x--;
            }
        }
        return x;
    }
}