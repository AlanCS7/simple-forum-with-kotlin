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

---

## 📂 Project Structure

```
src/main/kotlin/dev/alancss/forum
├── controller         # Handles HTTP requests
├── dto                # Data Transfer Objects
├── enum               # Enumerations (e.g., TopicStatus)
├── exception          # Custom exceptions and handlers
├── mapper             # Entity/DTO mapping utilities
├── model              # JPA Entities (Topic, Course, User, etc.)
├── repository         # Spring Data repositories
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

* 🔐 Add authentication/authorization (JWT + Spring Security)
* 🧪 Unit and integration testing (JUnit + Mockito)
* 📘 API Documentation with Swagger/OpenAPI
* 🚀 CI/CD Integration (GitHub Actions, etc.)

---

## 🧪 Testing

* Currently, no tests
* Plans to include unit and integration tests in the next versions

---

## ⚙️ Requirements

* Java 21+
* Maven 3.9+
* Docker & Docker Compose

---

## 💬 Contributions

Suggestions and contributions are welcome! Feel free to open issues or submit pull requests.
