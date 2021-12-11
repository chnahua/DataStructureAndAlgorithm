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
// 效率低, 时间不及 DFS
class P210_Solution1 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
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
class P210_Solution2 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 邻接表存储有向图
        P210_CourseNode[] adjList = new P210_CourseNode[numCourses];
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
            adjList[from] = new P210_CourseNode(to, adjList[from]);
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
        P210_CourseNode next;
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
}

// 拓扑排序(DFS 递归实现)
// DFS 第一种实现, 官方实现, 使用 List<List<Integer>> 构建邻接表
class P210_Solution3 {
    // 使用 List<List<Integer>> adjList = new ArrayList<>(); 构造邻接表
    List<List<Integer>> adjList;
    // 记录每个节点的状态: 0=未搜索，1=搜索中，2=已完成
    int[] visited;
    // 标记是否是有向无环图
    boolean valid = true;
    // 用数组来模拟栈, 下标 n-1 为栈底, 0 为栈顶, 遍历结束后栈中元素即为拓扑排序
    int[] ans;
    // 栈下标
    int index;

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        adjList = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }
        visited = new int[numCourses];
        ans = new int[numCourses];
        index = numCourses - 1;
        // 构建邻接表
        for (int[] edge : prerequisites) {
            adjList.get(edge[1]).add(edge[0]);
        }
        // 从未遍历过的节点开始遍历
        for (int i = 0; i < numCourses; i++) {
            // 未搜索过
            if (visited[i] == 0) {
                dfs(i);
            }
            if (!valid) {
                return new int[0];
            }
        }
        return ans;
    }

    private void dfs(int i) {
        // 标记为 1, 表示正在搜索中
        visited[i] = 1;
        for (int next : adjList.get(i)) {
            // 相邻节点未搜索过, 搜索这个节点
            if (visited[next] == 0) {
                dfs(next);
                // 剪枝判断, 若已知存在环了, 可提前结束循环
                if (!valid) {
                    return;
                }
            } else if (visited[next] == 1) {
                // 表示曾经搜索过 next 节点, 并从 next 节点一直搜索到 i, 现在要从 i 再去找 next, 说明存在了环
                valid = false;
                return;
            }/* else {
                // visited[next] == 2 时
                // 不做操作
            }*/
        }
        // 标记为 2, 表示已经搜索完毕这个节点及其相邻节点
        visited[i] = 2;
        // 将节点入栈
        ans[index] = i;
        // 下标索引减 1
        index--;
    }
}

// 拓扑排序(DFS 递归实现)
// DFS 第二种实现, 创建 CourseNode 节点, 使用 CourseNode[] 构建邻接表
// 效率比前三种都高, 耗时更少, 但是消耗的内存更多
class P210_Solution {
    // 使用 CourseNode[] 构建邻接表
    P210_CourseNode[] adjList;
    // 记录每个节点的状态: 0=未搜索，1=搜索中，2=已完成
    int[] visited;
    // 标记是否是有向无环图
    boolean valid = true;
    // 用数组来模拟栈, 下标 n-1 为栈底, 0 为栈顶, 遍历结束后栈中元素即为拓扑排序
    int[] ans;
    // 栈下标
    int index;

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 邻接表存储有向图
        adjList = new P210_CourseNode[numCourses];
        visited = new int[numCourses];
        ans = new int[numCourses];
        index = numCourses - 1;

        // 构建邻接表
        int from, to;
        for (int[] edge : prerequisites) {
            // 有向边的起始节点
            from = edge[1];
            // 有向边的终止节点
            to = edge[0];
            // 将该边添加进邻接表 adjList
            // 采用头插法构造图, 与注释的三行等价
            adjList[from] = new P210_CourseNode(to, adjList[from]);
            /*
            CourseNode toNode = new CourseNode(to);
            toNode.next = adjList[from];
            adjList[from] = toNode;
             */
        }
        // 从未遍历过的节点开始遍历
        for (int i = 0; i < numCourses; i++) {
            // 未搜索过
            if (visited[i] == 0) {
                dfs(i);
            }
            if (!valid) {
                return new int[0];
            }
        }
        return ans;
    }

    private void dfs(int i) {
        // 标记为 1, 表示正在搜索中
        visited[i] = 1;

        P210_CourseNode next = adjList[i];
        while (next != null) {
            // 相邻节点未搜索过, 搜索这个节点
            if (visited[next.value] == 0) {
                dfs(next.value);
                // 剪枝判断, 若已知存在环了, 可提前结束循环
                if (!valid) {
                    return;
                }
            } else if (visited[next.value] == 1) {
                // 表示曾经搜索过 next 节点, 并从 next 节点一直搜索到 i, 现在要从 i 再去找 next, 说明存在了环
                valid = false;
                return;
            }/* else {
                // visited[next] == 2 时
                // 不做操作
            }*/
            next = next.next;
        }

        // 标记为 2, 表示已经搜索完毕这个节点及其相邻节点
        visited[i] = 2;
        // 将节点入栈
        ans[index] = i;
        // 下标索引减 1
        index--;
    }
}

// CourseNode
class P210_CourseNode {
    // value 为课程编号
    int value;
    P210_CourseNode next;

    public P210_CourseNode(int value) {
        this.value = value;
    }

    public P210_CourseNode(int value, P210_CourseNode next) {
        this.value = value;
        this.next = next;
    }
}