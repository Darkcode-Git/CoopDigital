import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import type { Socio } from '@coopdigital/shared';
import { fetchSocios } from '../services/api';
import type { RootState } from '../app/store';

export const SociosPage = () => {
  const token = useSelector((state: RootState) => state.session.token);
  const [socios, setSocios] = useState<Socio[]>([]);

  useEffect(() => {
    if (!token) {
      return;
    }

    fetchSocios(token)
      .then((result) => setSocios(result))
      .catch(() => setSocios([]));
  }, [token]);

  return (
    <section>
      <h2>Socios</h2>
      <ul>
        {socios.map((socio) => (
          <li key={socio.cedula}>
            {socio.nombre} ({socio.cedula})
          </li>
        ))}
      </ul>
    </section>
  );
};
