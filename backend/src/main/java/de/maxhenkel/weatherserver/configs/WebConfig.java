package de.maxhenkel.weatherserver.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Environment environment;

    @Value("#{environment.matchesProfiles('dev')}")
    private boolean isDev;

    @Value("#{environment.matchesProfiles('prod')}")
    private boolean isProd;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        List<String> resourceLocations = new ArrayList<>();
        if (isDev) {
            resourceLocations.add(Paths.get("frontend/dist/").toAbsolutePath().toUri().toString());
        }
        if (isProd) {
            resourceLocations.add("file:/var/www/");
        }
        registry
                .addResourceHandler("/**")
                .addResourceLocations(resourceLocations.toArray(new String[0]))
                .setCachePeriod(0);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

}
