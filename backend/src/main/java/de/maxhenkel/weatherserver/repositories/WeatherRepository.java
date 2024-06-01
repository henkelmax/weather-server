package de.maxhenkel.weatherserver.repositories;

import de.maxhenkel.weatherserver.entities.WeatherEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WeatherRepository extends CrudRepository<WeatherEntity, Long> {

    List<WeatherEntity> findByStationIdAndDateGreaterThanEqualAndDateLessThanEqualOrderByDateDesc(long deviceId, LocalDateTime start, LocalDateTime end);

    Optional<WeatherEntity> findFirstByStationIdOrderByDateDesc(long deviceId);

}
