<template>
  <v-card>
    <v-card-text class="pa-2">
      <GChart
        type="LineChart"
        :settings="{ packages: ['corechart', 'line'] }"
        :data="chartData"
        :options="chartOptions"
      />
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: "Graph",
  props: {
    chartData: {
      type: Array,
    },
    seriesColors: {
      type: Array,
    },
    maxValue: {
      type: Number,
    },
    minValue: {
      type: Number,
      default: 0,
    },
  },
  computed: {
    chartOptions() {
      return {
        height: 250,
        curveType: "function",
        backgroundColor: "#1E1E1E",
        series: this.seriesColors.map((e) => {
          return { color: e };
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
          maxValue: this.maxValue,
          minValue: this.minValue,
          gridlines: {
            color: "#666",
          },
          textStyle: {
            color: "#FFF",
          },
        },
        hAxis: {
          baseline: this.chartData[1][0],
          baselineColor: "#FFF",
          format: "HH:mm",
          gridlines: {
            color: "#666",
          },
          textStyle: {
            color: "#FFF",
          },
        },
        pointSize: 5,
        chartArea: {
          left: 50,
          right: 25,
        },
      };
    },
  },
};
</script>
