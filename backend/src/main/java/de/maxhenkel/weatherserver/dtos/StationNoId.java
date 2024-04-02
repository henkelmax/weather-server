package de.maxhenkel.weatherserver.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StationNoId {

    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;

}
