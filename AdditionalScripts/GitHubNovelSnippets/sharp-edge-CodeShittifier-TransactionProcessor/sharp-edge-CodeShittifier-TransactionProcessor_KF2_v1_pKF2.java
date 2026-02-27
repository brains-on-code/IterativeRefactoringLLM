package com.example.banking.transactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionProcessor {

    private final TransactionRepository repository;
    private final NotificationService notificationService;
    private final AuditLogger auditLogger;

    public TransactionProcessor(
            TransactionRepository repository,
            NotificationService notificationService,
            AuditLogger auditLogger
    ) {
        this.repository = repository;
        this.notificationService = notificationService;
        this.auditLogger = auditLogger;
    }

    public TransactionResult processTransaction(Transaction transaction) {
        validateNotNull(transaction);

        ValidationResult validation = validateTransaction(transaction);
        if (!validation.isValid()) {
            return TransactionResult.failure(validation.getErrors());
        }

        try {
            Transaction processed = executeTransaction(transaction);
            persistAndNotify(processed);
            return TransactionResult.success(processed);

        } catch (InsufficientFundsException e) {
            auditLogger.logFailure(transaction, "Insufficient funds");
            return TransactionResult.failure("Insufficient funds");

        } catch (Exception e) {
            auditLogger.logError(transaction, e);
            return TransactionResult.error("Transaction processing failed");
        }
    }

    private void validateNotNull(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
    }

    private void persistAndNotify(Transaction processed) {
        repository.save(processed);
        notificationService.notifySuccess(processed);
        auditLogger.logSuccess(processed);
    }

    private ValidationResult validateTransaction(Transaction transaction) {
        List<String> errors = new ArrayList<>();

        validateAmount(transaction, errors);
        validateFromAccount(transaction, errors);
        validateToAccount(transaction, errors);
        validateDistinctAccounts(transaction, errors);

        return errors.isEmpty()
                ? ValidationResult.valid()
                : ValidationResult.invalid(errors);
    }

    private void validateAmount(Transaction transaction, List<String> errors) {
        BigDecimal amount = transaction.getAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Amount must be positive");
        }
    }

    private void validateFromAccount(Transaction transaction, List<String> errors) {
        String fromAccount = transaction.getFromAccount();
        if (fromAccount == null || fromAccount.isEmpty()) {
            errors.add("From account is required");
        }
    }

    private void validateToAccount(Transaction transaction, List<String> errors) {
        String toAccount = transaction.getToAccount();
        if (toAccount == null || toAccount.isEmpty()) {
            errors.add("To account is required");
        }
    }

    private void validateDistinctAccounts(Transaction transaction, List<String> errors) {
        String fromAccount = transaction.getFromAccount();
        String toAccount = transaction.getToAccount();

        if (fromAccount != null && fromAccount.equals(toAccount)) {
            errors.add("Cannot transfer to same account");
        }
    }

    private Transaction executeTransaction(Transaction transaction) throws InsufficientFundsException {
        Account fromAccount = repository.getAccount(transaction.getFromAccount());
        Account toAccount = repository.getAccount(transaction.getToAccount());

        ensureSufficientFunds(fromAccount, transaction.getAmount());

        fromAccount.debit(transaction.getAmount());
        toAccount.credit(transaction.getAmount());

        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setProcessedAt(LocalDateTime.now());

        return transaction;
    }

    private void ensureSufficientFunds(Account fromAccount, BigDecimal amount) throws InsufficientFundsException {
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Account has insufficient funds");
        }
    }

    public BatchResult processBatch(List<Transaction> transactions) {
        List<TransactionResult> results = new ArrayList<>();

        for (Transaction transaction : transactions) {
            results.add(processTransaction(transaction));
        }

        return BatchResult.create(results);
    }
}