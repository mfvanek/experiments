package com.mfvanek.money.transfer.models.parties;

import com.mfvanek.money.transfer.interfaces.Identifiable;

public abstract class Party implements Identifiable {

    private final Long id;
    private final PartyType partyType;

    Party(Long id, PartyType partyType) {
        this.id = id;
        this.partyType = partyType;
    }

    @Override
    public final Long getId() {
        return id;
    }

    public final PartyType getPartyType() {
        return partyType;
    }

    public final boolean isPrivatePerson() {
        return PartyType.PRIVATE_PERSON == partyType;
    }

    public final boolean isLegalPerson() {
        return PartyType.LEGAL_PERSON == partyType;
    }

    @Override
    public final String toString() {
        return String.format("Party{%s, type=%s, id=%d}", getName(), getPartyType(), getId());
    }

    abstract String getName();

    public static Party newLegalPerson(Long id, String name) {
        return new LegalPerson(id, name);
    }

    public static Party newPrivatePerson(Long id, String firstName, String lastName) {
        return new PrivatePerson(id, firstName, lastName);
    }
}
