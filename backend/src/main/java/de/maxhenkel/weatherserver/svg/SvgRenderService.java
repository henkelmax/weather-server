package de.maxhenkel.weatherserver.svg;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import de.maxhenkel.weatherserver.dtos.Weather;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class SvgRenderService {

    private static final NumberFormat ONE_DIGIT;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        ONE_DIGIT = new DecimalFormat("##.#", symbols);
    }

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final SvgCacheService svgCache;
    private final Template template;

    public SvgRenderService(SvgCacheService svgCache) throws IOException {
        this.svgCache = svgCache;
        Handlebars handlebars = new Handlebars();
        template = handlebars.compile("widget/weather.svg");
    }

    public String renderWidget(Weather weather) throws IOException {
        return template.apply(Context.newBuilder(createModel(weather)).build());
    }

    private Map<String, Object> createModel(Weather weather) {
        Map<String, Object> model = new HashMap<>();
        Svgs svgs = svgCache.getSvgs();
        String weatherImage;
        if (weather.getRainRate() > 0F || weather.getRainHourly() > 0F) {
            weatherImage = svgs.getRainy();
        } else if (weather.getUvi() <= 0F) {
            weatherImage = svgs.getCloudy();
        } else if (weather.getUvi() >= 1F && weather.getUvi() <= 2F) {
            weatherImage = svgs.getSunnyCloudy();
        } else {
            weatherImage = svgs.getSunny();
        }

        model.put("image", weatherImage);
        model.put("temperature", ONE_DIGIT.format(weather.getTemperature()));
        model.put("rain", (int) Math.ceil(weather.getRainDaily()));
        model.put("rainIcon", svgs.getRainIcon());
        model.put("uvi", weather.getUvi());
        model.put("sunIcon", svgs.getSunIcon());
        model.put("updateIcon", svgs.getUpdateIcon());
        model.put("date", weather.getDate().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).format(DATE_FORMAT));
        return model;
    }

}
