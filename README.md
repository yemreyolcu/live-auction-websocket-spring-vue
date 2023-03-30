
# Kartaca Internship Case - Web Socket

This project consists of a series of tasks offered and requested by Kartaca company for internship. These are generally as follows. A web application that signals live is required for the auction process.






## Registration Key

```
81697120bf1479a377b0ea9d3373aba2b603dc3471bf277c61963a8ffd082e40
```

  
## Run Project

Clone project

```bash
  git clone https://github.com/yemreyolcu/auction-websocket-kartaca.git
```

Raise Containers

```bash
  docker-compose up
```

Click to client

```bash
  http://localhost:8081
```

  
## Used Techs

**Client:** Javascript, Vue.js, Vuetify

**Server:** Spring Boot, Spring Security, Redis, PostgreSQL, JWT

  
## API Usage

#### Log in

```http
  POST /api/auth/login
```

| Status | Type     | Content                |
| :-------- | :------- | :------------------------- |
| `200` | `object` | Returns jwt token  |

#### Register

```http
  POST /api/auth/register
```

| Status | Type     | Content                |
| :-------- | :------- | :------------------------- |
| `201` | `object` | Returns user type object  |

#### Get All Products

```http
  GET /api/product/products
```

| Status | Type     | Content                       |
| :-------- | :------- | :-------------------------------- |
| `200`      | `Array` | Returns products for client|



  