package com.mfvanek.money.transfer.interfaces;

public interface AccountsRepository extends Repository<Account> {

    Account addOurBankAccount(Currency currency, String number);
}
