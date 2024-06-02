import {createRouter, createWebHistory} from "vue-router";
import Weather from "../views/Weather.vue";
import Settings from "../views/Settings.vue";
import Privacy from "../views/Privacy.vue";
import Legal from "../views/Legal.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "weather",
            component: Weather
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
