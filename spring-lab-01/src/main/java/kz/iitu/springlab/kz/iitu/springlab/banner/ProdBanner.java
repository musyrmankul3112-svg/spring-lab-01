package kz.iitu.springlab.kz.iitu.springlab.banner;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProdBanner implements EnvironmentBanner {

    @Override
    public String message() {
        return "Running in PROD profile";
    }
}