package com.mfvanek.money.transfer.models;

import com.mfvanek.money.transfer.interfaces.Identifiable;
import com.mfvanek.money.transfer.models.parties.Party;

import java.math.BigDecimal;

public abstract class Account implements Identifiable {

    abstract String getNumber();

    abstract Currency getCurrency();

    abstract BigDecimal getBalance();

    abstract Party getHolder();
}
