package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.Company;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.service.CompanyService;
import com.spring.jobportal_redo.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    // GET all companies
    @GetMapping
    @ApiMessage(message = "Fetch companies")
    public ResponseEntity<PagingReturnDto> getAllCompanies(
            Pageable pageable,
            @Filter Specification<Company> spec
    ) {
        return ResponseEntity.ok(companyService.getAll(spec, pageable));
    }

    // GET company by id
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getById(id);
        return ResponseEntity.ok(company);
    }

    // CREATE company
    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody @Valid Company company) {
        return ResponseEntity.ok(companyService.create(company));
    }

    // UPDATE company
    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody @Valid Company companyDetails) {
        Company updatedCompany = companyService.update(id, companyDetails);
        return ResponseEntity.ok(updatedCompany);
    }

    // DELETE company
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity.ok().build();
    }
}
