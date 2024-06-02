<template>
  <v-card>
    <v-card-text class="pa-2">
      <GChart
          type="LineChart"
          :settings="{ packages: ['corechart', 'timeline'] }"
          :data="chartData"
          :options="chartOptions"
      />
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import {GChart} from "vue-google-charts";
import {computed} from "vue";

import {useSettingsStore} from "@/stores/settings";

const settingsStore = useSettingsStore();

interface GraphProps {
  chartData: any[][],
  seriesColors: string[],
  maxValue?: number
  minValue?: number,
  lowerBound?: boolean
}

const props = withDefaults(defineProps<GraphProps>(), {
  minValue: 0,
  lowerBound: true
});

const chartOptions = computed(() => {
  return {
    height: 250,
    curveType: settingsStore.smoothGraphs ? "function" : undefined,
    backgroundColor: "#212121",
    series: props.seriesColors.map((e) => {
      return {color: e};
    }),
    legend: {
      position: "top",
      alignment: "end",
      textStyle: {
        color: "#FFF",
        fontSize: 16,
      },
    },
    vAxis: {
      baselineColor: "#FFF",
      maxValue: props.maxValue,
      minValue: props.minValue,
      gridlines: {
        color: "#666",
      },
      textStyle: {
        color: "#FFF",
      },
      viewWindow: {
        min: props.lowerBound ? 0 : undefined,
      },
    },
    hAxis: {
      baseline: props.chartData[1][0],
      baselineColor: "#FFF",
      format: "HH:mm",
      gridlines: {
        color: "#666",
      },
      textStyle: {
        color: "#FFF",
      },
    },
    chartArea: {
      left: 50,
      right: 25,
    },
  };
});
</script>