package de.maxhenkel.weatherserver.controllers;

import de.maxhenkel.weatherserver.annotations.ValidateApiKey;
import de.maxhenkel.weatherserver.dtos.Station;
import de.maxhenkel.weatherserver.dtos.StationNoId;
import de.maxhenkel.weatherserver.services.StationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("stations")
    public List<Station> stations() {
        return stationService.getAll();
    }

    @GetMapping("station")
    public Station station(@RequestParam(name = "id") Long deviceId) {
        return stationService.getById(deviceId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No element found")); //TODO Return problem+json
    }

    @ValidateApiKey
    @PostMapping("stations")
    public Station addStation(@RequestBody @Valid StationNoId station) {
        return stationService.addStation(station);
    }

}
