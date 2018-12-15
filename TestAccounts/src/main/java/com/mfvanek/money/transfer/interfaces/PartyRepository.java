package com.mfvanek.money.transfer.interfaces;

public interface PartyRepository extends Repository<Party> {

    Party addLegalPerson(String taxIdentificationNumber, String name);

    Party addPrivatePerson(String taxIdentificationNumber, String firstName, String lastName);

    Party getOurBank();
}
