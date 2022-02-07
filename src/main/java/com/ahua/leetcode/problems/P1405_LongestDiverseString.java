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

class P1405_Solution {

    static final int A = 0;
    static final int B = 1;
    static final int C = 2;
    int[] abcNum;

    public String longestDiverseString(int a, int b, int c) {
        if (a == 0 && b == 0 && c == 0) {
            return "";
        }
        this.abcNum = new int[]{a, b, c};
        final String[] str = new String[]{"a", "b", "c", "aa", "bb", "cc"};
        StringBuilder sb = new StringBuilder("");
        int addStrIndex = getStrIndex(-1);
        while (addStrIndex != -1) {
            sb.append(str[addStrIndex]);
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
}