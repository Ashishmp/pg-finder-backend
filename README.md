![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL%2015-blue)
![Redis](https://img.shields.io/badge/Cache-Redis-red)
![JWT](https://img.shields.io/badge/Auth-JWT-red)
![Rate Limiting](https://img.shields.io/badge/Security-Rate%20Limiting-critical)
![Swagger](https://img.shields.io/badge/API-Swagger-green)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)




📍 PG Finder Backend

PG Finder Backend is a Java + Spring Boot REST API for a role-based PG (Paying Guest) accommodation platform — supporting Users, PG Owners, and Admins with secure authentication, listings, bookings, and reviews.

This backend service powers the PG Finder ecosystem and is designed for scalability, clean architecture, and real-world usage with JWT security, PostgreSQL persistence, Redis-based caching, and distributed rate limiting.


🚀 Features

🔐 JWT Authentication & Role-Based Authorization  
🏘️ PG & Room Management  
🔎 Advanced PG Search with filters  
📅 Booking Management (User & Owner views)  
⭐ Reviews & Ratings  
📊 Owner Analytics Dashboard  
💳 Payment Simulation (Admin-only)  
⚡ Redis-based Caching  
🚦 Distributed Rate Limiting (Redis-backed)  
🐳 Dockerized Setup (Backend + PostgreSQL + Redis)  
📄 Swagger API Documentation  




🧠 Architecture Highlights

- Stateless JWT-based authentication
- Redis-backed distributed caching
- Redis-backed rate limiting at security filter level
- Clean layered architecture (Controller → Service → Repository)
- Docker-first local and production setup


```
> All secured APIs require:
> Authorization: Bearer <JWT_TOKEN>
```

🛠 Tech Stack

| Layer        | Technology                          |
|-------------|--------------------------------------|
| Backend     | Spring Boot 4, Java 17                |
| Security    | Spring Security, JWT                 |
| Database    | PostgreSQL 15                        |
| Cache       | Redis                                |
| Rate Limit  | Redis (Custom Spring Security Filter)|
| ORM         | Hibernate, JPA                       |
| Build Tool  | Maven                                |
| Containers  | Docker, Docker Compose               |
| Docs        | Swagger (Springdoc OpenAPI)          |

⚡ Redis & Rate Limiting

This application uses Redis for:

- Caching high-read public APIs (e.g. PG listings)
- Implementing distributed rate limiting to prevent API abuse

### Rate Limiting Details
- Implemented as a custom Spring Security filter
- Redis-backed counters with TTL-based sliding window
- Limits excessive requests per IP per minute
- Automatically resets after time window expires

### Example Behavior
- Normal traffic → HTTP 200
- Excessive requests → HTTP 429 (Too Many Requests)


👥 Roles & Permissions
| Role  | Description                                         |
| ----- | --------------------------------------------------- |
| USER  | Search PGs, book rooms, view bookings               |
| OWNER | Manage PGs & rooms, view owner bookings & analytics |
| ADMIN | Approve PGs, simulate payments, platform control    |



```text
pg-finder-backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/pgfinder/pg_finder_backend/
│       │       ├── config/                 # Security, JWT, Swagger, CORS configs
│       │       ├── controller/
│       │       │   ├── auth/               # Login, register APIs
│       │       │   ├── admin/              # Admin-only APIs
│       │       │   ├── analytics/          # Owner analytics dashboard APIs
│       │       │   ├── booking/            # Booking APIs
│       │       │   ├── pg/                 # PG browsing & management APIs
│       │       │   └── room/               # Room management APIs
│       │       ├── service/
│       │       │   ├── impl/               # Service implementations
│       │       │   └── analytics/          # Analytics services
│       │       ├── repository/
│       │       │   └── analytics/          # Analytics custom repositories
│       │       ├── entity/                 # JPA entities (User, Pg, Room, Booking, Payment, Review)
│       │       ├── dto/
│       │       │   ├── request/            # Request DTOs
│       │       │   ├── response/           # Response DTOs
│       │       │   └── analytics/           # Analytics DTOs
│       │       ├── mapper/                 # Entity ↔ DTO mappers
│       │       ├── exception/              # Global & custom exceptions
│       │       ├── security/               # JWT, UserDetails, filters
│       │       └── PgFinderBackendApplication.java
│       │
│       └── resources/
│           ├── application.yml             # App configuration
│           └── db/migration/               # Flyway migration scripts
│
├── Dockerfile                            # Backend Docker image
├── docker-compose.yml                    # App + PostgreSQL + Redis
├── pom.xml                               # Maven build config
└── README.md                             # Project documentation
 configuration
└── README.md                          ← Project documentation
```


🔐 Authentication & User

| Method | Endpoint                   | Role                 | Description                |
| ------ | -------------------------- | -------------------- | -------------------------- |
| POST   | `/api/v1/auth/register`    | Public               | Register a new user        |
| POST   | `/api/v1/auth/login`       | Public               | Login and get JWT          |
| POST   | `/api/v1/auth/logout`      | Authenticated        | Logout user                |
| PUT    | `/api/v1/auth/update/{id}` | Authenticated        | Update user details        |
| GET    | `/api/users/me`            | USER / OWNER / ADMIN | Get logged-in user profile |
| PUT    | `/api/users/me`            | USER / OWNER / ADMIN | Update profile             |
| PATCH  | `/api/users/me/password`   | USER / OWNER / ADMIN | Change password            |


🏠 PG Management

| Method | Endpoint                | Role                 | Description             |
| ------ | ----------------------- | -------------------- | ----------------------- |
| GET    | `/api/v1/pgs`           | Public               | List all PGs            |
| GET    | `/api/v1/pgs/{id}`      | Public               | Get PG basic details    |
| GET    | `/api/v1/pgs/{id}/full` | USER / OWNER / ADMIN | Get full PG details     |
| GET    | `/api/v1/pgs/search`    | Public               | Search PGs with filters |
| POST   | `/api/v1/pgs`           | OWNER                | Create a new PG         |
| PUT    | `/api/v1/pgs/{id}`      | OWNER                | Update PG               |
| DELETE | `/api/v1/pgs/{id}`      | OWNER                | Delete PG               |


🛏 Room Management

| Method | Endpoint                   | Role   | Description        |
| ------ | -------------------------- | ------ | ------------------ |
| GET    | `/api/v1/pgs/{pgId}/rooms` | Public | Get rooms for a PG |
| POST   | `/api/v1/pgs/{pgId}/rooms` | OWNER  | Add room to PG     |
| PUT    | `/api/v1/rooms/{roomId}`   | OWNER  | Update room        |
| DELETE | `/api/v1/rooms/{roomId}`   | OWNER  | Delete room        |


📅 Booking Management

| Method | Endpoint                                | Role         | Description                  |
| ------ | --------------------------------------- | ------------ | ---------------------------- |
| POST   | `/api/v1/bookings/rooms/{roomId}`       | USER         | Create booking               |
| GET    | `/api/v1/bookings/me`                   | USER         | Get my bookings              |
| GET    | `/api/v1/bookings/owner`                | OWNER        | Get bookings for owner’s PGs |
| PUT    | `/api/v1/bookings/{bookingId}/approve`  | OWNER        | Approve booking              |
| PUT    | `/api/v1/bookings/{bookingId}/check-in` | OWNER        | Check-in booking             |
| PUT    | `/api/v1/bookings/{bookingId}/vacate`   | OWNER        | Vacate booking               |
| PUT    | `/api/v1/bookings/{bookingId}/cancel`   | USER / OWNER | Cancel booking               |


⭐ Reviews

| Method | Endpoint                               | Role   | Description    |
| ------ | -------------------------------------- | ------ | -------------- |
| POST   | `/api/v1/reviews/bookings/{bookingId}` | USER   | Add review     |
| GET    | `/api/v1/reviews/pgs/{pgId}`           | Public | Get PG reviews |
| DELETE | `/api/v1/reviews/{reviewId}`           | USER   | Delete review  |

🧑‍💼 Owner APIs

| Method | Endpoint                       | Role  | Description           |
| ------ | ------------------------------ | ----- | --------------------- |
| GET    | `/api/v1/owners/me/pgs`        | OWNER | Get my PGs            |
| GET    | `/api/v1/owners/me/pgs/{pgId}` | OWNER | Get specific owned PG |


📊 Analytics & Dashboard

| Method | Endpoint                          | Role  | Description             |
| ------ | --------------------------------- | ----- | ----------------------- |
| GET    | `/api/v1/owner/dashboard/summary` | OWNER | Owner dashboard metrics |
| GET    | `/api/v1/analytics/owner/summary` | OWNER | Owner analytics summary |

🧰 Amenities & Rules

| Method | Endpoint                    | Role   | Description         |
| ------ | --------------------------- | ------ | ------------------- |
| GET    | `/api/amenities`            | Public | Get all amenities   |
| PUT    | `/api/pgs/{pgId}/amenities` | OWNER  | Update PG amenities |
| PUT    | `/api/pgs/{pgId}/rules`     | OWNER  | Update PG rules     |


🛠️ Admin APIs

| Method | Endpoint                           | Role  | Description     |
| ------ | ---------------------------------- | ----- | --------------- |
| GET    | `/api/v1/admin/pgs/pending`        | ADMIN | Get pending PGs |
| PUT    | `/api/v1/admin/pgs/{pgId}/approve` | ADMIN | Approve PG      |
| PUT    | `/api/v1/admin/pgs/{pgId}/reject`  | ADMIN | Reject PG       |



❤️ Health Check

| Method | Endpoint                   | Description              |
| ------ | -------------------------- | ------------------------ |
| GET    | `/api/public/health-check` | Application health check |


💳 Payment Simulation

| Method | Endpoint                                | Role  | Description                         |
| ------ | --------------------------------------- | ----- | ----------------------------------- |
| POST   | `/api/v1/payments/{bookingId}/simulate` | ADMIN | Simulate payment (SUCCESS / FAILED) |






🐳 Running with Docker (Recommended)
Prerequisites

Docker
Docker Compose
```
Steps
git clone https://github.com/Ashishmp/pg-finder-backend.git
cd pg-finder-backend
docker compose up --build
```

Backend will be available at:
```
http://localhost:8080
```

PostgreSQL:
```
localhost:5432
```
▶️ Running Locally (Without Docker)

Install PostgreSQL
```
Create database:

CREATE DATABASE pgfinder;
```

Update application.yml

Run:
```
mvn spring-boot:run
```
## 📞 Contact

**Ashish Ranjan** – Software Engineer  
📧 **Email:** ashishranjanmp@gmail.com  
📞 **Phone:** +91 7294075490  
🐙 **GitHub:** https://github.com/Ashishmp  

❤️ Acknowledgements
Thanks to the open-source community, Spring docs, and related learning resources.
