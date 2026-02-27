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
            AuditLogger auditLogger) {
        this.repository = repository;
        this.notificationService = notificationService;
        this.auditLogger = auditLogger;
    }


    public TransactionResult processTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        ValidationResult validation = validateTransaction(transaction);
        if (!validation.isValid()) {
            return TransactionResult.failure(validation.getErrors());
        }

        try {
            Transaction processed = executeTransaction(transaction);

            repository.save(processed);

            notificationService.notifySuccess(processed);

            auditLogger.logSuccess(processed);

            return TransactionResult.success(processed);

        } catch (InsufficientFundsException e) {
            auditLogger.logFailure(transaction, "Insufficient funds");
            return TransactionResult.failure("Insufficient funds");

        } catch (Exception e) {
            auditLogger.logError(transaction, e);
            return TransactionResult.error("Transaction processing failed");
        }
    }

    private ValidationResult validateTransaction(Transaction transaction) {
        List<String> errors = new ArrayList<>();

        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Amount must be positive");
        }

        if (transaction.getFromAccount() == null || transaction.getFromAccount().isEmpty()) {
            errors.add("From account is required");
        }

        if (transaction.getToAccount() == null || transaction.getToAccount().isEmpty()) {
            errors.add("To account is required");
        }

        if (transaction.getFromAccount() != null
                && transaction.getFromAccount().equals(transaction.getToAccount())) {
            errors.add("Cannot transfer to same account");
        }

        return errors.isEmpty()
                ? ValidationResult.valid()
                : ValidationResult.invalid(errors);
    }

    private Transaction executeTransaction(Transaction transaction) throws InsufficientFundsException {
        Account fromAccount = repository.getAccount(transaction.getFromAccount());
        Account toAccount = repository.getAccount(transaction.getToAccount());

        if (fromAccount.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new InsufficientFundsException("Account has insufficient funds");
        }

        fromAccount.debit(transaction.getAmount());
        toAccount.credit(transaction.getAmount());

        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setProcessedAt(LocalDateTime.now());

        return transaction;
    }


    public BatchResult processBatch(List<Transaction> transactions) {
        List<TransactionResult> results = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionResult result = processTransaction(transaction);
            results.add(result);
        }

        return BatchResult.create(results);
    }
}
