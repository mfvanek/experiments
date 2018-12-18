package com.mfvanek.money.transfer.utils.generators;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Transaction;
import com.mfvanek.money.transfer.interfaces.repositories.AccountsRepository;
import com.mfvanek.money.transfer.utils.Context;
import com.mfvanek.money.transfer.utils.TransactionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class InitialTransactionGenerator extends AbstractGenerator {

    private final List<Long> accountIds;

    public InitialTransactionGenerator(Context context, List<Long> accountIds) {
        super(context, accountIds != null ? accountIds.size() : 100, "initial transactions");
        Objects.requireNonNull(accountIds, "Ids list cannot be null");
        this.accountIds = accountIds;
    }

    @Override
    void doGenerate(final ExecutorService threadPool) {
        for (Long accountId : accountIds) {
            Runnable runnableTask = () -> this.generateInitialTransaction(accountId);
            threadPool.submit(runnableTask);
        }
    }

    private void generateInitialTransaction(final Long creditAccountId) {
        final AccountsRepository accountsRepository = context.getAccountsRepository();
        final Account debit = accountsRepository.getOurBankMainAccount();
        final Account credit = accountsRepository.getById(creditAccountId);
        if (credit.isValid()) {
            final BigDecimal amount = TransactionUtils.generateAmount(500_000, 1000_000);
            final Transaction transaction = context.getTransactionRepository().add(debit, credit, amount);
            ids.add(transaction.getId());
            transaction.run();
        } else {
            logger.error("Credit account with id = {} not found", creditAccountId);
        }
    }
}
