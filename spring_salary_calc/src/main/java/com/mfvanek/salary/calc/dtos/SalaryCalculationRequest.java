package com.mfvanek.salary.calc.dtos;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class SalaryCalculationRequest {

    @NotNull
    private UUID employeeId;

    @NotNull
    private int workingDaysCount;
}
