package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-30 0:17
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * 2024. 考试的最大困扰度 maximize-the-confusion-of-an-exam
 * 一位老师正在出一场由 n 道判断题构成的考试，每道题的答案为 true （用 'T' 表示）或者 false （用 'F' 表示）。
 * 老师想增加学生对自己做出答案的不确定性，方法是 最大化 有 连续相同 结果的题数。（也就是连续出现 true 或者连续出现 false）。
 * <p>
 * 给你一个字符串 answerKey ，其中 answerKey[i] 是第 i 个问题的正确结果。
 * 除此以外，还给你一个整数 k ，表示你能进行以下操作的最多次数：
 * <p>
 * 每次操作中，将问题的正确答案改为 'T' 或者 'F' （也就是将 answerKey[i] 改为 'T' 或者 'F' ）。
 * 请你返回在不超过 k 次操作的情况下，最大 连续 'T' 或者 'F' 的数目。
 * <p>
 * n == answerKey.length
 * 1 <= n <= 5 * 10^4
 * answerKey[i] 要么是 'T' ，要么是 'F'
 * 1 <= k <= n
 */
public class P2024_MaxConsecutiveAnswers {
    public static void main(String[] args) {
        P2024_Solution solution = new P2024_Solution();
        System.out.println(solution.maxConsecutiveAnswers("TTFF", 2)); // 4
        System.out.println(solution.maxConsecutiveAnswers("TFFT", 1)); // 3
        System.out.println(solution.maxConsecutiveAnswers("TTFTTFTT", 1)); // 5
    }
}

// 滑动窗口
class P2024_Solution {
    public int maxConsecutiveAnswers(String answerKey, int k) {
        // 'T' 变成 'F' 与 'F' 变成 'T' 的最大数目
        return Math.max(max(answerKey, k, 'T'), max(answerKey, k, 'F'));
    }

    // 17 ms 21.79%
    // 41.6 MB 40.17%
    private int max(String answerKey, int k, char ch) {
        int n = answerKey.length();
        int left = 0;
        int count = 0;
        int max = 0;
        for (int right = 0; right < n; right++) {
            if (answerKey.charAt(right) == ch) {
                count++;
                while (count > k) {
                    if (answerKey.charAt(left) == ch) {
                        count--;
                    }
                    left++;
                }
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    // 以为增加了空间, 可以减小时间的消耗, 结果并没有
    // 19 ms 16.67
    // 42.7 MB 5.12%
    private int max1(String answerKey, int k, char ch) {
        int n = answerKey.length();
        int left = 0;
        int count = 0;
        int max = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int right = 0; right < n; right++) {
            if (answerKey.charAt(right) == ch) {
                queue.add(right);
                count++;
                if (count > k) {
                    left = queue.remove() + 1;
                    count--;
                }
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }
}