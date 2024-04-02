package de.maxhenkel.weatherserver.controllers;

import de.maxhenkel.weatherserver.dtos.EcowittData;
import de.maxhenkel.weatherserver.services.EcowittService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping({"/api/v1/weather/ecowitt", "/data/ecowitt"})
public class EcowittController {

    @Autowired
    private EcowittService ecowittService;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> ecowitt(@Valid EcowittData ecowittData) {
        if (!ecowittService.addEcowittWeather(ecowittData)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid passkey"); //TODO Return problem+json
        }
        return ResponseEntity.ok().build();
    }

}
