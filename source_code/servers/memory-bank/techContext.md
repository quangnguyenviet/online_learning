# Tech Context

## Technology Stack

### Backend
- **Language**: Java 21
- **Framework**: Spring Boot 3.3.9
- **Security**: Spring Security, OAuth2 Resource Server
- **Documentation**: SpringDoc OpenAPI (Swagger)

### Data & Storage
- **Database**: 
  - Primary: PostgreSQL
  - Testing: H2 (In-memory)
- **Migration**: Flyway
- **Cache**: Redis
- **File Storage**: AWS S3

### Infrastructure & Middleware
- **Service Discovery**: Netflix Eureka
- **Message Broker**: Apache Kafka
- **Infrastructure Monitoring**: Kafka UI (Port 8090)
- **Containerization**: Docker & Docker Compose

### Tools & Libraries
- **Build Tool**: Maven
- **Video Processing**: FFmpeg (specifically `ffprobe`)
- **Message Serialization**: Jackson JSON (with Spring Kafka)
- **Utilities**: 
  - Lombok (Boilerplate reduction)
  - MapStruct (Object mapping)
  - Spring WebFlux (Reactive programming)
  - WebSockets (Real-time communication)

## Development Environment Requirements
- **JDK**: Version 21 or higher.
- **FFmpeg**: Must be installed and configured in `PATH` or specified in `application.properties`.
- **Docker**: Used for local infrastructure (PostgreSQL, Redis, Kafka, Zookeeper, Kafka UI) via `docker-compose.yaml`.

## Development Standards
- **[RULE] No Hardcoded Credentials**: Absolutely no passwords, API keys, or sensitive information in source code or `application.properties`. Use environment variables `${VAR_NAME}` managed via `.env` files.
- **Environment Parity**: Maintain consistent environment variable names across different services for shared infrastructure (e.g., `KAFKA_BOOTSTRAP_SERVERS`).

## External Constraints
- **FFmpeg Path**: The system relies on external binaries for video processing, which must be correctly configured in each environment.
- **AWS Connectivity**: S3 integration requires valid credentials and network access to AWS endpoints.
