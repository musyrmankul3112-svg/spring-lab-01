package kz.iitu.springlab.transaction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class IsolationExperimentService {

    private final AccountRepository repository;
    private final IsolationUpdateService updateService;

    @PersistenceContext
    private EntityManager entityManager;

    public IsolationExperimentService(
            AccountRepository repository,
            IsolationUpdateService updateService
    ) {
        this.repository = repository;
        this.updateService = updateService;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> testReadCommitted(Long accountId) {
        return runExperiment(accountId, "READ_COMMITTED");
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Map<String, Object> testRepeatableRead(Long accountId) {
        return runExperiment(accountId, "REPEATABLE_READ");
    }

    private Map<String, Object> runExperiment(Long accountId, String isolation) {
        BigDecimal firstRead = repository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"))
                .getBalance();

        updateService.increaseBalance(accountId, new BigDecimal("10.00"));

        // Очищаем кэш JPA, чтобы повторно запросить значение из базы.
        entityManager.clear();

        BigDecimal secondRead = repository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"))
                .getBalance();

        return Map.of(
                "isolation", isolation,
                "firstRead", firstRead,
                "secondRead", secondRead
        );
    }
}