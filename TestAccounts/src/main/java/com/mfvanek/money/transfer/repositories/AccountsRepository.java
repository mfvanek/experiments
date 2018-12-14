package com.mfvanek.money.transfer.repositories;

import com.mfvanek.money.transfer.models.accounts.Account;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class AccountsRepository {

    private static AtomicLong counter = new AtomicLong(0L);
    private static final ConcurrentMap<Long, Account> ACCOUNTS = new ConcurrentHashMap<>();

    public static Account getById(Long id) {
        return ACCOUNTS.getOrDefault(id, null);
    }
}
