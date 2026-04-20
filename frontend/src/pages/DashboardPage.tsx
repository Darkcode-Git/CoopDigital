import { useSelector } from 'react-redux';
import type { RootState } from '../app/store';

export const DashboardPage = () => {
  const user = useSelector((state: RootState) => state.session.user);

  return (
    <section>
      <h2>Dashboard</h2>
      <p>Bienvenido {user?.email ?? 'invitado'}.</p>
      <p>Desde aquí podrás gestionar socios, cuentas y transacciones.</p>
    </section>
  );
};
