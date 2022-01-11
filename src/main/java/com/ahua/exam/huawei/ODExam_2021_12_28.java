package com.ahua.exam.huawei;

import java.util.Scanner;

/**
 * @author huajun
 * @create 2021-12-28 21:41
 */

public class ODExam_2021_12_28 {
    public static void main(String[] args) {

    }
}

/**
 * 爬楼梯, 每次只能爬 1 步或者 3 步, 请问到达第 n 级台阶的不同方法数
 * 0 < n <= 50
 * 通过 100%
 */
class exam1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            if (n == 1 || n == 2) {
                System.out.println(1);
                continue;
            }
            int[] dp = new int[5];
            dp[1] = 1;
            dp[2] = 1;
            dp[3] = 2;
            for (int i = 4; i <= n; i++) {
                dp[4] = dp[3] + dp[1];
                dp[1] = dp[2];
                dp[2] = dp[3];
                dp[3] = dp[4];
            }
            System.out.println(dp[3]);
        }
    }
}

/**
 * 一个只有小写字母组成的字符串，交换其中两个字母(只交换一次)，使得该字符串的字典序最小，返回交换后的字符串
 * 1 <= n <= 10000
 * 通过 93.33%
 * <p>
 * 有一种情况没有考虑到，字符串的第一个字符就是整个字符串中的最小字符，并且只出现一次，那么 minCharIndex 下标就一直为 0
 * 但是整个字符串中的字符并不是单调递增的，在后续部分如果出现某个字符比前一个字符小的话，最终肯定是要交换一次的，只是这个交换的最小字符不是
 * 字符串的第一个字符(整体最小字符)，而是在一段连续递增字符后遇到的另一个最小字符
 * 如 abcdefg 不用交换
 * 而 abcdfegc 则需要将第二个 c 与 d 交换
 */
class exam2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            StringBuilder str = new StringBuilder(sc.next());
            int n = str.length();
            if (n == 0 || n == 1) {
                System.out.println(str);
                continue;
            } else if (n == 2) {
                if (str.charAt(0) <= str.charAt(1)) {
                    System.out.println(str);
                } else {
                    System.out.println(str.charAt(1) + str.charAt(0));
                }
                continue;
            }
            // 最小字符的下标
            int minCharIndex = 0;
            // 最小字符
            char minChar = str.charAt(0);
            // 默认 str 是递增子串
            boolean isIncreasingStr = true;
            for (int i = 1; i < n; i++) {
                if (str.charAt(i) <= minChar) {
                    minCharIndex = i;
                    minChar = str.charAt(i);
                }
                if (isIncreasingStr) {
                    // 此处表示不是递增子串了，那么 minCharIndex 就在 i 处后产生
                    if (str.charAt(i) < str.charAt(i - 1)) {
                        minCharIndex = i;
                        minChar = str.charAt(i);
                        isIncreasingStr = false;
                    }
                }
            }
            // 如果此处 minCharIndex 还为 0, 说明该字符串是递增字符串
            if (minCharIndex != 0) {
                // minChar = str.charAt(minCharIndex);
                // 交换最小字母和第一个比它大的字母
                // 可以在前面使用 hashmap 存储各个前面非严格递增的 k 个字符，相等的字符只存储第一个出现的位置
                // 这样就可以在使用 O(k) (最多 O(n)) 空间的 hashmap 的情况下，能在交换时 O(1) 的交换了，而不是 O(k)
                for (int i = 0; i < n; i++) {
                    if (str.charAt(i) > minChar) {
                        str.replace(minCharIndex, minCharIndex + 1, String.valueOf(str.charAt(i)));
                        str.replace(i, i + 1, String.valueOf(minChar));
                        break;
                    }
                }
            }
            System.out.println(str);
        }
    }
}

/**
 * 一个二维数组表示一个矩形，其值只能为 "F"、"M"，求出相连的 "M" 最多有多少个？
 * 相连的定义为: 水平方向、垂直方向、对角线方向、反对角线方向
 * 通过 85.42%
 */
/*
案例

3,4
F,M,M,F
F,M,M,F
F,F,F,M
输出 3

5,6
F,M,M,M,F,M
M,F,M,M,F,M
F,M,M,F,M,F
M,M,F,M,F,M
M,F,F,M,M,M
输出 4
 */
class exam3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            char[] charArray = sc.nextLine().toCharArray();
            int charLen = charArray.length;
            int n = charArray[0] - 48;
            int m = charArray[charLen - 1] - 48;
            String[][] str = new String[n][m];
            String s = "";
//            int t = 0;
//            while (sc.hasNextLine()) {
//                s = sc.nextLine();
//                if (!s.equals("")) {
//                    str[t] = s.split(",");
//                    t++;
//                }
//            }
            for (int i = 0; i < n; i++) {
                // 牛客网报错 貌似是 java.util.NoSuchElementException 还有个 是 no find line
                s = sc.nextLine();
                if (s != null) {
                    str[i] = s.split(",");
                }
            }
            // System.out.println(Arrays.deepToString(str));
            if (n == 0 || m == 0) {
                System.out.println(0);
                continue;
            }
            if (n == 1 && m == 1) {
                System.out.println("M".equals(str[0][0]) ? 1 : 0);
                continue;
            }
            // dp[i][j][0] 表示 水平方向
            // dp[i][j][1] 表示 垂直方向
            // dp[i][j][2] 表示 对角线方向
            // dp[i][j][3] 表示 反对角线方向
            int[][][] dp = new int[n + 2][m + 2][4];

            int maxMan = 0;
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    if ("M".equals(str[i - 1][j - 1])) {
                        // 水平方向
                        dp[i][j][0] = dp[i][j - 1][0] + 1;
                        // 垂直方向
                        dp[i][j][1] = dp[i - 1][j][1] + 1;
                        // 对角线方向
                        dp[i][j][2] = dp[i - 1][j - 1][2] + 1;
                        // 反对角线方向
                        dp[i][j][3] = dp[i - 1][j + 1][3] + 1;
                        // 当前次循环中的最大值 maxMan
                        for (int k = 0; k < 4; k++) {
                            maxMan = Math.max(maxMan, dp[i][j][k]);
                        }
                    }
                }
            }
            System.out.println(maxMan);
        }
    }
}