CREATE TABLE IF NOT EXISTS usuarios (
  id UUID PRIMARY KEY,
  email TEXT NOT NULL UNIQUE,
  password_hash TEXT NOT NULL,
  rol TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS socios (
  cedula TEXT PRIMARY KEY,
  nombre TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS cuentas (
  numero_cuenta TEXT PRIMARY KEY,
  socio_cedula TEXT NOT NULL REFERENCES socios(cedula) ON DELETE CASCADE,
  tipo TEXT NOT NULL,
  tasa_interes NUMERIC(10, 6) NOT NULL,
  saldo NUMERIC(14, 2) NOT NULL CHECK (saldo >= 0),
  created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS transacciones (
  id BIGSERIAL PRIMARY KEY,
  numero_cuenta TEXT NOT NULL REFERENCES cuentas(numero_cuenta) ON DELETE CASCADE,
  tipo TEXT NOT NULL,
  monto NUMERIC(14, 2) NOT NULL CHECK (monto > 0),
  saldo_anterior NUMERIC(14, 2) NOT NULL,
  saldo_nuevo NUMERIC(14, 2) NOT NULL,
  fecha TIMESTAMP NOT NULL DEFAULT now()
);
