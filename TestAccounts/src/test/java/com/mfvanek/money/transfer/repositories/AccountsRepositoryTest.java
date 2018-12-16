package com.mfvanek.money.transfer.repositories;

import com.mfvanek.money.transfer.interfaces.Account;
import com.mfvanek.money.transfer.interfaces.repositories.AccountsRepository;
import com.mfvanek.money.transfer.interfaces.repositories.PartyRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountsRepositoryTest {

    @Test
    void getById() {
    }

    @Test
    void getInvalid() {
    }

    @Test
    void addOurBankAccount() {
    }

    @Test
    void addOurBankAccount1() {
    }

    @Test
    void getInitialBalance() {
        final AccountsRepository repository = make();
        assertEquals(BigDecimal.valueOf(100_000_000.00d), repository.getInitialBalance());
    }

    @Test
    void getOurBankMainAccount() {
        final AccountsRepository repository = make();
        final Account a = repository.getOurBankMainAccount();
        assertNotNull(a);
        assertTrue(a.isValid());
        assertTrue(a.isActive());
        assertEquals(repository.getInitialBalance(), a.getBalance());
    }

    @Test
    void addPassiveAccount() {
    }

    @Test
    void addPassiveAccount1() {
    }

    @Test
    void size() {
        final AccountsRepository repository = make();
        assertEquals(1, repository.size());
        repository.addOurBankAccount("20202810100000012345", BigDecimal.ZERO);
        assertEquals(2, repository.size());
    }

    private AccountsRepository make() {
        final PartyRepository partyRepository = new DefaultPartyRepository();
        return new DefaultAccountsRepository(partyRepository);
    }
}