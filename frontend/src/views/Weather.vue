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
              <v-date-picker v-model="date"></v-date-picker>
            </v-menu>
          </v-card-text>
        </v-card>
      </v-col>

      <template v-if="weather != null && weather.length > 0">
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
          <Graph :chartData="humidityData" :seriesColors="['#00FF00']"/>
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

<script setup lang="ts">
import moment from "moment/min/moment-with-locales";
import Graph from "../components/Graph.vue";
import MinMax from "../components/MinMax.vue";
import {getSunrise, getSunset} from "sunrise-sunset-js";
import {computed, ref, watch} from "vue";
import {useI18n} from "vue-i18n";
import {useRoute} from "vue-router";
import {on} from "../events/eventBus";

const {locale, t} = useI18n();

moment.locale(locale.value)

const route = useRoute();

const station = ref<Station | null>(null);
const currentWeather = ref<Weather | null>(null);
const weather = ref<Weather[] | null>(null);
const date = ref(moment().startOf('day').toDate());
const dateMenu = ref(false);

function fetchStation() {
  fetch(`${getServerHost()}/api/v1/station`)
      .then((response) => response.json())
      .then((data) => {
        station.value = data;
      });
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
  searchParams.append("from", String(moment(date.value, "YYYY-MM-DD").valueOf()));
  searchParams.append("to", String(moment(date.value, "YYYY-MM-DD").add(1, "days").valueOf()));
  return `${getServerHost()}/api/v1/weather?${searchParams}`;
}

function getServerHost() {
  if (import.meta.env.MODE === "development") {
    // return "http://localhost:8089";
    return "https://weather.maxhenkel.de";
  } else {
    return location.protocol + "//" + location.host;
  }
}

function isToday() {
  return (
      moment(date.value, "YYYY-MM-DD").diff(
          moment(moment().format("YYYY-MM-DD"), "YYYY-MM-DD")
      ) === 0
  );
}

function openMap(lat: number, lon: number) {
  window.open(`https://maps.google.com/?q=${lat},${lon}`, "_blank");
}

function formatDate(date: Date) {
  return moment(date).format("HH:mm:ss");
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

const temperatureData = computed(() => {
  if (weather.value === null) {
    return [];
  }
  const arr: any[] = weather.value.map((w) => {
    return [
      w.date,
      w.temperature,
      `${formatDate(w.date)}\n${w.temperature} °C`,
      +dewpoint(w.temperature, w.humidity).toFixed(2),
      `${formatDate(w.date)}\n${dewpoint(
          w.temperature,
          w.humidity
      ).toFixed(2)} °C`,
    ];
  });
  if (arr.length <= 0) {
    arr.push([new Date(), 0, "", 0, ""]);
  }
  arr.unshift([
    t("date"),
    t("temperature"),
    {type: "string", role: "tooltip"},
    t("dewpoint"),
    {type: "string", role: "tooltip"},
  ]);
  return arr;
});

const humidityData = computed(() => {
  if (weather.value === null) {
    return [];
  }
  const arr: any[] = weather.value.map((w) => {
    return [
      w.date,
      w.humidity,
      `${formatDate(w.date)}\n${w.humidity} %`,
    ];
  });
  if (arr.length <= 0) {
    arr.push([new Date(), 0, ""]);
  }
  arr.unshift([
    t("date"),
    t("humidity"),
    {type: "string", role: "tooltip"},
  ]);
  return arr;
});

const pressureData = computed((): any[][] => {
  if (weather.value === null) {
    return [];
  }
  const arr: any[] = weather.value.map((w) => {
    return [
      w.date,
      w.relativePressure,
      `${formatDate(w.date)}\n${w.relativePressure} hPa`,
      w.absolutePressure,
      `${formatDate(w.date)}\n${w.absolutePressure} hPa`,
    ];
  });
  if (arr.length <= 0) {
    arr.push([new Date(), 0, "", 0, ""]);
  }
  arr.unshift([
    t("date"),
    t("relative_pressure"),
    {type: "string", role: "tooltip"},
    t("absolute_pressure"),
    {type: "string", role: "tooltip"},
  ]);
  return arr;
});

const rainfallData = computed(() => {
  if (weather.value === null) {
    return [];
  }
  const arr: any[] = weather.value.map((w) => {
    return [
      w.date,
      w.rainRate,
      `${formatDate(w.date)}\n${w.rainRate} mm`,
    ];
  });
  if (arr.length <= 0) {
    arr.push([new Date(), 0, ""]);
  }
  arr.unshift([
    t("date"),
    t("rainfall"),
    {type: "string", role: "tooltip"},
  ]);
  return arr;
});

const windData = computed(() => {
  if (weather.value === null) {
    return [];
  }
  const arr: any[] = weather.value.map((w) => {
    return [
      w.date,
      w.windSpeed,
      `${formatDate(w.date)}\n${w.windSpeed} km/h`,
      w.windGust,
      `${formatDate(w.date)}\n${w.windGust} km/h`,
    ];
  });
  if (arr.length <= 0) {
    arr.push([new Date(), 0, "", 0, ""]);
  }
  arr.unshift([
    t("date"),
    t("wind_speed"),
    {type: "string", role: "tooltip"},
    t("wind_gust"),
    {type: "string", role: "tooltip"},
  ]);
  return arr;
});

const solarData = computed(() => {
  if (weather.value === null) {
    return [];
  }
  const arr: any[] = weather.value.map((w) => {
    return [
      w.date,
      w.solarRadiation,
      `${formatDate(w.date)}\n${w.solarRadiation} w/m²`,
    ];
  });
  if (arr.length <= 0) {
    arr.push([new Date(), 0, ""]);
  }
  arr.unshift([
    t("date"),
    t("solar_radiation"),
    {type: "string", role: "tooltip"},
  ]);
  return arr;
});

const uviData = computed(() => {
  if (weather.value === null) {
    return [];
  }
  const arr: any[] = weather.value.map((w) => {
    return [w.date, w.uvi, `${formatDate(w.date)}\n${w.uvi} UVI`];
  });
  if (arr.length <= 0) {
    arr.push([new Date(), 0, ""]);
  }
  arr.unshift([
    t("date"),
    t("uv_index"),
    {type: "string", role: "tooltip"},
  ]);
  return arr;
});

const formattedDate = computed(() => {
  return moment(date.value).format("YYYY-MM-DD");
})

on("update", updateWeatherData);

updateWeatherData();
setInterval(updateWeatherData, 30000);
</script>

<style scoped>
.weather-container {
  max-width: 1785px;
}
</style>