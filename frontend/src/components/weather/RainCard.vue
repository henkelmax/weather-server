<template>
  <v-card height="100%">
    <v-card-text>
      <v-row v-if="currentWeather" align="center">
        <v-col cols="12">
          <span class="text-h5 text-grey-lighten-1"> {{ $t("rain") }} </span>
        </v-col>
        <v-col cols="6">
          <v-row align="center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t("current") }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-h4">
                {{ currentWeather.rainRate }}
              </span>
              <span class="text-grey-lighten-1"> mm/h</span>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="6">
          <v-row align="center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t("today") }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-h4">
                {{ currentWeather.rainDaily }}
              </span>
              <span class="text-grey-lighten-1"> mm</span>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="6">
          <v-row align="center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t("this_week") }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-h4">
                {{ currentWeather.rainWeekly.toFixed(0) }}
              </span>
              <span class="text-grey-lighten-1"> mm</span>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="6">
          <v-row align="center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t("this_month") }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-h4">
                {{ currentWeather.rainMonthly.toFixed(0) }}
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
defineProps<{ currentWeather: Weather | null }>();
</script>