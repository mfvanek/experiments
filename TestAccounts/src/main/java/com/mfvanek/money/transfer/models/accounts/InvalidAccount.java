package com.mfvanek.money.transfer.models.accounts;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.Identifiable;
import com.mfvanek.money.transfer.models.currencies.Currency;
import com.mfvanek.money.transfer.models.parties.AbstractParty;

final class InvalidAccount extends AbstractAccount {

    private InvalidAccount() {
        super(Identifiable.INVALID_ID, Currency.getInvalid(), "", AbstractParty.getInvalid());
    }

    @Override
    public int hashCode() {
        return (int) Identifiable.INVALID_ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        return (obj instanceof InvalidAccount);
    }

    private static class LazyHolder {
        private static final InvalidAccount INSTANCE = new InvalidAccount();
    }

    static Account getInstance() {
        return LazyHolder.INSTANCE;
    }
}
