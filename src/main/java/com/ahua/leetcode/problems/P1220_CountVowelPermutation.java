package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-17 22:56
 */

/**
 * 1220. 统计元音字母序列的数目 count-vowels-permutation
 * 给你一个整数 n，请你帮忙统计一下我们可以按下述规则形成多少个长度为 n 的字符串：
 * <p>
 * 字符串中的每个字符都应当是小写元音字母（'a', 'e', 'i', 'o', 'u'）
 * 每个元音 'a' 后面都只能跟着 'e'
 * 每个元音 'e' 后面只能跟着 'a' 或者是 'i'
 * 每个元音 'i' 后面 不能 再跟着另一个 'i'
 * 每个元音 'o' 后面只能跟着 'i' 或者是 'u'
 * 每个元音 'u' 后面只能跟着 'a'
 * 由于答案可能会很大，所以请你返回 模 10^9 + 7 之后的结果。
 * <p>
 * 1 <= n <= 2 * 10^4
 */
public class P1220_CountVowelPermutation {
    public static void main(String[] args) {
        P1220_Solution solution = new P1220_Solution();
//        System.out.println(solution.countVowelPermutation(1)); // 5
//        System.out.println(solution.countVowelPermutation(2)); // 10
//        System.out.println(solution.countVowelPermutation(5)); // 68
        System.out.println(solution.countVowelPermutation(144)); // 18208803
    }
}

class P1220_Solution {
    public int countVowelPermutation(int n) {
        final int mod = 1000000007;
        long[][] dp = new long[n][5];
        dp[0][0] = 1; // a
        dp[0][1] = 1; // e
        dp[0][2] = 1; // i
        dp[0][3] = 1; // o
        dp[0][4] = 1; // u
        /*
         每行的 a e i o u 表示它的前面可以是那些字母
           a  e  i  o  u
        a  0  1  1  0  1  124
        e  1  0  1  0  0  02
        i  0  1  0  1  0  13
        o  0  0  1  0  0  2
        u  0  0  1  1  0  23
         */
        for (int i = 1; i < n; i++) {
//            dp[i][0] = (dp[i - 1][1] % mod + dp[i - 1][2] % mod + dp[i - 1][4] % mod) % mod;
//            dp[i][1] = (dp[i - 1][0] % mod + dp[i - 1][2] % mod) % mod;
//            dp[i][2] = (dp[i - 1][1] % mod + dp[i - 1][3] % mod) % mod;
//            dp[i][3] = dp[i - 1][2] % mod;
//            dp[i][4] = (dp[i - 1][2] % mod + dp[i - 1][3] % mod) % mod;

            dp[i][0] = dp[i - 1][1] % mod + dp[i - 1][2] % mod + dp[i - 1][4] % mod;
            dp[i][1] = dp[i - 1][0] % mod + dp[i - 1][2] % mod;
            dp[i][2] = dp[i - 1][1] % mod + dp[i - 1][3] % mod;
            dp[i][3] = dp[i - 1][2] % mod;
            dp[i][4] = dp[i - 1][2] % mod + dp[i - 1][3] % mod;
        }
        long ans = 0;
        for (int i = 0; i < 5; i++) {
            ans = (ans + dp[n - 1][i] % mod) % mod;
            // System.out.println(dp[n - 2][i]);
            // System.out.println(dp[n - 1][i]);
            // System.out.println("ans = " + ans);
        }
        return (int) ans;
    }
}