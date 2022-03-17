package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-16 21:31
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 432. 全 O(1) 的数据结构 all-oone-data-structure
 * 请你设计一个用于存储字符串计数的数据结构，并能够返回计数最小和最大的字符串。
 * <p>
 * 实现 AllOne 类：
 * <p>
 * AllOne() 初始化数据结构的对象。
 * inc(String key) 字符串 key 的计数增加 1 。如果数据结构中尚不存在 key ，那么插入计数为 1 的 key 。
 * dec(String key) 字符串 key 的计数减少 1 。如果 key 的计数在减少后为 0 ，那么需要将这个 key 从数据结构中删除。
 * 测试用例保证：在减少计数前，key 存在于数据结构中。
 * getMaxKey() 返回任意一个计数最大的字符串。如果没有元素存在，返回一个空字符串 "" 。
 * getMinKey() 返回任意一个计数最小的字符串。如果没有元素存在，返回一个空字符串 "" 。
 * <p>
 * 1 <= key.length <= 10
 * key 由小写英文字母组成
 * 测试用例保证：在每次调用 dec 时，数据结构中总存在 key
 * 最多调用 inc、dec、getMaxKey 和 getMinKey 方法 5 * 10^4 次
 */
public class P432_AllOne {
    public static void main(String[] args) {
        AllOne allOne = new AllOne();
        allOne.inc("hello");
        allOne.inc("hello");
        System.out.println(allOne.getMaxKey());
        System.out.println(allOne.getMinKey());
        allOne.inc("leet");
        System.out.println(allOne.getMaxKey());
        System.out.println(allOne.getMinKey());
    }
}

// 哈希表 + 双向链表(准确说是循环双向链表)
// 47 ms 83.20%
// 57.1 MB 22.57%
class AllOne {
    Node root;
    Map<String, Node> nodes;

    public AllOne() {
        root = new Node();
        root.prev = root;
        root.next = root;
        nodes = new HashMap<>();
    }

    public void inc(String key) {
        Node node = nodes.get(key);
        // 当前 key 是第一次添加时
        if (node == null) {
            // 判断计数为 1 的结点是否存在
            if (root.next.count == 1) {
                // 就这行代码没写, 让我改了两小时
                // 存在计数为 1 的结点
                root.next.keysSet.add(key);
            } else {
                root.insert(new Node(key, 1));
            }
            // 哈希表中添加或修改 key 对应的 node
            nodes.put(key, root.next);
        } else {
            // 当前 key 不是第一次添加时
            // 1. 判断计数为 node.count + 1 的结点是否存在
            if (node.next.count == node.count + 1) {
                node.next.keysSet.add(key);
            } else {
                // 计数为 node.count + 1 的结点不存在
                node.insert(new Node(key, node.count + 1));
            }
            // 2. 哈希表中添加或修改 key 对应的 node
            nodes.put(key, node.next);
            // 3. 出现次数为 node.count 的结点的 set 集合中移除此 key
            node.keysSet.remove(key);
            if (node.keysSet.isEmpty()) {
                node.remove();
            }
        }
    }

    // 测试用例保证: 在每次调用 dec 时, 数据结构中总存在 key
    public void dec(String key) {
        Node node = nodes.get(key);
        // 当前 key 删除前的计数为 1 时
        if (node.count == 1) {
            // 哈希表中修改或删除: 哈希表中直接删除该 key
            nodes.remove(key);
        } else {
            // 当前 key 删除前的计数大于 1 时
            // 1. 双向链表中添加 key
            // 判断计数为 node.count - 1 的结点是否存在
            if (node.prev.count == node.count - 1) {
                // 存在, 直接添加进 set 中
                node.prev.keysSet.add(key);
            } else {
                // 不存在, 新建结点再添加进去
                node.prev.insert(new Node(key, node.count - 1));
            }
            // 2. 哈希表中添加或修改 key 对应的 node
            nodes.put(key, node.prev);
        }
        // 双向链表中删除 key
        // 从双向链表(出现次数为 node.count)的结点 node 的 set 集合中删除该 key
        // 如果集合中存在此 key 以外的 key, 则只删除此 key, 否则需要删除该整个结点
        node.keysSet.remove(key);
        if (node.keysSet.isEmpty()) {
            node.remove();
        }
    }

    public String getMaxKey() {
        return root.prev != root ? root.prev.keysSet.iterator().next() : "";
    }

    public String getMinKey() {
        return root.next != root ? root.next.keysSet.iterator().next() : "";
    }

    static class Node {
        Node prev;
        Node next;
        Set<String> keysSet;
        int count;

        public Node() {
            this("", 0);
        }

        public Node(String key, int count) {
            this.count = count;
            keysSet = new HashSet<>();
            keysSet.add(key);
        }

        public Node insert(Node node) {
            node.prev = this;
            node.next = this.next;
            this.next.prev = node;
            this.next = node;
            return node;
        }

        public void remove() {
            this.prev.next = this.next;
            this.next.prev = this.prev;
        }
    }
}


/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
