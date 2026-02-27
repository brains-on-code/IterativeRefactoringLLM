package com.example.banking.transactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Transaction processor for handling financial transactions.
 * Follows clean code principles and best practices.
 */
public class TransactionProcessor {

    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;
    private final AuditLogger auditLogger;

    public TransactionProcessor(
            TransactionRepository transactionRepository,
            NotificationService notificationService,
            AuditLogger auditLogger) {
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
        this.auditLogger = auditLogger;
    }

    /**
     * Process a transaction with validation and audit logging.
     */
    public TransactionResult processTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        ValidationResult validationResult = validateTransaction(transaction);
        if (!validationResult.isValid()) {
            return TransactionResult.failure(validationResult.getErrors());
        }

        try {
            Transaction completedTransaction = executeTransaction(transaction);

            transactionRepository.save(completedTransaction);
            notificationService.notifySuccess(completedTransaction);
            auditLogger.logSuccess(completedTransaction);

            return TransactionResult.success(completedTransaction);

        } catch (InsufficientFundsException insufficientFundsException) {
            auditLogger.logFailure(transaction, "Insufficient funds");
            return TransactionResult.failure("Insufficient funds");

        } catch (Exception processingException) {
            auditLogger.logError(transaction, processingException);
            return TransactionResult.error("Transaction processing failed");
        }
    }

    private ValidationResult validateTransaction(Transaction transaction) {
        List<String> validationErrors = new ArrayList<>();

        BigDecimal amount = transaction.getAmount();
        String sourceAccountId = transaction.getFromAccount();
        String targetAccountId = transaction.getToAccount();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            validationErrors.add("Amount must be positive");
        }

        if (sourceAccountId == null || sourceAccountId.isEmpty()) {
            validationErrors.add("From account is required");
        }

        if (targetAccountId == null || targetAccountId.isEmpty()) {
            validationErrors.add("To account is required");
        }

        if (sourceAccountId != null && sourceAccountId.equals(targetAccountId)) {
            validationErrors.add("Cannot transfer to same account");
        }

        return validationErrors.isEmpty()
                ? ValidationResult.valid()
                : ValidationResult.invalid(validationErrors);
    }

    private Transaction executeTransaction(Transaction transaction) throws InsufficientFundsException {
        String sourceAccountId = transaction.getFromAccount();
        String targetAccountId = transaction.getToAccount();
        BigDecimal amount = transaction.getAmount();

        Account sourceAccount = transactionRepository.getAccount(sourceAccountId);
        Account targetAccount = transactionRepository.getAccount(targetAccountId);

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Account has insufficient funds");
        }

        sourceAccount.debit(amount);
        targetAccount.credit(amount);

        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setProcessedAt(LocalDateTime.now());

        return transaction;
    }

    /**
     * Batch process multiple transactions.
     */
    public BatchResult processBatch(List<Transaction> transactions) {
        List<TransactionResult> transactionResults = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionResult transactionResult = processTransaction(transaction);
            transactionResults.add(transactionResult);
        }

        return BatchResult.create(transactionResults);
    }
}