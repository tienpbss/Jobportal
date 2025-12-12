package com.spring.jobportal_redo.util.mapper;

import com.spring.jobportal_redo.domain.Company;
import com.spring.jobportal_redo.domain.dto.company.CompanyCreateDto;
import com.spring.jobportal_redo.domain.dto.company.CompanyResponseDto;
import com.spring.jobportal_redo.domain.dto.company.CompanyUpdateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company toCompany(CompanyCreateDto companyCreateDto);
    CompanyResponseDto toCompanyResponseDto(Company company);
    List<CompanyResponseDto> toCompanyResponseDtoList(List<Company> companies);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateCompanyFromDto(CompanyUpdateDto updateDto, @MappingTarget Company company);
}
