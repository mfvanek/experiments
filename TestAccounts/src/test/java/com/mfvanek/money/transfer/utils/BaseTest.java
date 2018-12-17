package com.mfvanek.money.transfer.utils;

import com.mfvanek.money.transfer.interfaces.repositories.AccountsRepository;
import com.mfvanek.money.transfer.interfaces.repositories.PartyRepository;
import com.mfvanek.money.transfer.repositories.DefaultAccountsRepository;
import com.mfvanek.money.transfer.repositories.DefaultPartyRepository;

public abstract class BaseTest {

    protected final AccountsRepository make() {
        final PartyRepository partyRepository = new DefaultPartyRepository();
        return new DefaultAccountsRepository(partyRepository);
    }
}
