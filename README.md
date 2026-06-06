# ExamEase 🧠⚡

A fast, privacy-first mental wellness tracker designed for Indian students during exam season (NEET, JEE, UPSC, Board Exams). Built for a hackathon within 3 hours using Java 17, Spring Boot 3, and Thymeleaf.

## Features (MVP)
* **Low-Friction Check-ins**: Log your mood and stress triggers in under 15 seconds.
* **Contextual Stress Triggers**: India-specific options like "Syllabus Pressure", "Mock Test Scores", and "Parental Expectations".
* **Rule-Based Support Engine**: Deterministic, non-judgmental, and practical suggestions based on mood + trigger combos.
* **Safety First**: Clearly delineated boundaries with helplines visible when needed.

## Tech Stack
* Backend: Java 17, Spring Boot 3 (Web, Data JPA, Validation)
* Database: H2 (Local memory database)
* Frontend: Thymeleaf, Bootstrap 5, Vanilla CSS

## How to Run
1. Clone the repository.
2. Ensure you have Java 17+ and Maven installed.
3. Run the following command from the root folder:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Navigate to `http://localhost:8080/`

## Deployment Notes (Render / Railway)
* Replace the `spring.datasource.url` in `application.properties` via Environment Variables to a PostgreSQL instance.
* Set `spring.jpa.hibernate.ddl-auto=update` appropriately for production migrations.
