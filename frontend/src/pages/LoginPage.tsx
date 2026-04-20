import { useState, type FormEvent } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setSession } from '../features/session/sessionSlice';
import { login } from '../services/api';
import type { AppDispatch } from '../app/store';

export const LoginPage = () => {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const [email, setEmail] = useState('ana@coopdigital.test');
  const [password, setPassword] = useState('strong-pass');
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();

    try {
      const session = await login(email, password);
      dispatch(setSession({ token: session.token, user: session.user }));
      navigate('/dashboard');
    } catch {
      setError('Credenciales inválidas');
    }
  };

  return (
    <section>
      <h2>Acceso de socios</h2>
      <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '0.5rem', maxWidth: 320 }}>
        <label htmlFor="email">Correo</label>
        <input id="email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} />

        <label htmlFor="password">Contraseña</label>
        <input
          id="password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        {error && <p style={{ color: '#b91c1c' }}>{error}</p>}
        <button type="submit">Ingresar</button>
      </form>
    </section>
  );
};
