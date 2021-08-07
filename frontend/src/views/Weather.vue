<template>
  <v-container v-if="current">
    <v-row class="text-center" justify="center">
      <v-col cols="12" lg="3" md="4" class="pa-1">
        <MinMax
          title="Temperature"
          :value="`${current.temperature.toFixed(1)} °C`"
          :min="`${min(weather, (e) => e.temperature).toFixed(1)} °C`"
          :max="`${max(weather, (e) => e.temperature).toFixed(1)} °C`"
        />
      </v-col>

      <v-col cols="12" lg="3" md="4" class="pa-1">
        <v-card>
          <v-card-text>
            <v-row align="center">
              <v-col cols="12">
                <span class="text-h5"> Wind </span>
              </v-col>
              <v-col cols="6">
                <v-row align="center">
                  <v-col cols="12" class="pt-0">
                    <v-icon
                      x-large
                      :style="`transform: rotate(${current.windDirection}deg);`"
                    >
                      mdi-navigation
                    </v-icon>
                  </v-col>
                  <v-col cols="12" class="pt-0">
                    <span>{{ current.windDirection }} °</span>
                  </v-col>
                </v-row>
              </v-col>
              <v-col cols="6">
                <v-row align="center">
                  <v-col cols="12" class="pt-0">
                    <v-icon small> mdi-windsock </v-icon>
                    {{ current.windSpeed.toFixed(1) }} km/h
                  </v-col>
                  <v-col cols="12" class="pt-0">
                    <v-icon small> mdi-weather-windy </v-icon>
                    {{ current.windGust.toFixed(1) }} km/h
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" lg="3" md="4" class="pa-1">
        <v-card>
          <v-card-text>
            <v-row align="center">
              <v-col cols="12">
                <span class="text-h5"> Rain </span>
              </v-col>
              <v-col cols="6">
                <v-row align="center">
                  <v-col cols="12" class="pt-0">
                    <span>Current</span>
                  </v-col>
                  <v-col cols="12" class="pt-0">
                    <span class="text-h4 white--text">
                      {{ current.rainRate }}
                    </span>
                    <span> mm/h</span>
                  </v-col>
                </v-row>
              </v-col>
              <v-col cols="6">
                <v-row align="center">
                  <v-col cols="12" class="pt-0">
                    <span>Today</span>
                  </v-col>
                  <v-col cols="12" class="pt-0">
                    <span class="text-h4 white--text">
                      {{ current.rainDaily }}
                    </span>
                    <span> mm</span>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" lg="3" md="4" class="pa-1">
        <v-card>
          <v-card-text>
            <v-row align="center">
              <v-col cols="12">
                <span class="text-h5"> Sun </span>
              </v-col>
              <v-col cols="6">
                <v-row align="center">
                  <v-col cols="12" class="pt-0">
                    <span>Solar</span>
                  </v-col>
                  <v-col cols="12" class="pt-0">
                    <span class="text-h4 white--text">
                      {{ current.solarRadiation.toFixed(0) }}
                    </span>
                    <span> w/m²</span>
                  </v-col>
                </v-row>
              </v-col>
              <v-col cols="6">
                <v-row align="center">
                  <v-col cols="12" class="pt-0">
                    <span>UV Index</span>
                  </v-col>
                  <v-col cols="12" class="pt-0">
                    <span class="text-h4 white--text">
                      {{ current.uvi }}
                    </span>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" lg="3" md="4" class="pa-1">
        <MinMax
          title="Pressure"
          :value="`${current.relativePressure.toFixed(0)} hPa`"
          :min="`${min(weather, (e) => e.relativePressure).toFixed(0)} hPa`"
          :max="`${max(weather, (e) => e.relativePressure).toFixed(0)} hPa`"
        />
      </v-col>

      <v-col cols="12" lg="3" md="4" class="pa-1">
        <MinMax
          title="Humidity"
          :value="`${current.humidity.toFixed(0)} %`"
          :min="`${min(weather, (e) => e.humidity).toFixed(0)} %`"
          :max="`${max(weather, (e) => e.humidity).toFixed(0)} %`"
        />
      </v-col>

      <v-col cols="12" class="pa-1">
        <Graph :chartData="temperatureData" :seriesColors="['#FFFF00']" />
      </v-col>
      <v-col cols="12" class="pa-1">
        <Graph
          :chartData="rainfallData"
          :seriesColors="['#0088FF']"
          :maxValue="10"
        />
      </v-col>
      <v-col cols="12" class="pa-1">
        <Graph
          :chartData="windData"
          :seriesColors="['#00FF88']"
          :maxValue="10"
        />
      </v-col>
      <v-col cols="12" class="pa-1">
        <Graph :chartData="humidityData" :seriesColors="['#00FF00']" />
      </v-col>
      <v-col cols="12" class="pa-1">
        <Graph
          :chartData="solarData"
          :seriesColors="['#FFFF00']"
          :maxValue="10"
        />
      </v-col>
      <v-col cols="12" class="pa-1">
        <Graph
          :chartData="uviData"
          :seriesColors="['#FFFF00']"
          :maxValue="10"
        />
      </v-col>
      <v-col cols="12" class="pa-1">
        <Graph
          :chartData="pressureData"
          :seriesColors="['#00FFFF', '#00AAFF']"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Graph from "../components/Graph";
import MinMax from "../components/MinMax";

export default {
  name: "Weather",
  components: {
    Graph,
    MinMax,
  },
  data: () => ({
    weather: [],
  }),
  created() {
    this.updateWeatherData();
    setTimeout(this.updateWeatherData, 30000);
  },
  methods: {
    updateWeatherData() {
      fetch(
        `${this.getServerHost()}data/weather?id=${this.$route.query.id || 1}`
      )
        .then((response) => response.json())
        .then((data) => {
          this.weather = data.map((w) => {
            return { ...w, date: new Date(w.date) };
          });
        });
    },
    getServerHost() {
      if (process.env.NODE_ENV === "development") {
        return "http://localhost:8088/";
      } else {
        return "";
      }
    },
    formatDate(date) {
      return this.$moment(date).format("HH:mm:ss");
    },
    max(data, func) {
      return Math.max(...data.map((e) => func(e)));
    },
    min(data, func) {
      return Math.min(...data.map((e) => func(e)));
    },
  },
  computed: {
    current() {
      if (this.weather.length <= 0) {
        return null;
      }
      return this.weather[this.weather.length - 1];
    },
    temperatureData() {
      const arr = this.weather.map((w) => {
        return [
          w.date,
          w.temperature,
          `${this.formatDate(w.date)}\n${w.temperature} °C`,
        ];
      });
      if (arr.length <= 0) {
        arr.push([new Date(), 0, ""]);
      }
      arr.unshift(["Date", "Temperature", { type: "string", role: "tooltip" }]);
      return arr;
    },
    humidityData() {
      const arr = this.weather.map((w) => {
        return [
          w.date,
          w.humidity,
          `${this.formatDate(w.date)}\n${w.humidity} %`,
        ];
      });
      if (arr.length <= 0) {
        arr.push([new Date(), 0, ""]);
      }
      arr.unshift(["Date", "Humidity", { type: "string", role: "tooltip" }]);
      return arr;
    },
    pressureData() {
      const arr = this.weather.map((w) => {
        return [
          w.date,
          w.relativePressure,
          `${this.formatDate(w.date)}\n${w.relativePressure} hPa`,
          w.absolutePressure,
          `${this.formatDate(w.date)}\n${w.absolutePressure} hPa`,
        ];
      });
      if (arr.length <= 0) {
        arr.push([new Date(), 0, ""]);
      }
      arr.unshift([
        "Date",
        "Relative Pressure",
        { type: "string", role: "tooltip" },
        "Absolute Pressure",
        { type: "string", role: "tooltip" },
      ]);
      return arr;
    },
    rainfallData() {
      const arr = this.weather.map((w) => {
        return [
          w.date,
          w.rainRate,
          `${this.formatDate(w.date)}\n${w.rainRate} mm`,
        ];
      });
      if (arr.length <= 0) {
        arr.push([new Date(), 0, ""]);
      }
      arr.unshift(["Date", "Rainfall", { type: "string", role: "tooltip" }]);
      return arr;
    },
    windData() {
      const arr = this.weather.map((w) => {
        return [
          w.date,
          w.windSpeed,
          `${this.formatDate(w.date)}\n${w.windSpeed} km/h`,
          w.windGust,
          `${this.formatDate(w.date)}\n${w.windGust} km/h`,
        ];
      });
      if (arr.length <= 0) {
        arr.push([new Date(), 0, ""]);
      }
      arr.unshift([
        "Date",
        "Wind Speed",
        { type: "string", role: "tooltip" },
        "Wind Gust",
        { type: "string", role: "tooltip" },
      ]);
      return arr;
    },
    solarData() {
      const arr = this.weather.map((w) => {
        return [
          w.date,
          w.solarRadiation,
          `${this.formatDate(w.date)}\n${w.solarRadiation} w/m²`,
        ];
      });
      if (arr.length <= 0) {
        arr.push([new Date(), 0, ""]);
      }
      arr.unshift([
        "Date",
        "Solar Radiation",
        { type: "string", role: "tooltip" },
      ]);
      return arr;
    },
    uviData() {
      const arr = this.weather.map((w) => {
        return [w.date, w.uvi, `${this.formatDate(w.date)}\n${w.uvi} UVI`];
      });
      if (arr.length <= 0) {
        arr.push([new Date(), 0, ""]);
      }
      arr.unshift(["Date", "UV Index", { type: "string", role: "tooltip" }]);
      return arr;
    },
  },
};
</script>
