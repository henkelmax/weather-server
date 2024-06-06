<template>
  <v-card height="100%">
    <v-card-text>
      <v-row v-if="station" align="center">
        <v-col cols="12">
          <span class="text-h5 text-grey-lighten-1"> {{ $t("daylight") }} </span>
        </v-col>
        <v-col cols="6">
          <v-row align="center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t("sunrise") }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-h4">
                {{ moment(getSunrise(station.latitude, station.longitude, date)).format("HH:mm") }}
              </span>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="6">
          <v-row align="center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t("sunset") }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-h4">
                {{ moment(getSunset(station.latitude, station.longitude, date)).format("HH:mm") }}
              </span>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
      <v-skeleton-loader v-else type="heading, list-item-three-line"/>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import moment from "moment/min/moment-with-locales";
import {getSunrise, getSunset} from "sunrise-sunset-js";

type DaylightProps = {
  station: Station | null,
  date: Date
};

withDefaults(defineProps<DaylightProps>(), {
  station: null,
  date: () => new Date()
});
</script>