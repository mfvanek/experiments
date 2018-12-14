package com.mfvanek.money.transfer.models.parties;

final class LegalPerson extends Party {

    private final String name;

    LegalPerson(Long id, String name) {
        super(id, PartyType.LEGAL_PERSON);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
