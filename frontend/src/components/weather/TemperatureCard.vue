<template>
  <template v-if="weather && currentWeather">
    <MinMaxCurrent
        :title="$t('temperature')"
        :value="`${round(currentWeather.temperature)} °C`"
        :min="`${round(min(weather, (e) => e.temperature))} °C`"
        :max="`${round(max(weather, (e) => e.temperature))} °C`"
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
import {max, min, round} from "@/utils/math";

defineProps<{ currentWeather: Weather | null, weather: Weather[] | null }>();
</script>