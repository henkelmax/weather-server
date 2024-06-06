<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card>
          <v-card-title class="text-center">{{ $t("settings") }}</v-card-title>
          <v-card-text>
            <v-checkbox v-model="settingsStore.showCurrentDay" :label="$t('show_current_day')"/>
            <v-checkbox v-model="settingsStore.smoothGraphs" :label="$t('smooth_graphs')"/>
            <div class="text-xl">{{ $t("graph_history_time") }}</div>
            <v-slider
                v-model="settingsStore.graphHistoryTime"
                show-ticks="always"
                :ticks="graphHistoryTimeTickLabels"
                :step="1"
                :tick-size="1"
                :max="24"
                :min="1"
            />
            <v-select
                v-if="stations"
                class="mt-6"
                density="compact"
                v-model="selectedStation"
                :item-props="stationProps"
                :items="stations"
                :label="$t('station')"
                :hint="`${selectedStation?.name}, ${selectedStation?.description}`"
                persistent-hint
            />
          </v-card-text>
          <v-card-actions>
            <v-spacer/>
            <v-btn to="/" class="font-weight-bold mr-2" variant="plain">{{ $t("back") }}</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import {useSettingsStore} from "@/stores/settings";
import {computed, ref} from "vue";
import {fetchStations} from "@/utils/api";

const settingsStore = useSettingsStore();

const stations = ref<Station[] | null>(null);
const graphHistoryTimeTickLabels = ref({
  1: '1',
  6: '6',
  12: '12',
  18: '18',
  24: '24'
});

function stationProps(station: Station) {
  return {
    title: station.name,
    subtitle: station.description,
  };
}

const selectedStation = computed<Station | null>({
  get() {
    return stations.value?.find(station => station.deviceId === settingsStore.stationId) ?? null
  },
  set(newValue) {
    settingsStore.stationId = newValue?.deviceId ?? 1;
  }
});

fetchStations().then(s => stations.value = s);
</script>

