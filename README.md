
# 🎓 Microservices Spring Boot avec Gateway, Eureka, Keycloak, MySQL et Docker

Ce projet met en œuvre une architecture microservices complète comprenant :

- 🎯 `etudiantislem` : microservice de gestion des étudiants (Spring Boot, JPA, MySQL)
- 🌐 `apiGateway` : API Gateway basée sur Spring Cloud Gateway (avec sécurité Keycloak)
- 📘 `eureka-server` : serveur de découverte Eureka
- 🔐 `Keycloak` : serveur d'authentification OpenID Connect
- 🐬 **MySQL** : base de données relationnelle utilisée par `etudiantislem`
- 🐳 Docker & Docker Compose : containerisation de tous les composants

---

## ⚙️ Technologies utilisées

| Composant          | Description                              |
|--------------------|------------------------------------------|
| Java 21            | Langage principal                        |
| Spring Boot 3.3.0  | Framework principal                      |
| Spring Cloud 2023.0.1 | Eureka, Gateway, Discovery client     |
| Spring Security    | Sécurisation avec OAuth2 / JWT          |
| Keycloak 23        | Fournisseur d'identité (authentification) |
| MySQL 8.x          | Base de données relationnelle            |
| Docker             | Conteneurisation                        |
| Docker Compose     | Orchestration multi-conteneurs          |
| Postman            | Tests d’API                             |
| Maven              | Outil de build                          |

---

## 🗂️ Structure des microservices

```
PI-Stagy/
│
├── eureka-server/
│   └── Dockerfile
│
├── apiGateway/
│   ├── apiGateway/
│   │   └── Dockerfile
│
├── BACKEND/
│   └── Etudiant/
│       └── Dockerfile
│
└── docker-compose.yml
```

---

## 🐳 Docker : Build & Lancement

### 1. Compilation des projets

Assure-toi d'avoir bien exécuté :

```bash
mvn clean package -DskipTests
```

### 2. Lancement des services

```bash
docker-compose up --build
```

### 3. Vérification

- Eureka : http://localhost:8761  
- API Gateway : http://localhost:8058  
- Keycloak : http://localhost:8180  
- Etudiant : http://localhost:8081  

---

## 🔐 Sécurité Keycloak

| Paramètre                 | Valeur                            |
|--------------------------|------------------------------------|
| Realm                    | `AppWeb`                          |
| Client ID                | `apiGateway`                      |
| Root URL                 | `http://localhost:8058`           |
| Valid Redirect URIs      | `http://localhost:8058/*`         |
| Web Origins              | `*`                               |
| Port d’écoute            | `8180`                            |

⚠️ Activez **Client Authentication** si vous utilisez des tokens dans Postman.

---

## 🧪 Tester avec Postman

1. **Type d’authentification : OAuth 2.0**
2. **Token URL** : `http://localhost:8180/realms/AppWeb/protocol/openid-connect/token`
3. **Client ID** : `apiGateway`
4. **Client Secret** : (copier depuis l’onglet "Credentials" dans Keycloak)
5. **Scope** : `openid`
6. Cliquez sur "Get New Access Token", puis "Use Token"

---

## 📦 Publication Docker Hub

```bash
docker login
docker tag etudiantislem trabelsi9/etudiantislem
docker push trabelsi9/etudiantislem

docker tag eureka-server trabelsi9/eureka-server
docker push trabelsi9/eureka-server

docker tag api-gateway trabelsi9/api-gateway
docker push trabelsi9/api-gateway
```

---

## 👤 Auteur

- **Trabelsi Islem**
- Docker Hub: [trabelsi9](https://hub.docker.com/u/trabelsi9)

---
