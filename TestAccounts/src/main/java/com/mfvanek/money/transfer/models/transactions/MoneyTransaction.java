package com.mfvanek.money.transfer.models.transactions;

import com.mfvanek.money.transfer.consts.Consts;
import com.mfvanek.money.transfer.enums.TransactionState;
import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Transaction;
import com.mfvanek.money.transfer.utils.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MoneyTransaction implements Transaction {

    private static final Logger logger = LoggerFactory.getLogger(MoneyTransaction.class);

    private final Long id;
    private final Account debit;
    private final Account credit;
    private final BigDecimal amount;
    private TransactionState state;
    private final ReadWriteLock rwLock;

    MoneyTransaction(Long id, Account debit, Account credit, BigDecimal amount) {
        Objects.requireNonNull(id, "Id cannot be null");
        Objects.requireNonNull(debit, "Debit account cannot be null");
        Objects.requireNonNull(credit, "Credit account cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");
        Validator.validateAmount(amount);
        // TODO Support multi-currency operations
        Validator.validateCurrencyIsTheSame(debit, credit);

        this.id = id;
        this.debit = debit;
        this.credit = credit;
        this.amount = amount;
        this.state = TransactionState.NEW;
        this.rwLock = new ReentrantReadWriteLock();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Account getDebit() {
        return debit;
    }

    @Override
    public Account getCredit() {
        return credit;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public TransactionState getState() {
        final Lock lock = rwLock.readLock();
        try {
            lock.lock();
            return state;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean run() {
        final Lock lock = rwLock.writeLock();
        try {
            if (lock.tryLock(Consts.TRANSACTION_WAIT_INTERVAL, TimeUnit.MILLISECONDS)) {
                try {
                    if (state != TransactionState.COMPLETED) {
                        changeState();
                        return doRun();
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    private boolean doRun() {
        final Lock debitLock = debit.writeLock();
        try {
            if (debitLock.tryLock(Consts.ACCOUNT_WAIT_INTERVAL, TimeUnit.MILLISECONDS)) {
                try {
                    final Lock creditLock = credit.writeLock();
                    if (creditLock.tryLock(Consts.ACCOUNT_WAIT_INTERVAL, TimeUnit.MILLISECONDS)) {
                        try {
                            if (debit.debit(amount)) {
                                if (credit.credit(amount)) {
                                    state = TransactionState.COMPLETED;
                                    return true;
                                }
                            }
                            state = TransactionState.INSUFFICIENT_FUNDS;
                        } finally {
                            creditLock.unlock();
                        }
                    } else {
                        state = TransactionState.CONCURRENCY_ERROR;
                    }
                } finally {
                    debitLock.unlock();
                }
            } else {
                state = TransactionState.CONCURRENCY_ERROR;
            }
        } catch (InterruptedException e) {
            state = TransactionState.CONCURRENCY_ERROR;
            logger.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    private void changeState() {
        switch (state) {
            case INSUFFICIENT_FUNDS:
            case CONCURRENCY_ERROR:
                state = TransactionState.RESTARTED;
                break;
        }
    }

    public static Transaction getInvalid() {
        return InvalidTransaction.getInstance();
    }

    public static Transaction make(Long id, Account debit, Account credit, BigDecimal amount) {
        return new MoneyTransaction(id, debit, credit, amount);
    }
}
