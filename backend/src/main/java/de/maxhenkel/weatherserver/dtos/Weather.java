package de.maxhenkel.weatherserver.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Weather {

    private Long id;
    @JsonProperty("deviceId")
    private Integer stationId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime date;
    private Float temperature;
    private Float humidity;
    private Float relativePressure;
    private Float absolutePressure;
    private Float windDirection;
    private Float windSpeed;
    private Float windGust;
    private Float maxDailyWindGust;
    private Float rainRate;
    private Float rainEvent;
    private Float rainHourly;
    private Float rainDaily;
    private Float rainWeekly;
    private Float rainMonthly;
    private Float solarRadiation;
    private Integer uvi;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Weather that = (Weather) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
