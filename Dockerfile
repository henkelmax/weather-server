FROM node:12-alpine AS frontend-builder

COPY frontend/package.json .
COPY frontend/yarn.lock .

RUN yarn install --non-interactive

COPY frontend .

RUN yarn build


FROM node:12-alpine AS widget-builder

COPY widget/package.json .
COPY widget/yarn.lock .

RUN yarn install --non-interactive

COPY widget .

RUN yarn build


FROM node:12-alpine

WORKDIR /weather

COPY package.json .
COPY yarn.lock .

RUN yarn install --production --silent

COPY *.js .
COPY --from=frontend-builder dist frontend/dist
COPY --from=widget-builder dist widget/dist

ENV DB_IP=localhost
ENV DB_PORT=27017
ENV DB_NAME=weather
ENV PORT=80

ENTRYPOINT []

CMD ["node","index.js"]