import {defineStore} from "pinia";
import {useLocalStorage} from "@vueuse/core";

export const useSettingsStore = defineStore("settings", () => {
    const smoothGraphs = useLocalStorage("smoothGraphs", true);
    const graphHistoryTime = useLocalStorage("graphHistoryTime", 24);
    const stationId = useLocalStorage("stationId", 1);
    return {stationId, smoothGraphs, graphHistoryTime};
});
