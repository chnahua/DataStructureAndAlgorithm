package com.ahua.nowcoder.huawei;

import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author huajun
 * @create 2021-12-02 23:41
 */

// 第二次做
// 效率还没第一次做的高
public class HJ8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int key;
        int value;
        for (int i = 0; i < n; i++) {
            key = sc.nextInt();
            value = map.getOrDefault(key, 0) + sc.nextInt();
            map.put(key, value);
        }

/*        Iterator<Integer> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            key = iterator.next();
            value = map.get(key);
            System.out.println(key + " " + value);
        }*/
        for (Integer index : map.keySet()) {
            value = map.get(index);
            System.out.println(index + " " + value);
        }
    }
}

// 第一次做
// 2021.12.02 晚, 看不懂以前写的代码了
// import java.util.Scanner;
// import java.lang.String;
class HJ8_1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int row = in.nextInt();
        int[] indexArr = new int[row];
        int[] valueArr = new int[row];
        for (int i = 0; i < row; i++) {
//            while (in.hasNextInt()) {
            indexArr[i] = in.nextInt();
            valueArr[i] = in.nextInt();
//            }
        }
        //只有一行数据，直接输出返回
        if (row == 1) {
            System.out.println(indexArr[0] + " "+ valueArr[0]);
            return;
        }
        int indexTemp = 0;
        int valueTemp = 0;
        for (int j = 0; j < row; j++) {
            while (valueArr[j] == 0 && j != row - 1) {
                j++;
            }
            //此三行代码是保证valueArr[j] == 0 && j == row - 1时，不执行后续的for循环以及输出语句
            if (valueArr[j] == 0 && j == row - 1) {
                break;
            }
            for (int k = j + 1; k < row; k++) {
                if (indexArr[j] == indexArr[k]) {
                    valueArr[j] += valueArr[k];
                    valueArr[k] = 0;
                } else if (indexArr[j] >= indexArr[k] && valueArr[k] != 0) {
                    //交换index
                    indexTemp = indexArr[j];
                    indexArr[j] = indexArr[k];
                    indexArr[k] = indexTemp;
                    //交换value
                    valueTemp = valueArr[j];
                    valueArr[j] = valueArr[k];
                    valueArr[k] = valueTemp;
                }
            }
            System.out.println(indexArr[j] + " " + valueArr[j]);
        }
    }
}