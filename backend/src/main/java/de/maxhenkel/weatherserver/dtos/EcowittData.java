package de.maxhenkel.weatherserver.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class EcowittData {

    @NotBlank
    @Pattern(regexp = "^[0-9a-fA-F]{32}$")
    private String PASSKEY;

    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$")
    private String dateutc;

    @NotNull
    private Double tempf;

    @NotNull
    private Double humidity;

    @NotNull
    private Double winddir;

    @NotNull
    private Double windspeedmph;

    @NotNull
    private Double windgustmph;

    @NotNull
    private Double maxdailygust;

    @NotNull
    private Double rainratein;

    @NotNull
    private Double eventrainin;

    @NotNull
    private Double hourlyrainin;

    @NotNull
    private Double dailyrainin;

    @NotNull
    private Double weeklyrainin;

    @NotNull
    private Double monthlyrainin;

    @NotNull
    private Double solarradiation;

    @NotNull
    private Integer uv;

    @NotNull
    private Double baromrelin;

    @NotNull
    private Double baromabsin;

}
