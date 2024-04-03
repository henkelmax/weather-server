package de.maxhenkel.weatherserver.controllers;

import de.maxhenkel.weatherserver.annotations.ValidateApiKey;
import de.maxhenkel.weatherserver.dtos.Station;
import de.maxhenkel.weatherserver.services.StationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class StationController {

    @Autowired
    private StationService stationService;

    @Value("${weatherserver.defaultStationId:1}")
    private Long defaultStationId;

    @GetMapping("stations")
    public List<Station> stations() {
        return stationService.getAll();
    }

    @GetMapping("station")
    public Station station(@RequestParam(name = "id") Optional<Long> deviceId) {
        return stationService.getById(deviceId.orElse(defaultStationId)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No element found")); //TODO Return problem+json
    }

    @ValidateApiKey
    @PostMapping("stations")
    public Station addStation(@RequestBody @Valid Station station) {
        Station addedStation = stationService.addStation(station);
        if (addedStation == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Station already exists");
        }
        return addedStation;
    }

}
