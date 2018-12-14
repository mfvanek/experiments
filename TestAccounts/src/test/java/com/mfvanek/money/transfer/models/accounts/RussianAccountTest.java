package com.mfvanek.money.transfer.models.accounts;

import com.mfvanek.money.transfer.repositories.PartyRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RussianAccountTest {

    @Test
    void getChapter() {
        RussianAccount account = RussianAccount.newDefault(1L, "20202810100000000001", PartyRepository.getOurBank());
        assertNotNull(account);
        assertEquals(Chapter.BALANCE, account.getChapter());
    }

    @Test
    void getId() {
        RussianAccount account = RussianAccount.newDefault(11L, "20202810100000000001", PartyRepository.getOurBank());
        assertNotNull(account);
        assertEquals(Long.valueOf(11L), account.getId());
    }

    @Test
    void getNumber() {
    }

    @Test
    void getCurrency() {
    }

    @Test
    void getBalance() {
    }

    @Test
    void getHolder() {
    }
}