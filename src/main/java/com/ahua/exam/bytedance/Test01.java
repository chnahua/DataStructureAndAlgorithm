package com.ahua.exam.bytedance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author huajun
 * @create 2021-10-10 21:11
 */

/*
 * 一个字符串由0-9数字、大小写字母和问号“?”组成
 * 要求将该字符串中的
 * 数字从大到小排序放在原来是数字的位置
 * 字母按照字典顺序放在原来是字母的位置
 * 问号“?”则不做任何改变
 */
public class Test01 {
    public static void main(String[] args) {
        String str = "21?zcDA?53";

        char[] charArray1 = str.toCharArray();
        Solution_1(charArray1);
        System.out.println(charArray1);
        // 之前输出为 53?    ?21, 字母不见了?
        // 原因在于从小到大排序后字母是在数组后面部分,而不是从0开始的前面部分

        char[] charArray2 = str.toCharArray();
        Solution_2(charArray2);
        System.out.println(charArray2);// 输出为 53?ADcz?21
    }

    public static void Solution_1(char[] charArray) {
        // 保存数字的数组,后续对其排序
        char[] tempNum = new char[charArray.length];
        // 保存字母的数组,后续对其排序
        char[] tempChar = new char[charArray.length];
        int j = 0, k = 0;
        // 遍历一遍,将数字和字母保存到对应的数组中
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] >= '0' && charArray[i] <= '9') {
                tempNum[j] = charArray[i];
                j++;
            } else if ((charArray[i] >= 'a' && charArray[i] <= 'z') || (charArray[i] >= 'A' && charArray[i] <= 'Z')) {
                tempChar[k] = charArray[i];
                k++;
            }
        }
        /*
        for(int i = k; i < tempChar.length; i++) {
            tempChar[i] = '|';
        }
        k = 0;
        */
        // 对数字数组和字母数组排序
        Arrays.sort(tempNum);
        Arrays.sort(tempChar);
        // System.out.println(tempNum);
        // System.out.println(tempChar);
        // 将原字符串中的各数字和字母位置处的值按照相应顺序依次替换为tempNum和tempChar中的值
        // 从 tempNum.length - j 开始为从小到大的数字,需求是数字从大到小排序,于是 j = tempNum.length - 1;
        j = tempNum.length - 1;
        // 从 tempChar.length - k 开始为从小到大的字母,需求是字母从小到大排序,于是 k = tempChar.length - k;
        k = tempChar.length - k;
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] >= '0' && charArray[i] <= '9') {
                charArray[i] = tempNum[j];
                j--;
            } else if ((charArray[i] >= 'a' && charArray[i] <= 'z') || (charArray[i] >= 'A' && charArray[i] <= 'Z')) {
                charArray[i] = tempChar[k];
                k++;
            }
            // System.out.println(charArray[i]);
        }
    }

    public static void Solution_2(char[] charArray) {
        // 保存数字的数组,后续对其排序
        ArrayList<Character> tempNum = new ArrayList<>();
        // 保存字母的数组,后续对其排序
        ArrayList<Character> tempChar = new ArrayList<>();
        // 遍历一遍,将数字和字母保存到对应的数组中
        for (char c : charArray) {
            if (c >= '0' && c <= '9') {
                tempNum.add(c);
            } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                tempChar.add(c);
            }
        }
        // 对数字数组和字母数组排序
        Collections.sort(tempNum);
        Collections.sort(tempChar);
        // 将原字符串中的各数字和字母位置处的值按照相应顺序依次替换为tempNum和tempChar中的值
        int j = tempNum.size() - 1;
        int k = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] >= '0' && charArray[i] <= '9') {
                charArray[i] = tempNum.get(j);
                j--;
            } else if ((charArray[i] >= 'a' && charArray[i] <= 'z') || (charArray[i] >= 'A' && charArray[i] <= 'Z')) {
                charArray[i] = tempChar.get(k);
                k++;
            }
            // System.out.println(charArray[i]);
        }
    }
}
