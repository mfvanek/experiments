package com.mfvanek.money.transfer.interfaces;

import java.math.BigDecimal;

public interface Transaction {

    boolean transfer(Account debit, Account credit, BigDecimal amount);
}
