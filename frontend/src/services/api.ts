import type { Session, Socio } from '@coopdigital/shared';

const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:3001/api';

export const login = async (email: string, password: string): Promise<Session> => {
  const response = await fetch(`${API_URL}/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password }),
  });

  if (!response.ok) {
    throw new Error('No fue posible iniciar sesión');
  }

  return (await response.json()) as Session;
};

export const fetchSocios = async (token: string): Promise<Socio[]> => {
  const response = await fetch(`${API_URL}/socios`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error('No fue posible obtener socios');
  }

  return (await response.json()) as Socio[];
};
