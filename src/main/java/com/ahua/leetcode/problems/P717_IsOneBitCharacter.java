package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-20 1:26
 */

/**
 * 717. 1比特与2比特字符 1-bit-and-2-bit-characters
 * <p>
 * 有两种特殊字符：
 * <p>
 * 第一种字符可以用一个比特 0 来表示
 * 第二种字符可以用两个比特(10 或 11)来表示、
 * 给定一个以 0 结尾的二进制数组 bits ，如果最后一个字符必须是一位字符，则返回 true 。
 * <p>
 * 1 <= bits.length <= 1000
 * bits[i] == 0 or 1
 */
public class P717_IsOneBitCharacter {
    public static void main(String[] args) {
        int[] bits = new int[]{1, 0, 0};
        int[] bits1 = new int[]{1, 1, 1, 0};
        int[] bits2 = new int[]{1, 1, 0};
        P717_Solution solution = new P717_Solution();
        System.out.println(solution.isOneBitCharacter(bits)); // true
        System.out.println(solution.isOneBitCharacter(bits1)); // false
        System.out.println(solution.isOneBitCharacter(bits2)); // true
    }
}

class P717_Solution {
    // 几乎等同官方解法
    // 正序遍历
    // O(N) O(1)
    // 0 ms 100.00%
    // 40.8 MB 11.57%
    public boolean isOneBitCharacter1(int[] bits) {
        int n = bits.length;
//        for (int i = 0; i <= n - 2; i++) {
//            if (bits[i] == 1) {
//                i++;
//            }
//        }
        int i = 0;
        while (i <= n - 2) {
            if (bits[i] == 1) {
                i++;
            }
            i++;
        }
        return i == n - 1;
    }

    // 倒序遍历
    // 倒数第二个 0 到最后一个 0 之间的所有 1 的个数为偶数个才满足返回 true
    // O(N) O(1)
    // 0 ms 100.00%
    // 40.6 MB 18.21%
    public boolean isOneBitCharacter(int[] bits) {
        int n = bits.length;
        int i = n - 2;
        while (i >= 0 && bits[i] == 1) {
            i--;
        }
        return (n - i - 2) % 2 == 0;
    }
}