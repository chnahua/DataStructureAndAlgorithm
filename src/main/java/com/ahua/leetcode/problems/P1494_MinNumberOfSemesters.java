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
        System.out.println(solution.minNumberOfSemesters(4, relations, 2));
//        // 4 一个最优方案是：第一学期上课程 2 和 3，第二学期上课程 4 ，第三学期上课程 1 ，第四学期上课程 5
        System.out.println(solution.minNumberOfSemesters(5, relations1, 2));
        System.out.println(solution.minNumberOfSemesters(11, relations2, 2)); // 6
        System.out.println(solution.minNumberOfSemesters(5, relations3, 4)); // 2
        System.out.println(solution.minNumberOfSemesters(13, relations4, 9)); // 3
        System.out.println(solution.minNumberOfSemesters(14, relations5, 7)); // 2
        System.out.println(solution.minNumberOfSemesters(14, relations5, 2)); // 7
        System.out.println(solution.minNumberOfSemesters(15, relations6, 12)); // 2
        // 这个案例太有趣了, 太特殊了, 直接让最快删除边数和最快删除入度为 0 的两种贪心都不能成功
        System.out.println(solution.minNumberOfSemesters(12, relations7, 2)); // 6
    }
}

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

class P1494_Solution {
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