package com.mfvanek.salary.calc.entities;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity
@Table(name = "salary_calc")
public class SalaryCalculation {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @NotNull
    @Column(name = "emp_id", nullable = false)
    private UUID employeeId;

    @NotNull
    @Column(name = "wrk_days", nullable = false)
    private int workingDaysCount;

    @NotNull
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
}
