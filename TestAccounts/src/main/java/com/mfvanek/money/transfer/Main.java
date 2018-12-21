package com.mfvanek.money.transfer;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Party;
import com.mfvanek.money.transfer.interfaces.Transaction;
import com.mfvanek.money.transfer.interfaces.repositories.Repository;
import com.mfvanek.money.transfer.utils.Bank;
import com.mfvanek.money.transfer.utils.PaginationParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Bank.getInstance().generateData();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }

        Spark.port(55000);

        // TODO json pretty-print

        // http://localhost:55000/parties
        // http://localhost:55000/parties?limit=10
        // http://localhost:55000/parties?page=2&limit=20
        Spark.get("/parties", (req, res) -> {
            final Repository<Party> repository = Bank.getInstance().getContext().getPartyRepository();
            final PaginationParams pgParams = new PaginationParams(req);
            if (pgParams.isValid()) {
                return repository.getAll(pgParams.getPageNumber(), pgParams.getRecordsPerPage());
            }
            return repository.getAll();
        });

        // http://localhost:55000/accounts
        Spark.get("/accounts", (req, res) -> {
            final Repository<Account> repository = Bank.getInstance().getContext().getAccountsRepository();
            final PaginationParams pgParams = new PaginationParams(req);
            if (pgParams.isValid()) {
                return repository.getAll(pgParams.getPageNumber(), pgParams.getRecordsPerPage());
            }
            return repository.getAll();
        });

        // http://localhost:55000/transactions
        Spark.get("/transactions", (req, res) -> {
            final Repository<Transaction> repository = Bank.getInstance().getContext().getTransactionRepository();
            final PaginationParams pgParams = new PaginationParams(req);
            if (pgParams.isValid()) {
                return repository.getAll(pgParams.getPageNumber(), pgParams.getRecordsPerPage());
            }
            return repository.getAll();
        });
    }
}
