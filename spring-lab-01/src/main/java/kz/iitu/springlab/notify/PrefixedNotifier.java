package kz.iitu.springlab.notify;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("prefixed")
@Order(3)
public class PrefixedNotifier implements Notifier {

    @Value("${app.prefix:LAB2}")
    private String prefix;

    @PostConstruct
    public void init() {
        System.out.println("PREFXED NOTIFIER INIT: " + prefix);
    }

    @Override
    public String send(String message) {
        return prefix + ": " + message;
    }

    @Override
    public String channel() {
        return "prefixed";
    }
}