package de.maxhenkel.weatherserver.repositories;

import de.maxhenkel.weatherserver.entities.StationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StationRepository extends CrudRepository<StationEntity, Long> {

    Optional<StationEntity> findByStationId(Long id);

    boolean existsByStationId(Long id);

}
