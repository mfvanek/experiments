package com.mfvanek.money.transfer.repositories;

import com.mfvanek.money.transfer.exceptions.InvalidBalanceException;
import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Currency;
import com.mfvanek.money.transfer.interfaces.Party;
import com.mfvanek.money.transfer.interfaces.repositories.AccountsRepository;
import com.mfvanek.money.transfer.interfaces.repositories.PartyRepository;
import com.mfvanek.money.transfer.models.accounts.AbstractAccount;
import com.mfvanek.money.transfer.models.currencies.BaseCurrency;
import com.mfvanek.money.transfer.utils.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultAccountsRepository implements AccountsRepository {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAccountsRepository.class);
    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(100_000_000.00d);

    private final AtomicLong counter;
    private final ConcurrentMap<Long, Account> accounts;
    private final PartyRepository partyRepository;
    private final Long ourBankAccountId;

    public DefaultAccountsRepository(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
        this.counter = new AtomicLong(0L);
        this.accounts = new ConcurrentHashMap<>();
        final Account ourBankAccount = addOurBankAccount("20202810100000010001", INITIAL_BALANCE);
        this.ourBankAccountId = ourBankAccount.getId();
    }

    @Override
    public Account getById(Long id) {
        return accounts.getOrDefault(id, getInvalid());
    }

    @Override
    public Account getInvalid() {
        return AbstractAccount.getInvalid();
    }

    @Override
    public Account addOurBankAccount(String number, BigDecimal balance) {
        return addOurBankAccount(BaseCurrency.getDefault(), number, balance);
    }

    @Override
    public Account addOurBankAccount(Currency currency, String number, BigDecimal balance) {
        // TODO Add unique index for account number + currency
        final Account account = AbstractAccount.makeActiveAccount(
                counter.incrementAndGet(), currency, number, partyRepository.getOurBank(), balance);
        accounts.putIfAbsent(account.getId(), account);
        return account;
    }

    @Override
    public BigDecimal getInitialBalance() {
        return INITIAL_BALANCE;
    }

    @Override
    public Account getOurBankMainAccount() {
        // by design
        return getById(ourBankAccountId);
    }

    @Override
    public Account addPassiveAccount(Currency currency, String number, Party holder) {
        final Account account = AbstractAccount.makePassiveAccount(counter.incrementAndGet(), currency, number, holder);
        accounts.putIfAbsent(account.getId(), account);
        return account;
    }

    @Override
    public Account addPassiveAccount(String number, Party holder) {
        return addPassiveAccount(BaseCurrency.getDefault(), number, holder);
    }

    @Override
    public int size() {
        return accounts.size();
    }

    @Override
    public void validateBalance() {
        final BigDecimal expected = getInitialBalance();
        BigDecimal totalSum = BigDecimal.ZERO;
        for (Account a : accounts.values()) {
            Validator.validateAmountNotNegative(a);
            totalSum = totalSum.add(a.getBalance());
        }
        if (totalSum.compareTo(expected) != 0) {
            throw new InvalidBalanceException(expected, totalSum);
        }
        logger.debug("Balance is valid! {} == {}", expected, totalSum);
    }
}
