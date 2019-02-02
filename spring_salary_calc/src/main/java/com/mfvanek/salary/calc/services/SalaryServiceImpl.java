package com.mfvanek.salary.calc.services;

import com.mfvanek.salary.calc.requests.SalaryCalculationOnDateRequest;
import com.mfvanek.salary.calc.entities.Employee;
import com.mfvanek.salary.calc.entities.SalaryCalculation;
import com.mfvanek.salary.calc.repositories.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Optional<SalaryCalculation> findById(final UUID id) {
        Objects.requireNonNull(id);
        return salaryRepository.findById(id);
    }

    @Override
    public SalaryCalculation calculateOnDate(final SalaryCalculationOnDateRequest request) {
        Objects.requireNonNull(request);
        final Optional<Employee> employee = employeeService.findById(request.getEmployeeId());
        if (!employee.isPresent()) {
            throw new EntityNotFoundException(String.format("Employee with id = %s not found", request.getEmployeeId()));
        }
        final BigDecimal totalAmount = calculateTotalAmount(employee.get(), request.getWorkingDaysCount());
        final SalaryCalculation salaryCalculation = new SalaryCalculation(UUID.randomUUID(),
                request.getCalculationDate(), request.getWorkingDaysCount(), totalAmount, employee.get());
        return salaryRepository.save(salaryCalculation);
    }

    private BigDecimal calculateTotalAmount(final Employee employee, int workingDaysCount) {
        final BigDecimal salaryPerHour = employee.getSalaryPerHour();
        final BigDecimal totalHours = BigDecimal.valueOf(workingDaysCount * employee.getStandardHoursPerDay());
        return salaryPerHour.multiply(totalHours);
    }
}
