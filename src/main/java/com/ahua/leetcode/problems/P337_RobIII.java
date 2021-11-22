package com.ahua.leetcode.problems;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huajun
 * @create 2021-11-22 16:29
 */

/**
 * 在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。
 * 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。
 * 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。
 * <p>
 * 计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
 */
public class P337_RobIII {
    public static void main(String[] args) {

    }
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class P337_Solution1 {
    // 表示选择 node 节点的情况下, node 节点的子树上被选择的节点的最大权值和
    Map<TreeNode, Integer> selectThisNode = new HashMap<>();
    // 表示不选择 node 节点的情况下, node 节点的子树上被选择的节点的最大权值和
    Map<TreeNode, Integer> notSelectThisNode = new HashMap<>();

    public int rob(TreeNode root) {
        dfs(root);
        return Math.max(selectThisNode.getOrDefault(root, 0), notSelectThisNode.getOrDefault(root, 0));
    }

    // 后序遍历
    public void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        // 遍历左子树, 得到 node 节点的左子树上 (被选择和不被选择) 的节点的最大权值和
        // 退出 dfs 时, (node.left, 被选择和不被选择的 权值和) 已经添加进了 selectThisNode 和 notSelectThisNode 中
        dfs(node.left);
        // 遍历右子树, 得到 node 节点的右子树上 (被选择和不被选择) 的节点的最大权值和
        dfs(node.right);

        // 当 node 被选中时, node 的左右孩子都不能被选中, 故 node 被选中情况下子树上被选中点的最大权值和为
        // node.left 和 node.right 不被选中的最大权值和相加,
        // 即 sel(node) = node.val + not(node.left) + not(node.right)
        selectThisNode.put(node, node.val
                + notSelectThisNode.getOrDefault(node.left, 0)
                + notSelectThisNode.getOrDefault(node.right, 0)
        );
        // 当 node 不被选中时, node 的左右孩子可以被选中, 也可以不被选中
        // 对于 node 的某个具体的孩子 children, 它对 node 的贡献是 children 被选中和不被选中情况下权值和的较大值
        // 故 not(node) = max{sel(node.left) , not(node.left)} + max{sel(node.right) , not(node.right)}
        notSelectThisNode.put(node,
                Math.max(selectThisNode.getOrDefault(node.left, 0), notSelectThisNode.getOrDefault(node.left, 0))
                        + Math.max(selectThisNode.getOrDefault(node.right, 0), notSelectThisNode.getOrDefault(node.right, 0))
        );
    }
}

// 优化, 省去使用哈希表的空间, 但是整体空间复杂度并未改变, 均是O(N)
class P337_Solution {

    public int rob(TreeNode root) {
        int[] rootStatus = dfs(root);
        return Math.max(rootStatus[0], rootStatus[1]);
    }

    // 后序遍历
    public int[] dfs1(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        // 遍历左子树, 得到 node 节点的左子树上 (被选择和不被选择) 的节点的最大权值和
        // 退出 dfs 时, 返回 node.left 的 被选择和不被选择的权值和数组 leftNodeStatus
        // leftNodeStatus[0] 表示 选择 node.left 的权值和
        // rightNodeStatus[0] 表示 不选择 node.left 的权值和
        int[] leftNodeStatus = dfs(node.left);
        // 遍历右子树, 得到 node 节点的右子树上 (被选择和不被选择) 的节点的最大权值和
        int[] rightNodeStatus = dfs(node.right);

        // 当 node 被选中时, node 的左右孩子都不能被选中, 故 node 被选中情况下子树上被选中点的最大权值和为
        // node.left 和 node.right 不被选中的最大权值和相加,
        // 即 sel(node) = node.val + not(node.left) + not(node.right)
        int valOfSelectThisNode = node.val + leftNodeStatus[1] + rightNodeStatus[1];

        // 当 node 不被选中时, node 的左右孩子可以被选中, 也可以不被选中
        // 对于 node 的某个具体的孩子 children, 它对 node 的贡献是 children 被选中和不被选中情况下权值和的较大值
        // 故 not(node) = max{sel(node.left) , not(node.left)} + max{sel(node.right) , not(node.right)}
        int valOfNotSelectThisNode = Math.max(leftNodeStatus[0], leftNodeStatus[1]) + Math.max(rightNodeStatus[0], rightNodeStatus[1]);

        return new int[]{valOfSelectThisNode, valOfNotSelectThisNode};
    }

    // 后序遍历, 些微改变
    public int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        // 遍历左子树, 得到 node 节点的左子树上 (被选择和不被选择) 的节点的最大权值和
        // 退出 dfs 时, 返回 node.left 的 被选择和不被选择的权值和数组 leftNodeStatus
        // leftNodeStatus[0] 表示 选择 node.left 的权值和
        // rightNodeStatus[0] 表示 不选择 node.left 的权值和
        int[] leftNodeStatus = dfs(node.left);
        // 遍历右子树, 得到 node 节点的右子树上 (被选择和不被选择) 的节点的最大权值和
        int[] rightNodeStatus = dfs(node.right);

        int[] nodeStatus = new int[2];

        // 当 node 被选中时, node 的左右孩子都不能被选中, 故 node 被选中情况下子树上被选中点的最大权值和为
        // node.left 和 node.right 不被选中的最大权值和相加,
        // 即 sel(node) = node.val + not(node.left) + not(node.right)
        nodeStatus[0] = node.val + leftNodeStatus[1] + rightNodeStatus[1];

        // 当 node 不被选中时, node 的左右孩子可以被选中, 也可以不被选中
        // 对于 node 的某个具体的孩子 children, 它对 node 的贡献是 children 被选中和不被选中情况下权值和的较大值
        // 故 not(node) = max{sel(node.left) , not(node.left)} + max{sel(node.right) , not(node.right)}
        nodeStatus[1] = Math.max(leftNodeStatus[0], leftNodeStatus[1]) + Math.max(rightNodeStatus[0], rightNodeStatus[1]);

        return nodeStatus;
    }
}