package com.ahua.nowcoder.huawei;

import java.util.Scanner;

/**
 * @author huajun
 * @create 2021-12-25 22:49
 */

public class HJ96 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.next();
            StringBuilder sb = new StringBuilder();
            boolean bl = false;
            char c = str.charAt(0);
            if (c >= '0' && c <= '9' ) {
                sb.append('*').append(c);
                bl = true;
            } else {
                sb.append(c);
            }
            for (int i = 1; i < str.length(); i++) {
                c = str.charAt(i);
                if (c >= '0' && c <= '9') {
                    if (bl) {
                        sb.append(c);
                    } else {
                        sb.append('*').append(c);
                        bl = true;
                    }
                } else {
                    if (bl) {
                        sb.append('*').append(c);
                        bl = false;
                    } else {
                        sb.append(c);
                    }
                }
            }
            if (bl) {
                sb.append('*');
            }
            System.out.println(sb);
        }
        sc.close();
    }
}