package com.mfvanek.money.transfer.models.accounts;

import com.mfvanek.money.transfer.enums.Chapter;
import com.mfvanek.money.transfer.interfaces.PartyRepository;
import com.mfvanek.money.transfer.models.currencies.Currency;
import com.mfvanek.money.transfer.repositories.DefaultPartyRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RussianAccountTest {

    private final PartyRepository repository = new DefaultPartyRepository();

    @Test
    void getChapter() {
        RussianAccount account = RussianAccount.newRUB(1L, "20202810100000000001", repository.getOurBank());
        assertNotNull(account);
        assertEquals(Chapter.BALANCE, account.getChapter());
    }

    @Test
    void getId() {
        RussianAccount account = RussianAccount.newRUB(11L, "20202810100000000001", repository.getOurBank());
        assertNotNull(account);
        assertEquals(Long.valueOf(11L), account.getId());
    }

    @Test
    void getNumber() {
        RussianAccount account = RussianAccount.newRUB(1L, "30102810100000000001", repository.getOurBank());
        assertNotNull(account);
        assertEquals("30102810100000000001", account.getNumber());
    }

    @Test
    void getCurrency() {
        RussianAccount account = RussianAccount.newRUB(1L, "30102810100000000001", repository.getOurBank());
        assertNotNull(account);
        assertEquals(Currency.valueOf("RUB"), account.getCurrency());
    }

    @Test
    void getBalance() {
        RussianAccount account = RussianAccount.newRUB(1L, "30102810100000000001", repository.getOurBank());
        assertNotNull(account);
        assertEquals(BigDecimal.valueOf(0L), account.getBalance());
    }

    @Test
    void getHolder() {
        RussianAccount account = RussianAccount.newRUB(1L, "30102810100000000001", repository.getOurBank());
        assertNotNull(account);
        assertEquals(repository.getOurBank(), account.getHolder());
    }
}