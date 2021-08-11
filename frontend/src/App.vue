<template>
  <v-app>
    <v-app-bar app color="gray" dark>
      <v-toolbar-title class="headline text-uppercase">
        <router-link to="/" class="no-link">
          <span>{{ $t("weather") }}</span>
        </router-link>
      </v-toolbar-title>

      <v-spacer></v-spacer>
      <v-btn v-if="!!installPrompt" @click="installPWA" plain>
        <v-icon>mdi-download</v-icon>
        <template v-if="!$vuetify.breakpoint.smAndDown">
          {{ $t("install") }}
        </template>
      </v-btn>

      <v-btn v-if="!installed" to="/privacy" plain>
        <v-icon v-if="$vuetify.breakpoint.smAndDown">mdi-cookie-lock</v-icon>
        <template v-else>{{ $t("privacy") }}</template>
      </v-btn>
      <v-btn v-if="!installed" to="/legal" plain>
        <v-icon v-if="$vuetify.breakpoint.smAndDown">mdi-account-box</v-icon>
        <template v-else>{{ $t("legal") }}</template>
      </v-btn>
      <v-btn v-if="installed" @click="refresh" plain>
        <v-icon>mdi-refresh</v-icon>
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
    refresh() {
      this.forceSWupdate();
      this.$eventBus.$emit("update");
    },
    forceSWupdate() {
      if ("serviceWorker" in navigator) {
        navigator.serviceWorker.getRegistrations().then((registrations) => {
          for (let registration of registrations) {
            registration.update();
          }
        });
      }
    },
  },
  computed: {
    installed() {
      return window.matchMedia("(display-mode: standalone)").matches;
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