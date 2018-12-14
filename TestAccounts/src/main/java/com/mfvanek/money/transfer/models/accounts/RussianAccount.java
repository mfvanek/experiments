package com.mfvanek.money.transfer.models.accounts;

import com.mfvanek.money.transfer.models.Currency;
import com.mfvanek.money.transfer.models.parties.Party;

import java.util.Objects;

public final class RussianAccount extends Account {

    private final Chapter chapter;

    private RussianAccount(Long id, Chapter chapter, Currency currency, String number, Party holder) {
        super(id, currency, number, holder);
        Objects.requireNonNull(chapter, "Chapter cannot be null");
        validateNumber(number);
        this.chapter = chapter;
    }

    public final Chapter getChapter() {
        return chapter;
    }

    private static void validateNumber(String number) {
        if (number.length() != 20) {
            throw new IllegalArgumentException("Account number must contain 20 characters");
        }
    }

    public static RussianAccount newDefault(Long id, String number, Party holder) {
        return new RussianAccount(id, Chapter.BALANCE, Currency.valueOf("RUB"), number, holder);
    }
}
