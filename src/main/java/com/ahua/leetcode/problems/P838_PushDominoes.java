package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-21 20:30
 */

/**
 * 838. 推多米诺 push-dominoes
 * n 张多米诺骨牌排成一行，将每张多米诺骨牌垂直竖立。在开始时，同时把一些多米诺骨牌向左或向右推。
 * <p>
 * 每过一秒，倒向左边的多米诺骨牌会推动其左侧相邻的多米诺骨牌。
 * 同样地，倒向右边的多米诺骨牌也会推动竖立在其右侧的相邻多米诺骨牌。
 * <p>
 * 如果一张垂直竖立的多米诺骨牌的两侧同时有多米诺骨牌倒下时，由于受力平衡， 该骨牌仍然保持不变。
 * <p>
 * 就这个问题而言，我们会认为一张正在倒下的多米诺骨牌不会对其它正在倒下或已经倒下的多米诺骨牌施加额外的力。
 * <p>
 * 给你一个字符串 dominoes 表示这一行多米诺骨牌的初始状态，其中：
 * <p>
 * dominoes[i] = 'L'，表示第 i 张多米诺骨牌被推向左侧，
 * dominoes[i] = 'R'，表示第 i 张多米诺骨牌被推向右侧，
 * dominoes[i] = '.'，表示没有推动第 i 张多米诺骨牌。
 * 返回表示最终状态的字符串。
 * <p>
 * n == dominoes.length
 * 1 <= n <= 105
 * dominoes[i] 为 'L'、'R' 或 '.'
 */
public class P838_PushDominoes {
    public static void main(String[] args) {
        String dominoes = "RR.L";
        String dominoes1 = ".L.R...LR..L..";
        String dominoes2 = ".L.R.";
        String dominoes3 = ".R..L.";
        String dominoes4 = ".L.R...LR..L..";
        P838_Solution solution = new P838_Solution();
        System.out.println(solution.pushDominoes(dominoes)); // "RR.L"
        System.out.println(solution.pushDominoes(dominoes1)); // "LL.RR.LLRRLL.."
        System.out.println(solution.pushDominoes(dominoes2)); // "LL.RR"
        System.out.println(solution.pushDominoes(dominoes3)); // ".RRLL."
        System.out.println(solution.pushDominoes(dominoes4)); // "LL.RR.LLRRLL.."
    }
}

// 基本操作 双指针
// O(N) O(1)
class P838_Solution {
    // 8 ms 100%
    // 42.5 MB 27.47%
    public String pushDominoes1(String dominoes) {
        char[] dominoesArr = dominoes.toCharArray();
        int n = dominoesArr.length;
        // 必须置为这两个初始值
        int left = -1;
        int right = -2;
        for (int i = 0; i < n; i++) {
            if (dominoesArr[i] == 'L') {
                if (left > right) {
                    for (int l = left + 1; l < i; l++) {
                        dominoesArr[l] = 'L';
                    }
                } else {
                    for (int r = right + 1, l = i - 1; r < l; r++, l--) {
                        dominoesArr[r] = 'R';
                        dominoesArr[l] = 'L';
                    }
                }
                left = i;
            } else if (dominoesArr[i] == 'R') {
                if (left < right) {
                    for (int r = right + 1; r < i; r++) {
                        dominoesArr[r] = 'R';
                    }
                }
                right = i;
            }
        }
        if (left < right) {
            for (int r = right + 1; r < n; r++) {
                dominoesArr[r] = 'R';
            }
        }
        return new String(dominoesArr);
    }

    // 整理优化代码, 提取复用代码为方法
    // 9 ms 97.30%
    // 42.5 MB 25.67%
    public String pushDominoes(String dominoes) {
        char[] dominoesArr = dominoes.toCharArray();
        int n = dominoesArr.length;
        // left 表示为 'L' 的下标, right 表示为 'R' 的下标, 初始化时需满足 right < left, left = -1
        int left = -1;
        int right = -2;
        for (int i = 0; i < n; i++) {
            if (dominoesArr[i] == 'L') {
                // 连续两个 L , 从后一个(当前) L 处向左倒
                if (left > right) {
                    pushOneSide(dominoesArr, left, i, 'L');
                } else {
                    // 前为 R, 后为 L(当前个为 L), 两边往中间倒
                    pushTwoSide(dominoesArr, right, i);
                }
                left = i;
            } else if (dominoesArr[i] == 'R') {
                // 连续两个 R , 从前一个 R 处向右倒
                if (left < right) {
                    pushOneSide(dominoesArr, right, i, 'R');
                }
                right = i;
            }
        }
        // 可能最后几块要往右边倒, 比如这个例子 ".L.R..."
        if (left < right) {
            pushOneSide(dominoesArr, right, n, 'R');
        }
        return new String(dominoesArr);
    }

    // 往左边或者右边倒
    private void pushOneSide(char[] dominoesArr, int left, int right, char ch) {
        for (int i = left + 1; i < right; i++) {
            dominoesArr[i] = ch;
        }
    }

    // 两边往中间倒
    private void pushTwoSide(char[] dominoesArr, int left, int right) {
        for (int i = left + 1, j = right - 1; i < j; i++, j--) {
            dominoesArr[i] = 'R';
            dominoesArr[j] = 'L';
        }
    }
}