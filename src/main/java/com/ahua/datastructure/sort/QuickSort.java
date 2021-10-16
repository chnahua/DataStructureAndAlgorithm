package com.ahua.datastructure.sort;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huajun
 * @create 2021-04-17 16:40
 */
// 2s
public class QuickSort {

    public static void main(String[] args) {
        //int[] arr = {-9,78,0,1,23,-567,1,70,1,-1,900,1,4561};

        //测试快排的执行速度
        //创建要给80000个的随机的数组
        int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

        System.out.println("排序前");
        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        quickSort(arr, 0, arr.length-1);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序前的时间是=" + date2Str);
        //System.out.println("arr=" + Arrays.toString(arr));
    }

    public static void quickSort(int[] arr,int left, int right) {
        int l = left; //左下标
        int r = right; //右下标
        // pivot 中轴值
        int pivot = arr[(left + right) / 2];
        int temp = 0; //临时变量，作为交换时使用
        // while循环的目的是让比 pivot 值小放到左边
        // 比pivot 值大放到右边
        while(l < r) {
            // 在 pivot 值的左边一直找,找到大于等于 pivot 值,才退出
            while(arr[l] < pivot) {
                l += 1;
            }
            // 在 pivot 值的右边一直找,找到小于等于 pivot 值,才退出
            while(arr[r] > pivot) {
                r -= 1;
            }
            // 如果l >= r说明在 pivot 值的
            // 左边全部是小于等于 pivot 值，右边全部是大于等于 pivot 值
            if(l >= r) {
                break;
            }

            // 此时一定
            // 交换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            //如果交换完后，发现这个arr[l] == pivot值 相等 r--， 前移
            if(arr[l] == pivot) {
                r -= 1;
            }
            //如果交换完后，发现这个arr[r] == pivot值 相等 l++， 后移
            if(arr[r] == pivot) {
                l += 1;
            }
        }

        // 如果 l == r, 必须l++, r--, 否则为出现栈溢出
        if(l == r) {
            l += 1;
            r -= 1;
        }
        //退出循环的标志是l >= r，故以下传入参数是left,r和l,right,而不是left,l和r,right
        //向左递归
        if(left < r) {
            quickSort(arr, left, r);
        }
        //向右递归
        if(right > l) {
            quickSort(arr, l, right);
        }
    }
}
