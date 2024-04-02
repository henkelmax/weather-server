package de.maxhenkel.weatherserver.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity(name = "stations")
@NoArgsConstructor
public class StationEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stationId; //TODO Rename to station ID

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        StationEntity that = (StationEntity) object;
        return Objects.equals(stationId, that.stationId);
    }

    @Override
    public int hashCode() {
        return stationId != null ? stationId.hashCode() : 0;
    }
}
