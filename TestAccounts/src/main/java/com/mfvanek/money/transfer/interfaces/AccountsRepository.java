package com.mfvanek.money.transfer.interfaces;

public interface AccountsRepository extends Repository<Account> {

    Account addOurBankAccount(Currency currency, String number);

    Account addOurBankAccount(String number);

    Account getOurBankMainAccount();

    Account addPassiveAccount(Currency currency, String number, Party holder);

    Account addPassiveAccount(String number, Party holder);
}
