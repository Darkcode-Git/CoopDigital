import 'dotenv/config';
import { buildApp } from './app.js';

const PORT = Number(process.env.PORT ?? 3001);

const app = buildApp();

app.listen(PORT, () => {
  console.log(`Backend running on port ${PORT}`);
});
