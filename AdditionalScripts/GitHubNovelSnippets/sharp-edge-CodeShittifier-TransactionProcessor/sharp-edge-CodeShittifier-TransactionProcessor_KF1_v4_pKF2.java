package com.example.banking.var5;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for validating, executing, persisting,
 * and auditing banking transactions.
 */
public class TransactionService {

    private static final String ERROR_INSUFFICIENT_FUNDS = "Insufficient funds";
    private static final String ERROR_PROCESSING_FAILED = "Transaction processing failed";
    private static final String ERROR_AMOUNT_POSITIVE = "Amount must be positive";
    private static final String ERROR_FROM_ACCOUNT_REQUIRED = "From account is required";
    private static final String ERROR_TO_ACCOUNT_REQUIRED = "To account is required";
    private static final String ERROR_SAME_ACCOUNT = "Cannot transfer to same account";
    private static final String ERROR_NULL_TRANSACTION = "Transaction cannot be null";

    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;
    private final AuditLogger auditLogger;

    public TransactionService(
            TransactionRepository transactionRepository,
            NotificationService notificationService,
            AuditLogger auditLogger
    ) {
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
        this.auditLogger = auditLogger;
    }

    /**
     * Validates, executes, persists, notifies, and audits a single transaction.
     *
     * @param transaction transaction to process
     * @return processing result
     */
    public TransactionResult processTransaction(Transaction transaction) {
        requireNonNullTransaction(transaction);

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
            auditLogger.logFailure(transaction, ERROR_INSUFFICIENT_FUNDS);
            return TransactionResult.failure(ERROR_INSUFFICIENT_FUNDS);

        } catch (Exception e) {
            auditLogger.logError(transaction, e);
            return TransactionResult.error(ERROR_PROCESSING_FAILED);
        }
    }

    private void requireNonNullTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException(ERROR_NULL_TRANSACTION);
        }
    }

    /**
     * Applies basic business-rule validation to a transaction.
     *
     * @param transaction transaction to validate
     * @return validation result
     */
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
        if (fromAccount == null || fromAccount.isEmpty()) {
            errors.add(ERROR_FROM_ACCOUNT_REQUIRED);
        }
    }

    private void validateToAccount(Transaction transaction, List<String> errors) {
        String toAccount = transaction.getToAccount();
        if (toAccount == null || toAccount.isEmpty()) {
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

    /**
     * Debits and credits the appropriate accounts and updates transaction metadata.
     *
     * @param transaction transaction to execute
     * @return executed transaction
     * @throws InsufficientFundsException if the source account has insufficient funds
     */
    private Transaction executeTransaction(Transaction transaction) throws InsufficientFundsException {
        Account fromAccount = transactionRepository.getAccount(transaction.getFromAccount());
        Account toAccount = transactionRepository.getAccount(transaction.getToAccount());

        ensureSufficientFunds(fromAccount, transaction.getAmount());

        fromAccount.debit(transaction.getAmount());
        toAccount.credit(transaction.getAmount());

        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setProcessedAt(LocalDateTime.now());

        return transaction;
    }

    private void ensureSufficientFunds(Account fromAccount, BigDecimal amount)
            throws InsufficientFundsException {
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(ERROR_INSUFFICIENT_FUNDS);
        }
    }

    /**
     * Processes a list of transactions and aggregates their results.
     *
     * @param transactions transactions to process
     * @return aggregated batch result
     */
    public BatchResult processBatch(List<Transaction> transactions) {
        List<TransactionResult> results = new ArrayList<>();

        for (Transaction transaction : transactions) {
            results.add(processTransaction(transaction));
        }

        return BatchResult.create(results);
    }
}