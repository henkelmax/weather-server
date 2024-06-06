<template>
  <template v-if="weather">
    <MinMax
        :title="$t('temperature')"
        :min="`${round(min(weather, (e) => e.temperature))} °C`"
        :max="`${round(max(weather, (e) => e.temperature))} °C`"
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
import {round, min, max} from "@/utils/math";

defineProps<{ weather: Weather[] | null }>();
</script>