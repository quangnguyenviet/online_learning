# Blacklist JWT khi logout - Sequence Diagram (Mermaid)

```mermaid
sequenceDiagram
    autonumber
    actor U as User
    participant C as Client
    participant AC as AuthenticationController
    participant AS as AuthenticationService
    participant JS as JwtService
    participant R as Redis
    participant SF as SecurityFilter

    U->>C: Nhấn logout
    C->>AC: POST /auth/logout (Bearer token)
    AC->>AS: logout(request)
    AS->>JS: verify + extract(jti, exp)
    JS-->>AS: jti, exp
    AS->>AS: ttl = exp - now

    alt ttl > 0
        AS->>R: SET auth:blacklist:{jti} = 1 EX ttl
        R-->>AS: OK
    else ttl <= 0
        AS-->>AS: Bỏ qua ghi blacklist
    end

    AS-->>AC: logout success
    AC-->>C: 200 OK

    Note over U,C: User dùng lại token cũ
    U->>C: Gửi request API bảo vệ
    C->>SF: Authorization: Bearer token cũ
    SF->>JS: verify + extract jti
    JS-->>SF: jti
    SF->>R: EXISTS auth:blacklist:{jti}
    R-->>SF: true
    SF-->>C: 401 Unauthorized
```

## Điểm kiểm soát bảo mật
- Check blacklist phải nằm trong luồng xác thực cho mọi endpoint bảo vệ.
- TTL phải bám sát `exp` để tránh key sống lâu không cần thiết.
- Ưu tiên token có `jti` duy nhất cho từng lần phát hành.
