# System Patterns

## Architecture Overview
The project is currently in a transition phase from a **Monolithic Spring Boot** application to a **Microservices Architecture**.

### Current Components
1. **Online Learning Server**: The core monolith containing most business logic, course management, and user handling.
2. **Eureka Server**: Service discovery component (infrastructure for microservices).
3. **Notification Service**: A separate service dedicated to handling notifications.

## Design Patterns
- **Layered Architecture**: Standard `Controller` -> `Service` -> `Repository` pattern.
- **DTO (Data Transfer Objects)**: Used for API requests and responses to decouple the domain model from the API.
- **Automated Mapping**: MapStruct is used to convert between Entities and DTOs.
- **Repository Pattern**: Spring Data JPA interfaces for database abstraction.

## Key Technical Decisions
- **Video Metadata Extraction**: FFmpeg (via `ffprobe`) is used to extract duration and other metadata from uploaded videos.
- **Database Migrations**: Flyway is used to manage schema changes in a version-controlled manner.
- **Cloud Integration**: AWS S3 is utilized for scalable file storage.
- **Caching**: Redis is integrated for performance optimization (e.g., sessions, frequently accessed data).
- **Real-time Updates**: WebSockets are implemented for interactive features.

## Data Model Highlights
- **User/Role**: Standard RBAC for access control.
- **Course/Lesson**: Hierarchical structure for content delivery.
- **Progress Tracking**: Dedicated `LessonProgress` entity to maintain student state.
