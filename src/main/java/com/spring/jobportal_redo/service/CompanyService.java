package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.Company;
import com.spring.jobportal_redo.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company create(Company companyInfo) {
        return companyRepository.save(companyInfo);
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Company not found"));
    }

    public Company update(Long id, Company companyInfo) {
        Company company = getById(id);
        company.setName(companyInfo.getName());
        company.setDescription(companyInfo.getDescription());
        company.setAddress(companyInfo.getAddress());
        company.setLogo(companyInfo.getLogo());
        return companyRepository.save(company);
    }

    public void delete(Long id) {
        getById(id);
        companyRepository.deleteById(id);
    }
}
