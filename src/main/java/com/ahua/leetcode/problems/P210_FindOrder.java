package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-09 20:54
 */

import java.util.*;

/**
 * 210. 课程表 II
 * <p>
 * 现在你总共有 numCourses 门课需要选，记为 0 到 numCourses - 1。给你一个数组 prerequisites ，其中 prerequisites[i] = [ai, bi] ，表示在选修课程 ai 前 必须 先选修 bi 。
 * <p>
 * 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示：[0,1] 。
 * 返回你为了学完所有课程所安排的学习顺序。可能会有多个正确的顺序，你只要返回 任意一种 就可以了。如果不可能完成所有课程，返回 一个空数组 。
 * <p>
 * 1 <= numCourses <= 2000
 * 0 <= prerequisites.length <= numCourses * (numCourses - 1)
 * prerequisites[i].length == 2
 * 0 <= ai, bi < numCourses
 * ai != bi
 * 所有[ai, bi] 互不相同
 */
public class P210_FindOrder {
    public static void main(String[] args) {
        int[][] prerequisites = new int[][]{{1, 0}};
        int[][] prerequisites1 = new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        int[][] prerequisites2 = new int[][]{};
        P210_Solution solution = new P210_Solution();
        // [0, 1]
        System.out.println(Arrays.toString(solution.findOrder(2, prerequisites)));
        // [0, 2, 1, 3] 或者 [0, 1, 2, 3]
        System.out.println(Arrays.toString(solution.findOrder(4, prerequisites1)));
        // [0]
        System.out.println(Arrays.toString(solution.findOrder(1, prerequisites2)));
    }
}

// 拓扑排序(BFS 队列实现)
// BFS 第一种实现, 官方实现, 使用 List<List<Integer>> 构建邻接表
// 效率低, 时间空间均不及 DFS
class P210_Solution1 {
    public int[] findOrder1(int numCourses, int[][] prerequisites) {
        // 邻接表存储有向图
        List<List<Integer>> adjList = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }
        // 存储每个节点的入度
        int[] inDegree = new int[numCourses];
        // 构建邻接表, 并得到每个节点的入度
        int from, to;
        for (int[] edge : prerequisites) {
            // 有向边的起始节点
            from = edge[1];
            // 有向边的终止节点
            to = edge[0];
            // 终止节点的入度加 1
            inDegree[to]++;
            // 将该边添加进邻接表 adjList
            adjList.get(from).add(to);
        }

        // BFS
        // 入度为 0 的节点入队
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // 一个入度为 0 的节点都没有, 说明有一个整个图是一个大环, 可直接返回
        // 此判断可删除
        if (queue.isEmpty()) {
            return new int[0];
        }

        // 答案, 每出队一个入度为 0 的节点, 等于确定一门先修课程
        int[] ans = new int[numCourses];
        // 添加的位置, 同时也代表得到答案中的节点个数 index + 1
        int index = 0;
        int cur;
        while (!queue.isEmpty()) {
            // 从队首取出一个入度为 0 的节点
            cur = queue.remove();
            // 将 cur 添加进答案中
            ans[index] = cur;
            // 下一个添加位置
            index++;
            for (Integer next : adjList.get(cur)) {
                // 以 cur 为起始点的有向边的终点的入度减 1
                inDegree[next]--;
                // 入度减小到为 0 时, 说明 next 的所有前修课程均已经学习完毕, 将其加入队列, 后续就可以学习这门课程了
                if (inDegree[next] == 0) {
                    queue.add(next);
                }
            }
        }
        // 循环结束时, 如果 index 等于 numCourses, 说明这个学习顺序能够完成所有课程的学习
        return index == numCourses ? ans : new int[0];
    }
}

// 拓扑排序(BFS 队列实现)
// BFS 第二种实现, 创建 CourseNode 节点, 使用 CourseNode[] 构建邻接表
// 效率比第一种高, 耗时更少, 空间也比第一种更少
class P210_Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 邻接表存储有向图
        CourseNode[] adjList = new CourseNode[numCourses];
        // 存储每个节点的入度
        int[] inDegree = new int[numCourses];
        // 构建邻接表, 并得到每个节点的入度
        int from, to;
        for (int[] edge : prerequisites) {
            // 有向边的起始节点
            from = edge[1];
            // 有向边的终止节点
            to = edge[0];
            // 终止节点的入度加 1
            inDegree[to]++;
            // 将该边添加进邻接表 adjList
            // 采用头插法构造图, 与注释的三行等价
            adjList[from] = new CourseNode(to, adjList[from]);
            /*
            CourseNode toNode = new CourseNode(to);
            toNode.next = adjList[from];
            adjList[from] = toNode;
             */
        }

        // BFS
        // 入度为 0 的节点入队
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // 一个入度为 0 的节点都没有, 说明有一个整个图是一个大环, 可直接返回
        // 此判断可删除
        if (queue.isEmpty()) {
            return new int[0];
        }

        // 答案, 每出队一个入度为 0 的节点, 等于确定一门先修课程
        int[] ans = new int[numCourses];
        // 添加的位置, 同时也代表得到答案中的节点个数 index + 1
        int index = 0;
        int cur;
        CourseNode next;
        while (!queue.isEmpty()) {
            // 从队首取出一个入度为 0 的节点
            cur = queue.remove();
            // 将 cur 添加进答案中
            ans[index] = cur;
            // 下一个添加位置
            index++;
            next = adjList[cur];
            while (next != null) {
                // 以 cur 为起始点的有向边的终点的入度减 1
                inDegree[next.value]--;
                // 入度减小到为 0 时, 说明 next 的所有前修课程均已经学习完毕, 将其加入队列, 后续就可以学习这门课程了
                if (inDegree[next.value] == 0) {
                    queue.add(next.value);
                }
                next = next.next;
            }
        }
        // 循环结束时, 如果 index 等于 numCourses, 说明这个学习顺序能够完成所有课程的学习
        return index == numCourses ? ans : new int[0];
    }

    // 不要使用这种方式创建 CourseNode
    static class CourseNode {
        // value 为课程编号
        int value;
        CourseNode next;

        public CourseNode(int value) {
            this.value = value;
        }

        public CourseNode(int value, CourseNode next) {
            this.value = value;
            this.next = next;
        }
    }
}

// 使用这种方式创建 CourseNode
//class CourseNode {
//    // value 为课程编号
//    int value;
//    CourseNode next;
//
//    public CourseNode(int value) {
//        this.value = value;
//    }
//
//    public CourseNode(int value, CourseNode next) {
//        this.value = value;
//        this.next = next;
//    }
//}