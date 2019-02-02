package com.mfvanek.salary.calc.controllers;

import com.mfvanek.salary.calc.dtos.SalaryDto;
import com.mfvanek.salary.calc.entities.Salary;
import com.mfvanek.salary.calc.requests.SalaryCalculationOnDateRequest;
import com.mfvanek.salary.calc.services.SalaryService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<SalaryDto> getSalaryCalculation(@PathVariable UUID id) {
        final Optional<Salary> salary = salaryService.findById(id);
        final HttpStatus status = salary.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        final SalaryDto salaryDto = convertToDto(salary.orElse(new Salary()));
        return new ResponseEntity<>(salaryDto, status);
    }

    @PostMapping("/onDate")
    public ResponseEntity<SalaryDto> calculateSalary(@RequestBody SalaryCalculationOnDateRequest request,
                                                     UriComponentsBuilder uriComponentsBuilder) {
        final Salary salary = salaryService.calculateOnDate(request);
        final UriComponents uriComponents = uriComponentsBuilder.path("/salary/{id}").buildAndExpand(salary.getId());
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        final SalaryDto salaryDto = convertToDto(salary);
        return new ResponseEntity<>(salaryDto, headers, HttpStatus.CREATED);
    }

    private SalaryDto convertToDto(final Salary salary) {
        return modelMapper.map(salary, SalaryDto.class);
    }
}
