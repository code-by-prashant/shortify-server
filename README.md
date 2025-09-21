
# 🚀 URL Shortener Service (Spring Boot + MySQL + Redis)

A **production-ready URL Shortener** built with **Spring Boot**, using **Base64 encoding** for short URLs.
This service supports **user registration with API keys**, **Redis caching**, and **automatic expiry cleanup**.

---

## 📌 Features

* ✅ User registration with **API key generation**
* ✅ Shorten long URLs into **Base64 encoded short links**
* ✅ Redirect short URL → original URL
* ✅ **API key validation** using interceptor
* ✅ **Redis caching** with TTL for fast lookups
* ✅ **MySQL persistence** for users & URLs
* ✅ **Scheduled cleanup** of expired URLs
* ✅ **Postman-ready APIs** for easy testing

---

## 🏗️ Architecture

```
Controller → Service → Repository → Database (MySQL)
                   ↘ Cache (Redis)
```

* **Controller** → Handles REST requests.
* **Service** → Business logic (URL shortening, validation).
* **Repository** → MySQL persistence.
* **Redis** → Cache short URLs for faster redirects.
* **Interceptor** → Validates API key before protected requests.

---

## ⚙️ Tech Stack

* **Java 17**
* **Spring Boot 3.4**
* **MySQL 8+** (persistence)
* **Redis** (caching)
* **Maven** (build tool)
* **Lombok** (boilerplate reduction)

---

## 📥 Setup Instructions

### 1. Clone the Repo

```bash
git clone https://github.com/code-by-prashant/shortify-server.git
cd shortify-server
```

### 2. Configure MySQL

Create a database:

```sql
CREATE DATABASE shortify;
```

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shortify?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=<yourpassword>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

### 3. Start Redis

If using Docker:

```bash
docker run -d --name redis -p 6379:6379 redis
```

### 4. Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🧪 API Endpoints (Test in Postman)

### 🔹 1. Register User

**POST** `http://localhost:8080/shortify/user/register`
**Body (JSON)**:

```json
{
  "name": "Prashant",
  "email": "prashant@gmail.com"
}
```

**Response**:

```json
{
  "id": 1,
  "name": "Prashant",
  "email": "prashant@gmail.com",
  "apiKey": "c2f1a9c0-5b0f-4c2a-b3e7-8e123456abcd"
}
```

---

### 🔹 2. Shorten URL

**POST** `http://localhost:8080/shortify/url/shorten`
**Headers**:

```
X-API-KEY: <your-api-key> 'example : c2f1a9c0-5b0f-4c2a-b3e7-8e123456abcd'
```

**Body (JSON)**:

```json
{
    "originalUrl": "https://github.com/code-by-prashant/shortify-server.git",
    "userId": "1"
}
```

**Response**:

```json
"MQ=="   // short URL ID in Base64
```

---

### 🔹 3. Redirect to Original URL

**GET** `http://localhost:8080/shortify/url/MQ==`
🔁 Redirects to `https://github.com/code-by-prashant/shortify-server.git`

---


## ✅ Example Flow in Postman

1. **Register a User** → Copy API key.
2. **Shorten URL** (pass `X-API-KEY`) → Get short code.
3. **Open Short URL** → Redirect to original link.

---

## 🐳 Optional: Install Docker (For Redis)


#### Windows / Mac

Download and install Docker Desktop:
[https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/)

#### Linux

```bash
sudo apt update
sudo apt install docker.io -y
sudo systemctl enable docker --now
```

Verify Docker installation:

```bash
docker --version
```

---

### Run Redis with Docker

Pull Redis image:

```bash
docker pull redis:latest
```

Run Redis container:

```bash
docker run -d --name redis-local -p 6379:6379 redis:latest
```

Check if Redis is running:

```bash
docker ps
```

Test connection:

```bash
docker exec -it redis-local redis-cli
ping
```

Expected response:

```
PONG
```


---

## 📚 Future Improvements

* Add **custom short codes** instead of Base64 only.
* Add **analytics** (click count, geo tracking).
* Add **role-based security** (Spring Security).
* Deploy on **Kubernetes + Helm**.

---

## 👨‍💻 Author

Made with ❤️ by **Prashant Mishra**
🔗 Free to use & improve


