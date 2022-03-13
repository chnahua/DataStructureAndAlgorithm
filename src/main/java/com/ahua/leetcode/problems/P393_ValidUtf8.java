package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-13 22:29
 */

/**
 * 393. UTF-8 编码验证 utf-8-validation
 * 给定一个表示数据的整数数组 data ，返回它是否为有效的 UTF-8 编码。
 * <p>
 * UTF-8 中的一个字符可能的长度为 1 到 4 字节，遵循以下的规则：
 * <p>
 * 对于 1 字节 的字符，字节的第一位设为 0 ，后面 7 位为这个符号的 unicode 码。
 * 对于 n 字节 的字符 (n > 1)，第一个字节的前 n 位都设为1，第 n+1 位设为 0 ，后面字节的前两位一律设为 10 。
 * 剩下的没有提及的二进制位，全部为这个符号的 unicode 码。
 * <p>
 * 这是 UTF-8 编码的工作方式：
 * <p>
 * Char. number range  |        UTF-8 octet sequence
 * (hexadecimal)    |              (binary)
 * --------------------+---------------------------------------------
 * 0000 0000-0000 007F | 0xxxxxxx
 * 0000 0080-0000 07FF | 110xxxxx 10xxxxxx
 * 0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
 * 0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
 * 注意：输入是整数数组。只有每个整数的 最低 8 个有效位 用来存储数据。这意味着每个整数只表示 1 字节的数据。
 *
 * <p>
 * 1 <= data.length <= 2 * 10^4
 * 0 <= data[i] <= 255
 */
public class P393_ValidUtf8 {
    public static void main(String[] args) {
        int[] data = new int[]{197, 130, 1};
        int[] data1 = new int[]{235, 140, 4};
        P393_Solution solution = new P393_Solution();
        System.out.println(solution.validUtf8(data)); // true
        System.out.println(solution.validUtf8(data1)); // false

    }
}

// 基本操作 有字符串操作
// 哈哈, 一次通过, 舒服哎
// 3 ms 27.09%
// 41.7 MB 35.79%
class P393_Solution1 {
    public boolean validUtf8(int[] data) {
        int n = data.length;
        for (int i = 0; i < n; i++) {
            // 判断当前字符是几字节数据, 即连续多少个 '1', 返回值可能是 0 1 2 3 4 5
            int numPrefix1 = getNumPrefix1(data[i]);
            // 连续 1 个或者大于 5 个均是错误的
            if (numPrefix1 == 1 || numPrefix1 >= 5) {
                return false;
            }
            // 此处应为 numPrefix1 >= 2 && numPrefix1 <= 4, 但可简化
            if (numPrefix1 >= 2) {
                // 数组中没有剩余 numPrefix1 - 1 个延续字节(这么多个数)
                if (i + numPrefix1 - 1 > n - 1) {
                    return false;
                }
                for (int j = 1; j <= numPrefix1 - 1; j++) {
                    // 延续字节不是以 "10" 开头
                    if (!checkPrefix10(data[i + j])) {
                        return false;
                    }
                }
                i = i + numPrefix1 - 1;
            }
            // 如果 numPrefix1 == 0, 说明当前数据是一个字节的, 可直接判断下一个
        }
        return true;
    }

    private int getNumPrefix1(int data) {
        String s = binaryString(data);
        // 连续 1 的个数, 正确情况为 0 2 3 4
        int i = 0;
        for (; i <= 5; i++) {
            if (s.charAt(i) == '0') {
                return i;
            }
        }
        return i;
    }

    private String binaryString(int data) {
        String s = Integer.toBinaryString(data);
        int len = s.length();
        // len < 8 时需要手动填充首位 0
        if (len < 8) {
            StringBuilder binaryStr = new StringBuilder();
            while (len < 8) {
                binaryStr.append('0');
                len++;
            }
            binaryStr.append(s);
            s = binaryStr.toString();
        }
        return s;
    }

    private boolean checkPrefix10(int data) {
//        String s = binaryString(data);
//        return s.charAt(0) == '1' && s.charAt(1) == '0';
        // 等价于 return (data & (1 << 7)) == 128 && (data & (1 << 6)) == 0;
        return data >= 128 && data < 192;
    }
}

// 根据数是否小于 128 可以提前处理 1 字节数据, 而不用再手动为 1 字节数据的二进制表示填充首位 0
// 2 ms 47.83%
// 42.4 MB 5.02%
// 由于我使用到了字符串操作, 所以整体效率低了很多, 更优的解题方法是全部使用位运算来判断编码规则
class P393_Solution2 {
    public boolean validUtf8(int[] data) {
        int n = data.length;
        for (int i = 0; i < n; i++) {
            // 1 字节数据
            // 等价于 data < 128 或者 ((data[i] >> 7) & 1) == 0
            if ((data[i] >> 7) == 0) {
                continue;
            }
            // 判断当前字符是几字节数据, 即连续多少个 '1', 返回值可能是 1 2 3 4 5
            int numPrefix1 = getNumPrefix1(data[i]);
            // 连续 1 个或者大于 5 个 '1' 均是错误的
            if (numPrefix1 == 1 || numPrefix1 >= 5) {
                return false;
            }
            // 此处应为 numPrefix1 >= 2 && numPrefix1 <= 4, 但可简化
            if (numPrefix1 >= 2) {
                // 数组中没有剩余 numPrefix1 - 1 个延续字节(这么多个数)
                if (i + numPrefix1 - 1 > n - 1) {
                    return false;
                }
                for (int j = 1; j <= numPrefix1 - 1; j++) {
                    // 延续字节不是以 "10" 开头
                    if (!checkPrefix10(data[i + j])) {
                        return false;
                    }
                }
                i = i + numPrefix1 - 1;
            }
        }
        return true;
    }

    private int getNumPrefix1(int data) {
        String s = Integer.toBinaryString(data);
        // 连续 1 的个数, 正确情况为 0 2 3 4
        int i = 0;
        for (; i <= 5; i++) {
            if (s.charAt(i) == '0') {
                return i;
            }
        }
        return i;
    }

    private boolean checkPrefix10(int data) {
//        String s = binaryString(data);
//        return s.charAt(0) == '1' && s.charAt(1) == '0';
        // 等价于 return (data & (1 << 7)) == 128 && (data & (1 << 6)) == 0;
        return data >= 128 && data < 192;
    }
}

// 位运算
// 2 ms 47.83%
// 41.7 MB 31.44%
class P393_Solution {
    public boolean validUtf8(int[] data) {
        int n = data.length;
        for (int i = 0; i < n; ) {
            int j = 7;
            // 遍历完或遇到第一个 0 时退出循环
            while (j >= 0 && (((data[i] >> j) & 1) == 1)) {
                j--;
            }
            // 连续 1 的个数
            int cnt = 7 - j;
            if (cnt == 1 || cnt > 4) {
                return false;
            }
            if (i + cnt - 1 >= n) {
                return false;
            }
            for (int k = i + 1; k < i + cnt; k++) {
                if (!(((data[k] >> 7) == 1) && (((data[k] >> 6) & 1) == 0))) {
                    return false;
                }
            }
            if (cnt == 0) {
                i++;
            } else {
                i += cnt;
            }
        }
        return true;
    }
}