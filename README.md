![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![JWT](https://img.shields.io/badge/Auth-JWT-red)
![Swagger](https://img.shields.io/badge/API-Swagger-green)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)




📍 PG Finder Backend

PG Finder Backend is a Java + Spring Boot REST API for a role-based PG (Paying Guest) accommodation platform — supporting Users, PG Owners, and Admins with secure authentication, listings, bookings, and reviews.

This backend service powers the PG Finder ecosystem and is designed for scalability, clean architecture, and real-world usage with JWT security and MySQL persistence.

🚀 Features

🔐 JWT Authentication & Role-Based Authorization

🏘️ PG & Room Management

🔎 Advanced PG Search with filters

📅 Booking Management (User & Owner views)

⭐ Reviews & Ratings

📊 Owner Analytics Dashboard

💳 Payment Simulation (Admin-only)

🐳 Dockerized Setup (Backend + PostgreSQL)

📄 Swagger API Documentation



🛠 Tech Stack
| Layer      | Technology                  |
| ---------- | --------------------------- |
| Backend    | Spring Boot 4, Java 17      |
| Security   | Spring Security, JWT        |
| Database   | PostgreSQL 15               |
| ORM        | Hibernate, JPA              |
| Build Tool | Maven                       |
| Containers | Docker, Docker Compose      |
| Docs       | Swagger (Springdoc OpenAPI) |


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
├── docker-compose.yml                    # App + PostgreSQL
├── pom.xml                               # Maven build config
└── README.md                             # Project documentation
 configuration
└── README.md                          ← Project documentation
```


🔐 Authentication

| Method | Endpoint                | Description                 |
| ------ | ----------------------- | --------------------------- |
| POST   | `/api/v1/auth/register` | Register a new user         |
| POST   | `/api/v1/auth/login`    | Login and receive JWT token |
| POST   | `/api/v1/auth/logout`   | Logout user                 |

👤 User Profile

| Method | Endpoint                 | Description                   |
| ------ | ------------------------ | ----------------------------- |
| GET    | `/api/users/me`          | Get logged-in user profile    |
| PUT    | `/api/users/me`          | Update logged-in user profile |
| PATCH  | `/api/users/me/password` | Change account password       |

🏠 PG Management

| Method | Endpoint                  | Description                        |
| ------ | ------------------------- | ---------------------------------- |
| POST   | `/api/v1/pgs`             | Create a new PG (Owner)            |
| GET    | `/api/v1/pgs`             | Get all PGs                        |
| GET    | `/api/v1/pgs/{id}`        | Get PG by ID                       |
| PUT    | `/api/v1/pgs/{id}`        | Update PG                          |
| DELETE | `/api/v1/pgs/{id}`        | Delete PG                          |
| PATCH  | `/api/v1/pgs/{id}/status` | Enable / Disable PG                |
| GET    | `/api/v1/pgs/{id}/full`   | Get full PG details (private view) |
| GET    | `/api/v1/pgs/search`      | Search PGs with filters            |

🛏 Room Management

| Method | Endpoint                   | Description     |
| ------ | -------------------------- | --------------- |
| POST   | `/api/v1/rooms/pgs/{pgId}` | Add room to PG  |
| GET    | `/api/v1/rooms/pgs/{pgId}` | Get rooms by PG |
| PUT    | `/api/v1/rooms/{roomId}`   | Update room     |
| DELETE | `/api/v1/rooms/{roomId}`   | Delete room     |

📅 Booking Management

| Method | Endpoint                                | Description            |
| ------ | --------------------------------------- | ---------------------- |
| POST   | `/api/v1/bookings/rooms/{roomId}`       | Create booking         |
| PUT    | `/api/v1/bookings/{bookingId}/approve`  | Approve booking        |
| PUT    | `/api/v1/bookings/{bookingId}/check-in` | Check-in               |
| PUT    | `/api/v1/bookings/{bookingId}/vacate`   | Vacate room            |
| PUT    | `/api/v1/bookings/{bookingId}/cancel`   | Cancel booking         |
| GET    | `/api/v1/bookings/me`                   | Get my bookings (User) |
| GET    | `/api/v1/bookings/owner`                | Get owner bookings     |

⭐ Reviews

| Method | Endpoint                               | Description              |
| ------ | -------------------------------------- | ------------------------ |
| POST   | `/api/v1/reviews/bookings/{bookingId}` | Add review after booking |
| GET    | `/api/v1/reviews/pgs/{pgId}`           | Get reviews for PG       |
| DELETE | `/api/v1/reviews/{reviewId}`           | Delete review            |

🧑‍💼 Owner APIs

| Method | Endpoint                       | Description               |
| ------ | ------------------------------ | ------------------------- |
| GET    | `/api/v1/owners/me/pgs`        | Get all PGs owned by user |
| GET    | `/api/v1/owners/me/pgs/{pgId}` | Get specific owned PG     |

🛡 Admin – PG Approval

| Method | Endpoint                           | Description              |
| ------ | ---------------------------------- | ------------------------ |
| GET    | `/api/v1/admin/pgs/pending`        | Get pending PG approvals |
| PUT    | `/api/v1/admin/pgs/{pgId}/approve` | Approve PG               |
| PUT    | `/api/v1/admin/pgs/{pgId}/reject`  | Reject PG                |

🧰 Amenities & Rules

| Method | Endpoint                    | Description         |
| ------ | --------------------------- | ------------------- |
| PUT    | `/api/pgs/{pgId}/amenities` | Update PG amenities |
| PUT    | `/api/pgs/{pgId}/rules`     | Update PG rules     |
| GET    | `/api/amenities`            | Get all amenities   |

❤️ Health Check

| Method | Endpoint                   | Description              |
| ------ | -------------------------- | ------------------------ |
| GET    | `/api/public/health-check` | Application health check |

Owner Analytics API

| API                                             | Access |
| ----------------------------------------------- | ------ |
| GET `/api/v1/analytics/owner/dashboard/summary` | OWNER  |

Payment (Simulation)

| API                                      | Access |
| ---------------------------------------- | ------ |
| POST `/api/v1/payments/simulate/success` | ADMIN  |
| POST `/api/v1/payments/simulate/failure` | ADMIN  |





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
