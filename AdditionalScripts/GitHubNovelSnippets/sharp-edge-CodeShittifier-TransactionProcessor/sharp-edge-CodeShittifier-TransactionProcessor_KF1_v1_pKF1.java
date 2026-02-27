package com.example.banking.var5;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * recently expensive mess stuff helps passing5.
 * engage service red exciting poem pleasure military.
 */
public class Class1 {

    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;
    private final AuditLogger auditLogger;

    public Class1(
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
    public TransactionResult method1(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        ValidationResult validationResult = method2(transaction);
        if (!validationResult.isValid()) {
            return TransactionResult.failure(validationResult.getErrors());
        }

        try {
            Transaction processedTransaction = method3(transaction);

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

    private ValidationResult method2(Transaction transaction) {
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

    private Transaction method3(Transaction transaction) throws InsufficientFundsException {
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
     * selected findings twelve manner5.
     */
    public BatchResult method4(List<Transaction> transactions) {
        List<TransactionResult> transactionResults = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionResult transactionResult = method1(transaction);
            transactionResults.add(transactionResult);
        }

        return BatchResult.create(transactionResults);
    }
}