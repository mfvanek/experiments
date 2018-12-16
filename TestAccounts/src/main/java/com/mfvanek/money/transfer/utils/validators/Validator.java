package com.mfvanek.money.transfer.utils.validators;

import com.mfvanek.money.transfer.interfaces.Account;

import java.math.BigDecimal;

public class Validator {

    public static void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    public static void validateCurrencyIsTheSame(Account debit, Account credit) {
        if (!debit.getCurrency().equals(credit.getCurrency())) {
            throw new UnsupportedOperationException(
                    String.format("Multi-currency operations are not supported. Debit account = %s; credit account = %s", debit, credit));
        }
    }
}
