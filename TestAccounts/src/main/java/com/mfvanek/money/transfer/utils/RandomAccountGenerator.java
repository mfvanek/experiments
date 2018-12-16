package com.mfvanek.money.transfer.utils;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.repositories.AccountsRepository;
import com.mfvanek.money.transfer.interfaces.Party;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomAccountGenerator {

    private static final Logger logger = LoggerFactory.getLogger(RandomAccountGenerator.class);
    private static final int ACCOUNTS_FOR_CLIENT = 2;

    private final AtomicInteger counter = new AtomicInteger(0);
    private final List<Long> ids;
    private final Context context;
    private final List<Long> partyIds;

    public RandomAccountGenerator(Context context, List<Long> partyIds) {
        Objects.requireNonNull(context, "Context cannot be null");
        Objects.requireNonNull(partyIds, "Ids list cannot be null");

        this.ids = Collections.synchronizedList(new ArrayList<>(partyIds.size() * ACCOUNTS_FOR_CLIENT));
        this.context = context;
        this.partyIds = partyIds;
    }

    public List<Long> generate() {
        System.out.println("Generating accounts");
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

    private void generateAccount(Long partyId) {
        final AccountsRepository accountsRepository = context.getAccountsRepository();
        final Party pt = context.getPartyRepository().getById(partyId);
        if (pt.isValid()) {
            for (int i = 0; i < ACCOUNTS_FOR_CLIENT; ++i) {
                final int idx = counter.incrementAndGet();
                final Account a = accountsRepository.addPassiveAccount(generateNumber(idx), pt);
                ids.add(a.getId());
            }
        } else {
            logger.error("Party with id = {} not found", partyId);
        }
    }

    private static String generateNumber(final int idx) {
        return "4080281010" + StringUtils.leftPad(String.valueOf(idx), 10, '0');
    }
}
