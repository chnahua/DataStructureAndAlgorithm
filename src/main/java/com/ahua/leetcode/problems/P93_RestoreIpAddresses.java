package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2021-11-12 14:40
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个只包含数字的字符串 s ，用以表示一个 IP 地址，返回所有可能从 s 获得的 有效 IP 地址 。你可以按 任何 顺序返回答案。
 * <p>
 * 有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。
 * <p>
 * 例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，但是 "0.011.255.245"、"192.168.1.312" 和 "192.168@1.1" 是 无效 IP 地址。
 * <p>
 * 0 <= s.length <= 3000
 * s 仅由数字组成
 */

public class P93_RestoreIpAddresses {
    public static void main(String[] args) {
        String s = "25525511135";
        String s1 = "0000";
        String s2 = "1111";
        String s3 = "010010";
        String s4 = "101023";
        String s5 = "000256";
        P93_Solution solution = new P93_Solution();
        System.out.println(solution.restoreIpAddresses(s));
        System.out.println(solution.restoreIpAddresses(s1));
        System.out.println(solution.restoreIpAddresses(s2));
        System.out.println(solution.restoreIpAddresses(s3));
        System.out.println(solution.restoreIpAddresses(s4));
        System.out.println(solution.restoreIpAddresses(s5));
    }
}

// 回溯
class P93_Solution {
    static final int SEG_COUNT = 4;
    int len;
    int[] segments = new int[SEG_COUNT];
    List<String> ans;
    // List<String> ans = new ArrayList<>();

    public List<String> restoreIpAddresses(String s) {
        ans = new ArrayList<>();
        if (s == null) {
            return ans;
        }
        this.len = s.length();
        if (len < 4 || len > 12) {
            return ans;
        }

        if (len == 4) {
            StringBuilder ipAddr = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                ipAddr.append(s.charAt(i));
                if (i <= 2) {
                    ipAddr.append('.');
                }
            }
            ans.add(ipAddr.toString());
            return ans;
        }

        dfs(s, 0, 0);
        return ans;
    }

    public void dfs(String s, int segId, int segStart) {
        // 如果找到了 4 段 IP 地址并且遍历完了字符串，那么就是一种答案
        if (segId == SEG_COUNT) {
            if (segStart == len) {
                StringBuilder ipAddr = new StringBuilder();
                for (int i = 0; i < SEG_COUNT; i++) {
                    ipAddr.append(segments[i]);
                    if (i <= 2) {
                        ipAddr.append('.');
                    }
                }
                ans.add(ipAddr.toString());
            }
            return;
        }

        // 如果还没有找到 4 段 IP 地址就已经遍历完了字符串, 那么提前回溯
        if (segStart == len) {
            return;
        }

        // 删除此 if 不影响结果
        if (len - segStart > (SEG_COUNT - segId) * 3) {
            return;
        } else if (len - segStart == (SEG_COUNT - segId) * 3) {
            for (int i = 0; i < SEG_COUNT - segId; i++) {
                int temp = s.charAt(segStart + i * 3) - '0';
                if (temp == 0 || temp > 2) {
                    return;
                }
                if (temp == 2) {
                    int temp1 = s.charAt(segStart + i * 3 + 1) - '0';
                    if (temp1 >= 6) {
                        return;
                    }
                    if (temp1 == 5 && (s.charAt(segStart + i * 3 + 2) - '0') >= 6) {
                        return;
                    }
                }
            }
            StringBuilder ipAddr = new StringBuilder();
            for (int i = 0; i < SEG_COUNT; i++) {
                if (i < segId) {
                    ipAddr.append(segments[i]);
                } else {
                    ipAddr.append(s.substring(segStart, segStart + 3));
                }
                if (i <= 2) {
                    ipAddr.append('.');
                }
            }
            ans.add(ipAddr.toString());
            return;
        }

        // 由于不能有前导零，如果当前数字为 0，那么这一段 IP 地址只能为 0
        if (s.charAt(segStart) == '0') {
            segments[segId] = 0;
            dfs(s, segId + 1, segStart + 1);
        }

        // 一般情况，枚举每一种可能性并递归
        int addr = 0;
        for (int i = segStart; i < len; i++) {
            addr = addr * 10 + (s.charAt(i) - '0');
            if (addr > 0 && addr <= 0xFF) {
                segments[segId] = addr;
                dfs(s, segId + 1, i + 1);
            } else {
                break;
            }
        }
    }
}

// 回溯
class P93_Solution1 {
    static final int SEG_COUNT = 4;
    int len;
    int[] segments = new int[SEG_COUNT];
    List<String> ans;
    // List<String> ans = new ArrayList<>();

    public List<String> restoreIpAddresses(String s) {
        ans = new ArrayList<>();
        if (s == null) {
            return ans;
        }
        this.len = s.length();
        if (len < 4 || len > 12) {
            return ans;
        }

        if (len == 4) {
            StringBuilder ipAddr = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                ipAddr.append(s.charAt(i));
                if (i <= 2) {
                    ipAddr.append('.');
                }
            }
            ans.add(ipAddr.toString());
            return ans;
        }

        dfs(s, 0, 0);
        return ans;
    }

    public void dfs(String s, int segId, int segStart) {
        // 如果找到了 4 段 IP 地址并且遍历完了字符串，那么就是一种答案
        if (segId == SEG_COUNT) {
            if (segStart == len) {
                StringBuilder ipAddr = new StringBuilder();
                for (int i = 0; i < SEG_COUNT; i++) {
                    ipAddr.append(segments[i]);
                    if (i <= 2) {
                        ipAddr.append('.');
                    }
                }
                ans.add(ipAddr.toString());
            }
            return;
        }

        // 如果还没有找到 4 段 IP 地址就已经遍历完了字符串, 那么提前回溯
        if (segStart == len) {
            return;
        }
/*
        // 删除此 if 不影响结果
        if (len - segStart > (SEG_COUNT - segId) * 3) {
            return;
        } else if (len - segStart == (SEG_COUNT - segId) * 3) {
            for (int i = 0; i < SEG_COUNT - segId; i++) {
                int temp = s.charAt(segStart + i * 3) - '0';
                if (temp == 0 || temp > 2) {
                    return;
                }
                if (temp == 2) {
                    int temp1 = s.charAt(segStart + i * 3 + 1) - '0';
                    if (temp1 >= 6) {
                        return;
                    }
                    if (temp1 == 5 && (s.charAt(segStart + i * 3 + 2) - '0') >= 6) {
                        return;
                    }
                }
            }
            StringBuilder ipAddr = new StringBuilder();
            for (int i = 0; i < SEG_COUNT; i++) {
                if (i < segId) {
                    ipAddr.append(segments[i]);
                } else {
                    ipAddr.append(s.substring(segStart, segStart + 3));
                }
                if (i <= 2) {
                    ipAddr.append('.');
                }
            }
            ans.add(ipAddr.toString());
            return;
        }
*/
        // 由于不能有前导零，如果当前数字为 0，那么这一段 IP 地址只能为 0
        if (s.charAt(segStart) == '0') {
            segments[segId] = 0;
            dfs(s, segId + 1, segStart + 1);
        }

        // 一般情况，枚举每一种可能性并递归
        int addr = 0;
        for (int i = segStart; i < len; i++) {
            addr = addr * 10 + (s.charAt(i) - '0');
            if (addr > 0 && addr <= 0xFF && (len - segStart <= (SEG_COUNT - segId) * 3)) {
                segments[segId] = addr;
                dfs(s, segId + 1, i + 1);
            } else {
                break;
            }
        }
    }
}