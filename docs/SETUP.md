# Local setup

## Requirements
- Java 21
- Maven 3.9+
- Node.js 20+
- Docker Desktop

## 1. Start PostgreSQL

From the repository root:

```bash
docker compose up -d postgres
```

## 2. Start the backend

```bash
cd backend
mvn spring-boot:run
```

The API runs at `http://localhost:8080`.

Demo data is created automatically on first startup:
- Plaza: `PLZ-001`
- Vehicle: `AP37AB1234`
- FASTag: `TAG-DEMO-001`
- Wallet balance: `₹1000`

## 3. Start the frontend

```bash
cd frontend
npm install
npm run dev
```

Open the Vite URL shown in the terminal, normally `http://localhost:5173`.

For a deployed environment set `VITE_API_URL` to the public backend API URL. Never use the development database password or JWT secret in production.

## API examples

List plazas:

```bash
curl http://localhost:8080/api/plazas
```

Process a toll:

```bash
curl -X POST http://localhost:8080/api/transactions/process \
  -H 'Content-Type: application/json' \
  -d '{"registrationNumber":"AP37AB1234","plazaId":1,"laneNumber":"L1"}'
```
