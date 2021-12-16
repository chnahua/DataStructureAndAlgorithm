package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-14 23:47
 */

import java.util.*;

/**
 * 1494. 并行课程 II
 * <p>
 * 给你一个整数 n 表示某所大学里课程的数目，编号为 1 到 n ，数组 dependencies 中，
 * dependencies[i] = [xi, yi] 表示一个先修课的关系，也就是课程 xi 必须在课程 yi 之前上。同时你还有一个整数 k 。
 * <p>
 * 在一个学期中，你 最多 可以同时上 k 门课，前提是这些课的先修课在之前的学期里已经上过了。
 * <p>
 * 请你返回上完所有课最少需要多少个学期。题目保证一定存在一种上完所有课的方式。
 * <p>
 * 1 <= n <= 15
 * 1 <= k <= n
 * 0 <= dependencies.length <= n * (n-1) / 2
 * dependencies[i].length == 2
 * 1 <= xi, yi <= n
 * xi != yi
 * 所有先修关系都是不同的，也就是说 dependencies[i] != dependencies[j]
 * 题目输入的图是个有向无环图。
 */
public class P1494_MinNumberOfSemesters {
    public static void main(String[] args) {
        int[][] relations = new int[][]{{2, 1}, {3, 1}, {1, 4}};
        int[][] relations1 = new int[][]{{2, 1}, {3, 1}, {4, 1}, {1, 5}};
        int[][] relations2 = new int[][]{};
        int[][] relations3 = new int[][]{{3, 1}};
        int[][] relations4 = new int[][]{
                {12, 8}, {2, 4}, {3, 7}, {6, 8}, {11, 8},
                {9, 4}, {9, 7}, {12, 4}, {11, 4}, {6, 4},
                {1, 4}, {10, 7}, {10, 4}, {1, 7}, {1, 8},
                {2, 7}, {8, 4}, {10, 8}, {12, 7}, {5, 4},
                {3, 4}, {11, 7}, {7, 4}, {13, 4}, {9, 8}, {13, 8}
        };
        int[][] relations5 = new int[][]{{11, 7}};
        int[][] relations6 = new int[][]{{12, 11}};
        int[][] relations7 = new int[][]{
                {1, 2}, {1, 3}, {7, 5},
                {7, 6}, {4, 8}, {8, 9},
                {9, 10}, {10, 11}, {11, 12}
        };
        P1494_Solution solution = new P1494_Solution();
//        // 3 在第一个学期中，我们可以上课程 2 和课程 3 。然后第二个学期上课程 1 ，第三个学期上课程 4
//        System.out.println(solution.minNumberOfSemesters(4, relations, 2));
//        // 4 一个最优方案是：第一学期上课程 2 和 3，第二学期上课程 4 ，第三学期上课程 1 ，第四学期上课程 5
        System.out.println(solution.minNumberOfSemesters(5, relations1, 2));
//        System.out.println(solution.minNumberOfSemesters(11, relations2, 2)); // 6
//        System.out.println(solution.minNumberOfSemesters(5, relations3, 4)); // 2
//        System.out.println(solution.minNumberOfSemesters(13, relations4, 9)); // 3
//        System.out.println(solution.minNumberOfSemesters(14, relations5, 7)); // 2
//        System.out.println(solution.minNumberOfSemesters(14, relations5, 2)); // 7
//        System.out.println(solution.minNumberOfSemesters(15, relations6, 12)); // 2
//        // 这个案例太有趣了, 太特殊了, 直接让最快删除边数和最快删除入度为 0 的两种贪心都不能成功
//        System.out.println(solution.minNumberOfSemesters(12, relations7, 2)); // 6
    }
}

// 简单的拓扑排序尝试 未完成
class P1494_Solution1 {
    public int minNumberOfSemesters(int n, int[][] relations, int k) {
        // 邻接表构建图, 下标从 1 开始, 0 不做任何处理
        P1494_CourseNode[] adjList = new P1494_CourseNode[n + 1];
        // 图中各节点的入度
        int[] inDegree = new int[n + 1];

        // 构建邻接表并且得到各节点的入度
        int from, to;
        for (int[] edge : relations) {
            from = edge[0];
            to = edge[1];

            inDegree[to]++;

            // 将该边添加进邻接表 adjList
            // 采用头插法构造图, 与注释的三行等价
            adjList[from] = new P1494_CourseNode(to, adjList[from]);
            /*
            P1494_CourseNode toNode = new P1494_CourseNode(to);
            toNode.next = adjList[from];
            adjList[from] = toNode;
             */
        }

        // 将入度为 0 的节点入队
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        int semesters = 0;
        int size = 0;
        int temp = k;
        int cur;
        P1494_CourseNode next;
        while (!queue.isEmpty()) {
            if (temp == k) {
                size = queue.size();
                if (size <= k) {
                    temp = size;
                }
                semesters++;
            }
            temp--;
//            if (temp == 0 || size < k) {
//                temp = k;
//                semesters++;
//            }
            if (temp == 0) {
                temp = k;
            }

//            Queue<Integer> newQueue = new LinkedList<>(queue);
//            List<Integer> list = new ArrayList<>(size);
//            for (int i = 0; i < size; i++) {
//                list.add();
//            }

            cur = queue.poll();
            next = adjList[cur];
            while (next != null) {
                inDegree[next.value]--;
                if (inDegree[next.value] == 0) {
                    queue.add(next.value);
                }
                next = next.next;
            }
        }

        return semesters;
    }
}

// 回溯 BFS 未完成
class P1494_Solution2 {
    // 邻接表构建图, 下标从 1 开始, 0 不做任何处理
    P1494_CourseNode[] adjList;
    // 图中各节点的入度
    int[] inDegree;
    int n;
    int k;
    int semesters;
    int num;
    int[] newInDegree;

    public int minNumberOfSemesters(int n, int[][] relations, int k) {
        this.n = n;
        this.k = k;
        this.num = n % k != 0 ? n / k + 1 : n / k;
        semesters = 0;
        if (k == 1) {
            return n;
        }
        if (relations.length == 0) {
            return num;
        }/* else if (relations.length == 1) {
            if (k == 1) {

            }
        }*/
        //num = n + 1;
        // 邻接表构建图, 下标从 1 开始, 0 不做任何处理
        adjList = new P1494_CourseNode[n + 1];
        // 图中各节点的入度
        inDegree = new int[n + 1];

        // 构建邻接表并且得到各节点的入度
        int from, to;
        for (int[] edge : relations) {
            from = edge[0];
            to = edge[1];

            inDegree[to]++;

            // 将该边添加进邻接表 adjList
            // 采用头插法构造图, 与注释的三行等价
            adjList[from] = new P1494_CourseNode(to, adjList[from]);
            /*
            P1494_CourseNode toNode = new P1494_CourseNode(to);
            toNode.next = adjList[from];
            adjList[from] = toNode;
             */
        }

        // 将入度为 0 的节点入队
        Queue<Integer> q = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                q.add(i);
            }
        }

        int size = 0;
        int cur;
        P1494_CourseNode next;
        while (!q.isEmpty()) {
            size = q.size();
            if (size <= k) {
                func(q, size);
                semesters++;
            } else {
                List<Integer> newQueue = new ArrayList<>(q);
                List<Integer> list = new ArrayList<>(newQueue);
                boolean[] visited = new boolean[size];
                int temp = 0;
                int numPoll;
                int l;

//                for (int i = 0; i < size; i++) {
//                    if (adjList[newQueue.get(i)] == null) {
//                        visited[i] = true;
//                        temp++;
//                    }
//                }
//                while (true) {
//                    if (size <= k) {
//                        break;
//                    }
//                    if (temp == 0 || temp == k) { //
//                        break;
//                    }
//                    boolean[] v1;
//                    if (temp < k) {
//                        v1 = new boolean[size - temp];
//                        numPoll = 0;
//                        l = 0;
//                        for (int i = 0; i < size; i++) {
//                            if (visited[i] && numPoll < temp) {
//                                cur = newQueue.get(i);
//                                list.remove((Integer) cur);
//                                numPoll++;
//                            } else {
//                                v1[l] = visited[i];
//                                l++;
//                            }
//                        }
//                        visited = v1;
//                        newQueue = new ArrayList<>(list);
//                        size = newQueue.size();
//                        // temp -= k;
//                        break;
//                    }
//                    while (temp > k) { //  && size - temp < k
//                        v1 = new boolean[size - k]; // 还有 temp - k 个为 true 的
//                        numPoll = 0;
//                        l = 0;
//                        // 取出 k 个入度为 0 且单独存在的无后继节点的节点
//                        for (int i = 0; i < size; i++) {
//                            if (visited[i] && numPoll < k) {
//                                cur = newQueue.get(i);
//                                list.remove((Integer) cur);
//                                numPoll++;
//                            } else {
//                                // System.out.println("l:" + l);
//                                v1[l] = visited[i];
//                                l++;
//                            }
//                        }
//                        visited = v1;
//                        newQueue = new ArrayList<>(list);
//                        size = newQueue.size();
//                        temp -= k;
//                        semesters++;
//                    }
//                }
//                if (temp == k) {
//                    temp = 0;
//                    visited = new boolean[size];
//                }
//                if (size <= k) {
//                    q = new LinkedList<>(newQueue);
//                    continue;
//                }
                semesters += dfs(temp, visited, list, newQueue) + 1;
                return semesters;
            }
        }

        return semesters;
    }

    private void func(Queue<Integer> q, int size) {
        int cur;
        P1494_CourseNode next;
        for (int i = 0; i < size; i++) {
            cur = q.remove();
            next = adjList[cur];
            while (next != null) {
                inDegree[next.value]--;
                if (inDegree[next.value] == 0) {
                    q.add(next.value);
                }
                next = next.next;
            }
        }
    }

    private int dfs(int depth, boolean[] visited, List<Integer> list, List<Integer> q) {
        int sem = 0;
        Queue<Integer> queue = new LinkedList<>(list);
        newInDegree = Arrays.copyOf(inDegree, n + 1);
        if (depth == k) {
            while (!queue.isEmpty()) {
                int size = queue.size();
                if (size <= k) {
                    int cur;
                    P1494_CourseNode next;
                    for (int i = 0; i < size; i++) {
                        cur = queue.remove();
                        next = adjList[cur];
                        while (next != null) {
                            inDegree[next.value]--;
                            if (inDegree[next.value] == 0) {
                                queue.add(next.value);
                            }
                            next = next.next;
                        }
                    }
                    sem++;
                } else {
                    List<Integer> newQueue = new ArrayList<>(queue);
                    List<Integer> newList = new ArrayList<>(queue);
//                    for (int i = 0; i < size; i++) {
//                        newList.add(newQueue.get(i));
//                    }
                    boolean[] newVisited = new boolean[size];
                    sem += dfs(0, newVisited, newList, newQueue);
                }
            }
            inDegree = newInDegree;
            return sem;
        }
        int cur;
        P1494_CourseNode next;
        // newInDegree = Arrays.copyOf(inDegree, n + 1);
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                visited[i] = true;

                cur = q.get(i);
                list.remove((Integer) cur);
                next = adjList[cur];
                while (next != null) {
                    inDegree[next.value]--;
                    if (inDegree[next.value] == 0) {
                        list.add(next.value);
                    }
                    next = next.next;
                }

                if (sem == 0) {
                    sem = dfs(depth + 1, visited, list, q);
                } else if (sem <= num) {
                    return sem;
                } else {
                    sem = Math.min(sem, dfs(depth + 1, visited, list, q));
                }
//                next = adjList[cur];
//                while (next != null) {
//                    if (inDegree[next.value] == 0) {
//                        list.remove((Integer) next.value);
//                    }
//                    inDegree[next.value]++;
//                    next = next.next;
//                }
//                list.add(cur);
                list = new ArrayList<>(queue);
                inDegree = Arrays.copyOf(newInDegree, n + 1);

                visited[i] = false;
            }
        }
        // System.out.println("000000000");
        return sem;
    }
}

// 贪心 未完成
class P1494_Solution3 {
    // 邻接表构建图, 下标从 1 开始, 0 不做任何处理
    P1494_CourseNode[] adjList;
    // 图中各节点的入度
    int[] inDegree;
    // 图中各节点的出度
    List<Map.Entry<Integer, Integer>> outDegree;
    int n;
    int k;
    int semesters;
    int num;
    int[] newInDegree;

    public int minNumberOfSemesters(int n, int[][] relations, int k) {
        this.n = n;
        this.k = k;
        this.num = n % k != 0 ? n / k + 1 : n / k;
        semesters = 0;
        if (k == 1) {
            return n;
        }
        if (relations.length == 0) {
            return num;
        }/* else if (relations.length == 1) {
            if (k == 1) {

            }
        }*/

        // 邻接表构建图, 下标从 1 开始, 0 不做任何处理
        adjList = new P1494_CourseNode[n + 1];
        // 图中各节点的入度
        inDegree = new int[n + 1];
        // 图中各节点的出度 临时保存
        HashMap<Integer, Integer> tempOutDegree = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            tempOutDegree.put(i, 0);
        }

        // 构建邻接表并且得到各节点的入度
        int from, to;
        for (int[] edge : relations) {
            from = edge[0];
            to = edge[1];

            inDegree[to]++;

            tempOutDegree.put(from, tempOutDegree.get(from) + 1);

            // 将该边添加进邻接表 adjList
            // 采用头插法构造图, 与注释的三行等价
            adjList[from] = new P1494_CourseNode(to, adjList[from]);
            /*
            P1494_CourseNode toNode = new P1494_CourseNode(to);
            toNode.next = adjList[from];
            adjList[from] = toNode;
             */
        }

        outDegree = new ArrayList<>(tempOutDegree.entrySet());
        // 出度降序排列
        outDegree.sort((o1, o2) -> o2.getValue() - o1.getValue());
        System.out.println("初始所有节点的出度 : " + outDegree);

        // 将入度为 0 的节点入队
        Queue<Integer> q = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                q.add(i);
            }
        }

        int size = 0;
        int cur;
        P1494_CourseNode next;
        while (!q.isEmpty()) {
            size = q.size();
            if (size <= k) {
                func(q, size);
                System.out.println("size <= k : " + outDegree);
                semesters++;
            } else {
                List<Integer> newQueue = new ArrayList<>(q);
                List<Integer> list = new ArrayList<>(newQueue);
                // newInDegree = Arrays.copyOf(inDegree, n + 1);
                // int countIn = 0;
                // 节点 和 边 双重作用
                // 获取前 k 个出度最多的节点, 将其出队
                // 新加入节点最多的组合
                // TreeMap<Integer, Integer> map = new TreeMap<>();
//                for (int i = 0; i < size; i++) {
//                    cur = newQueue.get(i);
//                    next = adjList[cur];
//                    while (next != null) {
//                        newInDegree[next.value]--;
//                        if (newInDegree[next.value] == 0) {
//                            countIn++;
//                        }
//                        next = next.next;
//                    }
//                    map.put(i, countIn);
//                }
                int temp = 0;
                // 遍历出度
                Iterator<Map.Entry<Integer, Integer>> iterator = outDegree.iterator();
                Queue<Integer> remoList = new LinkedList<>();
                while (iterator.hasNext() && temp < k) {
                    cur = iterator.next().getKey();
                    if (list.contains(cur)) {
                        remoList.add(cur);
                        temp++;
                    }
                }
                // list 中移除 remoList 中的元素
                list.removeAll(remoList);
                System.out.println("remoList 中的元素个数" + remoList.size());

                // 将 remoList 的 k 个元素移除, 更新入度, 新的入度为 0 的 元素加入 remoList
                func(remoList, k);
                semesters++;
                // list 中添加 remoList 中新加入的元素, 此时的 list 就为新的队列
                list.addAll(remoList);
                q = new LinkedList<>(list);
                System.out.println("size > k : " + outDegree);

//                boolean[] visited;
//                int numPoll;
//                int l;
//
//                list = new ArrayList<>(newQueue);
//                visited = new boolean[size];
//                for (int i = 0; i < size; i++) {
//                    if (adjList[newQueue.get(i)] == null) {
//                        visited[i] = true;
//                        temp++;
//                    }
//                }
//                while (true) {
//                    if (size <= k) {
//                        break;
//                    }
//                    if (temp == 0 || temp == k) {
//                        break;
//                    }
//                    boolean[] v1;
//                    if (temp < k) {
//                        v1 = new boolean[size - temp];
//                        numPoll = 0;
//                        l = 0;
//                        for (int i = 0; i < size; i++) {
//                            if (visited[i] && numPoll < temp) {
//                                cur = newQueue.get(i);
//                                list.remove((Integer) cur);
//                                numPoll++;
//                            } else {
//                                v1[l] = visited[i];
//                                l++;
//                            }
//                        }
//                        visited = v1;
//                        newQueue = new ArrayList<>(list);
//                        size = newQueue.size();
//                        // temp -= k;
//                        break;
//                    }
//                    while (temp > k) { //  && size - temp < k
//                        v1 = new boolean[size - k]; // 还有 temp - k 个为 true 的
//                        // l1 = new  ArrayList<>(temp - k);
//                        numPoll = 0;
//                        l = 0;
//                        // 取出 k 个入度为 0 且单独存在的无后继节点的节点
//                        for (int i = 0; i < size; i++) {
//                            if (visited[i] && numPoll < k) {
//                                cur = newQueue.get(i);
//                                list.remove((Integer) cur);
//                                numPoll++;
//                            } else {
//                                // System.out.println("l:" + l);
//                                v1[l] = visited[i];
//                                l++;
//                            }
//                        }
//                        visited = v1;
//                        newQueue = new ArrayList<>(list);
//                        size = newQueue.size();
//                        temp -= k;
//                        semesters++;
//                    }
//                }
//                if (temp == k) {
//                    temp = 0;
//                    visited = new boolean[size];
//                }
//                if (size <= k) {
//                    q = new LinkedList<>(newQueue);
//                    continue;
//                }
//                semesters += dfs(temp, visited, list, newQueue) + 1;
//                return semesters;
            }
        }
        return semesters;
    }

    private void func(Queue<Integer> q, int size) {
        P1494_CourseNode next;
        for (int i = 0; i < size; i++) {
            final int cur = q.remove();
            next = adjList[cur];
            while (next != null) {
                inDegree[next.value]--;
                if (inDegree[next.value] == 0) {
                    q.add(next.value);
                }
                next = next.next;
            }
            outDegree.removeIf(e -> (e.getKey() == cur));
        }
    }

    private int dfs(int depth, boolean[] visited, List<Integer> list, List<Integer> q) {
        int sem = 0;
        Queue<Integer> queue = new LinkedList<>(list);
        if (depth == k) {
            newInDegree = Arrays.copyOf(inDegree, n + 1);
            while (!queue.isEmpty()) {
                int size = queue.size();
                if (size <= k) {
                    int cur;
                    P1494_CourseNode next;
                    for (int i = 0; i < size; i++) {
                        cur = queue.remove();
                        next = adjList[cur];
                        while (next != null) {
                            inDegree[next.value]--;
                            if (inDegree[next.value] == 0) {
                                queue.add(next.value);
                            }
                            next = next.next;
                        }
                    }
                    sem++;
                } else {
                    List<Integer> newQueue = new ArrayList<>(queue);
                    List<Integer> newList = new ArrayList<>(queue);
//                    for (int i = 0; i < size; i++) {
//                        newList.add(newQueue.get(i));
//                    }
                    boolean[] newVisited = new boolean[size];
                    sem += dfs(0, newVisited, newList, newQueue);
                }
            }
            inDegree = newInDegree;
            return sem;
        }
        int cur;
        P1494_CourseNode next;
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                visited[i] = true;

                cur = q.get(i);
                list.remove((Integer) cur);
                next = adjList[cur];
                while (next != null) {
                    inDegree[next.value]--;
                    if (inDegree[next.value] == 0) {
                        list.add(next.value);
                    }
                    next = next.next;
                }

                if (sem == 0) {
                    sem = dfs(depth + 1, visited, list, q);
                } else if (sem <= num) {
                    return sem;
                } else {
                    sem = Math.min(sem, dfs(depth + 1, visited, list, q));
                }
                list.add(cur);
                next = adjList[cur];
                while (next != null) {
                    if (inDegree[next.value] == 0) {
                        list.remove((Integer) next.value);
                    }
                    inDegree[next.value]++;
                    next = next.next;
                }
                // list.add(cur);
                //   list = new ArrayList<>(queue);
                visited[i] = false;
            }
        }
        // System.out.println("000000000");
        return sem;
    }
}

class P1494_CourseNode {
    // value 为课程编号
    int value;
    P1494_CourseNode next;

    public P1494_CourseNode(int value) {
        this.value = value;
    }

    public P1494_CourseNode(int value, P1494_CourseNode next) {
        this.value = value;
        this.next = next;
    }
}

// 动态规划: 状态压缩 DP
class P1494_Solution4 {
    public int minNumberOfSemesters(int n, int[][] relations, int k) {
        // 先修课程 二进制状态压缩
        // pre[i] 保存的是第 i(0到n-1) 门课程的先修课程, 用二进制来表示
        // 例如 i 的先修课程如果是 2 5 7 的话, 则 pre[i] 处保存的值的二进制为 (省略若干个 0 ) 0101 0010, 在第 j(1,4,6) 个位置上的值置为 1
        int[] pre = new int[n];
        // 构造 pre, 通过将 1 左移 (先修课程编号 - 1) 位实现, 这只是一门课程的二进制表示
        // 每当有一门先修课程, 还需要将 (1 左移后的二进制值) 与 (pre[i]) 进行 '或' 运算
        // 例如, 现有三个 relation: (2,1)(5,1)(7,1) , 表示课程 1 有三门先修课程, 分别是 2, 5, 7
        // 初始时: pre[1-1] = pre[0] = 0000 0000
        // 第 1 次: 1 << (2 - 1) = 0000 0010, 此时 pre[0]: 0000 0000 | 0000 0010 = 0000 0010
        // 第 2 次: 1 << (5 - 1) = 0001 0000, 此时 pre[0]: 0000 0010 | 0001 0000 = 0001 0010
        // 第 3 次: 1 << (7 - 1) = 0100 0000, 此时 pre[0]: 0001 0010 | 0100 0000 = 0101 0010
        // 最终得到课程 1(pre 中下标为 0) 的所有先修课程的集合(只是这个集合是以一个二进制形式保存而已, 更重要的是方便取其集合子集)
        for (int[] relation : relations) {
            pre[relation[1] - 1] |= 1 << relation[0] - 1;
        }
        // 动态规划, 求出每种状态下的最少学习学期数
        // 总状态数 N, 状态可理解为现有 n 门课程, 每门课程有两种情况(可能已学习, 也可能未学习), 共计 2^n = 1 << n 种状态
        final int N = 1 << n;
        // dp[mask] 表示修完 mask 代表的所有课程的最少学习学期数
        // 动态规划就会遍历这 N 种状态, 得到每种状态下的最少学习学期数
        int[] dp = new int[N];
        // 对 dp 数组赋初值, 题目保证能全部修完, 初始化为最大值 n (或 >n 的数)即可
        // 因为不管有多少门课程, k(k>=1) 为多少, n 门课程最多不过 n 学期就能学完
        Arrays.fill(dp, n);
        // 当然对于 dp[0], 即状态 mask == 0, 代表学习 0 门课程的最少学习学期数, 当然是 0, 其余的情况未知, 设为最大值 n
        // 假设为 4 门课程, 初始 0 门课需要 0 学期 dp[0000] = 0, 最终目标是求 dp[1111]
        dp[0] = 0;

        // 其实上面还是没有完全讲清楚 2^n 种状态中的状态到底是什么, 每一种状态 mask 可以理解为
        // 假设现在学习了 (mask的二进制代表的) 这 m 门课程的情况下, 还能从剩下的还未学习的课程中学习哪些课程? 学习多少门课程?
        // 而能够学习的这些课程(肯定它的先修课程已经学完了, 已经存在 mask的二进制 中了)总数可能超过 k
        // 即便没有超过 k, 那学习哪几门课程呢? 这里就会产生多种学习课程组合, 而通过二进制保存的形式, 又能很方便地得到种种组合

        // (mask的二进制代表的)这 m 门课程 是指
        // mask 的值范围为 (0, 2^n), 对于其中一种特定的状态, 一个特定的 mask 值
        // 例如 mask 为 18 = 0001 0010 时, dp[mask] 表示学习(第 2 门课程 和 第 5 门课程)需要学习的最少学期数
        // 但是在以 mask = 18 的遍历过程中, 并不是理解成此轮循环是在求 dp[mask](dp[18])
        // 而是求在假定已经学习了 (课程 2 和 课程 5) 的情况下
        // 若还能学习其它若干门课程, 那么学习这 (课程 2 和 课程 5 以及加上这若干门课程) 的最少学习学期数 dp[newMask]
        // newMask 为 0001 0010 '或上' 这若干门课程集合的二进制表示

        // 遍历这 2^n 种状态
        for (int mask = 0; mask < N; ++mask) {
            // 当前状态下可学习的所有课程集合(二进制表示)
            int canStudy = 0;
            // 得到当前状态下的 n 门课程中可学习的课程集合 canStudy, 设最终课程数为 c
            // 在 mask 表示的已经学习了 m 门课程的状态下, 对于任意一门课程 i(0<=i<n), 是否能学习课程 i 由两个条件决定
            // 条件 1: 课程 i 肯定是要未学习过的, 也就是课程 i 要不在 mask 表示的 m 门已经学过的课程中才又可能去学习它
            // 条件 2: 课程 i 的所有先修课程都已经学习完毕, 也就是所有的先修课程都包含在 mask 表示的 m 门已经学过的课程中
            // 总结: 能学习课程 i, 课程 i 必须保证之前还未学习过(学过了就不学了), 并且它的所有先修课程都已经学习完毕(先修课程没学完就学不了)
            for (int i = 0; i < n; ++i) {
                // 条件 1: (mask & (1 << n)) == 0
                // 1 << i, 表示当前正在判断的这门是否能学习的课程 i 的二进制表示(1 的位置)
                // (mask & (1 << i)) != 0, 说明当前这门要学习的课程 i 已经在此次状态 mask 表示的 m 门已学习的课程中
                // 即已经学过它, 跳过, 不学习它; 反之, 则不存在, 后续条件 2 再判断它的先修课程是否已经学完, 学完则说明该课程 i 可以学习

                // 条件 2: (mask & pre[i]) == pre[i]
                // pre[i] 是课程 i 的所有先修课程, mask 是已经学习的所有课程, 现要判断后者是否包含前者, 也就是说在两者的二进制表示中
                // pre[i] 中为 1 的相应位置, mask 中也必须为 1; pre[i]中为 0 的位置, mask 中可能为 0 或 1
                // 将两者进行 & 运算, mask 中的(pre[i] 中为 1 的)相应位置必须也为 1, 除此外的其它位置置为 0, 最终得到的结果如果与 pre[i] 相等
                // 说明 mask 包含课程 i 的所有先修课程(还可能包含其它课程), 将其保存; 反之, 则不包含所有先修课程, 不能学习课程 i
                if ((mask & (1 << n)) == 0 && (mask & pre[i]) == pre[i]) {
                    // 课程 i 未学习过, 且 mask 包含课程 i 的所有先修课程, 则可以学习课程 i, 将课程 i 加入集合 canStudy
                    // canStudy 保存 mask 状态下的每一门可以学习的课程, 每有一门, 就将 canStudy 的相应二进制位置为 1, 表示保存
                    // 例如: 总共 n 门课程中能学习 2,5,7 号课程(下标 i 对应为 1,4,6)
                    // 就将 canStudy 的二进制表示 0000 0000 的第 2,5,7 个位置的 0 在三轮循环中依次置为 1
                    // 初始时: canStudy = 0000 0000
                    // 第 1 次, i == 1: 1 << 1 = 0000 0010, 此时 canStudy: 0000 0000 | 0000 0010 = 0000 0010
                    // 第 2 次, i == 4: 1 << 4 = 0001 0000, 此时 canStudy: 0000 0010 | 0001 0000 = 0001 0010
                    // 第 3 次, i == 6: 1 << 6 = 0100 0000, 此时 canStudy: 0001 0010 | 0100 0000 = 0101 0010
                    // 最终 canStudy 为 0101 0010, 表示当前 mask 这种已经学习了 m 门课程的状态下, 能选择学习的课程为 canStudy 表示的课程集合
                    canStudy |= 1 << i;
                }
            }
            // 在得到了所有可以学习的课程集合 canStudy 后, 现在要学习这 canStudy 表示的若干门课程中的课程
            // 那究竟要学习几门课程、哪些课程呢? 并且由于每次最多学习 k 门课程, 说明选择学习的课程是不是会受到此条件的限制?

            // 假设 canStudy 为前面提到过的 0101 0010, 现在从这三门课程中挑选课程学习, 有多少种情况呢? 也就是 canStudy 有哪些子集合呢?
            // 列举一下, canStudy 代表 2,5,7 号课程, 所有子集合为 (2)(5)(7)(25)(27)(57)(257), 共 2^n-1 = 2^3-1 = 7 种(不包含空集合)
            // 此时根据 k 值的不同要学习的课程组合的情况也就不同, 例如:
            // k == 1 : 可选择 (2)(5)(7)                   | 表示每次只能在这 7 种组合中挑选出不多于 1 门课程的子集合课程进行学习
            // k == 2 : 可选择 (2)(5)(7)(25)(27)(57)       | 表示每次只能在这 7 种组合中挑选出不多于 2 门课程的子集合课程进行学习
            // k == 3 : 可选择 (2)(5)(7)(25)(27)(57)(257)  | 表示每次只能在这 7 种组合中挑选出不多于 3 门课程的子集合课程进行学习
            // k >= 4 : 同 k == 3, 因为总共可学习的课程就是三门, 也就是全部课程都能学习, 所有的组合情况都能学习
            // 以上是对问题思路的解决, 但是用代码怎么实现呢? 怎么枚举出可学习课程的所有子集呢?

            // 以下是一种枚举方法, 不知道谁发明的, 真是奇妙, 可以特别记忆一下
            /* 枚举可学习课程的子集进行状态转移
               例 1: canStudy = 1010(2,4), 其子集有 2^2-1 = 3 种, 用二进制表示为 (1010)(1000)(0010)
               首先子集一定在 (0000, 1010] 范围内, 过程如下
               第 1 个 初始时: 1010(24), 十进制值为 10
               第 2 个 (1010 - 1) & 1010 = 1001 & 1010 = 1000(4), 十进制值为 8
               第 3 个 (1000 - 1) & 1010 = 0111 & 1010 = 0010(2), 十进制值为 2
               第 4 个 (0010 - 1) & 1010 = 0001 & 1010 = 0000( ), 十进制值为 0, 不合法

               例 2: canStudy = 0101 0010, 其子集有 2^3-1 = 7 种
               用二进制表示为 0101 0010(257)、0101 0000(57)、0100 0010(27)、0100 0000(7)、0001 0010(25)、0001 0000(5)、0000 0010(2)
               首先子集一定在 (0000 0000, 0101 0010] 范围内, 过程如下
               第 1 个 初始时: 0101 0010(257), 十进制值为 2^6+2^4+2^1 = 82
               第 2 个 (0101 0010 - 1) & 0101 0010 = 0101 0001 & 0101 0010 = 0101 0000(57), 十进制值为 2^6+2^4+     = 80
               第 3 个 (0101 0000 - 1) & 0101 0010 = 0100 1111 & 0101 0010 = 0100 0010(27), 十进制值为 2^6+   + 2^1 = 66
               第 4 个 (0100 0010 - 1) & 0101 0010 = 0100 0001 & 0101 0010 = 0100 0000(7),  十进制值为 2^6+   +     = 64
               第 5 个 (0100 0000 - 1) & 0101 0010 = 0011 1111 & 0101 0010 = 0001 0010(25), 十进制值为    +2^4+ 2^1 = 18
               第 6 个 (0001 0010 - 1) & 0101 0010 = 0001 0001 & 0101 0010 = 0001 0000(5),  十进制值为    +2^4+     = 16
               第 7 个 (0001 0000 - 1) & 0101 0010 = 0000 1111 & 0101 0010 = 0000 0010(2),  十进制值为    +   + 2^1 = 2
               第 8 个 (0000 0010 - 1) & 0101 0010 = 0000 0001 & 0101 0010 = 0000 0000( ),  十进制值为    +   +     = 0, 不合法
            */
            for (int toStudy = canStudy; toStudy > 0; toStudy = (toStudy - 1) & canStudy) {
                // Integer.bitCount(study) : 二进制 toStudy 中的 1 的个数, 表示此子集代表的课程数量(假设为 t)
                // 如果能学习此子集代表的课程, 则其子集代表的课程的数量 t 必须要 <= k(一次最多能学习的课程数), 才能学习这个子集课程组合
                // 如果大于 k, 说明在 mask 这种已经学习了 m 门课程的状态下, 虽然有 canStudy 代表的 c 门课程可以选择学习
                // 但是不能通过一次学习就学完这种课程数超过 k 的子集代表的这 t 门课程
                if (Integer.bitCount(toStudy) <= k) {
                    // mask | toStudy 表示 [学习了 mask 代表的 m 门课程和学习了 toStudy 代表的 t(<= k) 门课程] 的
                    // 这种 (m+t) 门课程这种唯一一种组合的状态
                    // 每次更新的值 dp[mask | toStudy] 表示学习 mask | toStudy 这种状态的课程组合的最少学习学期数
                    // 其值等于 曾经的历史状态 dp[mask | toStudy] 与 待定想要更新的状态值 两者中的较小值
                    // 而这个待定想要更新的状态值是 在 mask 这种学习了 m 门课程的状态下的值 dp[mask] 通过一次学习操作(学习这 t 门课程, 学期数加 1)转变过来的
                    // 第 1 种理解:
                    // 即已知 dp[mask] 为学习 mask 代表的这 m 门课程的最少学习学期数, 那么现在要学习另外的 t 门课程
                    // 那么完成后的学期数为 dp[mask]+1, 对应的那个状态是 dp[mask | toStudy]
                    // 第 2 种理解:
                    // dp[mask | toStudy] 表示要学习 (m+t) 门的这种课程组合, 可以通过先学 mask 代表的这 m 门课程, 再在这已学 m 的条件下学另外的 t 门课程
                    // 由前一个状态转变过来, 先学 m 的学期数为 dp[mask], 后学 t 门课程, 需要 1 个学期, 其值为 dp[mask]+1
                    dp[mask | toStudy] = Math.min(dp[mask | toStudy], dp[mask] + 1);
                }
            }
            // 通过上面的描述以及理解, 最终可以总结
            // 其实每次遍历一种 mask 状态, 看可以通过它(mask)能够转变到哪些状态(mask | toStudy), 更新能够转变到的那种状态的 dp 值
            // 而如何转变就是学习 canStudy 中的若干个包含小于等于 k 的课程数的课程子集 toStudy, 满足要求的子集数就是更新状态的次数, 也是能够转变到的状态次数
            // 总结: 也就是每一种状态(mask)都可能由之前循环中的许多种不同状态(< mask)转变而来
            // 而每种状态(mask)也可以通过学习满足要求的 toStudy 课程转变到其它状态(mask | toStudy)
        }
        // System.out.println(Arrays.toString(dp));
        // 最种要得到的是学完 n 门课程的最少学习学期数, 即在最后那种 n 位二进制的 n 个位置上的值都为 1 的那个状态
        return dp[N - 1];
    }
}

// 动态规划: 状态压缩 DP (改进后)
class P1494_Solution {
    public int minNumberOfSemesters(int n, int[][] relations, int k) {
        // 先修课程 二进制状态压缩
        // pre[i] 保存的是第 i(0到n-1) 门课程的先修课程, 用二进制来表示
        // 例如 i 的先修课程如果是 2 5 7 的话, 则 pre[i] 处保存的值的二进制为 (省略若干个 0 ) 0101 0010, 在第 j(1,4,6) 个位置上的值置为 1
        int[] pre = new int[n];
        // 构造 pre, 通过将 1 左移 (先修课程编号 - 1) 位实现, 这只是一门课程的二进制表示
        // 每当有一门先修课程, 还需要将 (1 左移后的二进制值) 与 (pre[i]) 进行 '或' 运算
        // 例如, 现有三个 relation: (2,1)(5,1)(7,1) , 表示课程 1 有三门先修课程, 分别是 2, 5, 7
        // 初始时: pre[1-1] = pre[0] = 0000 0000
        // 第 1 次: 1 << (2 - 1) = 0000 0010, 此时 pre[0]: 0000 0000 | 0000 0010 = 0000 0010
        // 第 2 次: 1 << (5 - 1) = 0001 0000, 此时 pre[0]: 0000 0010 | 0001 0000 = 0001 0010
        // 第 3 次: 1 << (7 - 1) = 0100 0000, 此时 pre[0]: 0001 0010 | 0100 0000 = 0101 0010
        // 最终得到课程 1(pre 中下标为 0) 的所有先修课程的集合(只是这个集合是以一个二进制形式保存而已, 更重要的是方便取其集合子集)
        for (int[] relation : relations) {
            pre[relation[1] - 1] |= 1 << relation[0] - 1;
        }
        // 动态规划, 求出每种状态下的最少学习学期数
        // 总状态数 N, 状态可理解为现有 n 门课程, 每门课程有两种情况(可能已学习, 也可能未学习), 共计 2^n = 1 << n 种状态
        final int N = 1 << n;
        // dp[mask] 表示修完 mask 代表的所有课程的最少学习学期数
        // 动态规划就会遍历这 N 种状态, 得到每种状态下的最少学习学期数
        int[] dp = new int[N];
        // 对 dp 数组赋初值, 题目保证能全部修完, 初始化为最大值 n (或 >n 的数)即可
        // 因为不管有多少门课程, k(k>=1) 为多少, n 门课程最多不过 n 学期就能学完
        Arrays.fill(dp, n);
        // 当然对于 dp[0], 即状态 mask == 0, 代表学习 0 门课程的最少学习学期数, 当然是 0, 其余的情况未知, 设为最大值 n
        // 假设为 4 门课程, 初始 0 门课需要 0 学期 dp[0000] = 0, 最终目标是求 dp[1111]
        dp[0] = 0;

        // 其实上面还是没有完全讲清楚 2^n 种状态中的状态到底是什么, 每一种状态 mask 可以理解为
        // 假设现在学习了 (mask的二进制代表的) 这 m 门课程的情况下, 还能从剩下的还未学习的课程中学习哪些课程? 学习多少门课程?
        // 而能够学习的这些课程(肯定它的先修课程已经学完了, 已经存在 mask的二进制 中了)总数可能超过 k
        // 即便没有超过 k, 那学习哪几门课程呢? 这里就会产生多种学习课程组合, 而通过二进制保存的形式, 又能很方便地得到种种组合

        // (mask的二进制代表的)这 m 门课程 是指
        // mask 的值范围为 (0, 2^n), 对于其中一种特定的状态, 一个特定的 mask 值
        // 例如 mask 为 18 = 0001 0010 时, dp[mask] 表示学习(第 2 门课程 和 第 5 门课程)需要学习的最少学期数
        // 但是在以 mask = 18 的遍历过程中, 并不是理解成此轮循环是在求 dp[mask](dp[18])
        // 而是求在假定已经学习了 (课程 2 和 课程 5) 的情况下
        // 若还能学习其它若干门课程, 那么学习这 (课程 2 和 课程 5 以及加上这若干门课程) 的最少学习学期数 dp[newMask]
        // newMask 为 0001 0010 '或上' 这若干门课程集合的二进制表示

        // 遍历这 2^n 种状态
        for (int mask = 0; mask < N; mask++) {
            boolean valid = true;

            // 当前状态下可学习的所有课程集合(二进制表示)
            int canStudy = 0;
            // 得到当前状态下的 n 门课程中可学习的课程集合 canStudy, 设最终课程数为 c
            // 在 mask 表示的已经学习了 m 门课程的状态下, 对于任意一门课程 i(0<=i<n), 是否能学习课程 i 由两个条件决定
            // 条件 1: 课程 i 肯定是要未学习过的, 也就是课程 i 要不在 mask 表示的 m 门已经学过的课程中才又可能去学习它
            // 条件 2: 课程 i 的所有先修课程都已经学习完毕, 也就是所有的先修课程都包含在 mask 表示的 m 门已经学过的课程中
            // 总结: 能学习课程 i, 课程 i 必须保证之前还未学习过(学过了就不学了), 并且它的所有先修课程都已经学习完毕(先修课程没学完就学不了)
            for (int i = 0; i < n; i++) {

                // 条件 1: (mask & (1 << n)) == 0
                // 1 << i, 表示当前正在判断的这门是否能学习的课程 i 的二进制表示(1 的位置)
                // (mask & (1 << i)) != 0, 说明当前这门要学习的课程 i 已经在此次状态 mask 表示的 m 门已学习的课程中
                // 即已经学过它, 跳过, 不学习它; 反之, 则不存在, 后续条件 2 再判断它的先修课程是否已经学完, 学完则说明该课程 i 可以学习

                // 条件 2: (mask & pre[i]) == pre[i]
                // pre[i] 是课程 i 的所有先修课程, mask 是已经学习的所有课程, 现要判断后者是否包含前者, 也就是说在两者的二进制表示中
                // pre[i] 中为 1 的相应位置, mask 中也必须为 1; pre[i]中为 0 的位置, mask 中可能为 0 或 1
                // 将两者进行 & 运算, mask 中的(pre[i] 中为 1 的)相应位置必须也为 1, 除此外的其它位置置为 0, 最终得到的结果如果与 pre[i] 相等
                // 说明 mask 包含课程 i 的所有先修课程(还可能包含其它课程), 将其保存; 反之, 则不包含所有先修课程, 不能学习课程 i
/*                if ((mask & (1 << i)) == 0 && (mask & pre[i]) == pre[i]) {
                    // 课程 i 未学习过, 且 mask 包含课程 i 的所有先修课程, 则可以学习课程 i, 将课程 i 加入集合 canStudy
                    // canStudy 保存 mask 状态下的每一门可以学习的课程, 每有一门, 就将 canStudy 的相应二进制位置为 1, 表示保存
                    // 例如: 总共 n 门课程中能学习 2,5,7 号课程(下标 i 对应为 1,4,6)
                    // 就将 canStudy 的二进制表示 0000 0000 的第 2,5,7 个位置的 0 在三轮循环中依次置为 1
                    // 初始时: canStudy = 0000 0000
                    // 第 1 次, i == 1: 1 << 1 = 0000 0010, 此时 canStudy: 0000 0000 | 0000 0010 = 0000 0010
                    // 第 2 次, i == 4: 1 << 4 = 0001 0000, 此时 canStudy: 0000 0010 | 0001 0000 = 0001 0010
                    // 第 3 次, i == 6: 1 << 6 = 0100 0000, 此时 canStudy: 0001 0010 | 0100 0000 = 0101 0010
                    // 最终 canStudy 为 0101 0010, 表示当前 mask 这种已经学习了 m 门课程的状态下, 能选择学习的课程为 canStudy 表示的课程集合
                    canStudy |= 1 << i;
                }
*/
                // 优化
                // 如果 mask 中包含了这门课程 i, 说明已经学习了这门课程 i, 那么 mask 就一定要包含课程 i 的所有先修课程
                // 才能使得 mask 这种状态作为中转的状态, 能够转移到其它的状态, 不然就是不可能状态
                if ((mask & (1 << i)) != 0) {
                    // mask 包含了课程 i, 但是不包含或者不完全包含课程 i 的所有先修课程, 这种状态就是不对的, 可以跳过
                    if ((mask & pre[i]) != pre[i]) {
                        valid = false;
                        break;
                    }
                } else {
                    // 如果 mask 中不包含这门课程 i, 再根据是否包含这门课程 i 的所有先修课程, 决定是否能学习这门课程
                    // 包含所有先修课程, 则能学习, 反之, 则不能学习
                    if ((mask & pre[i]) == pre[i]) {
                        canStudy |= 1 << i;
                    }
                }
            }
            // 如果 mask 是错误状态, 不可能存在的状态, 则直接进行下一次循环
            if (!valid) {
                continue;
            }
            // 在得到了所有可以学习的课程集合 canStudy 后, 现在要学习这 canStudy 表示的若干门课程中的课程
            // 那究竟要学习几门课程、哪些课程呢? 并且由于每次最多学习 k 门课程, 说明选择学习的课程是不是会受到此条件的限制?

            // 假设 canStudy 为前面提到过的 0101 0010, 现在从这三门课程中挑选课程学习, 有多少种情况呢? 也就是 canStudy 有哪些子集合呢?
            // 列举一下, canStudy 代表 2,5,7 号课程, 所有子集合为 (2)(5)(7)(25)(27)(57)(257), 共 2^n-1 = 2^3-1 = 7 种(不包含空集合)
            // 此时根据 k 值的不同要学习的课程组合的情况也就不同, 例如:
            // k == 1 : 可选择 (2)(5)(7)                   | 表示每次只能在这 7 种组合中挑选出不多于 1 门课程的子集合课程进行学习
            // k == 2 : 可选择 (2)(5)(7)(25)(27)(57)       | 表示每次只能在这 7 种组合中挑选出不多于 2 门课程的子集合课程进行学习
            // k == 3 : 可选择 (2)(5)(7)(25)(27)(57)(257)  | 表示每次只能在这 7 种组合中挑选出不多于 3 门课程的子集合课程进行学习
            // k >= 4 : 同 k == 3, 因为总共可学习的课程就是三门, 也就是全部课程都能学习, 所有的组合情况都能学习
            // 以上是对问题思路的解决, 但是用代码怎么实现呢? 怎么枚举出可学习课程的所有子集呢?

            // 以下是一种枚举方法, 不知道谁发明的, 真是奇妙, 可以特别记忆一下
            /* 枚举可学习课程的子集进行状态转移
               例 1: canStudy = 1010(2,4), 其子集有 2^2-1 = 3 种, 用二进制表示为 (1010)(1000)(0010)
               首先子集一定在 (0000, 1010] 范围内, 过程如下
               第 1 个 初始时: 1010(24), 十进制值为 10
               第 2 个 (1010 - 1) & 1010 = 1001 & 1010 = 1000(4), 十进制值为 8
               第 3 个 (1000 - 1) & 1010 = 0111 & 1010 = 0010(2), 十进制值为 2
               第 4 个 (0010 - 1) & 1010 = 0001 & 1010 = 0000( ), 十进制值为 0, 不合法

               例 2: canStudy = 0101 0010, 其子集有 2^3-1 = 7 种
               用二进制表示为 0101 0010(257)、0101 0000(57)、0100 0010(27)、0100 0000(7)、0001 0010(25)、0001 0000(5)、0000 0010(2)
               首先子集一定在 (0000 0000, 0101 0010] 范围内, 过程如下
               第 1 个 初始时: 0101 0010(257), 十进制值为 2^6+2^4+2^1 = 82
               第 2 个 (0101 0010 - 1) & 0101 0010 = 0101 0001 & 0101 0010 = 0101 0000(57), 十进制值为 2^6+2^4+     = 80
               第 3 个 (0101 0000 - 1) & 0101 0010 = 0100 1111 & 0101 0010 = 0100 0010(27), 十进制值为 2^6+   + 2^1 = 66
               第 4 个 (0100 0010 - 1) & 0101 0010 = 0100 0001 & 0101 0010 = 0100 0000(7),  十进制值为 2^6+   +     = 64
               第 5 个 (0100 0000 - 1) & 0101 0010 = 0011 1111 & 0101 0010 = 0001 0010(25), 十进制值为    +2^4+ 2^1 = 18
               第 6 个 (0001 0010 - 1) & 0101 0010 = 0001 0001 & 0101 0010 = 0001 0000(5),  十进制值为    +2^4+     = 16
               第 7 个 (0001 0000 - 1) & 0101 0010 = 0000 1111 & 0101 0010 = 0000 0010(2),  十进制值为    +   + 2^1 = 2
               第 8 个 (0000 0010 - 1) & 0101 0010 = 0000 0001 & 0101 0010 = 0000 0000( ),  十进制值为    +   +     = 0, 不合法
            */
            for (int toStudy = canStudy; toStudy > 0; toStudy = (toStudy - 1) & canStudy) {

                // Integer.bitCount(study) : 二进制 toStudy 中的 1 的个数, 表示此子集代表的课程数量(假设为 t)
                // 如果能学习此子集代表的课程, 则其子集代表的课程的数量 t 必须要 <= k(一次最多能学习的课程数), 才能学习这个子集课程组合
                // 如果大于 k, 说明在 mask 这种已经学习了 m 门课程的状态下, 虽然有 canStudy 代表的 c 门课程可以选择学习
                // 但是不能通过一次学习就学完这种课程数超过 k 的子集代表的这 t 门课程
                if (Integer.bitCount(toStudy) <= k) {
                    // if (bitCount(toStudy) <= k) {
                    // mask | toStudy 表示 [学习了 mask 代表的 m 门课程和学习了 toStudy 代表的 t(<= k) 门课程] 的
                    // 这种 (m+t) 门课程这种唯一一种组合的状态
                    // 每次更新的值 dp[mask | toStudy] 表示学习 mask | toStudy 这种状态的课程组合的最少学习学期数
                    // 其值等于 曾经的历史状态 dp[mask | toStudy] 与 待定想要更新的状态值 两者中的较小值
                    // 而这个待定想要更新的状态值是 在 mask 这种学习了 m 门课程的状态下的值 dp[mask] 通过一次学习操作(学习这 t 门课程, 学期数加 1)转变过来的
                    // 第 1 种理解:
                    // 即已知 dp[mask] 为学习 mask 代表的这 m 门课程的最少学习学期数, 那么现在要学习另外的 t 门课程
                    // 那么完成后的学期数为 dp[mask]+1, 对应的那个状态是 dp[mask | toStudy]
                    // 第 2 种理解:
                    // dp[mask | toStudy] 表示要学习 (m+t) 门的这种课程组合, 可以通过先学 mask 代表的这 m 门课程, 再在这已学 m 的条件下学另外的 t 门课程
                    // 由前一个状态转变过来, 先学 m 的学期数为 dp[mask], 后学 t 门课程, 需要 1 个学期, 其值为 dp[mask]+1
                    System.out.println("dp[" + mask + " | " + toStudy + "] : " + dp[mask | toStudy]);
                    dp[mask | toStudy] = Math.min(dp[mask | toStudy], dp[mask] + 1);
                    System.out.println("dp[" + mask + " | " + toStudy + "] : " + dp[mask | toStudy]);
                }
            }
            // 通过上面的描述以及理解, 最终可以总结
            // 其实每次遍历一种 mask 状态, 看可以通过它(mask)能够转变到哪些状态(mask | toStudy), 更新能够转变到的那种状态的 dp 值
            // 而如何转变就是学习 canStudy 中的若干个包含小于等于 k 的课程数的课程子集 toStudy, 满足要求的子集数就是更新状态的次数, 也是能够转变到的状态次数
            // 总结: 也就是每一种状态(mask)都可能由之前循环中的许多种不同状态(< mask)转变而来
            // 而每种状态(mask)也可以通过学习满足要求的 toStudy 课程转变到其它状态(mask | toStudy)
        }
        // System.out.println(Arrays.toString(dp));
        // 最种要得到的是学完 n 门课程的最少学习学期数, 即在最后那种 n 位二进制的 n 个位置上的值都为 1 的那个状态
        return dp[N - 1];
    }

    // 第一个求二进制中 1 的个数的方法
    // 这个自己写的和源码的效率相差太大了吧
    // 使用源码是 94 ms
    // 使用这个是 886 ms
    private int bitCount(int a) {
        int count = 0;
        while (a != 0) {
            if ((a & 0x1) == 1) {
                count++;
            }
            a = a >> 1;
        }
        return count;
    }

    // 第二个求二进制中 1 的个数的方法 99ms
    private int getBit(int x) {
        int res = 0;
        while (x > 0) {
            res++;
            x = x & (x - 1);
        }
        return res;
    }
}