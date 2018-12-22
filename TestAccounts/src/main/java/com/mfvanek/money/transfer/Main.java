package com.mfvanek.money.transfer;

import com.google.gson.Gson;
import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Party;
import com.mfvanek.money.transfer.interfaces.Transaction;
import com.mfvanek.money.transfer.interfaces.repositories.Repository;
import com.mfvanek.money.transfer.utils.Bank;
import com.mfvanek.money.transfer.utils.PaginationParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Response;
import spark.Spark;

import javax.servlet.http.HttpServletResponse;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Bank.getInstance().generateData();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }

        Spark.port(9999);

        // http://localhost:9999/parties?limit=10
        // http://localhost:9999/parties?page=2&limit=20
        Spark.get("/parties", (req, res) -> {
            final Gson converter = new Gson().newBuilder().setPrettyPrinting().create();
            final Repository<Party> repository = Bank.getInstance().getContext().getPartyRepository();
            final PaginationParams pgParams = PaginationParams.from(req);
            return converter.toJson(repository.getAll(pgParams.getPageNumber(), pgParams.getRecordsPerPage()));
        });

        // http://localhost:9999/accounts?limit=10
        Spark.get("/accounts", (req, res) -> {
            final Gson converter = new Gson().newBuilder().setPrettyPrinting().create();
            final Repository<Account> repository = Bank.getInstance().getContext().getAccountsRepository();
            final PaginationParams pgParams = PaginationParams.from(req);
            return converter.toJson(repository.getAll(pgParams.getPageNumber(), pgParams.getRecordsPerPage()));
        });

        // http://localhost:9999/transactions?limit=100
        Spark.get("/transactions", (req, res) -> {
            final Gson converter = new Gson().newBuilder().setPrettyPrinting().create();
            final Repository<Transaction> repository = Bank.getInstance().getContext().getTransactionRepository();
            final PaginationParams pgParams = PaginationParams.from(req);
            return converter.toJson(repository.getAll(pgParams.getPageNumber(), pgParams.getRecordsPerPage()));
        });

        Spark.after((req, res) -> res.type("application/json"));

        Spark.exception(IllegalArgumentException.class, (exception, request, response) ->
                fillErrorInfo(response, exception, HttpServletResponse.SC_BAD_REQUEST));

        Spark.exception(NullPointerException.class, (exception, request, response) ->
                fillErrorInfo(response, exception, HttpServletResponse.SC_BAD_REQUEST));

        Spark.exception(NumberFormatException.class, (exception, request, response) ->
                fillErrorInfo(response, exception, HttpServletResponse.SC_BAD_REQUEST));
    }

    private static void fillErrorInfo(Response response, Exception err, int errCode) {
        response.status(errCode);
        response.body(String.format("<html><body><h2>%d %s</h2></body></html>", errCode, err.getLocalizedMessage()));
    }
}
