<template>
  <v-container class="weather-container">
    <v-row class="text-center" justify="center">
      <v-col cols="12" lg="3" md="4" class="pa-1">
        <StationCard :station="station"/>
      </v-col>
    </v-row>
    <v-row class="text-center" justify="center">
      <v-col cols="12" lg="3" md="4" class="pa-1">
        <v-card>
          <v-card-text class="d-flex justify-center">
            <v-date-picker v-model="date" :allowed-dates="allowedDates" hide-header></v-date-picker>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
    <v-empty-state
        v-if="weather && weather.length <= 0"
        icon="mdi-weather-cloudy-alert"
        :title="$t('no_data_title')"
        :text="$t('no_data_description')"
    ></v-empty-state>
    <template v-else>
      <v-row class="text-center" justify="center">
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <TemperatureHistoryCard :weather="weather"/>
        </v-col>
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <PressureHistoryCard :weather="weather"/>
        </v-col>
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <HumidityHistoryCard :weather="weather"/>
        </v-col>
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <RainHistoryCard :weather="weather"/>
        </v-col>
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <DaylightCard :station="station" :date="date"/>
        </v-col>
      </v-row>
      <v-row class="text-center" justify="center">
        <Graphs :weather="weather"/>
      </v-row>
    </template>
  </v-container>
</template>

<script setup lang="ts">
import Graphs from "@/components/weather/Graphs.vue";
import StationCard from "@/components/weather/StationCard.vue";
import TemperatureHistoryCard from "@/components/weather/TemperatureHistoryCard.vue";
import PressureHistoryCard from "@/components/weather/PressureHistoryCard.vue";
import HumidityHistoryCard from "@/components/weather/HumidityHistoryCard.vue";
import moment from "moment/min/moment-with-locales";
import {ref, watch} from "vue";
import {fetchStation, fetchWeather, getWeatherParams} from "@/utils/api";
import {useSettingsStore} from "@/stores/settings";
import {useI18n} from "vue-i18n";
import {on} from "@/events/eventBus";
import RainHistoryCard from "@/components/weather/RainHistoryCard.vue";
import DaylightCard from "@/components/weather/DaylightCard.vue";

const {locale} = useI18n();
moment.locale(locale.value);

const settingsStore = useSettingsStore();

const date = ref(moment().subtract(1, "day").toDate());
const station = ref<Station | null>(null);
const weather = ref<Weather[] | null>(null);

function updateWeatherData() {
  if (station.value == null) {
    fetchStation().then(s => station.value = s);
  }
  fetchWeather(getWeatherParams(settingsStore.stationId, date.value)).then(w => weather.value = w);
}

function allowedDates(date: unknown): boolean {
  if (!(date instanceof Date)) {
    return true;
  }
  return moment(date).startOf("day").diff(moment().startOf("day")) <= 0;
}

on("update", updateWeatherData);
watch(date, updateWeatherData);
updateWeatherData();
</script>

<style scoped>
.weather-container {
  max-width: 1785px;
}
</style>