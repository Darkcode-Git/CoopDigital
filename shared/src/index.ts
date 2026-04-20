export interface Socio {
  nombre: string;
  cedula: string;
}

export interface Cuenta {
  numeroCuenta: string;
  saldo: number;
  socioCedula: string;
  tipo: 'AHORROS';
  tasaInteres: number;
}

export interface Transaccion {
  tipo: 'DEPOSITO' | 'RETIRO';
  numeroCuenta: string;
  monto: number;
  fecha: string;
  saldoAnterior: number;
  saldoNuevo: number;
}

export interface DepositoPayload {
  numeroCuenta: string;
  monto: number;
}

export interface RetiroPayload {
  numeroCuenta: string;
  monto: number;
}

export interface ReporteSaldos {
  totalCuentas: number;
  saldoTotal: number;
  saldoPromedio: number;
}

export interface AuthCredentials {
  email: string;
  password: string;
}

export interface AuthUser {
  id: string;
  email: string;
  passwordHash: string;
  role: 'SOCIO' | 'ADMIN';
}

export interface Session {
  token: string;
  user: {
    id: string;
    email: string;
    role: 'SOCIO' | 'ADMIN';
  };
}
