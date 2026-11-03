package kz.iitu.springlab.transaction;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final PropagationExperimentService propagationService;
    private final AuditRecordRepository auditRepository;
    private final IsolationExperimentService isolationService;
    private final OptimisticLockExperimentService optimisticLockService;

    public TransferController(
            AccountService accountService,
            AccountRepository accountRepository,
            PropagationExperimentService propagationService,
            AuditRecordRepository auditRepository,
            IsolationExperimentService isolationService,
            OptimisticLockExperimentService optimisticLockService
    ) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.propagationService = propagationService;
        this.auditRepository = auditRepository;
        this.isolationService = isolationService;
        this.optimisticLockService = optimisticLockService;
    }

    @PostMapping
    public Map<String, Object> transfer(
            @RequestParam Long fromId,
            @RequestParam Long toId,
            @RequestParam BigDecimal amount
    ) {
        accountService.transfer(fromId, toId, amount);

        var from = accountRepository.findById(fromId).orElseThrow();
        var to = accountRepository.findById(toId).orElseThrow();

        return Map.of(
                "from", from.getOwnerName(),
                "fromBalance", from.getBalance(),
                "to", to.getOwnerName(),
                "toBalance", to.getBalance()
        );
    }

    @GetMapping("/balances")
    public List<Map<String, Object>> balances() {
        return accountRepository.findAll().stream()
                .map(account -> Map.<String, Object>of(
                        "id", account.getId(),
                        "owner", account.getOwnerName(),
                        "balance", account.getBalance()
                ))
                .toList();
    }

    @PostMapping("/rollback-test")
    public Map<String, Object> rollbackTest(
            @RequestParam Long fromId,
            @RequestParam BigDecimal amount
    ) {
        accountService.transferAndFail(fromId, amount);
        return Map.of("message", "Unexpected: no exception was thrown");
    }

    @PostMapping("/propagation/required")
    public void testRequired() {
        propagationService.runRequiredThenFail();
    }

    @PostMapping("/propagation/requires-new")
    public void testRequiresNew() {
        propagationService.runRequiresNewThenFail();
    }

    @GetMapping("/audit")
    public List<String> auditRecords() {
        return auditRepository.findAll().stream()
                .map(AuditRecord::getMessage)
                .toList();
    }

    @PostMapping("/isolation/read-committed")
    public Map<String, Object> testReadCommitted(
            @RequestParam(defaultValue = "1") Long accountId
    ) {
        return isolationService.testReadCommitted(accountId);
    }

    @PostMapping("/isolation/repeatable-read")
    public Map<String, Object> testRepeatableRead(
            @RequestParam(defaultValue = "1") Long accountId
    ) {
        return isolationService.testRepeatableRead(accountId);
    }

    @PostMapping("/optimistic-lock")
    public void testOptimisticLock(
            @RequestParam(defaultValue = "1") Long accountId
    ) {
        optimisticLockService.runExperiment(accountId);
    }
}