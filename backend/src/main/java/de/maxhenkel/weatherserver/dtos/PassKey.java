package de.maxhenkel.weatherserver.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class PassKey {

    @NotNull
    private Long stationId;
    @NotNull
    @Pattern(regexp = "^[0-9a-fA-F]{32}$")
    private String passKey;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        PassKey that = (PassKey) object;
        return Objects.equals(stationId, that.stationId);
    }

    @Override
    public int hashCode() {
        return stationId != null ? stationId.hashCode() : 0;
    }
}
