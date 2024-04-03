package de.maxhenkel.weatherserver.svg;

import lombok.Getter;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class SvgCacheService {

    private final ResourceLoader resourceLoader;

    @Getter
    private final Svgs svgs;

    public SvgCacheService(ResourceLoader resourceLoader) throws IOException {
        this.resourceLoader = resourceLoader;
        svgs = new Svgs();
        svgs.setUpdateIcon(getSvg("update_icon"));
        svgs.setSunIcon(getSvg("sun_icon"));
        svgs.setRainIcon(getSvg("rain_icon"));
        svgs.setSunny(getSvg("sunny"));
        svgs.setCloudy(getSvg("cloudy"));
        svgs.setSunnyCloudy(getSvg("sunny_cloudy"));
        svgs.setRainy(getSvg("rainy"));
    }

    private String getSvg(String name) throws IOException {
        return resourceLoader.getResource("classpath:/widget/%s.svg".formatted(name)).getContentAsString(StandardCharsets.UTF_8);
    }

}
