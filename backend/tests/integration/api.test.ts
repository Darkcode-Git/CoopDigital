import request from 'supertest';
import { describe, expect, it } from 'vitest';
import { buildApp } from '../../src/app.js';

describe('API integration', () => {
  it('permite flujo básico de auth y cooperativa', async () => {
    const app = buildApp();

    const register = await request(app).post('/api/auth/register').send({
      email: 'ana@coopdigital.test',
      password: 'strong-pass',
    });
    expect(register.status).toBe(201);

    const login = await request(app).post('/api/auth/login').send({
      email: 'ana@coopdigital.test',
      password: 'strong-pass',
    });
    expect(login.status).toBe(200);

    const token = login.body.token as string;

    const socio = await request(app)
      .post('/api/socios')
      .set('Authorization', `Bearer ${token}`)
      .send({ nombre: 'Ana Gómez', cedula: '1001' });
    expect(socio.status).toBe(201);

    const cuenta = await request(app)
      .post('/api/cuentas')
      .set('Authorization', `Bearer ${token}`)
      .send({
        numeroCuenta: 'AH-1001-1',
        saldo: 500000,
        socioCedula: '1001',
        tipo: 'AHORROS',
        tasaInteres: 0.02,
      });
    expect(cuenta.status).toBe(201);

    const reporte = await request(app)
      .get('/api/reportes/saldos')
      .set('Authorization', `Bearer ${token}`);

    expect(reporte.status).toBe(200);
    expect(reporte.body.saldoTotal).toBe(500000);
  });
});
