FROM node:22-alpine AS deps
WORKDIR /app
COPY package*.json ./
COPY backend/package*.json ./backend/
COPY shared/package*.json ./shared/
RUN npm install

FROM deps AS build
COPY backend ./backend
COPY shared ./shared
RUN npm run build -w shared && npm run build -w @coopdigital/backend

FROM node:22-alpine AS runner
WORKDIR /app
COPY --from=build /app/backend/dist ./backend/dist
COPY --from=deps /app/node_modules ./node_modules
COPY backend/package.json ./backend/package.json
EXPOSE 3001
CMD ["node", "backend/dist/index.js"]
