package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.Company;
import com.spring.jobportal_redo.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // GET all companies
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAll());
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
