package com.mfvanek.money.transfer.models.currencies;

import com.mfvanek.money.transfer.interfaces.Validatable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ToString
@EqualsAndHashCode
public class Currency implements Validatable {

    private static final int ISO_CODE_LENGTH = 3;
    private static final ConcurrentMap<String, Currency> CURRENCIES = new ConcurrentHashMap<>();

    @Getter
    private final String isoCode;

    Currency(String isoCode) {
        this.isoCode = isoCode;
    }

    @Override
    public boolean isValid() {
        return isoCode.length() == ISO_CODE_LENGTH;
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
        if (isoCode.length() != ISO_CODE_LENGTH) {
            throw new IllegalArgumentException("Currency code have to have length equals to " + ISO_CODE_LENGTH);
        }
    }

    public static Currency getInvalid() {
        return InvalidCurrency.getInstance();
    }
}
