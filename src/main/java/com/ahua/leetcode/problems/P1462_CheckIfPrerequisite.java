package com.ahua.leetcode.problems;

import java.util.*;

/**
 * @author huajun
 * @create 2021-12-14 18:00
 */

/**
 * 1462. 课程表 IV
 * 你总共需要上 n 门课，课程编号依次为 0 到 n-1 。
 * <p>
 * 有的课会有直接的先修课程，比如如果想上课程 0 ，你必须先上课程 1 ，那么会以 [1,0] 数对的形式给出先修课程数对。
 * <p>
 * 给你课程总数 n 和一个直接先修课程数对列表 prerequisite 和一个查询对列表 queries。
 * <p>
 * 对于每个查询对 queries[i]，请判断 queries[i][0] 是否是 queries[i][1] 的先修课程。
 * <p>
 * 请返回一个布尔值列表，列表中每个元素依次分别对应 queries 每个查询对的判断结果。
 * <p>
 * 注意：如果课程 a 是课程 b 的先修课程且课程 b 是课程 c 的先修课程，那么课程 a 也是课程 c 的先修课程。
 * <p>
 * 2 <= n <= 100
 * 0 <= prerequisite.length <= (n * (n - 1) / 2)
 * 0 <= prerequisite[i][0], prerequisite[i][1] < n
 * prerequisite[i][0] != prerequisite[i][1]
 * 先修课程图中没有环。
 * 先修课程图中没有重复的边。
 * 1 <= queries.length <= 10^4
 * queries[i][0] != queries[i][1]
 */
public class P1462_CheckIfPrerequisite {
    public static void main(String[] args) {
        int[][] prerequisites = new int[][]{{1, 0}};
        int[][] queries = new int[][]{{0, 1}, {1, 0}};

        int[][] prerequisites1 = new int[][]{};
        int[][] queries1 = new int[][]{{1, 0}, {0, 1}};

        int[][] prerequisites2 = new int[][]{{1, 2}, {1, 0}, {2, 0}};
        int[][] queries2 = new int[][]{{1, 0}, {1, 2}};

        int[][] prerequisites3 = new int[][]{{1, 0}, {2, 0}};
        int[][] queries3 = new int[][]{{0, 1}, {2, 0}};

        int[][] prerequisites4 = new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 4}};
        int[][] queries4 = new int[][]{{0, 4}, {4, 0}, {1, 3}, {3, 0}};

        P1462_Solution solution = new P1462_Solution();
        // [false, true] 课程 0 不是课程 1 的先修课程, 但课程 1 是课程 0 的先修课程
        System.out.println(solution.checkIfPrerequisite(2, prerequisites, queries));
        // [false, false] 没有先修课程对, 所以每门课程之间是独立的
        System.out.println(solution.checkIfPrerequisite(2, prerequisites1, queries1));
        // [true, true]
        System.out.println(solution.checkIfPrerequisite(3, prerequisites2, queries2));
        // [false, true]
        System.out.println(solution.checkIfPrerequisite(3, prerequisites3, queries3));
        // [true, false, true, false]
        System.out.println(solution.checkIfPrerequisite(5, prerequisites4, queries4));
    }
}

// 深度优先搜索(递归实现)
// 过程代码, 可删
class P1462_Solution1 {
    // 答案链表
    List<Boolean> ans;
    // 邻接表构造有向图
    P1462_CourseNode[] adjList;
    // 记忆化是否可达, 1 代表可达, -1 代表不可达, 初始默认值为 0
    int[][] isReachable;
    // 记录之前的路径, 遍历中记忆化所有的到达情况
    Deque<Integer> path;

    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        // 答案链表
        ans = new ArrayList<>(numCourses);
        // 邻接表构造有向图
        adjList = new P1462_CourseNode[numCourses];

        // 记忆化是否可达
        isReachable = new int[numCourses][numCourses];
        path = new LinkedList<>();

        // 构建邻接表
        int from, to;
        for (int[] edge : prerequisites) {
            from = edge[0];
            to = edge[1];

            // 记忆可达到
            isReachable[from][to] = 1;
            isReachable[to][from] = -1;

            // 将该边添加进邻接表 adjList
            // 采用头插法构造图, 与注释的三行等价
            adjList[from] = new P1462_CourseNode(to, adjList[from]);
            /*
            P1462_CourseNode toNode = new P1462_CourseNode(to);
            toNode.next = adjList[from];
            adjList[from] = toNode;
             */
        }

        // DFS
        for (int[] query : queries) {
            from = query[0];
            to = query[1];

            if (isReachable[from][to] == 1) {
                ans.add(true);
                continue;
            }

            if (dfs(from, to)) {
                isReachable[from][to] = 1;
                isReachable[to][from] = -1;
            }

            ans.add(isReachable[from][to] == 1);
        }

        return ans;
    }

    private boolean dfs(int from, int to) {
        if (from == to) {
            return true;
        }
        if (isReachable[from][to] == 1) {
            return true;
        }
        if (isReachable[from][to] == -1) {
            return false;
        }

        P1462_CourseNode next = adjList[from];
        path.offerLast(from);

        while (next != null) {
//            // 不应该在这里判断, 应该在循环外进行边界条件的判断
//            if (isReachable[next.value][to] == 1) {
//                path.removeLast();
//                return true;
//            }
//            // 添加下面这一段是错误的
//            if (isReachable[next.value][to] == -1) {
//                path.removeLast();
//                return false;
//            }

            if (dfs(next.value, to)) {
                isReachable[next.value][to] = 1;
                isReachable[to][next.value] = -1;
                path.removeLast();
                return true;
            }
            // 本打算使用此 path 来记录遍历过的节点之间互相是否可达, 可是如此的话就会增加很多循环遍历 path
            // 反而导致了效率更加低下
            // 但是如果将 path 定义出来, 不执行以下这三行, 其它的保留, 反而比不定义 path 的空间消耗更低? 这让我感到很不可思议
            for (Integer pre : path) {
                isReachable[pre][next.value] = 1;
            }
            next = next.next;
        }
        path.removeLast();
        isReachable[from][to] = -1;
        return false;
    }
}

// 深度优先搜索(递归实现)
class P1462_Solution {
    // 答案链表
    List<Boolean> ans;
    // 邻接表构造有向图
    P1462_CourseNode[] adjList;
    // 记忆化是否可达, 1 代表可达, -1 代表不可达, 初始默认值为 0
    // 定义为 byte、short、int 貌似差别也并不大
    // 最开始定义为 boolean类型, 但是参考了他人的代码, 发现超时不能通过全部案例
    int[][] isReachable;

    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        // 答案链表
        ans = new ArrayList<>(numCourses);
        // 邻接表构造有向图
        adjList = new P1462_CourseNode[numCourses];

        // 记忆化是否可达
        isReachable = new int[numCourses][numCourses];
        // 构建邻接表
        int from, to;
        for (int[] edge : prerequisites) {
            from = edge[0];
            to = edge[1];

            // 记忆可达到
            isReachable[from][to] = 1;
            isReachable[to][from] = -1;

            // 将该边添加进邻接表 adjList
            // 采用头插法构造图, 与注释的三行等价
            adjList[from] = new P1462_CourseNode(to, adjList[from]);
            /*
            P1462_CourseNode toNode = new P1462_CourseNode(to);
            toNode.next = adjList[from];
            adjList[from] = toNode;
             */
        }

        // DFS
        for (int[] query : queries) {
            from = query[0];
            to = query[1];

            if (dfs(from, to)) {
                isReachable[from][to] = 1;
                isReachable[to][from] = -1;
            }
            ans.add(isReachable[from][to] == 1);
        }

        return ans;
    }

    private boolean dfs(int from, int to) {
        if (from == to) {
            return true;
        }
        if (isReachable[from][to] == 1) {
            return true;
        }
        if (isReachable[from][to] == -1) {
            return false;
        }
        // 有向边的起点 from -> 终点 next
        P1462_CourseNode next = adjList[from];

        while (next != null) {
            if (dfs(next.value, to)) {
                isReachable[next.value][to] = 1;
                isReachable[to][next.value] = -1;
                return true;
            }
            next = next.next;
        }

        isReachable[from][to] = -1;
        return false;
    }
}

class P1462_CourseNode {
    // value 为课程编号
    int value;
    P1462_CourseNode next;

    public P1462_CourseNode(int value) {
        this.value = value;
    }

    public P1462_CourseNode(int value, P1462_CourseNode next) {
        this.value = value;
        this.next = next;
    }
}

// 弗洛伊德(Floyd)算法
// 效率不及 DFS
class P1462_Solution2 {
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        // 记忆化是否可达
        boolean[][] isReachable = new boolean[numCourses][numCourses];

        for (int[] edge : prerequisites) {
            isReachable[edge[0]][edge[1]] = true;
        }
        // 中转点
        for (int k = 0; k < numCourses; k++) {
            // 起点
            for (int i = 0; i < numCourses; i++) {
                // 终点
                for (int j = 0; j < numCourses; j++) {
//                    // 如果可达不执行任何操作, 如果不可达则判断通过中转点 k 是否可达
//                    if (!isReachable[i][j]) {
//                        isReachable[i][j] = isReachable[i][k] && isReachable[k][j];
//                    }
                    // 与上等价
                    isReachable[i][j] = isReachable[i][j] || (isReachable[i][k] && isReachable[k][j]);
                }
            }
        }
        // 答案链表
        List<Boolean> ans = new ArrayList<>();
        for (int[] query : queries) {
            ans.add(isReachable[query[0]][query[1]]);
        }
        return ans;
    }
}