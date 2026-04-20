import { Link, Outlet } from 'react-router-dom';

export const Layout = () => (
  <div style={{ padding: '1.5rem', maxWidth: 960, margin: '0 auto' }}>
    <header style={{ marginBottom: '1rem' }}>
      <h1>CoopDigital</h1>
      <nav style={{ display: 'flex', gap: '0.75rem' }}>
        <Link to="/dashboard">Dashboard</Link>
        <Link to="/socios">Socios</Link>
        <Link to="/cuentas">Cuentas</Link>
      </nav>
    </header>

    <main>
      <Outlet />
    </main>
  </div>
);
