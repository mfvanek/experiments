package com.mfvanek.salary.calc.services;

import com.mfvanek.salary.calc.dtos.CreateEmployeeRequest;
import com.mfvanek.salary.calc.entities.Employee;
import com.mfvanek.salary.calc.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    @Override
    public Employee findById(final UUID id) {
        Objects.requireNonNull(id);
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Employee with id = %s not found", id)));
    }

    @Override
    public Employee create(final CreateEmployeeRequest request) {
        final Employee employee = new Employee(UUID.randomUUID(), request.getFirstName(), request.getLastName(),
                request.getStandardHoursPerDay(), request.getSalaryPerHour());
        return employeeRepository.save(employee);
    }
}
