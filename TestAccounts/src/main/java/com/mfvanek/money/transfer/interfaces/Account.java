package com.mfvanek.money.transfer.interfaces;

import java.math.BigDecimal;

public interface Account extends Identifiable {

    String getNumber();

    Currency getCurrency();

    BigDecimal getBalance();

    Party getHolder();
}
