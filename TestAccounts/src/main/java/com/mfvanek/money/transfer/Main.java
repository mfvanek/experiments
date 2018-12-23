package com.mfvanek.money.transfer;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Identifiable;
import com.mfvanek.money.transfer.interfaces.Party;
import com.mfvanek.money.transfer.interfaces.Transaction;
import com.mfvanek.money.transfer.interfaces.repositories.Repository;
import com.mfvanek.money.transfer.utils.Bank;
import com.mfvanek.money.transfer.utils.JsonUtils;
import com.mfvanek.money.transfer.utils.PaginationParams;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Spark;

import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

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
            final Repository<Party> repository = Bank.getInstance().getContext().getPartyRepository();
            final PaginationParams pgParams = PaginationParams.from(req);
            return JsonUtils.make().toJson(repository.getAll(pgParams.getPageNumber(), pgParams.getRecordsPerPage()));
        });

        // http://localhost:9999/parties/1
        Spark.get("/parties/:id", (req, res) -> {
            final Repository<Party> repository = Bank.getInstance().getContext().getPartyRepository();
            return findById(Party.class, repository, req);
        });

        // http://localhost:9999/parties/1/accounts
        Spark.get("/parties/:id/accounts", (req, res) -> {
            final Repository<Party> repository = Bank.getInstance().getContext().getPartyRepository();
            // TODO
            throw new NotImplementedException("/parties/:id/accounts");
        });

        // http://localhost:9999/accounts?limit=10
        Spark.get("/accounts", (req, res) -> {
            final Repository<Account> repository = Bank.getInstance().getContext().getAccountsRepository();
            final PaginationParams pgParams = PaginationParams.from(req);
            return JsonUtils.make().toJson(repository.getAll(pgParams.getPageNumber(), pgParams.getRecordsPerPage()));
        });

        // http://localhost:9999/accounts/1
        Spark.get("/accounts/:id", (req, res) -> {
            final Repository<Account> repository = Bank.getInstance().getContext().getAccountsRepository();
            return findById(Account.class, repository, req);
        });

        // http://localhost:9999/transactions?limit=100
        Spark.get("/transactions", (req, res) -> {
            final Repository<Transaction> repository = Bank.getInstance().getContext().getTransactionRepository();
            final PaginationParams pgParams = PaginationParams.from(req);
            return JsonUtils.make().toJson(repository.getAll(pgParams.getPageNumber(), pgParams.getRecordsPerPage()));
        });

        // http://localhost:9999/transactions/1
        Spark.get("/transactions/:id", (req, res) -> {
            final Repository<Transaction> repository = Bank.getInstance().getContext().getTransactionRepository();
            return findById(Transaction.class, repository, req);
        });

        Spark.after((req, res) -> res.type("application/json"));

        Spark.exception(IllegalArgumentException.class, (e, req, res) ->
                fillErrorInfo(res, e, HttpServletResponse.SC_BAD_REQUEST));

        Spark.exception(NullPointerException.class, (e, req, res) ->
                fillErrorInfo(res, e, HttpServletResponse.SC_BAD_REQUEST));

        Spark.exception(NumberFormatException.class, (e, req, res) ->
                fillErrorInfo(res, e, HttpServletResponse.SC_BAD_REQUEST));

        Spark.exception(NoSuchElementException.class, (e, req, res) ->
                fillErrorInfo(res, e, HttpServletResponse.SC_NOT_FOUND));
    }

    private static void fillErrorInfo(Response res, Exception err, int errCode) {
        res.type("application/json");
        res.status(errCode);
        res.body(JsonUtils.toJson(err, errCode));
    }

    private static <T extends Identifiable> String findById(Class<T> type, Repository<T> repository, Request req) {
        final String id = req.params("id");
        final T t = repository.getById(Long.valueOf(id, 10));
        if (t.isNotValid()) {
            throw new NoSuchElementException(String.format("%s with id %s is not found", type.getSimpleName(), id));
        }
        return JsonUtils.make().toJson(t);
    }
}
