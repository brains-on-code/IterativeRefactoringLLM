package com.example.banking.transactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionProcessor {

    private static final String ERROR_AMOUNT_POSITIVE = "Amount must be positive";
    private static final String ERROR_FROM_ACCOUNT_REQUIRED = "From account is required";
    private static final String ERROR_TO_ACCOUNT_REQUIRED = "To account is required";
    private static final String ERROR_SAME_ACCOUNT = "Cannot transfer to same account";
    private static final String ERROR_INSUFFICIENT_FUNDS = "Insufficient funds";
    private static final String ERROR_PROCESSING_FAILED = "Transaction processing failed";
    private static final String ERROR_NULL_TRANSACTION = "Transaction cannot be null";

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
        requireNonNullTransaction(transaction);

        ValidationResult validationResult = validateTransaction(transaction);
        if (!validationResult.isValid()) {
            return TransactionResult.failure(validationResult.getErrors());
        }

        try {
            Transaction processedTransaction = executeTransaction(transaction);
            persistAndNotify(processedTransaction);
            return TransactionResult.success(processedTransaction);
        } catch (InsufficientFundsException e) {
            auditLogger.logFailure(transaction, ERROR_INSUFFICIENT_FUNDS);
            return TransactionResult.failure(ERROR_INSUFFICIENT_FUNDS);
        } catch (Exception e) {
            auditLogger.logError(transaction, e);
            return TransactionResult.error(ERROR_PROCESSING_FAILED);
        }
    }

    public BatchResult processBatch(List<Transaction> transactions) {
        List<TransactionResult> results = new ArrayList<>();
        for (Transaction transaction : transactions) {
            results.add(processTransaction(transaction));
        }
        return BatchResult.create(results);
    }

    private void requireNonNullTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException(ERROR_NULL_TRANSACTION);
        }
    }

    private void persistAndNotify(Transaction processedTransaction) {
        repository.save(processedTransaction);
        notificationService.notifySuccess(processedTransaction);
        auditLogger.logSuccess(processedTransaction);
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
            errors.add(ERROR_AMOUNT_POSITIVE);
        }
    }

    private void validateFromAccount(Transaction transaction, List<String> errors) {
        String fromAccount = transaction.getFromAccount();
        if (isNullOrEmpty(fromAccount)) {
            errors.add(ERROR_FROM_ACCOUNT_REQUIRED);
        }
    }

    private void validateToAccount(Transaction transaction, List<String> errors) {
        String toAccount = transaction.getToAccount();
        if (isNullOrEmpty(toAccount)) {
            errors.add(ERROR_TO_ACCOUNT_REQUIRED);
        }
    }

    private void validateDistinctAccounts(Transaction transaction, List<String> errors) {
        String fromAccount = transaction.getFromAccount();
        String toAccount = transaction.getToAccount();

        if (fromAccount != null && fromAccount.equals(toAccount)) {
            errors.add(ERROR_SAME_ACCOUNT);
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
            throw new InsufficientFundsException(ERROR_INSUFFICIENT_FUNDS);
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}