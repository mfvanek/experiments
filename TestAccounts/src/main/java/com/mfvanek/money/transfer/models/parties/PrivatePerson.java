package com.mfvanek.money.transfer.models.parties;

final class PrivatePerson extends Party {

    private final String firstName;
    private final String lastName;

    PrivatePerson(Long id, String firstName, String lastName) {
        super(id, PartyType.LEGAL_PERSON);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }
}
