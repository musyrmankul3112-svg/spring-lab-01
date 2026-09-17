package kz.iitu.springlab;

import kz.iitu.springlab.notify.Notifier;
import kz.iitu.springlab.notify.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ContainerReport implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(ContainerReport.class);

    private final ApplicationContext context;

    public ContainerReport(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) {
        log.info("BEAN DEFINITIONS = {}", context.getBeanDefinitionCount());

        log.info("NOTIFIER BEANS = {}",
                Arrays.toString(context.getBeanNamesForType(Notifier.class)));

        log.info("NOTIFICATION SERVICE = {}",
                context.getBean(NotificationService.class).getClass().getName());

        log.info("FILTERED BEANS = {}",
                Arrays.stream(context.getBeanDefinitionNames())
                        .filter(name -> name.toLowerCase().contains("notifier"))
                        .toList());
    }
}