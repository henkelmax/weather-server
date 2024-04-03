package de.maxhenkel.weatherserver.services;

import de.maxhenkel.weatherserver.dtos.Weather;
import de.maxhenkel.weatherserver.entities.WeatherEntity;
import de.maxhenkel.weatherserver.repositories.WeatherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Weather> getWeather(long deviceId, LocalDateTime from, LocalDateTime to) {
        List<WeatherEntity> weather = weatherRepository.findByStationIdAndDateGreaterThanEqualAndDateLessThanEqual(deviceId, from, to);
        return weather.stream().map(updateEntity -> modelMapper.map(updateEntity, Weather.class)).toList();
    }

    public Optional<Weather> getCurrentWeather(long deviceId) {
        return weatherRepository.findFirstByStationIdOrderByDateDesc(deviceId).map(updateEntity -> modelMapper.map(updateEntity, Weather.class));
    }

    public WeatherEntity save(WeatherEntity weatherEntity) {
        return weatherRepository.save(weatherEntity);
    }

}
