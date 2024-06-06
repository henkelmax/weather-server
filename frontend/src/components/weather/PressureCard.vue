<template>
  <template v-if="weather && currentWeather">
    <MinMaxCurrent
        :title="$t('pressure')"
        :value="`${currentWeather.relativePressure.toFixed(0)} hPa`"
        :min="`${min(weather, (e) => e.relativePressure).toFixed(0)} hPa`"
        :max="`${max(weather, (e) => e.relativePressure).toFixed(0)} hPa`"
    />
  </template>
  <v-card v-else height="100%">
    <v-card-text>
      <v-skeleton-loader type="list-item, heading, list-item"/>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import MinMaxCurrent from "@/components/MinMaxCurrent.vue";
import {max, min} from "@/utils/math";

defineProps<{ currentWeather: Weather | null, weather: Weather[] | null }>();
</script>