package com.ahua.nowcoder.huawei;

import java.util.Scanner;

/**
 * @author huajun
 * @create 2021-12-02 22:50
 */

public class HJ61_PutApples {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()) {
            // m代表的是水果数量
            int m = sc.nextInt();
            // n代表的是盘子的数量
            int n = sc.nextInt();

            int[][] dp = new int[m + 1][n + 1];

            // 赋初值
            dp[0][0] = 0;
            // 首列为0
            // 第二列为1,盘子只有一个时,只有一种
            for (int i = 1; i <= m; i++) {
                dp[i][0] = 0;
                dp[i][1] = 1;
            }
            // 首行为0
            // 第二行为1,水果只有一个时,只有一种
            for (int j = 1; j <= n; j++) {
                dp[0][j] = 0;
                dp[1][j] = 1;
            }

            for (int i = 2; i <= m; i++) {
                for (int j = 2; j <= n; j++) {
                    if (i < j) {
                        // 如果果子数i小于盘子数j,那么把这个i个果子放入j个盘子中的方法数就和将这i个果子放入i个盘子的方法数一样
                        // 这种情况下dp[i][j - 1]和dp[i][i]的值是一样的，也就是写成dp[i][j] = dp[i][i];也应是对的
                        // dp[i][j] = dp[i][i];
                        dp[i][j] = dp[i][j - 1];
                    } else if (i == j) {
                        // 此时盘子多了一个,刚好使得盘子数和果子数相等,此时多的这一种方法就是j个盘子分别装了一个果子
                        dp[i][j] = dp[i][j - 1] + 1;
                    } else {
                        // 如果此次循环果子数i大于等于盘子数j,此处有两种情况，两种情况的值加起来就为dp[i][j]值
                        // 1.假设这个（第j个）盘子不放入果子
                        // 那么就要将这i个果子放入之前的（j-1）个盘子中,此时不用考虑在这（j - 1）个盘子中怎样放置,不就为dp[i][j - 1]吗？此时应该想到这里面包含了有些盘子并没有被放到果子
                        // 2.假设这个（第j个）盘子要放入果子,此时应该还要确保前（j-1）个盘子至少要放入一个果子,即不能为空
                        // 因为如果不放入,即让其中的某个盘子空了的话,不就和第一种假设的情况存在重复？即两种假设中都存在有空盘子（第一种是至少第j个是空的,此处是前（j-1）个盘子中有空的,会导致重复情况）
                        // 于是,在确保了这j个盘子都不为空（即都先放入一个果子）的条件下,那么剩下的（i - j）个果子要放入j个盘子的方法数就等于dp[i - j][j];
                        // 最终,两者之和即为总数
                        dp[i][j] = dp[i][j - 1] + dp[i - j][j];
                    }
                }
            }
            System.out.println(dp[m][n]);
        }
        sc.close();
    }
}