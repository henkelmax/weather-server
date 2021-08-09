<template>
  <v-app>
    <v-app-bar app color="gray" dark>
      <v-toolbar-title class="headline text-uppercase">
        <router-link to="/" class="no-link"><span>WEATHER</span></router-link>
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn to="/privacy" plain>Privacy</v-btn>
      <v-btn to="/legal" plain>Legal</v-btn>
      <v-btn
        href="https://github.com/henkelmax/weather-server"
        target="_blank"
        plain
      >
        <span class="mr-2">Source Code</span>
        <v-icon>mdi-open-in-new</v-icon>
      </v-btn>
    </v-app-bar>

    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",
};
</script>

<style scoped>
.no-link {
  color: white;
  text-decoration: none;
}
</style>