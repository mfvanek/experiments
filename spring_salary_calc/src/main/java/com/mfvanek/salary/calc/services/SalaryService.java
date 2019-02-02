package com.mfvanek.salary.calc.services;

import com.mfvanek.salary.calc.entities.Ticket;
import com.mfvanek.salary.calc.requests.SalaryCalculationOnDateRequest;
import com.mfvanek.salary.calc.entities.Salary;

import java.util.Optional;
import java.util.UUID;

public interface SalaryService {

    Optional<Salary> findById(final UUID id);

    Ticket calculateOnDate(final SalaryCalculationOnDateRequest request);
}
