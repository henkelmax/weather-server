<template>
  <v-app class="app">
    <v-app-bar>
      <v-app-bar-title class="text-uppercase">
        <RouterLink to="/" class="no-link">{{ $t("weather") }}</RouterLink>
      </v-app-bar-title>
      <v-spacer></v-spacer>
      <template v-if="!$vuetify.display.smAndDown">
        <v-btn v-if="!!installPrompt" variant="plain" @click="installPWA">
          <v-icon icon="mdi-download"/>
          <span class="ml-1">
          {{ $t("install") }}
        </span>
        </v-btn>
      </template>
      <template v-else>
        <v-btn icon="mdi-download" variant="plain" @click="installPWA">
          <v-icon icon="mdi-download"/>
        </v-btn>
      </template>

      <v-btn icon="mdi-cog" variant="plain" to="/settings"/>
      <v-btn icon="mdi-refresh" variant="plain" @click="refresh"/>
    </v-app-bar>
    <v-main class="main">
      <RouterView/>
    </v-main>
    <v-footer class="pa-3">
      <v-spacer></v-spacer>
      <v-btn to="/privacy" class="font-weight-bold" variant="plain">{{ $t("privacy") }}</v-btn>
      <v-btn to="/legal" class="font-weight-bold mr-2" variant="plain">{{ $t("legal") }}</v-btn>
      <div class="mr-2">&copy; {{ new Date().getFullYear() }} Max Henkel</div>
    </v-footer>
  </v-app>
</template>

<script setup lang="ts">
import {RouterLink, RouterView} from "vue-router";
import {ref} from "vue";
import {emit} from "./events/eventBus";

const installPrompt = ref<BeforeInstallPromptEvent | null>(null);

window.addEventListener("beforeinstallprompt", (e) => {
  e.preventDefault();
  installPrompt.value = e as BeforeInstallPromptEvent;
});

function installPWA() {
  if (!installPrompt.value) {
    return;
  }
  installPrompt.value.prompt();
  installPrompt.value.userChoice.then((choiceResult) => {
    if (choiceResult.outcome === "accepted") {
      installPrompt.value = null;
    }
  });
}

function refresh() {
  forceSwUpdate();
  emit("update");
}

function forceSwUpdate() {
  if ("serviceWorker" in navigator) {
    navigator.serviceWorker.getRegistrations().then((registrations) => {
      for (let registration of registrations) {
        registration.update();
      }
    });
  }
}
</script>

<style scoped>
.no-link {
  color: white;
  text-decoration: none;
}

.app {
  height: 100%;
}

.main {
  min-height: calc(100vh - 60px);
}
</style>