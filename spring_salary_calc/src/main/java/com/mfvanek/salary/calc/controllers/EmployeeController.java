package com.mfvanek.salary.calc.controllers;

import com.mfvanek.salary.calc.dtos.CreateEmployeeRequest;
import com.mfvanek.salary.calc.entities.Employee;
import com.mfvanek.salary.calc.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping(path = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable UUID id) {
        return employeeService.findById(id);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody CreateEmployeeRequest newEmployee) {
        return employeeService.create(newEmployee);
    }
}
