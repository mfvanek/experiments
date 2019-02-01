package com.mfvanek.salary.calc.services;

import com.mfvanek.salary.calc.dtos.SalaryCalculationOnDateRequest;
import com.mfvanek.salary.calc.entities.SalaryCalculation;

import java.util.Optional;
import java.util.UUID;

public interface SalaryService {

    Optional<SalaryCalculation> findById(final UUID id);

    SalaryCalculation calculateOnDate(final SalaryCalculationOnDateRequest request);
}
