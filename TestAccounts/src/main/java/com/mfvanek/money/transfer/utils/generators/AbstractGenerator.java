package com.mfvanek.money.transfer.utils.generators;

import com.mfvanek.money.transfer.utils.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
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
    final Collection<Long> ids;
    private final String message;

    AbstractGenerator(final Context context, final String message) {
        Objects.requireNonNull(context, "Context cannot be null");
        Objects.requireNonNull(message, "Message cannot be null");

        this.counter = new AtomicInteger(0);
        this.context = context;
        this.ids = new ConcurrentLinkedQueue<>();
        this.message = message;
    }

    abstract List<Future<?>> doGenerate(final ExecutorService threadPool);

    public final List<Long> generate() {
        final long timeStart = System.currentTimeMillis();
        try {
            logger.info("Generating {}", message);
            final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            final List<Future<?>> futures = doGenerate(threadPool);
            threadPool.shutdown();
            waitForCompletion(futures);
        } finally {
            final long timeEnd = System.currentTimeMillis();
            logger.info("Generation {} is completed. Time elapsed = {} (ms)", message, timeEnd - timeStart);
        }
        return Collections.unmodifiableList(new ArrayList<>(ids));
    }

    private void waitForCompletion(final List<Future<?>> futures) {
        logger.info("Waiting for completion of {} tasks...", futures.size());
        int processed = 0;
        int batch = 0;
        final int threshold = futures.size() / 10;
        for (final Future<?> future : futures) {
            try {
                future.get(AWAIT_PERIOD, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                logger.error(e.getMessage(), e);
            } finally {
                ++processed;
                ++batch;
            }
            if (batch >= threshold) {
                final int percentage = processed * 100 / futures.size();
                logger.info("Completed {}%...", percentage);
                batch = 0;
            }
        }
    }
}
