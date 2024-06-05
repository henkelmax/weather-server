import "./assets/main.css";

import {createApp} from "vue";
import {createPinia} from "pinia";

import {createI18n} from "vue-i18n";
import enLang from "./locales/en.json";
import deLang from "./locales/de.json";

import "vuetify/styles";
import {createVuetify} from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "@mdi/font/css/materialdesignicons.css";

import {en, de} from "vuetify/locale"

import App from "./App.vue";
import router from "./router";

const language = navigator.language ? navigator.language.split("-")[0] : "de";

const i18n = createI18n({
    legacy: false,
    globalInjection: true,
    locale: language,
    fallbackLocale: "de",
    messages: {
        en: enLang,
        de: deLang
    }
});

const vuetify = createVuetify({
    components,
    directives,
    theme: {
        defaultTheme: "dark"
    },
    locale: {
        locale: language,
        fallback: "de",
        messages: {de, en},
    },
});

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(i18n);
app.use(vuetify);

app.mount("#app");

