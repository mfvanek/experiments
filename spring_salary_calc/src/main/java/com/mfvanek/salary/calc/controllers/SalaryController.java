package com.mfvanek.salary.calc.controllers;

import com.mfvanek.salary.calc.requests.SalaryCalculationOnDateRequest;
import com.mfvanek.salary.calc.entities.SalaryCalculation;
import com.mfvanek.salary.calc.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping("/{id}")
    public ResponseEntity<SalaryCalculation> getSalaryCalculation(@PathVariable UUID id) {
        final Optional<SalaryCalculation> salary = salaryService.findById(id);
        return new ResponseEntity<>(salary.orElse(null), salary.isPresent()? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/onDate")
    public ResponseEntity<SalaryCalculation> calculateSalary(@RequestBody SalaryCalculationOnDateRequest request,
                                                             UriComponentsBuilder uriComponentsBuilder) {
        final SalaryCalculation salary = salaryService.calculateOnDate(request);
        final UriComponents uriComponents = uriComponentsBuilder.path("/salary/{id}").buildAndExpand(salary.getId());
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<>(salary, headers, HttpStatus.CREATED);
    }
}
