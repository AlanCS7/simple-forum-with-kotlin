# 📘 Simple Forum API

A lightweight API for a simple question-and-answer forum where users can create, update, delete, and view discussion topics organized by course.

---

## 🚀 Technologies Used

* **Language**: Kotlin
* **Framework**: Spring Boot 3.4.4
* **Web Layer**: Spring Web
* **Persistence**: Spring Data JPA + Hibernate
* **Database**: PostgreSQL (via Docker)
* **Database Versioning**: Flyway
* **Caching**: Spring Cache (in-memory)
* **Dependency Management**: Maven
* **Environment**: Docker + Docker Compose
* **Authentication**: Spring Security with JWT
* **Documentation**: Swagger/OpenAPI (Available at `GET /swagger-ui.html`)
* **Testing**: JUnit + Mockito

---

## 📂 Project Structure

```
src/main/kotlin/dev/alancss/forum
├── config             # Configuration classes (e.g., Swagger, Security)
├── controller         # Handles HTTP requests
├── dto                # Data Transfer Objects
├── enum               # Enumerations (e.g., TopicStatus)
├── exception          # Custom exceptions and handlers
├── mapper             # Entity/DTO mapping utilities
├── model              # JPA Entities (Topic, Course, User, etc.)
├── repository         # Spring Data repositories
├── security           # JWT, filters, and security configs
├── service            # Business logic
└── ForumApplication.kt
```

---

## 🧑‍💻 Features Implemented

* ✅ **Create Topic**
* ✅ **Update Topic**
* ✅ **Delete Topic**
* ✅ **List Topics** (with pagination and optional filter by course name)
* ✅ **Generate Report** (Topics grouped by category)
* ✅ **Authentication and Authorization** using JWT and Spring Security
* ✅ **API Documentation** available at `GET /swagger-ui.html`
* ✅ **Unit and Integration Testing** using JUnit and Mockito

### 📌 Example: List Topics with Filter

```http
GET /topics?courseName=Java EE
```

### 📌 Example: Report by Category

```http
GET /topics/report
```

Response:

```json
[
  {
    "category": "Programming",
    "quantity": 4
  },
  {
    "category": "Frameworks",
    "quantity": 2
  }
]
```

---

## 🐘 Database Configuration

* PostgreSQL is managed via Docker Compose
* Port: `5432`
* Schema versioned using Flyway migrations in `src/main/resources/db/migration`
* Preloaded with sample data (Courses, Topics, Users)

---

## 🐳 Running with Docker

Make sure Docker is installed. Then run:

```bash
docker compose up -d
```

This will start the PostgreSQL container for development.

---

## 🎯 Future Improvements

* 🚀 CI/CD Integration (GitHub Actions, etc.)

---

## 🧪 Testing

* Includes unit and integration tests using JUnit and Mockito

---

## ⚙️ Requirements

* Java 21+
* Maven 3.9+
* Docker & Docker Compose

---

## 💬 Contributions

Suggestions and contributions are welcome! Feel free to open issues or submit pull requests.
