interface Weather {
    id: number;
    stationId: number;
    date: Date;
    temperature: number;
    humidity: number;
    relativePressure: number;
    absolutePressure: number;
    windDirection: number;
    windSpeed: number;
    windGust: number;
    maxDailyWindGust: number;
    rainRate: number;
    rainEvent: number;
    rainHourly: number;
    rainDaily: number;
    rainWeekly: number;
    rainMonthly: number;
    solarRadiation: number;
    uvi: number;
}

type Station = {
    deviceId: number;
    name: string;
    description: string;
    latitude: number;
    longitude: number;
}