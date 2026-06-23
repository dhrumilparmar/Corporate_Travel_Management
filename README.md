# Corporate Travel Management System - Backend

Spring Boot backend for a Corporate Travel Management System that manages employee travel requests, manager/finance approvals, expense tracking, policy validation, and role-based access control.

## Frontend Repository

Angular frontend is available here:
https://github.com/dhrumilparmar/Corporate_Travel_Management.frontend.git

## Tech Stack

* Java
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* REST APIs
* Lombok

## Features

* JWT-based login and authentication
* Role-based access for Employee, Manager, Finance, and Admin
* Travel request creation and tracking
* Manager and finance approval workflow
* Expense management
* Budget calculation
* Approval history and audit tracking
* Travel policy validation
* Angular frontend integration

## Architecture

The backend follows a layered architecture:

```text
Controller → Service → Repository → Entity/Database
```

Main modules:

```text
security
employee
travelRequests
manager
finance
```

## Run Backend

```bash
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

## Run Frontend

```bash
git clone https://github.com/dhrumilparmar/Corporate_Travel_Management.frontend.git
cd Corporate_Travel_Management.frontend
npm install
ng serve
```

Frontend runs on:

```text
http://localhost:4200
```

## Author

**Dhrumil Parmar**
GitHub: https://github.com/dhrumilparmar
