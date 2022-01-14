package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-14 20:33
 */

import java.util.*;

/**
 * 239. 滑动窗口最大值 sliding-window-maximum
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * <p>
 * 返回滑动窗口中的最大值。
 * <p>
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * 1 <= k <= nums.length
 */
public class P239_MaxSlidingWindow {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int[] nums1 = new int[]{1};
        int[] nums2 = new int[]{1, -1};
        int[] nums3 = new int[]{9, 11};
        int[] nums4 = new int[]{4, -2};
        int[] nums5 = new int[]{7, 2, 4};
        P239_Solution solution = new P239_Solution();
        System.out.println(Arrays.toString(solution.maxSlidingWindow(nums, 3))); // [3, 3, 5, 5, 6, 7]
        System.out.println(Arrays.toString(solution.maxSlidingWindow(nums1, 1))); // [1]
        System.out.println(Arrays.toString(solution.maxSlidingWindow(nums2, 1))); // [1, -1]
        System.out.println(Arrays.toString(solution.maxSlidingWindow(nums3, 2))); // [11]
        System.out.println(Arrays.toString(solution.maxSlidingWindow(nums4, 2))); // [4]
        System.out.println(Arrays.toString(solution.maxSlidingWindow(nums5, 2))); // [7, 4]
    }
}

// 优先队列
// 我的 pq 是随时保证队列中只有 k 个元素, 而官方解法是保证在前面已经添加到队列中的若干数中的最大值是在最近nums 中的 k 个数
// 其中有些较小的数仍然可能是滑动窗口左侧边界左边的数, 它并没有被移除, 而我是每次都将不在窗口中的数移除了, 而要移除, 就会涉及到查找, 因此降低了效率
// 而官方的移除只需要移除堆顶, 并不是移除等于多少的具体的那个数（不涉及查找）
// 评论区评论
// 这里应该把主动寻找、及时删除的思维转换为被动检测、延迟处理
class P239_Solution1 {
    // 我的解法, 超时
    public int[] maxSlidingWindow1(int[] nums, int k) {
        if (k == 1) {
            return nums;
        }
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> (o2 - o1));
        // 初始化, 先将前 k 个数添加进队列
        for (int i = 0; i < k; i++) {
            priorityQueue.add(nums[i]);
        }
        // 得到第一个滑动窗口的最大值
        ans[0] = priorityQueue.peek();
        for (int i = k; i < n; i++) {
            // 出队列一个  超时就是来源于这一步
            priorityQueue.remove(nums[i - k]);
            // 当前滑动窗口中的最大值
            ans[i - k + 1] = Math.max(priorityQueue.peek(), nums[i]);
            // 入队列一个新的数
            priorityQueue.add(nums[i]);
        }
        return ans;
    }

    // 根据官方解法修改
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (k == 1) {
            return nums;
        }
        int n = nums.length;
        // 此处, 是大顶堆, 先根据 num 值从大到小排序, 再根据位置下标 i 排序(从小到大、从大到小皆可)
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] != o2[0] ? o2[0] - o1[0] : o2[1] - o1[1]);
        // 初始化, 先将前 k 个数添加进队列
        for (int i = 0; i < k; i++) {
            pq.add(new int[]{nums[i], i});
        }
        int[] ans = new int[n - k + 1];
        // 得到第一个滑动窗口的最大值
        ans[0] = pq.peek()[0];
        for (int i = k; i < n; i++) {
            // 先将新的数添加进队列, 此时队列中就可能会产生一个新的最大值
            pq.add(new int[]{nums[i], i});
            // 但是这个最大值可能是当前滑动窗口中左侧边界左边的数, 不在滑动窗口中, 因此需要将其从队列中移除
            while (pq.peek()[1] <= i - k) {
                pq.remove();
            }
            ans[i - k + 1] = pq.peek()[0];
        }
        return ans;
    }
}

// 单调队列 (双端队列)
class P239_Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (k == 1) {
            return nums;
        }
        int n = nums.length;
        // 单调队列 (递减)
        Deque<Integer> deque = new LinkedList<>();
        // 初始化, 先将前 k 个数添加进队列
        for (int i = 0; i < k; i++) {
            // 移除队列尾比 nums[i] 小的数的下标
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.removeLast();
            }
            deque.addLast(i);
        }

        int[] ans = new int[n - k + 1];
        // 得到第一个滑动窗口的最大值
        ans[0] = nums[deque.peekFirst()];
        for (int i = k; i < n; i++) {
            // 移除队列尾比 nums[i] 小的数的下标
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.removeLast();
            }
            // 添加 nums[i] 的下标
            deque.addLast(i);
            // 移除超出滑动窗口左侧边界左边的数的下标
            while (deque.peekFirst() <= i - k) {
                deque.removeFirst();
            }
            // 此时的 nums[deque.peekFirst()] 即为在滑动窗口内部的所有数中的最大值
            ans[i - k + 1] = nums[deque.peekFirst()];
        }
        return ans;
    }
}