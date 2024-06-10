package io.swagger.service;

import io.swagger.model.entity.Account;
import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.model.enumeration.AccountType;
import io.swagger.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TransactionValidatorService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionRepo transactionRepo;

    public boolean areBothAccountsSavings(Account from, Account to) {
        return from.getAccountType().equals(AccountType.SAVINGS) && to.getAccountType().equals(AccountType.SAVINGS);
    }

    public boolean isOneAccountSavings(Account from, Account to) {
        return from.getAccountType().equals(AccountType.SAVINGS) || to.getAccountType().equals(AccountType.SAVINGS);
    }

    public boolean isUserOwnerOfAccounts(Account from, Account to) {
        return from.getUser().getId().equals(to.getUser().getId());
    }

    public boolean isDifferentAccount(String accountFrom, String accountTo) {
        return !accountFrom.equals(accountTo);
    }

    public boolean hasSufficientFunds(Optional<Account> optionalAccountFrom, double amount) {
        Account accountFrom = optionalAccountFrom.orElseThrow(() -> new NoSuchElementException("Account not found."));
        return (accountFrom.getBalance() - amount) >= accountFrom.getAbsLimit();
    }

    public boolean doesNotExceedDayLimit(User user, Transaction transaction) {
        double dailyLimit = user.getDayLimit();
        LocalDateTime today = LocalDateTime.now();

        double totalAmountToday = transactionRepo.findAllByUserPerformingAndTimestamp(user.getId(), today)
                .stream()
                .mapToDouble(Transaction::getAmount)
                .sum();

        return (totalAmountToday + transaction.getAmount()) <= dailyLimit;
    }


    public boolean areAccountsActive(String ibanFrom, String ibanTo) {
        Account from = accountService.findAccountByIban(ibanFrom).orElseThrow(() -> new NoSuchElementException("From account not found."));
        Account to = accountService.findAccountByIban(ibanTo).orElseThrow(() -> new NoSuchElementException("To account not found."));
        return from.getActive() && to.getActive();
    }

}