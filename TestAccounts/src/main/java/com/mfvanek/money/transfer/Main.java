package com.mfvanek.money.transfer;

import com.mfvanek.money.transfer.utils.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final Context context = Context.create();

    public static void main(String[] args) {
        try {
            // TODO via Builder. need fewer data
            DemoApp.generateData(context, true, true);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }

        Spark.port(55000);

        // TODO make pagination

        // TODO json pretty-print

        // http://localhost:55000/parties
        Spark.get("/parties", (req, res) -> {
            return context.getPartyRepository().getAll();
        });

        // http://localhost:55000/accounts
        Spark.get("/accounts", (req, res) -> {
            return context.getAccountsRepository().getAll();
        });

        // http://localhost:55000/transactions
        Spark.get("/transactions", (req, res) -> {
            return context.getTransactionRepository().getAll();
        });
    }
}
