package com.mfvanek.money.transfer.models.accounts;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Party;
import com.mfvanek.money.transfer.models.currencies.Currency;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class AbstractAccount implements Account {

    private final Long id;
    private final Currency currency;
    private final String number;
    private final Party holder;
    private BigDecimal balance;

    private AbstractAccount(Long id, Currency currency, String number, Party holder, BigDecimal balance) {
        Objects.requireNonNull(id, "Id cannot be null");
        Objects.requireNonNull(currency, "Currency cannot be null");
        Objects.requireNonNull(number, "Number cannot be null");
        Objects.requireNonNull(holder, "Holder cannot be null");
        Objects.requireNonNull(balance, "Balance cannot be null");

        this.id = id;
        this.currency = currency;
        this.number = number;
        this.holder = holder;
        this.balance = balance;
    }

    AbstractAccount(Long id, Currency currency, String number, Party holder) {
        this(id, currency, number, holder, BigDecimal.ZERO);
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
        // TODO synchronization
        return balance;
    }

    @Override
    public final Party getHolder() {
        return holder;
    }

    public static Account getInvalid() {
        return InvalidAccount.getInstance();
    }
}
