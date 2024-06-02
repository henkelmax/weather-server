<template>
  <v-container>
    <v-row class="text-center">
      <v-col>
        <v-card>
          <v-card-title>{{ $t("settings") }}</v-card-title>
          <v-card-text>
            <v-row>
              <v-col>
                <v-checkbox v-model="settingsStore.smoothGraphs" :label="$t('smooth_graphs')"/>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import {useSettingsStore} from "@/stores/settings";

const settingsStore = useSettingsStore();
</script>

