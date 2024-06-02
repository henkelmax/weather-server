import "./assets/main.css";
import "./types/pwa";

import {createApp} from "vue";
import {createPinia} from "pinia";

import {createI18n} from "vue-i18n";
import en from "./locales/en.json";
import de from "./locales/de.json";

import "vuetify/styles";
import {createVuetify} from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "@mdi/font/css/materialdesignicons.css";

import App from "./App.vue";
import router from "./router";

const i18n = createI18n({
    legacy: false,
    globalInjection: true,
    locale: navigator.language ? navigator.language.split("-")[0] : "de",
    fallbackLocale: "de",
    messages: {
        en,
        de
    }
});

const vuetify = createVuetify({
    components,
    directives,
    theme: {
        defaultTheme: "dark"
    }
});

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(i18n);
app.use(vuetify);

app.mount("#app");

