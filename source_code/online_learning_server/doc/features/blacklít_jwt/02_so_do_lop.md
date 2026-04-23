# Blacklist JWT khi logout - Sơ đồ lớp (Mermaid)

```mermaid
classDiagram
    class AuthenticationController {
        -authenticationService: AuthenticationService
        +logout(request: LogoutRequest): ApiResponse~Void~
    }

    class AuthenticationService {
        -jwtService: JwtService
        -tokenBlacklistService: TokenBlacklistService
        +logout(request: LogoutRequest): void
        +introspect(request: IntrospectRequest): IntrospectResponse
        -parseAndValidateToken(token: String): JwtClaims
    }

    class JwtService {
        +extractJti(token: String): String
        +extractExp(token: String): Instant
        +verify(token: String): JwtClaims
    }

    class TokenBlacklistService {
        -keyPrefix: String
        +blacklist(jti: String, ttlSeconds: long): void
        +isBlacklisted(jti: String): boolean
        -buildKey(jti: String): String
    }

    class RedisTemplate {
        +opsForValue().set(key: String, value: String, ttl: Duration): void
        +hasKey(key: String): Boolean
    }

    class SecurityFilter {
        -jwtService: JwtService
        -tokenBlacklistService: TokenBlacklistService
        +doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain): void
    }

    class LogoutRequest {
        +token: String
    }

    class IntrospectRequest {
        +token: String
    }

    class IntrospectResponse {
        +valid: boolean
        +subject: String
        +expiryTime: Instant
    }

    class JwtClaims {
        +jti: String
        +sub: String
        +exp: Instant
        +scope: String
    }

    class ApiResponse~T~ {
        +status: int
        +message: String
        +data: T
    }

    AuthenticationController --> AuthenticationService : gọi logout
    AuthenticationService --> JwtService : parse JWT
    AuthenticationService --> TokenBlacklistService : ghi blacklist
    TokenBlacklistService --> RedisTemplate : lưu/đọc key
    SecurityFilter --> JwtService : verify JWT
    SecurityFilter --> TokenBlacklistService : check blacklist
    AuthenticationController ..> LogoutRequest
    AuthenticationController ..> ApiResponse~Void~
    AuthenticationService ..> IntrospectRequest
    AuthenticationService ..> IntrospectResponse
    AuthenticationService ..> JwtClaims
    JwtService ..> JwtClaims
```

## Ghi chú
- `TokenBlacklistService` là lớp trung tâm khi tích hợp Redis.
- `SecurityFilter` (hoặc lớp verify token tương đương) cần kiểm tra blacklist ở mọi request bảo vệ.
- Có thể dùng `StringRedisTemplate` thay cho `RedisTemplate` nếu chỉ lưu chuỗi.
