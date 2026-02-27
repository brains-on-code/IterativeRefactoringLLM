package com.example.banking.transactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionProcessor {

    private static final String ERROR_TRANSACTION_NULL = "Transaction cannot be null";
    private static final String ERROR_AMOUNT_POSITIVE = "Amount must be positive";
    private static final String ERROR_FROM_ACCOUNT_REQUIRED = "From account is required";
    private static final String ERROR_TO_ACCOUNT_REQUIRED = "To account is required";
    private static final String ERROR_SAME_ACCOUNT = "Cannot transfer to same account";
    private static final String ERROR_INSUFFICIENT_FUNDS = "Insufficient funds";
    private static final String ERROR_PROCESSING_FAILED = "Transaction processing failed";

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
            handleSuccessfulProcessing(processedTransaction);
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
            throw new IllegalArgumentException(ERROR_TRANSACTION_NULL);
        }
    }

    private void handleSuccessfulProcessing(Transaction processedTransaction) {
        repository.save(processedTransaction);
        notificationService.notifySuccess(processedTransaction);
        auditLogger.logSuccess(processedTransaction);
    }

    private ValidationResult validateTransaction(Transaction transaction) {
        List<String> errors = new ArrayList<>();

        validateAmount(transaction.getAmount(), errors);
        validateFromAccount(transaction.getFromAccount(), errors);
        validateToAccount(transaction.getToAccount(), errors);
        validateDifferentAccounts(transaction.getFromAccount(), transaction.getToAccount(), errors);

        if (errors.isEmpty()) {
            return ValidationResult.valid();
        }
        return ValidationResult.invalid(errors);
    }

    private void validateAmount(BigDecimal amount, List<String> errors) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add(ERROR_AMOUNT_POSITIVE);
        }
    }

    private void validateFromAccount(String fromAccount, List<String> errors) {
        if (fromAccount == null || fromAccount.isBlank()) {
            errors.add(ERROR_FROM_ACCOUNT_REQUIRED);
        }
    }

    private void validateToAccount(String toAccount, List<String> errors) {
        if (toAccount == null || toAccount.isBlank()) {
            errors.add(ERROR_TO_ACCOUNT_REQUIRED);
        }
    }

    private void validateDifferentAccounts(String fromAccount, String toAccount, List<String> errors) {
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

        markTransactionCompleted(transaction);

        return transaction;
    }

    private void ensureSufficientFunds(Account fromAccount, BigDecimal amount) throws InsufficientFundsException {
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(ERROR_INSUFFICIENT_FUNDS);
        }
    }

    private void markTransactionCompleted(Transaction transaction) {
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setProcessedAt(LocalDateTime.now());
    }
}