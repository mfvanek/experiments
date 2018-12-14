package com.mfvanek.money.transfer.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ToString
@EqualsAndHashCode
public final class Currency {

    private static final ConcurrentMap<String, Currency> CURRENCIES = new ConcurrentHashMap<>();

    @Getter
    private final String isoCode;

    private Currency(String isoCode) {
        this.isoCode = isoCode;
    }

    public static Currency valueOf(String isoCode) {
        if (isoCode == null) {
            throw new IllegalArgumentException("Currency code cannot be null");
        }

        if (isoCode.length() != 3) {
            throw new IllegalArgumentException("Currency code have to have length equals to 3");
        }

        final String code = isoCode.toUpperCase();
        Currency currency = CURRENCIES.get(code);
        if (currency == null) {
            currency = CURRENCIES.putIfAbsent(code, new Currency(code));
        }
        return currency;
    }
}
