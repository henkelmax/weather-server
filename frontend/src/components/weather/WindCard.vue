<template>
  <v-card height="100%">
    <v-card-text>
      <v-row v-if="currentWeather" align="center">
        <v-col cols="12">
          <span class="text-h5 text-grey-lighten-1"> {{ $t("wind") }} </span>
        </v-col>
        <v-col cols="6">
          <v-row align="center">
            <v-col cols="12" class="pt-0">
              <v-icon x-large :style="`transform: rotate(${ currentWeather.windDirection + 180 }deg);`">
                mdi-navigation
              </v-icon>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ currentWeather.windDirection }} °</span>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="6">
          <v-row align="center">
            <v-col cols="12" class="pt-0">
              <v-icon small> mdi-windsock</v-icon>
              <span class="ml-1 text-grey-lighten-1">{{ currentWeather.windSpeed.toFixed(1) }} km/h</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <v-icon small> mdi-weather-windy</v-icon>
              <span class="ml-1 text-grey-lighten-1">{{ currentWeather.windGust.toFixed(1) }} km/h</span>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
      <v-skeleton-loader v-else type="heading, list-item-three-line"/>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
defineProps<{ currentWeather: Weather | null }>();
</script>