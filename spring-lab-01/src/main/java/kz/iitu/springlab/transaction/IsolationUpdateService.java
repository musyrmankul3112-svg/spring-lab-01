package kz.iitu.springlab.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class IsolationUpdateService {

    private final AccountRepository repository;

    public IsolationUpdateService(AccountRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void increaseBalance(Long accountId, BigDecimal amount) {
        Account account = repository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.credit(amount);
    }
}