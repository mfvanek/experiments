package com.mfvanek.money.transfer.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
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
        Objects.requireNonNull(isoCode, "Currency code cannot be null");
        validateCode(isoCode);

        final String code = isoCode.toUpperCase();
        Currency currency = CURRENCIES.get(code);
        if (currency == null) {
            currency = CURRENCIES.computeIfAbsent(code, Currency::new);
        }
        return currency;
    }

    private static void validateCode(String isoCode) {
        if (isoCode.length() != 3) {
            throw new IllegalArgumentException("Currency code have to have length equals to 3");
        }
    }
}
