import {defineStore} from "pinia";
import {useLocalStorage} from "@vueuse/core";
import {ref} from "vue";

export const useSettingsStore = defineStore("settings", () => {
    const smoothGraphs = useLocalStorage("smoothGraphs", true);
    const graphHistoryTime = useLocalStorage("graphHistoryTime", 24);
    const stationId = ref(1);
    return {stationId, smoothGraphs, graphHistoryTime};
});
