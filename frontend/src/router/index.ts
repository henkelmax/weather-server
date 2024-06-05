import {createRouter, createWebHashHistory} from "vue-router";
import Weather from "../views/Weather.vue";
import WeatherHistory from "../views/WeatherHistory.vue";
import Settings from "../views/Settings.vue";
import Privacy from "../views/Privacy.vue";
import Legal from "../views/Legal.vue";

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: "/",
            name: "weather",
            component: Weather
        },
        {
            path: "/history",
            name: "history",
            component: WeatherHistory
        },
        {
            path: "/settings",
            name: "settings",
            component: Settings
        },
        {
            path: "/privacy",
            name: "privacy",
            component: Privacy
        },
        {
            path: "/legal",
            name: "legal",
            component: Legal
        }
    ]
});

export default router;
