import type {
  AuthUser,
  Cuenta,
  DepositoPayload,
  ReporteSaldos,
  RetiroPayload,
  Socio,
  Transaccion,
} from '@coopdigital/shared';

export interface DataStore {
  socios: Map<string, Socio>;
  cuentas: Map<string, Cuenta>;
  usuarios: Map<string, AuthUser>;
  transacciones: Transaccion[];
}

export const createDataStore = (): DataStore => ({
  socios: new Map(),
  cuentas: new Map(),
  usuarios: new Map(),
  transacciones: [],
});

export const registrarSocio = (store: DataStore, socio: Socio): Socio => {
  if (store.socios.has(socio.cedula)) {
    throw new Error('Socio ya registrado');
  }

  store.socios.set(socio.cedula, { ...socio });
  return socio;
};

export const crearCuentaAhorros = (store: DataStore, cuenta: Cuenta): Cuenta => {
  if (store.cuentas.has(cuenta.numeroCuenta)) {
    throw new Error('La cuenta ya existe');
  }

  const socio = store.socios.get(cuenta.socioCedula);
  if (!socio) {
    throw new Error('Socio no encontrado');
  }

  store.cuentas.set(cuenta.numeroCuenta, { ...cuenta });
  return cuenta;
};

export const aplicarDeposito = (store: DataStore, payload: DepositoPayload): Cuenta => {
  const cuenta = store.cuentas.get(payload.numeroCuenta);
  if (!cuenta) {
    throw new Error('Cuenta no encontrada');
  }

  if (payload.monto <= 0) {
    throw new Error('Monto inválido');
  }

  const saldoAnterior = cuenta.saldo;
  cuenta.saldo += payload.monto;

  store.transacciones.push({
    tipo: 'DEPOSITO',
    numeroCuenta: cuenta.numeroCuenta,
    monto: payload.monto,
    fecha: new Date().toISOString(),
    saldoAnterior,
    saldoNuevo: cuenta.saldo,
  });

  return cuenta;
};

export const aplicarRetiro = (store: DataStore, payload: RetiroPayload): Cuenta => {
  const cuenta = store.cuentas.get(payload.numeroCuenta);
  if (!cuenta) {
    throw new Error('Cuenta no encontrada');
  }

  if (payload.monto <= 0) {
    throw new Error('Monto inválido');
  }

  if (cuenta.saldo < payload.monto) {
    throw new Error('Saldo insuficiente');
  }

  const saldoAnterior = cuenta.saldo;
  cuenta.saldo -= payload.monto;

  store.transacciones.push({
    tipo: 'RETIRO',
    numeroCuenta: cuenta.numeroCuenta,
    monto: payload.monto,
    fecha: new Date().toISOString(),
    saldoAnterior,
    saldoNuevo: cuenta.saldo,
  });

  return cuenta;
};

export const obtenerReporteSaldos = (store: DataStore): ReporteSaldos => {
  const cuentas = Array.from(store.cuentas.values());
  const total = cuentas.reduce((acc, cuenta) => acc + cuenta.saldo, 0);

  return {
    totalCuentas: cuentas.length,
    saldoTotal: total,
    saldoPromedio: cuentas.length ? total / cuentas.length : 0,
  };
};
