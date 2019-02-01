package com.mfvanek.salary.calc.services;

import com.mfvanek.salary.calc.dtos.EmployeeCreationRequest;
import com.mfvanek.salary.calc.entities.Employee;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {

    Optional<Employee> findById(final UUID id);

    Employee create(final EmployeeCreationRequest request);
}
