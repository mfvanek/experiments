package com.mfvanek.money.transfer.interfaces;

public interface AccountsRepository {

    Account getById(Long id);

    Account getInvalid();
}
