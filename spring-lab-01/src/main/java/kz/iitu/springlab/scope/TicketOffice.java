package kz.iitu.springlab.scope;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class TicketOffice {

    private final Ticket direct;
    private final ObjectProvider<Ticket> provider;

    public TicketOffice(Ticket direct,
                        ObjectProvider<Ticket> provider) {
        this.direct = direct;
        this.provider = provider;
    }

    public String demo() {
        Ticket p1 = provider.getObject();
        Ticket p2 = provider.getObject();

        return "direct=" + direct.getId()
                + ", provider1=" + p1.getId()
                + ", provider2=" + p2.getId()
                + ", sameProvider=" + (p1 == p2)
                + ", sameDirect=" + (direct == p1);
    }
}