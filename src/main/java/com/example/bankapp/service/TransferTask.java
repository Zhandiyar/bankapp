package com.example.bankapp.service;


import com.example.bankapp.model.Account;
import com.example.bankapp.service.AccountService;

import java.util.List;
import java.util.Random;

import java.util.List;

public class TransferTask implements Runnable {
    private final AccountService accountService;
    private final List<Account> accounts;
    private final Random random = new Random();

    public TransferTask(AccountService accountService, List<Account> accounts) {
        this.accountService = accountService;
        this.accounts = accounts;
    }

    @Override
    public void run() {
        while (accountService.getTransactionCount() < 30) {
            Account from = accounts.get(random.nextInt(accounts.size()));
            Account to;
            do {
                to = accounts.get(random.nextInt(accounts.size()));
            } while (from == to);

            int amount = random.nextInt(1000);
            accountService.transferMoney(from, to, amount);

            try {
                Thread.sleep(1000 + random.nextInt(1001));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
