package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-04-05 16:00
 */

/**
 * 307. 区域和检索 - 数组可修改 range-sum-query-mutable
 * 给你一个数组 nums ，请你完成两类查询。
 * <p>
 * 其中一类查询要求 更新 数组 nums 下标对应的值
 * 另一类查询要求返回数组 nums 中索引 left 和索引 right 之间（ 包含 ）的nums元素的 和，其中 left <= right
 * 实现 NumArray 类：
 * <p>
 * NumArray(int[] nums) 用整数数组 nums 初始化对象
 * void update(int index, int val) 将 nums[index] 的值 更新 为 val
 * int sumRange(int left, int right) 返回数组 nums 中索引 left 和索引 right 之间（ 包含 ）的nums元素的 和
 * （即，nums[left] + nums[left + 1], ..., nums[right]）
 * <p>
 * 1 <= nums.length <= 3 * 10^4
 * -100 <= nums[i] <= 100
 * 0 <= index < nums.length
 * -100 <= val <= 100
 * 0 <= left <= right < nums.length
 * 调用 update 和 sumRange 方法次数不大于 3 * 10^4
 */
public class P307_NumArray {
    public static void main(String[] args) {

    }
}

// 超出时间限制
class NumArray1 {
    int[] nums;
    // 记录 nums 数组的前缀和
    int[] pref;

    public NumArray1(int[] nums) {
        this.nums = nums;
        pref = new int[nums.length];
        // 初始化得到前缀和
        updatePref(0, nums.length - 1);
    }

    public void update(int index, int val) {
        if (nums[index] != val) {
            nums[index] = val;
            // 每次更新时重新计算前缀和
            updatePref(index, nums.length - 1);
        }
    }

    public int sumRange(int left, int right) {
        return pref[right] - pref[left] + nums[left];
    }

    private void updatePref(int index, int end) {
        if (index == 0) {
            pref[0] = nums[0];
            index++;
        }
        for (int i = index; i <= end; i++) {
            pref[i] = pref[i - 1] + nums[i];
        }
    }
}

// 超出时间限制
class NumArray2 {
    int[] nums;

    public NumArray2(int[] nums) {
        this.nums = nums;
    }

    public void update(int index, int val) {
        if (nums[index] != val) {
            nums[index] = val;
        }
    }

    public int sumRange(int left, int right) {
        int ans = 0;
        for (int i = left; i <= right; i++) {
            ans += nums[i];
        }
        return ans;
    }
}

// 分块处理
// 95 ms 17.38%
// 70.6 MB 41.32%
class NumArray {
    private final int[] nums;
    // 每个块的 blockSize 个元素的总和
    int[] blockSum;
    // 每个块的大小
    int blockSize;

    public NumArray(int[] nums) {
        this.nums = nums;
        int n = nums.length;
        blockSize = (int) Math.sqrt(n);
        // n/blockSize 向上取整
        blockSum = new int[(n + blockSize - 1) / blockSize];
        for (int i = 0; i < n; i++) {
            blockSum[i / blockSize] += nums[i];
        }
    }

    public void update(int index, int val) {
        if (nums[index] != val) {
            blockSum[index / blockSize] += val - nums[index];
            nums[index] = val;
        }
    }

    public int sumRange(int left, int right) {
        int ans = 0;
        // left 是位于哪个块中
        int block1 = left / blockSize;
        // 第几个位置
        int index1 = left % blockSize;
        // right 是位于哪个块中
        int block2 = right / blockSize;
        // 第几个位置
        int index2 = right % blockSize;
        // 在同一个块中, 直接求和
        if (block1 == block2) {
            int sum = 0;
            for (int i = left; i <= right; i++) {
                sum += nums[i];
            }
            return sum;
        }
        // 不同块中时, 分为三部分的和
        int sum1 = 0;
        for (int i = index1; i < blockSize; i++) {
            sum1 += nums[block1 * blockSize + i];
        }
        int sum2 = 0;
        for (int i = 0; i <= index2; i++) {
            sum2 += nums[block2 * blockSize + i];
        }
        int sum3 = 0;
        for (int i = block1 + 1; i < block2; i++) {
            sum3 += blockSum[i];
        }
        return sum1 + sum2 + sum3;
    }
}

// 线段树
// 树状数组
/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */