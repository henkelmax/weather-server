<template>
  <template v-if="weather">
    <MinMax
        :title="$t('humidity')"
        :min="`${min(weather, (e) => e.humidity).toFixed(0)} %`"
        :max="`${max(weather, (e) => e.humidity).toFixed(0)} %`"
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