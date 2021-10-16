package com.ahua.leetcode;

/**
 * @author huajun
 * @create 2021-04-25 1:36
 */
/*
public class test1<path> {


        //int[] root = new int[];
        TreeNode root = new TreeNode(2);
        List<List<Integer>> ret = new LinkedList<List<Integer>>();
        Deque<Integer> path;
        path = new LinkedList<Integer>();

        public List<List<Integer>> pathSum (TreeNode root,int sum){
            dfs(root, sum);
            return ret;
        }

        public void dfs (TreeNode root,int sum){
            if (root == null) {
                return;
            }
            path.offerLast(root.val);
            sum -= root.val;
            if (root.left == null && root.right == null && sum == 0) {
                ret.add(new LinkedList<Integer>(path));
            }
            dfs(root.left, sum);
            dfs(root.right, sum);
            path.pollLast();
        }


}

 class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

 */