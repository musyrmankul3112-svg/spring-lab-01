package kz.iitu.springlab.kz.iitu.springlab.banner;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevBanner implements EnvironmentBanner {

    @Override
    public String message() {
        return "Running in DEV profile";
    }
}