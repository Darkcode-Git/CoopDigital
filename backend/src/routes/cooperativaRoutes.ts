import { Router } from 'express';
import type { Cuenta, DepositoPayload, RetiroPayload, Socio } from '@coopdigital/shared';
import {
  aplicarDeposito,
  aplicarRetiro,
  crearCuentaAhorros,
  obtenerReporteSaldos,
  registrarSocio,
  type DataStore,
} from '../domain/models.js';
import { requireAuth } from '../middleware/auth.js';

export const buildCooperativaRouter = (store: DataStore): Router => {
  const router = Router();

  router.use(requireAuth);

  router.get('/socios', (_req, res) => {
    res.json(Array.from(store.socios.values()));
  });

  router.post('/socios', (req, res) => {
    const payload = req.body as Socio;

    try {
      const socio = registrarSocio(store, payload);
      res.status(201).json(socio);
    } catch (error) {
      res.status(400).json({ error: (error as Error).message });
    }
  });

  router.post('/cuentas', (req, res) => {
    const payload = req.body as Cuenta;

    try {
      const cuenta = crearCuentaAhorros(store, payload);
      res.status(201).json(cuenta);
    } catch (error) {
      res.status(400).json({ error: (error as Error).message });
    }
  });

  router.post('/transacciones/deposito', (req, res) => {
    const payload = req.body as DepositoPayload;

    try {
      const cuenta = aplicarDeposito(store, payload);
      res.json(cuenta);
    } catch (error) {
      res.status(400).json({ error: (error as Error).message });
    }
  });

  router.post('/transacciones/retiro', (req, res) => {
    const payload = req.body as RetiroPayload;

    try {
      const cuenta = aplicarRetiro(store, payload);
      res.json(cuenta);
    } catch (error) {
      res.status(400).json({ error: (error as Error).message });
    }
  });

  router.get('/reportes/saldos', (_req, res) => {
    res.json(obtenerReporteSaldos(store));
  });

  return router;
};
