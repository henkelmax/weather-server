<template>
  <template v-if="weather">
    <MinMax
        :title="$t('pressure')"
        :min="`${min(weather, (e) => e.relativePressure).toFixed(0)} hPa`"
        :max="`${max(weather, (e) => e.relativePressure).toFixed(0)} hPa`"
    />
  </template>
  <v-card v-else height="100%">
    <v-card-text>
      <v-skeleton-loader type="heading, list-item"/>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import MinMax from "@/components/MinMax.vue";
import {min, max} from "@/utils/math";

defineProps<{ weather: Weather[] | null }>();
</script>