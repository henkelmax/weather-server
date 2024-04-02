package de.maxhenkel.weatherserver.services;

import de.maxhenkel.weatherserver.dtos.PassKey;
import de.maxhenkel.weatherserver.entities.PasskeyEntity;
import de.maxhenkel.weatherserver.repositories.PasskeyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class PasskeyService {

    @Autowired
    private PasskeyRepository passkeyRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<Long> getId(String passkey) {
        return passkeyRepository.findFirstByPasskey(passkey).map(PasskeyEntity::getStationId);
    }

    public Optional<PassKey> getById(long id) {
        return passkeyRepository.findById(id).map(passkeyEntity -> modelMapper.map(passkeyEntity, PassKey.class));
    }

    public List<PassKey> getAll() {
        return StreamSupport.stream(passkeyRepository.findAll().spliterator(), false).map(passkeyEntity -> modelMapper.map(passkeyEntity, PassKey.class)).toList();
    }

    public boolean addPasskey(PassKey passkey) {
        if (passkeyRepository.existsById(passkey.getStationId())) {
            return false;
        }
        PasskeyEntity passkeyEntity = modelMapper.map(passkey, PasskeyEntity.class);
        passkeyRepository.save(passkeyEntity);
        return true;
    }

}
