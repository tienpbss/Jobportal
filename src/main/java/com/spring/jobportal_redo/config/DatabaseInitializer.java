package com.spring.jobportal_redo.config;


import com.spring.jobportal_redo.domain.Permission;
import com.spring.jobportal_redo.domain.Role;
import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.repository.PermissionRepository;
import com.spring.jobportal_redo.repository.RoleRepository;
import com.spring.jobportal_redo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        boolean i_want_to_initialize = false; // Set this flag to true if you want to initialize the database

        if (!i_want_to_initialize) {
            System.out.println("Database initialization skipped.");
            return;
        }

        List<Permission> permissions = initPermission();

        Role adminRole = initAdminRole(permissions);

        User adminUser = initAdminUser(adminRole);

        System.out.println("Database initialization completed.");

    }

    public List<Permission> initPermission() {
        System.out.println("Initializing permissions...");
        permissionRepository.deleteAll();

//        System.out.println(permissionRepository.count());

        List<Permission> permissions = new ArrayList<>();

        permissions.add(new Permission("Create User", "/api/v1/users", "POST", "USERS"));
        permissions.add(new Permission("Get User By Id", "/api/v1/users/{id}", "GET", "USERS"));
        permissions.add(new Permission("Get All User", "/api/v1/users", "GET", "USERS"));
        permissions.add(new Permission("Update User", "/api/v1/users", "PUT", "USERS"));
        permissions.add(new Permission("Delete User", "/api/v1/users/{id}", "DELETE", "USERS"));

        permissions.add(new Permission("Create Company", "/api/v1/companies", "POST", "COMPANIES"));
        permissions.add(new Permission("Get Company By Id", "/api/v1/companies/{id}", "GET", "COMPANIES"));
        permissions.add(new Permission("Get All Company", "/api/v1/companies", "GET", "COMPANIES"));
        permissions.add(new Permission("Update Company", "/api/v1/companies", "PUT", "COMPANIES"));
        permissions.add(new Permission("Delete Company", "/api/v1/companies/{id}", "DELETE", "COMPANIES"));

        permissions.add(new Permission("Create Job", "/api/v1/jobs", "POST", "JOBS"));
        permissions.add(new Permission("Get Job By Id", "/api/v1/jobs/{id}", "GET", "JOBS"));
        permissions.add(new Permission("Get All Job", "/api/v1/jobs", "GET", "JOBS"));
        permissions.add(new Permission("Update Job", "/api/v1/jobs", "PUT", "JOBS"));
        permissions.add(new Permission("Delete Job", "/api/v1/jobs/{id}", "DELETE", "JOBS"));

        permissions.add(new Permission("Create Permission", "/api/v1/permission", "POST", "PERMISSIONS"));
        permissions.add(new Permission("Get Permission By Id", "/api/v1/permission/{id}", "GET", "PERMISSIONS"));
        permissions.add(new Permission("Get All Permission", "/api/v1/permission", "GET", "PERMISSIONS"));
        permissions.add(new Permission("Update Permission", "/api/v1/permission", "PUT", "PERMISSIONS"));
        permissions.add(new Permission("Delete Permission", "/api/v1/permission/{id}", "DELETE", "PERMISSIONS"));

        permissions.add(new Permission("Create Resume", "/api/v1/resumes", "POST", "RESUMES"));
        permissions.add(new Permission("Get Resume By Id", "/api/v1/resumes/{id}", "GET", "RESUMES"));
        permissions.add(new Permission("Get All Resume", "/api/v1/resumes", "GET", "RESUMES"));
        permissions.add(new Permission("Update Resume", "/api/v1/resumes", "PUT", "RESUMES"));
        permissions.add(new Permission("Delete Resume", "/api/v1/resumes/{id}", "DELETE", "RESUMES"));

        permissions.add(new Permission("Create Role", "/api/v1/roles", "POST", "ROLES"));
        permissions.add(new Permission("Get Role By Id", "/api/v1/roles/{id}", "GET", "ROLES"));
        permissions.add(new Permission("Get All Role", "/api/v1/roles", "GET", "ROLES"));
        permissions.add(new Permission("Update Role", "/api/v1/roles", "PUT", "ROLES"));
        permissions.add(new Permission("Delete Role", "/api/v1/roles/{id}", "DELETE", "ROLES"));

        permissions.add(new Permission("Create Skill", "/api/v1/skills", "POST", "SKILLS"));
        permissions.add(new Permission("Get Skill By Id", "/api/v1/skills/{id}", "GET", "SKILLS"));
        permissions.add(new Permission("Get All Skill", "/api/v1/skills", "GET", "SKILLS"));
        permissions.add(new Permission("Update Skill", "/api/v1/skills", "PUT", "SKILLS"));
        permissions.add(new Permission("Delete Skill", "/api/v1/skills/{id}", "DELETE", "SKILLS"));

        permissions.add(new Permission("Create Subscribe", "/api/v1/subscribes", "POST", "SUBSCRIBES"));
        permissions.add(new Permission("Get Subscribe By Id", "/api/v1/subscribes/{id}", "GET", "SUBSCRIBES"));
        permissions.add(new Permission("Get All Subscribe", "/api/v1/subscribes", "GET", "SUBSCRIBES"));
        permissions.add(new Permission("Update Subscribe", "/api/v1/subscribes", "PUT", "SUBSCRIBES"));
        permissions.add(new Permission("Delete Subscribe", "/api/v1/subscribes/{id}", "DELETE", "SUBSCRIBES"));

        return permissionRepository.saveAll(permissions);
    }

    public Role initAdminRole(List<Permission> permissions) {
        System.out.println("Initializing admin role...");
        String adminRoleName = "admin";

        if (!roleRepository.existsByName(adminRoleName)) {
            Role adminRole = new Role();
            adminRole.setName(adminRoleName);
            adminRole.setDescription("Administrator role with full permissions");
            adminRole.setActive(true);

            List<Permission> allPermissions = permissionRepository.findAll();
            for (Permission permission : allPermissions) {
                adminRole.addPermission(permission);
            }

            return roleRepository.save(adminRole);
        }
        return roleRepository.findByName(adminRoleName);
    }

    public User initAdminUser(Role adminRole) {
        System.out.println("Initializing admin user...");

        String emailAdmin = "admin@gg.com";

        if (!userRepository.existsByEmail(emailAdmin)) {
            User adminUser = new User();
            adminUser.setName("Admin User");
            adminUser.setEmail(emailAdmin);
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setAge(12);
            adminUser.setRole(adminRole);
            return userRepository.save(adminUser);
        }
        return userRepository.findByEmail(emailAdmin).orElse(null);
    }
}
