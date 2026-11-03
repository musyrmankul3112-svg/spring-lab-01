package kz.iitu.springlab.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {

    private final AuditRecordRepository repository;

    public AuditService(AuditRecordRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void writeRequired(String message) {
        repository.save(new AuditRecord(message));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void writeRequiresNew(String message) {
        repository.save(new AuditRecord(message));
    }
}