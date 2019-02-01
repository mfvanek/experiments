package com.mfvanek.salary.calc.controllers;

import com.mfvanek.salary.calc.dtos.CreateEmployeeRequest;
import com.mfvanek.salary.calc.entities.Employee;
import com.mfvanek.salary.calc.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping(path = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable UUID id) {
        final Optional<Employee> employee = employeeService.findById(id);
        return new ResponseEntity<>(employee.orElse(null), employee.isPresent()? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody CreateEmployeeRequest newEmployee,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        final Employee employee = employeeService.create(newEmployee);
        final UriComponents uriComponents = uriComponentsBuilder.path("/employee/{id}").buildAndExpand(employee.getId());
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<>(employee, headers, HttpStatus.CREATED);
    }
}
