<template>
  <v-container class="weather-container">
    <v-row class="text-center" justify="center">
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
import Graphs from "@/components/weather/Graphs.vue";
import moment from "moment/min/moment-with-locales";
import {computed, ref, watch} from "vue";
import {fetchStation, fetchWeather, getWeatherParams} from "@/utils/api";
import {useSettingsStore} from "@/stores/settings";
import {useI18n} from "vue-i18n";
import {on} from "@/events/eventBus";

const {locale} = useI18n();
moment.locale(locale.value);

const settingsStore = useSettingsStore();

const date = ref(new Date());
const dateMenu = ref(false);
const station = ref<Station | null>(null);
const weather = ref<Weather[] | null>(null);

const formattedDate = computed(() => {
  return moment(date.value).format("YYYY-MM-DD");
});

watch(date, () => {
  updateWeatherData();
  setTimeout(() => {
    dateMenu.value = false;
  }, 500);
});

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
updateWeatherData();
</script>

<style scoped>
.weather-container {
  max-width: 1785px;
}
</style>