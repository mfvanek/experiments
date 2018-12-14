package com.mfvanek.money.transfer.repositories;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.AccountsRepository;
import com.mfvanek.money.transfer.models.accounts.AbstractAccount;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultAccountsRepository implements AccountsRepository {

    private AtomicLong counter = new AtomicLong(0L);
    private final ConcurrentMap<Long, Account> ACCOUNTS = new ConcurrentHashMap<>();

    @Override
    public Account getById(Long id) {
        return ACCOUNTS.getOrDefault(id, getInvalid());
    }

    @Override
    public Account getInvalid() {
        return AbstractAccount.getInvalid();
    }
}
