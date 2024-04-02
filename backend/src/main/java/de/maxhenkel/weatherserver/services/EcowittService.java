package de.maxhenkel.weatherserver.services;

import de.maxhenkel.weatherserver.dtos.EcowittData;
import de.maxhenkel.weatherserver.entities.WeatherEntity;
import de.maxhenkel.weatherserver.repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class EcowittService {

    private static final DateTimeFormatter ECOWITT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private PasskeyService passkeyService;

    @Autowired
    private WeatherRepository weatherRepository;

    public boolean addEcowittWeather(EcowittData ecowittData) {
        Optional<Long> optionalId = passkeyService.getId(ecowittData.getPASSKEY());

        if (optionalId.isEmpty()) {
            return false;
        }

        weatherRepository.save(convert(optionalId.get(), ecowittData));
        return true;
    }

    private WeatherEntity convert(long deviceId, EcowittData ecowittData) {
        WeatherEntity weather = new WeatherEntity();

        //TODO Truncate after two digits
        weather.setStationId(deviceId);
        weather.setDate(LocalDateTime.parse(ecowittData.getDateutc(), ECOWITT_FORMATTER));
        weather.setTemperature(toCelsius(ecowittData.getTempf()));
        weather.setHumidity(ecowittData.getHumidity());
        weather.setRelativePressure(toHpa(ecowittData.getBaromrelin()));
        weather.setAbsolutePressure(toHpa(ecowittData.getBaromabsin()));
        weather.setWindDirection(ecowittData.getWinddir());
        weather.setWindSpeed(toKmh(ecowittData.getWindspeedmph()));
        weather.setWindGust(toKmh(ecowittData.getWindgustmph()));
        weather.setMaxDailyWindGust(toKmh(ecowittData.getMaxdailygust()));
        weather.setRainRate(toMm(ecowittData.getRainratein()));
        weather.setRainEvent(toMm(ecowittData.getEventrainin()));
        weather.setRainHourly(toMm(ecowittData.getHourlyrainin()));
        weather.setRainDaily(toMm(ecowittData.getDailyrainin()));
        weather.setRainWeekly(toMm(ecowittData.getWeeklyrainin()));
        weather.setRainMonthly(toMm(ecowittData.getMonthlyrainin()));
        weather.setSolarRadiation(ecowittData.getSolarradiation());
        weather.setUvi(ecowittData.getUv());

        return weather;
    }

    private static double toCelsius(double fahrenheit) {
        return (fahrenheit - 32D) * 5D / 9D;
    }

    private static double toHpa(double inhg) {
        return inhg * 33.86389D;
    }

    private static double toKmh(double mph) {
        return mph * 1.609344D;
    }

    private static double toMm(double inch) {
        return inch * 25.4D;
    }

}
