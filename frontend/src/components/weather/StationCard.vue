<template>
  <v-card height="100%" @click="station ? openMap(station.latitude, station.longitude) : {}">
    <v-card-text>
      <v-row v-if="station" align="center">
        <v-col cols="12" class="pb-0">
          <span class="text-h5 text-grey-lighten-1"> {{ $t("station") }} </span>
        </v-col>
        <v-col cols="12" class="pb-0">
          <span class="text-h4">
            {{ station.name }}
          </span>
        </v-col>
        <v-col cols="12">
          <span class="text-grey-lighten-1">
            {{ station.description }}
          </span>
        </v-col>
      </v-row>
      <v-skeleton-loader v-else type="list-item, heading, list-item"/>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
defineProps<{ station: Station | null }>();

function openMap(lat: number, lon: number) {
  window.open(`https://maps.google.com/?q=${lat},${lon}`, "_blank");
}
</script>