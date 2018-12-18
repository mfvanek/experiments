package com.mfvanek.money.transfer.utils.generators;

import com.mfvanek.money.transfer.utils.Context;
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

    abstract void doGenerate(final ExecutorService threadPool);

    public final List<Long> generate() {
        logger.debug("Generating " + message);
        final long timeStart = System.currentTimeMillis();
        try {
            final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            doGenerate(threadPool);
            threadPool.shutdown();
            threadPool.awaitTermination(AWAIT_PERIOD, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        final long timeEnd = System.currentTimeMillis();
        logger.debug("Generation is completed. Time elapsed = {} (ms)", timeEnd - timeStart);
        return Collections.unmodifiableList(ids);
    }
}
