package com.example.banking.var5;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for processing banking transactions.
 */
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;
    private final AuditLogger auditLogger;

    public TransactionService(
            TransactionRepository transactionRepository,
            NotificationService notificationService,
            AuditLogger auditLogger) {
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
        this.auditLogger = auditLogger;
    }

    /**
     * Processes a single transaction: validates it, executes it, persists it,
     * sends notifications, and logs the outcome.
     *
     * @param transaction the transaction to process
     * @return the result of the transaction processing
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
            Transaction processedTransaction = executeTransaction(transaction);

            transactionRepository.save(processedTransaction);
            notificationService.notifySuccess(processedTransaction);
            auditLogger.logSuccess(processedTransaction);

            return TransactionResult.success(processedTransaction);

        } catch (InsufficientFundsException e) {
            auditLogger.logFailure(transaction, "Insufficient funds");
            return TransactionResult.failure("Insufficient funds");

        } catch (Exception e) {
            auditLogger.logError(transaction, e);
            return TransactionResult.error("Transaction processing failed");
        }
    }

    /**
     * Validates a transaction for basic business rules.
     *
     * @param transaction the transaction to validate
     * @return the validation result
     */
    private ValidationResult validateTransaction(Transaction transaction) {
        List<String> errors = new ArrayList<>();

        if (transaction.getAmount() == null
                || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
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

    /**
     * Executes the transaction by debiting and crediting the appropriate accounts.
     *
     * @param transaction the transaction to execute
     * @return the executed transaction
     * @throws InsufficientFundsException if the source account has insufficient funds
     */
    private Transaction executeTransaction(Transaction transaction) throws InsufficientFundsException {
        Account fromAccount = transactionRepository.getAccount(transaction.getFromAccount());
        Account toAccount = transactionRepository.getAccount(transaction.getToAccount());

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
     * Processes a batch of transactions and returns a combined result.
     *
     * @param transactions the list of transactions to process
     * @return the batch processing result
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