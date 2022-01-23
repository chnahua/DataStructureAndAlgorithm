package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-01-23 22:23
 */

import java.util.HashMap;
import java.util.TreeMap;

/**
 * 2034. 股票价格波动 stock-price-fluctuation
 * 给你一支股票价格的数据流。数据流中每一条记录包含一个 时间戳 和该时间点股票对应的 价格 。
 * <p>
 * 不巧的是，由于股票市场内在的波动性，股票价格记录可能不是按时间顺序到来的。某些情况下，有的记录可能是错的。如果两个有相同时间戳的记录出现在数据流中，前一条记录视为错误记录，后出现的记录 更正 前一条错误的记录。
 * <p>
 * 请你设计一个算法，实现：
 * <p>
 * 更新 股票在某一时间戳的股票价格，如果有之前同一时间戳的价格，这一操作将 更正 之前的错误价格。
 * 找到当前记录里 最新股票价格 。最新股票价格 定义为时间戳最晚的股票价格。
 * 找到当前记录里股票的 最高价格 。
 * 找到当前记录里股票的 最低价格 。
 * 请你实现 StockPrice 类：
 * <p>
 * StockPrice() 初始化对象，当前无股票价格记录。
 * void update(int timestamp, int price) 在时间点 timestamp 更新股票价格为 price 。
 * int current() 返回股票 最新价格 。
 * int maximum() 返回股票 最高价格 。
 * int minimum() 返回股票 最低价格 。
 * <p>
 * 1 <= timestamp, price <= 10^9
 * update，current，maximum 和 minimum 总 调用次数不超过 10^5 。
 * current，maximum 和 minimum 被调用时，update 操作 至少 已经被调用过 一次 。
 */
public class P2034_StockPrice {
    public static void main(String[] args) {

    }
}

// 超时
// 原因在于如果只使用一个 Map, 求最高价格和最低价格时需要遍历整个 Map
class StockPrice1 {
    TreeMap<Integer, Integer> stockPrice;

    // 初始化对象，当前无股票价格记录
    public StockPrice1() {
        this.stockPrice = new TreeMap<>((o1, o2) -> (o2 - o1));
    }

    // 在时间点 timestamp 更新股票价格为 price
    public void update(int timestamp, int price) {
        stockPrice.put(timestamp, price);
    }

    //  返回股票 最新价格
    public int current() {
        return stockPrice.get(stockPrice.firstKey());
    }

    // 返回股票 最高价格
    public int maximum() {
        int maxPrice = 0;
        for (int price : stockPrice.values()) {
            maxPrice = Math.max(maxPrice, price);
        }
        return maxPrice;
    }

    // 返回股票 最低价格
    public int minimum() {
        int minPrice = Integer.MAX_VALUE;
        for (int price : stockPrice.values()) {
            minPrice = Math.min(minPrice, price);
        }
        return minPrice;
    }
}

// 哈希表 + 有序集合
// 111 ms 官方的是 120 ms, 果然修改后比它快一点, 但貌似空间多耗了一点点
class StockPrice {
    // 最近时间戳, 最新价格的时间
    int recentTimestamp;
    // 保存股票价格记录 timestamp 与 price
    HashMap<Integer, Integer> stockTimePrice;
    // 保存价格与出现次数
    TreeMap<Integer, Integer> prices;

    // 初始化对象，当前无股票价格记录
    public StockPrice() {
        recentTimestamp = 0;
        this.stockTimePrice = new HashMap<>();
        this.prices = new TreeMap<>();
    }

    // 在时间点 timestamp 更新股票价格为 price
    public void update(int timestamp, int price) {
        // 比较更新股票最近时间
        recentTimestamp = Math.max(recentTimestamp, timestamp);
        // 添加或更新该时间点 timestamp 以及股票价格 price
        // 如果当前 timestamp 之前已经存在于 map 中, 则返回值 prevPrice 为前一个时间点 timestamp 的对应价格
        // 如果不在, 则 prevPrice 返回为 null
        Integer prevPrice = stockTimePrice.put(timestamp, price);
        //
        if (prevPrice != null) {
            // 前一个价格值的次数
            Integer prevPriceCount = prices.get(prevPrice);
            // 次数为 1, 此次要将其减 1, 则为 0 了, 为 0 的话, 就要删除它, 等价于等于 1 时直接删除
            if (prevPriceCount == 1) {
                prices.remove(prevPrice);
            } else {
                // prevPriceCount 肯定是大于 1 的
                prices.put(prevPrice, prevPriceCount - 1);
            }
        }
        // 当前时间点的对应价格的出现次数加 1
        prices.put(price, prices.getOrDefault(price, 0) + 1);
    }

    //  返回股票 最新价格
    public int current() {
        return stockTimePrice.get(recentTimestamp);
    }

    // 返回股票 最高价格
    public int maximum() {
        return prices.lastKey();
    }

    // 返回股票 最低价格
    public int minimum() {
        return prices.firstKey();
    }
}

/**
 * Your StockPrice object will be instantiated and called as such:
 * StockPrice obj = new StockPrice();
 * obj.update(timestamp,price);
 * int param_2 = obj.current();
 * int param_3 = obj.maximum();
 * int param_4 = obj.minimum();
 */