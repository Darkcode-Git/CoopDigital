import { Navigate, Route, Routes } from 'react-router-dom';
import { Layout } from './components/Layout';
import { CuentasPage } from './pages/CuentasPage';
import { DashboardPage } from './pages/DashboardPage';
import { LoginPage } from './pages/LoginPage';
import { SociosPage } from './pages/SociosPage';

const App = () => (
  <Routes>
    <Route path="/login" element={<LoginPage />} />

    <Route path="/" element={<Layout />}>
      <Route index element={<Navigate to="/dashboard" replace />} />
      <Route path="dashboard" element={<DashboardPage />} />
      <Route path="socios" element={<SociosPage />} />
      <Route path="cuentas" element={<CuentasPage />} />
    </Route>
  </Routes>
);

export default App;
