# SensorMonitorApi
Backend Spring Project for SensorMonitorApi

This project is a RESTful Spring-based java backend web app for a SensorMonitor store.
Its main logic is as follows:

The project specification:
- JDK 17 and Spring 5;
- Spring Boot 3 Rest Api;
- 2-module structure (common & api);
- Hibernate Repositories (Spring Data JPA version is also available on correspondent commit);
- DB: PostgreSQL DB, Flyway;
- SQL scripts for 2 pre-install users (user & admin) at common module resources/sql_insert_users/insert_users.sql;
- Authentication & authorisation (Spring Security, JWT token);
- Validation of data entries, incl. custom Enum validation;
- Entity/DTO converters;
- OpenAPI UI for testing purposes (not fully documented).
