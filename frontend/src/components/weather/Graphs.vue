<template>
  <v-col cols="12" class="pa-1">
    <Graph :data="temperatureData" :min="0"/>
  </v-col>
  <v-col cols="12" class="pa-1">
    <Graph :data="rainfallData" :min="0" :max="10"/>
  </v-col>
  <v-col cols="12" class="pa-1">
    <Graph :data="windData" :min="0" :max="10"/>
  </v-col>
  <v-col cols="12" class="pa-1">
    <Graph :data="humidityData" :min="0" :max="100"/>
  </v-col>
  <v-col cols="12" class="pa-1">
    <Graph :data="solarData" :min="0" :max="1000"/>
  </v-col>
  <v-col cols="12" class="pa-1">
    <Graph :data="pressureData" :min="950" :max="1050"/>
  </v-col>
</template>

<script setup lang="ts">
import Graph from "@/components/Graph.vue";
import {computed} from "vue";
import {round} from "@/utils/math";
import {useI18n} from "vue-i18n";

const {t} = useI18n();

const props = defineProps<{ weather: Weather[] | null }>();

const temperatureData = computed<TimeSeries[]>(() => {
  const series: TimeSeries[] = [];
  if (props.weather === null || props.weather.length <= 0) {
    return series;
  }

  const temps: TimeSeriesEntry[] = props.weather.map((w) => {
    return {
      x: w.date,
      y: round(w.temperature)
    };
  });

  series.push({
    name: t("temperature"),
    unit: "°C",
    color: "#FFFF00",
    data: temps
  });

  const dewpoints: TimeSeriesEntry[] = props.weather.map((w) => {
    return {
      x: w.date,
      y: round(dewpoint(w.temperature, w.humidity))
    };
  });

  series.push({
    name: t("dewpoint"),
    unit: "°C",
    color: "#DC3912",
    data: dewpoints
  });

  return series;
});

const humidityData = computed<TimeSeries[]>(() => {
  const series: TimeSeries[] = [];
  if (props.weather === null || props.weather.length <= 0) {
    return series;
  }

  const humidities: TimeSeriesEntry[] = props.weather.map((w) => {
    return {
      x: w.date,
      y: round(w.humidity)
    };
  });

  series.push({
    name: t("humidity"),
    unit: "%",
    color: "#00FF00",
    data: humidities
  });

  return series;
});

const rainfallData = computed<TimeSeries[]>(() => {
  const series: TimeSeries[] = [];
  if (props.weather === null || props.weather.length <= 0) {
    return series;
  }

  const rainfalls: TimeSeriesEntry[] = props.weather.map((w) => {
    return {
      x: w.date,
      y: round(w.rainRate)
    };
  });

  series.push({
    name: t("rainfall"),
    unit: "mm",
    color: "#0088FF",
    data: rainfalls
  });

  return series;
});

const windData = computed<TimeSeries[]>(() => {
  const series: TimeSeries[] = [];
  if (props.weather === null || props.weather.length <= 0) {
    return series;
  }

  const windSpeeds: TimeSeriesEntry[] = props.weather.map((w) => {
    return {
      x: w.date,
      y: round(w.windSpeed)
    };
  });

  series.push({
    name: t("wind_speed"),
    unit: "km/h",
    color: "#00FF88",
    data: windSpeeds
  });

  const windGusts: TimeSeriesEntry[] = props.weather.map((w) => {
    return {
      x: w.date,
      y: round(w.windGust)
    };
  });

  series.push({
    name: t("wind_gust"),
    unit: "km/h",
    color: "#DC3912",
    data: windGusts
  });

  return series;
});

const solarData = computed<TimeSeries[]>(() => {
  const series: TimeSeries[] = [];
  if (props.weather === null || props.weather.length <= 0) {
    return series;
  }

  const solarRadiations: TimeSeriesEntry[] = props.weather.map((w) => {
    return {
      x: w.date,
      y: round(w.solarRadiation),
      tooltipFooter: `UVI: ${w.uvi}`
    };
  });

  series.push({
    name: t("solar_radiation"),
    unit: "w/m²",
    color: "#FFFF00",
    data: solarRadiations
  });

  return series;
});

const pressureData = computed<TimeSeries[]>(() => {
  const series: TimeSeries[] = [];
  if (props.weather === null || props.weather.length <= 0) {
    return series;
  }

  const relativePressures: TimeSeriesEntry[] = props.weather.map((w) => {
    return {
      x: w.date,
      y: round(w.relativePressure)
    };
  });

  series.push({
    name: t("relative_pressure"),
    unit: "hPa",
    color: "#00FFFF",
    data: relativePressures
  });

  const absolutePressures: TimeSeriesEntry[] = props.weather.map((w) => {
    return {
      x: w.date,
      y: round(w.absolutePressure)
    };
  });

  series.push({
    name: t("absolute_pressure"),
    unit: "hPa",
    color: "#00AAFF",
    data: absolutePressures
  });

  return series;
});

function dewpoint(temp: number, humidity: number) {
  let a, b;
  if (temp >= 0) {
    a = 7.5;
    b = 237.3;
  } else {
    a = 7.6;
    b = 240.7;
  }
  let sdd = 6.1078 * Math.pow(10, (a * temp) / (b + temp));
  let dd = sdd * (humidity / 100);
  let v = Math.log10(dd / 6.1078);
  return (b * v) / (a - v);
}
</script>