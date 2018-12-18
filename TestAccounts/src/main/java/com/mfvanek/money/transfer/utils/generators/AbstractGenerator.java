package com.mfvanek.money.transfer.utils.generators;

import com.mfvanek.money.transfer.utils.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractGenerator {

    private static final int AWAIT_PERIOD = 10; // in seconds
    static final Logger logger = LoggerFactory.getLogger(AbstractGenerator.class);

    final AtomicInteger counter;
    final Context context;
    final List<Long> ids;
    private final String message;

    AbstractGenerator(final Context context, final int expectedIdsCapacity, final String message) {
        Objects.requireNonNull(context, "Context cannot be null");
        Objects.requireNonNull(message, "Message cannot be null");

        this.counter = new AtomicInteger(0);
        this.context = context;
        this.ids = Collections.synchronizedList(new ArrayList<>(expectedIdsCapacity));
        this.message = message;
    }

    abstract List<Future<?>> doGenerate(final ExecutorService threadPool);

    public final List<Long> generate() {
        final long timeStart = System.currentTimeMillis();
        try {
            logger.info("Generating {}", message);
            try {
                final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                final List<Future<?>> futures = doGenerate(threadPool);
                threadPool.shutdown();
                // threadPool.awaitTermination(AWAIT_PERIOD, TimeUnit.SECONDS);
                logger.info("Waiting for completion...");
                for (final Future<?> future : futures) {
                    future.get(AWAIT_PERIOD, TimeUnit.SECONDS);
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            final long timeEnd = System.currentTimeMillis();
            logger.info("Generation {} is completed. Time elapsed = {} (ms)", message, timeEnd - timeStart);
        }
        return Collections.unmodifiableList(ids);
    }
}
