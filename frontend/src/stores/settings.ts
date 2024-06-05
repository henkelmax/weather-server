import {defineStore} from "pinia";
import {useLocalStorage} from "@vueuse/core";

export const useSettingsStore = defineStore("settings", () => {
    const smoothGraphs = useLocalStorage("smoothGraphs", true);
    const graphHistoryTime = useLocalStorage("graphHistoryTime", 24);
    return {smoothGraphs, graphHistoryTime};
});
