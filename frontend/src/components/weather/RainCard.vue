<template>
  <v-card height="100%" class="d-flex">
    <v-card-text class="align-self-center">
      <v-row class="align-center ga-2" v-if="currentWeather">
        <v-col cols="12">
          <span class="text-headline-medium text-grey-lighten-1"> {{ $t('rain') }} </span>
        </v-col>
        <v-col cols="6">
          <v-row class="align-center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t('current') }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-headline-large">
                {{ currentWeather.rainRate }}
              </span>
              <span class="text-grey-lighten-1"> mm/h</span>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="6">
          <v-row class="align-center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t('today') }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-headline-large">
                {{ currentWeather.rainDaily }}
              </span>
              <span class="text-grey-lighten-1"> mm</span>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="6">
          <v-row class="align-center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t('this_week') }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-headline-large">
                {{ currentWeather.rainWeekly.toFixed(0) }}
              </span>
              <span class="text-grey-lighten-1"> mm</span>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="6">
          <v-row class="align-center">
            <v-col cols="12" class="pt-0">
              <span class="text-grey-lighten-1">{{ $t('this_month') }}</span>
            </v-col>
            <v-col cols="12" class="pt-0">
              <span class="text-headline-large">
                {{ currentWeather.rainMonthly.toFixed(0) }}
              </span>
              <span class="text-grey-lighten-1"> mm</span>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
      <v-skeleton-loader v-else type="heading, list-item-three-line" />
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
defineProps<{currentWeather: Weather | null}>();
</script>
