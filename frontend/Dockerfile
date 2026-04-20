FROM node:22-alpine AS deps
WORKDIR /app
COPY package*.json ./
COPY frontend/package*.json ./frontend/
COPY shared/package*.json ./shared/
RUN npm install

FROM deps AS build
COPY frontend ./frontend
COPY shared ./shared
RUN npm run build -w shared && npm run build -w @coopdigital/frontend

FROM node:22-alpine AS runner
WORKDIR /app
COPY --from=build /app/frontend/dist ./frontend/dist
RUN npm install -g serve
EXPOSE 4173
CMD ["serve", "-s", "frontend/dist", "-l", "4173"]
