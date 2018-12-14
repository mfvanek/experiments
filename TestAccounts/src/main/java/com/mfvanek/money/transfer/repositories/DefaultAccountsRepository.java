package com.mfvanek.money.transfer.repositories;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.AccountsRepository;
import com.mfvanek.money.transfer.interfaces.Currency;
import com.mfvanek.money.transfer.interfaces.PartyRepository;
import com.mfvanek.money.transfer.models.accounts.AbstractAccount;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultAccountsRepository implements AccountsRepository {

    private final AtomicLong counter = new AtomicLong(0L);
    private final ConcurrentMap<Long, Account> ACCOUNTS = new ConcurrentHashMap<>();
    private final PartyRepository partyRepository;

    public DefaultAccountsRepository(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    @Override
    public Account getById(Long id) {
        return ACCOUNTS.getOrDefault(id, getInvalid());
    }

    @Override
    public Account getInvalid() {
        return AbstractAccount.getInvalid();
    }

    @Override
    public Account addOurBankAccount(Currency currency, String number) {
        final Account account = AbstractAccount.makeAccount(counter.incrementAndGet(), currency, number, partyRepository.getOurBank());
        ACCOUNTS.putIfAbsent(account.getId(), account);
        return account;
    }
}
