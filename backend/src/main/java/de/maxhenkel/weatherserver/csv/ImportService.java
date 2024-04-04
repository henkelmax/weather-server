package de.maxhenkel.weatherserver.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import de.maxhenkel.weatherserver.entities.WeatherEntity;
import de.maxhenkel.weatherserver.services.WeatherService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ImportService {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ModelMapper modelMapper;

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
