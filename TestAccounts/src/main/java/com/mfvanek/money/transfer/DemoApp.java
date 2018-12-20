package com.mfvanek.money.transfer;

import com.mfvanek.money.transfer.utils.Context;
import com.mfvanek.money.transfer.utils.generators.AbstractGenerator;
import com.mfvanek.money.transfer.utils.generators.InitialTransactionGenerator;
import com.mfvanek.money.transfer.utils.generators.RandomAccountGenerator;
import com.mfvanek.money.transfer.utils.generators.RandomPartyGenerator;
import com.mfvanek.money.transfer.utils.generators.RandomTransactionGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DemoApp {

    private static final Logger logger = LoggerFactory.getLogger(DemoApp.class);

    /**
     * Use [java -XshowSettings:vm] to see max heap size
     * In order to run this app please consider to increase default java heap size as following:
     * Go to => Run/Debug Configuration => VM Options and set up VM Options = -Xms6G -Xmx8G
     * or
     * try to use environment variable JAVA_TOOL_OPTIONS = -Xms6G -Xmx8G
     */
    public static void main(String[] args) {
        try {
            final Context context = Context.create();
            generateData(context, true, true);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    // TODO make via Builder with optional parameters
    public static void generateData(final Context context, boolean withInitialTransactions, boolean withClientTransactions) {
        final AbstractGenerator partyGenerator = new RandomPartyGenerator(context);
        final List<Long> partyIds = partyGenerator.generate();
        // Our bank already exists
        logger.debug("Party ids count = {}", partyIds.size());
        logger.debug("Party repository size = {}", context.getPartyRepository().size());

        final AbstractGenerator accountGenerator = new RandomAccountGenerator(context, partyIds);
        final List<Long> accountIds = accountGenerator.generate();
        // Our bank account already exists
        logger.debug("Account ids count = {}", accountIds.size());
        logger.debug("Account repository size = {}", context.getAccountsRepository().size());

        if (withInitialTransactions) {
            final AbstractGenerator initialTransactionGenerator = new InitialTransactionGenerator(context, accountIds, true);
            final List<Long> initialTrnIds = initialTransactionGenerator.generate();
            logger.debug("Initial transaction ids count = {}", initialTrnIds.size());
            logger.debug("Transaction repository size = {}", context.getTransactionRepository().size());
            context.getAccountsRepository().validateBalance();

            if (withClientTransactions) {
                final AbstractGenerator transactionGenerator = new RandomTransactionGenerator(context, accountIds, true, 10);
                final List<Long> trnIds = transactionGenerator.generate();
                logger.debug("Transaction ids count = {}", trnIds.size());
                logger.debug("Transaction repository size = {}", context.getTransactionRepository().size());
                context.getAccountsRepository().validateBalance();
            }
        }
    }
}
