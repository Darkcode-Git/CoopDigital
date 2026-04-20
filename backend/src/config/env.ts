const isProduction = process.env.NODE_ENV === 'production';

const ensure = (name: string, fallback?: string): string => {
  const value = process.env[name] ?? fallback;

  if (!value || (isProduction && value === fallback)) {
    throw new Error(`Missing required environment variable: ${name}`);
  }

  return value;
};

export const env = {
  JWT_SECRET: ensure('JWT_SECRET', isProduction ? undefined : 'dev-secret'),
};
