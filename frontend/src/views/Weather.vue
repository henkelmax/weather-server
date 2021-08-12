<template>
  <v-container>
    <v-row class="text-center" justify="center">
      <v-col v-if="station" cols="12" lg="3" md="4" class="pa-1">
        <v-card
          height="100%"
          @click="openMap(station.latitude, station.longitude)"
        >
          <v-card-text>
            <v-row align="center">
              <v-col cols="12" class="pb-0">
                <span class="text-h5"> {{ $t("station") }} </span>
              </v-col>
              <v-col cols="12" class="pb-0">
                <span class="text-h4 white--text">
                  {{ station.name }}
                </span>
              </v-col>
              <v-col cols="12">
                <span>
                  {{ station.description }}
                </span>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>

      <template v-if="currentWeather && isToday() && weather.length > 0">
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <MinMax
            :title="$t('temperature')"
            :value="`${currentWeather.temperature.toFixed(1)} °C`"
            :min="`${min(weather, (e) => e.temperature).toFixed(1)} °C`"
            :max="`${max(weather, (e) => e.temperature).toFixed(1)} °C`"
          />
        </v-col>

        <v-col cols="12" lg="3" md="4" class="pa-1">
          <v-card>
            <v-card-text>
              <v-row align="center">
                <v-col cols="12">
                  <span class="text-h5"> {{ $t("wind") }} </span>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <v-icon
                        x-large
                        :style="`transform: rotate(${
                          currentWeather.windDirection + 180
                        }deg);`"
                      >
                        mdi-navigation
                      </v-icon>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span>{{ currentWeather.windDirection }} °</span>
                    </v-col>
                  </v-row>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <v-icon small> mdi-windsock </v-icon>
                      {{ currentWeather.windSpeed.toFixed(1) }} km/h
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <v-icon small> mdi-weather-windy </v-icon>
                      {{ currentWeather.windGust.toFixed(1) }} km/h
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
                  <span class="text-h5"> {{ $t("rain") }} </span>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <span>{{ $t("current") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4 white--text">
                        {{ currentWeather.rainRate }}
                      </span>
                      <span> mm/h</span>
                    </v-col>
                  </v-row>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <span>{{ $t("today") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4 white--text">
                        {{ currentWeather.rainDaily }}
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
                  <span class="text-h5"> {{ $t("sun") }} </span>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <span>{{ $t("solar") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4 white--text">
                        {{ currentWeather.solarRadiation.toFixed(0) }}
                      </span>
                      <span> w/m²</span>
                    </v-col>
                  </v-row>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <span>{{ $t("uv_index") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4 white--text">
                        {{ currentWeather.uvi }}
                      </span>
                    </v-col>
                  </v-row>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>
        </v-col>

        <v-col v-if="station" cols="12" lg="3" md="4" class="pa-1">
          <v-card>
            <v-card-text>
              <v-row align="center">
                <v-col cols="12">
                  <span class="text-h5"> {{ $t("daylight") }} </span>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <span>{{ $t("sunrise") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4 white--text">
                        {{
                          $moment(
                            getSunrise(station.latitude, station.longitude)
                          ).format("HH:mm")
                        }}
                      </span>
                    </v-col>
                  </v-row>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <span>{{ $t("sunset") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4 white--text">
                        {{
                          $moment(
                            getSunset(station.latitude, station.longitude)
                          ).format("HH:mm")
                        }}
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
            :title="$t('pressure')"
            :value="`${currentWeather.relativePressure.toFixed(0)} hPa`"
            :min="`${min(weather, (e) => e.relativePressure).toFixed(0)} hPa`"
            :max="`${max(weather, (e) => e.relativePressure).toFixed(0)} hPa`"
          />
        </v-col>

        <v-col cols="12" lg="3" md="4" class="pa-1">
          <MinMax
            :title="$t('humidity')"
            :value="`${currentWeather.humidity.toFixed(0)} %`"
            :min="`${min(weather, (e) => e.humidity).toFixed(0)} %`"
            :max="`${max(weather, (e) => e.humidity).toFixed(0)} %`"
          />
        </v-col>

        <v-col cols="12" lg="3" md="4" class="pa-1">
          <v-card height="100%">
            <v-card-text>
              <v-row align="center">
                <v-col cols="12" class="pb-0">
                  <span class="text-h5"> {{ $t("last_update") }} </span>
                </v-col>
                <v-col cols="12" class="pb-0">
                  <span class="text-h4 white--text">
                    {{ this.$moment(this.currentWeather.date).fromNow() }}
                  </span>
                </v-col>
                <v-col cols="12">
                  <span>
                    {{
                      this.$moment(this.currentWeather.date).format(
                        "DD.MM.YYYY HH:mm"
                      )
                    }}
                  </span>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>
        </v-col>
      </template>

      <v-col cols="12" class="pa-1">
        <v-card>
          <v-card-text>
            <v-menu
              v-model="calendar"
              transition="scale-transition"
              offset-y
              min-width="auto"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  v-model="date"
                  label="Date"
                  prepend-icon="mdi-calendar"
                  readonly
                  v-bind="attrs"
                  v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker
                v-model="date"
                scrollable
                no-title
                :first-day-of-week="1"
                color="blue"
              ></v-date-picker>
            </v-menu>
          </v-card-text>
        </v-card>
      </v-col>

      <template v-if="weather.length > 0">
        <v-col cols="12" class="pa-1">
          <Graph
            :chartData="temperatureData"
            :seriesColors="['#FFFF00']"
            :lowerBound="false"
          />
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
      </template>
    </v-row>
  </v-container>
</template>

<script>
import Graph from "../components/Graph";
import MinMax from "../components/MinMax";
import { getSunrise, getSunset } from "sunrise-sunset-js";

export default {
  name: "Weather",
  components: {
    Graph,
    MinMax,
  },
  data() {
    return {
      station: null,
      currentWeather: null,
      weather: [],
      date: this.$moment().format("YYYY-MM-DD"),
      calendar: false,
    };
  },
  beforeCreate() {
    this.$moment.locale(this.$i18n.locale);
  },
  created() {
    this.$eventBus.$on("update", this.updateWeatherData);
    fetch(`${this.getServerHost()}/api/v1/station`)
      .then((response) => response.json())
      .then((data) => {
        this.station = data;
      });
    this.updateWeatherData();
    setInterval(this.updateWeatherData, 30000);
  },
  methods: {
    getSunrise,
    getSunset,
    updateWeatherData() {
      fetch(this.getURL())
        .then((response) => response.json())
        .then((data) => {
          this.weather = data.map((w) => {
            return { ...w, date: new Date(w.date) };
          });
        });
      fetch(`${this.getServerHost()}/api/v1/weather/current?id=${this.id}`)
        .then((response) => response.json())
        .then((data) => {
          this.currentWeather = data;
        });
    },
    getURL() {
      const params = new URLSearchParams({
        id: this.id,
        from: this.$moment(this.date, "YYYY-MM-DD").valueOf(),
        to: this.$moment(this.date, "YYYY-MM-DD").add(1, "days").valueOf(),
      });
      return `${this.getServerHost()}/api/v1/weather?${params}`;
    },
    getServerHost() {
      if (process.env.NODE_ENV === "development") {
        // return "http://localhost:8089";
        return "https://weather.maxhenkel.de";
      } else {
        return location.protocol + "//" + location.host;
      }
    },
    isToday() {
      return (
        this.$moment(this.date, "YYYY-MM-DD").diff(
          this.$moment(this.$moment().format("YYYY-MM-DD"), "YYYY-MM-DD")
        ) === 0
      );
    },
    openMap(lat, lon) {
      window.open(`https://maps.google.com/?q=${lat},${lon}`, "_blank");
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
    dewpoint(temp, humidity) {
      let a, b;
      if (temp >= 0) {
        a = 7.5;
        b = 237.3;
      } else {
        a = 7.6;
        b = 240.7;
      }
      let sdd = 6.1078 * Math.pow(10, (a * temp) / (b + temp));
      let dd = sdd * (humidity / 100);
      let v = Math.log10(dd / 6.1078);
      return (b * v) / (a - v);
    },
  },
  watch: {
    date() {
      this.updateWeatherData();
    },
  },
  computed: {
    id() {
      return Number.parseInt(this.$route.query.id) || 1;
    },
    temperatureData() {
      const arr = this.weather.map((w) => {
        return [
          w.date,
          w.temperature,
          `${this.formatDate(w.date)}\n${w.temperature} °C`,
          +this.dewpoint(w.temperature, w.humidity).toFixed(2),
          `${this.formatDate(w.date)}\n${this.dewpoint(
            w.temperature,
            w.humidity
          ).toFixed(2)} °C`,
        ];
      });
      if (arr.length <= 0) {
        arr.push([new Date(), 0, "", 0, ""]);
      }
      arr.unshift([
        this.$t("date"),
        this.$t("temperature"),
        { type: "string", role: "tooltip" },
        this.$t("dewpoint"),
        { type: "string", role: "tooltip" },
      ]);
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
      arr.unshift([
        this.$t("date"),
        this.$t("humidity"),
        { type: "string", role: "tooltip" },
      ]);
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
        arr.push([new Date(), 0, "", 0, ""]);
      }
      arr.unshift([
        this.$t("date"),
        this.$t("relative_pressure"),
        { type: "string", role: "tooltip" },
        this.$t("absolute_pressure"),
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
      arr.unshift([
        this.$t("date"),
        this.$t("rainfall"),
        { type: "string", role: "tooltip" },
      ]);
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
        arr.push([new Date(), 0, "", 0, ""]);
      }
      arr.unshift([
        this.$t("date"),
        this.$t("wind_speed"),
        { type: "string", role: "tooltip" },
        this.$t("wind_gust"),
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
        this.$t("date"),
        this.$t("solar_radiation"),
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
      arr.unshift([
        this.$t("date"),
        this.$t("uv_index"),
        { type: "string", role: "tooltip" },
      ]);
      return arr;
    },
  },
};
</script>
