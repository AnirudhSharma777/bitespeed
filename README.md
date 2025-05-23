# Contact Identification Service

This Spring Boot application provides an API to identify and consolidate user contacts based on email and phone number. It ensures that related or duplicate contacts are linked using a primary-secondary relationship model.

## 🚀 Features

- Accepts email and/or phone number to identify users.
- Merges related contacts into a single response group.
- Establishes and maintains primary-secondary relationships.
- Prevents duplicate entries by linking existing contacts.

## 📦 Tech Stack

- Java 17+ / 21+
- Spring Boot 3.x
- Hibernate / JPA
- MySQL (compatible with Aiven or local setup)
- Maven or Gradle

---

## 📁 Project Structure

```bash
src/
├── main/
│   ├── java/com/bitespeed/
│   │   ├── controller/         # REST API controller
│   │   ├── services/           # Business logic
│   │   ├── dto/                # Request and response DTOs
│   │   ├── entities/           # JPA Entities
│   │   ├── repositories/       # Spring Data JPA interfaces
│   └── resources/
│       ├── application.yml
````

---

## 🔌 API Endpoint

### `POST /api/v1/identify`

**Request Body:**

```json
{
  "email": "john.doe@gmail.com",
  "phoneNumber": "123456"
}
```

**Response:**

```json
{
  "contact": {
    "primaryContactId": 1,
    "emails": ["john.doe@gmail.com", "jane.doe@gmail.com"],
    "phoneNumbers": ["123456"],
    "secondaryContactIds": [2]
  }
}
```

---

## 🛠️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/AnirudhSharma777/bitespeed.git
cd bitespeed
```

### 2. Configure MySQL

Edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://<host>:<port>/<dbname>?ssl-mode=REQUIRED
    username: <your-username>
    password: <your-password>
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect


```
### 3. Run the Application

```bash
./mvnw spring-boot:run
# OR
./gradlew bootRun
```

---

## 🧪 Testing

You can test the API using Postman or curl:

```bash
curl -X POST http://localhost:8080/api/v1/identify \
  -H "Content-Type: application/json" \
  -d '{"email":"john.doe@gmail.com", "phoneNumber":"123456"}'
```

---



## 📄 License

This project is open source and available under the [MIT License](LICENSE)