package com.mfvanek.money.transfer.interfaces;

public interface Repository<T> {

    int size();

    T getById(Long id);

    T getInvalid();
}
