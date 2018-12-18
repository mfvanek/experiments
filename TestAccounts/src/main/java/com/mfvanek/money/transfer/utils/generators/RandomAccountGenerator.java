package com.mfvanek.money.transfer.utils.generators;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Party;
import com.mfvanek.money.transfer.interfaces.repositories.AccountsRepository;
import com.mfvanek.money.transfer.utils.Context;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class RandomAccountGenerator extends AbstractGenerator {

    private static final int ACCOUNTS_FOR_CLIENT = 2;
    private final List<Long> partyIds;

    public RandomAccountGenerator(Context context, List<Long> partyIds) {
        super(context, partyIds != null ? partyIds.size() * ACCOUNTS_FOR_CLIENT : 100, "accounts");
        Objects.requireNonNull(partyIds, "Ids list cannot be null");
        this.partyIds = partyIds;
    }

    @Override
    void doGenerate(final ExecutorService threadPool) {
        for (Long partyId : partyIds) {
            Runnable runnableTask = () -> this.generateAccount(partyId);
            threadPool.submit(runnableTask);
        }
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