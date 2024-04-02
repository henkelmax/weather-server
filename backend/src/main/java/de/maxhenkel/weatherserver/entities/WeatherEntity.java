package de.maxhenkel.weatherserver.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity(name = "weather")
@NoArgsConstructor
public class WeatherEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long stationId;
    @Column(nullable = false)
    private LocalDateTime date;
    //TODO Make everything NonNull
    private Double temperature;
    private Double humidity;
    private Double relativePressure;
    private Double absolutePressure;
    private Double windDirection;
    private Double windSpeed;
    private Double windGust;
    private Double maxDailyWindGust;
    private Double rainRate;
    private Double rainEvent;
    private Double rainHourly;
    private Double rainDaily;
    private Double rainWeekly;
    private Double rainMonthly;
    private Double solarRadiation;
    private Integer uvi;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        WeatherEntity that = (WeatherEntity) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
