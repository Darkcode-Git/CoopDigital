# CoopDigital

Monorepo Full Stack (estándar Full Stack Open) para la cooperativa digital **CoopDigital**.

## Estado actual

El código Java original permanece en `src/` como referencia de dominio. La nueva base de trabajo se migró a TypeScript con arquitectura de monorepo:

- `frontend/`: React + React Router + Redux Toolkit + Vitest.
- `backend/`: Node.js + Express + autenticación JWT + Vitest/Supertest.
- `shared/`: contratos de dominio tipados compartidos.
- `infra/`: Docker Compose y esquema PostgreSQL inicial.

## Requisitos

- Node.js 20+
- npm
- Docker (opcional, para stack completo)

## Instalación

```bash
npm install
```

## Comandos principales

```bash
npm run dev            # frontend + backend
npm run lint           # lint en workspaces
npm run typecheck      # chequeo estricto TS
npm run test           # pruebas unitarias e integración
npm run test:coverage  # cobertura de pruebas
npm run build          # build de shared + backend + frontend
npm run check          # lint + typecheck + test
```

## Testing configurado

### Backend

- Unit tests de dominio (`backend/tests/unit`).
- Integration tests API Express (`backend/tests/integration`).
- Cobertura mínima configurada en Vitest.

### Frontend

- Tests de componentes y rutas (`frontend/src/test`).
- Entorno `jsdom` con Testing Library.
- Cobertura mínima configurada en Vitest.

## API inicial (backend)

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/socios` (auth)
- `POST /api/socios` (auth)
- `POST /api/cuentas` (auth)
- `POST /api/transacciones/deposito` (auth)
- `POST /api/transacciones/retiro` (auth)
- `GET /api/reportes/saldos` (auth)

## Docker

```bash
cd infra
docker compose up --build
```

Servicios:

- Frontend: http://localhost:5173
- Backend: http://localhost:3001
- PostgreSQL: localhost:5432

## CI

Pipeline en `.github/workflows/ci.yml` ejecuta:

1. lint
2. typecheck
3. test
4. build

## Siguientes pasos recomendados

1. Reemplazar el store en memoria del backend por repositorios PostgreSQL reales.
2. Añadir migraciones y seeds.
3. Endurecer autorización por roles y refresh tokens.
4. Ampliar pruebas de comportamiento de UI y flujos críticos de transacciones.
5. Evaluar GraphQL solo para consultas complejas reales.
