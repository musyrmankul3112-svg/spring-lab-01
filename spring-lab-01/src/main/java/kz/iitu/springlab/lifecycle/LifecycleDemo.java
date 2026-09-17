package kz.iitu.springlab.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class LifecycleDemo {

    private static final Logger log =
            LoggerFactory.getLogger(LifecycleDemo.class);

    private final DateTimeFormatter formatter;
    private final List<String> events = new ArrayList<>();

    public LifecycleDemo(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @PostConstruct
    public void init() {
        String event = "INIT " + LocalDateTime.now().format(formatter);
        events.add(event);
        log.info("LIFECYCLE >> {}", event);
    }

    public List<String> getEvents() {
        return List.copyOf(events);
    }

    @PreDestroy
    public void destroy() {
        String event = "DESTROY " + LocalDateTime.now().format(formatter);
        log.info("LIFECYCLE >> {}", event);
    }
}