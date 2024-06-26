<template>
  <v-card>
    <v-card-text class="pa-2" style="min-height: 250px">
      <Line :data="data" :options="options"/>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import {
  Chart,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip,
  Legend,
  TimeSeriesScale
} from "chart.js";
import {Line} from "vue-chartjs";
import "chartjs-adapter-moment";
import {useSettingsStore} from "@/stores/settings";
import {computed} from "vue";

const settingsStore = useSettingsStore();

Chart.register(
    LinearScale,
    PointElement,
    LineElement,
    Tooltip,
    Legend,
    TimeSeriesScale
);

type GraphProps = {
  min?: number,
  max?: number,
  yStepSize?: number,
  data: TimeSeries[],
  tooltipFooter?: (tooltipItems: any) => string
};

const props = defineProps<GraphProps>();

function minYValue(): number | undefined {
  if (props.min === undefined) {
    return;
  }
  if(props.data.length <= 0) {
    return;
  }
  return Math.min(props.data.map(d => d.data.map(e => e.y)).flat().reduce((a, b) => Math.min(a, b)), props.min);
}

function maxYValue(): number | undefined {
  if (props.max === undefined) {
    return;
  }
  if(props.data.length <= 0) {
    return;
  }
  return Math.max(props.data.map(d => d.data.map(e => e.y)).flat().reduce((a, b) => Math.max(a, b)), props.max);
}

const data = computed<any>(() => {
  return {
    datasets: props.data.map((ts: TimeSeries) => {
      return {
        label: ts.name,
        color: ts.color,
        borderColor: ts.color,
        hoverBackgroundColor: ts.color,
        tension: settingsStore.smoothGraphs ? 0.25 : 0,
        pointHitRadius: 10,
        tooltip: {
          callbacks: {
            label: (context: any) => {
              if (ts.unit) {
                return `${ts.name}: ${context.parsed.y} ${ts.unit}`
              } else {
                return `${ts.name}: ${context.parsed.y}`
              }
            }
          }
        },
        data: ts.data
      }
    })
  }
});

const options = computed<any>(() => {
  return {
    responsive: true,
    maintainAspectRatio: false,
    elements: {
      point: {
        radius: 0,
        hoverRadius: 6
      }
    },
    plugins: {
      legend: {
        align: "end",
        labels: {
          boxHeight: 1,
          color: "#FFF",
        }
      },
      tooltip: {
        mode: 'index',
        position: 'average',
        displayColors: false,
        backgroundColor: "#333",
        callbacks: {
          afterBody: (tooltipItems: any[]) => {
            if (tooltipItems?.length <= 0 || props.data?.length <= 0) {
              return "";
            }
            const dataLength = props.data[0].data.length;
            const dataIndex = dataLength - 1 - tooltipItems[0].dataIndex;
            let tooltipText = "";
            for (let x of props.data) {
              let footer = x.data[dataIndex].tooltipFooter;
              if (footer) {
                if (tooltipText !== "") {
                  tooltipText += "\n";
                }
                tooltipText += footer;
              }
            }
            return tooltipText;
          },
        }
      }
    },
    scales: {
      x: {
        type: "time",
        time: {
          tooltipFormat: "HH:mm",
          displayFormats: {
            millisecond: "HH:mm",
            second: "HH:mm",
            minute: "HH:mm",
            hour: "HH:mm",
            day: "HH:mm",
            week: "HH:mm",
            month: "HH:mm",
            quarter: "HH:mm",
            year: "HH:mm",
          }
        },
        grid: {
          color: "#666"
        },
        ticks: {
          color: "#FFF",
          autoStep: true
        },
        border: {
          display: true,
          color: "#AAA",
          width: 2
        },
      },
      y: {
        min: minYValue(),
        max: maxYValue(),
        grid: {
          color: "#666",
        },
        ticks: {
          color: "#FFF",
          stepSize: props.yStepSize ?? undefined,
          maxTicksLimit: 16
        },
        border: {
          display: true,
          color: "#AAA",
          width: 2
        },
      }
    }
  }
});
</script>
