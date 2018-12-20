package com.mfvanek.money.transfer.interfaces.repositories;

import java.util.Collection;

public interface Pageable<T> {

    Collection<T> getAll();

    Collection<T> getAll(int pageNumber, int recordsPerPage);
}
