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

### Tools & Libraries
- **Build Tool**: Maven
- **Video Processing**: FFmpeg (specifically `ffprobe`)
- **Utilities**: 
  - Lombok (Boilerplate reduction)
  - MapStruct (Object mapping)
  - Spring WebFlux (Reactive programming)
  - WebSockets (Real-time communication)

## Development Environment Requirements
- **JDK**: Version 21 or higher.
- **FFmpeg**: Must be installed and configured in `PATH` or specified in `application.properties`.
- **Docker**: Used for local infrastructure (MySQL, Redis, etc.) via `docker-compose.yaml`.

## External Constraints
- **FFmpeg Path**: The system relies on external binaries for video processing, which must be correctly configured in each environment.
- **AWS Connectivity**: S3 integration requires valid credentials and network access to AWS endpoints.
