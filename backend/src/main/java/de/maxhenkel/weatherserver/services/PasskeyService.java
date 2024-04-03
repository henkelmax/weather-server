package de.maxhenkel.weatherserver.services;

import de.maxhenkel.weatherserver.dtos.Passkey;
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

    public Optional<Passkey> getById(long id) {
        return passkeyRepository.findById(id).map(passkeyEntity -> modelMapper.map(passkeyEntity, Passkey.class));
    }

    public List<Passkey> getAll() {
        return StreamSupport.stream(passkeyRepository.findAll().spliterator(), false).map(passkeyEntity -> modelMapper.map(passkeyEntity, Passkey.class)).toList();
    }

    public boolean addPasskey(Passkey passkey) {
        if (passkeyRepository.existsById(passkey.getStationId())) {
            return false;
        }
        PasskeyEntity passkeyEntity = modelMapper.map(passkey, PasskeyEntity.class);
        passkeyRepository.save(passkeyEntity);
        return true;
    }

    public boolean deletePasskey(long id) {
        if (!passkeyRepository.existsById(id)) {
            return false;
        }
        passkeyRepository.deleteById(id);
        return true;
    }

}
