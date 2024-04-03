package de.maxhenkel.weatherserver.svg;

import de.maxhenkel.weatherserver.dtos.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class SvgRenderService {

    private static final NumberFormat ONE_DIGIT = new DecimalFormat("##.0");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Autowired
    private SvgCacheService svgCache;

    public void populateModel(Model model, Weather weather) {
        Svgs svgs = svgCache.getSvgs();
        String weatherImage;
        if (weather.getRainRate() > 0F || weather.getRainHourly() > 0F) {
            weatherImage = svgs.getRainIcon();
        } else if (weather.getUvi() <= 0F) {
            weatherImage = svgs.getCloudy();
        } else if (weather.getUvi() >= 1F && weather.getUvi() <= 2F) {
            weatherImage = svgs.getSunnyCloudy();
        } else {
            weatherImage = svgs.getSunny();
        }

        model.addAttribute("image", weatherImage);
        model.addAttribute("temperature", ONE_DIGIT.format(weather.getTemperature()));
        model.addAttribute("rain", Math.ceil(weather.getRainDaily()));
        model.addAttribute("rainIcon", svgs.getRainIcon());
        model.addAttribute("uvi", weather.getUvi());
        model.addAttribute("sunIcon", svgs.getSunIcon());
        model.addAttribute("updateIcon", svgs.getUpdateIcon());
        model.addAttribute("date", weather.getDate().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).format(DATE_FORMAT));
    }

}
