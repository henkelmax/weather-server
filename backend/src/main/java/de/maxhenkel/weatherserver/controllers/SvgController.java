package de.maxhenkel.weatherserver.controllers;

import de.maxhenkel.weatherserver.dtos.Weather;
import de.maxhenkel.weatherserver.services.WeatherService;
import de.maxhenkel.weatherserver.svg.SvgRenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@CrossOrigin
public class SvgController {

    @Autowired
    private SvgRenderService renderService;

    @Autowired
    private WeatherService weatherService;

    @Value("${weatherserver.defaultStationId:1}")
    private Long defaultStationId;

    @Cacheable(value = "weatherSvg", cacheManager = "svgCacheManager")
    @GetMapping(value = "/api/v1/widget/weather.svg", produces = "image/svg+xml;charset=UTF-8")
    public String svg(@RequestParam(value = "id", required = false) Long stationId) throws IOException {
        if (stationId == null) {
            stationId = defaultStationId;
        }
        Weather currentWeather = weatherService.getCurrentWeather(stationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No weather data found"));
        return renderService.renderWidget(currentWeather);
    }

}
