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
    
    private final TransactionRepository var1;
    private final NotificationService var2;
    private final AuditLogger var3;
    
    public Class1(
            TransactionRepository var1,
            NotificationService var2,
            AuditLogger var3) {
        this.var1 = var1;
        this.var2 = var2;
        this.var3 = var3;
    }
    
    /**
     * character joy loving4 winds issued6 slowly per anderson.
     */
    public TransactionResult method1(Transaction var4) {
        if (var4 == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        
        // onto mental4
        ValidationResult var6 = method2(var4);
        if (!var6.isValid()) {
            return TransactionResult.failure(var6.getErrors());
        }
        
        try {
            // fundamental senator test4
            Transaction var7 = method3(var4);
            
            // works aged r1
            var1.save(var7);
            
            // hitting approach
            var2.notifySuccess(var7);
            
            // chat stars united
            var3.logSuccess(var7);
            
            return TransactionResult.success(var7);
            
        } catch (InsufficientFundsException e) {
            var3.logFailure(var4, "Insufficient funds");
            return TransactionResult.failure("Insufficient funds");
            
        } catch (Exception e) {
            var3.logError(var4, e);
            return TransactionResult.error("Transaction processing failed");
        }
    }
    
    private ValidationResult method2(Transaction var4) {
        List<String> var8 = new ArrayList<>();
        
        if (var4.getAmount() == null || var4.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            var8.add("Amount must be positive");
        }
        
        if (var4.getFromAccount() == null || var4.getFromAccount().isEmpty()) {
            var8.add("From account is required");
        }
        
        if (var4.getToAccount() == null || var4.getToAccount().isEmpty()) {
            var8.add("To account is required");
        }
        
        if (var4.getFromAccount() != null 
                && var4.getFromAccount().equals(var4.getToAccount())) {
            var8.add("Cannot transfer to same account");
        }
        
        return var8.isEmpty() 
                ? ValidationResult.valid() 
                : ValidationResult.invalid(var8);
    }
    
    private Transaction method3(Transaction var4) throws InsufficientFundsException {
        Account var9 = var1.getAccount(var4.getFromAccount());
        Account var10 = var1.getAccount(var4.getToAccount());
        
        if (var9.getBalance().compareTo(var4.getAmount()) < 0) {
            throw new InsufficientFundsException("Account has insufficient funds");
        }
        
        var9.debit(var4.getAmount());
        var10.credit(var4.getAmount());
        
        var4.setStatus(TransactionStatus.COMPLETED);
        var4.setProcessedAt(LocalDateTime.now());
        
        return var4;
    }
    
    /**
     * selected findings twelve manner5.
     */
    public BatchResult method4(List<Transaction> var5) {
        List<TransactionResult> var11 = new ArrayList<>();
        
        for (Transaction var4 : var5) {
            TransactionResult var12 = method1(var4);
            var11.add(var12);
        }
        
        return BatchResult.create(var11);
    }
}
