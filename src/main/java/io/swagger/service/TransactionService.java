package io.swagger.service;

import io.swagger.model.entity.Account;
import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.model.enumeration.AccountType;
import io.swagger.repo.AccountRepo;
import io.swagger.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    TransactionValidatorService transactionValidatorService;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    UserService userService;

    public Transaction createTransaction(Transaction trans) {
        Account accountFrom = getAccountByIban(trans.getFrom().getIban());
        Account accountTo = getAccountByIban(trans.getTo());

        validateAccounts(accountFrom, accountTo);
        checkGeneralConditions(trans);

        // Set the timestamp before limit check
        trans.setTimestamp(LocalDateTime.now());

        if (isRegularTransaction(accountFrom, accountTo)) {
            checkDailyLimit(trans);
        }

        trans.setAccountType(determineAccountType(accountFrom, accountTo));
        updateBalances(accountFrom, accountTo, trans.getAmount());

        return transactionRepo.save(trans);
    }

    private void validateAccounts(Account accountFrom, Account accountTo) {
        if (transactionValidatorService.areBothAccountsSavings(accountFrom, accountTo)) {
            throw new IllegalArgumentException("Cannot create transaction; Cannot transfer between two savings accounts.");
        }

        if (transactionValidatorService.isOneAccountSavings(accountFrom, accountTo)) {
            if (!transactionValidatorService.isUserOwnerOfAccounts(accountFrom, accountTo)) {
                throw new IllegalArgumentException("Cannot create transaction; Cannot transfer to or from a savings account that is not yours.");
            }
        }
    }

    private void checkGeneralConditions(Transaction trans) {
        String fromIban = trans.getFrom().getIban();
        String toIban = trans.getTo();

        if (!transactionValidatorService.isDifferentAccount(fromIban, toIban)) {
            throw new IllegalArgumentException("Cannot create transaction; cannot transfer between two accounts that are the same.");
        }

        if (!transactionValidatorService.areAccountsActive(fromIban, toIban)) {
            throw new IllegalArgumentException("Cannot create transaction; at least one of the accounts is inactive.");
        }

        if (trans.getAmount() <= 0) {
            throw new IllegalArgumentException("Cannot create transaction; Amount must be greater than 0.");
        }

        Optional<Account> accountFrom = accountService.findAccountByIban(fromIban);
        if (!transactionValidatorService.hasSufficientFunds(accountFrom, trans.getAmount())) {
            throw new IllegalArgumentException("Cannot create transaction; Cannot exceed absolute limit.");
        }
        
        User userPerforming = userService.findById(trans.getUserPerforming());
        if (!transactionValidatorService.doesNotExceedTransactionLimit(userPerforming, trans)) {
            throw new IllegalArgumentException("Cannot create transaction; Cannot exceed transaction limit.");
        }
    }

    private void checkDailyLimit(Transaction trans) {
        User userPerforming = userService.findById(trans.getUserPerforming());
        Account accountFrom = getAccountByIban(trans.getFrom().getIban());
        Account accountTo = getAccountByIban(trans.getTo());
        AccountType transactionAccountType = determineAccountType(accountFrom, accountTo);
        trans.setAccountType(transactionAccountType);
        if (!transactionValidatorService.doesNotExceedDayLimit(userPerforming, trans)) {
            throw new IllegalArgumentException("Cannot create transaction; Cannot exceed day limit.");
        }
    }

    private boolean isRegularTransaction(Account accountFrom, Account accountTo) {
        return !transactionValidatorService.isOneAccountSavings(accountFrom, accountTo);
    }

    private void updateBalances(Account accountFrom, Account accountTo, double amount) {
        updateFromBalance(accountFrom, amount);
        updateToBalance(accountTo, amount);
    }

    private void updateToBalance(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
        accountRepo.save(account);
    }

    private void updateFromBalance(Account account, double amount) {
        account.setBalance(account.getBalance() - amount);
        accountRepo.save(account);
    }

    public Transaction createWithdrawal(Transaction trans) {
        Account accountFrom = getAccountByIban(trans.getFrom().getIban());
        Account accountTo = getAccountByIban(trans.getTo());

        validatePinCode(trans);
        checkGeneralConditions(trans);

        trans.setTimestamp(LocalDateTime.now());

        trans.setAccountType(determineAccountType(accountFrom, accountTo));
        checkDailyLimit(trans);

        updateFromBalance(accountFrom, trans.getAmount());
        return transactionRepo.save(trans);
    }

    public Transaction createDeposit(Transaction trans) {
        validatePinCode(trans);
        Account accountTo = getAccountByIban(trans.getTo());
        updateToBalance(accountTo, trans.getAmount());
        return transactionRepo.save(trans);
    }

    public Transaction findTransactionById(UUID id) {
        return transactionRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Transaction with the given id not found."));
    }

    public List<Transaction> findTransactionsByUserId(UUID userId) {
        return transactionRepo.findByUserPerforming(userId);
    }

    public List<Transaction> getAllTransactionsForEmployees(UUID userId) {
        return transactionRepo.findByUserPerforming(userId);
    }

    public List<Transaction> findFilteredTransactions(UUID userId, String fromAccount, String toAccount, Double amount, String amountFilterType, LocalDateTime startTime, LocalDateTime endTime) {
        return transactionRepo.findFilteredTransactions(userId, fromAccount, toAccount, amount, amountFilterType, startTime, endTime);
    }

    private boolean isPinCodeNull(Integer pinCode) {
        return pinCode == null;
    }

    private boolean isFromPinCodeCorrect(Transaction trans) {
        Account account = getAccountByIban(trans.getFrom().getIban());
        return trans.getPinCode().equals(account.getPinCode());
    }

    private boolean isToPinCodeCorrect(Transaction trans) {
        Account account = getAccountByIban(trans.getTo());
        return trans.getPinCode().equals(account.getPinCode());
    }

    private void validatePinCode(Transaction trans) {
        if (isPinCodeNull(trans.getPinCode())) {
            throw new IllegalArgumentException("Operation failed; no pin code entered");
        }
        if (!isFromPinCodeCorrect(trans) && !isToPinCodeCorrect(trans)) {
            throw new IllegalArgumentException("Operation failed; wrong pin code entered");
        }
    }

    private Account getAccountByIban(String iban) {
        return accountService.findAccountByIban(iban).orElseThrow(() -> new NoSuchElementException("Account with the given IBAN not found."));
    }

    private AccountType determineAccountType(Account accountFrom, Account accountTo) {
        if (accountFrom.getAccountType().equals(AccountType.SAVINGS) || accountTo.getAccountType().equals(AccountType.SAVINGS)) {
            return AccountType.SAVINGS;
        } else {
            return AccountType.CURRENT;
        }
    }
}
