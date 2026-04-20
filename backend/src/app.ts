import cors from 'cors';
import express from 'express';
import rateLimit from 'express-rate-limit';
import { buildAuthRouter } from './routes/authRoutes.js';
import { buildCooperativaRouter } from './routes/cooperativaRoutes.js';
import { createDataStore, type DataStore } from './domain/models.js';

export const buildApp = (store: DataStore = createDataStore()): express.Express => {
  const app = express();

  app.use(cors());
  app.use(express.json());

  const authLimiter = rateLimit({
    windowMs: 15 * 60 * 1000,
    limit: 20,
    standardHeaders: true,
    legacyHeaders: false,
  });

  const apiLimiter = rateLimit({
    windowMs: 15 * 60 * 1000,
    limit: 120,
    standardHeaders: true,
    legacyHeaders: false,
  });

  app.get('/health', (_req, res) => {
    res.json({ status: 'ok' });
  });

  app.use('/api/auth', authLimiter, buildAuthRouter(store));
  app.use('/api', apiLimiter, buildCooperativaRouter(store));

  return app;
};
