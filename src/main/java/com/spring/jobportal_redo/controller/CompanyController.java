package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.Company;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.company.CompanyCreateDto;
import com.spring.jobportal_redo.domain.dto.company.CompanyResponseDto;
import com.spring.jobportal_redo.domain.dto.company.CompanyUpdateDto;
import com.spring.jobportal_redo.service.CompanyService;
import com.spring.jobportal_redo.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {
    private final CompanyService companyService;

    // CREATE company
    @PostMapping
    @ApiMessage( message = "Create a company")
    public CompanyResponseDto createCompany(@RequestBody @Valid CompanyCreateDto createDto) {
        return companyService.create(createDto);
    }

    // GET all companies
    @GetMapping
    @ApiMessage(message = "Get all companies")
    public PagingReturnDto getAllCompanies(
            Pageable pageable,
            @Filter Specification<Company> filter
    ) {
        return companyService.getAll(filter, pageable);
    }

    // GET company by id
    @GetMapping("/{id}")
    @ApiMessage(message = "Get company by id")
    public CompanyResponseDto getCompanyById(@PathVariable Long id) {
        return companyService.getById(id);

    }

    // UPDATE company
    @PutMapping
    @ApiMessage(message = "Update company")
    public CompanyResponseDto updateCompany(@RequestBody @Valid CompanyUpdateDto updateDto) {
        return companyService.update(updateDto);
    }

    // DELETE company
    @DeleteMapping("/{id}")
    @ApiMessage(message = "Delete a company")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity.ok().build();
    }
}
