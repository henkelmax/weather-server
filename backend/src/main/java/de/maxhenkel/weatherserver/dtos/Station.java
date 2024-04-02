package de.maxhenkel.weatherserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Station extends StationNoId {

    @NotNull
    @JsonProperty("deviceId") //TODO Remove in v2
    private Long stationId;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Station that = (Station) object;
        return Objects.equals(stationId, that.stationId);
    }

    @Override
    public int hashCode() {
        return stationId != null ? stationId.hashCode() : 0;
    }
}
