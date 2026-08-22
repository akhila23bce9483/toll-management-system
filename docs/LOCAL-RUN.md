# Local launch

## Prerequisites
- Java 21
- Maven 3.9+
- Node.js 20+
- Docker Desktop (recommended for PostgreSQL)

## 1. Start PostgreSQL
From the repository root:

```bash
docker compose up -d postgres
```

## 2. Start the API

```bash
cd backend
mvn spring-boot:run
```

API: http://localhost:8080

## 3. Start the web app
Open a second terminal:

```bash
cd frontend
npm install
npm run dev
```

Web app: http://localhost:5173

## Demo data
The API seeds a demo plaza, toll rates, vehicle `AP37AB1234`, and a wallet with ₹1,000 on first startup.

## Smoke test
Open the web app, choose the seeded plaza and click **Process toll payment**. A successful car transaction should deduct ₹100 from the demo wallet and appear in Recent Transactions.

If Docker is not available, install PostgreSQL locally and set the datasource environment variables documented in `backend/src/main/resources/application.yml`.
