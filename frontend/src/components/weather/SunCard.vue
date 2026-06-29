<template>
  <v-card height="100%" class="d-flex">
    <v-card-text class="align-self-center">
      <v-row class="align-center" v-if="currentWeather">
        <v-col cols="12">
          <span class="text-headline-medium text-grey-lighten-1"> {{ $t("sun") }} </span>
        </v-col>
        <v-col cols="6">
          <v-row class="align-center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t("solar") }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-headline-large">
                {{ currentWeather.solarRadiation.toFixed(0) }}
              </span>
              <span class="text-grey-lighten-1"> w/m²</span>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="6">
          <v-row class="align-center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t("uv_index") }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-headline-large">
                {{ currentWeather.uvi }}
              </span>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
      <v-skeleton-loader v-else type="heading, list-item-two-line"/>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
defineProps<{ currentWeather: Weather | null }>();
</script>