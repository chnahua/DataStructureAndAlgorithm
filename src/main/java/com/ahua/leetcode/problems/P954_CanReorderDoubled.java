package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-04-01 22:15
 */

import java.util.Arrays;
import java.util.HashMap;

/**
 * 954. 二倍数对数组 array-of-doubled-pairs
 * 给定一个长度为偶数的整数数组 arr，只有对 arr 进行重组后可以满足
 * “对于每个 0 <= i < len(arr) / 2，都有 arr[2 * i + 1] = 2 * arr[2 * i]” 时，返回 true；否则，返回 false。
 * <p>
 * 0 <= arr.length <= 3 * 10^4
 * arr.length 是偶数
 * -10^5 <= arr[i] <= 10^5
 */
public class P954_CanReorderDoubled {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 1, 3, 6};
        int[] arr1 = new int[]{2, 1, 2, 6};
        int[] arr2 = new int[]{4, -2, 2, -4};
        int[] arr3 = new int[]{2, 4, 0, 0, 8, 1};
        int[] arr4 = new int[]{-6, -3};
        P954_Solution solution = new P954_Solution();
        System.out.println(solution.canReorderDoubled(arr)); // false
        System.out.println(solution.canReorderDoubled(arr1)); // false
        System.out.println(solution.canReorderDoubled(arr2)); // true
        System.out.println(solution.canReorderDoubled(arr3)); // true
        System.out.println(solution.canReorderDoubled(arr4)); // true
    }
}

// 我的解法一
// 排序 + 哈希表
// 能通过, 但是逻辑冗余了, 因为 arr 已经被我排序了, 所以在遍历某个元素时, 在它之前不会出现比它更大的元素, 因此可简化
class P954_Solution1 {
    // 32 ms 81.96%
    // 49 MB 13.91%
    public boolean canReorderDoubled1(int[] arr) {
        Arrays.sort(arr);
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int num : arr) {
            // num 为偶数时, 查看 hashMap 中是否存在 num/2
            if (num % 2 == 0) {
                Integer count1 = hashMap.get(num >> 1);
                if (count1 != null) {
                    if (count1 == 1) {
                        hashMap.remove(num >> 1);
                    } else {
                        hashMap.put(num >> 1, count1 - 1);
                    }
                    continue;
                }
            }
            // num 为偶数或奇数时, 查看 hashMap 中是否存在 num*2
            Integer count2 = hashMap.get(num << 1);
            if (count2 != null) {
                if (count2 == 1) {
                    hashMap.remove(num << 1);
                } else {
                    hashMap.put(num << 1, count2 - 1);
                }
                continue;
            }
            // hashMap 中没有 2*num, 也没有 num/2, 将该 num 添加进 hashMap 中(在 hashMap 中的次数加 1)
            hashMap.put(num, hashMap.getOrDefault(num, 0) + 1);
        }
        return hashMap.isEmpty();
    }

    // 29 ms 88.14%
    // 49.2 MB 6.70%
    public boolean canReorderDoubled(int[] arr) {
        Arrays.sort(arr);
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int num : arr) {
            // num 为偶数时, 查看 hashMap 中是否存在 num/2
            if (num % 2 == 0) {
                boolean isDoubled = check(hashMap, num >> 1);
                if (isDoubled) {
                    continue;
                }
            }

            // num 为偶数或奇数时, 查看 hashMap 中是否存在 num*2
            boolean isDoubled = check(hashMap, num << 1);
            if (isDoubled) {
                continue;
            }
            // hashMap 中没有 2*num, 也没有 num/2, 将该 num 添加进 hashMap 中(在 hashMap 中的次数加 1)
            hashMap.put(num, hashMap.getOrDefault(num, 0) + 1);
        }
        return hashMap.isEmpty();
    }

    private boolean check(HashMap<Integer, Integer> hashMap, int num) {
        Integer count = hashMap.get(num);
        if (count != null) {
            if (count == 1) {
                hashMap.remove(num);
            } else {
                hashMap.put(num, count - 1);
            }
            return true;
        }
        return false;
    }
}

// 我的解法二 逻辑优化
// 排序 + 哈希表
class P954_Solution {
    // 29 ms 88.14%
    // 49 MB 13.40%
    public boolean canReorderDoubled(int[] arr) {
        Arrays.sort(arr);
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int num : arr) {
            // num 为偶数或者小于 0 的奇数时, 查看 hashMap 中是否存在 num/2
            if (num % 2 == 0 || num < 0) {
                // 负数的一般是左移一位, 正数的一半是右移一位
                int key = num < 0 ? num << 1 : num >> 1;
                Integer count = hashMap.get(key);
                if (count != null) {
                    if (count == 1) {
                        hashMap.remove(key);
                    } else {
                        hashMap.put(key, count - 1);
                    }
                    continue;
                }
            }

            // num 为大于0的奇数或者 num 为偶数时 hashMap 中没有 num/2, 将该 num 添加进 hashMap 中(在 hashMap 中的次数加 1)
            hashMap.put(num, hashMap.getOrDefault(num, 0) + 1);
        }
        return hashMap.isEmpty();
    }
}