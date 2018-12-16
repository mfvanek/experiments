package com.mfvanek.money.transfer.utils;

import com.mfvanek.money.transfer.interfaces.Party;
import com.mfvanek.money.transfer.interfaces.repositories.PartyRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomPartyGenerator {

    private static final Logger logger = LoggerFactory.getLogger(RandomPartyGenerator.class);
    private static final int TOP_BOUND = 1000;

    private final AtomicInteger counter = new AtomicInteger(0);
    private final List<Long> ids = Collections.synchronizedList(new ArrayList<>(TOP_BOUND));
    private final Context context;

    public RandomPartyGenerator(Context context) {
        this.context = context;
    }

    public List<Long> generate() {
        System.out.println("Generating parties");
        final long timeStart = System.currentTimeMillis();
        try {
            final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (int i = 0; i < TOP_BOUND; ++i) {
                threadPool.submit(this::generateParty);
            }
            threadPool.shutdown();
            threadPool.awaitTermination(TOP_BOUND / 1000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        final long timeEnd = System.currentTimeMillis();
        System.out.println(String.format("Generation completed. Time elapsed = %d (ms)", timeEnd - timeStart));
        return Collections.unmodifiableList(ids);
    }

    private void generateParty() {
        final PartyRepository partyRepository = context.getPartyRepository();
        final int idx = counter.incrementAndGet();
        if (idx % 2 == 0) {
            Party pt = partyRepository.addLegalPerson(generateTaxId(idx, 10), generateCompanyName(idx));
            ids.add(pt.getId());
        } else {
            Party pt = partyRepository.addPrivatePerson(generateTaxId(idx, 12), generateFirstName(idx), "John");
            ids.add(pt.getId());
        }
    }

    private static String generateCompanyName(final int idx) {
        if (idx % 4 == 0) {
            return "Apple" + idx;
        } else {
            if (idx % 6 == 0) {
                return "Google" + idx;
            }
        }
        return "Amazon" + idx;
    }

    private static String generateFirstName(final int idx) {
        if (idx % 3 == 0) {
            return "Page" + idx;
        } else {
            if (idx % 5 == 0) {
                return "Bezos" + idx;
            } else {
                if (idx % 7 == 0) {
                    return "Gates" + idx;
                }
            }
        }
        return "Jobs" + idx;
    }

    private static String generateTaxId(final int idx, final int length) {
        return StringUtils.leftPad(String.valueOf(idx), length, '0');
    }
}
