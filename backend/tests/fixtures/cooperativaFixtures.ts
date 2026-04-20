import type { Cuenta, Socio } from '@coopdigital/shared';

export const socioFixture: Socio = {
  nombre: 'Ana Gómez',
  cedula: '1001',
};

export const cuentaFixture: Cuenta = {
  numeroCuenta: 'AH-1001-1',
  saldo: 500000,
  socioCedula: '1001',
  tipo: 'AHORROS',
  tasaInteres: 0.02,
};
