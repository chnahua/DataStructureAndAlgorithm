package com.ahua.leetcode.problems;

/**
 * @author huajun
 * @create 2022-03-18 21:06
 */

/**
 * 2043. 简易银行系统 simple-bank-system
 * 你的任务是为一个很受欢迎的银行设计一款程序，以自动化执行所有传入的交易（转账，存款和取款）。
 * 银行共有 n 个账户，编号从 1 到 n 。
 * 每个账号的初始余额存储在一个下标从 0 开始的整数数组 balance 中，其中第 (i + 1) 个账户的初始余额是 balance[i] 。
 * <p>
 * 请你执行所有 有效的 交易。如果满足下面全部条件，则交易 有效 ：
 * 指定的账户数量在 1 和 n 之间，且
 * 取款或者转账需要的钱的总数 小于或者等于 账户余额。
 * <p>
 * 实现 Bank 类：
 * Bank(long[] balance) 使用下标从 0 开始的整数数组 balance 初始化该对象。
 * boolean transfer(int account1, int account2, long money) 从编号为 account1 的账户向编号为 account2 的账户转帐 money 美元。
 * 如果交易成功，返回 true ，否则，返回 false 。
 * boolean deposit(int account, long money) 向编号为 account 的账户存款 money 美元。
 * 如果交易成功，返回 true ；否则，返回 false 。
 * boolean withdraw(int account, long money) 从编号为 account 的账户取款 money 美元。
 * 如果交易成功，返回 true ；否则，返回 false 。
 * <p>
 * n == balance.length
 * 1 <= n, account, account1, account2 <= 10^5
 * 0 <= balance[i], money <= 10^12
 * transfer, deposit, withdraw 三个函数，每个 最多调用 10^4 次
 */
public class P2043_Bank {
    public static void main(String[] args) {

    }
}

// 101 ms 43.03%
// 78.2 MB 81.32%
class Bank {
    // 账户金额
    long[] balance;
    // 账户数量
    int n;

    public Bank(long[] balance) {
        this.balance = balance;
        this.n = balance.length;
    }

    // 转账
    public boolean transfer(int account1, int account2, long money) {
        if (isValidAccount(account1) && isValidAccount(account2)) {
            if (balance[account1 - 1] >= money) {
                balance[account1 - 1] -= money;
                balance[account2 - 1] += money;
                return true;
            }
        }
        return false;
    }

    // 存款
    public boolean deposit(int account, long money) {
        if (isValidAccount(account)) {
            balance[account - 1] += money;
            return true;
        }
        return false;
    }

    // 取款
    public boolean withdraw(int account, long money) {
        if (isValidAccount(account)) {
            if (balance[account - 1] >= money) {
                balance[account - 1] -= money;
                return true;
            }
        }
        return false;
    }

    // 检验合法账户
    public boolean isValidAccount(int account) {
        return account >= 1 && account <= n;
    }
}

/**
 * Your Bank object will be instantiated and called as such:
 * Bank obj = new Bank(balance);
 * boolean param_1 = obj.transfer(account1,account2,money);
 * boolean param_2 = obj.deposit(account,money);
 * boolean param_3 = obj.withdraw(account,money);
 */