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
        requireNonNullTransaction(transaction);

        ValidationResult validation = validateTransaction(transaction);
        if (!validation.isValid()) {
            return TransactionResult.failure(validation.getErrors());
        }

        try {
            Transaction processed = executeTransaction(transaction);
            persistAndNotify(processed);
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

    private void requireNonNullTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
    }

    private void persistAndNotify(Transaction processed) {
        repository.save(processed);
        notificationService.notifySuccess(processed);
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
        if (isInvalidAmount(transaction.getAmount())) {
            errors.add("Amount must be positive");
        }
    }

    private void validateFromAccount(Transaction transaction, List<String> errors) {
        if (isNullOrEmpty(transaction.getFromAccount())) {
            errors.add("From account is required");
        }
    }

    private void validateToAccount(Transaction transaction, List<String> errors) {
        if (isNullOrEmpty(transaction.getToAccount())) {
            errors.add("To account is required");
        }
    }

    private void validateDistinctAccounts(Transaction transaction, List<String> errors) {
        if (isSameAccount(transaction)) {
            errors.add("Cannot transfer to same account");
        }
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

        ensureSufficientFunds(fromAccount, transaction.getAmount());

        fromAccount.debit(transaction.getAmount());
        toAccount.credit(transaction.getAmount());

        markTransactionCompleted(transaction);

        return transaction;
    }

    private void ensureSufficientFunds(Account fromAccount, BigDecimal amount) throws InsufficientFundsException {
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Account has insufficient funds");
        }
    }

    private void markTransactionCompleted(Transaction transaction) {
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setProcessedAt(LocalDateTime.now());
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