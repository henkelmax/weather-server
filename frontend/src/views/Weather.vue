<template>
  <v-container class="weather-container">
    <v-row class="text-center" justify="center">
      <v-col v-if="station" cols="12" lg="3" md="4" class="pa-1">
        <v-card
            height="100%"
            @click="openMap(station.latitude, station.longitude)"
        >
          <v-card-text>
            <v-row align="center">
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
          </v-card-text>
        </v-card>
      </v-col>

      <template
          v-if="
          currentWeather && isToday() && weather != null && weather.length > 0
        "
      >
        <v-col cols="12" lg="3" md="4" class="pa-1">
          <MinMax
              :title="$t('temperature')"
              :value="`${currentWeather.temperature.toFixed(1)} °C`"
              :min="`${min(weather, (e) => e.temperature).toFixed(1)} °C`"
              :max="`${max(weather, (e) => e.temperature).toFixed(1)} °C`"
          />
        </v-col>

        <v-col cols="12" lg="3" md="4" class="pa-1">
          <v-card height="100%">
            <v-card-text>
              <v-row align="center">
                <v-col cols="12">
                  <span class="text-h5 text-grey-lighten-1"> {{ $t("wind") }} </span>
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
                      <span class="text-grey-lighten-1">{{ currentWeather.windDirection }} °</span>
                    </v-col>
                  </v-row>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <v-icon small> mdi-windsock</v-icon>
                      <span class="ml-1 text-grey-lighten-1">{{ currentWeather.windSpeed.toFixed(1) }} km/h</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <v-icon small> mdi-weather-windy</v-icon>
                      <span class="ml-1 text-grey-lighten-1">{{ currentWeather.windGust.toFixed(1) }} km/h</span>
                    </v-col>
                  </v-row>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>
        </v-col>

        <v-col cols="12" lg="3" md="4" class="pa-1">
          <v-card height="100%">
            <v-card-text>
              <v-row align="center">
                <v-col cols="12">
                  <span class="text-h5 text-grey-lighten-1"> {{ $t("rain") }} </span>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <span class="text-grey-lighten-1">{{ $t("current") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4">
                        {{ currentWeather.rainRate }}
                      </span>
                      <span class="text-grey-lighten-1"> mm/h</span>
                    </v-col>
                  </v-row>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <span class="text-grey-lighten-1">{{ $t("today") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4">
                        {{ currentWeather.rainDaily }}
                      </span>
                      <span class="text-grey-lighten-1"> mm</span>
                    </v-col>
                  </v-row>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <span class="text-grey-lighten-1">{{ $t("this_week") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4">
                        {{ currentWeather.rainWeekly.toFixed(0) }}
                      </span>
                      <span class="text-grey-lighten-1"> mm</span>
                    </v-col>
                  </v-row>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <span class="text-grey-lighten-1">{{ $t("this_month") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4">
                        {{ currentWeather.rainMonthly.toFixed(0) }}
                      </span>
                      <span class="text-grey-lighten-1"> mm</span>
                    </v-col>
                  </v-row>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>
        </v-col>

        <v-col cols="12" lg="3" md="4" class="pa-1">
          <v-card height="100%">
            <v-card-text>
              <v-row align="center">
                <v-col cols="12">
                  <span class="text-h5 text-grey-lighten-1"> {{ $t("sun") }} </span>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <span class="text-grey-lighten-1">{{ $t("solar") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4">
                        {{ currentWeather.solarRadiation.toFixed(0) }}
                      </span>
                      <span class="text-grey-lighten-1"> w/m²</span>
                    </v-col>
                  </v-row>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <span class="text-grey-lighten-1">{{ $t("uv_index") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4">
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
          <v-card height="100%">
            <v-card-text>
              <v-row align="center">
                <v-col cols="12">
                  <span class="text-h5 text-grey-lighten-1"> {{ $t("daylight") }} </span>
                </v-col>
                <v-col cols="6">
                  <v-row align="center">
                    <v-col cols="12" class="pt-0">
                      <span class="text-grey-lighten-1">{{ $t("sunrise") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4">
                        {{
                          moment(
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
                      <span class="text-grey-lighten-1">{{ $t("sunset") }}</span>
                    </v-col>
                    <v-col cols="12" class="pt-0">
                      <span class="text-h4">
                        {{
                          moment(
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
                  <span class="text-h5 text-grey-lighten-1"> {{ $t("last_update") }} </span>
                </v-col>
                <v-col cols="12" class="pb-0">
                  <span class="text-h4">
                    {{ moment(currentWeather.date).fromNow() }}
                  </span>
                </v-col>
                <v-col cols="12">
                  <span class="text-grey-lighten-1">
                    {{
                      moment(currentWeather.date).format(
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

      <v-col v-if="loading" cols="12" class="pa-1">
        <v-card height="100%">
          <v-card-text>
            <v-progress-circular
                :size="70"
                :width="6"
                indeterminate
            ></v-progress-circular>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" class="pa-1">
        <v-card>
          <v-card-text>
            <v-menu v-model="dateMenu" location="bottom" :close-on-content-click="false">
              <template v-slot:activator="{ props }">
                <v-text-field
                    v-model="formattedDate"
                    label="Date"
                    prepend-icon="mdi-calendar"
                    readonly
                    v-bind="props"
                ></v-text-field>
              </template>
              <v-date-picker v-model="date" :allowed-dates="allowedDates"></v-date-picker>
            </v-menu>
          </v-card-text>
        </v-card>
      </v-col>

      <template v-if="weather != null && weather.length > 0">
        <v-col cols="12" class="pa-1">
          <Graph :data="temperatureData" :min="0"/>
        </v-col>
        <v-col cols="12" class="pa-1">
          <Graph :data="rainfallData" :min="0" :max="10"/>
        </v-col>
        <v-col cols="12" class="pa-1">
          <Graph :data="windData" :min="0" :max="10"/>
        </v-col>
        <v-col cols="12" class="pa-1">
          <Graph :data="humidityData" :min="0" :max="100"/>
        </v-col>
        <v-col cols="12" class="pa-1">
          <Graph :data="solarData" :min="0" :max="1000"/>
        </v-col>
        <v-col cols="12" class="pa-1">
          <Graph :data="pressureData" :min="950" :max="1050"/>
        </v-col>
      </template>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import moment from "moment/min/moment-with-locales";
import Graph from "@/components/Graph.vue";
import MinMax from "../components/MinMax.vue";
import {getSunrise, getSunset} from "sunrise-sunset-js";
import {computed, ref, watch} from "vue";
import {useI18n} from "vue-i18n";
import {useRoute} from "vue-router";
import {on} from "../events/eventBus";
import {useSettingsStore} from "@/stores/settings";

const settingsStore = useSettingsStore();
const {locale, t} = useI18n();

moment.locale(locale.value)

const route = useRoute();

const station = ref<Station | null>(null);
const currentWeather = ref<Weather | null>(null);
const weather = ref<Weather[] | null>(null);
const date = ref(new Date());
const dateMenu = ref(false);

function allowedDates(date: unknown): boolean {
  if (!(date instanceof Date)) {
    return true;
  }
  return moment(date).startOf("day").diff(moment().startOf("day")) <= 0;
}

function fetchStation() {
  fetch(`${getServerHost()}/api/v1/station`)
      .then((response) => response.json())
      .then((data) => {
        station.value = data;
      });
}

function onUserUpdate() {
  date.value = new Date();
  updateWeatherData();
}

function updateWeatherData() {
  if (station.value == null) {
    fetchStation();
  }
  fetch(getTodaysWeatherURL())
      .then((response) => response.json())
      .then((data) => {
        weather.value = data.map((w: any) => {
          return {...w, date: new Date(w.date)} as Weather;
        });
      });
  fetch(`${getServerHost()}/api/v1/weather/current?id=${id.value}`)
      .then((response) => response.json())
      .then((data) => {
        currentWeather.value = data;
      });
}

function getTodaysWeatherURL() {
  const searchParams: URLSearchParams = new URLSearchParams();
  searchParams.append("id", String(id.value));
  if (isToday()) {
    searchParams.append("from", String(moment().subtract(settingsStore.graphHistoryTime, "hours").valueOf()));
  } else {
    searchParams.append("from", String(moment(date.value).startOf("day").valueOf()));
  }

  searchParams.append("to", String(moment(date.value).endOf("day").valueOf()));
  return `${getServerHost()}/api/v1/weather?${searchParams}`;
}

function getServerHost() {
  if (import.meta.env.MODE === "development") {
    return "http://localhost:8088";
    // return "https://weather.maxhenkel.de";
  } else {
    return location.protocol + "//" + location.host;
  }
}

function isToday() {
  return (
      moment(date.value).startOf("day").diff(moment().startOf("day")) === 0
  );
}

function openMap(lat: number, lon: number) {
  window.open(`https://maps.google.com/?q=${lat},${lon}`, "_blank");
}

function max<T>(data: T[], func: (e: T) => number): number {
  return Math.max(...data.map((e) => func(e)));
}

function min<T>(data: T[], func: (e: T) => number): number {
  return Math.min(...data.map((e) => func(e)));
}

function dewpoint(temp: number, humidity: number) {
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
}

function round(n: number): number {
  return Math.round(n * 10) / 10
}

watch(date, () => {
  updateWeatherData();
  setTimeout(() => {
    dateMenu.value = false;
  }, 500);
})

const id = computed(() => {
  return Number.parseInt(route.query.id as string) || 1;
});

const loading = computed(() => {
  return weather.value === null;
});

const temperatureData = computed<TimeSeries[]>(() => {
  const series: TimeSeries[] = [];
  if (weather.value === null || weather.value.length <= 0) {
    return series;
  }

  const temps: TimeSeriesEntry[] = weather.value.map((w) => {
    return {
      x: w.date,
      y: round(w.temperature)
    };
  });

  series.push({
    name: t("temperature"),
    unit: "°C",
    color: "#FFFF00",
    data: temps
  });

  const dewpoints: TimeSeriesEntry[] = weather.value.map((w) => {
    return {
      x: w.date,
      y: round(dewpoint(w.temperature, w.humidity))
    };
  });

  series.push({
    name: t("dewpoint"),
    unit: "°C",
    color: "#DC3912",
    data: dewpoints
  });

  return series;
});

const humidityData = computed<TimeSeries[]>(() => {
  const series: TimeSeries[] = [];
  if (weather.value === null || weather.value.length <= 0) {
    return series;
  }

  const humidities: TimeSeriesEntry[] = weather.value.map((w) => {
    return {
      x: w.date,
      y: round(w.humidity)
    };
  });

  series.push({
    name: t("humidity"),
    unit: "%",
    color: "#00FF00",
    data: humidities
  });

  return series;
});

const rainfallData = computed<TimeSeries[]>(() => {
  const series: TimeSeries[] = [];
  if (weather.value === null || weather.value.length <= 0) {
    return series;
  }

  const rainfalls: TimeSeriesEntry[] = weather.value.map((w) => {
    return {
      x: w.date,
      y: round(w.rainRate)
    };
  });

  series.push({
    name: t("rainfall"),
    unit: "mm",
    color: "#0088FF",
    data: rainfalls
  });

  return series;
});

const windData = computed<TimeSeries[]>(() => {
  const series: TimeSeries[] = [];
  if (weather.value === null || weather.value.length <= 0) {
    return series;
  }

  const windSpeeds: TimeSeriesEntry[] = weather.value.map((w) => {
    return {
      x: w.date,
      y: round(w.windSpeed)
    };
  });

  series.push({
    name: t("wind_speed"),
    unit: "km/h",
    color: "#00FF88",
    data: windSpeeds
  });

  const windGusts: TimeSeriesEntry[] = weather.value.map((w) => {
    return {
      x: w.date,
      y: round(w.windGust)
    };
  });

  series.push({
    name: t("wind_gust"),
    unit: "km/h",
    color: "#DC3912",
    data: windGusts
  });

  return series;
});

const solarData = computed<TimeSeries[]>(() => {
  const series: TimeSeries[] = [];
  if (weather.value === null || weather.value.length <= 0) {
    return series;
  }

  const solarRadiations: TimeSeriesEntry[] = weather.value.map((w) => {
    return {
      x: w.date,
      y: round(w.solarRadiation),
      tooltipFooter: `UVI: ${w.uvi}`
    };
  });

  series.push({
    name: t("solar_radiation"),
    unit: "w/m²",
    color: "#FFFF00",
    data: solarRadiations
  });

  return series;
});

const pressureData = computed<TimeSeries[]>(() => {
  const series: TimeSeries[] = [];
  if (weather.value === null || weather.value.length <= 0) {
    return series;
  }

  const relativePressures: TimeSeriesEntry[] = weather.value.map((w) => {
    return {
      x: w.date,
      y: round(w.relativePressure)
    };
  });

  series.push({
    name: t("relative_pressure"),
    unit: "hPa",
    color: "#00FFFF",
    data: relativePressures
  });

  const absolutePressures: TimeSeriesEntry[] = weather.value.map((w) => {
    return {
      x: w.date,
      y: round(w.absolutePressure)
    };
  });

  series.push({
    name: t("absolute_pressure"),
    unit: "hPa",
    color: "#00AAFF",
    data: absolutePressures
  });

  return series;
});

const formattedDate = computed(() => {
  return moment(date.value).format("YYYY-MM-DD");
})

on("update", onUserUpdate);

updateWeatherData();
setInterval(updateWeatherData, 30000);
</script>

<style scoped>
.weather-container {
  max-width: 1785px;
}
</style>