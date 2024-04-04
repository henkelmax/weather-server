package de.maxhenkel.weatherserver.csv;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import de.maxhenkel.weatherserver.entities.WeatherEntity;
import de.maxhenkel.weatherserver.repositories.WeatherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

@Service
public class ExportService {

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private ModelMapper modelMapper;

    public String exportCsv() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        StringWriter writer = new StringWriter();

        StatefulBeanToCsvBuilder<WeatherCsvEntry> builder = new StatefulBeanToCsvBuilder<>(writer);
        StatefulBeanToCsv<WeatherCsvEntry> beanWriter = builder
                .withApplyQuotesToAll(false)
                .build();

        for (WeatherEntity weatherEntity : weatherRepository.findAll()) {
            beanWriter.write(modelMapper.map(weatherEntity, WeatherCsvEntry.class));
        }

        return writer.toString();
    }

}
