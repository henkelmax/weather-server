package de.maxhenkel.weatherserver.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import de.maxhenkel.weatherserver.entities.WeatherEntity;
import de.maxhenkel.weatherserver.services.WeatherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportService {

    private final WeatherService weatherService;
    private final ModelMapper modelMapper;

    @Transactional
    public int importCsv(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            CsvToBean<WeatherCsvEntry> csvToBean = new CsvToBeanBuilder<WeatherCsvEntry>(reader)
                    .withType(WeatherCsvEntry.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<WeatherCsvEntry> entries = csvToBean.parse();

            for (WeatherCsvEntry entry : entries) {
                WeatherEntity weatherEntity = modelMapper.map(entry, WeatherEntity.class);
                weatherService.save(weatherEntity);
            }

            return entries.size();
        }
    }

}
