package de.maxhenkel.weatherserver.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherCsvEntry {

    @CsvBindByName(column = "deviceId")
    private long stationId;
    @CsvBindByName(column = "date")
    @CsvDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime date;
    @CsvBindByName(column = "temperature")
    private float temperature;
    @CsvBindByName(column = "humidity")
    private float humidity;
    @CsvBindByName(column = "relativePressure")
    private float relativePressure;
    @CsvBindByName(column = "absolutePressure")
    private float absolutePressure;
    @CsvBindByName(column = "windDirection")
    private float windDirection;
    @CsvBindByName(column = "windSpeed")
    private float windSpeed;
    @CsvBindByName(column = "windGust")
    private float windGust;
    @CsvBindByName(column = "maxDailyWindGust")
    private float maxDailyWindGust;
    @CsvBindByName(column = "rainRate")
    private float rainRate;
    @CsvBindByName(column = "rainEvent")
    private float rainEvent;
    @CsvBindByName(column = "rainHourly")
    private float rainHourly;
    @CsvBindByName(column = "rainDaily")
    private float rainDaily;
    @CsvBindByName(column = "rainWeekly")
    private float rainWeekly;
    @CsvBindByName(column = "rainMonthly")
    private float rainMonthly;
    @CsvBindByName(column = "solarRadiation")
    private float solarRadiation;
    @CsvBindByName(column = "uvi")
    private int uvi;

}
