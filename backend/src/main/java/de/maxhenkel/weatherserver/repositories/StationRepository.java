package de.maxhenkel.weatherserver.repositories;

import de.maxhenkel.weatherserver.entities.StationEntity;
import org.springframework.data.repository.CrudRepository;

public interface StationRepository extends CrudRepository<StationEntity, Long> {

}
