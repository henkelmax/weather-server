package de.maxhenkel.weatherserver.services;

import de.maxhenkel.weatherserver.dtos.Station;
import de.maxhenkel.weatherserver.dtos.StationNoId;
import de.maxhenkel.weatherserver.entities.StationEntity;
import de.maxhenkel.weatherserver.repositories.StationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<Station> getById(long id) {
        return stationRepository.findById(id).map(e -> modelMapper.map(e, Station.class));
    }

    public List<Station> getAll() {
        return StreamSupport.stream(stationRepository.findAll().spliterator(), false).map(e -> modelMapper.map(e, Station.class)).toList();
    }

    public Station addStation(StationNoId station) {
        return modelMapper.map(stationRepository.save(modelMapper.map(station, StationEntity.class)), Station.class);
    }

}
