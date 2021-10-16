package com.ahua.algorithm.dijkstra;

import java.util.Arrays;

/**
 * @author huajun
 * @create 2021-10-12 15:27
 */

public class DijkstraAlgorithm {

    public static void main(String[] args) {
        // 顶点数组
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;// 表示不可以连接
        matrix[0] = new int[]{N, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, N, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, N, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, N, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, N, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, N, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, N};
        // 创建 Graph对象
        Graph graph = new Graph(vertex, matrix);
        // 输出图的邻接矩阵
        graph.showGraph();
        System.out.println("==========================");
        // 迪杰斯特拉算法
        graph.dsj(2);//C
        graph.showDijkstra();

    }
}

class Graph {
    private final char[] vertex; // 顶点数组
    private final int[][] matrix; // 邻接矩阵
    private VisitedVertex vv; // 已经访问的顶点的集合

    // 构造器
    public Graph(char[] vertex, int[][] matrix) {
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
     * @param index 表示出发顶点对应的下标
     */
    public void dsj(int index) {
        vv = new VisitedVertex(vertex.length, index);
        // 更新index顶点到周围顶点的距离和前驱顶点
        update(index);
        for (int j = 1; j < vertex.length; j++) {
            // 选择并返回新的访问顶点
            index = vv.updateArr();
            // 更新index顶点到周围顶点的距离和前驱顶点
            update(index);
        }
    }

    // 更新index下标顶点到周围顶点的距离和周围顶点的前驱顶点
    private void update(int index) {
        int distance;
        // 根据遍历邻接矩阵的 matrix[index] 行
        for (int j = 0; j < matrix[index].length; j++) {
            // 与 index 顶点相连的没有被访问过的顶点 j
            if (matrix[index][j] != 65535 && !vv.isVisited[j]) {
                System.out.println("准备 update " + j + "-" + vertex[j]);
                // distance 含义是: 出发顶点到 index 顶点的距离 + 从 index 顶点到 j 顶点的距离的和
                distance = vv.dis[index] + matrix[index][j];
                // distance 小于出发顶点到 j 顶点的距离,则更新
                if (distance < vv.dis[j]) {
                    // 更新 j 顶点的前驱为 index 顶点
                    // vv.updatePre(j, index);
                    vv.preVisited[j] = index;
                    // 更新出发顶点到 j 顶点的距离
                    // vv.updateDis(j, distance);
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
class VisitedVertex {
    // 记录各个顶点是否访问过 1表示访问过,0未访问,会动态更新
    public boolean[] isVisited;
    // 每个下标对应的值为前一个顶点下标, 会动态更新
    public int[] preVisited;
    // 记录出发顶点到其他所有顶点的距离,比如G为出发顶点，就会记录G到其它顶点的距离，会动态更新，求的最短距离就会存放到dis
    public int[] dis;

    /**
     * @param vertexNum:表示顶点的个数
     * @param index: 出发顶点对应的下标, 比如G顶点，下标就是6
     */
    public VisitedVertex(int vertexNum, int index) {
        this.isVisited = new boolean[vertexNum];
        this.preVisited = new int[vertexNum];
        this.dis = new int[vertexNum];
        // 初始化 dis 数组
        Arrays.fill(dis, 65535);
        // 设置出发顶点到出发顶点的距离为 0
        // [65535, 65535, 0, 65535, 65535, 65535, 65535]
        this.dis[index] = 0;
        System.out.println("dis 数组初始为 " + Arrays.toString(dis));
        // 设置出发顶点被访问过
        this.isVisited[index] = true;
    }

//    /**
//     * 功能: 判断index顶点是否被访问过
//     *
//     * @param index
//     * @return 如果访问过，就返回true, 否则访问false
//     */
//    public boolean isIndexVisited(int index) {
//        return isVisited[index];
//    }

//    /**
//     * 功能: 更新出发顶点到index顶点的距离
//     *
//     * @param index
//     * @param distance
//     */
//    public void updateDis(int index, int distance) {
//        dis[index] = distance;
//    }
//
//    /**
//     * 功能: 更新 index 这个顶点的前驱顶点为 pre 顶点
//     *
//     * @param index
//     * @param pre
//     */
//    public void updatePre(int index, int pre) {
//        preVisited[index] = pre;
//    }

    /**
     * 继续选择并返回新的访问顶点， 比如这里的G 完后，就是 A点作为新的访问顶点(注意不是出发顶点)
     *
     * @return index
     */
    public int updateArr() {
        int min = 65535, index = 0;
        for (int i = 0; i < isVisited.length; i++) {
            if (!isVisited[i] && dis[i] < min) {
                min = dis[i];
                index = i;
            }
        }
        //更新 index 顶点被访问过
        isVisited[index] = true;
        return index;
    }

//    /**
//     * 功能:返回出发顶点到index顶点的距离
//     *
//     * @param index
//     */
//    public int getDis(int index) {
//        return dis[index];
//    }


    //显示最后的结果
    //即将三个数组的情况输出
    public void show() {

        System.out.println("==========================");
        //输出already_arr
        for (Boolean i : isVisited) {
            System.out.print(i + " ");
        }
        System.out.println();
        //输出pre_visited
        for (int i : preVisited) {
            System.out.print(i + " ");
        }
        System.out.println();
        //输出dis
        for (int i : dis) {
            System.out.print(i + " ");
        }
        System.out.println();
        //为了好看最后的最短距离，我们处理
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
