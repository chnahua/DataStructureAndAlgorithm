package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-12-29 21:22
 */

import java.util.*;

/**
 * 864. 获取所有钥匙的最短路径
 * 给定一个二维网格 grid。 "." 代表一个空房间， "#" 代表一堵墙， "@" 是起点，
 * （"a", "b", ...）代表钥匙，（"A", "B", ...）代表锁。
 * <p>
 * 我们从起点开始出发，一次移动是指向四个基本方向之一行走一个单位空间。
 * 我们不能在网格外面行走，也无法穿过一堵墙。如果途经一个钥匙，我们就把它捡起来。除非我们手里有对应的钥匙，否则无法通过锁。
 * <p>
 * 假设 K 为钥匙/锁的个数，且满足 1 <= K <= 6，字母表中的前 K 个字母在网格中都有自己对应的一个小写和一个大写字母。
 * 换言之，每个锁有唯一对应的钥匙，每个钥匙也有唯一对应的锁。另外，代表钥匙和锁的字母互为大小写并按字母顺序排列。
 * <p>
 * 返回获取所有钥匙所需要的移动的最少次数。如果无法获取所有钥匙，返回 -1 。
 * <p>
 * 1 <= grid.length <= 30
 * 1 <= grid[0].length <= 30
 * grid[i][j] 只含有 '.', '#', '@', 'a'-'f' 以及 'A'-'F'
 * 钥匙的数目范围是 [1, 6]，每个钥匙都对应一个不同的字母，正好打开一个对应的锁。
 */
public class P864_ShortestPathAllKeys {
    public static void main(String[] args) {
        String[] grid = new String[]{"@.a.#", "###.#", "b.A.B"};
        String[] grid1 = new String[]{"@..aA", "..B#.", "....b"};
        P864_Solution solution = new P864_Solution();
        System.out.println(solution.shortestPathAllKeys(grid));
        System.out.println(solution.shortestPathAllKeys(grid1));
    }
}

class P864_Solution1 {
    int INF = Integer.MAX_VALUE;
    String[] grid;
    int N, M;
    Map<Character, Integer> location;
    // 上左下右
    int[] dx = new int[]{-1, 0, 1, 0};
    int[] dy = new int[]{0, -1, 0, 1};

    public int shortestPathAllKeys(String[] grid) {
        this.grid = grid;
        N = grid.length;
        M = grid[0].length();

        // location : the points of interest
        //
        location = new HashMap<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                char v = grid[i].charAt(j);
                if (v != '.' && v != '#') {
                    location.put(v, i * M + j);
                }
            }
        }
        int targetState = (1 << (location.size() / 2)) - 1;
        // int targetState = (location.size() >> 1) + 1; 错的
        Map<Character, Map<Character, Integer>> dists = new HashMap<>();
        for (char place : location.keySet()) {
            dists.put(place, bfsFrom(place));
        }

        //Dijkstra
        PriorityQueue<ANode> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.dist, b.dist));
        pq.offer(new ANode(new BNode('@', 0), 0));
        Map<BNode, Integer> finalDist = new HashMap<>();
        finalDist.put(new BNode('@', 0), 0);

        while (!pq.isEmpty()) {
            ANode anode = pq.poll();
            BNode node = anode.node;
            int d = anode.dist;
            if (finalDist.getOrDefault(node, INF) < d) continue;
            if (node.state == targetState) return d;

            for (char destination : dists.get(node.place).keySet()) {
                int d2 = dists.get(node.place).get(destination);
                int state2 = node.state;
                if (Character.isLowerCase(destination)) //key
                    state2 |= (1 << (destination - 'a'));
                if (Character.isUpperCase(destination)) //lock
                    if ((node.state & (1 << (destination - 'A'))) == 0) // no key
                        continue;

                if (d + d2 < finalDist.getOrDefault(new BNode(destination, state2), INF)) {
                    finalDist.put(new BNode(destination, state2), d + d2);
                    pq.offer(new ANode(new BNode(destination, state2), d + d2));
                }
            }
        }

        return -1;
    }

    public Map<Character, Integer> bfsFrom(char source) {
        int sr = location.get(source) / M;
        int sc = location.get(source) % M;
        boolean[][] seen = new boolean[N][M];
        seen[sr][sc] = true;
        int curDepth = 0;
        Queue<Integer> queue = new LinkedList<>();
        // 起点入队列
        queue.offer(location.get(source));
        queue.offer(-1);
        Map<Character, Integer> dist = new HashMap<>();

        while (!queue.isEmpty()) {
            int id = queue.poll();
            if (id == -1) {
                curDepth++;
                if (!queue.isEmpty())
                    queue.offer(-1);
                continue;
            }

            int x = id / M, y = id % M;
            if (grid[x].charAt(y) != source && grid[x].charAt(y) != '.') {
                dist.put(grid[x].charAt(y), curDepth);
                continue; // Stop walking from here if we reach a point of interest
            }

            for (int i = 0; i < 4; i++) {
                int cr = x + dx[i];
                int cc = y + dy[i];
                if (0 <= cr && cr < N && 0 <= cc && cc < M && !seen[cr][cc]) {
                    if (grid[cr].charAt(cc) != '#') {
                        queue.offer(cr * M + cc);
                        seen[cr][cc] = true;
                    }
                }
            }
        }

        return dist;
    }
}

// ANode: Annotated Node
class ANode {
    BNode node;
    int dist;

    ANode(BNode n, int d) {
        node = n;
        dist = d;
    }
}

class BNode {
    char place;
    int state;

    BNode(char p, int s) {
        place = p;
        state = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BNode)) return false;
        BNode other = (BNode) o;
        return (place == other.place && state == other.state);
    }

    @Override
    public int hashCode() {
        return 256 * state + place;
    }
}

class P864_Solution {
    // 上下左右
    final static int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int shortestPathAllKeys(String[] grid) {
        int m = grid.length, n = grid[0].length();
        // 利用状态压缩钥匙表示获取钥匙的状态，记录最终获取全部钥匙为target，并在BFS中进行转移
        int target = 0;
        int startX = 0;
        int startY = 0;
        for (int i = 0; i < m; i++) {
            String s = grid[i];
            for (int j = 0; j < n; j++) {
                char ch = s.charAt(j);
                if (ch == '@') {
                    startX = i;
                    startY = j;
                } else if (ch >= 'a' && ch <= 'f') {
                    // 1 左移对应 ch 偏移 a 的位数 即为 第几位 有钥匙
                    target |= (1 << (ch - 'a'));
                }
            }
        }

        // 用 LinkedList 比用 ArrayDeque 快
        Queue<Node> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[m][n][64];

        // 初始起点没有任何钥匙
        queue.offer(new Node(startX * n + startY, 0, 0));
        visited[startX][startY][0] = true;
        // 带状态的 BFS
        while (!queue.isEmpty()) {
            Node curNode = queue.poll();
            int x = curNode.id / n;
            int y = curNode.id % n;
            int dist = curNode.dist;
            int state = curNode.state;

            int nx, ny;
            for (int[] dir : dirs) {
                nx = x + dir[0];
                ny = y + dir[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    // 已经访问过当前状态
                    // 此处不再像常规 BFS 那样, 只判断曾经是否经过该节点(方格), 就可以确定是否访问过,
                    // 而是还要判断曾经经过该节点(方格)时的状态, 而这个状态指的每次到达该节点(方格)时, 拥有哪些钥匙(理论上是 2^k 种)
                    if (visited[nx][ny][state]) {
                        continue;
                    }
                    char ch = grid[nx].charAt(ny);
                    // 如果是障碍物, 说明不能从 (x,y) 走到 (nx,ny), 判断下一个
                    if (ch == '#') {
                        continue;
                    }
                    int newState = state;
                    // 遇到锁, 查看现在是否拥有对应钥匙
                    // 如果没有对应钥匙, 说明暂时还不能走这个格子去找其它钥匙, 得先走其它路径找到这把锁对应的钥匙
                    if (ch >= 'A' && ch <= 'F') {
                        // 两种方法
                        // (state >> (ch - 'A')) & 1) == 0
                        // (state & (1 << (ch - 'A'))) == 1 表示拥有这把锁对应钥匙
                        if ((state & (1 << (ch - 'A'))) == 0) {
                            continue;
                        }
                    } else if (ch >= 'a' && ch <= 'f') {
                        // 新找到的这把钥匙, 更新当前 state
                        newState |= (1 << (ch - 'a'));
                        // 到达终点
                        if (newState == target) {
                            return dist + 1;
                        }
                    }
                    queue.offer(new Node(nx * n + ny, dist + 1, newState));
                    visited[nx][ny][newState] = true;
                }
            }
        }
        return -1;
    }

    static class Node {
        int id, dist, state;

        Node(int id, int dist, int state) {
            this.id = id;
            this.dist = dist;
            this.state = state;
        }
    }
}
