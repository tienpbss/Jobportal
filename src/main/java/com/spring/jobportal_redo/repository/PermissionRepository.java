package com.spring.jobportal_redo.repository;

import com.spring.jobportal_redo.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    Boolean existsByApiPathAndMethod(String apiPath, String method);
}
