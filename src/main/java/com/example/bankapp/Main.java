package com.example.bankapp;

import com.example.bankapp.model.Account;
import com.example.bankapp.service.AccountService;
import com.example.bankapp.service.TransferTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            accounts.add(new Account(UUID.randomUUID().toString(), 10000));
        }

        AccountService accountService = new AccountService(accounts);

        Thread t1 = new Thread(new TransferTask(accountService, accounts));
        Thread t2 = new Thread(new TransferTask(accountService, accounts));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (Account account : accounts) {
            System.out.println("Account ID: " + account.getId() + ", Balance: " + account.getMoney());
        }
    }
}