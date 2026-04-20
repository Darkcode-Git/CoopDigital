import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import type { AuthCredentials, AuthUser, Session } from '@coopdigital/shared';
import type { DataStore } from '../domain/models.js';
import { env } from '../config/env.js';

export const registerUser = async (store: DataStore, payload: AuthCredentials): Promise<AuthUser> => {
  if (store.usuarios.has(payload.email)) {
    throw new Error('Usuario ya registrado');
  }

  const passwordHash = await bcrypt.hash(payload.password, 10);
  const user: AuthUser = {
    id: crypto.randomUUID(),
    email: payload.email,
    passwordHash,
    role: 'SOCIO',
  };

  store.usuarios.set(payload.email, user);
  return user;
};

export const loginUser = async (store: DataStore, payload: AuthCredentials): Promise<Session> => {
  const user = store.usuarios.get(payload.email);
  if (!user) {
    throw new Error('Credenciales inválidas');
  }

  const isValid = await bcrypt.compare(payload.password, user.passwordHash);
  if (!isValid) {
    throw new Error('Credenciales inválidas');
  }

  const token = jwt.sign({ sub: user.id, role: user.role, email: user.email }, env.JWT_SECRET, {
    expiresIn: '2h',
  });

  return {
    token,
    user: {
      id: user.id,
      email: user.email,
      role: user.role,
    },
  };
};
