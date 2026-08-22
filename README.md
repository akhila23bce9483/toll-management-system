# Toll Management System

A real-world full-stack toll management platform for toll plazas, lanes, vehicles, toll rates, FASTag-style accounts, transactions, operators, reporting, and administration.

## Stack
- Backend: Spring Boot 3, Java 21, Spring Security, JPA, PostgreSQL
- Frontend: React, Vite, TypeScript
- Infrastructure: Docker Compose

## Core modules
- Authentication and role-based access
- Admin, operator, and user dashboards
- Toll plaza and lane management
- Vehicle and vehicle-class management
- Dynamic toll-rate configuration
- FASTag-style wallet accounts
- Automatic toll calculation
- Toll transactions and receipts
- Refunds and transaction audit trail
- Operator and shift management
- Revenue and operational reports
- CSV export

## Important scope note
This project simulates a production-style toll platform. Real highway hardware, ANPR cameras, RFID/FASTag readers, government toll networks, and banking/payment rails require certified external integrations and credentials; this repository provides clean integration points for those systems.

## Local development
See `docs/SETUP.md`. PostgreSQL is provided through Docker Compose.
