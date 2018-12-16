package com.mfvanek.money.transfer.models.accounts;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Currency;
import com.mfvanek.money.transfer.interfaces.Party;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractAccount implements Account {

    private final Long id;
    private final Currency currency;
    private final String number;
    private final Party holder;
    private final boolean active;
    private BigDecimal balance;
    private final ReadWriteLock readWriteLock;

    private AbstractAccount(Long id, Currency currency, String number,
                            Party holder, boolean active, BigDecimal balance) {
        Objects.requireNonNull(id, "Id cannot be null");
        Objects.requireNonNull(currency, "Currency cannot be null");
        Objects.requireNonNull(number, "Number cannot be null");
        Objects.requireNonNull(holder, "Holder cannot be null");
        Objects.requireNonNull(balance, "Balance cannot be null");
        validateAmount(balance);

        this.id = id;
        this.currency = currency;
        this.number = number;
        this.holder = holder;
        this.active = active;
        this.balance = balance;
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    AbstractAccount(Long id, Currency currency, String number, Party holder, boolean active) {
        this(id, currency, number, holder, active, BigDecimal.ZERO);
    }

    @Override
    public final Long getId() {
        return id;
    }

    @Override
    public final String getNumber() {
        return number;
    }

    @Override
    public final Currency getCurrency() {
        return currency;
    }

    @Override
    public final BigDecimal getBalance() {
        final Lock lock = readWriteLock.readLock();
        try {
            lock.lock();
            return balance;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean debit(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        validateAmount(amount);

        final Lock lock = readWriteLock.writeLock();
        try {
            lock.lock();
            if (balance.compareTo(amount) > 0) {
                balance = balance.subtract(amount);
                return true;
            }
        } finally {
            lock.unlock();
        }
        return false;
    }

    @Override
    public boolean credit(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        validateAmount(amount);

        final Lock lock = readWriteLock.writeLock();
        try {
            lock.lock();
            balance = balance.add(amount);
        } finally {
            lock.unlock();
        }
        return true;
    }

    @Override
    public final Party getHolder() {
        return holder;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    private static void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    public static Account getInvalid() {
        return InvalidAccount.getInstance();
    }

    public static Account makeActiveAccount(Long id, Currency currency, String number, Party holder) {
        return RussianAccount.makeActiveBalance(id, currency, number, holder);
    }

    public static Account makePassiveAccount(Long id, Currency currency, String number, Party holder) {
        return RussianAccount.makeActiveBalance(id, currency, number, holder);
    }

    public static Account makeActiveAccount(Long id, String number, Party holder) {
        return RussianAccount.makeActiveRouble(id, number, holder);
    }

    public static Account makePassiveAccount(Long id, String number, Party holder) {
        return RussianAccount.makeActiveRouble(id, number, holder);
    }
}
