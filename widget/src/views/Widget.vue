<template>
  <div id="app" v-if="weather">
    <h1 class="text">{{ weather.temperature.toFixed(1) }} °C</h1>
    <div>
      <div class="column">
        <div class="flex-right" style="padding-right: 5vw">
          <img class="icon-h3" :src="require(`../assets/rain_icon.svg`)" />
          <h3 class="text">{{ weather.rainDaily.toFixed(0) }} mm</h3>
        </div>
      </div>
      <div class="column">
        <div class="flex-left" style="padding-left: 5vw">
          <img class="icon-h3" :src="require(`../assets/sun_icon.svg`)" />
          <h3 class="text">UVI {{ weather.uvi }}</h3>
        </div>
      </div>
    </div>
    <img class="weather-image" :src="require(`../assets/${getIcon()}.svg`)" />
    <div class="flex-center">
      <img class="icon-h4" :src="require(`../assets/update_icon.svg`)" />
      <h4 class="text">
        {{ $moment(weather.date).format("DD.MM.YYYY HH:mm") }}
      </h4>
    </div>
  </div>
</template>

<script>
export default {
  name: "Widget",
  data() {
    return {
      weather: null,
    };
  },
  created() {
    fetch(`${this.getServerHost()}/data/weather/current?id=${this.id}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to get weather data");
        }
        return response.json();
      })
      .then((data) => {
        this.weather = data;
      })
      .catch((err) => {
        console.error(err);
      });
  },
  methods: {
    getServerHost() {
      if (process.env.NODE_ENV === "development") {
        return "http://localhost:8089";
      } else {
        return location.protocol + "//" + location.host;
      }
    },
    getIcon() {
      if (this.weather.rainRate > 0 || this.weather.rainHourly > 0) {
        return "rainy";
      } else if (this.weather.uvi <= 0) {
        return "cloudy";
      } else if (this.weather.uvi >= 1 && this.weather.uvi <= 2) {
        return "sunny_cloudy";
      } else {
        return "sunny";
      }
    },
  },
  computed: {
    id() {
      return Number.parseInt(this.$route.query.id) || 1;
    },
  },
};
</script>

<style>
html {
  overflow: hidden;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  margin-top: 10px;
}
.text {
  color: #3e3e3e;
}
.weather-image {
  width: 75vw;
  height: 60vw;
  object-fit: cover;
  margin-top: -3vw;
  margin-bottom: -3vw;
}
h1 {
  margin: 0px;
  font-size: 16vw;
}
h3 {
  font-size: 10vw;
  margin: 0px;
  font-size: 4vw;
}
.icon-h3 {
  height: 5vw;
  margin-right: 1vw;
}
h4 {
  margin: 0px;
  font-size: 3vw;
}
.icon-h4 {
  height: 4vw;
  margin-right: 1vw;
}
.column {
  float: left;
  width: 50%;
}
.flex-center {
  display: flex;
  align-items: center;
  justify-content: center;
}
.flex-left {
  display: flex;
}
.flex-right {
  display: flex;
  justify-content: flex-end;
}
</style>
