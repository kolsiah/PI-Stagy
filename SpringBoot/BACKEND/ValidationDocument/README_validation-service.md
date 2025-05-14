# ✅ Validation-Service

This microservice handles document validation actions by supervisors (enseignants) within the internship platform.

## ⚙️ Technologies Used
- Java 17
- Spring Boot 3.x
- Spring Cloud Eureka
- Spring Data JPA
- OpenFeign
- MySQL
- Maven

## 📘 Description
The `validation-service` allows supervisors to:
- Validate or reject documents submitted by students
- Write and store comments
- View validation history
- Send email notifications upon validation
- Perform sentiment analysis on comments via a Python API

## 🗂 Project Structure
```
validation-service/
├── controller/         # REST endpoints
├── service/            # Business logic
├── repository/         # Validation data access
├── feign/              # Feign client to communicate with document-service
├── config/             # CORS and Eureka configuration
└── application.properties
```

## 🔗 Key Endpoints

| Method | Endpoint                               | Description                                |
|--------|----------------------------------------|--------------------------------------------|
| POST   | `/validations`                         | Validate a document                        |
| GET    | `/validations/encadrant/{id}`          | Get validations by supervisor              |
| GET    | `/validations/etudiant/{id}`           | Get validations for a student              |
| POST   | `/validations/analyse-commentaire`     | Analyze a single comment via Python Flask  |
| POST   | `/validations/analyse-multiple`        | Analyze multiple comments (pie chart)      |

## 🔁 Inter-Service Communication
- Uses **Feign Client** to call `document-service` and update document status
- Sends email notification using `EmailService` upon validation

## 🤖 Machine Learning Integration
- Communicates with a local Python Flask API:
  - `POST /analyze` → Analyze one comment (returns: `positif` / `négatif`)
  - `POST /analyze-multiple` → Aggregate sentiment for all comments

## 📦 Configuration Notes
- Registered in Eureka as: `VALIDATION-SERVICE`
- CORS configured for Angular front-end: `http://localhost:4200`
