import { describe, expect, it } from 'vitest';
import {
  aplicarDeposito,
  aplicarRetiro,
  crearCuentaAhorros,
  createDataStore,
  registrarSocio,
} from '../../src/domain/models.js';
import { cuentaFixture, socioFixture } from '../fixtures/cooperativaFixtures.js';

describe('domain services', () => {
  it('registra socios y cuentas correctamente', () => {
    const store = createDataStore();

    registrarSocio(store, socioFixture);
    crearCuentaAhorros(store, cuentaFixture);

    expect(store.socios.size).toBe(1);
    expect(store.cuentas.size).toBe(1);
  });

  it('actualiza saldo con depósito y retiro', () => {
    const store = createDataStore();

    registrarSocio(store, socioFixture);
    crearCuentaAhorros(store, cuentaFixture);

    aplicarDeposito(store, { numeroCuenta: cuentaFixture.numeroCuenta, monto: 100000 });
    aplicarRetiro(store, { numeroCuenta: cuentaFixture.numeroCuenta, monto: 50000 });

    expect(store.cuentas.get(cuentaFixture.numeroCuenta)?.saldo).toBe(550000);
  });
});
