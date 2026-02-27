package com.example.banking.transactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles processing of financial transactions, including validation,
 * persistence, notifications, and audit logging.
 */
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

    /**
     * Processes a single transaction with validation and audit logging.
     *
     * @param transaction the transaction to process
     * @return the result of the transaction processing
     */
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

        if (isInvalidAmount(transaction.getAmount())) {
            errors.add("Amount must be positive");
        }

        if (isNullOrEmpty(transaction.getFromAccount())) {
            errors.add("From account is required");
        }

        if (isNullOrEmpty(transaction.getToAccount())) {
            errors.add("To account is required");
        }

        if (isSameAccount(transaction)) {
            errors.add("Cannot transfer to same account");
        }

        return errors.isEmpty()
                ? ValidationResult.valid()
                : ValidationResult.invalid(errors);
    }

    private boolean isInvalidAmount(BigDecimal amount) {
        return amount == null || amount.compareTo(BigDecimal.ZERO) <= 0;
    }

    private boolean isNullOrEmpty(String account) {
        return account == null || account.isEmpty();
    }

    private boolean isSameAccount(Transaction transaction) {
        String from = transaction.getFromAccount();
        String to = transaction.getToAccount();
        return from != null && from.equals(to);
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

    /**
     * Processes a batch of transactions.
     *
     * @param transactions the list of transactions to process
     * @return the aggregated result of the batch processing
     */
    public BatchResult processBatch(List<Transaction> transactions) {
        List<TransactionResult> results = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionResult result = processTransaction(transaction);
            results.add(result);
        }

        return BatchResult.create(results);
    }
}