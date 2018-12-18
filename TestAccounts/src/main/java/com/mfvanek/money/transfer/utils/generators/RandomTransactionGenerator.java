package com.mfvanek.money.transfer.utils.generators;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Transaction;
import com.mfvanek.money.transfer.interfaces.repositories.AccountsRepository;
import com.mfvanek.money.transfer.utils.Context;
import com.mfvanek.money.transfer.utils.TransactionUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class RandomTransactionGenerator extends AbstractGenerator {

    private static final int MAX_TRN_COUNT = 100_000;
    private final List<Long> accountIds;

    public RandomTransactionGenerator(Context context, List<Long> accountIds) {
        super(context, MAX_TRN_COUNT, "clients transactions");
        Objects.requireNonNull(accountIds, "Ids list cannot be null");
        this.accountIds = accountIds;
    }

    @Override
    void doGenerate(final ExecutorService threadPool) {
        for (int i = 0; i < MAX_TRN_COUNT; ++i) {
            threadPool.submit(this::generateTransaction);
        }
    }

    private void generateTransaction() {
        final AccountsRepository accountsRepository = context.getAccountsRepository();
        final Pair<Long, Long> randomIds = TransactionUtils.getRandomAccountIds(accountIds);
        final Account debit = accountsRepository.getById(randomIds.getLeft());
        if (debit.isValid()) {
            final Account credit = accountsRepository.getById(randomIds.getRight());
            if (credit.isValid()) {
                final BigDecimal amount = TransactionUtils.generateAmount(5_000, 100_000);
                final Transaction transaction = context.getTransactionRepository().add(debit, credit, amount);
                ids.add(transaction.getId());
                transaction.run();
            } else {
                logger.error("Credit account with id = {} not found", randomIds.getRight());
            }
        } else {
            logger.error("Debit account with id = {} not found", randomIds.getLeft());
        }
    }
}
