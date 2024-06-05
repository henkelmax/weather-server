<template>
  <template v-if="weather && currentWeather">
    <MinMax
        :title="$t('humidity')"
        :value="`${currentWeather.humidity.toFixed(0)} %`"
        :min="`${min(weather, (e) => e.humidity).toFixed(0)} %`"
        :max="`${max(weather, (e) => e.humidity).toFixed(0)} %`"
    />
  </template>
  <v-card v-else height="100%">
    <v-card-text>
      <v-skeleton-loader type="list-item, heading, list-item"/>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import MinMax from "@/components/MinMax.vue";
import {max, min} from "@/utils/math";

defineProps<{ currentWeather: Weather | null, weather: Weather[] | null }>();
</script>