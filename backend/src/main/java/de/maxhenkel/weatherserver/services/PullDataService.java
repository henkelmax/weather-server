package de.maxhenkel.weatherserver.services;

import de.maxhenkel.weatherserver.dtos.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@EnableScheduling
@ConditionalOnProperty(name = "pull_weather.enabled", havingValue = "true")
public class PullDataService {

    @Value("${pull_weather.url}")
    private String url;

    @Value("${pull_weather.id:1}")
    private Integer id;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherService weatherService;

    @Scheduled(fixedRate = 10L * 60L * 1000L, initialDelay = 0L)
    public void fetchWeatherFromUrl() {
        if (url == null) {
            log.warn("No url set for weather pull");
            return;
        }
        log.info("Pulling weather from '{}'", url);
        Weather weather = restTemplate.getForObject(url, Weather.class);
        if (weather == null) {
            log.warn("Failed to pull weather from '{}'", url);
            return;
        }

        weather.setId(null);
        weather.setStationId(id);
        weatherService.save(weather);
    }

}
