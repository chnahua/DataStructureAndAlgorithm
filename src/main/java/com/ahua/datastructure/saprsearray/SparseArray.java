package com.ahua.datastructure.saprsearray;

/**
 * @author huajun
 * @create 2021-04-14 19:00
 */

public class SparseArray {

    public static void main(String[] args) {

        // 创建一个原始的二维数组 11 * 11
        // 0: 表示没有棋子， 1 表示黑子 2 表示蓝子
        int[][] chessArr1 = new int[8][8];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[3][4] = 3;

        // 输出原始的二维数组
        System.out.println("原始的二维数组:");
        for (int[] row : chessArr1) {
            for (int data : row) {
                System.out.print(data + "\t");
            }
            System.out.println();
        }

        // 将二维数组 转 稀疏数组
        // 1.先遍历二维数组 得到非0数据的个数sum
        int sum = 0;
//        for (int i = 0; i < chessArr1.length; i++) {
//            for (int j = 0; j < chessArr1[i].length; j++) {
//                if (chessArr1[i][j] != 0) {
//                    sum++;
//                }
//            }
//        }
        for (int[] row : chessArr1) {
            for (int value : row) {
                if (value != 0) {
                    sum++;
                }
            }
        }

        // 2. 创建对应的稀疏数组
        int[][] sparseArr = new int[sum + 1][3];
        // 给稀疏数组赋初值 首行表示原数组共由几行、几列、有多少个值
        sparseArr[0][0] = chessArr1.length;
        sparseArr[0][1] = chessArr1[0].length;
        sparseArr[0][2] = sum;

        // 遍历二维数组，将非0的值存放到 sparseArr 中
        // count 用于记录是第几个非0数据
        int count = 0;
        for (int i = 0; i < chessArr1.length; i++) {
            for (int j = 0; j < chessArr1[i].length; j++) {
                if (chessArr1[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr1[i][j];
                }
            }
        }

        // 输出稀疏数组的形式
        System.out.println();
        System.out.println("得到稀疏数组为:");
        System.out.println("行" + "\t" + "列" + "\t" + "值");
//        for (int i = 0; i < sparseArr.length; i++) {
//            System.out.printf("%d\t%d\t%d\n", sparseArr[i][0], sparseArr[i][1], sparseArr[i][2]);
//        }
        for (int[] ints : sparseArr) {
            System.out.printf("%d\t%d\t%d\n", ints[0], ints[1], ints[2]);
        }

        // 将稀疏数组 --> 恢复成 原始的二维数组

        /*
         * 1.先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的 chessArr2 = int [11][11]
         * 2.再读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可
         */

        // 1.先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组

        int[][] chessArr2 = new int[sparseArr[0][0]][sparseArr[0][1]];

        // 2.再读取稀疏数组后几行的数据(从第二行开始)，并赋给 原始的二维数组 即可

        for (int i = 1; i < sparseArr.length; i++) {
            chessArr2[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }

        // 输出恢复后的二维数组
        System.out.println();
        System.out.println("恢复后的二维数组:");

        for (int[] row : chessArr2) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }
}
