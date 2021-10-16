package com.ahua.algorithm.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author huajun
 * @create 2021-10-12 20:20
 */

public class DijkstraAlgorithm2 {

    public static void main(String[] args) {
        // 顶点数组
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        // 表示不可以连接
        final int N = 65535;
        matrix[0] = new int[]{N, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, N, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, N, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, N, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, N, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, N, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, N};
        // 创建 Graph对象
        Graph2 graph = new Graph2(vertex, matrix);
        // 输出图的邻接矩阵
        graph.showGraph();
        System.out.println("==========================");
        // 迪杰斯特拉算法
        graph.dsj(2);//C
        graph.showDijkstra();

    }
}

class Graph2 {
    private final char[] vertex; // 顶点数组
    private final int[][] matrix; // 邻接矩阵
    private VisitedVertex2 vv; // 已经访问的顶点的集合

    // 构造器
    public Graph2(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
    }

    // 显示结果
    public void showDijkstra() {
        vv.show();
    }

    // 显示图
    public void showGraph() {
        for (int[] link : matrix) {
            System.out.println(Arrays.toString(link));
        }
    }

    //迪杰斯特拉算法实现

    /**
     * @param begin 表示出发顶点对应的下标
     */
    public void dsj(int begin) {
        vv = new VisitedVertex2(vertex.length);
        // 初始化 访问出发顶点
        // 设置出发顶点到出发顶点的距离为 0
        // [65535, 65535, 0, 65535, 65535, 65535, 65535]
        vv.dis[begin] = 0;
        System.out.println("dis 数组初始为 " + Arrays.toString(vv.dis));
        // 更新 begin 顶点到周围顶点的距离和前驱顶点
        update(begin);
        int index;
        // 一次循环访问一个顶点,更新一次
        for (int j = 1; j < vertex.length; j++) {
            // 选择并返回新的访问顶点
            index = vv.findNotVisitedVertex();
            // 更新 index 顶点到周围顶点的距离和前驱顶点
            update(index);
        }
    }

    // 更新 index 下标顶点到周围顶点的距离和周围顶点的前驱顶点
    private void update(int index) {
        int distance;
        // 访问过的顶点 index 从中移除
        vv.notVisitedHashSet.remove(index);
        // 遍历邻接矩阵的 matrix[index] 行
        for (int j = 0; j < matrix[index].length; j++) {
            // 与 index 顶点相连的没有被访问过的顶点 j
            if (matrix[index][j] != 65535 && vv.notVisitedHashSet.contains(j)) {
                System.out.println("准备 update " + j + "-" + vertex[j]);
                // distance 含义是: 出发顶点到 index 顶点的距离 + 从 index 顶点到 j 顶点的距离的和
                distance = vv.dis[index] + matrix[index][j];
                // distance 小于出发顶点到 j 顶点的距离,则更新;大于则不更新,遍历下一个与 index 顶点相连的没有被访问过的顶点 j
                if (distance < vv.dis[j]) {
                    // 更新 j 顶点的前驱为 index 顶点
                    vv.preVisited[j] = index;
                    // 更新出发顶点到 j 顶点的距离
                    vv.dis[j] = distance;
                    System.out.println("更新 update " + j + "-" + vertex[j] + " 的 dis 为 " + Arrays.toString(vv.dis));
                } else {
                    System.out.println("不需要 update " + j + "-" + vertex[j] + " 的 dis");
                }
            }
        }
    }
}

// 已访问顶点集合
class VisitedVertex2 {
    // 记录各个顶点是否访问过,会动态更新 ==》 改用 notVisitedHashSet 记录未被访问过的顶点
    // public boolean[] isVisited;
    // 记录未被访问过的顶点
    public HashSet<Integer> notVisitedHashSet;
    // 每个下标对应的值为前一个顶点下标, 会动态更新
    public int[] preVisited;
    // 记录出发顶点到其他所有顶点的距离,比如G为出发顶点，就会记录G到其它顶点的距离，会动态更新，求的最短距离就会存放到dis
    public int[] dis;

    /**
     * @param vertexNum: 表示顶点的个数
     */
    public VisitedVertex2(int vertexNum) {
        this.notVisitedHashSet = new HashSet<>(vertexNum);
        this.preVisited = new int[vertexNum];
        this.dis = new int[vertexNum];
        // 初始时所有顶点未被访问过,放入其中
        for (int i = 0; i < vertexNum; i++) {
            this.notVisitedHashSet.add(i);
        }
        // 初始化 dis 数组
        Arrays.fill(dis, 65535);
    }

    /**
     * 继续选择并返回新的访问顶点， 比如这里的G 完后，就是 A点作为新的访问顶点(注意不是出发顶点)
     *
     * @return index
     */
    public int findNotVisitedVertex() {
        int min = 65535, index = 0;
        // 找到在未被访问过的顶点中,从出发顶点到该顶点的距离最小的那个顶点
        for (Integer i : notVisitedHashSet) {
            System.out.println(i + "---11111");
            if (dis[i] < min) {
                System.out.println(i + "---22222");
                min = dis[i];
                index = i;
            }
        }
        return index;
    }

    // 显示最后的结果
    // 即将三个数组的情况输出
    public void show() {

        System.out.println("==========================");

        System.out.println("preVisited");
        //输出 preVisited
        for (int i : preVisited) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("dis");
        //输出 dis
        for (int i : dis) {
            System.out.print(i + " ");
        }
        System.out.println();
        // 使最后的最短距离更为直观
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int count = 0;
        for (int i : dis) {
            if (i != 65535) {
                System.out.print(vertex[count] + "(" + i + ") ");
            } else {
                System.out.println("N ");
            }
            count++;
        }
        System.out.println();
    }
}
