package kz.iitu.springlab.transaction;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OptimisticLockExperimentService {

    private final OptimisticLockWorkerService workerService;

    public OptimisticLockExperimentService(
            OptimisticLockWorkerService workerService
    ) {
        this.workerService = workerService;
    }

    public void runExperiment(Long accountId) {
        Account firstSnapshot = workerService.loadSnapshot(accountId);
        Account staleSnapshot = workerService.loadSnapshot(accountId);

        // Первая транзакция меняет счёт и увеличивает @Version.
        workerService.increaseCurrentBalance(
                firstSnapshot.getId(),
                new BigDecimal("10.00")
        );

        // Вторая копия всё ещё содержит старую версию.
        // Сохранение должно вызвать optimistic-lock конфликт.
        workerService.saveStaleSnapshot(
                staleSnapshot,
                new BigDecimal("10.00")
        );
    }
}