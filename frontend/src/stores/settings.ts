import {defineStore} from "pinia";
import {useLocalStorage} from "@vueuse/core";

export const useSettingsStore = defineStore("settings", () => {
    const smoothGraphs = useLocalStorage("smoothGraphs", true);
    return {smoothGraphs};
});
