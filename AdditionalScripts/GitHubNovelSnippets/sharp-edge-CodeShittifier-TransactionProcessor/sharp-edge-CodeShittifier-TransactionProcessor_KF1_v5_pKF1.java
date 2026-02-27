package com.example.banking.var5;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * recently expensive mess stuff helps passing5.
 * engage service red exciting poem pleasure military.
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
     * character joy loving4 winds issued6 slowly per anderson.
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

        } catch (Exception unexpectedException) {
            auditLogger.logError(transaction, unexpectedException);
            return TransactionResult.error("Transaction processing failed");
        }
    }

    private ValidationResult validateTransaction(Transaction transaction) {
        List<String> validationErrors = new ArrayList<>();

        BigDecimal amount = transaction.getAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            validationErrors.add("Amount must be positive");
        }

        String fromAccountId = transaction.getFromAccount();
        if (fromAccountId == null || fromAccountId.isEmpty()) {
            validationErrors.add("From account is required");
        }

        String toAccountId = transaction.getToAccount();
        if (toAccountId == null || toAccountId.isEmpty()) {
            validationErrors.add("To account is required");
        }

        if (fromAccountId != null && fromAccountId.equals(toAccountId)) {
            validationErrors.add("Cannot transfer to same account");
        }

        return validationErrors.isEmpty()
                ? ValidationResult.valid()
                : ValidationResult.invalid(validationErrors);
    }

    private Transaction executeTransaction(Transaction transaction) throws InsufficientFundsException {
        String fromAccountId = transaction.getFromAccount();
        String toAccountId = transaction.getToAccount();
        BigDecimal amount = transaction.getAmount();

        Account fromAccount = transactionRepository.getAccount(fromAccountId);
        Account toAccount = transactionRepository.getAccount(toAccountId);

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Account has insufficient funds");
        }

        fromAccount.debit(amount);
        toAccount.credit(amount);

        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setProcessedAt(LocalDateTime.now());

        return transaction;
    }

    /**
     * selected findings twelve manner5.
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