<template>
  <v-app>
    <v-app-bar app color="gray" dark>
      <v-toolbar-title class="headline text-uppercase">
        <router-link to="/" class="no-link"><span>WEATHER</span></router-link>
      </v-toolbar-title>

      <v-spacer></v-spacer>
      <v-btn v-if="!!installPrompt" @click="installPWA" plain>
        <v-icon>mdi-download</v-icon>
        Install
      </v-btn>
      <v-btn to="/privacy" plain>Privacy</v-btn>
      <v-btn to="/legal" plain>Legal</v-btn>
    </v-app-bar>

    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      installPrompt: null,
    };
  },
  beforeCreate() {
    window.addEventListener("beforeinstallprompt", (e) => {
      e.preventDefault();
      this.installPrompt = e;
    });
  },
  methods: {
    installPWA() {
      if (!this.installPrompt) {
        return;
      }
      this.installPrompt.prompt();
      this.installPrompt.userChoice.then((choiceResult) => {
        if (choiceResult.outcome === "accepted") {
          this.installPrompt = null;
        }
      });
    },
  },
};
</script>

<style scoped>
.no-link {
  color: white;
  text-decoration: none;
}
</style>