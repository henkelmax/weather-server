package de.maxhenkel.weatherserver.controllers;

import de.maxhenkel.weatherserver.annotations.ValidateApiKey;
import de.maxhenkel.weatherserver.dtos.Station;
import de.maxhenkel.weatherserver.services.StationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/stations")
    public List<Station> getStations() {
        return stationService.getAll();
    }

    @GetMapping("/station")
    public Station getStation(@RequestParam(name = "id") Optional<Long> stationId) {
        return stationService.getByStationId(stationId.orElse(defaultStationId)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No element found")); //TODO Return problem+json
    }

    @GetMapping("/stations/{stationId}")
    public Station getStation(@PathVariable long stationId) {
        return stationService.getByStationId(stationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No element found")); //TODO Return problem+json
    }

    @ValidateApiKey
    @PostMapping("/stations")
    public Station addStation(@RequestBody @Valid Station station) {
        Station addedStation = stationService.addStation(station);
        if (addedStation == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Station already exists");
        }
        return addedStation;
    }

    @ValidateApiKey
    @DeleteMapping("/stations/{stationId}")
    public ResponseEntity<Void> deleteStation(@PathVariable long stationId) {
        if (!stationService.deleteStation(stationId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found"); //TODO Return problem+json
        }
        return ResponseEntity.ok().build();
    }

}
