<template>
  <v-card height="100%" class="d-flex">
    <v-card-text class="align-self-center">
      <v-row v-if="currentWeather" align="center">
        <v-col cols="12" class="pb-0">
          <span class="text-h5 text-grey-lighten-1"> {{ $t("last_update") }} </span>
        </v-col>
        <v-col cols="12" class="pb-0">
          <span class="text-h4">
            {{ moment(currentWeather.date).fromNow() }}
          </span>
        </v-col>
        <v-col cols="12">
          <span class="text-grey-lighten-1">
            {{ moment(currentWeather.date).format("DD.MM.YYYY HH:mm") }}
          </span>
        </v-col>
      </v-row>
      <v-skeleton-loader v-else type="list-item, heading, list-item"/>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import moment from "moment/min/moment-with-locales";
import {useI18n} from "vue-i18n";

const {locale} = useI18n();
moment.locale(locale.value);

defineProps<{ currentWeather: Weather | null }>();
</script>