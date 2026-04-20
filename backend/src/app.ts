import cors from 'cors';
import express from 'express';
import { buildAuthRouter } from './routes/authRoutes.js';
import { buildCooperativaRouter } from './routes/cooperativaRoutes.js';
import { createDataStore, type DataStore } from './domain/models.js';

export const buildApp = (store: DataStore = createDataStore()): express.Express => {
  const app = express();

  app.use(cors());
  app.use(express.json());

  app.get('/health', (_req, res) => {
    res.json({ status: 'ok' });
  });

  app.use('/api/auth', buildAuthRouter(store));
  app.use('/api', buildCooperativaRouter(store));

  return app;
};
