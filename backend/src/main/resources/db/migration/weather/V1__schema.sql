CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.weather
(
    "id"                  BIGSERIAL PRIMARY KEY,
    "date"                TIMESTAMP NOT NULL,
    "station_id"          BIGINT    NOT NULL,
    "temperature"         FLOAT     NOT NULL,
    "humidity"            FLOAT     NOT NULL,
    "relative_pressure"   FLOAT     NOT NULL,
    "absolute_pressure"   FLOAT     NOT NULL,
    "wind_direction"      FLOAT     NOT NULL,
    "wind_speed"          FLOAT     NOT NULL,
    "wind_gust"           FLOAT     NOT NULL,
    "max_daily_wind_gust" FLOAT     NOT NULL,
    "rain_rate"           FLOAT     NOT NULL,
    "rain_event"          FLOAT     NOT NULL,
    "rain_hourly"         FLOAT     NOT NULL,
    "rain_daily"          FLOAT     NOT NULL,
    "rain_weekly"         FLOAT     NOT NULL,
    "rain_monthly"        FLOAT     NOT NULL,
    "solar_radiation"     FLOAT     NOT NULL,
    "uvi"                 INTEGER   NOT NULL
);

CREATE TABLE public.stations
(
    "id"          BIGSERIAL PRIMARY KEY,
    "station_id"  BIGINT           NOT NULL UNIQUE,
    "name"        TEXT             NOT NULL,
    "description" TEXT             NOT NULL,
    "latitude"    DOUBLE PRECISION NOT NULL,
    "longitude"   DOUBLE PRECISION NOT NULL
);

CREATE TABLE public.passkeys
(
    "id"         BIGSERIAL PRIMARY KEY,
    "station_id" BIGINT NOT NULL UNIQUE,
    "passkey"    TEXT   NOT NULL
);
