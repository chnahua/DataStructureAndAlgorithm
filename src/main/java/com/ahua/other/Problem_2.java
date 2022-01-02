package com.ahua.other;

/**
 * @author huajun
 * @create 2022-01-02 0:38
 */

/**
 * 最长连续方波
 *
 * 一行 "01" 字符串表示方波信号, 信号以 0 开始和结束, 01间隔出现的信号为完美信号, 出现连续连续两个 1 的为不完美信号
 * 求最长完美信号及其长度
 */
public class Problem_2 {
    public static void main(String[] args) {
        String str = "00101010101100001010010";
        String str1 = "001110101001010010101010";
        String str2 = "001101010101010101001110";
        String str3 = "0011001010001110101100101001110";
        O2_Solution solution = new O2_Solution();
        System.out.println(solution.test1(str)); // 5
        System.out.println(solution.test1(str1)); // 9
        System.out.println(solution.test1(str2)); // 1
        System.out.println(solution.test1(str3)); // 5
    }
}

class O2_Solution {
    public int test1(String str) {
        int n = str.length();
        // 前一个字符
        char pre = str.charAt(0);
        // 当前字符
        char cur;
        // 完美子串的起始、末尾下标、长度
        int start = 0, end = 0, max = 0;
        // 为 true 表示是完美子串
        boolean flag = true;
        for (int i = 1; i < n; i++) {
            // 当前遍历的字符
            cur = str.charAt(i);
            // 正在遍历的该子串(从 start 到 end)是完美子串时, 判断当前第 i 个是否能加进该完美子串中
            if (flag) {
                if (pre == '0') {
                    // [...01...]
                    if (cur == '1') {
                        // 前一个是 0, 当前是 1, 那么当前这个 1 可以加进去, 此时完美子串的末尾下标为 i
                        end = i;
                    } else {
                        // [...00...]
                        // 前一个是 0, 当前是 0, 连续遇到两个 0, 说明在 i-1 和 i 之间截断了, 计算 max 值并比较, 以及更新 start end
                        // 下一个完美子串就从当前 i 这个 0 开始
                        max = Math.max(max, end - start + 1);
                        // 打印这个完美子串
                        System.out.println("  完美子串 : " + str.substring(start, i));
                        start = i;
                        end = i;
                    }
                } else {
                    // [...10...]
                    // 前一个是 1, 当前是 0, 那么当前这个 0 可以加进去, 此时完美子串的末尾下标为 i
                    if (cur == '0') {
                        end = i;
                    } else {
                        // [...11...]
                        // 前一个是 1, 当前是 1, 连续遇到两个 1, 说明这个之前一直保持是完美子串的子串现在不完美了, flag 置为 false
                        // 并且此时的 start 和 end 已经没用了, 等这个不完美子串找到结束 0 时，再重新赋值
                        flag = false;
                    }
                }
            } else {
                // 这里就说明当前这个从 start 开始到 当前 i 的子串是不完美子串, 需要找到它的末尾 0
                // 而这种情况只能是当有连续两个 0 时才出现, 其他的什么 10 11 01 等都不是结束标志, 都要将这个 0 或 1 添加到这个不完美子串中
                // [...00...]
                if (pre == '0' && cur == '0') {
                    // 为 00 时, 说明找到, 重新赋值, 下一个完美子串就从当前 i 这个 0 开始
                    // 打印这个不完美子串
                    System.out.println("不完美子串 : " + str.substring(start, i));
                    start = i;
                    end = i;
                    flag = true;
                }
            }
            // 对于下一次要遍历的 i+1 字符来说, 此处的 cur 就为 pre 了
            pre = cur;
        }

        if (flag) {
            // 打印最后这个完美子串
            System.out.println("  完美子串 : " + str.substring(start, n));
            return Math.max(max, end - start + 1);
        } else {
            // 打印最后这个不完美子串
            System.out.println("不完美子串 : " + str.substring(start, n));
            return max;
        }
    }
}