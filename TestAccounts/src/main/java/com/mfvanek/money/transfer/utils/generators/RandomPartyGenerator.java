package com.mfvanek.money.transfer.utils.generators;

import com.mfvanek.money.transfer.interfaces.Party;
import com.mfvanek.money.transfer.interfaces.repositories.PartyRepository;
import com.mfvanek.money.transfer.utils.Context;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ExecutorService;

public class RandomPartyGenerator extends AbstractGenerator {

    private static final int TOP_BOUND = 1000;

    public RandomPartyGenerator(Context context) {
        super(context, TOP_BOUND, "parties");
    }

    @Override
    void doGenerate(final ExecutorService threadPool) {
        for (int i = 0; i < TOP_BOUND; ++i) {
            threadPool.submit(this::generateParty);
        }
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
