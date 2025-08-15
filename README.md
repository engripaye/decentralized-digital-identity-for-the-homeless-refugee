<img width="1536" height="1024" alt="Digital ID for Refugees and Homeless" src="https://github.com/user-attachments/assets/750f2e50-9a56-4bb6-a794-17a2b2a1f6e4" />

---

```markdown
# 🪪 IDConnect — Digital Identity for the Homeless / Refugees

> A **secure, privacy-first digital identity wallet** built with **Java 21** and **Spring Boot 3.5**, enabling homeless individuals and refugees to **safely access services** without exposing unnecessary personal data.

---

## 🧾 Problem

Millions of **refugees and homeless individuals** face daily challenges accessing essential services like healthcare, shelter, or education because they lack **digital proof of identity**.

Without a verifiable way to prove who they are, many cannot:
- Apply for housing
- Access free meals and shelters
- Prove age for child/elder care services
- Share health/vaccination records

---

## 💡 Solution

**IDConnect** is a **decentralized digital identity wallet** that:
- Lets individuals **create secure, minimal digital profiles**
- Uses **OAuth2/OIDC login** (Google, Auth0, or government ID)
- Allows **NGOs/shelters** to verify specific identity claims with **user consent**
- Shares **only the data needed** for a service (e.g., *just age*, not full name)

---

## 🔐 Why OAuth2 + Scopes?

Using **OAuth2 scopes** and **JWT access tokens**:
- Share only **specific attributes** (e.g., `ageGroup`) instead of full profiles
- Enable **third-party NGO integrations** with fine-grained permissions
- Ensure **privacy, security, and auditability** for every identity request

---

## 🚀 Features

### 👤 For Users
- Create/update a **minimal, consent-driven profile**
- Log in securely with **OIDC (Auth0, Google, etc.)**
- Control **what information is shared** with NGOs
- View **verified claims** issued by NGOs (e.g., "Vaccinated", "Age Over 18")

### 🏢 For NGOs / Shelters
- **Verify identity claims** (scope: `verify_identity`)
- **Issue verified claims** (scope: `claims.issue`)
- Request **only the data needed** for a specific service

### 🛡 For Everyone
- Role-based access: `ROLE_USER`, `ROLE_NGO`, `ROLE_ADMIN`
- Fine-grained **scope-based API access control**
- **Audit logging** of all verifications and claims

---

## 🏗 Architecture

```

\[ User (Wallet) ] <-- OAuth2 --> \[ IDConnect API (Spring Boot) ] <---> \[ MySQL DB ]
↑                                  ↑
\|                                  |
\[ Auth0 / OIDC Provider ]         \[ NGOs / Shelters / Services ]

```

- **Java 21 + Spring Boot 3.5**
- **Spring Security OAuth2 Resource Server**
- **MySQL** for profile and claim storage
- **JWT** for secure data sharing
- **Scopes** for fine-grained API permissions

---

## 📂 Project Structure

```

idconnect/
├─ pom.xml
├─ src/main/java/dev/idconnect
│   ├─ IdConnectApplication.java
│   ├─ config/SecurityConfig.java
│   ├─ controller/
│   │   ├─ ProfileController.java
│   │   ├─ ClaimsController.java
│   │   └─ VerificationController.java
│   ├─ model/
│   │   ├─ UserProfile.java
│   │   ├─ VerifiedClaim.java
│   │   ├─ Organization.java
│   │   └─ AuditLog.java
│   ├─ repo/
│   │   ├─ UserProfileRepo.java
│   │   ├─ VerifiedClaimRepo.java
│   │   └─ OrganizationRepo.java
│   └─ service/
│       ├─ ProfileService.java
│       ├─ ClaimsService.java
│       └─ SigningService.java
└─ src/main/resources/application.yml

````

---

## ⚙️ Tech Stack

- **Backend**: Java 21, Spring Boot 3.5
- **Security**: Spring Security OAuth2 Resource Server
- **Database**: MySQL
- **Token Signing**: Nimbus JOSE + JWT
- **Authentication**: Auth0 / OIDC Provider
- **Build Tool**: Maven

---

## 🔧 Installation & Setup

### 1️⃣ Clone the repo
```bash
git clone https://github.com/yourusername/idconnect.git
cd idconnect
````

### 2️⃣ Configure MySQL

```sql
CREATE DATABASE idconnect;
CREATE USER 'idconnect'@'%' IDENTIFIED BY 'idconnect';
GRANT ALL PRIVILEGES ON idconnect.* TO 'idconnect'@'%';
```

### 3️⃣ Set up Auth0 (OIDC Provider)

* Create an Auth0 application
* Add an API with Identifier: `https://api.idconnect.example`
* Add scopes:

  * `profile.read`
  * `profile.update`
  * `verify_identity`
  * `claims.issue`
* Create roles: `ROLE_USER`, `ROLE_NGO`, `ROLE_ADMIN`

### 4️⃣ Configure `application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/idconnect
    username: idconnect
    password: idconnect
  jpa:
    hibernate:
      ddl-auto: update

spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://YOUR_AUTH0_TENANT_DOMAIN/
```

### 5️⃣ Run the application

```bash
./mvnw spring-boot:run
```

---

## 📡 API Endpoints

| Method | Endpoint                          | Scope(s)          | Role(s) | Description                   |
| ------ | --------------------------------- | ----------------- | ------- | ----------------------------- |
| GET    | `/api/profile/me`                 | `profile.read`    | USER    | Get own profile               |
| PUT    | `/api/profile/me`                 | `profile.update`  | USER    | Update own profile            |
| GET    | `/api/verify/{subject}/age-group` | `verify_identity` | NGO     | Verify age group with consent |
| POST   | `/api/claims/{subject}/issue`     | `claims.issue`    | NGO     | Issue verified claim          |
| GET    | `/api/claims/me`                  | `profile.read`    | USER    | Get own verified claims       |

---

## 🛡 Security Model

* **OAuth2 Scopes**: Limit what an access token can do
* **Roles**: Control high-level permissions (User, NGO, Admin)
* **Consent Flags**: Ensure user explicitly allows attribute sharing
* **Audit Logging**: Track all identity verification requests

---

## 📋 Example Usage

```bash
# Get own profile
curl -H "Authorization: Bearer $USER_TOKEN" \
     http://localhost:8080/api/profile/me

# Update profile
curl -X PUT -H "Content-Type: application/json" \
     -H "Authorization: Bearer $USER_TOKEN" \
     -d '{"preferredName":"Amina","birthYear":"1998","consentShareAge":true}' \
     http://localhost:8080/api/profile/me

# NGO verifies age group
curl -H "Authorization: Bearer $NGO_TOKEN" \
     http://localhost:8080/api/verify/{userSub}/age-group
```

---

## 📌 Roadmap

* [ ] W3C Verifiable Credentials integration
* [ ] MinIO/S3 storage for documents
* [ ] QR codes for temporary IDs
* [ ] Multi-language support
* [ ] Offline verification app

---

## 🤝 Contributing

We welcome contributions from:

* Developers
* NGOs
* Security/privacy experts

Steps:

1. Fork the repo
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📜 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

## 🌟 Acknowledgements

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Auth0](https://auth0.com/)
* [Nimbus JOSE + JWT](https://connect2id.com/products/nimbus-jose-jwt)
* NGOs & humanitarian workers who inspired this project

---

> *IDConnect exists to **restore dignity and access** to those who need it most. Privacy and safety are not optional — they are a human right.*

```

---

If you want, I can also **generate a matching project banner image** for the top of the README so it visually pops on GitHub. That would make it stand out more to NGOs and potential collaborators.
```
