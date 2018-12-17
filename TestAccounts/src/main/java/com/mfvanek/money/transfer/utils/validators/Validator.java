package com.mfvanek.money.transfer.utils.validators;

import com.mfvanek.money.transfer.interfaces.Account;

import java.math.BigDecimal;

public class Validator {

    public static void validateAmountNotNegative(Account account) {
        if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative for " + account);
        }
    }

    public static void validateAmountNotNegative(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    public static void validateAmountPositive(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    public static void validateCurrencyIsTheSame(Account debit, Account credit) {
        if (!debit.getCurrency().equals(credit.getCurrency())) {
            throw new UnsupportedOperationException(
                    String.format("Multi-currency operations are not supported. Debit account = %s; credit account = %s", debit, credit));
        }
    }

    public static void validateAccountsAreValid(Account debit, Account credit) {
        if (debit.isNotValid()) {
            throw new IllegalArgumentException("Debit account must be valid");
        }

        if (credit.isNotValid()) {
            throw new IllegalArgumentException("Credit account must be valid");
        }
    }

    public static void validateAccountIsDifferent(Account debit, Account credit) {
        if (debit.equals(credit)) {
            throw new IllegalArgumentException("Accounts must be different");
        }
    }
}
