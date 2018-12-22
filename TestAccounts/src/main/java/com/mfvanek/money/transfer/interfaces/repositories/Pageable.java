package com.mfvanek.money.transfer.interfaces.repositories;

public interface Pageable<T> {

    PagedResult<T> getAll(int pageNumber, int recordsPerPage);
}
