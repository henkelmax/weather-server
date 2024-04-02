package de.maxhenkel.weatherserver.dtos;

import lombok.*;

@Data
@NoArgsConstructor
public class Error {

    private int statusCode;
    private String message;

}
