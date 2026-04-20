import { Router } from 'express';
import type { AuthCredentials } from '@coopdigital/shared';
import { loginUser, registerUser } from '../services/authService.js';
import type { DataStore } from '../domain/models.js';

export const buildAuthRouter = (store: DataStore): Router => {
  const router = Router();

  router.post('/register', async (req, res) => {
    const payload = req.body as AuthCredentials;

    try {
      const user = await registerUser(store, payload);
      res.status(201).json({ id: user.id, email: user.email, role: user.role });
    } catch (error) {
      res.status(400).json({ error: (error as Error).message });
    }
  });

  router.post('/login', async (req, res) => {
    const payload = req.body as AuthCredentials;

    try {
      const session = await loginUser(store, payload);
      res.json(session);
    } catch (error) {
      res.status(401).json({ error: (error as Error).message });
    }
  });

  return router;
};
