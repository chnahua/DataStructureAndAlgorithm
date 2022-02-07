package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-02-07 21:43
 */

/**
 * 1405. 最长快乐字符串 longest-happy-string
 * 如果字符串中不含有任何 'aaa'，'bbb' 或 'ccc' 这样的字符串作为子串，那么该字符串就是一个「快乐字符串」。
 * <p>
 * 给你三个整数 a，b ，c，请你返回 任意一个 满足下列全部条件的字符串 s：
 * <p>
 * s 是一个尽可能长的快乐字符串。
 * s 中 最多 有a 个字母 'a'、b 个字母 'b'、c 个字母 'c' 。
 * s 中只含有 'a'、'b' 、'c' 三种字母。
 * 如果不存在这样的字符串 s ，请返回一个空字符串 ""。
 * <p>
 * 0 <= a, b, c <= 100
 * a + b + c > 0
 */
public class P1405_LongestDiverseString {
    public static void main(String[] args) {
        P1405_Solution solution = new P1405_Solution();
        System.out.println(solution.longestDiverseString(1, 1, 7)); // "ccaccbcc" "ccbccacc"
        System.out.println(solution.longestDiverseString(2, 2, 1)); // "aabbc"
        System.out.println(solution.longestDiverseString(7, 1, 0)); // "aabaa"
        System.out.println(solution.longestDiverseString(0, 8, 11)); // "ccbccbbccbbccbbccbc"
    }
}

// 不知道采取了何种方法, 看了官方题解后, 原来是贪心
// 0ms 100%
class P1405_Solution {
    int[] abcNum;
    // 对应于 abcNum 中保存的 a,b,c 的下标
    static final int A = 0;
    static final int B = 1;
    static final int C = 2;

    public String longestDiverseString(int a, int b, int c) {
        // 可删除
        if (a == 0 && b == 0 && c == 0) {
            return "";
        }
        this.abcNum = new int[]{a, b, c};
        final String[] str = new String[]{"a", "b", "c", "aa", "bb", "cc"};
        StringBuilder sb = new StringBuilder();
        int addStrIndex = getStrIndex(-1);
        while (addStrIndex != -1) {
            // 添加所剩字符最多的一个或者两个字符到末尾
            sb.append(str[addStrIndex]);
            // 得到下一次添加到末尾的字符是哪一种, 是一个还是两个
            // pre 表示上一次添加的是 abc 中的哪种字符(下标表示), 函数返回下一次要添加的字符以及个数(对应于 str 数组的索引)
            addStrIndex = getStrIndex(addStrIndex % 3);
        }
        return sb.toString();
    }

    private int getStrIndex1(int pre) {
        int a = abcNum[A];
        int b = abcNum[B];
        int c = abcNum[C];
        int max = -1;
        int mid = -1;
        if (a == b && a == c && a == 0) {
            return -1;
        }
        // a b c 的大小关系
        if (a >= b && a >= c) {
            // a 最大且 a 不等于 0
            max = A;
            if (max != pre) {
                if (a >= 2) {
                    abcNum[max] -= 2;
                    return max + 3;
                } else if (a == 1) {
                    abcNum[max]--;
                    return max;
                }
            }
            if (b >= c) {
                // a >= b >= c
                mid = B;
            } else {
                // a >= c >= b
                mid = C;
            }
        } else if (b >= a && b >= c) {
            // b 最大且 b 不等于 0
            max = B;
            if (max != pre) {
                if (b >= 2) {
                    abcNum[max] -= 2;
                    return max + 3;
                } else if (b == 1) {
                    abcNum[max]--;
                    return max;
                }
            }
            if (a >= c) {
                // b >= a >= c
                mid = A;
            } else {
                // b >= c >= a
                mid = C;
            }
        } else if (c >= a && c >= b) {
            // c 最大且 c 不等于 0
            max = C;
            if (max != pre) {
                if (c >= 2) {
                    abcNum[max] -= 2;
                    return max + 3;
                } else if (c == 1) {
                    abcNum[max]--;
                    return max;
                }
            }
            if (a >= b) {
                // c >= a >= b
                mid = A;
            } else {
                // c >= b >= a
                mid = B;
            }
        }
        // max == pre 时
        if (abcNum[mid] > 2) {
            if (abcNum[mid] * 2 < abcNum[max]) {
                abcNum[mid]--;
                return mid;
            } else {
                abcNum[mid] -= 2;
                return mid + 3;
            }
        } else if (abcNum[mid] == 1 || abcNum[mid] == 2) {
            // 此处当 abcNum[mid] == 2 时, 不要一次添加两个字符, 否则会出错(0,8,11)、(0,2,4)、(0,2,3)
            abcNum[mid]--;
            return mid;
        } else {
            return -1;
        }
    }

    // 代码逻辑优化
    private int getStrIndex(int pre) {
        int a = abcNum[A];
        int b = abcNum[B];
        int c = abcNum[C];
        // 最多字符和次多字符的位置(0,1,2)
        int max, mid;
        if (a == 0 && b == 0 && c == 0) {
            return -1;
        }
        // a b c 的大小关系
        if (a >= b && a >= c) {
            // a 最大且 a 不等于 0
            // a >= b >= c 或 a >= c >= b
            max = A;
            mid = b >= c ? B : C;
        } else if (b >= a && b >= c) {
            // b 最大且 b 不等于 0
            // b >= a >= c 或 b >= c >= a
            max = B;
            mid = a >= c ? A : C;
        } else {
            // c 最大且 c 不等于 0
            // c >= a >= b 或 c >= b >= a
            max = C;
            mid = a >= b ? A : B;
        }
        // 返回此次应当添加哪种字符以及添加几个(对应于 str 数组的索引)
        return func(max, mid, pre);
    }

    private int func(int max, int mid, int pre) {
        // max != pre 时, 表示当前最多字符与上一次添加的字符不是同一个字符, 那么接下来这次就可以添加这个现有字符最多的字符
        // 而添加一个还是两个该字符则由它的个数 abcNum[max] 决定
        if (max != pre) {
            if (abcNum[max] >= 2) {
                abcNum[max] -= 2;
                return max + 3;
            } else if (abcNum[max] == 1) {
                abcNum[max]--;
                return max;
            }
        }
        // max == pre 时 表示当前最多字符与上一次添加的字符是同一个字符, 那么此次就不能添加这个上次添加过的字符, 而应当添加次多的字符
        // 而添加一个还是两个该字符则由它的个数 abcNum[mid] 以及与最多字符的个数 abcNum[max] 之间的关系共同决定
        if (abcNum[mid] > 2) {
            // 这一步是关键, < 或者 <= 都正确
            // 如果最多字符的个数 abcNum[max] 比次多字符的个数的两倍都多, 那么就要减缓添加次多字符的速度, 即只添加一个
            // 至于为何这样, 我也没有一个理性的认识, 只是感觉如此
            if (abcNum[mid] * 2 < abcNum[max]) {
                abcNum[mid]--;
                return mid;
            } else {
                abcNum[mid] -= 2;
                return mid + 3;
            }
        } else if (abcNum[mid] == 1 || abcNum[mid] == 2) {
            // 此处当 abcNum[mid] == 2 时这种情况, 不要将其放到上个条件判断中
            // 在上个条件判断中, 在最后 'b' 字符个数为 2 时, 会一次添加两个字符, 这样就出错了
            // 错误案例(0,8,11)
            // (0,2,4)、(0,2,3) 这两个案例是最后的某种状态, 之前添加的是 'c', 现在应当添加 'b'
            // 而如果一次添加两个 'b', 最终 'c' 就会有剩余(未添加)
            abcNum[mid]--;
            return mid;
        } else {
            // 次多字符个数为 0, 返回 -1, 不能再添加了
            return -1;
        }
    }
}