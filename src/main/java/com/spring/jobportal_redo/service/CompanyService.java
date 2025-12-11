package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.Company;
import com.spring.jobportal_redo.domain.dto.MetaPaging;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Company create(Company companyInfo) {
        return companyRepository.save(companyInfo);
    }

    public PagingReturnDto getAll(Specification<Company> spec, Pageable pageable) {
        Page<Company> companyPage = companyRepository.findAll(spec, pageable);
        MetaPaging mt = MetaPaging.builder()
                .page(pageable.getPageNumber() + 1)
                .size(pageable.getPageSize())
                .totalPages(companyPage.getTotalPages())
                .totalElements(companyPage.getTotalElements())
                .build();
        return PagingReturnDto.builder()
                .meta(mt)
                .result(companyPage.getContent())
                .build();
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
