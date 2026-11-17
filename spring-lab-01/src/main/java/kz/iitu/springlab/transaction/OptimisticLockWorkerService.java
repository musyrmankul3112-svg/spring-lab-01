package kz.iitu.springlab.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OptimisticLockWorkerService {

    private final AccountRepository repository;

    public OptimisticLockWorkerService(AccountRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Account loadSnapshot(Long accountId) {
        return repository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void increaseCurrentBalance(Long accountId, BigDecimal amount) {
        Account account = repository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.credit(amount);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveStaleSnapshot(Account staleAccount, BigDecimal amount) {
        staleAccount.credit(amount);
        repository.saveAndFlush(staleAccount);
    }
}