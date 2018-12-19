package com.mfvanek.money.transfer;

import com.mfvanek.money.transfer.interfaces.repositories.AccountsRepository;
import com.mfvanek.money.transfer.interfaces.repositories.PartyRepository;
import com.mfvanek.money.transfer.interfaces.repositories.TransactionRepository;
import com.mfvanek.money.transfer.repositories.DefaultAccountsRepository;
import com.mfvanek.money.transfer.repositories.DefaultPartyRepository;
import com.mfvanek.money.transfer.repositories.DefaultTransactionRepository;
import com.mfvanek.money.transfer.utils.Context;
import com.mfvanek.money.transfer.utils.generators.AbstractGenerator;
import com.mfvanek.money.transfer.utils.generators.InitialTransactionGenerator;
import com.mfvanek.money.transfer.utils.generators.RandomAccountGenerator;
import com.mfvanek.money.transfer.utils.generators.RandomPartyGenerator;
import com.mfvanek.money.transfer.utils.generators.RandomTransactionGenerator;

import java.util.List;

public class DemoApp {

    /**
     * Use [java -XshowSettings:vm] to see max heap size
     * In order to run this app please consider to increase default java heap size as following:
     * Go to => Run/Debug Configuration => VM Options and set up VM Options = -Xms6G -Xmx8G
     * or
     * try to use environment variable JAVA_TOOL_OPTIONS = -Xms6G -Xmx8G
     */
    public static void main(String[] args) {
        try {
            final PartyRepository partyRepository = new DefaultPartyRepository();
            final AccountsRepository accountsRepository = new DefaultAccountsRepository(partyRepository);
            final TransactionRepository transactionRepository = new DefaultTransactionRepository();
            final Context context = new Context(partyRepository, accountsRepository, transactionRepository);

            final AbstractGenerator partyGenerator = new RandomPartyGenerator(context);
            final List<Long> partyIds = partyGenerator.generate();
            // Our bank already exists
            System.out.println("Party ids count = " + partyIds.size());
            System.out.println("Party repository size = " + partyRepository.size());

            final AbstractGenerator accountGenerator = new RandomAccountGenerator(context, partyIds);
            final List<Long> accountIds = accountGenerator.generate();
            // Our bank account already exists
            System.out.println("Account ids count = " + accountIds.size());
            System.out.println("Account repository size = " + accountsRepository.size());

            final AbstractGenerator initialTransactionGenerator = new InitialTransactionGenerator(context, accountIds, true);
            final List<Long> initialTrnIds = initialTransactionGenerator.generate();
            System.out.println("Initial transaction ids count = " + initialTrnIds.size());
            System.out.println("Transaction repository size = " + transactionRepository.size());
            accountsRepository.validateBalance();

            final AbstractGenerator transactionGenerator = new RandomTransactionGenerator(context, accountIds, true, 10);
            final List<Long> trnIds = transactionGenerator.generate();
            System.out.println("Transaction ids count = " + trnIds.size());
            System.out.println("Transaction repository size = " + transactionRepository.size());
            accountsRepository.validateBalance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
