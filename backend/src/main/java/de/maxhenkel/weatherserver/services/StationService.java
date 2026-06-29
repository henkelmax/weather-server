package de.maxhenkel.weatherserver.services;

import de.maxhenkel.weatherserver.dtos.Station;
import de.maxhenkel.weatherserver.entities.StationEntity;
import de.maxhenkel.weatherserver.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;
    private final ModelMapper modelMapper;

    public Optional<Station> getByStationId(long id) {
        return stationRepository.findByStationId(id).map(e -> modelMapper.map(e, Station.class));
    }

    public List<Station> getAll() {
        return StreamSupport.stream(stationRepository.findAll().spliterator(), false).map(e -> modelMapper.map(e, Station.class)).toList();
    }

    @Nullable
    public Station addStation(Station station) {
        if (stationRepository.existsByStationId(station.getStationId())) {
            return null;
        }
        return modelMapper.map(stationRepository.save(modelMapper.map(station, StationEntity.class)), Station.class);
    }

    public boolean deleteStation(long id) {
        if (!stationRepository.existsById(id)) {
            return false;
        }
        stationRepository.deleteById(id);
        return true;
    }
}
