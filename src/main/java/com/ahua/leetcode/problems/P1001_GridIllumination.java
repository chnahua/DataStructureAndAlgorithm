package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-16 0:22
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 1001. 网格照明 grid-illumination
 * 在大小为 n x n 的网格 grid 上，每个单元格都有一盏灯，最初灯都处于 关闭 状态。
 * <p>
 * 给你一个由灯的位置组成的二维数组 lamps ，其中 lamps[i] = [rowi, coli] 表示 打开 位于 grid[rowi][coli] 的灯。
 * 即便同一盏灯库可能在 lamps 中多次列出，不会影响这盏灯处于 打开 状态。
 * <p>
 * 当一盏灯处于打开状态，它将会照亮 自身所在单元格 以及同一 行 、同一 列 和两条 对角线 上的 所有其他单元格 。
 * <p>
 * 另给你一个二维数组 queries ，其中 queries[j] = [rowj, colj] 。
 * 对于第 j 个查询，如果单元格 [rowj, colj] 是被照亮的，则查询结果为 1 ，否则为 0 。
 * 在第 j 次查询之后 [按照查询的顺序] ，关闭 位于单元格 grid[rowj][colj] 上
 * 及相邻 8 个方向上（与单元格 grid[rowi][coli] 共享角或边）的任何灯。
 * <p>
 * 返回一个整数数组 ans 作为答案， answer[j] 应等于第 j 次查询 queries[j] 的结果，1 表示照亮，0 表示未照亮。
 * <p>
 * 1 <= n <= 10^9
 * 0 <= lamps.length <= 20000
 * 0 <= queries.length <= 20000
 * lamps[i].length == 2
 * 0 <= rowi, coli < n
 * queries[j].length == 2
 * 0 <= rowj, colj < n
 */
public class P1001_GridIllumination {
    public static void main(String[] args) {
        int[][] lamps = new int[][]{{0, 0}, {4, 4}};
        int[][] queries = new int[][]{{1, 1}, {1, 0}};
        int[][] lamps1 = new int[][]{{0, 0}, {4, 4}};
        int[][] queries1 = new int[][]{{1, 1}, {1, 1}};
        int[][] lamps2 = new int[][]{{0, 0}, {0, 4}};
        int[][] queries2 = new int[][]{{0, 4}, {0, 1}, {1, 4}};

        int[][] lamps3 = new int[][]{{0, 0}, {1, 0}};
        int[][] queries3 = new int[][]{{1, 1}, {1, 1}};

        P1001_Solution solution = new P1001_Solution();
        System.out.println(Arrays.toString(solution.gridIllumination(5, lamps, queries))); // [1, 0]
        System.out.println(Arrays.toString(solution.gridIllumination(5, lamps1, queries1))); // [1, 1]
        System.out.println(Arrays.toString(solution.gridIllumination(5, lamps2, queries2))); // [1, 1, 0]
        System.out.println(Arrays.toString(solution.gridIllumination(5, lamps3, queries3))); // [1, 0]
    }
}

// n = 10000 超出内存限制
class P1001_Solution1 {
    final int[] dx = new int[]{-1, -1, -1, 0, 0, 0, 1, 1, 1};
    final int[] dy = new int[]{-1, 0, 1, -1, 0, 1, -1, 0, 1};

    public int[] gridIllumination(int n, int[][] lamps, int[][] queries) {
        int ln = lamps.length;
        int qn = queries.length;
        if (qn == 0 || ln == 0) {
            return new int[qn];
        }
        int[][] grid = new int[n][n];
        // 保存 lamp 光源
        HashSet<Integer> hashSet = new HashSet<>();
        // 照亮开灯
        for (int[] lamp : lamps) {
            // 重复的灯源跳过不添加
            if (!hashSet.contains(lamp[0] * n + lamp[1])) {
                turnOnOrTurnOff(n, grid, lamp[0], lamp[1], 1);
                // 将灯源添加进 hashSet
                hashSet.add(lamp[0] * n + lamp[1]);
            }
        }
        // 照亮开灯后的 网格
        System.out.println("照亮开灯后的网格 : ");
        for (int[] gri : grid) {
            System.out.println(Arrays.toString(gri));
        }

        int[] ans = new int[qn];

        // 查询关灯
        for (int i = 0; i < qn; i++) {
            int row = queries[i][0];
            int col = queries[i][1];
            if (grid[row][col] > 0) {
                ans[i] = 1;
            }
            // 九宫格
            for (int j = 0; j < 9; j++) {
                int x = row + dx[j];
                int y = col + dy[j];
                // (x, y)在网格内，并且是光源
                if (x >= 0 && x < n && y >= 0 && y < n && hashSet.contains(x * n + y)) {
                    turnOnOrTurnOff(n, grid, x, y, -1);
                    hashSet.remove(x * n + y);
                }
            }
            // 查询关灯后的 网格
            System.out.println("查询关灯后的网格 : ");
            for (int[] gri : grid) {
                System.out.println(Arrays.toString(gri));
            }
        }
        return ans;
    }

    private void turnOnOrTurnOff(int n, int[][] grid, int row, int col, int flag) {
        if (flag < -1 || flag > 1 || flag == 0) {
            throw new IllegalArgumentException("flag must be 1 or -1");
        }
        // 行和列
        for (int i = 0; i < n; i++) {
            grid[row][i] += flag;
            grid[i][col] += flag;
        }
        // 左上到右下 对角线
        for (int i = row - Math.min(row, col), j = col - Math.min(row, col); i < n && j < n; i++, j++) {
            grid[i][j] += flag;
        }
        // 左下到右上 对角线
        for (int i = row, j = col; i < n && j >= 0; i++, j--) {
            grid[i][j] += flag;
        }
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            grid[i][j] += flag;
        }
        // lamp 处被自加了 4 次, 最后需要减 3
        grid[row][col] -= 3 * flag;
    }
}

// n = 10000 超出内存限制
class P1001_Solution1_2 {
    final int[] dx = new int[]{-1, -1, -1, 0, 0, 0, 1, 1, 1};
    final int[] dy = new int[]{-1, 0, 1, -1, 0, 1, -1, 0, 1};

    public int[] gridIllumination(int n, int[][] lamps, int[][] queries) {
        int ln = lamps.length;
        int qn = queries.length;
        if (qn == 0 || ln == 0) {
            return new int[qn];
        }
        int[][] grid = new int[n][n];
        // 保存 lamp 光源
        HashSet<Integer> hashSet = new HashSet<>();
        // 照亮开灯
        for (int[] lamp : lamps) {
            if (!hashSet.contains(lamp[0] * n + lamp[1])) {
                turnOnOrTurnOff(n, grid, lamp[0], lamp[1], 1);
                hashSet.add(lamp[0] * n + lamp[1]);
            }
        }
        // 照亮开灯后的 网格
        System.out.println("照亮开灯后的网格 : ");
        for (int[] gri : grid) {
            System.out.println(Arrays.toString(gri));
        }

        int[] ans = new int[qn];

        // 查询关灯
        for (int i = 0; i < qn; i++) {
            int row = queries[i][0];
            int col = queries[i][1];
            if (grid[row][col] > 0) {
                ans[i] = 1;
            }
            for (int j = 0; j < 9; j++) {
                int x = row + dx[j];
                int y = col + dy[j];
                if (x >= 0 && x < n && y >= 0 && y < n && grid[x][y] >> 16 == 1) {
                    turnOnOrTurnOff(n, grid, x, y, -1);
                }
            }
            // 查询关灯后的 网格
            System.out.println("查询关灯后的网格 : ");
            for (int[] gri : grid) {
                System.out.println(Arrays.toString(gri));
            }
        }
        return ans;
    }

    private void turnOnOrTurnOff(int n, int[][] grid, int row, int col, int flag) {
        if (flag < -1 || flag > 1 || flag == 0) {
            throw new IllegalArgumentException("flag must be 1 or -1");
        }
        // 行和列
        for (int i = 0; i < n; i++) {
            grid[row][i] += flag;
            grid[i][col] += flag;
        }
        // 左上到右下 对角线
        for (int i = row - Math.min(row, col), j = col - Math.min(row, col); i < n && j < n; i++, j++) {
            grid[i][j] += flag;
        }
        // 左下到右上 对角线
        for (int i = row, j = col; i < n && j >= 0; i++, j--) {
            grid[i][j] += flag;
        }
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            grid[i][j] += flag;
        }
        // lamp 处被加或减了 4 次, 先回到最初状态, 不加不减, 再将第 16 位置为 1 或 0;
        // grid[row][col] ^= (1 << 2); // 等价于 +- 4
        grid[row][col] -= 4 * flag;
        grid[row][col] ^= (1 << 16);
    }
}

// n = 1000000000 超出内存限制
class P1001_Solution2 {
    int n;
    int[] hang;
    int[] lie;
    int[] zheng;
    int[] fan;
    final int[] dx = new int[]{-1, -1, -1, 0, 0, 0, 1, 1, 1};
    final int[] dy = new int[]{-1, 0, 1, -1, 0, 1, -1, 0, 1};

    public int[] gridIllumination(int n, int[][] lamps, int[][] queries) {
        int ln = lamps.length;
        int qn = queries.length;
        if (qn == 0 || ln == 0) {
            return new int[qn];
        }
        this.n = n;
        hang = new int[n];
        lie = new int[n];
        zheng = new int[2 * n - 1];
        fan = new int[2 * n - 1];
        int row, col;
        // 记录 lamp 光源
        HashSet<Integer> hashSet = new HashSet<>();
        // 照亮开灯
        for (int[] lamp : lamps) {
            row = lamp[0];
            col = lamp[1];
            // 已添加过的光源不再添加
            if (!hashSet.contains(row * n + col)) {
                // flag == 1, 记录光源四个方向的灯泡都亮
                turnOnOrTurnOff(row, col, 1);
                // 记录光源
                hashSet.add(row * n + col);
            }
        }
        // 照亮开灯后的 网格
        System.out.println("照亮开灯后的网格 : ");
        System.out.println(Arrays.toString(hang));
        System.out.println(Arrays.toString(lie));
        System.out.println(Arrays.toString(zheng));
        System.out.println(Arrays.toString(fan));

        int[] ans = new int[qn];

        // 查询关灯
        for (int i = 0; i < qn; i++) {
            row = queries[i][0];
            col = queries[i][1];
            // 是亮着的, ans[i] = 1;
            if (queryLamp(row, col)) {
                ans[i] = 1;
            }
            // 消除九宫格中的光源, 非光源不能消除
            for (int j = 0; j < 9; j++) {
                int x = row + dx[j];
                int y = col + dy[j];
                if (x >= 0 && x < n && y >= 0 && y < n && hashSet.contains(x * n + y)) {
                    turnOnOrTurnOff(x, y, -1);
                    hashSet.remove(x * n + y);
                }
            }
            // 查询关灯后的 网格
            System.out.println("查询关灯后的网格 : ");
            System.out.println(Arrays.toString(hang));
            System.out.println(Arrays.toString(lie));
            System.out.println(Arrays.toString(zheng));
            System.out.println(Arrays.toString(fan));
        }
        return ans;
    }

    private void turnOnOrTurnOff(int row, int col, int flag) {
        if (flag < -1 || flag > 1 || flag == 0) {
            throw new IllegalArgumentException("flag must be 1 or -1");
        }
        hang[row] += flag;
        lie[col] += flag;
        zheng[row + col] += flag;
        fan[n - 1 - (row - col)] += flag;
    }

    private boolean queryLamp(int row, int col) {
        return hang[row] > 0 || lie[col] > 0 || zheng[row + col] > 0 || fan[n - 1 - (row - col)] > 0;
    }
}

// 执行用时：61 ms, 在所有 Java 提交中击败了 98.36% 的用户
// 内存消耗：48.9 MB, 在所有 Java 提交中击败了 100.00% 的用户
// 通过测试用例：45 / 45
class P1001_Solution3 {
    int n;
    HashSet<Integer> lampsRecorder;
    HashMap<Integer, Integer> rowRecorder;
    HashMap<Integer, Integer> colRecorder;
    HashMap<Integer, Integer> positiveRecorder;
    HashMap<Integer, Integer> negativeRecorder;
    final int[] dx = new int[]{-1, -1, -1, 0, 0, 0, 1, 1, 1};
    final int[] dy = new int[]{-1, 0, 1, -1, 0, 1, -1, 0, 1};

    public int[] gridIllumination(int n, int[][] lamps, int[][] queries) {
        int ln = lamps.length;
        int qn = queries.length;
        if (qn == 0 || ln == 0) {
            return new int[qn];
        }
        this.n = n;
        // 记录 lamp 光源
        lampsRecorder = new HashSet<>();
        rowRecorder = new HashMap<>();
        colRecorder = new HashMap<>();
        positiveRecorder = new HashMap<>();
        negativeRecorder = new HashMap<>();
        int row, col;
        // 照亮开灯
        for (int[] lamp : lamps) {
            row = lamp[0];
            col = lamp[1];
            // 已添加过的光源不再添加
            if (!lampsRecorder.contains(row * n + col)) {
                // flag == 1, 记录光源四个方向的灯泡都亮
                turnOnOrTurnOff(row, col, 1);
                // 记录光源
                lampsRecorder.add(row * n + col);
            }
        }
        // 照亮开灯后
        myPrint("照亮开灯后的 lampsRecorder 和四个 hashMap : ");

        int[] ans = new int[qn];

        // 查询关灯
        for (int i = 0; i < qn; i++) {
            row = queries[i][0];
            col = queries[i][1];
            // 是亮着的, ans[i] = 1;
            if (queryLamp(row, col)) {
                ans[i] = 1;
            }
            // 消除九宫格中的光源, 非光源不能消除
            for (int j = 0; j < 9; j++) {
                int x = row + dx[j];
                int y = col + dy[j];
                if (x >= 0 && x < n && y >= 0 && y < n && lampsRecorder.contains(x * n + y)) {
                    // flag == -1, 将该光源四个方向的灯泡都熄灭
                    turnOnOrTurnOff(x, y, -1);
                    // 将该光源从记录中移除
                    lampsRecorder.remove(x * n + y);
                }
            }
            // 查询关灯后
            myPrint("查询(" + row + ", " + col + ")关灯后的 lampsRecorder 和四个 hashMap : ");
        }
        return ans;
    }

    private void turnOnOrTurnOff(int row, int col, int flag) {
        if (flag < -1 || flag > 1 || flag == 0) {
            throw new IllegalArgumentException("flag must be 1 or -1");
        }
        rowRecorder.put(row, rowRecorder.getOrDefault(row, 0) + flag);
        colRecorder.put(col, colRecorder.getOrDefault(col, 0) + flag);
        positiveRecorder.put(row + col, positiveRecorder.getOrDefault(row + col, 0) + flag);
        negativeRecorder.put(n - 1 - (row - col), negativeRecorder.getOrDefault(n - 1 - (row - col), 0) + flag);
    }

    private boolean queryLamp(int row, int col) {
        // 只判断是否包含 key 会出现错误, 因为可能以下四个 key 的对应值为 0, 本身 (row, col) 是不亮的, 但由于存在 key, 返回为 true
        // 只判断 key 对应的值是否大于 0 会出现空指针错误, 因为 key 可能根本就不存在
//        return rowRecorder.containsKey(row)
//                || colRecorder.containsKey(col)
//                || positiveRecorder.containsKey(row + col)
//                || negativeRecorder.containsKey(n - 1 - (row - col));

        // 故首先要判断 key 是否存在, 再判断 key 对应处的 value 值是否大于 0
        return (rowRecorder.containsKey(row) && rowRecorder.get(row) > 0)
                || (colRecorder.containsKey(col) && colRecorder.get(col) > 0)
                || (positiveRecorder.containsKey(row + col) && positiveRecorder.get(row + col) > 0)
                || (negativeRecorder.containsKey(n - 1 - (row - col)) && negativeRecorder.get(n - 1 - (row - col)) > 0);
    }

    private void myPrint(String str) {
        System.out.println(str);
        System.out.println(lampsRecorder);
        System.out.println(rowRecorder);
        System.out.println(colRecorder);
        System.out.println(positiveRecorder);
        System.out.println(negativeRecorder);
    }
}

// 尝试一下效率能否更快, 发现并不
class P1001_Solution {
    int n;
    // 记录 lamp 光源
    HashSet<Integer> lampsRecorder;
    // 记录 lamp 光源所在 行 亮, key 为光源行下标 row(0, n-1), value 为该行光源数
    HashMap<Integer, Integer> rowRecorder;
    // 记录 lamp 光源所在 列 亮, key 为光源列下标 col(0, n-1), value 为该列光源数
    HashMap<Integer, Integer> colRecorder;
    // 记录 lamp 光源所在 正对角线 亮, key 为光源正对角线下标 (row + col)(0, 2n-1), value 为该正对角线光源数
    HashMap<Integer, Integer> positiveRecorder;
    // 记录 lamp 光源所在 反对角线 亮, key 为光源反对角线下标 (col - row)(-(n-1), n-1), value 为该反对角线光源数
    HashMap<Integer, Integer> negativeRecorder;
    // 九宫格各坐标与 (row, col) 的关系
    final int[] dx = new int[]{-1, -1, -1, 0, 0, 0, 1, 1, 1};
    final int[] dy = new int[]{-1, 0, 1, -1, 0, 1, -1, 0, 1};

    public int[] gridIllumination(int n, int[][] lamps, int[][] queries) {
        int ln = lamps.length;
        int qn = queries.length;
        if (qn == 0 || ln == 0) {
            return new int[qn];
        }
        this.n = n;
        lampsRecorder = new HashSet<>();
        rowRecorder = new HashMap<>();
        colRecorder = new HashMap<>();
        positiveRecorder = new HashMap<>();
        negativeRecorder = new HashMap<>();
        // 结果数组
        int[] ans = new int[qn];
        // 行, 列下标
        int row, col;
        // 1.照亮开灯
        for (int[] lamp : lamps) {
            row = lamp[0];
            col = lamp[1];
            // 已添加过的光源不再添加, 只添加未添加过的光源
            if (!lampsRecorder.contains(row * n + col)) {
                // 记录光源四个方向的灯泡都亮
                turnOn(row, col);
                // 记录光源
                lampsRecorder.add(row * n + col);
            }
        }
        // 照亮开灯后
        // myPrint("照亮开灯后的 lampsRecorder 和四个 hashMap : ");

        // 2.查询关灯
        for (int i = 0; i < qn; i++) {
            row = queries[i][0];
            col = queries[i][1];
            // 如果返回为 true, 说明 (row, col) 是亮着的, ans[i] = 1
            if (queryLamp(row, col)) {
                ans[i] = 1;
            }
            // 消除九宫格中的光源, 非光源不能消除, 跳过
            for (int j = 0; j < 9; j++) {
                int x = row + dx[j];
                int y = col + dy[j];
                // 在网格内, 且 (x, y) 处是光源
                if (x >= 0 && x < n && y >= 0 && y < n && lampsRecorder.contains(x * n + y)) {
                    // 将该光源四个方向的灯泡都熄灭
                    turnOff(x, y);
                    // 将该光源从记录中移除
                    lampsRecorder.remove(x * n + y);
                }
            }
            // 查询关灯后
            // myPrint("查询(" + row + ", " + col + ")关灯后的 lampsRecorder 和四个 hashMap : ");
        }
        return ans;
    }

    // 开灯
    private void turnOn(int row, int col) {
        rowRecorder.put(row, rowRecorder.getOrDefault(row, 0) + 1);
        colRecorder.put(col, colRecorder.getOrDefault(col, 0) + 1);
        positiveRecorder.put(row + col, positiveRecorder.getOrDefault(row + col, 0) + 1);
        negativeRecorder.put(col - row, negativeRecorder.getOrDefault(col - row, 0) + 1);
    }

    // 关灯
    private void turnOff(int row, int col) {
        int rVal = rowRecorder.getOrDefault(row, 0);
        int cVal = colRecorder.getOrDefault(col, 0);
        int pVal = positiveRecorder.getOrDefault(row + col, 0);
        int nVal = negativeRecorder.getOrDefault(col - row, 0);
        // 当是在移除最后一个光源时, 可以直接移除该 key, 而不是 put(key,0)
        if (rVal == 1) {
            rowRecorder.remove(row);
        } else {
            rowRecorder.put(row, rVal - 1);
        }
        if (cVal == 1) {
            colRecorder.remove(col);
        } else {
            colRecorder.put(col, cVal - 1);
        }
        if (pVal == 1) {
            positiveRecorder.remove(row + col);
        } else {
            positiveRecorder.put(row + col, pVal - 1);
        }
        if (nVal == 1) {
            negativeRecorder.remove(col - row);
        } else {
            negativeRecorder.put(col - row, nVal - 1);
        }
    }

    // 此处就只需要判断 key 是否存在就可以了, 不需要判断 key 对应的 value 值是否大于 0, 来确定 (row, col) 是否亮
    // 因为在 关灯后, 如果某个方向无光源了, 该方向的下标 key 就从相应的 hashMap 中移除了, 而不是将其值变成了 0
    private boolean queryLamp(int row, int col) {
        return rowRecorder.containsKey(row)
                || colRecorder.containsKey(col)
                || positiveRecorder.containsKey(row + col)
                || negativeRecorder.containsKey(col - row);
    }

    // 打印
    private void myPrint(String str) {
        System.out.println(str);
        System.out.println(lampsRecorder);
        System.out.println(rowRecorder);
        System.out.println(colRecorder);
        System.out.println(positiveRecorder);
        System.out.println(negativeRecorder);
    }
}