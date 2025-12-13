package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.Company;
import com.spring.jobportal_redo.domain.dto.MetaPaging;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.company.CompanyCreateDto;
import com.spring.jobportal_redo.domain.dto.company.CompanyResponseDto;
import com.spring.jobportal_redo.domain.dto.company.CompanyUpdateDto;
import com.spring.jobportal_redo.repository.CompanyRepository;
import com.spring.jobportal_redo.util.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyResponseDto create(CompanyCreateDto createDto) {
        checkNameCompanyExists(createDto.getName());
        Company company = companyMapper.toCompany(createDto);
        Company company1 = companyRepository.save(company);
        return companyMapper.toCompanyResponseDto(company1);
    }

    public PagingReturnDto getAll(Specification<Company> spec, Pageable pageable) {
        Page<Company> companyPage = companyRepository.findAll(spec, pageable);
        MetaPaging mt = MetaPaging.builder()
                .page(pageable.getPageNumber() + 1)
                .size(pageable.getPageSize())
                .totalPages(companyPage.getTotalPages())
                .totalElements(companyPage.getTotalElements())
                .build();
        List<CompanyResponseDto> companyResponseDtoList = companyMapper.toCompanyResponseDtoList(companyPage.getContent());
        return PagingReturnDto.builder()
                .meta(mt)
                .result(companyResponseDtoList)
                .build();
    }

    public CompanyResponseDto getById(Long id) {
        Company company = getCompanyByIdOrThrow(id);
        return companyMapper.toCompanyResponseDto(company);
    }

    public CompanyResponseDto update(CompanyUpdateDto updateDto) {
        Company company = getCompanyByIdOrThrow(updateDto.getId());
        if (!updateDto.getName().equals(company.getName())) {
            checkNameCompanyExists(updateDto.getName());
        }
        companyMapper.updateCompanyFromDto(updateDto, company);
        Company company1 = companyRepository.save(company);
        return companyMapper.toCompanyResponseDto(company);
    }

    public void delete(Long id) {
        getById(id);
        companyRepository.deleteById(id);
    }

    public void checkNameCompanyExists(String name) {
        if  (companyRepository.existsByName(name)) {
            throw new IllegalArgumentException("Company with name " + name + " already exists");
        }
    }

    public Company getCompanyByIdOrThrow(Long id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Company not found with id " + id)
        );
    }

}
