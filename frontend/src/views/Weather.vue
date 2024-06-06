<template>
  <v-container class="weather-container">
    <v-row class="text-center" justify="center">
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
import {ref} from "vue";
import {on} from "@/events/eventBus";
import {useSettingsStore} from "@/stores/settings";
import Graphs from "@/components/weather/Graphs.vue";
import {fetchCurrentWeather, fetchStation, fetchWeather, getTodaysWeatherParams} from "@/utils/api";

const settingsStore = useSettingsStore();

const station = ref<Station | null>(null);
const currentWeather = ref<Weather | null>(null);
const weather = ref<Weather[] | null>(null);

function updateWeatherData() {
  if (station.value == null) {
    fetchStation().then(s => station.value = s);
  }
  fetchCurrentWeather(settingsStore.stationId).then(w => currentWeather.value = w);
  fetchWeather(getTodaysWeatherParams(settingsStore.stationId, settingsStore.graphHistoryTime)).then(w => weather.value = w);
}

on("update", updateWeatherData);

updateWeatherData();
setInterval(updateWeatherData, 30000);
</script>

<style scoped>
.weather-container {
  max-width: 1785px;
}
</style>