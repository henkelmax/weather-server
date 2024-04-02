package de.maxhenkel.weatherserver.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity(name = "passkeys")
@NoArgsConstructor
public class PasskeyEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stationId;

    @Column(nullable = false)
    private String passkey;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        PasskeyEntity that = (PasskeyEntity) object;
        return Objects.equals(stationId, that.stationId);
    }

    @Override
    public int hashCode() {
        return stationId != null ? stationId.hashCode() : 0;
    }
}
