package de.maxhenkel.weatherserver.aspects;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@Aspect
public class AuthenticationAspect {

    @Autowired
    private Environment env;

    @Before("@annotation(de.maxhenkel.weatherserver.annotations.ValidateApiKey)")
    public void validateHeaders(JoinPoint joinPoint) {
        HttpServletRequest request = getRequest();
        if (!hasPermission(request.getHeader("x-api-key"))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Insufficient permissions");
        }
    }

    private boolean hasPermission(@Nullable String apiKey) {
        return getMasterKey().map(masterKey -> masterKey.equals(parseApiKey(apiKey))).orElse(false);
    }

    public Optional<UUID> getMasterKey() {
        try {
            if (env.acceptsProfiles(Profiles.of("!prod"))) {
                return Optional.of(UUID.fromString(env.getProperty("weatherserver.masterkey", "")));
            }
            return Optional.of(UUID.fromString(System.getenv("MASTER_KEY")));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public UUID parseApiKey(@Nullable String apiKey) {
        if (apiKey == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No API key provided");
        }
        try {
            return UUID.fromString(apiKey);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API key");
        }
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}