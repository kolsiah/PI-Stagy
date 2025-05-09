# 📄 Document-Service

This microservice is responsible for managing documents submitted by students in the internship management web application.

## ⚙️ Technologies Used
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Spring Cloud Eureka
- Lombok
- MySQL
- Maven

## 📘 Description
`document-service` allows students to:
- Upload documents (CVs, motivation letters, etc.)
- View or delete their documents
- Analyze document content (summarization + technology extraction)
- Compare documents for plagiarism
- Update document status (via Feign call from validation-service)

## 🗂 Project Structure
```
document-service/
├── controller/          # REST controllers
├── service/             # Business logic
├── repository/          # JPA repositories
├── model/               # JPA entities (Document)
├── config/              # Configuration (CORS, Eureka, etc.)
└── application.properties
```

## 🔗 Key Endpoints

| Method | Endpoint                          | Description                              |
|--------|-----------------------------------|------------------------------------------|
| POST   | `/documents`                      | Upload a document                        |
| GET    | `/documents/etudiant/{id}`        | Get documents for a specific student     |
| GET    | `/documents`                      | Get all documents                        |
| PUT    | `/documents/{id}/status`          | Update document status                   |
| DELETE | `/documents/{id}`                 | Delete a document                        |
| GET    | `/documents/analyse/{id}`         | Analyze document content                 |
| GET    | `/documents/compare/{id1}/{id2}`  | Compare two documents (plagiarism)       |

## 🔁 Inter-Service Communication
- Feign Client used by `validation-service` to update the status of documents.

## 📦 Configuration Notes
- Registered in Eureka as: `DOCUMENT`
- CORS enabled for `http://localhost:4200`
