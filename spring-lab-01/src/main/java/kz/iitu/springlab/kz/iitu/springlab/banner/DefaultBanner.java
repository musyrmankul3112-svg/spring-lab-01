package kz.iitu.springlab.kz.iitu.springlab.banner;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!dev & !prod")
public class DefaultBanner implements EnvironmentBanner {

    @Override
    public String message() {
        return "Running with no explicit profile";
    }
}