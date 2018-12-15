package com.mfvanek.money.transfer.repositories;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.AccountsRepository;
import com.mfvanek.money.transfer.interfaces.Currency;
import com.mfvanek.money.transfer.interfaces.Party;
import com.mfvanek.money.transfer.interfaces.PartyRepository;
import com.mfvanek.money.transfer.models.accounts.AbstractAccount;
import com.mfvanek.money.transfer.models.currencies.BaseCurrency;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultAccountsRepository implements AccountsRepository {

    private final AtomicLong counter = new AtomicLong(0L);
    private final ConcurrentMap<Long, Account> ACCOUNTS = new ConcurrentHashMap<>();
    private final PartyRepository partyRepository;
    private final Long ourBankAccountId;

    public DefaultAccountsRepository(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
        final Account ourBankAccount = addOurBankAccount("20202810100000010001");
        this.ourBankAccountId = ourBankAccount.getId();
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
    public Account addOurBankAccount(String number) {
        return addOurBankAccount(BaseCurrency.getDefault(), number);
    }

    @Override
    public Account addOurBankAccount(Currency currency, String number) {
        // TODO Add unique index for account number + currency
        final Account account = AbstractAccount.makeActiveAccount(counter.incrementAndGet(), currency, number, partyRepository.getOurBank());
        ACCOUNTS.putIfAbsent(account.getId(), account);
        return account;
    }

    @Override
    public Account getOurBankMainAccount() {
        // by design
        return getById(ourBankAccountId);
    }

    @Override
    public Account addPassiveAccount(Currency currency, String number, Party holder) {
        final Account account = AbstractAccount.makePassiveAccount(counter.incrementAndGet(), currency, number, holder);
        ACCOUNTS.putIfAbsent(account.getId(), account);
        return account;
    }

    @Override
    public Account addPassiveAccount(String number, Party holder) {
        return addPassiveAccount(BaseCurrency.getDefault(), number, holder);
    }

    @Override
    public int size() {
        return ACCOUNTS.size();
    }
}
