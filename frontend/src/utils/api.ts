import moment from "moment/min/moment-with-locales";

function getServerHost() {
    if (import.meta.env.MODE === "development") {
        // return "http://localhost:8088";
        return "https://weather.maxhenkel.de";
    } else {
        return location.protocol + "//" + location.host;
    }
}

function getWeatherURL(searchParams: URLSearchParams) {
    return `${getServerHost()}/api/v1/weather?${searchParams}`;
}

export function getTodaysWeatherParams(id: number, hours: number = 24) {
    const searchParams: URLSearchParams = new URLSearchParams();
    const now = moment();
    searchParams.append("id", String(id));
    if (hours <= 0) {
        searchParams.append("from", String(now.startOf("day").valueOf()));
    } else {
        searchParams.append("from", String(now.subtract(hours, "hours").valueOf()));
    }
    return searchParams;
}

export function getWeatherParams(id: number, date: Date) {
    const searchParams: URLSearchParams = new URLSearchParams();
    searchParams.append("id", String(id));
    searchParams.append("from", String(moment(date).startOf("day").valueOf()));
    searchParams.append("to", String(moment(date).endOf("day").valueOf()));
    return searchParams;
}

export function fetchCurrentWeather(id: number): Promise<Weather> {
    return fetch(`${getServerHost()}/api/v1/weather/current?id=${id}`).then((response) => response.json());
}

export function fetchWeather(params: URLSearchParams): Promise<Weather[]> {
    return fetch(getWeatherURL(params))
        .then((response) => response.json())
        .then((data) => {
            return data.map((w: any) => {
                return {...w, date: new Date(w.date)} as Weather;
            });
        });
}

export function fetchStation(): Promise<Station> {
    return fetch(`${getServerHost()}/api/v1/station`).then((response) => response.json());
}

export function fetchStations(): Promise<Station[]> {
    return fetch(`${getServerHost()}/api/v1/stations`).then((response) => response.json());
}