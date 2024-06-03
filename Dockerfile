FROM node:20-alpine AS frontend-builder

COPY frontend/package.json .
COPY frontend/package-lock.json .

RUN npm ci

COPY frontend .

RUN npm run build

FROM openjdk:17-jdk-alpine

COPY --from=frontend-builder dist /var/www/
COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew backend:build

ENTRYPOINT ["java","-jar","backend/build/libs/app.jar"]
