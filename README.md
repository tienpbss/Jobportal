# JobPortal

> A simple Spring Boot project backend providing REST APIs for an JobPortal project.

**Overview**
- **Language:** Java
- **Build:** Gradle (Kotlin DSL)
- **Frameworks / Libraries:** Spring Boot, Spring Data JPA, Spring Security, Thymeleaf (templates present), Lombok

**Features**
- CRUD for Jobs, Companies, Users, Roles, Permissions, Skills, Resumes, Subscribers
- Authentication, authorization endpoints and security configuration with jwt token
- Support email sending for user subscribe specific skills
- Paging, sorting and filter
- Api document with swagger

**Prerequisites**
- Java 17+ (or the JDK version configured for the project)


**How to run this project**
1. Clone the repository or open the workspace.
2. Use the bundled Gradle wrapper to build and run.

Build:

```bash
./gradlew build
```

Run (development):

```bash
./gradlew bootRun
```

Run tests:

```bash
./gradlew test
```

The application reads configuration from `src/main/resources/application.yaml`.

**Project structure (important folders)**
- `src/main/java/com/spring/jobportal_redo` — main Java sources
  - `controller` — REST controllers (AuthController, JobController, UserController, etc.)
  - `service` — business logic services (JobService, UserService, SkillService, ...)
  - `repository` — Spring Data JPA repositories
  - `domain` — JPA entities and DTOs
  - `config` — security, CORS, swagger and other configurations
- `src/main/resources/templates` — Thymeleaf templates for email sending


**Configuration & environment**
- Edit `src/main/resources/application.yaml` to configure datasource, mail, and other environment-specific values.

**API notes**
Controllers implement REST endpoints for the main resources (Auth, Job, Company, User, Skill, Resume, Role, Permission, Subscriber, File, Email). See `src/main/java/com/spring/jobportal_redo/controller` for exact paths and request/response DTOs.

---
Created for local development and learning. 