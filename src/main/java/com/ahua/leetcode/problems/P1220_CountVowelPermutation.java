package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-17 22:56
 */

import java.util.Arrays;

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
        System.out.println(solution.countVowelPermutation(20000)); // 759959057
    }
}

// 动态规划(同官方解法一), 有些像 P1411 以及我的那道笔试题
class P1220_Solution {
    // 二维动态规划
    // 18 ms (8.49%), 38.4 M (37.74%)
    public int countVowelPermutation1(int n) {
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
//            // 这里最后没必要再 % mod 了
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
            // 第一个 % mod 可以省略, 第二个 % mod 不能省略
            ans = (ans + dp[n - 1][i] % mod) % mod;
            // System.out.println(dp[n - 2][i]);
            // System.out.println(dp[n - 1][i]);
            // System.out.println("ans = " + ans);
        }
        return (int) ans;
    }

    // 第一次修改优化
    // 13 ms (45.28%), 37.6 M (61.32%)
    // 果然是不用提前创建完整个二维数组更快些
    public int countVowelPermutation1_1(int n) {
        final int mod = 1000000007;
        long[] dp = new long[5];
        dp[0] = 1; // a
        dp[1] = 1; // e
        dp[2] = 1; // i
        dp[3] = 1; // o
        dp[4] = 1; // u
        /*
         每行的 a e i o u 表示它的前面可以是那些字母
           a  e  i  o  u
        a  0  1  1  0  1  124
        e  1  0  1  0  0  02
        i  0  1  0  1  0  13
        o  0  0  1  0  0  2
        u  0  0  1  1  0  23
         */
        long[] temp;
        for (int i = 1; i < n; i++) {
            temp = Arrays.copyOf(dp, 5);
            dp[0] = temp[1] % mod + temp[2] % mod + temp[4] % mod;
            dp[1] = temp[0] % mod + temp[2] % mod;
            dp[2] = temp[1] % mod + temp[3] % mod;
            dp[3] = temp[2] % mod;
            dp[4] = temp[2] % mod + temp[3] % mod;
        }
        long ans = 0;
        for (int i = 0; i < 5; i++) {
            // 第一个 % mod 可以省略, 第二个 % mod 不能省略
            ans = (ans + dp[i] % mod) % mod;
        }
        return (int) ans;
    }

    // 第二次修改优化
    // 12 ms (53.77%), 35 M (96.23%)
    // 不使用 Arrays.copyOf(dp, 5) , 而是手动赋值
    public int countVowelPermutation1_2(int n) {
        final int mod = 1000000007;
        long[] dp = new long[5];
        dp[0] = 1; // a
        dp[1] = 1; // e
        dp[2] = 1; // i
        dp[3] = 1; // o
        dp[4] = 1; // u
        /*
         每行的 a e i o u 表示它的前面可以是那些字母
           a  e  i  o  u
        a  0  1  1  0  1  124
        e  1  0  1  0  0  02
        i  0  1  0  1  0  13
        o  0  0  1  0  0  2
        u  0  0  1  1  0  23
         */
        long[] temp = new long[5];
        for (int i = 1; i < n; i++) {
            temp[0] = dp[0];
            temp[1] = dp[1];
            temp[2] = dp[2];
            temp[3] = dp[3];
            temp[4] = dp[4];
            dp[0] = temp[1] % mod + temp[2] % mod + temp[4] % mod;
            dp[1] = temp[0] % mod + temp[2] % mod;
            dp[2] = temp[1] % mod + temp[3] % mod;
            dp[3] = temp[2] % mod;
            dp[4] = temp[2] % mod + temp[3] % mod;
        }
        long ans = 0;
        for (int i = 0; i < 5; i++) {
            // 第一个 % mod 可以省略, 第二个 % mod 不能省略
            ans = (ans + dp[i] % mod) % mod;
        }
        return (int) ans;
    }

    // 第三次修改优化(使用动态规划做, 这种应该是最优的了)
    // 7 ms (74.53%), 35.1 M (89.62%)
    // 使用 5 个变量保存前一次的结果
    public int countVowelPermutation(int n) {
        final int mod = 1000000007;
        long[] dp = new long[5];
        dp[0] = 1; // a
        dp[1] = 1; // e
        dp[2] = 1; // i
        dp[3] = 1; // o
        dp[4] = 1; // u
        /*
         每行的 a e i o u 表示它的前面可以是那些字母
           a  e  i  o  u
        a  0  1  1  0  1  124
        e  1  0  1  0  0  02
        i  0  1  0  1  0  13
        o  0  0  1  0  0  2
        u  0  0  1  1  0  23
         */
        long a, e, i, o, u;
        for (int j = 1; j < n; j++) {
            a = dp[0];
            e = dp[1];
            i = dp[2];
            o = dp[3];
            u = dp[4];
            // a 前面可以为 e  i  u
            dp[0] = (e + i + u) % mod;
            // e 前面可以为 a  i
            dp[1] = (a + i) % mod;
            // i 前面可以为 e  o
            dp[2] = (e + o) % mod;
            // o 前面可以为 i
            dp[3] = i % mod;
            // u 前面可以为 i  o
            dp[4] = (i + o) % mod;
        }
        long ans = 0;
        for (int j = 0; j < 5; j++) {
            // 第一个 % mod 可以省略, 第二个 % mod 不能省略
            // ans = (ans + dp[j] % mod) % mod;
            ans = (ans + dp[j]) % mod;
        }
        return (int) ans;
    }
}