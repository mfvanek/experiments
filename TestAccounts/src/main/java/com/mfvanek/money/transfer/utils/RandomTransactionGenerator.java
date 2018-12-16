package com.mfvanek.money.transfer.utils;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Transaction;
import com.mfvanek.money.transfer.interfaces.repositories.AccountsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomTransactionGenerator {

    private static final Logger logger = LoggerFactory.getLogger(RandomTransactionGenerator.class);

    private final AtomicInteger counter = new AtomicInteger(0);
    private final List<Long> ids;
    private final Context context;
    private final List<Long> accountIds;

    public RandomTransactionGenerator(Context context, List<Long> accountIds) {
        Objects.requireNonNull(context, "Context cannot be null");
        Objects.requireNonNull(accountIds, "Ids list cannot be null");

        this.context = context;
        this.accountIds = accountIds;
        this.ids = Collections.synchronizedList(new ArrayList<>(accountIds.size()));
    }

    public List<Long> generateInitial() {
        System.out.println("Generating initial transactions");
        final long timeStart = System.currentTimeMillis();
        try {
            final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (Long accountId : accountIds) {
                Runnable runnableTask = () -> this.generateInitialTransaction(accountId);
                threadPool.submit(runnableTask);
            }
            threadPool.shutdown();
            threadPool.awaitTermination(accountIds.size() / 100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        final long timeEnd = System.currentTimeMillis();
        System.out.println(String.format("Generation completed. Time elapsed = %d (ms)", timeEnd - timeStart));
        return Collections.unmodifiableList(ids);
    }
/*
    public List<Long> generate() {
        System.out.println("Generating transactions");
        final long timeStart = System.currentTimeMillis();
        try {
            final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (Long partyId : partyIds) {
                Runnable runnableTask = () -> this.generateAccount(partyId);
                threadPool.submit(runnableTask);
            }
            threadPool.shutdown();
            threadPool.awaitTermination(partyIds.size() / 100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        final long timeEnd = System.currentTimeMillis();
        System.out.println(String.format("Generation completed. Time elapsed = %d (ms)", timeEnd - timeStart));
        return Collections.unmodifiableList(ids);
    }
*/
    private void generateInitialTransaction(final Long creditAccountId) {
        final AccountsRepository accountsRepository = context.getAccountsRepository();
        final Account debit = accountsRepository.getOurBankMainAccount();
        final Account credit = accountsRepository.getById(creditAccountId);
        if (credit.isValid()) {
            final BigDecimal amount = generateAmount(50_000, 100_000);
            logger.trace("Generated amount = {}", amount);
            final Transaction transaction = context.getTransactionRepository().add(debit, credit, amount);
            ids.add(transaction.getId());
        }
    }

    private static BigDecimal generateAmount(int min, int max) {
        return BigDecimal.valueOf(min + new Random().nextInt(max - min), 2);
    }
}
