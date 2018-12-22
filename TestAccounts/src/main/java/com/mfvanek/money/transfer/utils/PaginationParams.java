package com.mfvanek.money.transfer.utils;

import lombok.Getter;
import lombok.ToString;
import spark.Request;

@ToString
public class PaginationParams {

    @Getter
    private final int recordsPerPage;
    @Getter
    private final int pageNumber;
    private final boolean valid;

    public PaginationParams(Request request) {
        int limit = 0;
        int page = 0;
        boolean valid = false;
        final String limitStr = request.queryParams("limit");
        if (limitStr != null) {
            limit = Integer.parseInt(limitStr, 10);
            final String pageStr = request.queryParams("page");
            if (pageStr != null) {
                page = Integer.parseInt(pageStr, 10);
            } else {
                page = 1; // default
            }
            valid = true;
        }

        this.recordsPerPage = limit;
        this.pageNumber = page;
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }
}
