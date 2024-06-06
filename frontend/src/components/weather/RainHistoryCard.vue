<template>
  <v-card height="100%">
    <v-card-text>
      <v-row v-if="lastWeather" align="center">
        <v-col cols="12">
          <span class="text-h5 text-grey-lighten-1"> {{ $t("rain") }} </span>
        </v-col>
        <v-col cols="12">
          <v-row align="center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t("day") }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-h4">
                {{ lastWeather.rainDaily }}
              </span>
              <span class="text-grey-lighten-1"> mm</span>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="6">
          <v-row align="center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t("week") }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-h4">
                {{ lastWeather.rainWeekly.toFixed(0) }}
              </span>
              <span class="text-grey-lighten-1"> mm</span>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="6">
          <v-row align="center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t("month") }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-h4">
                {{ lastWeather.rainMonthly.toFixed(0) }}
              </span>
              <span class="text-grey-lighten-1"> mm</span>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
      <v-skeleton-loader v-else type="heading, list-item-three-line"/>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import {computed} from "vue";

const props = defineProps<{ weather: Weather[] | null }>();

const lastWeather = computed<Weather | null>(() => {
  return props.weather?.slice(-1)[0] ?? null;
});
</script>