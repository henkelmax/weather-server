<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card>
          <v-card-title class="text-center">{{ $t("settings") }}</v-card-title>
          <v-card-text>
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
import {ref} from "vue";

const settingsStore = useSettingsStore();

const graphHistoryTimeTickLabels = ref({
  1: '1',
  6: '6',
  12: '12',
  18: '18',
  24: '24'
});
</script>

