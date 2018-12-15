package com.mfvanek.money.transfer.utils;

import com.mfvanek.money.transfer.interfaces.AccountsRepository;
import com.mfvanek.money.transfer.interfaces.PartyRepository;
import lombok.Getter;

@Getter
public class Context {

    private final PartyRepository partyRepository;
    private final AccountsRepository accountsRepository;

    public Context(PartyRepository partyRepository, AccountsRepository accountsRepository) {
        this.partyRepository = partyRepository;
        this.accountsRepository = accountsRepository;
    }
}
