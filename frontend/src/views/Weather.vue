<template>
  <v-container class="weather-container">
    <v-row class="text-center" justify="center">
      <template v-if="isToday()">
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <StationCard :station="station"/>
        </v-col>
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <TemperatureCard :current-weather="currentWeather" :weather="weather"/>
        </v-col>
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <WindCard :current-weather="currentWeather"/>
        </v-col>
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <RainCard :current-weather="currentWeather"/>
        </v-col>
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <SunCard :current-weather="currentWeather"/>
        </v-col>
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <DaylightCard :station="station"/>
        </v-col>
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <PressureCard :current-weather="currentWeather" :weather="weather"/>
        </v-col>
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <HumidityCard :current-weather="currentWeather" :weather="weather"/>
        </v-col>
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <LastUpdateCard :current-weather="currentWeather"/>
        </v-col>
      </template>

      <v-col cols="12" class="pa-1">
        <v-card>
          <v-card-text>
            <v-menu v-model="dateMenu" location="bottom" :close-on-content-click="false">
              <template v-slot:activator="{ props }">
                <v-text-field
                    v-model="formattedDate"
                    label="Date"
                    prepend-icon="mdi-calendar"
                    readonly
                    v-bind="props"
                ></v-text-field>
              </template>
              <v-date-picker v-model="date" :allowed-dates="allowedDates"></v-date-picker>
            </v-menu>
          </v-card-text>
        </v-card>
      </v-col>

      <Graphs :weather="weather"/>

    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import StationCard from "@/components/weather/StationCard.vue";
import TemperatureCard from "@/components/weather/TemperatureCard.vue";
import WindCard from "@/components/weather/WindCard.vue";
import RainCard from "@/components/weather/RainCard.vue";
import SunCard from "@/components/weather/SunCard.vue";
import DaylightCard from "@/components/weather/DaylightCard.vue";
import PressureCard from "@/components/weather/PressureCard.vue";
import HumidityCard from "@/components/weather/HumidityCard.vue";
import LastUpdateCard from "@/components/weather/LastUpdateCard.vue";
import {computed, ref, watch} from "vue";
import {useI18n} from "vue-i18n";
import {useRoute} from "vue-router";
import {on} from "@/events/eventBus";
import {useSettingsStore} from "@/stores/settings";
import moment from "moment/min/moment-with-locales";
import Graphs from "@/components/weather/Graphs.vue";

const settingsStore = useSettingsStore();
const {locale} = useI18n();

moment.locale(locale.value)

const route = useRoute();

const station = ref<Station | null>(null);
const currentWeather = ref<Weather | null>(null);
const weather = ref<Weather[] | null>(null);
const date = ref(new Date());
const dateMenu = ref(false);

function allowedDates(date: unknown): boolean {
  if (!(date instanceof Date)) {
    return true;
  }
  return moment(date).startOf("day").diff(moment().startOf("day")) <= 0;
}

function fetchStation() {
  fetch(`${getServerHost()}/api/v1/station`)
      .then((response) => response.json())
      .then((data) => {
        station.value = data;
      });
}

function onUserUpdate() {
  date.value = new Date();
  updateWeatherData();
}

function updateWeatherData() {
  if (station.value == null) {
    fetchStation();
  }
  fetch(getTodaysWeatherURL())
      .then((response) => response.json())
      .then((data) => {
        weather.value = data.map((w: any) => {
          return {...w, date: new Date(w.date)} as Weather;
        });
      });
  fetch(`${getServerHost()}/api/v1/weather/current?id=${id.value}`)
      .then((response) => response.json())
      .then((data) => {
        currentWeather.value = data;
      });
}

function getTodaysWeatherURL() {
  const searchParams: URLSearchParams = new URLSearchParams();
  searchParams.append("id", String(id.value));
  if (isToday()) {
    searchParams.append("from", String(moment().subtract(settingsStore.graphHistoryTime, "hours").valueOf()));
  } else {
    searchParams.append("from", String(moment(date.value).startOf("day").valueOf()));
  }

  searchParams.append("to", String(moment(date.value).endOf("day").valueOf()));
  return `${getServerHost()}/api/v1/weather?${searchParams}`;
}

function getServerHost() {
  if (import.meta.env.MODE === "development") {
    return "http://localhost:8088";
    // return "https://weather.maxhenkel.de";
  } else {
    return location.protocol + "//" + location.host;
  }
}

function isToday() {
  return (
      moment(date.value).startOf("day").diff(moment().startOf("day")) === 0
  );
}

watch(date, () => {
  updateWeatherData();
  setTimeout(() => {
    dateMenu.value = false;
  }, 500);
})

const id = computed(() => {
  return Number.parseInt(route.query.id as string) || 1;
});

const formattedDate = computed(() => {
  return moment(date.value).format("YYYY-MM-DD");
})

on("update", onUserUpdate);

updateWeatherData();
setInterval(updateWeatherData, 30000);
</script>

<style scoped>
.weather-container {
  max-width: 1785px;
}
</style>