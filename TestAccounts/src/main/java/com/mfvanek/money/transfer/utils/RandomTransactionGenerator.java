package com.mfvanek.money.transfer.utils;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Transaction;
import com.mfvanek.money.transfer.interfaces.repositories.AccountsRepository;
import org.apache.commons.lang3.tuple.Pair;
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
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class RandomTransactionGenerator {

    private static final Logger logger = LoggerFactory.getLogger(RandomTransactionGenerator.class);
    private static final int MAX_TRN_COUNT = 100_000;

    //private final AtomicInteger counter = new AtomicInteger(0);
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
            // TODO Limit the size of the blocking queue in order to prevent OutOfMemoryError
            final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (Long accountId : accountIds) {
                Runnable runnableTask = () -> this.generateInitialTransaction(accountId);
                threadPool.submit(runnableTask);
            }
            threadPool.shutdown();
            threadPool.awaitTermination(accountIds.size() / 1000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        final long timeEnd = System.currentTimeMillis();
        System.out.println(String.format("Generation completed. Time elapsed = %d (ms)", timeEnd - timeStart));
        return Collections.unmodifiableList(ids);
    }

    public List<Long> generate() {
        System.out.println("Generating clients transactions");
        final long timeStart = System.currentTimeMillis();
        try {
            final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (int i = 0; i < MAX_TRN_COUNT; ++i) {
                Runnable runnableTask = () -> this.generateTransaction();
                threadPool.submit(runnableTask);
            }
            threadPool.shutdown();
            threadPool.awaitTermination(MAX_TRN_COUNT / 1000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        final long timeEnd = System.currentTimeMillis();
        System.out.println(String.format("Generation completed. Time elapsed = %d (ms)", timeEnd - timeStart));
        return Collections.unmodifiableList(ids);
    }

    private void generateInitialTransaction(final Long creditAccountId) {
        final AccountsRepository accountsRepository = context.getAccountsRepository();
        final Account debit = accountsRepository.getOurBankMainAccount();
        final Account credit = accountsRepository.getById(creditAccountId);
        if (credit.isValid()) {
            final BigDecimal amount = generateAmount(500_000, 1000_000);
            final Transaction transaction = context.getTransactionRepository().add(debit, credit, amount);
            ids.add(transaction.getId());
            transaction.run();
        } else {
            logger.error("Credit account with id = {} not found", creditAccountId);
        }
    }

    private void generateTransaction() {
        final AccountsRepository accountsRepository = context.getAccountsRepository();
        final Pair<Long, Long> randomIds = getRandomAccountIds(accountIds);
        final Account debit = accountsRepository.getById(randomIds.getLeft());
        if (debit.isValid()) {
            final Account credit = accountsRepository.getById(randomIds.getRight());
            if (credit.isValid()) {
                final BigDecimal amount = generateAmount(5_000, 100_000);
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

    private static Pair<Long, Long> getRandomAccountIds(final List<Long> accountIds) {
        final int debitIdx = getRandom().nextInt(accountIds.size());
        final int creditIdx = debitIdx != 0 ? debitIdx - 1 : debitIdx + 1;
        final Pair<Long, Long> result = Pair.of(accountIds.get(debitIdx), accountIds.get(creditIdx));
        logger.trace("Generated accounts pair = {}", result);
        return result;
    }

    /**
     * Gets Random instance in multithread environment
     * @return Random instance
     */
    private static Random getRandom() {
        return ThreadLocalRandom.current();
    }

    private static BigDecimal generateAmount(int min, int max) {
        final BigDecimal amount = BigDecimal.valueOf(min + getRandom().nextInt(max - min), 2);
        logger.trace("Generated amount = {}", amount);
        return amount;
    }
}
