package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-25 19:36
 */

/**
 * 537. 复数乘法 complex-number-multiplication
 * 复数 可以用字符串表示，遵循 "实部+虚部i" 的形式，并满足下述条件：
 * <p>
 * 实部 是一个整数，取值范围是 [-100, 100]
 * 虚部 也是一个整数，取值范围是 [-100, 100]
 * i2 == -1
 * 给你两个字符串表示的复数 num1 和 num2 ，请你遵循复数表示形式，返回表示它们乘积的字符串。
 * <p>
 * num1 和 num2 都是有效的复数表示。
 */
public class P537_ComplexNumberMultiply {
    public static void main(String[] args) {
        P537_Solution solution = new P537_Solution();
        System.out.println(solution.complexNumberMultiply("1+1i", "1+1i")); // "0+2i"
        System.out.println(solution.complexNumberMultiply("1+-1i", "1+-1i")); // "0+-2i"

    }
}

// 基本操作
class P537_Solution {
    public String complexNumberMultiply(String num1, String num2) {
        // "\\"表示转义字符 "|"表示可以使用两个字符进行分割
        String[] complex1 = num1.split("\\+|i");
        String[] complex2 = num2.split("[+i]");
        int real1 = Integer.parseInt(complex1[0]);
        int imag1 = Integer.parseInt(complex1[1]);
        int real2 = Integer.parseInt(complex2[0]);
        int imag2 = Integer.parseInt(complex2[1]);
        // 7 ms 37.60%
        // 39.9 MB 5.06%
        return (real1 * real2 - imag1 * imag2) + "+" + (real1 * imag2 + real2 * imag1) + "i";
        // 8 ms 26.93%
        // 39.7 MB 11.73%
        // return String.format("%d+%di", real1 * real2 - imag1 * imag2, real1 * imag2 + imag1 * real2);
    }
}