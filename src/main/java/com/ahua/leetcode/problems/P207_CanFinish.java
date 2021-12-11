package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-06 19:34
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
 * <p>
 * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，
 * 其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程 bi 。
 * <p>
 * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
 * <p>
 * 1 <= numCourses <= 105
 * 0 <= prerequisites.length <= 5000
 * prerequisites[i].length == 2
 * 0 <= ai, bi < numCourses
 * prerequisites[i] 中的所有课程对 互不相同
 */

public class P207_CanFinish {
    public static void main(String[] args) {
        int[][] prerequisites = {{1, 0}};
        int[][] prerequisites1 = {{1, 0}, {0, 1}};
        P207_Solution solution = new P207_Solution();
        // true: 总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
        System.out.println(solution.canFinish(2, prerequisites));
        // false: 总共有 2 门课程。学习课程 1 之前，你需要先完成课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
        System.out.println(solution.canFinish(2, prerequisites1));
    }
}

// 拓扑排序(Topological sort)(BFS 队列实现)
// BFS 我的实现, 创建 CourseNode 节点, 使用 CourseNode[] 构建邻接表
// 官方实现未做
class P207_Solution1 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 各个节点的入度
        int[] inDegree = new int[numCourses];
        // 图的邻接表表示
        CourseNode[] adjList = new CourseNode[numCourses];
        // 一条有向边的起始节点与结束节点
        int from, to;
        // 构造图
        for (int[] edge : prerequisites) {
            // from 表示要先学的课程, 学完课程 from, 才能学 to
            from = edge[1];
            // to 表示要先学完课程 from, 才能学 to
            to = edge[0];
            // 更新 to 节点的入度, 一条 from -> to 的边, 则 to 节点的入度 + 1
            inDegree[to]++;
            // 采用头插法构造图, 与注释的三行等价
            adjList[from] = new CourseNode(to, adjList[from]);
            /*
            CourseNode toNode = new CourseNode(to);
            toNode.next = adjList[from];
            adjList[from] = toNode;
             */
        }

        // BFS
        // 将入度为 0 的所有节点入队, 有可能是非连通图
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // 如果 queue 中一个入度为 0 的节点都没有, 表示图存在环, 并且整个图是一个大环, 所有节点的入度都大于 0
        // 此判断可以删除
        if (queue.isEmpty()) {
            return false;
        }

        // bfsCount 表示 BFS 的次数, 同时也表示经过历次循环后, 入度为 0 的节点的个数, 学习了的课程数
        // 如果无环, 当循环结束时, bfsCount 应该等于节点总数 numCourses
        int bfsCount = 0;
        int cur;
        CourseNode next;
        while (!queue.isEmpty()) {
            // 出队一个入度为 0 的节点(无先修课程或已学完先修课程)
            cur = queue.remove();
            // 次数加 +1
            bfsCount++;
            // adjList[cur] 处存储的是 cur 能够到达的下一个节点 next
            // 即以 cur 为先修课程的每个课程 next
            next = adjList[cur];
            // 遍历所有以 cur 为先修课程的每个课程 next
            while (next != null) {
                // next 的入度 -1, 即学完了一门先修课程
                inDegree[next.value]--;
                // 为 0 时, 表示学完了所有的先修课程
                if (inDegree[next.value] == 0) {
                    // 那么此时就可以将这门课程加入队列
                    queue.add(next.value);
                }
                // 下一门以 cur 为先修课程的课程
                next = next.next;
            }
        }
        // 循环结束时, 如果 bfsCount == numCourses, 说明所有的课程都能学完(先学其先修课程), 也即图中无环
        // 如果不相等, 说明有些课程无法学习, 原因在于这几门课程互相之间以另一门课程为先修课程
        // 以数据结构的角度来看, 说明图中存在环
        return bfsCount == numCourses;
    }
}

// 拓扑排序(Topological sort)(DFS 递归实现)
// DFS 第一种实现, 官方实现, 使用 List<List<Integer>> 构建邻接表
class P207_Solution2 {
    // 使用 List<List<Integer>> adjList = new ArrayList<>(); 构造邻接表
    List<List<Integer>> adjList;
    // 记录遍历情况
    int[] visited;
    // 标记是否是有向无环图
    boolean valid = true;

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        adjList = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }
        visited = new int[numCourses];
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
                break;
            }
        }
        return valid;
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
    }
}

// 拓扑排序(Topological sort)(DFS 递归实现)
// DFS 第二种实现, 创建 CourseNode 节点, 使用 CourseNode[] 构建邻接表
// 效率最高, 耗时最少, 但是消耗的内存更多一点
class P207_Solution {
    // 使用 CourseNode[] 构建邻接表
    CourseNode[] adjList;
    // 记录每个节点的状态: 0=未搜索，1=搜索中，2=已完成
    int[] visited;
    // 标记是否是有向无环图
    boolean valid = true;

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 邻接表存储有向图
        adjList = new CourseNode[numCourses];
        visited = new int[numCourses];

        // 构建邻接表
        int from, to;
        for (int[] edge : prerequisites) {
            // 有向边的起始节点
            from = edge[1];
            // 有向边的终止节点
            to = edge[0];
            // 将该边添加进邻接表 adjList
            // 采用头插法构造图, 与注释的三行等价
            adjList[from] = new CourseNode(to, adjList[from]);
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
                break;
            }
        }
        return valid;
    }

    private void dfs(int i) {
        // 标记为 1, 表示正在搜索中
        visited[i] = 1;

        CourseNode next = adjList[i];
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
    }
}

class CourseNode {
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