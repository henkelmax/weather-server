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
    @Column(nullable = false)
    private Double temperature;
    @Column(nullable = false)
    private Double humidity;
    @Column(nullable = false)
    private Double relativePressure;
    @Column(nullable = false)
    private Double absolutePressure;
    @Column(nullable = false)
    private Double windDirection;
    @Column(nullable = false)
    private Double windSpeed;
    @Column(nullable = false)
    private Double windGust;
    @Column(nullable = false)
    private Double maxDailyWindGust;
    @Column(nullable = false)
    private Double rainRate;
    @Column(nullable = false)
    private Double rainEvent;
    @Column(nullable = false)
    private Double rainHourly;
    @Column(nullable = false)
    private Double rainDaily;
    @Column(nullable = false)
    private Double rainWeekly;
    @Column(nullable = false)
    private Double rainMonthly;
    @Column(nullable = false)
    private Double solarRadiation;
    @Column(nullable = false)
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
