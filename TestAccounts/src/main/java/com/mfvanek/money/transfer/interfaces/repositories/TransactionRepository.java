package com.mfvanek.money.transfer.interfaces.repositories;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Transaction;

import java.math.BigDecimal;

public interface TransactionRepository extends Repository<Transaction> {

    Transaction add(Account debit, Account credit, BigDecimal amount);
}
