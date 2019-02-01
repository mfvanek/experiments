package com.mfvanek.salary.calc.services;

import com.mfvanek.salary.calc.dtos.CreateEmployeeRequest;
import com.mfvanek.salary.calc.entities.Employee;

import java.util.UUID;

public interface EmployeeService {

    Employee findById(final UUID id);

    Employee create(final CreateEmployeeRequest request);
}
