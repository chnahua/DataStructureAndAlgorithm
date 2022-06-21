package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-28 21:34
 */

import java.util.Arrays;

/**
 * 1601. 最多可达成的换楼请求数目 maximum-number-of-achievable-transfer-requests
 * 我们有 n 栋楼，编号从 0 到 n - 1 。每栋楼有若干员工。由于现在是换楼的季节，部分员工想要换一栋楼居住。
 * <p>
 * 给你一个数组 requests ，其中 requests[i] = [fromi, toi] ，表示一个员工请求从编号为 fromi 的楼搬到编号为 toi 的楼。
 * <p>
 * 一开始 所有楼都是满的，所以从请求列表中选出的若干个请求是可行的需要满足 每栋楼员工净变化为 0 。
 * 意思是每栋楼 离开 的员工数目 等于 该楼 搬入 的员工数数目。
 * 比方说 n = 3 且两个员工要离开楼 0 ，一个员工要离开楼 1 ，一个员工要离开楼 2 ，
 * 如果该请求列表可行，应该要有两个员工搬入楼 0 ，一个员工搬入楼 1 ，一个员工搬入楼 2 。
 * <p>
 * 请你从原请求列表中选出若干个请求，使得它们是一个可行的请求列表，并返回所有可行列表中最大请求数目。
 * 1 <= n <= 20
 * 1 <= requests.length <= 16
 * requests[i].length == 2
 * 0 <= fromi, toi < n
 */
public class P1601_MaximumRequests {
    public static void main(String[] args) {
        int[][] requests = new int[][]{{0, 1}, {1, 0}, {0, 1}, {1, 2}, {2, 0}, {3, 4}};
        int[][] requests1 = new int[][]{{0, 0}, {1, 2}, {2, 1}};
        int[][] requests2 = new int[][]{{0, 3}, {3, 1}, {1, 2}, {2, 0}};
        P1601_Solution solution = new P1601_Solution();
        System.out.println(solution.maximumRequests(5, requests)); // 5
        System.out.println(solution.maximumRequests(3, requests1)); // 3
        System.out.println(solution.maximumRequests(4, requests2)); // 4
    }
}

// 回溯 + 枚举 官方解法(稍稍修改)
// O(2^m) m 是数组 requests 的长度, 即请求的数量
// 7 ms 98.97%
// O(m+n) 递归需要 O(m) 的栈空间, 数组 delta 需要 O(n) 的空间
// 39 MB 45.36%
class P1601_Solution {
    // 数组 delta 记录每一栋楼的员工变化量
    int[] delta;
    int ans;
    // 被选择的请求数量
    int selectedReqCnt;
    // delta 中的 0 的个数
    int zeroCnt;
    int n;

    public int maximumRequests(int n, int[][] requests) {
        this.n = n;
        delta = new int[n];
        this.selectedReqCnt = 0;
        this.zeroCnt = n;
        this.ans = 0;
        dfs(requests, 0);
        return ans;
    }

    // 正在枚举第 pos 个请求
    private void dfs(int[][] requests, int pos) {
        if (pos == requests.length) {
            if (zeroCnt == n) {
                ans = Math.max(ans, selectedReqCnt);
            }
            return;
        }
        int from = requests[pos][0];
        int to = requests[pos][1];
        // from == to 时, 必须选择该请求
        if (!(from == to)) {
            // 不选 requests[pos]
            dfs(requests, pos + 1);
        }

        // 选 requests[pos]
        // 保留当前值
        int curZero = zeroCnt;
        // 被选择的请求数量加 1
        selectedReqCnt++;
        // 该楼栋员工人数减 1, 相应的 zero 可能会发生变化
        if (delta[from] == 0) {
            zeroCnt--;
        } else if (delta[from] == 1) {
            zeroCnt++;
        }
        delta[from]--;

        // 该楼栋员工人数加 1, 相应的 zero 可能会发生变化
        if (delta[to] == 0) {
            zeroCnt--;
        } else if (delta[to] == -1) {
            zeroCnt++;
        }
        delta[to]++;

        dfs(requests, pos + 1);

        // 回溯之前变化过的四个值
        delta[from]++;
        delta[to]--;
        selectedReqCnt--;
        zeroCnt = curZero;
    }
}

// 二进制枚举
// 65 ms 49.28%
// 39 MB 83.33%
class P1601_Solution_1 {
    public int maximumRequests(int n, int[][] requests) {
        // 数组 delta 记录每一栋楼的员工变化量
        int[] delta = new int[n];
        int ans = 0;
        int m = requests.length;
        for (int mask = 0; mask < (1 << m); mask++) {
            // 当前选择请求的个数
            int cnt = Integer.bitCount(mask);
            if (cnt <= ans) {
                continue;
            }
            Arrays.fill(delta, 0);
            // 遍历选择的请求, 得到各楼栋变化后的人数
            for (int i = 0; i < m; i++) {
                if ((mask & (1 << i)) != 0) {
                    delta[requests[i][0]]--;
                    delta[requests[i][1]]++;
                }
            }
            // 最后对各楼栋人数进行判断, 一旦有楼栋人数不为 0
            // 则该列表为不可行的请求列表
            boolean flag = true;
            for (int x : delta) {
                if (x != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                ans = cnt;
            }
        }
        return ans;
    }
}