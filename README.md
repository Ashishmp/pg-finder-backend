📍 PG Finder Backend

PG Finder Backend is a Java + Spring Boot REST API for a role-based PG (Paying Guest) accommodation platform — supporting Users, PG Owners, and Admins with secure authentication, listings, bookings, and reviews.

This backend service powers the PG Finder ecosystem and is designed for scalability, clean architecture, and real-world usage with JWT security and MySQL persistence.

🚀 Features
🛠 Core Functionality

✔️ Authentication & Authorization
Role-based access using JWT tokens (User, PG Owner, Admin).

✔️ PG Listings
Create, list, update, search, and view PG details with public and private views.

✔️ Room Management
Manage rooms under PGs including sharing type, rent, AC/Non-AC, and availability.

✔️ Booking Management
Users can book rooms, while PG owners can approve, check-in, vacate, or cancel bookings.

✔️ Reviews & Ratings
Users can add reviews after successful bookings and view PG-wise reviews.

✔️ Amenities & Rules Management
Owners can configure PG amenities and house rules dynamically.

✔️ Admin Approval Flow
Admins can approve or reject PG listings before they go live.

✔️ User Profile Management
Users can view and update profiles and securely change passwords.

✔️ Robust Error Handling & Validation
Consistent API responses with proper HTTP status codes and validation messages.

✔️ API Documentation (Swagger UI)
Integrated OpenAPI documentation for easy API exploration and testing.

✔️ Docker Support
Optional Dockerized setup for simplified deployment.



🛠 Tech Stack
| Layer      | Technology            |
| ---------- | --------------------- |
| Backend    | Java, Spring Boot     |
| Database   | MySQL (JPA/Hibernate) |
| Security   | Spring Security, JWT  |
| API Docs   | Swagger / OpenAPI     |
| Build Tool | Maven                 |
| Deployment | Docker                |




pg-finder-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── config/            ← Security, JWT & Swagger configuration
│   │   │   ├── controller/        ← REST controllers (API layer)
│   │   │   ├── service/           ← Business logic & use cases
│   │   │   ├── repository/        ← Spring Data JPA repositories
│   │   │   ├── model/             ← JPA entity models
│   │   │   ├── dto/               ← Request / Response DTOs
│   │   │   ├── specification/     ← Filtering, sorting & pagination logic
│   │   │   └── exception/         ← Global & custom exception handling
│   │   │
│   │   └── PgFinderBackendApplication.java
│   │
│   └── resources/
│       ├── application.yml        ← Application configuration (DB, JWT, etc.)
│       └── db/                    ← Database scripts (optional)
│
├── Dockerfile                     ← Docker configuration
├── pom.xml                        ← Maven dependencies & build config
└── README.md                      ← Project documentation


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



🧪 How to Run
🏁 Prerequisites

✔ Java 17+
✔ Maven
✔ MySQL instance
✔ (Optional) Docker

1. Clone the repo

git clone https://github.com/Ashishmp/pg-finder-backend.git
cd pg-finder-backend

2. Configure environment
Create application.yml (or .env) with:

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/pgfinder
    username: your_db_user
    password: your_db_pass

jwt:
  secret: your_jwt_secret
  expiration_ms: 3600000

3. Run with Maven

mvn clean install
mvn spring-boot:run



🐳 Docker

Build:
docker build -t pg-finder-backend .

Run:
docker run -p 8080:8080 pg-finder-backend



📄 API Docs (Swagger)

After starting, access:
http://localhost:8080/swagger-ui.html



📞 Contact

Ashish Ranjan – Software Engineer
📧 Email: ashishranjanmp@gmail.com
GitHub: https://github.com/Ashishmp

❤️ Acknowledgements
Thanks to the open-source community, Spring docs, and related learning resources.
