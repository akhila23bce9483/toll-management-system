# Toll Management System

A full-stack Toll Management System built as a practical software project. It covers toll plazas, lanes, vehicles, vehicle classes, toll rates, FASTag-style wallets, transactions, reporting, and administration.

## Technology used
- **Backend:** Java 21, Spring Boot 3, Spring Data JPA, Spring Security
- **Database:** PostgreSQL
- **Frontend:** React, TypeScript, Vite
- **Development:** Docker Compose, Maven, npm

## Run locally

### Requirements
Install:
- Java 21
- Maven 3.9+
- Node.js 20+ and npm
- Docker Desktop
- Git

Check the installations:

```bash
java -version
mvn -version
node -v
npm -v
docker --version
```

### 1. Clone the repository

```bash
git clone https://github.com/akhila23bce9483/toll-management-system.git
cd toll-management-system
```

### 2. Start PostgreSQL

Make sure Docker Desktop is running, then from the project root:

```bash
docker compose up -d postgres
docker compose ps
```

### 3. Start the backend

Open **Terminal 1**:

```bash
cd backend
mvn spring-boot:run
```

The API normally runs at `http://localhost:8080`.

### 4. Start the frontend

Open **Terminal 2** from the project root:

```bash
cd frontend
npm install
npm run dev
```

Open the Vite URL shown in the terminal, normally **http://localhost:5173**.

### 5. Test the application

The backend creates demo data automatically when the database is empty:

- Vehicle: `AP37AB1234`
- FASTag: `TAG-DEMO-001`
- Vehicle class: `CAR`
- Starting wallet: `₹1000`
- Demo car toll: `₹100`

Use **Process toll payment** in the dashboard. The toll is deducted from the demo wallet and the transaction appears in the recent-transactions list.

### API smoke tests

```bash
curl http://localhost:8080/api/plazas
curl http://localhost:8080/api/vehicles
```

Process a demo toll:

```bash
curl -X POST http://localhost:8080/api/transactions/process -H "Content-Type: application/json" -d "{\"registrationNumber\":\"AP37AB1234\",\"plazaId\":1,\"laneNumber\":\"L1\"}"
```

### Troubleshooting

**Database:**
```bash
docker compose ps
docker compose logs postgres
docker compose down
docker compose up -d postgres
```

**Backend:**
```bash
cd backend
mvn clean spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

If the browser says the backend is unavailable, check that `http://localhost:8080/api/plazas` opens and that the backend terminal is still running. The frontend defaults to `http://localhost:8080/api`; this can be overridden with `frontend/.env`:

```env
VITE_API_URL=http://localhost:8080/api
```

### Stop everything

Stop the frontend/backend with `Ctrl + C`, then stop PostgreSQL:

```bash
docker compose down
```

For the expanded setup and smoke-test instructions, see:
- `docs/SETUP.md`
- `docs/LOCAL-RUN.md`

## Main features
- Toll plaza and lane management
- Vehicle and vehicle-class management
- Toll-rate configuration
- FASTag-style wallet balance
- Automatic toll calculation
- Toll transaction processing
- Transaction history
- Revenue dashboard
- Validation and error handling

## Scope

This is a software simulation of a production-style toll platform. Real highway equipment, ANPR cameras, RFID/FASTag readers, government toll networks, and banking/payment rails require certified external integrations and credentials. The project is designed so those integrations can be added later.
