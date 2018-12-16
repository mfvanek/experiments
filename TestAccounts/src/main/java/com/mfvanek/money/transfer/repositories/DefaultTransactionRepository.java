package com.mfvanek.money.transfer.repositories;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Transaction;
import com.mfvanek.money.transfer.interfaces.repositories.TransactionRepository;
import com.mfvanek.money.transfer.models.transactions.MoneyTransaction;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultTransactionRepository implements TransactionRepository {

    private final AtomicLong counter = new AtomicLong(0L);
    private final ConcurrentMap<Long, Transaction> transactions = new ConcurrentHashMap<>();

    @Override
    public Transaction getById(Long id) {
        return transactions.getOrDefault(id, getInvalid());
    }

    @Override
    public Transaction getInvalid() {
        return MoneyTransaction.getInvalid();
    }

    @Override
    public int size() {
        return transactions.size();
    }

    @Override
    public Transaction add(Account debit, Account credit, BigDecimal amount) {
        final Transaction transaction = MoneyTransaction.make(counter.incrementAndGet(), debit, credit, amount);
        transactions.putIfAbsent(transaction.getId(), transaction);
        return transaction;
    }
}
