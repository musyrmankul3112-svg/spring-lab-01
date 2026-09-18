package kz.iitu.springlab.web;

import kz.iitu.springlab.lifecycle.LifecycleDemo;
import kz.iitu.springlab.notify.Notifier;
import kz.iitu.springlab.notify.NotificationService;
import kz.iitu.springlab.scope.TicketOffice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab2")
public class Lab2Controller {

    private final NotificationService service;
    private final LifecycleDemo lifecycleDemo;
    private final TicketOffice ticketOffice;
    private final Notifier prefixedNotifier;

    public Lab2Controller(NotificationService service,
                          LifecycleDemo lifecycleDemo,
                          TicketOffice ticketOffice,
                          @Qualifier("prefixed") Notifier prefixedNotifier) {
        this.service = service;
        this.lifecycleDemo = lifecycleDemo;
        this.ticketOffice = ticketOffice;
        this.prefixedNotifier = prefixedNotifier;
    }

    @GetMapping("/notify")
    public Map<String, Object> notify(@RequestParam String text) {
        return Map.of(
                "primary", service.viaPrimary(text),
                "console", service.viaConsole(text),
                "all", service.viaAll(text),
                "beanNames", service.names()
        );
    }

    @GetMapping("/lifecycle")
    public List<String> lifecycle() {
        return lifecycleDemo.getEvents();
    }

    @GetMapping("/scopes")
    public String scopes() {
        return ticketOffice.demo();
    }

    @GetMapping("/custom")
    public String custom(@RequestParam String text) {
        return prefixedNotifier.send(text);
    }
}