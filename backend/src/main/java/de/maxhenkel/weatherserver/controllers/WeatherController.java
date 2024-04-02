package de.maxhenkel.weatherserver.controllers;

import de.maxhenkel.weatherserver.dtos.Weather;
import de.maxhenkel.weatherserver.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public List<Weather> weather(
            @RequestParam(name = "id") Long deviceId,
            @RequestParam(name = "from") Optional<Long> from,
            @RequestParam(name = "to") Optional<Long> to
    ) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fromTime = from.map(t -> Instant.ofEpochMilli(t).atZone(ZoneId.of("UTC")).toLocalDateTime()).orElse(now.minusDays(1));
        LocalDateTime toTime = to.map(t -> Instant.ofEpochMilli(t).atZone(ZoneId.of("UTC")).toLocalDateTime()).orElse(now);

        if (fromTime.isAfter(toTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The from time cannot be after the to time");
        }
        if (toTime.isAfter(fromTime.plusDays(2))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The maximum allowed time difference is two days"); //TODO check if this works
        }

        return weatherService.getWeather(deviceId, fromTime, toTime);
    }

    @GetMapping("current")
    public Weather current(@RequestParam(name = "id") Long deviceId) {
        return weatherService.getCurrentWeather(deviceId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No element found")); //TODO Return problem+json
    }

}
