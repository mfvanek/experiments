package com.mfvanek.money.transfer.interfaces;

public interface PartyRepository {

    Party addLegalPerson(String taxIdentificationNumber, String name);

    Party addPrivatePerson(String taxIdentificationNumber, String firstName, String lastName);

    Party getById(Long id);

    Party getOurBank();

    Party getInvalid();
}
