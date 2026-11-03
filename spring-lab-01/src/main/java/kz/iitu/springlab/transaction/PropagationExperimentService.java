package kz.iitu.springlab.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropagationExperimentService {

    private final AuditService auditService;

    public PropagationExperimentService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Transactional
    public void runRequiredThenFail() {
        auditService.writeRequired("REQUIRED audit");
        throw new IllegalStateException("Forced outer failure for REQUIRED");
    }

    @Transactional
    public void runRequiresNewThenFail() {
        auditService.writeRequiresNew("REQUIRES_NEW audit");
        throw new IllegalStateException("Forced outer failure for REQUIRES_NEW");
    }
}