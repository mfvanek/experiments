package com.mfvanek.money.transfer.models.accounts;

import com.mfvanek.money.transfer.interfaces.Identifiable;
import com.mfvanek.money.transfer.interfaces.Party;
import com.mfvanek.money.transfer.models.Currency;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Account implements Identifiable {

    private final Long id;
    private final Currency currency;
    private final String number;
    private final Party holder;
    private BigDecimal balance;

    private Account(Long id, Currency currency, String number, Party holder, BigDecimal balance) {
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

    Account(Long id, Currency currency, String number, Party holder) {
        this(id, currency, number, holder, BigDecimal.ZERO);
    }

    @Override
    public final Long getId() {
        return id;
    }

    public final String getNumber() {
        return number;
    }

    public final Currency getCurrency() {
        return currency;
    }

    public final BigDecimal getBalance() {
        // TODO synchronization
        return balance;
    }

    public final Party getHolder() {
        return holder;
    }
}
