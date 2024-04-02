package de.maxhenkel.weatherserver.repositories;

import de.maxhenkel.weatherserver.entities.PasskeyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasskeyRepository extends CrudRepository<PasskeyEntity, Long> {

    Optional<PasskeyEntity> findFirstByPasskey(String passkey);

}
