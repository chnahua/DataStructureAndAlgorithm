package com.ahua.leetcode.problems;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author huajun
 * @create 2021-10-15 16:47
 */

/**
 * 运用你所掌握的数据结构，设计和实现一个 LRU (最近最少使用) 缓存机制 。
 *
 * 实现 LRUCache 类：
 * LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
 * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 * void put(int key, int value)如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。
 * 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
 *
 * 进阶：你是否可以在 O(1) 时间复杂度内完成这两种操作？
 */

/**
 * 输入
 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * 输出
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 *
 */

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

public class P146_LRUCache {
    public static void main(String[] args) {
//        LRUCache lRUCache = new LRUCache(1);
//        lRUCache.put(1, 1); // 缓存是 {1=1}
//        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
//        lRUCache.get(1);    // 返回 1
//        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
//        lRUCache.get(2);    // 返回 -1 (未找到)
//        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
//        lRUCache.get(1);    // 返回 -1 (未找到)
//        lRUCache.get(3);    // 返回 3
//        lRUCache.get(4);    // 返回 4

//        LRUCache2_2 lRUCache = new LRUCache2_2(1);
//        lRUCache.put(2, 1); // 缓存是 {2=1}
//        lRUCache.get(2);    // 返回 2
//        lRUCache.put(3, 2); // 该操作会使得关键字 2 作废，缓存是 {3=2}

        LRUCache2_2 lRUCache = new LRUCache2_2(3000);
        for (int i = 0; i < 3000 ; i++) {
            lRUCache.put((int)(Math.random() * 1000), (int)(Math.random() * 1000));
        }
        for (int i = 0; i < 3000 ; i++) {
            lRUCache.get((int)(Math.random() * 1000));
        }
    }
}

// 使用 Java 自带 LinkedHashMap 实现
class LRUCache1 extends LinkedHashMap<Integer, Integer> {
    private final int capacity;

    public LRUCache1(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        // return super.removeEldestEntry(eldest);
        return size() > capacity;
    }
}

// 使用 哈希表 + 双向链表
// 时间复杂度：对于 put 和 get 都是 O(1)O(1)。
// 空间复杂度：O(\text{capacity})O(capacity)，因为哈希表和双向链表最多存储 \text{capacity} + 1capacity+1 个元素。
class LRUCache {

    static class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;

        public DLinkedNode() {
        }

        public DLinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final Map<Integer, DLinkedNode> cache = new HashMap<>();
    private int size;
    private final int capacity;
    private final DLinkedNode head;
    private final DLinkedNode tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        // 使用伪头部和伪尾部节点
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 如果 key 存在，先通过哈希表定位，再移到头部
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            // 如果 key 不存在，创建一个新的节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            // 添加进哈希表
            cache.put(key, newNode);
            // 新节点添加到双向链表的头部
            addToHead(newNode);
            size++;
            if (size > capacity) {
                // 如果超出容量，删除双向链表的尾部节点
                DLinkedNode tailNode = tail.prev;
                removeNode(tailNode);
                // 删除哈希表中对应的项
                cache.remove(tailNode.key);
                size--;
            }
        } else {
            // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
            node.value = value;
            moveToHead(node);
        }
    }

    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }
}

class LRUCache2_1 {

    static class SLinkedNode {
        int key;
        int value;
        SLinkedNode next;

        public SLinkedNode() {
        }

        public SLinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final Map<Integer, SLinkedNode> cache = new HashMap<>();
    private int size;
    private final int capacity;
    private final SLinkedNode head;
    private final SLinkedNode tail;
    private final Map<SLinkedNode, SLinkedNode> map = new HashMap<>();

    public LRUCache2_1(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        // 使用伪头部和伪尾部节点
        head = new SLinkedNode();
        tail = new SLinkedNode();
        head.next = tail;
        tail.next = head;
    }

    public int get(int key) {
        SLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 如果 key 存在，先通过哈希表定位，再移到头部
        // 保存要移动的这个节点的前一个节点
        SLinkedNode prevNode = map.get(node);
        moveToHead(node, prevNode, map);
        return node.value;
    }

    public void put(int key, int value) {
        SLinkedNode node = cache.get(key);
        if (node == null) {
            // 如果 key 不存在，创建一个新的节点
            SLinkedNode newNode = new SLinkedNode(key, value);
            // 添加进哈希表
            cache.put(key, newNode);
            // 新节点添加到链表的头部,更新 map
            addToHead(newNode, map);
            // 初始在添加完成第一个节点后,将 tail 指向这个尾部节点,也是第一个节点
            if (size == 0) {
                tail.next = newNode;
            }
            size++;
            if (size > capacity) {
                // 如果超出容量，删除链表的尾部节点,删除前保存尾部节点的前一个节点
                SLinkedNode tailNode = tail.next;
                // 保存要删除节点的前一个节点
                SLinkedNode tailPrevNode = map.get(tailNode);
                removeNode(tailNode, tailPrevNode, map);
                // 删除哈希表中对应的项
                cache.remove(tailNode.key);
                size--;
                // 更新 map, 将之前要删除的那个尾结点和它的前一个结点在 map 中保存的值<tailNode, tailPrevNode>删除,而tail节点<tail, tailNode>改为<tail, tailPrevNode>
//                map.remove(tailNode);
//                map.put(tail, tailPrevNode);
            }
        } else {
            // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
            node.value = value;
            // 保存要移动的这个节点的前一个节点
            SLinkedNode prevNode = map.get(node);
            moveToHead(node, prevNode, map);
        }
    }

    private void addToHead(SLinkedNode node, Map<SLinkedNode, SLinkedNode> map) {
        node.next = head.next;
        head.next = node;
        // 更新 map
        map.put(node.next, node);
        map.put(node, head);
    }

    private void removeNode(SLinkedNode node, SLinkedNode prevNode, Map<SLinkedNode, SLinkedNode> map) {
        prevNode.next = node.next;
        // 如果删除的是尾部节点,需要将 tail 指向尾部节点的前一个节点
        // 但是如果这个尾部节点也是唯一一个节点, 也就是, 该单链表中只有一个节点时, 则不需要做出上述改变
        if (tail.next == node && size != 1) {
            tail.next = prevNode;
        }
        map.put(node.next, prevNode);
        map.remove(node);
    }

    private void moveToHead(SLinkedNode node, SLinkedNode prevNode, Map<SLinkedNode, SLinkedNode> map) {
        removeNode(node, prevNode, map);
        addToHead(node, map);
    }
}

// cache 中并未保存 head 和 tail
class LRUCache2_2 {

    static class SLinkedNode {
        int key;
        int value;
        SLinkedNode next;

        public SLinkedNode() {
        }

        public SLinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final Map<Integer, SLinkedNode> cache = new HashMap<>();
    private int size;
    private final int capacity;
    private final SLinkedNode head;
    private final SLinkedNode tail;
    private final Map<Integer, Integer> map = new HashMap<>();

    public LRUCache2_2(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        // 使用伪头部和伪尾部节点
        head = new SLinkedNode();
        tail = new SLinkedNode();
        head.next = tail;
        tail.next = head;
    }

    public int get(int key) {
        SLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 如果 key 存在，先通过哈希表定位，再移到头部
        // 保存要移动的这个节点的前一个节点
        // 如果是这个节点是唯一的一个节点,即它的前一个节点是 head, 就需要特别赋值
        SLinkedNode prevNode;
        if (head.next == node) {
            prevNode = head;
        } else {
            prevNode = cache.get(map.get(key));
        }
        moveToHead(node, prevNode, map);
        return node.value;
    }

    public void put(int key, int value) {
        SLinkedNode node = cache.get(key);
        if (node == null) {
            // 如果 key 不存在，创建一个新的节点
            SLinkedNode newNode = new SLinkedNode(key, value);
            // 添加进哈希表
            cache.put(key, newNode);
            // 新节点添加到链表的头部,更新 map
            addToHead(newNode, map);
            // 初始在添加完成第一个节点后,将 tail 指向这个尾部节点,也是第一个节点
            if (size == 0) {
                tail.next = newNode;
            }
            size++;
            if (size > capacity) {
                // 如果超出容量，删除链表的尾部节点,删除前保存尾部节点的前一个节点
                SLinkedNode tailNode = tail.next;
                // 保存要删除节点的前一个节点
                SLinkedNode tailPrevNode = cache.get(map.get(tailNode.key));
                removeNode(tailNode, tailPrevNode, map);
                // 删除哈希表中对应的项
                cache.remove(tailNode.key);
                size--;
            }
        } else {
            // 如果 key 存在，先通过哈希表定位，再修改 value, 并移到头部
            node.value = value;
            // 保存要移动的这个节点的前一个节点
            // 如果是这个节点是唯一的一个节点,即它的前一个节点是 head, 就需要特别赋值
            SLinkedNode prevNode;
            if (head.next == node) {
                prevNode = head;
            } else {
                prevNode = cache.get(map.get(key));
            }
            moveToHead(node, prevNode, map);
        }
    }

    private void addToHead(SLinkedNode node, Map<Integer, Integer> map) {
        node.next = head.next;
        head.next = node;
        // 更新 map
        map.put(node.next.key, node.key);
        map.put(node.key, head.key);
    }

    private void removeNode(SLinkedNode node, SLinkedNode prevNode, Map<Integer, Integer> map) {
        prevNode.next = node.next;
        // 如果删除的是尾部节点,需要将 tail 指向尾部节点的前一个节点
        // 但是如果这个尾部节点也是唯一一个节点, 也就是, 该单链表中只有一个节点时, 则不需要做出上述改变
        if (tail.next == node && size != 1) {
            tail.next = prevNode;
        }
        map.put(node.next.key, prevNode.key);
        map.remove(node.key);
    }

    private void moveToHead(SLinkedNode node, SLinkedNode prevNode, Map<Integer, Integer> map) {
        removeNode(node, prevNode, map);
        addToHead(node, map);
    }
}
