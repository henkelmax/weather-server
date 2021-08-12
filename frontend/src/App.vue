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

      <v-btn to="/settings" plain fab>
        <v-icon>mdi-cog</v-icon>
      </v-btn>
      <v-btn @click="refresh" plain fab>
        <v-icon>mdi-refresh</v-icon>
      </v-btn>
    </v-app-bar>

    <v-main>
      <router-view></router-view>
    </v-main>

    <v-footer class="pa-3">
      <v-spacer></v-spacer>
      <v-btn to="/privacy" plain small>{{ $t("privacy") }}</v-btn>
      <v-btn to="/legal" plain small class="mr-2">{{ $t("legal") }}</v-btn>
      <div class="mr-2">&copy; {{ new Date().getFullYear() }} Max Henkel</div>
    </v-footer>
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