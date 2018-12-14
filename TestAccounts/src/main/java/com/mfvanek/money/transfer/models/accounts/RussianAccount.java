package com.mfvanek.money.transfer.models.accounts;

import com.mfvanek.money.transfer.enums.Chapter;
import com.mfvanek.money.transfer.interfaces.Currency;
import com.mfvanek.money.transfer.interfaces.Party;
import com.mfvanek.money.transfer.models.currencies.BaseCurrency;

import java.util.Objects;

public final class RussianAccount extends AbstractAccount {

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
            throw new IllegalArgumentException("AbstractAccount number must contain 20 characters");
        }
    }

    public static RussianAccount makeBalance(Long id, Currency currency, String number, Party holder) {
        return new RussianAccount(id, Chapter.BALANCE, currency, number, holder);
    }

    public static RussianAccount makeRouble(Long id, String number, Party holder) {
        return makeBalance(id, BaseCurrency.valueOf("RUB"), number, holder);
    }
}
