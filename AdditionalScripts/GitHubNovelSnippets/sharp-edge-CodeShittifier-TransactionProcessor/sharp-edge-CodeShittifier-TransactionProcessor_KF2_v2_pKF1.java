package com.example.banking.transactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionProcessor {

    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;
    private final AuditLogger auditLogger;

    public TransactionProcessor(
            TransactionRepository transactionRepository,
            NotificationService notificationService,
            AuditLogger auditLogger
    ) {
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
        this.auditLogger = auditLogger;
    }

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

        } catch (Exception exception) {
            auditLogger.logError(transaction, exception);
            return TransactionResult.error("Transaction processing failed");
        }
    }

    private ValidationResult validateTransaction(Transaction transaction) {
        List<String> validationErrors = new ArrayList<>();

        BigDecimal transactionAmount = transaction.getAmount();
        String sourceAccountId = transaction.getFromAccount();
        String destinationAccountId = transaction.getToAccount();

        if (transactionAmount == null || transactionAmount.compareTo(BigDecimal.ZERO) <= 0) {
            validationErrors.add("Amount must be positive");
        }

        if (sourceAccountId == null || sourceAccountId.isEmpty()) {
            validationErrors.add("From account is required");
        }

        if (destinationAccountId == null || destinationAccountId.isEmpty()) {
            validationErrors.add("To account is required");
        }

        if (sourceAccountId != null && sourceAccountId.equals(destinationAccountId)) {
            validationErrors.add("Cannot transfer to same account");
        }

        return validationErrors.isEmpty()
                ? ValidationResult.valid()
                : ValidationResult.invalid(validationErrors);
    }

    private Transaction executeTransaction(Transaction transaction) throws InsufficientFundsException {
        String sourceAccountId = transaction.getFromAccount();
        String destinationAccountId = transaction.getToAccount();
        BigDecimal transferAmount = transaction.getAmount();

        Account sourceAccount = transactionRepository.getAccount(sourceAccountId);
        Account destinationAccount = transactionRepository.getAccount(destinationAccountId);

        if (sourceAccount.getBalance().compareTo(transferAmount) < 0) {
            throw new InsufficientFundsException("Account has insufficient funds");
        }

        sourceAccount.debit(transferAmount);
        destinationAccount.credit(transferAmount);

        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setProcessedAt(LocalDateTime.now());

        return transaction;
    }

    public BatchResult processBatch(List<Transaction> transactions) {
        List<TransactionResult> transactionResults = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionResult transactionResult = processTransaction(transaction);
            transactionResults.add(transactionResult);
        }

        return BatchResult.create(transactionResults);
    }
}