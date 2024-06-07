package com.example.bankapp.service;


import com.example.bankapp.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class);
    private final List<Account> accounts;
    private final AtomicInteger transactionCount = new AtomicInteger(0);

    public AccountService(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void transferMoney(Account from, Account to, int amount) {
        synchronized (from) {
            synchronized (to) {
                if (from.getMoney() >= amount) {
                    from.setMoney(from.getMoney() - amount);
                    to.setMoney(to.getMoney() + amount);
                    transactionCount.incrementAndGet();
                    logger.info("Transferred {} from {} to {}", amount, from.getId(), to.getId());
                } else {
                    logger.warn("Failed to transfer {} from {} to {}: insufficient funds", amount, from.getId(), to.getId());
                }
            }
        }
    }

    public int getTransactionCount() {
        return transactionCount.get();
    }
}
