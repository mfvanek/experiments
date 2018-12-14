package com.mfvanek.money.transfer.repositories;

import com.mfvanek.money.transfer.models.parties.Party;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class PartyRepository {

    private static AtomicLong counter = new AtomicLong(0L);
    private static final ConcurrentMap<Long, Party> PARTIES = new ConcurrentHashMap<>();
    private static final Long ourBankId;

    static {
        final Party ourBank = addLegalPerson("Revolut LLC");
        ourBankId = ourBank.getId();
    }

    public static Party addLegalPerson(String name) {
        final Party legalPerson = Party.newLegalPerson(counter.incrementAndGet(), name);
        PARTIES.putIfAbsent(legalPerson.getId(), legalPerson);
        return legalPerson;
    }

    public static Party addPrivatePerson(String firstName, String lastName) {
        final Party privatePerson = Party.newPrivatePerson(counter.incrementAndGet(), firstName, lastName);
        PARTIES.putIfAbsent(privatePerson.getId(), privatePerson);
        return privatePerson;
    }

    public static Party getById(Long id) {
        return PARTIES.getOrDefault(id, null);
    }

    public static Party getOurBank() {
        // by design
        return getById(ourBankId);
    }
}
